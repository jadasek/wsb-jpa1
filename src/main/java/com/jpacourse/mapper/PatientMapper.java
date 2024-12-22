package com.jpacourse.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;

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
            visitTO.setTime(visit.getTime());
            visitTO.setDoctorName(visit.getDoctor().getFirstName() + " " + visit.getDoctor().getLastName());
            visitTO.setTreatmentTypes(
                    visit.getTreatment().stream().map(MedicalTreatmentEntity::getType).toList()
            );
            return visitTO;
        }).toList();

        to.setVisits(visitTOs);
        return to;
    }
}