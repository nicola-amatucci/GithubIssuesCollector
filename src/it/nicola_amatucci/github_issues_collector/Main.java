package it.nicola_amatucci.github_issues_collector;

/**
 * Main Method
 * 
 * @author Nicola
 *
 */
public class Main {

	public static void main(String[] args) {
	
		GithubIssueCollector collector = new GithubIssueCollector();
		collector.collect();
		collector.tearDown();

	}

}
