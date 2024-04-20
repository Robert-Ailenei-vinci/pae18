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
                              _version INTEGER NOT NULL
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
                            _version INTEGER NOT NULL,
                            FOREIGN KEY (contact) REFERENCES pae.contacts(id_contact),
                            FOREIGN KEY (supervisor) REFERENCES pae.internship_supervisor(id_supervisor),
                            FOREIGN KEY (_user) REFERENCES pae.users(id_user),
                            FOREIGN KEY (school_year) REFERENCES pae.school_years(id_year)

);

-- Insert into pae.school_years
INSERT INTO pae.school_years VALUES (1, '2020-2021');
INSERT INTO pae.school_years VALUES (2, '2021-2022');
INSERT INTO pae.school_years VALUES (3, '2022-2023');
INSERT INTO pae.school_years VALUES (4, '2023-2024');
INSERT INTO pae.school_years VALUES (5, '2024-2025');


--Insert into pae.users
-- Raphaël Baroni
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (1, 'raphael.baroni@vinci.be', 'Professeur', 'Baroni', 'Raphaël', '0481 01 01 01', 'password_hash', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Brigitte Lehmann
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (2,'brigitte.lehmann@vinci.be', 'Professeur', 'Lehmann', 'Brigitte', '0482 02 02 02', 'password_hash', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Laurent Leleux
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (3,'laurent.leleux@vinci.be', 'Professeur', 'Leleux', 'Laurent', '0483 03 03 03', 'password_hash', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Annouck Lancaster
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (4,'annouck.lancaster@vinci.be', 'Administratif', 'Lancaster', 'Annouck', '0484 04 04 04', 'password_hash', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Elle Skile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (5,'elle.skile@student.vinci.be', 'Etudiant', 'Skile', 'Elle', '0491 00 00 01', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Ilotie
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (6,'Basile.Ilotie@student.vinci.be', 'Etudiant', 'Ilotie', 'Basile', '0491 00 00 11', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Frilot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (7,'Basile.frilot@student.vinci.be', 'Etudiant', 'Frilot', 'Basile', '0491 00 00 21', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Ilot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (8,'Basile.Ilot@student.vinci.be', 'Etudiant', 'Ilot', 'Basile', '0492 00 00 01', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Arnaud Dito
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (9,'Arnaud.dito@student.vinci.be', 'Etudiant', 'Dito', 'Arnaud', '0493 00 00 01', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Arnaud Dilo
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (10,'Arnaud.dilo@student.vinci.be', 'Etudiant', 'Dilo', 'Arnaud', '0494 00 00 01', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Cedric Dilot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (11,'Cedric.dilot@student.vinci.be', 'Etudiant', 'Dilot', 'Cedric', '0495 00 00 01', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Auristelle Linot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (12,'Auristelle.linot@student.vinci.be', 'Etudiant', 'Linot', 'Auristelle', '0496 00 00 01', 'password_hash', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Demoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (13,'basile.demoulin@student.vinci.be', 'Etudiant', 'Demoulin', 'Basile', '0496 00 00 02', 'password_hash', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Arthur Moulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (14,'Arthur.moulin@student.vinci.be', 'Etudiant', 'Moulin', 'Arthur', '0497 00 00 02', 'password_hash', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Hugo Moulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (15,'Hugo.moulin@student.vinci.be', 'Etudiant', 'Moulin', 'Hugo', '0497 00 00 03', 'password', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Jeremy Demoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (16,'Jeremy.demoulin@student.vinci.be', 'Etudiant', 'Demoulin', 'Jeremy', '0497 00 00 20', 'password', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Aurèle Mile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (17,'Aurèle.mile@student.vinci.be', 'Etudiant', 'Mile', 'Aurèle', '0497 00 00 21', 'password', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Frank Mile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (18,'Frank.mile@student.vinci.be', 'Etudiant', 'Mile', 'Frank', '0497 00 00 75', 'password', '27/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Basile Dumoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (19,'basile.dumoulin@student.vinci.be', 'Etudiant', 'Dumoulin', 'Basile', '0497 00 00 58', 'password', '27/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Axel Dumoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (20,'Axel.dumoulin@student.vinci.be', 'Etudiant', 'Dumoulin', 'Axel', '0497 00 00 97', 'password', '27/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Caroline Line
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (21,'Caroline.line@student.vinci.be', 'Etudiant', 'Line', 'Caroline', '0486 00 00 01', 'password', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Achille Ile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (22,'Ach.ile@student.vinci.be', 'Etudiant', 'Ile', 'Achille', '0487 00 00 01', 'password', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Basile Ile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (23,'Basile.Ile@student.vinci.be', 'Etudiant', 'Ile', 'Basile', '0488 00 00 01', 'password', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Achille Skile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (24,'Achille.skile@student.vinci.be', 'Etudiant', 'Skile', 'Achille', '0490 00 00 01', 'password', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Carole Skile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (25,'Carole.skile@student.vinci.be', 'Etudiant', 'Skile', 'Carole', '0489 00 00 01', 'password', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Théophile Ile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (26,'theophile.ile@student.vinci.be', 'Etudiant', 'Ile', 'Théophile', '0488 35 33 89', 'password', '01/03/2024', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);


--Insert into pae.entreprises
-- Assyst Europe
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (1,'Assyst Europe', 'Avenue du Japon, 1/B9, 1420 Braine-l''Alleud', '02.609.25.00', false);
-- AXIS SRL
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (2,'AXIS SRL', 'Avenue de l''Hélianthe, 63, 1180 Uccle', '02 752 17 60', false);
-- Infrabel
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (3,'Infrabel', 'Rue Bara, 135, 1070 Bruxelles', '02 525 22 11', false);
-- La route du papier
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (4,'La route du papier', 'Avenue des Mimosas, 83, 1150 Woluwe-Saint-Pierre', '02 586 16 65', false);
-- LetsBuild
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (5,'LetsBuild', 'Chaussée de Bruxelles, 135A, 1310 La Hulpe', '014 54 67 54', false);
-- Niboo
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (6,'Niboo', 'Boulevard du Souverain, 24, 1170 Watermael-Boisfort', '0487 02 79 13', false);
-- Sopra Steria
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (7,'Sopra Steria', 'Avenue Arnaud Fraiteur, 15/23, 1050 Bruxelles', '02 566 66 66', false);
-- The Bayard Partnership
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted)
VALUES (8,'The Bayard Partnership', 'Grauwmeer, 1/57 bte 55, 3001 Leuven', '02 309 52 45', false);


-- Insert into pae.internship_supervisor
-- Stéphanie Dossche - LetsBuild
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (1, 'Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'));
-- Roberto Alvarez Corchete - Sopra Steria
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (2, 'ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', NULL, (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'));
-- Farid Assal - Assyst Europe
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (3, 'Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'));
-- Emile Ile - La route du papier
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (4, 'Ile', 'Emile', '0489 32 16 54', NULL, (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'La route du papier'));
-- Owln Hibo - Infrabel
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (5, 'Hibo', 'Owln', '0456 678 567', NULL, (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Infrabel'));
-- Henri Barn - AXIS SRL
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (6, 'Barn', 'Henri', '02 752 17 60', NULL, (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'));






-- Insert into pae.contacts 1
-- Caroline skile - LetsBuild
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (1,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Carole.skile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact
         FROM pae.contacts
         WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Carole.skile@student.vinci.be')
           AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild')),
        '10/10/2023',
        'Un ERP : Odoo',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Dossche'),
        (SELECT id_user FROM pae.users WHERE email = 'Carole.skile@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        1);


-- Achille Ile - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (2,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Ach.ile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Ach.ile@student.vinci.be') AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria')),
        '23/11/2023',
        'sBMS project - a complex environment',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'ALVAREZ CORCHETE'),
        (SELECT id_user FROM pae.users WHERE email = 'Ach.ile@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        1);

-- Achille Ile - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, reason_for_refusal, _version)
VALUES (3,'refusé',
        (SELECT id_user FROM pae.users WHERE email = 'Ach.ile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'A distance',
        'N''ont pas accepté d''avoir un entretien',
        1);
-- Basile Ile - Assyst Europe
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type,reason_for_refusal, _version)
VALUES (4,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Basile.Ile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'Dans l''entreprise',
        NULL,
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts as c WHERE c._user = (SELECT id_user FROM pae.users WHERE email = 'Basile.Ile@student.vinci.be') AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe')),
        '12/10/2023',
        'CRM : Microsoft Dynamics 365 For Sales',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Assal'),
        (SELECT id_user FROM pae.users WHERE email = 'Basile.Ile@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        1);

-- Basile Ile - LetsBuild
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (5,'suspendu', (SELECT id_user FROM pae.users WHERE email = 'Basile.Ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 'A distance', 1);

-- Basile Ile - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (6,'suspendu', (SELECT id_user FROM pae.users WHERE email = 'Basile.Ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 1);

-- Basile Ile - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year,reason_for_refusal, meeting_type, _version)
VALUES (7,'refusé', (SELECT id_user FROM pae.users WHERE email = 'Basile.Ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 'ne prennent qu''un seul étudiant', 'Dans l''entreprise', 1);

-- Caroline Line - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (8,'refusé', (SELECT id_user FROM pae.users WHERE email = 'Caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 'Pas d’affinité avec le l’ERP Odoo', 'A distance', 1);

-- Caroline Line - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (9,'non suivi', (SELECT id_user FROM pae.users WHERE email = 'Caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 1);

-- Caroline Line - LetsBuild
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (10,'pris', (SELECT id_user FROM pae.users WHERE email = 'Caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 'A distance', 1);

-- Théophile Ile - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (11,'initié', (SELECT id_user FROM pae.users WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 1);

-- Theophile Ile - Niboo (initiated)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, _version)
VALUES (12,'initié', (SELECT id_user FROM pae.users WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);

-- Theophile Ile - LetsBuild (initiated)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, _version)
VALUES (13,'initié', (SELECT id_user FROM pae.users WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);

-- Achille Skile - Sopra Steria (initiated)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, _version)
VALUES (14,'initié', (SELECT id_user FROM pae.users WHERE email = 'Achille.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);







-- Insert into pae.contacts 2
-- elle.skile@student.vinci.be - La route du papier (accepted)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (15,'accepté', (SELECT id_user FROM pae.users WHERE email = 'elle.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'La route du papier'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'A distance', 1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'elle.skile@student.vinci.be')), '25/11/2021', 'Conservation et restauration d’œuvres d’art', (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Ile'), (SELECT id_user FROM pae.users WHERE email = 'elle.skile@student.vinci.be'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);

-- Basile Ilot - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (16,'non suivi', (SELECT id_user FROM pae.users WHERE email = 'Basile.Ilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), NULL, 1);
-- Basile Frilot - The Bayard Partnership
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (17,'refusé', (SELECT id_user FROM pae.users WHERE email = 'Basile.frilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'The Bayard Partnership'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'ne prennent pas de stage', 'A distance', 1);

-- Arnaud Dito - Sopra Steria (accepted)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, reason_for_refusal, _version)
VALUES (18,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Arnaud.dito@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        'Dans l''entreprise',
        NULL,
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project,supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Arnaud.dito@student.vinci.be')),
        '17/11/2021',
        'L''analyste au centre du développement',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'ALVAREZ CORCHETE'),
        (SELECT id_user FROM pae.users WHERE email = 'Arnaud.dito@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        1);
-- Arnaud Dito - L'analyste au centre du développement (Stage)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, reason_for_refusal, _version)
VALUES (19,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Arnaud.dito@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        'Dans l''entreprise',
        NULL,
        1);
SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Arnaud.dito@student.vinci.be') AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria');

INSERT INTO pae.stages (contact, signature_date, internship_project,supervisor, _user, school_year, _version)
VALUES (19,
        '17/11/2021',
        'L''analyste au centre du développement',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'ALVAREZ CORCHETE'),
        (SELECT id_user FROM pae.users WHERE email = 'Arnaud.dito@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        1);

-- Cedric Dilot - Assyst Europe (accepted)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (20,'accepté', (SELECT id_user FROM pae.users WHERE email = 'Cedric.dilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'Dans l''entreprise', 1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Cedric.dilot@student.vinci.be')), '23/11/2021', 'ERP : Microsoft Dynamics 366', (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'ALVAREZ CORCHETE'), (SELECT id_user FROM pae.users WHERE email = 'Cedric.dilot@student.vinci.be'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);

-- Cedric Dilot - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (21,'refusé', (SELECT id_user FROM pae.users WHERE email = 'Cedric.dilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'Choix autre étudiant', 'Dans l''entreprise', 1);

-- Auristelle Linot - Infrabel (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (22,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Auristelle.linot@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Infrabel'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Auristelle.linot@student.vinci.be')),
        '22/11/2021',
        'Entretien des rails',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'ALVAREZ CORCHETE'),
        (SELECT id_user FROM pae.users WHERE email = 'Auristelle.linot@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        1);

-- Auristelle Linot - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (23,'suspendu', (SELECT id_user FROM pae.users WHERE email = 'Auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), NULL, 1);
-- Auristelle Linot - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (24,'refusé', (SELECT id_user FROM pae.users WHERE email = 'Auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'Choix autre étudiant', 'A distance', 1);

















-- Insert into pae.contacts 3
-- Jeremy Demoulin - Assyst Europe (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (25,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Jeremy.demoulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, _user, school_year, supervisor,  _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Jeremy.demoulin@student.vinci.be')),
        '23/11/2022',
        'CRM : Microsoft Dynamics 365 For Sales',
        (SELECT id_user FROM pae.users WHERE email = 'Jeremy.demoulin@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Assal'),
        1);

-- Arthur Moulin - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (26,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Arthur.moulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Arthur.moulin@student.vinci.be')),
        '19/10/2022',
        'Un métier : chef de projet',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Assal'),
        (SELECT id_user FROM pae.users WHERE email = 'Arthur.moulin@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        1);


-- Hugo Moulin - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (27,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Hugo.moulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Hugo.moulin@student.vinci.be')),
        '19/10/2022',
        'Un métier : chef de projet',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Barn'),
        (SELECT id_user FROM pae.users WHERE email = 'Hugo.moulin@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        1);

-- Aurèle Mile - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (28,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Aurèle.mile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Aurèle.mile@student.vinci.be')),
        '19/10/2022',
        'Un métier : chef de projet',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Barn'),
        (SELECT id_user FROM pae.users WHERE email = 'Aurèle.mile@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        1);


-- Frank Mile - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (29,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Frank.mile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Frank.mile@student.vinci.be')),
        '19/10/2022',
        'Un métier : chef de projet',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Barn'),
        (SELECT id_user FROM pae.users WHERE email = 'Frank.mile@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        1);


-- Basile Dumoulin - AXIS SRL (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (30,'refusé', (SELECT id_user FROM pae.users WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Entretien n''a pas eu lieu', 'Dans l''entreprise', 1);

-- Basile Dumoulin - Niboo (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (31,'refusé', (SELECT id_user FROM pae.users WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Entretien n''a pas eu lieu', 'Dans l''entreprise', 1);

-- Basile Dumoulin - Sopra Steria (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (32,'refusé', (SELECT id_user FROM pae.users WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Entretien n''a pas eu lieu', 'A distance', 1);

-- Axel Dumoulin - Sopra Steria (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (33,'accepté',
        (SELECT id_user FROM pae.users WHERE email = 'Axel.dumoulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'Axel.dumoulin@student.vinci.be')),
        '17/10/2022',
        'sBMS project - Java Development',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'ALVAREZ CORCHETE'),
        (SELECT id_user FROM pae.users WHERE email = 'Axel.dumoulin@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        1);

-- Basile Frilot - Sopra Steria (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (34,'refusé', (SELECT id_user FROM pae.users WHERE email = 'Basile.frilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Choix autre étudiant', 'A distance', 1);

-----• Comptage du nombre d’utilisateurs.
SELECT COUNT(*) AS user_count FROM pae.users;

-----• Comptage du nombre d’entreprises.
SELECT COUNT(*) AS entreprise_count FROM pae.entreprises;

------• Comptage du nombre de stages par année académique.

SELECT
    s.years_format,
    COUNT(*) AS stage_count
FROM
    pae.stages st
        JOIN
    pae.school_years s ON st.school_year = s.id_year
GROUP BY
    s.years_format;

------• Comptage du nombre de contacts par année académique.

SELECT
    s.years_format AS academic_year,
    COUNT(*) AS contact_count
FROM
    pae.contacts c
        JOIN
    pae.school_years s ON c.school_year = s.id_year
GROUP BY
    s.years_format;
-----• Etats (en format lisible par le client) et comptage du nombre de contacts dans chacun des etat

SELECT
    state,
    COUNT(*) AS state_count
FROM
    pae.contacts
GROUP BY
    state;



