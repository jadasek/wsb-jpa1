package com.jpacourse.rest;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitTO> createVisit(@RequestBody VisitTO visitTO) {
        // Wywołanie serwisu do dodania wizyty
        VisitTO createdVisitTO = visitService.createVisit(visitTO.getDoctorId(), visitTO.getPatientId(), visitTO);

        // Zwrócenie odpowiedzi z nową wizytą
        return ResponseEntity.ok(createdVisitTO);
    }
}
