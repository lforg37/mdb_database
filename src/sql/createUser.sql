DROP USER mmdb_frankreich;

CREATE USER mmdb_frankreich IDENTIFIED BY mmdb_frankreich;
GRANT CONNECT TO mmdb_frankreich;
GRANT CREATE TABLE TO mmdb_frankreich;
GRANT UNLIMITED TABLESPACE TO mmdb_frankreich;

GRANT CREATE ANY TABLE TO mmdb_frankreich;
GRANT CREATE ANY INDEX TO mmdb_frankreich;
GRANT CREATE ANY PROCEDURE TO mmdb_frankreich;

GRANT CREATE TYPE TO mmdb_frankreich;
GRANT CREATE INDEXTYPE TO mmdb_frankreich;
GRANT ALL PRIVILEGES TO mmdb_frankreich;

Commit ;
