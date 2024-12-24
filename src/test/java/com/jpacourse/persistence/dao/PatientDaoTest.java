package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import com.jpacourse.service.PatientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private PatientService patientService;
    @Autowired
    private EntityManager entityManager;

    private PatientEntity patient;
    private DoctorEntity doctor;

    @BeforeEach
    public void setUp() {
        patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setTelephoneNumber("123456789");
        patient.setEmail("john.doe@example.com");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1)); 
        patient.setInsuranceNumber(123411); 
        patient.setPatientNumber("AB1211");

        doctor = new DoctorEntity();
        doctor.setFirstName("Dr. Jane");
        doctor.setLastName("Smith");
        doctor.setSpecialization(Specialization.CARDIOLOGY);  
        doctor.setTelephoneNumber("987654321");
        doctor.setDoctorNumber("AI9199");

        // Zapisujemy pacjenta i doktora
        entityManager.persist(patient);
        entityManager.persist(doctor);
    }

    @Test
    @Transactional
    public void testAddVisitToPatient() {
        LocalDateTime visitTime = LocalDateTime.of(2024, 12, 23, 10, 0);
        patient = patientDao.addVisitToPatient(patient.getId(), doctor.getId(), visitTime, "Routine Checkup");

        // Sprawdzamy, czy wizyta została dodana
        assertNotNull(patient.getVisits());
        assertEquals(1, patient.getVisits().size());

        VisitEntity visit = patient.getVisits().get(0);
        assertEquals(visitTime, visit.getTime());  // Sprawdzamy, czy czas wizyty jest poprawny
        assertEquals("Routine Checkup", visit.getDescription());
        assertEquals(doctor, visit.getDoctor());
        assertEquals(patient, visit.getPatient());
    }

    @Test
    public void testFindPatientsByLastName() {
        List<PatientEntity> patients = patientDao.findPatientsByLastName("Brown");

        assertNotNull(patients);
        assertFalse(patients.isEmpty());
    }

    @Test
    public void testFindAllVisitsByPatientId() {
        Long patientId = 1L;
        List<VisitEntity> visits = patientService.findAllVisitsByPatientId(patientId);

        assertNotNull(visits);
        assertFalse(visits.isEmpty());
        assertTrue(visits.stream().allMatch(visit -> visit.getPatient().getId().equals(patientId)));
    }

    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        int visitCount = 2; 
        List<PatientEntity> patients = patientDao.findPatientsWithMoreThanXVisits(visitCount);

        assertNotNull(patients);
        assertFalse(patients.isEmpty());
        assertTrue(patients.stream().allMatch(patient -> patient.getVisits().size() > visitCount));
    }

    @Test
    public void testFindPatientsBornAfter() {
        LocalDate date = LocalDate.of(1990, 1, 1); 
        List<PatientEntity> patients = patientDao.findPatientsBornAfter(date);

        assertNotNull(patients);
        assertFalse(patients.isEmpty());
        assertTrue(patients.stream().allMatch(patient -> patient.getDateOfBirth().isAfter(date)));
    }

    @Test
    public void testOptimisticLocking() {
        // Pobranie pacjenta z bazy danych
        PatientEntity patient = entityManager.find(PatientEntity.class, 1L);
    
        // Symulacja innej transakcji
        entityManager.detach(patient);
        PatientEntity patientInOtherTransaction = entityManager.find(PatientEntity.class, 1L);
    
        // Zmiana w "pierwszej" transakcji
        patient.setLastName("pierwszy");
        entityManager.merge(patient);
        entityManager.flush(); // Zapis do bazy danych
    
        // Zmiana w "drugiej" transakcji
        patientInOtherTransaction.setLastName("drugi");
    
        // Oczekiwanie na OptimisticLockException przy zapisie drugiej transakcji
        assertThrows(OptimisticLockException.class, () -> {
            entityManager.merge(patientInOtherTransaction);
            entityManager.flush();
        });
    }

}
