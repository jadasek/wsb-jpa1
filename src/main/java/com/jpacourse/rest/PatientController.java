package com.jpacourse.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public PatientTO getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);  // Usuwamy pacjenta, metoda nie zwraca wartości
            return ResponseEntity.noContent().build();  // Zwracamy status 204 (No Content) w przypadku sukcesu
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // Zwracamy status 404 (Not Found), jeśli pacjent nie został znaleziony
        }
    }

    @PostMapping
    public ResponseEntity<PatientTO> addPatient(@RequestBody PatientTO patientTO) {
        PatientTO savedPatient = patientService.addPatient(patientTO);
        return ResponseEntity.status(201).body(savedPatient);  // Zwracamy status 201 (Created) wraz z zapisanym pacjentem
    }
}