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
    _user TEXT NOT NULL ,
    entreprise INTEGER NOT NULL ,
    school_year INTEGER NOT NULL ,
    reason_for_refusal TEXT NULL ,
    meeting_type TEXT NULL ,
    FOREIGN KEY (_user) REFERENCES pae.users(email),
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
INSERT INTO pae.school_years VALUES (1, '2023-2024');
INSERT INTO pae.school_years VALUES (2, '2020-2021');
INSERT INTO pae.school_years VALUES (3, '2024-2025');

INSERT INTO pae.users VALUES (1, 'zaza@student.vinci.be','etudiant','Mia','Lia','04855555','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','27/12/2024', 1, 0);
INSERT INTO pae.users VALUES (2, 'zuzu@student.vinci.be','etudiant','Raf', 'Louisiane','04855544','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','15/02/2024', 1, 0);
INSERT INTO pae.users VALUES (3, 'raphael.baroni@vinci.be','professeur','Baroni','Raphaël','0481 01 01 01','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 0);
INSERT INTO pae.users VALUES (4, 'brigitte.lehmann@vinci.be','professeur','Lehmann', 'Brigitte','0482 02 02 02','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 0);
INSERT INTO pae.users VALUES (5, 'laurent.leleux@vinci.be','professeur','Leleux','Laurent','0483 03 03 03','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 0);
INSERT INTO pae.users VALUES (6, 'annouck.lancaster@vinci.be','administratif','Lancaster', 'Annouck','0484 04 04 04','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 0);
INSERT INTO pae.users VALUES (7, 'Caroline.line@student.vinci.be','etudiant','Line','Caroline','0486 00 00 01','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 0);
INSERT INTO pae.users VALUES (8, 'Ach.ile@student.vinci.be','etudiant','Ile', 'Achille','0487 00 00 01','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 0);
INSERT INTO pae.users VALUES (9, 'Basile.Ile@student.vinci.be','etudiant','Ile','Basile','0488 00 00 01','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 0);
INSERT INTO pae.users VALUES (10, 'Achille.skile@student.vinci.be','etudiant','skile', 'Achille','0490 00 00 01','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 0);
INSERT INTO pae.users VALUES (11, 'Carole.skile@student.vinci.be','etudiant','skile','Carole','0489 00 00 01','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 0);

-- Insert into pae.entreprises
INSERT INTO pae.entreprises VALUES (1, 'Enterprise A', 'Designation A', 'Address A', '04855566', 'enterpriseA@example.com', false);
INSERT INTO pae.entreprises VALUES (2, 'Enterprise B', 'Designation B', 'Address B', '04855577', 'enterpriseB@example.com', false);
INSERT INTO pae.entreprises VALUES (3, 'Assyst Europe', NULL, 'Avenue du Japon, 1/B9', '02.609.25.00', '1420 Braine-l\'Alleud', false);
INSERT INTO pae.entreprises VALUES (4, 'LetsBuild', NULL, 'Chaussée de Bruxelles, 135A', '014 54 67 54', '1310 La Hulpe', false);
INSERT INTO pae.entreprises VALUES (5, 'Niboo', NULL, 'Boulevard du Souverain, 24', '0487 02 79 13', '1170 Watermael-Boisfort', false);
INSERT INTO pae.entreprises VALUES (6, 'Sopra Steria', NULL, 'Avenue Arnaud Fraiteur, 15/23', '02 566 66 66', '1050 Bruxelles', false);


-- Insert into pae.contacts
INSERT INTO pae.contacts VALUES (1, 'initie', 1, 1, 1,'Trop nul', 'Sur place');
INSERT INTO pae.contacts VALUES (2, 'initie', 2, 2, 1,'Pas ouf', 'MS TEAMS' );
INSERT INTO pae.contacts VALUES (3, 'initie', 2, 2, 1, 'Nul','Sur place' );
INSERT INTO pae.contacts VALUES (4, 'accepté', 11, 4, 1, NULL,'A distance' );
INSERT INTO pae.contacts VALUES (5, 'accepté', 8, 6, 1, NULL,'Dans l\'entreprise');
INSERT INTO pae.contacts VALUES (6, 'refusé', 8, 5, 1, 'N\'ont pas accepté d\'avoir un entretien','A distance' );
INSERT INTO pae.contacts VALUES (7, 'accepté', 9, 3, 1, NULL, 'Dans l\'entreprise' );
INSERT INTO pae.contacts VALUES (8, 'suspendu', 9, 4, 1, NULL, 'A distance' );
INSERT INTO pae.contacts VALUES (9, 'suspendu', 9, 6, 1, NULL,NULL);
INSERT INTO pae.contacts VALUES (10, 'refusé', 9, 5, 1, 'ne prennent qu\'un seul étudiant','Dans l\'entreprise');
INSERT INTO pae.contacts VALUES (11, 'pris', 7, 5, 1, NULL,'A distance' );
INSERT INTO pae.contacts VALUES (12, 'initié', 7, 6, 1, NULL,NULL);
INSERT INTO pae.contacts VALUES (13, 'initié', 7, 4, 1, NULL,NULL);
INSERT INTO pae.contacts VALUES (14, 'initié', 10, 6, 1, NULL,NULL);


-- Insert into pae.internship_supervisor
INSERT INTO pae.internship_supervisor VALUES (1, 'Supervisor A', 'First A', 1, '04855588', 'supervisorA@example.com');
INSERT INTO pae.internship_supervisor VALUES (2, 'Supervisor B', 'First B', 2, '04855599', 'supervisorB@example.com');
INSERT INTO pae.internship_supervisor VALUES (3, 'Dossche', 'Stéphanie', (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'), '014.54.67.54', 'stephanie.dossche@letsbuild.com');
INSERT INTO pae.internship_supervisor VALUES (4, 'ALVAREZ CORCHETE', 'Roberto', (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), '02.566.60.14', NULL);
INSERT INTO pae.internship_supervisor VALUES (5, 'Assal', 'Farid', (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'), '0474 39 69 09', 'f.assal@assyst-europe.com');

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

-- Insert into pae.stages
INSERT INTO pae.stages VALUES (1, '2024-03-01', 'Internship Project A', 1, 1, 1);
INSERT INTO pae.stages VALUES (2, '2024-04-01', 'Internship Project B', 2, 2, 1);
INSERT INTO pae.stages VALUES (4, '10/10/2023', NULL, 3, 11, 1);
INSERT INTO pae.stages VALUES (5, '23/11/2023', NULL, 4, 8, 1);
INSERT INTO pae.stages VALUES (6, '12/10/2023', NULL, 5, 8, 1);
