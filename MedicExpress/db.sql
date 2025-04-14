-- Suppression des tables existantes
DROP TABLE IF EXISTS message, avis, livraison, commande, ordonnance,
                      pharmacien, pharmacie, livreur, medecin, patient,
                      utilisateur, administrateur CASCADE;

-- Table utilisateur (user parent)
CREATE TABLE utilisateur (
    id_user SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(150) UNIQUE NOT NULL,
    mot_de_passe TEXT,
    type VARCHAR(20) CHECK (type IN ('patient', 'medecin', 'pharmacien', 'livreur'))
);

-- Table administrateur
CREATE TABLE administrateur (
    id_admin SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(150) UNIQUE NOT NULL
);

-- Table patient (hérite de utilisateur)
CREATE TABLE patient (
    id_patient INTEGER PRIMARY KEY REFERENCES utilisateur(id_user) ON DELETE CASCADE,
    adresse TEXT,
    numero_secu VARCHAR(20)
);

-- Table medecin (hérite de utilisateur)
CREATE TABLE medecin (
    id_medecin INTEGER PRIMARY KEY REFERENCES utilisateur(id_user) ON DELETE CASCADE,
    specialite VARCHAR(100)
);

-- Table pharmacie
CREATE TABLE pharmacie (
    id_pharmacie SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    adresse TEXT
);

-- Table pharmacien (hérite de utilisateur)
CREATE TABLE pharmacien (
    id_pharmacien INTEGER PRIMARY KEY REFERENCES utilisateur(id_user) ON DELETE CASCADE,
    pharmacie_id INTEGER REFERENCES pharmacie(id_pharmacie) ON DELETE CASCADE
);

-- Table livreur (hérite de utilisateur)
CREATE TABLE livreur (
    id_livreur INTEGER PRIMARY KEY REFERENCES utilisateur(id_user) ON DELETE CASCADE,
    vehicule VARCHAR(50)
);

-- Table ordonnance
CREATE TABLE ordonnance (
    id_ordonnance SERIAL PRIMARY KEY,
    qr_code TEXT,
    date_creation DATE,
    id_patient INTEGER REFERENCES patient(id_patient) ON DELETE CASCADE,
    id_medecin INTEGER REFERENCES medecin(id_medecin) ON DELETE CASCADE
);

-- Table commande
CREATE TABLE commande (
    id_commande SERIAL PRIMARY KEY,
    statut VARCHAR(50),
    date_commande DATE,
    montant NUMERIC(10,2),
    code_securite VARCHAR(100),
    id_ordonnance INTEGER REFERENCES ordonnance(id_ordonnance) ON DELETE CASCADE,
    id_patient INTEGER REFERENCES patient(id_patient) ON DELETE CASCADE,
    id_pharmacie INTEGER REFERENCES pharmacie(id_pharmacie) ON DELETE CASCADE
);

-- Table livraison
CREATE TABLE livraison (
    id_livraison SERIAL PRIMARY KEY,
    date_livraison DATE,
    statut VARCHAR(50),
    id_commande INTEGER REFERENCES commande(id_commande) ON DELETE CASCADE,
    id_livreur INTEGER REFERENCES livreur(id_livreur) ON DELETE CASCADE
);

-- Table avis
CREATE TABLE avis (
    id_avis SERIAL PRIMARY KEY,
    note INTEGER CHECK (note >= 1 AND note <= 5),
    commentaire TEXT,
    id_patient INTEGER REFERENCES patient(id_patient) ON DELETE CASCADE,
    id_livreur INTEGER REFERENCES livreur(id_livreur) ON DELETE CASCADE
);

-- Table message
CREATE TABLE message (
    id_message SERIAL PRIMARY KEY,
    contenu TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_admin INTEGER REFERENCES administrateur(id_admin) ON DELETE CASCADE,
    id_patient INTEGER REFERENCES patient(id_patient) ON DELETE CASCADE
);
