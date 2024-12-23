package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Repository
public class PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public PatientEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String visitDescription) {
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Invalid patient or doctor ID");
        }

        VisitEntity visit = new VisitEntity();
        visit.setTime(visitTime); 
        visit.setDescription(visitDescription);
        visit.setDoctor(doctor);
        visit.setPatient(patient);

        // Dodajemy wizytę do pacjenta
        patient.getVisits().add(visit);

        // Używamy kaskadowego merge, aby zapisać pacjenta z nową wizytą
        entityManager.merge(patient);

        // Zwracamy zaktualizowanego pacjenta
        return patient;
    }
}

//persist: Tylko dla nowych obiektów.
//merge: Dla nowych lub już istniejących obiektów, które mogą być poza sesją i wymagają synchronizacji ze stanem bazy danych.
