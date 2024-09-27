package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.dto.ConsultationDto;
import com.doctor.doctor.dto.RendezVousDto;
import com.doctor.doctor.entity.Consultation;
import com.doctor.doctor.entity.Medecin;
import com.doctor.doctor.entity.Patient;
import com.doctor.doctor.entity.RendezVous;
import com.doctor.doctor.repository.ConsultationRepository;
import com.doctor.doctor.repository.MedecinRepository;
import com.doctor.doctor.repository.PatientRepository;
import com.doctor.doctor.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private ConsultationRepository consultationRepository; // Ajout du repo de consultation

    // Récupérer tous les rendez-vous
    public List<RendezVousDto> getAllRendezVous() {
        return rendezVousRepository.findAll()
                .stream()
                .map(rv -> mapToDto(rv))
                .collect(Collectors.toList());
    }

    // Récupérer un rendez-vous par son id
    public RendezVousDto getRendezVousById(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous not found with id " + id));
        return mapToDto(rendezVous);
    }

    // Créer un nouveau rendez-vous avec éventuellement une consultation associée
    public RendezVousDto createRendezVous(RendezVousDto rendezVousDto) {
        Patient patient = patientRepository.findById(rendezVousDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + rendezVousDto.getPatientId()));

        Medecin medecin = medecinRepository.findById(rendezVousDto.getMedecinId())
                .orElseThrow(() -> new ResourceNotFoundException("Medecin not found with id " + rendezVousDto.getMedecinId()));


        RendezVous rendezVous = mapToEntity(rendezVousDto);
        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);


        RendezVous savedRendezVous = rendezVousRepository.save(rendezVous);
        return mapToDto(savedRendezVous);
    }

    // Mettre à jour un rendez-vous avec une consultation associée
    public RendezVousDto updateRendezVous(Long id, RendezVousDto rendezVousDto) {
        RendezVous existingRendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous not found with id " + id));

        Patient patient = patientRepository.findById(rendezVousDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + rendezVousDto.getPatientId()));

        Medecin medecin = medecinRepository.findById(rendezVousDto.getMedecinId())
                .orElseThrow(() -> new ResourceNotFoundException("Medecin not found with id " + rendezVousDto.getMedecinId()));

        existingRendezVous.setDateRdv(rendezVousDto.getDateRdv());
        existingRendezVous.setHeureRdv(rendezVousDto.getHeureRdv());
        existingRendezVous.setPatient(patient);
        existingRendezVous.setMedecin(medecin);



        RendezVous updatedRendezVous = rendezVousRepository.save(existingRendezVous);
        return mapToDto(updatedRendezVous);
    }

    // Supprimer un rendez-vous et sa consultation associée
    public void deleteRendezVous(Long id) {
        RendezVous existingRendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous not found with id " + id));

        // Supprimer la consultation si elle existe
        if (existingRendezVous.getConsultation() != null) {
            consultationRepository.delete(existingRendezVous.getConsultation());
        }

        rendezVousRepository.delete(existingRendezVous);
    }

    // Mapper une entité RendezVous en DTO
    private RendezVousDto mapToDto(RendezVous rendezVous) {
        ConsultationDto consultationDto = null;
        if (rendezVous.getConsultation() != null) {
            consultationDto = new ConsultationDto(
                    rendezVous.getConsultation().getId(),
                    rendezVous.getConsultation().getDate(),
                    rendezVous.getConsultation().getRapport(),
                    rendezVous.getId()
            );
        }

        return new RendezVousDto(
                rendezVous.getId(),
                rendezVous.getDateRdv(),
                rendezVous.getHeureRdv(),
                rendezVous.getPatient().getId(),
                rendezVous.getMedecin().getId()
        );
    }

    // Mapper un DTO RendezVous en entité
    private RendezVous mapToEntity(RendezVousDto rendezVousDto) {
        RendezVous rendezVous = new RendezVous();
        rendezVous.setId(rendezVousDto.getId());
        rendezVous.setDateRdv(rendezVousDto.getDateRdv());
        rendezVous.setHeureRdv(rendezVousDto.getHeureRdv());
        return rendezVous;
    }


}

