-- liquibase formatted sql



-- changeset ihlopachev:1
CREATE INDEX student_index ON student (name);
-- changeset ihlopachev:2
CREATE INDEX faculty_index_and_color on faculty (name, color);