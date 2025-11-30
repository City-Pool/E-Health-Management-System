package com.ehealth.dao;

import com.ehealth.model.Patient;
import com.ehealth.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Transaction-aware save: accepts connection
    public long save(Connection conn, Patient p) throws SQLException {
        String sql = "INSERT INTO patient (first_name, last_name, dob, gender, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setDate(3, p.getDob() != null ? Date.valueOf(p.getDob()) : null);
            ps.setString(4, p.getGender());
            ps.setString(5, p.getPhone());
            ps.setString(6, p.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    p.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("Failed to save patient");
    }

    // Convenience wrapper: opens its own connection
    public long save(Patient p) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return save(conn, p);
        }
    }

    public List<Patient> findAll() throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name FROM patient";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Patient p = new Patient(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"));
                list.add(p);
            }
        }
        return list;
    }
}
