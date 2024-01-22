// schema relationnel
ligue(__idLigue__, nomLigue, __#idEmploye__)
employe(__idEmploye__, nomEmploye, prenomEmploye, mailEmploye, passwordEmploye, dateArrivee, dateDepart, __#idLigue__)

// Création de la table Employe
CREATE TABLE employe (
    idEmploye INT PRIMARY KEY,
    nomEmploye VARCHAR(255),
    prenomEmploye VARCHAR(255),
    mailEmploye VARCHAR(255),
    passwordEmploye VARCHAR(255),
    dateArrivee DATE,
    dateDepart DATE,
    idLigue INT
);

// Création de la table Ligue
CREATE TABLE ligue (
    idLigue INT PRIMARY KEY,
    nomLigue VARCHAR(255),
    idEmploye INT
);

// Ajout de la clé étrangère dans la table Employe
ALTER TABLE employe
ADD FOREIGN KEY (idLigue) REFERENCES ligue(idLigue);

// Ajout de la clé étrangère dans la table Ligue
ALTER TABLE ligue
ADD FOREIGN KEY (idEmploye) REFERENCES employe(idEmploye);
