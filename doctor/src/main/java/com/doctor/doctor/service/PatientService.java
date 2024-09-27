package com.doctor.doctor.service;

import com.doctor.doctor.Exception.ResourceNotFoundException;
import com.doctor.doctor.dto.PatientDto;
import com.doctor.doctor.entity.Patient;
import com.doctor.doctor.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> new PatientDto(patient.getId(), patient.getNom(), patient.getEmail()))
                .collect(Collectors.toList());
    }

    public PatientDto getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patient -> new PatientDto(patient.getId(), patient.getNom(), patient.getEmail()))
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    public PatientDto createPatient(PatientDto dto) {
        Patient patient = new Patient();
        patient.setNom(dto.getNom());
        patient.setEmail(dto.getEmail());

        Patient savedPatient = patientRepository.save(patient);
        return new PatientDto(savedPatient.getId(), savedPatient.getNom(), savedPatient.getEmail());
    }

    public PatientDto updatePatient(Long id, PatientDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setNom(dto.getNom());
        patient.setEmail(dto.getEmail());

        Patient updatedPatient = patientRepository.save(patient);
        return new PatientDto(updatedPatient.getId(), updatedPatient.getNom(), updatedPatient.getEmail());
    }

    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patientRepository.delete(patient);
    }
}

