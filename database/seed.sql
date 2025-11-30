-- Optional seed data for testing
INSERT INTO doctor (first_name, last_name, specialization, phone, email) VALUES ('Emanuel', 'Minja', 'General', '9999999999', 'emanuel@galgotias.edu');
INSERT INTO patient (first_name, last_name, dob, gender, phone, email) VALUES ('Test', 'Patient', '1990-01-01', 'Male', '8888888888', 'test@patient.com');

-- create a few schedule slots for doctor id 1 (adjust id if different)
INSERT INTO doctor_schedule (doctor_id, slot, available) VALUES (1, '2025-12-01 10:00:00', TRUE);
INSERT INTO doctor_schedule (doctor_id, slot, available) VALUES (1, '2025-12-01 11:00:00', TRUE);
