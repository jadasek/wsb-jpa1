package com.jpacourse.mapper;

import com.jpacourse.dto.MedicalTreatmentTO;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import org.springframework.stereotype.Component;

@Component
public class MedicalTreatmentMapper {

    public MedicalTreatmentEntity toEntity(MedicalTreatmentTO treatmentTO) {
        MedicalTreatmentEntity treatmentEntity = new MedicalTreatmentEntity();
        treatmentEntity.setDescription(treatmentTO.getDescription());
        treatmentEntity.setType(treatmentTO.getType());  
        return treatmentEntity;
    }

    public MedicalTreatmentTO toTO(MedicalTreatmentEntity treatmentEntity) {
        MedicalTreatmentTO treatmentTO = new MedicalTreatmentTO();
        treatmentTO.setId(treatmentEntity.getId());
        treatmentTO.setDescription(treatmentEntity.getDescription());
        treatmentTO.setType(treatmentEntity.getType());  
        return treatmentTO;
    }
}
