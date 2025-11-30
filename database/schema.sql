CREATE DATABASE IF NOT EXISTS ehealth;
USE ehealth;

CREATE TABLE IF NOT EXISTS patient (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  dob DATE,
  gender VARCHAR(10),
  phone VARCHAR(30),
  email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS doctor (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  specialization VARCHAR(100),
  phone VARCHAR(30),
  email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS doctor_schedule (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  doctor_id BIGINT NOT NULL,
  slot DATETIME NOT NULL,
  available BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS appointment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT,
  doctor_id BIGINT,
  scheduled_datetime DATETIME,
  status VARCHAR(20),
  FOREIGN KEY (patient_id) REFERENCES patient(id),
  FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS medical_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT,
  doctor_id BIGINT,
  visit_date DATETIME,
  notes TEXT,
  FOREIGN KEY (patient_id) REFERENCES patient(id),
  FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);
