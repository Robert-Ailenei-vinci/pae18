DROP SCHEMA IF EXISTS pae;
CREATE SCHEMA pae;


CREATE TABLE utilisateurs (
    id_utilisateur SERIAL PRIMARY KEY,
    email TEXT NOT NULL ,
    role_u TEXT NOT NULL,
    nom TEXT NOT NULL ,
    prenom TEXT NOT NULL ,
    telephone INT NOT NULL ,
    mot_de_passe TEXT NOT NULL ,
    date_inscription DATE NOT NULL
);
