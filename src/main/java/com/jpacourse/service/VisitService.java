package com.jpacourse.service;

import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.repository.VisitRepository;

import java.util.Optional;

import com.jpacourse.repository.DoctorRepository;
import com.jpacourse.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitMapper visitMapper;

    public VisitTO createVisit(Long doctorId, Long patientId, VisitTO visitTO) {
        // Znajdź lekarza i pacjenta w bazie danych
        DoctorEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        PatientEntity patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    
        // Stwórz nową wizytę
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setDoctor(doctor);
        visitEntity.setPatient(patient);
        visitEntity.setTime(visitTO.getTime());
        visitEntity.setDescription(visitTO.getDescription());

        // Logika dodawania procedur medycznych (jeśli istnieją)
        List<MedicalTreatmentEntity> treatments = visitTO.getTreatmentTypes().stream()
                .map(type -> {
                    MedicalTreatmentEntity treatment = new MedicalTreatmentEntity();
                    treatment.setType(type);  // Ustaw typ leczenia
                    treatment.setVisit(visitEntity);  // Powiąż leczenie z wizytą
                    treatment.setDescription(visitTO.getDescriptionTreatment());  // Opcjonalnie dodaj opis
                    return treatment;
                })
                .collect(Collectors.toList());
    
        // Ustaw procedury leczenia w wizycie
        visitEntity.setTreatment(treatments);
    
        // Zapisz wizytę w bazie danych
        visitRepository.save(visitEntity);
    
        // Zwróć przekształconą wizytę (do DTO)
        return visitMapper.toTO(visitEntity);
    }
    
    public VisitTO getVisitById(Long id) {
        // Pobieranie wizyty z bazy danych
        Optional<VisitEntity> visitEntity = visitRepository.findById(id);
    
        // Jeśli wizyta istnieje, mapujemy ją na VisitTO, w przeciwnym razie zwracamy null
        return visitEntity.map(visitMapper::toTO).orElse(null);  // Używamy mapera do konwersji
    }
    
    
}
