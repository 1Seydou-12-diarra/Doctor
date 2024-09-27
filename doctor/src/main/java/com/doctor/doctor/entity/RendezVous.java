package com.doctor.doctor.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateRdv;
    private LocalTime heureRdv;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;

    public RendezVous() {
    }

    public RendezVous(Long id, LocalDate dateRdv, LocalTime heureRdv, Patient patient, Medecin medecin, Consultation consultation) {
        this.id = id;
        this.dateRdv = dateRdv;
        this.heureRdv = heureRdv;
        this.patient = patient;
        this.medecin = medecin;
        this.consultation = consultation;
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

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
// Getters et setters
}
