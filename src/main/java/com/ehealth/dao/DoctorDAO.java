package com.ehealth.dao;

import com.ehealth.model.Doctor;
import com.ehealth.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Transaction-aware save
    public long save(Connection conn, Doctor d) throws SQLException {
        String sql = "INSERT INTO doctor (first_name, last_name, specialization, phone, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getFirstName());
            ps.setString(2, d.getLastName());
            ps.setString(3, d.getSpecialization());
            ps.setString(4, d.getPhone());
            ps.setString(5, d.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    d.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("Failed to save doctor");
    }

    public long save(Doctor d) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return save(conn, d);
        }
    }

    public List<Doctor> findAll() throws SQLException {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, specialization FROM doctor";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Doctor d = new Doctor(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("specialization"));
                list.add(d);
            }
        }
        return list;
    }

    // Reserve a slot in doctor_schedule: transaction-aware
    public void reserveSlot(Connection conn, Long doctorId, java.time.LocalDateTime slot) throws SQLException {
        String sql = "UPDATE doctor_schedule SET available = FALSE WHERE doctor_id = ? AND slot = ? AND available = TRUE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, doctorId);
            ps.setTimestamp(2, Timestamp.valueOf(slot));
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new SQLException("Slot not available or already reserved");
            }
        }
    }
}
