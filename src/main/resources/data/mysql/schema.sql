/*
 * MySQL script.
 * Create the database schema for the application.
 */
# DROP TABLE IF EXISTS `Profile`;

CREATE TABLE IF NOT EXISTS `Profile` (
  `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `referenceId` VARCHAR(255)        NOT NULL,
  `name`        VARCHAR(100)        NOT NULL,
  `email`       VARCHAR(100),
  `mobile`      VARCHAR(20),
  `version`     INT(10) UNSIGNED    NOT NULL,
  `createdBy`   VARCHAR(100)        NOT NULL,
  `createdAt`   DATETIME            NOT NULL,
  `updatedBy`   VARCHAR(100)                 DEFAULT NULL,
  `updatedAt`   DATETIME                     DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `UQ_Profile_ReferenceId` UNIQUE (`referenceId`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# DROP TABLE IF EXISTS `AccountRole`;
# DROP TABLE IF EXISTS `AccountProfile`;
# DROP TABLE IF EXISTS `Account`;
# DROP TABLE IF EXISTS `Role`;

CREATE TABLE IF NOT EXISTS `Account` (
  `id`                 BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `referenceId`        VARCHAR(255)        NOT NULL,
  `username`           VARCHAR(100)        NOT NULL,
  `password`           VARCHAR(200)        NOT NULL,
  `enabled`            BIT(1)              NOT NULL DEFAULT 1,
  `credentialsexpired` BIT(1)              NOT NULL DEFAULT 0,
  `expired`            BIT(1)              NOT NULL DEFAULT 0,
  `locked`             BIT(1)              NOT NULL DEFAULT 0,
  `version`            INT(10) UNSIGNED    NOT NULL,
  `createdBy`          VARCHAR(100)        NOT NULL,
  `createdAt`          DATETIME            NOT NULL,
  `updatedBy`          VARCHAR(100)                 DEFAULT NULL,
  `updatedAt`          DATETIME                     DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `UQ_Account_ReferenceId` UNIQUE (`referenceId`),
  CONSTRAINT `UQ_Account_Username` UNIQUE (`username`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `Role` (
  `id`          BIGINT(20) UNSIGNED NOT NULL,
  `code`        VARCHAR(50)         NOT NULL,
  `label`       VARCHAR(100)        NOT NULL,
  `ordinal`     INT(10) UNSIGNED    NOT NULL,
  `effectiveAt` DATETIME            NOT NULL,
  `expiresAt`   DATETIME DEFAULT NULL,
  `createdAt`   DATETIME            NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `UQ_Role_Code` UNIQUE (`code`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `AccountRole` (
  `accountId` BIGINT(20) UNSIGNED NOT NULL,
  `roleId`    BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`accountId`, `roleId`),
  CONSTRAINT `FK_AccountRole_AccountId` FOREIGN KEY (`accountId`) REFERENCES `Account` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FK_AccountRole_RoleId` FOREIGN KEY (`roleId`) REFERENCES `Role` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `AccountProfile` (
  `accountId` BIGINT(20) UNSIGNED NOT NULL,
  `profileId` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`accountId`, `profileId`),
  CONSTRAINT `FK_AccountProfile_AccountId` FOREIGN KEY (`accountId`) REFERENCES `Account` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FK_AccountProfile_RoleId` FOREIGN KEY (`profileId`) REFERENCES `Profile` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/* JdBcTokenStore */
CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id               VARCHAR(255) PRIMARY KEY,
  resource_ids            VARCHAR(255),
  client_secret           VARCHAR(255),
  scope                   VARCHAR(255),
  authorized_grant_types  VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities             VARCHAR(255),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id          VARCHAR(255),
  token             LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name         VARCHAR(255),
  client_id         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id          VARCHAR(255),
  token             LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name         VARCHAR(255),
  client_id         VARCHAR(255),
  authentication    LONG VARBINARY,
  refresh_token     VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id       VARCHAR(255),
  token          LONG VARBINARY,
  authentication LONG VARBINARY
);

CREATE TABLE IF NOT EXISTS oauth_code (
  code           VARCHAR(255),
  authentication LONG VARBINARY
);

CREATE TABLE IF NOT EXISTS oauth_approvals (
  userId         VARCHAR(255),
  clientId       VARCHAR(255),
  scope          VARCHAR(255),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

-- customized oauth_client_details table
CREATE TABLE IF NOT EXISTS ClientDetails (
  appId                  VARCHAR(255) PRIMARY KEY,
  resourceIds            VARCHAR(255),
  appSecret              VARCHAR(255),
  scope                  VARCHAR(255),
  grantTypes             VARCHAR(255),
  redirectUrl            VARCHAR(255),
  authorities            VARCHAR(255),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(4096),
  autoApproveScopes      VARCHAR(255)
);