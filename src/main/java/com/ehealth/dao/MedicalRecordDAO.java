package com.ehealth.dao;

import com.ehealth.model.MedicalRecord;
import com.ehealth.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {

    public long save(Connection conn, MedicalRecord r) throws SQLException {
        String sql = "INSERT INTO medical_record (patient_id, doctor_id, visit_date, notes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, r.getPatientId());
            ps.setLong(2, r.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(r.getVisitDate()));
            ps.setString(4, r.getNotes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    r.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("Failed to save medical record");
    }

    public List<MedicalRecord> findByPatient(Long patientId) throws SQLException {
        List<MedicalRecord> list = new ArrayList<>();
        String sql = "SELECT id, doctor_id, visit_date, notes FROM medical_record WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MedicalRecord r = new MedicalRecord();
                    r.setId(rs.getLong("id"));
                    r.setDoctorId(rs.getLong("doctor_id"));
                    Timestamp ts = rs.getTimestamp("visit_date");
                    if (ts != null) r.setVisitDate(ts.toLocalDateTime());
                    r.setNotes(rs.getString("notes"));
                    list.add(r);
                }
            }
        }
        return list;
    }
}
