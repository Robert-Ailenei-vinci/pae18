DROP SCHEMA IF EXISTS pae CASCADE ;
CREATE SCHEMA pae;

CREATE TABLE pae.annees_academiques (
    id_annee INTEGER PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    en_cours BOOLEAN NOT NULL
);

CREATE TABLE pae.utilisateurs (
    id_user INTEGER PRIMARY KEY,
    email TEXT NOT NULL ,
    role_u TEXT NOT NULL,
    surname TEXT NOT NULL ,
    firstname TEXT NOT NULL ,
    phone_num TEXT NOT NULL ,
    psw TEXT NOT NULL ,
    inscription_date DATE NOT NULL,
    annee_academique INTEGER NOT NULL REFERENCES pae.annees_academiques(id_annee)
);

INSERT INTO pae.annees_academiques VALUES (1, '2024-2025', false);

INSERT INTO pae.utilisateurs VALUES (1, 'mia.liae@student.vinci.be','etudiant','Mia','Lia','04855555','$2a$10$FVdM7uPIIKcD9M4k6cjh1uUO8xgNQxKLtmxON1aA3iEs6vKiNHuYK','27/12/2024', 1);
INSERT INTO pae.utilisateurs VALUES (2, 'raf.louisiane@student.vinci.be','etudiant','Raf', 'Louisiane','04855544','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','15/02/2024', 1);
INSERT INTO pae.utilisateurs VALUES (3, 'vinci.leonard@student.vinci.be','professeur','Vinci','Leonard','04855594','$2a$10$/yWXmsA8IxK1OGxziDMlKO3RJz9uy/.Q0Io/RDw8FBFH7F3Dbvoxq','17/03/2024', 1);