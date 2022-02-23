DROP DATABASE irrigationmanagement;
DROP ROLE irrigationmanagement;

CREATE ROLE irrigationmanagement LOGIN PASSWORD 'irrigationmanagement' SUPERUSER CREATEDB VALID UNTIL 'infinity';
CREATE DATABASE irrigationmanagement WITH ENCODING='UTF8' OWNER = irrigationmanagement;