--
-- Database: `github_android_issues`
--
CREATE DATABASE IF NOT EXISTS `github_android_issues` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `github_android_issues`;

-- --------------------------------------------------------

--
-- Struttura della tabella `issues`
--

CREATE TABLE IF NOT EXISTS `issues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `repository` int(11) DEFAULT NULL,
  `issue_id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `body` text,
  `url` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `closed_at` varchar(255) DEFAULT NULL,
  `response_body` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `repositories`
--

CREATE TABLE IF NOT EXISTS `repositories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
