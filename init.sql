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
    blacklisted BOOLEAN NOT NULL,
    reason_blacklist TEXT NULL
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
    FOREIGN KEY (school_year) REFERENCES pae.school_years(id_year),
    _version INTEGER NOT NULL DEFAULT 0
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
INSERT INTO pae.entreprises VALUES (1,'Assyst Europe', NULL, 'Avenue du Japon, 1/B9 1420 Braine-lAlleud', '02.609.25.00', NULL, false);
INSERT INTO pae.entreprises VALUES (2,'LetsBuild', NULL, 'Chaussée de Bruxelles, 135A 1310 La Hulpe', '014 54 67 54', NULL, false);
INSERT INTO pae.entreprises VALUES (3,'Niboo', NULL, 'Boulevard du Souverain, 24 1170 Watermael-Boisfort', '0487 02 79 13', NULL, false);
INSERT INTO pae.entreprises VALUES (4,'Sopra Steria', NULL, 'Avenue Arnaud Fraiteur, 15/23 1050 Bruxelles', '02 566 66 66', NULL, false);


-- Insert into pae.contacts
INSERT INTO pae.contacts VALUES (4, 'accepte', 11, 2, 1, NULL,'A distance' );
INSERT INTO pae.contacts VALUES (5, 'accepte', 8, 4, 1, NULL,'Dans l entreprise');
INSERT INTO pae.contacts VALUES (6, 'refuse', 8, 3, 1, 'N ont pas accepté d avoir un entretien','A distance' );
INSERT INTO pae.contacts VALUES (7, 'accepte', 9, 1, 1, NULL, 'Dans l entreprise' );
INSERT INTO pae.contacts VALUES (8, 'suspendu', 9, 2, 1, NULL, 'A distance' );
INSERT INTO pae.contacts VALUES (9, 'suspendu', 9, 4, 1, NULL,NULL);
INSERT INTO pae.contacts VALUES (10, 'refuse', 9, 3, 1, 'ne prennent qu un seul étudiant','Dans l entreprise');
INSERT INTO pae.contacts VALUES (11, 'rencontre', 7, 3, 1, NULL,'A distance' );
INSERT INTO pae.contacts VALUES (12, 'initie', 7, 4, 1, NULL,NULL);
INSERT INTO pae.contacts VALUES (13, 'initie', 7, 2, 1, NULL,NULL);
INSERT INTO pae.contacts VALUES (14, 'initie', 10, 4, 1, NULL,NULL);


-- Insert into pae.internship_supervisor
INSERT INTO pae.internship_supervisor VALUES (3, 'Dossche', 'Stéphanie',2, '014.54.67.54', 'stephanie.dossche@letsbuild.com');
INSERT INTO pae.internship_supervisor VALUES (4, 'ALVAREZ CORCHETE', 'Roberto', 4, '02.566.60.14', NULL);
INSERT INTO pae.internship_supervisor VALUES (5, 'Assal', 'Farid', 1, '0474 39 69 09', 'f.assal@assyst-europe.com');



-- Insert into pae.stages
INSERT INTO pae.stages VALUES (4, '10-10-2023', 'Un ERP : Odoo', 3, 11, 1);
INSERT INTO pae.stages VALUES (5, '23-11-2023', 'sBMS project - a complex environment', 4, 8, 1);
INSERT INTO pae.stages VALUES (7, '12-10-2023', 'CRM : Microsoft Dynamics 365 For Sales', 5, 9, 1);
