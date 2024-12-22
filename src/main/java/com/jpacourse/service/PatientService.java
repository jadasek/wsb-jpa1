package com.jpacourse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public List<PatientTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toTO)
                .toList();
    }

    public PatientTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toTO)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }
}