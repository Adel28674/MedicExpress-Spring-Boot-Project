-- Suppression des tables si elles existent déjà
DROP TABLE IF EXISTS Message, Avis, Livraison, Commande, Ordonnance, Pharmacien, Pharmacie,
                     Livreur, Médecin, Patient, Administrateur CASCADE;

-- Table Administrateur
CREATE TABLE Administrateur (
    id_admin SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prénom VARCHAR(100),
    email VARCHAR(150) UNIQUE
);

-- Table Patient
CREATE TABLE Patient (
    id_patient SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prénom VARCHAR(100),
    adresse TEXT,
    email VARCHAR(150) UNIQUE,
    mot_de_passe TEXT,
    numéro_sécu VARCHAR(20)
);

-- Table Médecin
CREATE TABLE Médecin (
    id_médecin SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prénom VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    spécialité VARCHAR(100)
);

-- Table Ordonnance
CREATE TABLE Ordonnance (
    id_ordonnance SERIAL PRIMARY KEY,
    QR_code TEXT,
    date_creation DATE,
    id_patient INTEGER REFERENCES Patient(id_patient) ON DELETE CASCADE,
    id_médecin INTEGER REFERENCES Médecin(id_médecin) ON DELETE CASCADE
);

-- Table Pharmacie
CREATE TABLE Pharmacie (
    id_pharmacie SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    adresse TEXT
);

-- Table Pharmacien
CREATE TABLE Pharmacien (
    id_pharmacien SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prénom VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    pharmacie_id INTEGER REFERENCES Pharmacie(id_pharmacie) ON DELETE CASCADE
);

-- Table Commande
CREATE TABLE Commande (
    id_commande SERIAL PRIMARY KEY,
    statut VARCHAR(50),
    date_commande DATE,
    montant NUMERIC(10,2),
    code_sécurité VARCHAR(100),
    id_ordonnance INTEGER REFERENCES Ordonnance(id_ordonnance) ON DELETE CASCADE,
    id_patient INTEGER REFERENCES Patient(id_patient) ON DELETE CASCADE,
    id_pharmacie INTEGER REFERENCES Pharmacie(id_pharmacie) ON DELETE CASCADE
);

-- Table Livreur
CREATE TABLE Livreur (
    id_livreur SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prénom VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    véhicule VARCHAR(50)
);

-- Table Livraison
CREATE TABLE Livraison (
    id_livraison SERIAL PRIMARY KEY,
    date_livraison DATE,
    statut VARCHAR(50),
    id_commande INTEGER REFERENCES Commande(id_commande) ON DELETE CASCADE,
    id_livreur INTEGER REFERENCES Livreur(id_livreur) ON DELETE CASCADE
);

-- Table Avis
CREATE TABLE Avis (
    id_avis SERIAL PRIMARY KEY,
    note INTEGER CHECK (note >= 1 AND note <= 5),
    commentaire TEXT,
    id_patient INTEGER REFERENCES Patient(id_patient) ON DELETE CASCADE,
    id_livreur INTEGER REFERENCES Livreur(id_livreur) ON DELETE CASCADE
);

-- Table Message
CREATE TABLE Message (
    id_message SERIAL PRIMARY KEY,
    contenu TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_admin INTEGER REFERENCES Administrateur(id_admin) ON DELETE CASCADE,
    id_patient INTEGER REFERENCES Patient(id_patient) ON DELETE CASCADE
);
