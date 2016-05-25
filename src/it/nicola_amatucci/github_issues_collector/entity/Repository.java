package it.nicola_amatucci.github_issues_collector.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "repositories")
public class Repository {
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName = "user", canBeNull = false)
	private String user;
	
	@DatabaseField(columnName = "name", canBeNull = false)
	private String name;
	
	@DatabaseField(columnName = "url", canBeNull = false)
	private String url;
	
	public Repository() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIssuesUrl() {
		return getIssuesUrl(1);
	}
	
	public String getIssuesUrl(int page) {
		return "https://api.github.com/repos/"+user+"/"+name+"/issues?state=all&per_page=100&page="+page;
	}
	
	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		
		return url.equals(((Repository) other).url);
	}
}
