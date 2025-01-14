package com.doctor.doctor.dto;



public class PatientDto {
    private Long id;
    private String nom;
    private String email;

    // Constructeur
    public PatientDto(Long id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    // Constructeur par défaut (nécessaire pour la désérialisation)
    public PatientDto() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
