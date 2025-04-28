DROP TABLE IF EXISTS pharmacy_has_treatments, orders, treatment, pharmacy, administrator, doctor, deliveryDriver, patient, users CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    password TEXT,
    name VARCHAR(100),
    firstName VARCHAR(100)
);

CREATE TABLE patient (
    id INT PRIMARY KEY,
    sex TEXT,
    description TEXT,
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE deliveryDriver (
    kbis INT PRIMARY KEY,
    rate INT,
    available BOOLEAN,
    currentOrder INT,
    FOREIGN KEY (kbis) REFERENCES users(id)
);

CREATE TABLE doctor (
    RPPS INT PRIMARY KEY,
    FOREIGN KEY (RPPS) REFERENCES users(id)
);

CREATE TABLE administrator (
    id INT PRIMARY KEY,
    role TEXT,
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE pharmacy (
    id SERIAL PRIMARY KEY,
    address TEXT
);

CREATE TABLE treatment (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    date DATE,
    treatment_id INT,
    FOREIGN KEY (treatment_id) REFERENCES treatment(id)
);

CREATE TABLE pharmacy_has_treatments (
    id_pharma INT,
    id_treatment INT,
    stocks INT,
    PRIMARY KEY (id_pharma, id_treatment),
    FOREIGN KEY (id_pharma) REFERENCES pharmacy(id),
    FOREIGN KEY (id_treatment) REFERENCES treatment(id)
);
