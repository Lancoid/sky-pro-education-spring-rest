 -- liquibase formatted sql

 -- changeset lancoid:1
CREATE INDEX IDX_NAME ON student (name);

 -- changeset lancoid:2
CREATE INDEX IDX_COLOR_NAME ON faculty (color, name);