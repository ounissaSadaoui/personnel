schéma relationnel:

Ligue(__id_ligue__, nom_ligue)
Employe(__id_employe__, nom_employe, prenom_employe, mail_employe, password_employe, dateArrivee, dateDepart, __#id_Ligue__)

creation des tables

CREATE TABLE Employe (
        id_employe INT PRIMARY KEY not null,
        nom_employe VARCHAR(255), 
        prenom_employe VARCHAR(255), 
        mail_employe VARCHAR(255),   
        password_employe VARCHAR(255),
        dateArrivee DATE,
        dateDepart DATE,
        id_ligue int
);

create table Ligue (
    id_ligue int primary key not null,
    nom_ligue varchar (255)
    );
    
    ajout de la clé étrangère dans la table Employe
    
    Alter table Employe add foreign key id_ligue references LIGUE (id_ligue)
    
