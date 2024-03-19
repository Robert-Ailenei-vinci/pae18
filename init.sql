DROP SCHEMA IF EXISTS pae CASCADE ;
CREATE SCHEMA pae;

CREATE TABLE pae.school_years (
    id_year SERIAL PRIMARY KEY,
    years_format TEXT NOT NULL
);

CREATE TABLE pae.users (
    id_user SERIAL PRIMARY KEY,
    email TEXT NOT NULL ,
    role_u TEXT NOT NULL,
    last_name TEXT NOT NULL ,
    first_name TEXT NOT NULL ,
    phone_number TEXT NOT NULL ,
    psw TEXT NOT NULL ,
    registration_date TEXT NOT NULL,
    school_year INTEGER NOT NULL REFERENCES pae.school_years(id_year),
    _version INTEGER NOT NULL
);
CREATE TABLE pae.entreprises (
    id_entreprise SERIAL PRIMARY KEY,
    trade_name TEXT NOT NULL,
    designation TEXT NULL ,
    address TEXT NOT NULL,
    phone_num TEXT NULL,
    email TEXT NULL ,
    blacklisted BOOLEAN NOT NULL
);

CREATE TABLE pae.contacts (
    id_contact SERIAL PRIMARY KEY,
    state TEXT NOT NULL,
    _user INTEGER NOT NULL ,
    entreprise INTEGER NOT NULL ,
    school_year INTEGER NOT NULL ,
    reason_for_refusal TEXT NULL ,
    meeting_type TEXT NULL ,
    FOREIGN KEY (_user) REFERENCES pae.users(id_user),
    FOREIGN KEY (entreprise) REFERENCES pae.entreprises(id_entreprise),
    FOREIGN KEY (school_year) REFERENCES pae.school_years(id_year)
);

CREATE TABLE pae.internship_supervisor (
    id_supervisor SERIAL PRIMARY KEY,
    last_name TEXT NOT NULL,
    first_name TEXT NOT NULL ,
    entreprise INTEGER NOT NULL ,
    phone_number TEXT NOT NULL,
    email TEXT NULL,
    FOREIGN KEY (entreprise) REFERENCES pae.entreprises(id_entreprise)
);

CREATE TABLE pae.stages (
    contact SERIAL PRIMARY KEY,
    signature_date TEXT NOT NULL ,
    internship_project TEXT NULL ,
    supervisor INTEGER NOT NULL ,
    _user INTEGER NOT NULL ,
    school_year INTEGER NOT NULL ,
    FOREIGN KEY (contact) REFERENCES pae.contacts(id_contact),
    FOREIGN KEY (supervisor) REFERENCES pae.internship_supervisor(id_supervisor),
    FOREIGN KEY (_user) REFERENCES pae.users(id_user),
    FOREIGN KEY (school_year) REFERENCES pae.school_years(id_year)
);
-- Insert into pae.school_years
INSERT INTO pae.school_years VALUES (1, '2024-2025');

INSERT INTO pae.users VALUES (1, 'zaza@student.vinci.be','etudiant','Mia','Lia','04855555','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','27/12/2024', 1, 0);
INSERT INTO pae.users VALUES (2, 'zuzu@student.vinci.be','etudiant','Raf', 'Louisiane','04855544','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','15/02/2024', 1, 0);

-- Insert into pae.entreprises
INSERT INTO pae.entreprises VALUES (1, 'Enterprise A', 'Designation A', 'Address A', '04855566', 'enterpriseA@example.com', false);
INSERT INTO pae.entreprises VALUES (2, 'Enterprise B', 'Designation B', 'Address B', '04855577', 'enterpriseB@example.com', false);

-- Insert into pae.contacts
INSERT INTO pae.contacts VALUES (1, 'initie', 1, 1, 1,'Trop nul', 'Sur place');
INSERT INTO pae.contacts VALUES (2, 'initie', 2, 2, 1,'Pas ouf', 'MS TEAMS' );
INSERT INTO pae.contacts VALUES (3, 'initie', 2, 2, 1, 'Nul','Sur place' );


-- Insert into pae.internship_supervisor
INSERT INTO pae.internship_supervisor VALUES (1, 'Supervisor A', 'First A', 1, '04855588', 'supervisorA@example.com');
INSERT INTO pae.internship_supervisor VALUES (2, 'Supervisor B', 'First B', 2, '04855599', 'supervisorB@example.com');


-- Insert into pae.stages
INSERT INTO pae.stages VALUES (1, '2024-03-01', 'Internship Project A', 1, 1, 1);
INSERT INTO pae.stages VALUES (2, '2024-04-01', 'Internship Project B', 2, 2, 1);