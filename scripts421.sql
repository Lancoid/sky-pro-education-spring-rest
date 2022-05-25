ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age > 15);

ALTER TABLE student ALTER COLUMN name SET NOT NULL;
ALTER TABLE student ADD CONSTRAINT name UNIQUE (name);

ALTER TABLE faculty ADD CONSTRAINT color_name_unique UNIQUE (color, name);

ALTER TABLE student ALTER age_constraint SET DEFAULT 20;