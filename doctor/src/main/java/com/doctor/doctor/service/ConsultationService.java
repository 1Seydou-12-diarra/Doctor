package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.dto.ConsultationDto;
import com.doctor.doctor.entity.Consultation;
import com.doctor.doctor.entity.RendezVous;
import com.doctor.doctor.repository.ConsultationRepository;
import com.doctor.doctor.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;  // Nécessaire pour gérer l'association avec RendezVous

    // Récupérer toutes les consultations
    public List<ConsultationDto> getAllConsultations() {
        return consultationRepository.findAll()
                .stream()
                .map(consultation -> mapToDto(consultation))
                .collect(Collectors.toList());
    }

    // Récupérer une consultation par son id
    public ConsultationDto getConsultationById(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with id " + id));
        return mapToDto(consultation);
    }

    // Créer une nouvelle consultation
    public ConsultationDto createConsultation(ConsultationDto consultationDto) {
        RendezVous rendezVous = rendezVousRepository.findById(consultationDto.getRendezVousId())
                .orElseThrow(() -> new ResourceNotFoundException("RendezVous not found with id " + consultationDto.getRendezVousId()));

        Consultation consultation = mapToEntity(consultationDto);
        consultation.setRendezVous(rendezVous); // Lier la consultation avec le rendez-vous
        Consultation savedConsultation = consultationRepository.save(consultation);
        return mapToDto(savedConsultation);
    }

    // Mettre à jour une consultation existante
    public ConsultationDto updateConsultation(Long id, ConsultationDto consultationDto) {
        Consultation existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with id " + id));

        RendezVous rendezVous = rendezVousRepository.findById(consultationDto.getRendezVousId())
                .orElseThrow(() -> new ResourceNotFoundException("RendezVous not found with id " + consultationDto.getRendezVousId()));

        existingConsultation.setDate(consultationDto.getDate());
        existingConsultation.setRapport(consultationDto.getRapport());
        existingConsultation.setRendezVous(rendezVous);  // Mettre à jour le lien avec le rendez-vous

        Consultation updatedConsultation = consultationRepository.save(existingConsultation);
        return mapToDto(updatedConsultation);
    }

    // Supprimer une consultation par son id
    public void deleteConsultation(Long id) {
        Consultation existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with id " + id));
        consultationRepository.delete(existingConsultation);
    }

    // Mapper une entité Consultation en DTO
    private ConsultationDto mapToDto(Consultation consultation) {
        return new ConsultationDto(consultation.getId(), consultation.getDate(), consultation.getRapport(), consultation.getRendezVous().getId());
    }

    // Mapper un DTO Consultation en entité
    private Consultation mapToEntity(ConsultationDto consultationDto) {
        Consultation consultation = new Consultation();
        consultation.setId(consultationDto.getId());
        consultation.setDate(consultationDto.getDate());
        consultation.setRapport(consultationDto.getRapport());
        return consultation;
    }
}
