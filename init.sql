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
                                 reason_blacklist TEXT NULL,
                                 _version INTEGER NOT NULL
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


--Insert into pae.users
-- Raphaël Baroni
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (1, 'raphael.baroni@vinci.be', 'professeur', 'Baroni', 'Raphaël', '0481 01 01 01', '$2a$10$OVmywGMW3uRMQF.7UpcCpeepTx5kbPFcIZzhjCNNVSIriFDHw2fCS', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Brigitte Lehmann
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (2,'brigitte.lehmann@vinci.be', 'professeur', 'Lehmann', 'Brigitte', '0482 02 02 02', '$2a$10$OVmywGMW3uRMQF.7UpcCpeepTx5kbPFcIZzhjCNNVSIriFDHw2fCS', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Laurent Leleux
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (3,'laurent.leleux@vinci.be', 'professeur', 'Leleux', 'Laurent', '0483 03 03 03', '$2a$10$OVmywGMW3uRMQF.7UpcCpeepTx5kbPFcIZzhjCNNVSIriFDHw2fCS', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Annouck Lancaster
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (4,'annouck.lancaster@vinci.be', 'administratif', 'Lancaster', 'Annouck', '0484 04 04 04', '$2a$10$a9lno6UnbIwID2/S5RysRe0xdWAfe2rEFGrBtuSgEtRDtOxnpqMjC', '21/09/2020', (SELECT id_year FROM pae.school_years WHERE years_format = '2020-2021'), 1);
-- Elle Skile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (5,'elle.skile@student.vinci.be', 'etudiant', 'Skile', 'Elle', '0491 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Ilotie
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (6,'basile.ilotie@student.vinci.be', 'etudiant', 'Ilotie', 'Basile', '0491 00 00 11', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Frilot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (7,'basile.frilot@student.vinci.be', 'etudiant', 'Frilot', 'Basile', '0491 00 00 21', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Ilot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (8,'basile.ilot@student.vinci.be', 'etudiant', 'Ilot', 'Basile', '0492 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Arnaud Dito
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (9,'arnaud.dito@student.vinci.be', 'etudiant', 'Dito', 'Arnaud', '0493 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Arnaud Dilo
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (10,'arnaud.dilo@student.vinci.be', 'etudiant', 'Dilo', 'Arnaud', '0494 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Cedric Dilot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (11,'cedric.dilot@student.vinci.be', 'etudiant', 'Dilot', 'Cedric', '0495 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Auristelle Linot
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (12,'auristelle.linot@student.vinci.be', 'etudiant', 'Linot', 'Auristelle', '0496 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '21/09/2021', (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);
-- Basile Demoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (13,'basile.demoulin@student.vinci.be', 'etudiant', 'Demoulin', 'Basile', '0496 00 00 02', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Arthur Moulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (14,'arthur.moulin@student.vinci.be', 'etudiant', 'Moulin', 'Arthur', '0497 00 00 02', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Hugo Moulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (15,'hugo.moulin@student.vinci.be', 'etudiant', 'Moulin', 'Hugo', '0497 00 00 03', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Jeremy Demoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (16,'jeremy.demoulin@student.vinci.be', 'etudiant', 'Demoulin', 'Jeremy', '0497 00 00 20', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Aurèle Mile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (17,'aurele.mile@student.vinci.be', 'etudiant', 'Mile', 'Aurèle', '0497 00 00 21', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '23/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Frank Mile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (18,'frank.mile@student.vinci.be', 'etudiant', 'Mile', 'Frank', '0497 00 00 75', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '27/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Basile Dumoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (19,'basile.dumoulin@student.vinci.be', 'etudiant', 'Dumoulin', 'Basile', '0497 00 00 58', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '27/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Axel Dumoulin
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (20,'axel.dumoulin@student.vinci.be', 'etudiant', 'Dumoulin', 'Axel', '0497 00 00 97', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '27/09/2022', (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 1);
-- Caroline Line
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (21,'caroline.line@student.vinci.be', 'etudiant', 'Line', 'Caroline', '0486 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Achille Ile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (22,'ach.ile@student.vinci.be', 'etudiant', 'Ile', 'Achille', '0487 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Basile Ile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (23,'basile.ile@student.vinci.be', 'etudiant', 'Ile', 'Basile', '0488 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Achille Skile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (24,'achille.skile@student.vinci.be', 'etudiant', 'Skile', 'Achille', '0490 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Carole Skile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (25,'carole.skile@student.vinci.be', 'etudiant', 'Skile', 'Carole', '0489 00 00 01', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '18/09/2023', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);
-- Théophile Ile
INSERT INTO pae.users (id_user, email, role_u, last_name, first_name, phone_number, psw, registration_date, school_year, _version)
VALUES (26,'theophile.ile@student.vinci.be', 'etudiant', 'Ile', 'Théophile', '0488 35 33 89', '$2a$10$xmQOV5fGTVRvZJMulzj7X.9eybyFXS7Lf1NmjJC0mmZyQcheGRqFO', '01/03/2024', (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);


--Insert into pae.entreprises
-- Assyst Europe
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (1,'Assyst Europe', 'Avenue du Japon, 1/B9, 1420 Braine-l''Alleud', '02.609.25.00', false, 1);
-- AXIS SRL
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (2,'AXIS SRL', 'Avenue de l''Hélianthe, 63, 1180 Uccle', '02 752 17 60', false, 1);
-- Infrabel
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (3,'Infrabel', 'Rue Bara, 135, 1070 Bruxelles', '02 525 22 11', false, 1);
-- La route du papier
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (4,'La route du papier', 'Avenue des Mimosas, 83, 1150 Woluwe-Saint-Pierre', '02 586 16 65', false, 1);
-- LetsBuild
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (5,'LetsBuild', 'Chaussée de Bruxelles, 135A, 1310 La Hulpe', '014 54 67 54', false, 1);
-- Niboo
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (6,'Niboo', 'Boulevard du Souverain, 24, 1170 Watermael-Boisfort', '0487 02 79 13', false, 1);
-- Sopra Steria
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (7,'Sopra Steria', 'Avenue Arnaud Fraiteur, 15/23, 1050 Bruxelles', '02 566 66 66', false, 1);
-- The Bayard Partnership
INSERT INTO pae.entreprises (id_entreprise, trade_name, address, phone_num, blacklisted, _version)
VALUES (8,'The Bayard Partnership', 'Grauwmeer, 1/57 bte 55, 3001 Leuven', '02 309 52 45', false, 1);


-- Insert into pae.internship_supervisor
-- Stéphanie Dossche - LetsBuild
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (1, 'Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'));
-- Roberto Alvarez Corchete - Sopra Steria
INSERT INTO pae.internship_supervisor (id_supervisor, last_name, first_name, phone_number, email, entreprise)
VALUES (2, 'Alvarez Corchete', 'Roberto', '02.566.60.14', NULL, (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'));
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
VALUES (1,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'carole.skile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact
         FROM pae.contacts
         WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'carole.skile@student.vinci.be')
    AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild')),
    '10/10/2023',
    'Un ERP : Odoo',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Dossche'),
       (SELECT id_user FROM pae.users WHERE email = 'carole.skile@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
    1);


-- Achille Ile - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (2,'accepte\e',
        (SELECT id_user FROM pae.users WHERE email = 'ach.ile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'ach.ile@student.vinci.be') AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria')),
    '23/11/2023',
    'sBMS project - a complex environment',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Alvarez Corchete'),
       (SELECT id_user FROM pae.users WHERE email = 'ach.ile@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
    1);

-- Achille Ile - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, reason_for_refusal, _version)
VALUES (3,'refuse',
        (SELECT id_user FROM pae.users WHERE email = 'ach.ile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'A distance',
        'N''ont pas accepté d''avoir un entretien',
        1);
-- Basile Ile - Assyst Europe
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type,reason_for_refusal, _version)
VALUES (4,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'basile.ile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
        'Dans l''entreprise',
        NULL,
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts as c WHERE c._user = (SELECT id_user FROM pae.users WHERE email = 'basile.ile@student.vinci.be') AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe')),
    '12/10/2023',
    'CRM : Microsoft Dynamics 365 For Sales',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Assal'),
       (SELECT id_user FROM pae.users WHERE email = 'basile.ile@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'),
    1);

-- Basile Ile - LetsBuild
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (5,'suspendu', (SELECT id_user FROM pae.users WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 'A distance', 1);

-- Basile Ile - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (6,'suspendu', (SELECT id_user FROM pae.users WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 1);

-- Basile Ile - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year,reason_for_refusal, meeting_type, _version)
VALUES (7,'refuse', (SELECT id_user FROM pae.users WHERE email = 'basile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 'ne prennent qu''un seul étudiant', 'Dans l''entreprise', 1);

-- Caroline Line - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (8,'refuse', (SELECT id_user FROM pae.users WHERE email = 'caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 'Pas d’affinité avec le l’ERP Odoo', 'A distance', 1);

-- Caroline Line - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (9,'non suivi', (SELECT id_user FROM pae.users WHERE email = 'caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 1);

-- Caroline Line - LetsBuild
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (10,'pris', (SELECT id_user FROM pae.users WHERE email = 'caroline.line@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 'A distance', 1);

-- Théophile Ile - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (11,'initie', (SELECT id_user FROM pae.users WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), NULL, 1);

-- Theophile Ile - Niboo (initiated)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, _version)
VALUES (12,'initie', (SELECT id_user FROM pae.users WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);

-- Theophile Ile - LetsBuild (initiated)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, _version)
VALUES (13,'initie', (SELECT id_user FROM pae.users WHERE email = 'theophile.ile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'LetsBuild'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);

-- Achille Skile - Sopra Steria (initiated)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, _version)
VALUES (14,'initie', (SELECT id_user FROM pae.users WHERE email = 'achille.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2023-2024'), 1);







-- Insert into pae.contacts 2
-- elle.skile@student.vinci.be - La route du papier (accepted)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (15,'accepte', (SELECT id_user FROM pae.users WHERE email = 'elle.skile@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'La route du papier'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'A distance', 1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'elle.skile@student.vinci.be')), '25/11/2021', 'Conservation et restauration d’œuvres d’art', (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Ile'), (SELECT id_user FROM pae.users WHERE email = 'elle.skile@student.vinci.be'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);

-- Basile Ilot - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (16,'non suivi', (SELECT id_user FROM pae.users WHERE email = 'basile.ilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), NULL, 1);
-- Basile Frilot - The Bayard Partnership
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (17,'refuse', (SELECT id_user FROM pae.users WHERE email = 'basile.frilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'The Bayard Partnership'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'ne prennent pas de stage', 'A distance', 1);

-- Arnaud Dito - Sopra Steria (accepted)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, reason_for_refusal, _version)
VALUES (18,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'arnaud.dito@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        'Dans l''entreprise',
        NULL,
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project,supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'arnaud.dito@student.vinci.be')),
    '17/11/2021',
    'L''analyste au centre du développement',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Alvarez Corchete'),
       (SELECT id_user FROM pae.users WHERE email = 'arnaud.dito@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
    1);
-- Arnaud Dilo - L'analyste au centre du développement (Stage)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, reason_for_refusal, _version)
VALUES (19,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'arnaud.dilo@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        'Dans l''entreprise',
        NULL,
        1);
SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'arnaud.dilo@student.vinci.be') AND entreprise = (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria');

INSERT INTO pae.stages (contact, signature_date, internship_project,supervisor, _user, school_year, _version)
VALUES (19,
        '17/11/2021',
        'L''analyste au centre du développement',
        (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Alvarez Corchete'),
        (SELECT id_user FROM pae.users WHERE email = 'arnaud.dilo@student.vinci.be'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        1);

-- Cedric Dilot - Assyst Europe (accepted)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (20,'accepte', (SELECT id_user FROM pae.users WHERE email = 'cedric.dilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'Dans l''entreprise', 1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'cedric.dilot@student.vinci.be')), '23/11/2021', 'ERP : Microsoft Dynamics 366', (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Alvarez Corchete'), (SELECT id_user FROM pae.users WHERE email = 'cedric.dilot@student.vinci.be'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 1);

-- Cedric Dilot - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (21,'refuse', (SELECT id_user FROM pae.users WHERE email = 'cedric.dilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'Choix autre étudiant', 'Dans l''entreprise', 1);

-- Auristelle Linot - Infrabel (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (22,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'auristelle.linot@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Infrabel'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'auristelle.linot@student.vinci.be')),
    '22/11/2021',
    'Entretien des rails',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Alvarez Corchete'),
       (SELECT id_user FROM pae.users WHERE email = 'auristelle.linot@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'),
    1);

-- Auristelle Linot - Sopra Steria
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (23,'suspendu', (SELECT id_user FROM pae.users WHERE email = 'auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), NULL, 1);
-- Auristelle Linot - Niboo
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (24,'refuse', (SELECT id_user FROM pae.users WHERE email = 'auristelle.linot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2021-2022'), 'Choix autre étudiant', 'A distance', 1);

















-- Insert into pae.contacts 3
-- Jeremy Demoulin - Assyst Europe (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (25,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'jeremy.demoulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Assyst Europe'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, _user, school_year, supervisor,  _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'jeremy.demoulin@student.vinci.be')),
    '23/11/2022',
    'CRM : Microsoft Dynamics 365 For Sales',
       (SELECT id_user FROM pae.users WHERE email = 'jeremy.demoulin@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Assal'),
    1);

-- Arthur Moulin - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (26,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'arthur.moulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'arthur.moulin@student.vinci.be')),
    '19/10/2022',
    'Un métier : chef de projet',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Assal'),
       (SELECT id_user FROM pae.users WHERE email = 'arthur.moulin@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
    1);


-- Hugo Moulin - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (27,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'hugo.moulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'hugo.moulin@student.vinci.be')),
    '19/10/2022',
    'Un métier : chef de projet',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Barn'),
       (SELECT id_user FROM pae.users WHERE email = 'hugo.moulin@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
    1);

-- Aurèle Mile - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (28,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'aurele.mile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'aurele.mile@student.vinci.be')),
    '19/10/2022',
    'Un métier : chef de projet',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Barn'),
       (SELECT id_user FROM pae.users WHERE email = 'aurele.mile@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
    1);


-- Frank Mile - AXIS SRL (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (29,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'frank.mile@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'Dans l''entreprise',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'frank.mile@student.vinci.be')),
    '19/10/2022',
    'Un métier : chef de projet',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Barn'),
       (SELECT id_user FROM pae.users WHERE email = 'frank.mile@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
    1);


-- Basile Dumoulin - AXIS SRL (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (30,'refuse', (SELECT id_user FROM pae.users WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'AXIS SRL'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Entretien n''a pas eu lieu', 'Dans l''entreprise', 1);

-- Basile Dumoulin - Niboo (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (31,'refuse', (SELECT id_user FROM pae.users WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Niboo'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Entretien n''a pas eu lieu', 'Dans l''entreprise', 1);

-- Basile Dumoulin - Sopra Steria (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (32,'refuse', (SELECT id_user FROM pae.users WHERE email = 'basile.dumoulin@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Entretien n''a pas eu lieu', 'A distance', 1);

-- Axel Dumoulin - Sopra Steria (contact)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, meeting_type, _version)
VALUES (33,'accepte',
        (SELECT id_user FROM pae.users WHERE email = 'axel.dumoulin@student.vinci.be'),
        (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'),
        (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
        'A distance',
        1);
INSERT INTO pae.stages (contact, signature_date, internship_project, supervisor, _user, school_year, _version)
VALUES ((SELECT id_contact FROM pae.contacts WHERE _user = (SELECT id_user FROM pae.users WHERE email = 'axel.dumoulin@student.vinci.be')),
    '17/10/2022',
    'sBMS project - Java Development',
       (SELECT id_supervisor FROM pae.internship_supervisor AS s WHERE s.last_name = 'Alvarez Corchete'),
       (SELECT id_user FROM pae.users WHERE email = 'axel.dumoulin@student.vinci.be'),
       (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'),
    1);

-- Basile Frilot - Sopra Steria (refused)
INSERT INTO pae.contacts (id_contact, state, _user, entreprise, school_year, reason_for_refusal, meeting_type, _version)
VALUES (34,'refuse', (SELECT id_user FROM pae.users WHERE email = 'basile.frilot@student.vinci.be'), (SELECT id_entreprise FROM pae.entreprises WHERE trade_name = 'Sopra Steria'), (SELECT id_year FROM pae.school_years WHERE years_format = '2022-2023'), 'Choix autre étudiant', 'A distance', 1);

-- 1. Comptage du nombre d’utilisateurs, par rôle et par année académique.
SELECT
    s.years_format AS "Année académique",
    u.role_u AS "Rôle",
    COUNT(u.id_user) AS "Nombre d'utilisateurs"
FROM
    pae.users u
        JOIN pae.school_years s ON u.school_year = s.id_year
GROUP BY
    s.years_format, u.role_u
ORDER BY
    s.years_format, u.role_u;

-- 2. Année académique et comptage du nombre de stages par année académique.
SELECT
    s.years_format AS "Année académique",
    COUNT(st.contact) AS "Nombre de stages"
FROM
    pae.stages st
        JOIN pae.school_years s ON st.school_year = s.id_year
GROUP BY
    s.years_format
ORDER BY
    s.years_format;

-- 3. Entreprise, année académique, et comptage du nombre de stages par entreprise et année académique.
SELECT
    e.trade_name AS "Entreprise",
    sy.years_format AS "Année académique",
    COUNT(st.contact) AS "Nombre de stages"
FROM
    pae.stages st
        JOIN pae.contacts ct ON st.contact = ct.id_contact
        JOIN pae.entreprises e ON ct.entreprise = e.id_entreprise
        JOIN pae.school_years sy ON st.school_year = sy.id_year
GROUP BY
    e.trade_name, sy.years_format
ORDER BY
    sy.years_format, e.trade_name;

-- 4. Année académique et comptage du nombre de contacts par année académique.
SELECT
    sy.years_format AS "Année académique",
    COUNT(ct.id_contact) AS "Nombre de contacts"
FROM
    pae.contacts ct
        JOIN pae.school_years sy ON ct.school_year = sy.id_year
GROUP BY
    sy.years_format
ORDER BY
    sy.years_format;

-- 5. Etats (en format lisible par le client) et comptage du nombre de contacts dans chacun des états.
SELECT
    ct.state AS "État",
    COUNT(ct.id_contact) AS "Nombre de contacts"
FROM
    pae.contacts ct
GROUP BY
    ct.state
ORDER BY
    ct.state;

-- 6. Année académique, états (en format lisible par le client) et comptage du nombre de contacts dans chacun des états par année académique.
SELECT
    sy.years_format AS "Année académique",
    ct.state AS "État",
    COUNT(ct.id_contact) AS "Nombre de contacts"
FROM
    pae.contacts ct
        JOIN pae.school_years sy ON ct.school_year = sy.id_year
GROUP BY
    sy.years_format, ct.state
ORDER BY
    sy.years_format, ct.state;

-- 7. Entreprise, états (en format lisible par le client) et comptage du nombre de contacts dans chacun des états par entreprise.
SELECT
    e.trade_name AS "Entreprise",
    ct.state AS "État",
    COUNT(ct.id_contact) AS "Nombre de contacts"
FROM
    pae.contacts ct
        JOIN pae.entreprises e ON ct.entreprise = e.id_entreprise
GROUP BY
    e.trade_name, ct.state
ORDER BY
    e.trade_name, ct.state;
