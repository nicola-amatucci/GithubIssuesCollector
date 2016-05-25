# GitHub Issues Collector

A simple program to import Issues from a GitHub Repository into a MySQL Database.


##Quick Start

1. Import "/sql/database.sql"


2. Insert the repositories you want to Import into the "repositories" table.

> INSERT INTO repositories VALUES (NULL,  '%USERNAME%',  '%REPOSITORY%',  '%URL%');

Example:

> https://github.com/nicola-amatucci/GithubIssuesCollector

> INSERT INTO repositories VALUES (NULL,  'nicola-amatucci',  'GithubIssuesCollector',  'https://github.com/nicola-amatucci/GithubIssuesCollector');


3. Execute the Program

> java -jar GitHubIssuesCollector.jar


##GitHub API Token

> If you do not set an Access Token the number of API requests is limited (60 requests).

1. Follow this [tutorial](https://help.github.com/articles/creating-an-access-token-for-command-line-use/) to obtain an access token.

2. Set the String parameter "GITHUB_OAUTH_TOKEN" to the obtained token in the class "it.nicola_amatucci.github_issues_collector.config.Configuration".


##Dependencies

* [OrmLite](http://ormlite.com/)
* [jsoup](https://jsoup.org/)
* [Apache HttpComponents](https://hc.apache.org/)
* [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
* [javax.json](https://jsonp.java.net/)