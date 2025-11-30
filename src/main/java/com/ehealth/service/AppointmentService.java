package com.ehealth.service;

import com.ehealth.db.DatabaseConnection;
import com.ehealth.dao.AppointmentDAO;
import com.ehealth.dao.DoctorDAO;
import com.ehealth.model.Appointment;

import java.sql.Connection;
import java.sql.SQLException;

public class AppointmentService {

    private final AppointmentDAO appointmentDao = new AppointmentDAO();
    private final DoctorDAO doctorDao = new DoctorDAO();

    /**
     * Books an appointment atomically: reserve doctor's schedule slot + create appointment.
     * All operations share the same Connection/transaction.
     */
    public long bookAppointment(Appointment appointment) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // begin transaction

            // 1) check conflict
            boolean conflict = appointmentDao.existsByDoctorAndDatetime(conn, appointment.getDoctorId(), appointment.getScheduledDateTime());
            if (conflict) {
                throw new IllegalStateException("Selected slot already booked");
            }

            // 2) reserve slot in doctor_schedule table
            doctorDao.reserveSlot(conn, appointment.getDoctorId(), appointment.getScheduledDateTime());

            // 3) save appointment
            long appointmentId = appointmentDao.save(conn, appointment);

            // 4) commit
            conn.commit();
            return appointmentId;
        } catch (SQLException | RuntimeException ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { /* log rollback failure */ }
            }
            throw ex;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { /* log close failure */ }
            }
        }
    }
}
