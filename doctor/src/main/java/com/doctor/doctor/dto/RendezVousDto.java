package com.doctor.doctor.dto;

import java.time.LocalDate;

public class RendezVousDto {
    private Long id;
    private LocalDate date;
    private PatientDto patient;
    private MedecinDto medecin;

    public RendezVousDto() {
    }

    public RendezVousDto(Long id, LocalDate date, PatientDto patient, MedecinDto medecin) {
        this.id = id;
        this.date = date;
        this.patient = patient;
        this.medecin = medecin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public MedecinDto getMedecin() {
        return medecin;
    }

    public void setMedecin(MedecinDto medecin) {
        this.medecin = medecin;
    }
// Getters et setters
}
