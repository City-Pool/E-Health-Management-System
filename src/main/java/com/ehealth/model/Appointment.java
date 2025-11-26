package com.ehealth.model;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime scheduledDateTime;
    private String status;

    public Appointment() {}

    // getters and setters omitted for brevity
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public LocalDateTime getScheduledDateTime() { return scheduledDateTime; }
    public void setScheduledDateTime(LocalDateTime scheduledDateTime) { this.scheduledDateTime = scheduledDateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
