package com.doctor.doctor.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;


    @ManyToOne(fetch = FetchType.EAGER)  // Charger les entités liées immédiatement
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)  // Charger les entités liées immédiatement
    @JoinColumn(name = "medecin_id", referencedColumnName = "id")
    private Medecin medecin;


    public RendezVous() {
    }

    public RendezVous(Long id, LocalDate date, Patient patient, Medecin medecin) {
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }
}
