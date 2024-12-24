package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public List<PatientEntity> findPatientsByLastName(String lastName) {
        String jpql = "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName";
        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Transactional
    public List<VisitEntity> findVisitsByPatientId(Long patientId) {
        String jpql = "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId";
        TypedQuery<VisitEntity> query = entityManager.createQuery(jpql, VisitEntity.class);
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    @Transactional
    public List<PatientEntity> findPatientsWithMoreThanXVisits(int visitCount) {
        String jpql = "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :visitCount";
        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("visitCount", visitCount);
        return query.getResultList();
    }

    @Transactional
    public List<PatientEntity> findPatientsBornAfter(LocalDate date) {
        String jpql = "SELECT p FROM PatientEntity p WHERE p.dateOfBirth > :date";
        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}
