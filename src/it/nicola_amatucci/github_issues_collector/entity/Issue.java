package it.nicola_amatucci.github_issues_collector.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "issues")
public class Issue {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, columnName = "repository")
	private Repository repository;
	
	@DatabaseField(columnName = "issue_id", canBeNull = false)
	private String issueId;
	
	@DatabaseField(columnName = "title", canBeNull = true)
	private String title;
	
	@DatabaseField(columnName = "body", canBeNull = true, dataType=DataType.LONG_STRING)
	private String body;

	@DatabaseField(columnName = "url", canBeNull = true)
	private String url;
	
	@DatabaseField(columnName = "number", canBeNull = true)
	private int number;
	
	@DatabaseField(columnName = "created_at", canBeNull = true)
	private String createdAt;
	
	@DatabaseField(columnName = "updated_at", canBeNull = true)
	private String updatedAt;
	
	@DatabaseField(columnName = "closed_at", canBeNull = true)
	private String closedAt;
	
	@DatabaseField(columnName = "response_body", canBeNull = true, dataType=DataType.LONG_STRING)
	private String responseBody;
	
	public Issue() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(String closedAt) {
		this.closedAt = closedAt;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Issue) {
			return ((Issue)obj).getIssueId().equals(issueId);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return issueId.hashCode();
	}
	
	
}
