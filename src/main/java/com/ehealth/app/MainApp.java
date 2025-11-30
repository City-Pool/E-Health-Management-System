package com.ehealth.app;

import com.ehealth.model.Appointment;
import com.ehealth.service.AppointmentService;

import java.time.LocalDateTime;

public class MainApp {
    public static void main(String[] args) {
        AppointmentService service = new AppointmentService();
        Appointment a = new Appointment();
        a.setPatientId(1L); // adjust based on seeded data
        a.setDoctorId(1L);
        a.setScheduledDateTime(LocalDateTime.of(2025,12,1,10,0));
        a.setStatus("SCHEDULED");

        try {
            long id = service.bookAppointment(a);
            System.out.println("Appointment booked with id: " + id);
        } catch (Exception e) {
            System.err.println("Failed to book appointment: " + e.getMessage());
        }
    }
}
