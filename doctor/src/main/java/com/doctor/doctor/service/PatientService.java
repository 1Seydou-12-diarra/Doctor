package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.dto.PatientDto;
import com.doctor.doctor.entity.Patient;
import com.doctor.doctor.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    // Récupérer tous les patients
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> new PatientDto(patient.getId(), patient.getNom(), patient.getEmail()))
                .collect(Collectors.toList());
    }

    // Récupérer un patient par ID
    public PatientDto getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patient -> new PatientDto(patient.getId(), patient.getNom(), patient.getEmail()))
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé"));
    }

    // Créer un nouveau patient
    public PatientDto createPatient(PatientDto dto) {
        // Vérifier que le DTO n'est pas null
        if (dto == null) {
            throw new IllegalArgumentException("Le PatientDto ne peut pas être null");
        }

        // Créer l'entité Patient avec les données du DTO
        Patient patient = new Patient();
        patient.setNom(dto.getNom());
        patient.setEmail(dto.getEmail());

        // Sauvegarder le patient dans la base de données
        Patient savedPatient = patientRepository.save(patient);

        // Retourner le PatientDto avec l'ID du Patient créé
        return new PatientDto(savedPatient.getId(), savedPatient.getNom(), savedPatient.getEmail());
    }

    // Mettre à jour un patient existant
    public PatientDto updatePatient(Long id, PatientDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé"));

        // Mettre à jour les propriétés du patient
        patient.setNom(dto.getNom());
        patient.setEmail(dto.getEmail());

        Patient updatedPatient = patientRepository.save(patient);
        return new PatientDto(updatedPatient.getId(), updatedPatient.getNom(), updatedPatient.getEmail());
    }

    // Supprimer un patient
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé"));

        patientRepository.delete(patient);
        logger.info("Patient avec ID {} a été supprimé avec succès.", id);
    }
}

