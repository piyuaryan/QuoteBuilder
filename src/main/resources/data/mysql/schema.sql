/*
 * MySQL script.
 * Create the database schema for the application.
 */

-- DROP TABLE IF EXISTS `Profile`;

CREATE TABLE IF NOT EXISTS `Profile` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `referenceId` varchar(255) NOT NULL,
  `text` varchar(100) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedBy` varchar(100) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  PRIMARY KEY(`id`),
  CONSTRAINT `UQ_Profile_ReferenceId` UNIQUE (`referenceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- DROP TABLE IF EXISTS `AccountRole`;
-- DROP TABLE IF EXISTS `Account`;
-- DROP TABLE IF EXISTS `Role`;

CREATE TABLE IF NOT EXISTS `Account` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `referenceId` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `enabled` bit(1) NOT NULL DEFAULT 1,
  `credentialsexpired` bit(1) NOT NULL DEFAULT 0,
  `expired` bit(1) NOT NULL DEFAULT 0,
  `locked` bit(1) NOT NULL DEFAULT 0,
  `version` int(10) unsigned NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedBy` varchar(100) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `UQ_Account_ReferenceId` UNIQUE (`referenceId`),
  CONSTRAINT `UQ_Account_Username` UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Role` (
  `id` bigint(20) unsigned NOT NULL,
  `code` varchar(50) NOT NULL,
  `label` varchar(100) NOT NULL,
  `ordinal` int(10) unsigned NOT NULL,
  `effectiveAt` datetime NOT NULL,
  `expiresAt` datetime DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `UQ_Role_Code` UNIQUE (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `AccountRole` (
  `accountId` bigint(20) unsigned NOT NULL,
  `roleId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`accountId`, `roleId`),
  CONSTRAINT `FK_AccountRole_AccountId` FOREIGN KEY (`accountId`) REFERENCES `Account` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_AccountRole_RoleId` FOREIGN KEY (`roleId`) REFERENCES `Role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* JdBcTokenStore */
create table IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

create table IF NOT EXISTS oauth_client_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

create table IF NOT EXISTS  oauth_access_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255)
);

create table IF NOT EXISTS oauth_refresh_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);

create table IF NOT EXISTS oauth_code (
  code VARCHAR(255), authentication LONG VARBINARY
);

create table IF NOT EXISTS oauth_approvals (
	userId VARCHAR(255),
  clientId VARCHAR(255),
  scope VARCHAR(255),
  status VARCHAR(10),
  expiresAt TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

-- customized oauth_client_details table
create table IF NOT EXISTS ClientDetails (
  appId VARCHAR(255) PRIMARY KEY,
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255)
);