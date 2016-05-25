package it.nicola_amatucci.github_issues_collector.engine;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import it.nicola_amatucci.github_issues_collector.config.Configuration;
import it.nicola_amatucci.github_issues_collector.entity.Issue;
import it.nicola_amatucci.github_issues_collector.entity.Repository;

/**
 * Engine 
 * 
 * @author Nicola
 *
 */
public class GithubIssuesCollectorEngine {

	private final static Logger Log = Logger.getLogger(GithubIssuesCollectorEngine.class.getName()); 

	Repository repository;
	private Dao<Issue, Integer> issueDao;
	
	private Integer currentPage = 1;
	private Integer maxIssueNum = null;
	private Integer pagesNum = 0;
	
	/**
	 * Constructor
	 * 
	 * @param repository
	 * @param issueDao
	 */
	public GithubIssuesCollectorEngine(Repository repository, Dao<Issue, Integer> issueDao) {
		super();
		this.repository = repository;
		this.issueDao = issueDao;
	}
	
	/**
	 * Read Issues using GitHub API v3
	 */
	public void readIssues() {
		
		do {
			try {
				HttpClient httpClient = HttpClientBuilder.create().build();
		        HttpGet httpget = new HttpGet(repository.getIssuesUrl(currentPage));
		        
		        if (Configuration.GITHUB_OAUTH_TOKEN != null) { 
		        	httpget.addHeader("Authorization","token " + Configuration.GITHUB_OAUTH_TOKEN);
		        }
		        
		        HttpResponse response = httpClient.execute(httpget);
		        
		        Log.info("X-RateLimit-Remaining = " + response.getFirstHeader("X-RateLimit-Remaining").getValue());
		        
		        if (response.getFirstHeader("X-RateLimit-Remaining").getValue().equals("0")) {
		        	Log.info("X-RateLimit-Limit = " + response.getFirstHeader("X-RateLimit-Limit").getValue());
		        	Log.info("X-RateLimit-Remaining = " + response.getFirstHeader("X-RateLimit-Remaining").getValue());
		        	return;
		        }
		        
		        if(response.getStatusLine().getStatusCode() == 200) {
		        	String responseString = new BasicResponseHandler().handleResponse(response);
		        	JsonReader reader = Json.createReader(new StringReader(responseString));
		        	JsonArray responseJSON = reader.readArray();
		        	reader.close();
		        	
		        	for (int i = 0; i < responseJSON.size(); i++) {
		        		JsonObject obj = responseJSON.getJsonObject(i);
		        		
		        		if (maxIssueNum == null) {
		        			maxIssueNum = obj.getInt("number");
		        			pagesNum = (int)(maxIssueNum / 100) + 1;
		        			
		        			Log.info("Pages : " + pagesNum + " | Entries : " + maxIssueNum);
		        		}
		        		
		        		insertIssue(obj);	        		
		        	}
		        	
		        }
		        
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			//next page
			currentPage++;
			
		} while(currentPage <= pagesNum);
	}
	
	/**
	 * Import Issue into Database
	 * 
	 * @param JsonObject Issue
	 */
	private void insertIssue(JsonObject obj) {
		Issue issue = null;

		try {
			QueryBuilder<Issue, Integer> statementBuilder = issueDao.queryBuilder();
			Where<Issue, Integer> where = statementBuilder.where();
			where.eq("issue_id", obj.getInt("id"));
			statementBuilder.setWhere(where);
			issue = issueDao.queryForFirst(statementBuilder.prepare());
		} catch (Exception e) {
			e.printStackTrace();
			issue = null;
		}

		if (issue == null) {
			issue = new Issue();
		}
		
		issue.setRepository(repository);
		issue.setIssueId(Integer.toString(obj.getInt("id")));
		issue.setTitle(obj.getString("title"));
		issue.setBody(obj.getString("body"));
		issue.setUrl(obj.getString("url"));
		issue.setNumber(obj.getInt("number"));
		issue.setCreatedAt(obj.getString("created_at"));
		issue.setUpdatedAt(obj.getString("updated_at"));
		if (obj.isNull("closed_at") == false) {
			issue.setClosedAt(obj.getString("closed_at"));
		}
		issue.setResponseBody(obj.toString());
		
		try {
			issueDao.createOrUpdate(issue);
		} catch (SQLException e) {
			e.printStackTrace();
			issue = null;
		}
	}
	
}
