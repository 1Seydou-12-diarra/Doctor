package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.dto.MedecinDto;
import com.doctor.doctor.dto.PatientDto;
import com.doctor.doctor.dto.RendezVousDto;
import com.doctor.doctor.entity.Medecin;
import com.doctor.doctor.entity.Patient;
import com.doctor.doctor.entity.RendezVous;
import com.doctor.doctor.repository.MedecinRepository;
import com.doctor.doctor.repository.PatientRepository;
import com.doctor.doctor.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CrossOrigin(origins = "http://127.0.0.1:3001") // Spécifiez l'origine autorisée

public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedecinRepository medecinRepository;

    public List<RendezVousDto> getListeRendezVous() {
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        return rendezVousList.stream()
                .map(this::convertToDto)  // Convertir l'entité en DTO si nécessaire
                .collect(Collectors.toList());
    }
    public RendezVousDto createRendezVous(RendezVousDto dto) {
        // Vérifiez si le médecin existe déjà dans la base de données
        Medecin medecin = medecinRepository.findById(Long.valueOf(dto.getMedecin().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé"));
// Vérifiez si le patient existe déjà dans la base de données
        Patient patient = patientRepository.findById(Long.valueOf(dto.getPatient().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé"));


        // Créez un nouveau rendez-vous
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDate(dto.getDate());
        rendezVous.setMedecin(medecin);  // Référence le médecin existant
        rendezVous.setPatient(patient);   // Référence le patient existant

        // Sauvegardez le rendez-vous dans la base de données
        RendezVous savedRendezVous = rendezVousRepository.save(rendezVous);

        return convertToDto(savedRendezVous);  // Convertir en DTO avant de retourner
    }


    public RendezVousDto updateRendezVous(Long id, RendezVousDto rendezVousDto) {
        // Récupérer le rendez-vous existant à partir de l'id
        RendezVous existingRendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'id : " + id));

        // Mise à jour des champs
        existingRendezVous.setDate(rendezVousDto.getDate());
        existingRendezVous.setPatient(convertToEntity(rendezVousDto.getPatient()));
        existingRendezVous.setMedecin(convertToEntity(rendezVousDto.getMedecin()));

        // Sauvegarder les modifications
        RendezVous updatedRendezVous = rendezVousRepository.save(existingRendezVous);

        // Retourner le DTO mis à jour
        return convertToDto(updatedRendezVous);
    }

    public void deleteRendezVous(Long id) {
        // Vérifier si le rendez-vous existe
        RendezVous existingRendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'id : " + id));

        // Supprimer le rendez-vous
        rendezVousRepository.delete(existingRendezVous);
    }

    private RendezVousDto convertToDto(RendezVous rendezVous) {
        return new RendezVousDto(
                rendezVous.getId(),
                rendezVous.getDate(),
                convertToDto(rendezVous.getPatient()),  // Inclure les détails du patient
                convertToDto(rendezVous.getMedecin())   // Inclure les détails du médecin
        );
    }

    private PatientDto convertToDto(Patient patient) {
        return new PatientDto((long) Math.toIntExact(patient.getId()), patient.getNom(), patient.getEmail());
    }

    private MedecinDto convertToDto(Medecin medecin) {
        return new MedecinDto(Math.toIntExact(medecin.getId()), medecin.getNom(), medecin.getEmail(), medecin.getSpecialite());
    }


    private Patient convertToEntity(PatientDto patientDto) {
        return new Patient((long) Math.toIntExact(patientDto.getId()), patientDto.getNom(), patientDto.getEmail());
    }

    private Medecin convertToEntity(MedecinDto medecinDto) {
        return new Medecin( medecinDto.getNom(), medecinDto.getEmail(), medecinDto.getSpecialite());
    }
}
