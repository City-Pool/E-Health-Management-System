package com.ehealth.model;

import java.time.LocalDateTime;

public class MedicalRecord {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime visitDate;
    private String notes;

    public MedicalRecord() {}

    // getters and setters omitted for brevity
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public LocalDateTime getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDateTime visitDate) { this.visitDate = visitDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
