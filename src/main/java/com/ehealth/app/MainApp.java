package com.ehealth.app;

import com.ehealth.model.Patient;
import com.ehealth.dao.PatientDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public class MainApp {
    public static void main(String[] args) {
        PatientDAO dao = new PatientDAO();
        Patient p = new Patient();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setDob(LocalDate.of(1990,1,1));
        p.setGender("Male");

        try {
            dao.save(p);
            System.out.println("Saved patient with ID: " + p.getId());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
