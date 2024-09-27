package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.dto.MedecinDto;
import com.doctor.doctor.entity.Medecin;
import com.doctor.doctor.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;


    // Récupérer tous les médecins
    public List<MedecinDto> getAllMedecins() {
        return medecinRepository.findAll()
                .stream()
                .map(medecin -> mapToDto(medecin))
                .collect(Collectors.toList());
    }

    // Récupérer un médecin par son id
    public MedecinDto getMedecinById(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin not found with id " + id));
        return mapToDto(medecin);
    }

    // Créer un nouveau médecin
    public MedecinDto createMedecin(MedecinDto medecinDto) {
        Medecin medecin = mapToEntity(medecinDto);
        Medecin savedMedecin = medecinRepository.save(medecin);
        return mapToDto(savedMedecin);
    }

    // Mettre à jour un médecin existant
    public MedecinDto updateMedecin(Long id, MedecinDto medecinDto) {
        Medecin existingMedecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin not found with id " + id));

        existingMedecin.setNom(medecinDto.getNom());
        existingMedecin.setEmail(medecinDto.getEmail());
        existingMedecin.setSpecialite(medecinDto.getSpecialite());

        Medecin updatedMedecin = medecinRepository.save(existingMedecin);
        return mapToDto(updatedMedecin);
    }

    // Supprimer un médecin par son id
    public void deleteMedecin(Long id) {
        Medecin existingMedecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin not found with id " + id));
        medecinRepository.delete(existingMedecin);
    }

    // Mapper une entité Medecin en DTO
    private MedecinDto mapToDto(Medecin medecin) {
        return new MedecinDto(medecin.getId(), medecin.getNom(), medecin.getEmail(), medecin.getSpecialite());
    }

    // Mapper un DTO Medecin en entité
    private Medecin mapToEntity(MedecinDto medecinDto) {
        return new Medecin(medecinDto.getId(), medecinDto.getNom(), medecinDto.getEmail(), medecinDto.getSpecialite());
    }
}

