DROP SCHEMA IF EXISTS pae CASCADE ;
CREATE SCHEMA pae;

CREATE TABLE pae.school_years (
    id_year SERIAL PRIMARY KEY,
    years_format VARCHAR(50) NOT NULL
);

CREATE TABLE pae.users (
    id_user SERIAL PRIMARY KEY,
    email TEXT NOT NULL ,
    role_u TEXT NOT NULL,
    last_name TEXT NOT NULL ,
    first_name TEXT NOT NULL ,
    phone_number TEXT NOT NULL ,
    psw TEXT NOT NULL ,
    registration_date DATE NOT NULL,
    school_year INTEGER NOT NULL REFERENCES pae.school_years(id_year)
);

INSERT INTO pae.school_years VALUES (1, '2024-2025');
INSERT INTO pae.users VALUES (1, 'mia.liae@student.vinci.be','etudiant','Mia','Lia','04855555','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','27/12/2024', 1);
INSERT INTO pae.users VALUES (2, 'raf.louisiane@student.vinci.be','etudiant','Raf', 'Louisiane','04855544','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','15/02/2024', 1);