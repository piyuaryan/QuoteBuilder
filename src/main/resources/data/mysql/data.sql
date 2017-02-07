/*
 * MySQL script.
 * Load the database with reference data and unit test data.
 */

-- SysAdmin --
INSERT IGNORE INTO Account (referenceId, username, password, enabled, credentialsexpired, expired, locked, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('7bd137c8-ab64-4a45-bf2d-d9bae3574622', 'qbOperations', '$2a$10$ix4PIXYZI1LCy7UnerGJgOMhptS.upiSVDkfI/QNOZLSQqJ/Laxs.', TRUE, FALSE, FALSE, FALSE, 0, 'self', NOW(), NULL, NULL);
-- Admin --
INSERT IGNORE INTO Account (referenceId, username, password, enabled, credentialsexpired, expired, locked, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('a07bd221-3ecd-4893-a0f0-78d7c0fbf94e', 'qbAdmin', '$2a$10$8rQGDglElLqAMgxuxmY67OYtcgn6b4vf1gdooItnXk3OqoJQAVMja', TRUE, FALSE, FALSE, FALSE, 0, 'qbOperations', NOW(), NULL, NULL);
-- SalesRep --
INSERT IGNORE INTO Account (referenceId, username, password, enabled, credentialsexpired, expired, locked, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('84832567-2667-42f1-95a8-02e0443a95c6', 'qbSalesRep', '$2a$10$ZCdu3ExY10qw57Amj4f95unZ46o3s1n8uUfCszutEDSJ0iLC0sBgO', TRUE, FALSE, FALSE, FALSE, 0, 'qbAdmin', NOW(), NULL, NULL);
-- User --
INSERT IGNORE INTO Account (referenceId, username, password, enabled, credentialsexpired, expired, locked, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('a37541ea-1959-4558-8d9e-1c16eda9dd53', 'qbUser', '$2a$10$Kkwkg7.EJ.MEx08JOscDGu0QMXmZON2i.9BZH1YQODpceX42nh35a', TRUE, FALSE, FALSE, FALSE, 0, 'qbAdmin', NOW(), NULL, NULL);

INSERT IGNORE INTO Role (id, code, label, ordinal, effectiveAt, expiresAt, createdAt) VALUES (1, 'ROLE_SYSADMIN', 'System Admin', 0, '2015-01-01 00:00:00', NULL, NOW());
INSERT IGNORE INTO Role (id, code, label, ordinal, effectiveAt, expiresAt, createdAt) VALUES (2, 'ROLE_ADMIN', 'Admin', 1, '2015-01-01 00:00:00', NULL, NOW());
INSERT IGNORE INTO Role (id, code, label, ordinal, effectiveAt, expiresAt, createdAt) VALUES (3, 'ROLE_SALES_REP', 'SalesRep', 2, '2015-01-01 00:00:00', NULL, NOW());
INSERT IGNORE INTO Role (id, code, label, ordinal, effectiveAt, expiresAt, createdAt) VALUES (4, 'ROLE_USER', 'User', 3, '2015-01-01 00:00:00', NULL, NOW());

INSERT IGNORE INTO AccountRole (accountId, roleId)
  SELECT
    a.id,
    r.id
  FROM Account a, Role r
  WHERE a.username = 'qbOperations' AND r.id = 1;

INSERT IGNORE INTO AccountRole (accountId, roleId)
  SELECT
    a.id,
    r.id
  FROM Account a, Role r
  WHERE a.username = 'qbAdmin' AND r.id = 2;
INSERT IGNORE INTO AccountRole (accountId, roleId)
  SELECT
    a.id,
    r.id
  FROM Account a, Role r
  WHERE a.username = 'qbSalesRep' AND r.id = 3;
INSERT IGNORE INTO AccountRole (accountId, roleId)
  SELECT
    a.id,
    r.id
  FROM Account a, Role r
  WHERE a.username = 'qbUser' AND r.id = 4;

INSERT IGNORE INTO Profile (referenceId, name, version, createdBy, createdAt, updatedBy, updatedAt) VALUES ('1e0d5287-67fd-4043-9ac4-b8d358d6d7ce', 'SysAdmin Name', 0, 'qbAdmin', NOW(), NULL, NULL);
INSERT IGNORE INTO Profile (referenceId, name, version, createdBy, createdAt, updatedBy, updatedAt) VALUES ('37c3178d-3b49-47b6-99d1-277b1a3e8df8', 'Admin Name', 0, 'qbAdmin', NOW(), NULL, NULL);
INSERT IGNORE INTO Profile (referenceId, name, version, createdBy, createdAt, updatedBy, updatedAt) VALUES ('143bdd7c-5bd6-4119-ace9-8bf55e6ad487', 'SalesRep Name', 0, 'qbAdmin', NOW(), NULL, NULL);
INSERT IGNORE INTO Profile (referenceId, name, version, createdBy, createdAt, updatedBy, updatedAt) VALUES ('87e8d7f0-6967-4e58-8dd8-8580f2994fc9', 'User Name', 0, 'qbAdmin', NOW(), NULL, NULL);

INSERT IGNORE INTO AccountProfile (accountId, profileId)
  SELECT
    a.id,
    p.id
  FROM Account a, Profile p
  WHERE a.username = 'qbOperations' AND p.referenceId = '1e0d5287-67fd-4043-9ac4-b8d358d6d7ce';
INSERT IGNORE INTO AccountProfile (accountId, profileId)
  SELECT
    a.id,
    p.id
  FROM Account a, Profile p
  WHERE a.username = 'qbAdmin' AND p.referenceId = '37c3178d-3b49-47b6-99d1-277b1a3e8df8';
INSERT IGNORE INTO AccountProfile (accountId, profileId)
  SELECT
    a.id,
    p.id
  FROM Account a, Profile p
  WHERE a.username = 'qbSalesRep' AND p.referenceId = '143bdd7c-5bd6-4119-ace9-8bf55e6ad487';
INSERT IGNORE INTO AccountProfile (accountId, profileId)
  SELECT
    a.id,
    p.id
  FROM Account a, Profile p
  WHERE a.username = 'qbUser' AND p.referenceId = '87e8d7f0-6967-4e58-8dd8-8580f2994fc9';