-- Création de la base de données
CREATE DATABASE IF NOT EXISTS ebiblio_db;
USE ebiblio_db;

CREATE TABLE Adherents (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    type ENUM('ETUDIANT', 'ENSEIGNANT', 'VISITEUR') NOT NULL,
    idUnique VARCHAR(100),
    adresse VARCHAR(255)
);

CREATE TABLE Livres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(200) NOT NULL,
    auteur VARCHAR(100) NOT NULL,
    type ENUM('SCIENTIFIQUE', 'LITTERAIRE') NOT NULL,
    editeur VARCHAR(255),
    dateEdition DATE,
    codeUnique VARCHAR(100),
    nbExemplaires INT
);

CREATE TABLE Emprunts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    adherent_id INT NOT NULL,
    livre_id INT NOT NULL,
    date_emprunt DATE NOT NULL,
    date_limite DATE NOT NULL,
    date_retour DATE,
    FOREIGN KEY (adherent_id) REFERENCES Adherents(id) ON DELETE CASCADE,
    FOREIGN KEY (livre_id) REFERENCES Livres(id) ON DELETE CASCADE
);
