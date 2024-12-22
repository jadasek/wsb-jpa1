package com.jpacourse.rest;


import com.jpacourse.dto.DoctorTO;
import com.jpacourse.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorTO> addDoctor(@RequestBody DoctorTO doctorTO) {
        DoctorTO createdDoctor = doctorService.addDoctor(doctorTO);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }
}

