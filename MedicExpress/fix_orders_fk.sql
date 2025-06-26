-- Fix orders table foreign key constraints to point to users table
-- Drop the existing foreign key constraint that points to patients table
ALTER TABLE orders DROP CONSTRAINT IF EXISTS fka7bi85a1263srp54tjtnp1d6b;
-- Add new foreign key constraint pointing to users table
ALTER TABLE orders ADD CONSTRAINT fk_orders_patient_user FOREIGN KEY (patient) REFERENCES users(id);
