-- Database Migration: Fix Prescription Foreign Key Constraints
-- This script updates the prescriptions table to reference users table directly
-- instead of separate doctors and patients tables

-- Step 1: Drop existing foreign key constraints
-- (You may need to check the exact constraint names in your database)
ALTER TABLE prescriptions DROP CONSTRAINT IF EXISTS fkhogohsb63b9yvsgvpnowf88ct; -- doctor FK
ALTER TABLE prescriptions DROP CONSTRAINT IF EXISTS fkaku2o4f8qjvryp1s6bqqid95g; -- patient FK

-- Step 2: Add new foreign key constraints pointing to users table
ALTER TABLE prescriptions ADD CONSTRAINT fk_prescription_doctor 
    FOREIGN KEY (doctor) REFERENCES users(id) 
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE prescriptions ADD CONSTRAINT fk_prescription_patient 
    FOREIGN KEY (patient) REFERENCES users(id) 
    ON DELETE CASCADE ON UPDATE CASCADE;

-- Verification queries (run these to check the changes)
-- SELECT constraint_name, constraint_type FROM information_schema.table_constraints 
-- WHERE table_name = 'prescriptions'; 