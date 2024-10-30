package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.dto.MedecinDto;
import com.doctor.doctor.entity.Medecin;
import com.doctor.doctor.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CrossOrigin(origins = "http://127.0.0.1:3001") // Spécifiez l'origine autorisée

public class MedecinService {
    @Autowired
    private MedecinRepository medecinRepository;

    // Méthode pour récupérer tous les médecins
    public List<MedecinDto> getAllMedecins() {
        return medecinRepository.findAll()
                .stream()
                .map(medecin -> new MedecinDto(Math.toIntExact(medecin.getId()), medecin.getNom(), medecin.getEmail(), medecin.getSpecialite()))
                .collect(Collectors.toList());
    }

    // Méthode pour récupérer un médecin par son ID
    public MedecinDto getMedecinById(Long id) {
        return medecinRepository.findById(id)
                .map(medecin -> new MedecinDto(Math.toIntExact(medecin.getId()),medecin.getNom(), medecin.getEmail(), medecin.getSpecialite()))
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé"));
    }

    // Méthode pour créer un médecin
    public MedecinDto createMedecin(MedecinDto dto) {
        Medecin medecin = new Medecin();
        medecin.setNom(dto.getNom());
        medecin.setEmail(dto.getEmail());
        medecin.setSpecialite(dto.getSpecialite());

        Medecin savedMedecin = medecinRepository.save(medecin);
        return new MedecinDto(Math.toIntExact(savedMedecin.getId()), savedMedecin.getNom(), savedMedecin.getEmail(), savedMedecin.getSpecialite());
    }

    // Méthode pour mettre à jour un médecin
    public MedecinDto updateMedecin(Long id, MedecinDto dto) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé"));

        medecin.setNom(dto.getNom());
        medecin.setEmail(dto.getEmail());
        medecin.setSpecialite(dto.getSpecialite());

        Medecin updatedMedecin = medecinRepository.save(medecin);
        return new MedecinDto(Math.toIntExact(updatedMedecin.getId()), updatedMedecin.getNom(), updatedMedecin.getEmail(), updatedMedecin.getSpecialite());
    }

    // Méthode pour supprimer un médecin
    public void deleteMedecin(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé"));

        medecinRepository.delete(medecin);
    }
}
