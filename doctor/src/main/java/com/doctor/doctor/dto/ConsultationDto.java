package com.doctor.doctor.dto;

import java.time.LocalDate;

public class ConsultationDto {
    private Long id;
    private LocalDate date;
    private String rapport;
    private Long rendezVousId;

    public ConsultationDto() {
    }

    public ConsultationDto(Long id, LocalDate date, String rapport, Long rendezVousId) {
        this.id = id;
        this.date = date;
        this.rapport = rapport;
        this.rendezVousId = rendezVousId;
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

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public Long getRendezVousId() {
        return rendezVousId;
    }

    public void setRendezVousId(Long rendezVousId) {
        this.rendezVousId = rendezVousId;
    }
// Getters et setters
}
