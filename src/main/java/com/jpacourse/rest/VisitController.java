package com.jpacourse.rest;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;

    // Endpoint do tworzenia wizyty
    @PostMapping
    public ResponseEntity<VisitTO> createVisit(@RequestBody VisitTO visitTO) {
       
        VisitTO createdVisitTO = visitService.createVisit(visitTO.getDoctorId(), visitTO.getPatientId(), visitTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisitTO);
    }

    // Endpoint do pobierania wizyty po ID
    @GetMapping("/{id}")
    public ResponseEntity<VisitTO> getVisit(@PathVariable Long id) {
        VisitTO visitTO = visitService.getVisitById(id);

        if (visitTO == null) {
            // Jeśli nie znaleziono wizyty, zwróć 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Zwrócenie znalezionej wizyty
        return ResponseEntity.ok(visitTO);
    }
}
