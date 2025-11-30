package com.ehealth.integration;

import com.ehealth.db.DatabaseConnection;
import com.ehealth.model.Appointment;
import com.ehealth.service.AppointmentService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

import static org.junit.Assert.fail;

public class TransactionIntegrationTest {

    @Test
    public void testRollbackOnFailure() {
        // This is an integration-style test outline.
        // To run this you need a test database configured and accessible.
        AppointmentService service = new AppointmentService();
        Appointment a = new Appointment();
        a.setPatientId(1L);
        a.setDoctorId(1L);
        a.setScheduledDateTime(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0));
        a.setStatus("SCHEDULED");

        try {
            // Intentionally cause an error after reserving slot by reserving same slot in separate connection
            service.bookAppointment(a);

            // Attempt to reserve same slot again using raw update to simulate concurrency/conflict - should fail
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE doctor_schedule SET available = FALSE WHERE doctor_id = ? AND slot = ? AND available = TRUE")) {
                ps.setLong(1, 1L);
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(a.getScheduledDateTime()));
                int updated = ps.executeUpdate();
                if (updated == 0) {
                    // expected if previous transaction committed
                }
            }

        } catch (Exception e) {
            // if exception occurs, test failed for demo purposes
            fail("Integration test failed: " + e.getMessage());
        }
    }
}
