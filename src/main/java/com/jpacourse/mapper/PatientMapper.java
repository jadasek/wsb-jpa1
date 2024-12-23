package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;


import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PatientMapper {

    public PatientTO toTO(PatientEntity patient) {
        PatientTO to = new PatientTO();
        to.setId(patient.getId());
        to.setFirstName(patient.getFirstName());
        to.setLastName(patient.getLastName());
        to.setTelephoneNumber(patient.getTelephoneNumber());
        to.setEmail(patient.getEmail());
        to.setPatientNumber(patient.getPatientNumber());
        to.setDateOfBirth(patient.getDateOfBirth());
        to.setInsuranceNumber(patient.getInsuranceNumber());

        List<VisitTO> visitTOs = patient.getVisits().stream().map(visit -> {
            VisitTO visitTO = new VisitTO();
            visitTO.setId(visit.getId());
            visitTO.setPatientId(visit.getPatient().getId());
            visitTO.setDescription(visit.getDescription());
            visitTO.setDescriptionTreatment(visit.getDescriptionTreatment());
            visitTO.setDoctorFirstName(visit.getDoctor().getFirstName());
            visitTO.setDoctorLastName(visit.getDoctor().getLastName());
            visitTO.setTime(visit.getTime());
            visitTO.setDoctorId(visit.getDoctor().getId()); 
            visitTO.setTreatmentTypes(
                    visit.getTreatment().stream().map(MedicalTreatmentEntity::getType).toList()
            );
            return visitTO;
        }).toList();

        to.setVisits(visitTOs);
        return to;
    }

    // Mapowanie z PatientTO do PatientEntity
    public PatientEntity toEntity(PatientTO patientTO) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setInsuranceNumber(patientTO.getInsuranceNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        return patientEntity;
    }
}
