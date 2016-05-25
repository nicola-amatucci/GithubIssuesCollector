package it.nicola_amatucci.github_issues_collector;

import java.util.List;
import java.util.logging.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

import it.nicola_amatucci.github_issues_collector.config.Configuration;
import it.nicola_amatucci.github_issues_collector.engine.GithubIssuesCollectorEngine;
import it.nicola_amatucci.github_issues_collector.entity.Issue;
import it.nicola_amatucci.github_issues_collector.entity.Repository;

/**
 * GithubIssueCollector
 * 
 * @author Nicola
 *
 */
public class GithubIssueCollector {
	
	private final static Logger Log = Logger.getLogger(GithubIssueCollector.class.getName());

	JdbcConnectionSource connectionSource = null;
	private Dao<Repository, Integer> repositoryDao;
	private Dao<Issue, Integer> issueDao;
	
	/**
	 * Constructor
	 */
	public GithubIssueCollector() {
		super();
		this.setUp();
	}
	
	/**
	 * Set Up ORM
	 * 
	 * @return 
	 */
	private boolean setUp(){
		try {
			connectionSource = new JdbcConnectionSource(Configuration.DATABASE_URL,Configuration.DATABASE_USER, Configuration.DATABASE_PASS);
			
			repositoryDao = DaoManager.createDao(connectionSource, Repository.class);
			issueDao = DaoManager.createDao(connectionSource, Issue.class);
			
			//create the table
			TableUtils.createTableIfNotExists(connectionSource, Repository.class);
			TableUtils.createTableIfNotExists(connectionSource, Issue.class);			
			
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Tear Down ORM
	 * 
	 * @return
	 */
	public boolean tearDown() {
		if (connectionSource != null) {
			try {
				connectionSource.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Collect the Issues
	 */
	public void collect() {
		if (connectionSource != null && repositoryDao != null && issueDao != null) {
			try {
				QueryBuilder<Repository, Integer> statementBuilder = repositoryDao.queryBuilder();
				List<Repository> repositories = repositoryDao.query(statementBuilder.prepare());
				for (Repository r : repositories) {
					Log.info("Parsing : " + r.getName());
					new GithubIssuesCollectorEngine(r,issueDao).readIssues();
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
