package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    // Mapowanie z VisitEntity do VisitTO
    public VisitTO toTO(VisitEntity visitEntity) {
        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctorId(visitEntity.getDoctor().getId());  // Zamiast imienia i nazwiska, przypisujemy ID lekarza
        visitTO.setPatientId(visitEntity.getPatient().getId());  // Zamiast obiektu pacjenta, przypisujemy ID pacjenta
        visitTO.setTreatmentTypes(
            visitEntity.getTreatment().stream()
                       .map(MedicalTreatmentEntity::getType)
                       .collect(Collectors.toList())
        );
        
        // Mapowanie descriptionTreatment z MedicalTreatmentEntity (jeśli istnieje)
        if (!visitEntity.getTreatment().isEmpty()) {
            visitTO.setDescriptionTreatment(visitEntity.getTreatment().get(0).getDescription());  // Przykład: używamy opisu z pierwszego leczenia
        }

        visitTO.setDescription(visitEntity.getDescription());  // Mapowanie opisu wizyty
        return visitTO;
    }

    // Mapowanie z VisitTO do VisitEntity
    public VisitEntity toEntity(VisitTO visitTO, DoctorEntity doctor, PatientEntity patient) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setTime(visitTO.getTime());
        visitEntity.setDoctor(doctor);
        visitEntity.setPatient(patient);
        
        // Mapowanie leczenia
        List<MedicalTreatmentEntity> treatments = visitTO.getTreatmentTypes().stream()
            .map(type -> {
                MedicalTreatmentEntity treatment = new MedicalTreatmentEntity();
                treatment.setType(type);
                treatment.setVisit(visitEntity);  // Ustawienie odwrotnej strony relacji
                return treatment;
            })
            .collect(Collectors.toList());
        visitEntity.setTreatment(treatments);

        // Mapowanie opisu wizyty
        visitEntity.setDescription(visitTO.getDescription());
        
        // Jeśli opis leczenia jest ustawiony w VisitTO, przypisz go do odpowiedniego leczenia
        if (visitTO.getDescriptionTreatment() != null && !visitEntity.getTreatment().isEmpty()) {
            visitEntity.getTreatment().get(0).setDescription(visitTO.getDescriptionTreatment());  // Przykład: ustawiamy opis leczenia na pierwsze leczenie
        }

        return visitEntity;
    }
}


