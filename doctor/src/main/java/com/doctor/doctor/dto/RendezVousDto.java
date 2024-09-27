package com.doctor.doctor.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVousDto {
    private Long id;
    private LocalDate dateRdv;
    private LocalTime heureRdv;
    private Long patientId;
    private Long medecinId;


    public RendezVousDto() {
    }

    public RendezVousDto(Long id, LocalDate dateRdv, LocalTime heureRdv, Long patientId, Long medecinId) {
        this.id = id;
        this.dateRdv = dateRdv;
        this.heureRdv = heureRdv;
        this.patientId = patientId;
        this.medecinId = medecinId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(LocalDate dateRdv) {
        this.dateRdv = dateRdv;
    }

    public LocalTime getHeureRdv() {
        return heureRdv;
    }

    public void setHeureRdv(LocalTime heureRdv) {
        this.heureRdv = heureRdv;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }


// Getters et setters
}
