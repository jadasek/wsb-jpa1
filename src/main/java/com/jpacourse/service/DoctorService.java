package com.jpacourse.service;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.mapper.DoctorMapper;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    // Dodawanie lekarza
    public DoctorTO addDoctor(DoctorTO doctorTO) {
        // Mapujemy DTO na encję
        DoctorEntity doctorEntity = doctorMapper.toEntity(doctorTO);
        
        // Zapisujemy lekarza w bazie
        DoctorEntity savedDoctor = doctorRepository.save(doctorEntity);
        
        // Zwracamy zapisany lekarz w postaci DTO
        return doctorMapper.toTO(savedDoctor);
    }
}
