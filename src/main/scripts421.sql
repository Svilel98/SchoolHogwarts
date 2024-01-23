ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20,
    ADD CONSTRAINT age_cons CHECK (age > 16),
ALTER COLUMN name SET not null,
    ADD CONSTRAINT name_cons UNIQUE (name);
ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (name,color);