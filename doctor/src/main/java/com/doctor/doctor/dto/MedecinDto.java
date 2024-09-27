package com.doctor.doctor.dto;

public class MedecinDto {
    private Long id;
    private String nom;
    private String email;
    private String specialite;

    public MedecinDto() {
    }

    public MedecinDto(Long id, String nom, String email, String specialite) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.specialite = specialite;
    }

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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
// Getters et setters
}
