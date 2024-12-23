package com.jpacourse.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.entity.PatientEntity;
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

    public ResponseEntity<PatientTO> getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patient -> ResponseEntity.ok(patientMapper.toTO(patient)))  // Jeśli pacjent znaleziony, zwróć 200 OK z obiektem
                .orElseGet(() -> ResponseEntity.status(404).build());  // Jeśli pacjent nie znaleziony, zwróć 404 Not Found
    }

    public void deletePatient(Long id) {
        // Sprawdzamy, czy pacjent istnieje
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found");
        }
        
        // Usuwamy pacjenta
        patientRepository.deleteById(id);
    }

    public PatientTO addPatient(PatientTO patientTO) {
        // Mapujemy DTO na encję
        PatientEntity patientEntity = patientMapper.toEntity(patientTO);
        
        // Zapisujemy pacjenta w bazie
        PatientEntity savedPatient = patientRepository.save(patientEntity);
        
        // Zwracamy zapisany pacjent w postaci DTO
        return patientMapper.toTO(savedPatient);
    }

}