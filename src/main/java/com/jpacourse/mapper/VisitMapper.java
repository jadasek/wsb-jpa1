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
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctorId(visitEntity.getDoctor().getId());  // Zamiast imienia i nazwiska, przypisujemy ID lekarza
        visitTO.setTreatmentTypes(
            visitEntity.getTreatment().stream()
                       .map(MedicalTreatmentEntity::getType)
                       .collect(Collectors.toList())
        );
        return visitTO;
    }

    // Mapowanie z VisitTO do VisitEntity
    public VisitEntity toEntity(VisitTO visitTO, DoctorEntity doctor, PatientEntity patient) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setTime(visitTO.getTime());
        visitEntity.setDoctor(doctor);
        visitEntity.setPatient(patient);
        // Logika ustawiania procedur medycznych, jeśli są
        visitEntity.setTreatment(
            visitTO.getTreatmentTypes().stream()
                   .map(type -> new MedicalTreatmentEntity())
                   .collect(Collectors.toList())
        );
        return visitEntity;
    }
}
