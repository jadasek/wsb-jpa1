package com.jpacourse.mapper;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorTO toTO(DoctorEntity doctorEntity) {
        DoctorTO doctorTO = new DoctorTO();
        doctorTO.setId(doctorEntity.getId());
        doctorTO.setFirstName(doctorEntity.getFirstName());
        doctorTO.setLastName(doctorEntity.getLastName());
        doctorTO.setTelephoneNumber(doctorEntity.getTelephoneNumber());
        doctorTO.setEmail(doctorEntity.getEmail());
        doctorTO.setDoctorNumber(doctorEntity.getDoctorNumber());
        doctorTO.setSpecialization(doctorEntity.getSpecialization());
        
        // Mapowanie adresu - jeśli adres jest częścią DTO
        if (doctorEntity.getAddress() != null) {
            doctorTO.setAddress(doctorEntity.getAddress().getId()); // Mapowanie tylko ID
        }
        
        return doctorTO;
    }

    public DoctorEntity toEntity(DoctorTO doctorTO) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName(doctorTO.getFirstName());
        doctorEntity.setLastName(doctorTO.getLastName());
        doctorEntity.setTelephoneNumber(doctorTO.getTelephoneNumber());
        doctorEntity.setEmail(doctorTO.getEmail());
        doctorEntity.setDoctorNumber(doctorTO.getDoctorNumber());
        doctorEntity.setSpecialization(doctorTO.getSpecialization());

        if (doctorTO.getAddress() != null) {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setId(doctorTO.getAddress());
            doctorEntity.setAddress(addressEntity);
        }

        return doctorEntity;
    }
}
