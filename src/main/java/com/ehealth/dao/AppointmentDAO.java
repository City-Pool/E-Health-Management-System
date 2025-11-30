package com.ehealth.dao;

import com.ehealth.model.Appointment;
import com.ehealth.db.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Transaction-aware save: returns generated id
    public long save(Connection conn, Appointment a) throws SQLException {
        String sql = "INSERT INTO appointment (patient_id, doctor_id, scheduled_datetime, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, a.getPatientId());
            ps.setLong(2, a.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(a.getScheduledDateTime()));
            ps.setString(4, a.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    a.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("Failed to create appointment");
    }

    // Convenience wrapper
    public long save(Appointment a) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            return save(conn, a);
        }
    }

    // Check if doctor has appointment at exact datetime (transaction-aware)
    public boolean existsByDoctorAndDatetime(Connection conn, Long doctorId, LocalDateTime dt) throws SQLException {
        String sql = "SELECT 1 FROM appointment WHERE doctor_id = ? AND scheduled_datetime = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, doctorId);
            ps.setTimestamp(2, Timestamp.valueOf(dt));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Appointment> findByDoctor(Long doctorId) throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT id, patient_id, doctor_id FROM appointment WHERE doctor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Appointment a = new Appointment();
                    a.setId(rs.getLong("id"));
                    a.setPatientId(rs.getLong("patient_id"));
                    a.setDoctorId(rs.getLong("doctor_id"));
                    list.add(a);
                }
            }
        }
        return list;
    }
}
