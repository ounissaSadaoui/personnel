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

CREATE TABLE ligue (
    idLigue INT PRIMARY KEY,
    nomLigue VARCHAR(255),
    idEmploye INT
);
ALTER TABLE employe
ADD FOREIGN KEY (idLigue) REFERENCES ligue(idLigue);

ALTER TABLE ligue
ADD FOREIGN KEY (idEmploye) REFERENCES employe(idEmploye);

