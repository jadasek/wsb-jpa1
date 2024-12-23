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

    public VisitTO toTO(VisitEntity visitEntity) {
        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctorId(visitEntity.getDoctor().getId());  
        visitTO.setPatientId(visitEntity.getPatient().getId());  
        visitTO.setTreatmentTypes(
            visitEntity.getTreatment().stream()
                       .map(MedicalTreatmentEntity::getType)
                       .collect(Collectors.toList())
        );
        
        // Mapowanie descriptionTreatment z MedicalTreatmentEntity (jeśli istnieje)
        if (!visitEntity.getTreatment().isEmpty()) {
            visitTO.setDescriptionTreatment(visitEntity.getTreatment().get(0).getDescription());  // Przykład: używamy opisu z pierwszego leczenia
        }

        visitTO.setDescription(visitEntity.getDescription()); 
        return visitTO;
    }

    // Mapowanie z VisitTO do VisitEntity
    public VisitEntity toEntity(VisitTO visitTO, DoctorEntity doctor, PatientEntity patient) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setTime(visitTO.getTime());
        visitEntity.setDoctor(doctor);
        visitEntity.setPatient(patient);
        
        List<MedicalTreatmentEntity> treatments = visitTO.getTreatmentTypes().stream()
            .map(type -> {
                MedicalTreatmentEntity treatment = new MedicalTreatmentEntity();
                treatment.setType(type);
                treatment.setVisit(visitEntity);  
                return treatment;
            })
            .collect(Collectors.toList());
        visitEntity.setTreatment(treatments);

        visitEntity.setDescription(visitTO.getDescription());
        
        // Jeśli opis leczenia jest ustawiony w VisitTO, przypisz go do odpowiedniego leczenia
        if (visitTO.getDescriptionTreatment() != null && !visitEntity.getTreatment().isEmpty()) {
            visitEntity.getTreatment().get(0).setDescription(visitTO.getDescriptionTreatment());  // Przykład: ustawiamy opis leczenia na pierwsze leczenie
        }

        return visitEntity;
    }
}


