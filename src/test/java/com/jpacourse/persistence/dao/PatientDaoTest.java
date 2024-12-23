package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private EntityManager entityManager;

    private PatientEntity patient;
    private DoctorEntity doctor;

    @BeforeEach
    public void setUp() {
        // Tworzymy pacjenta
        patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setTelephoneNumber("123456789");
        patient.setEmail("john.doe@example.com");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1)); // Ustaw odpowiednią datę
        patient.setInsuranceNumber(123411); // Ustawienie numeru ubezpieczenia
        patient.setPatientNumber("AB1211");


        // Tworzymy doktora
        doctor = new DoctorEntity();
        doctor.setFirstName("Dr. Jane");
        doctor.setLastName("Smith");
        doctor.setSpecialization(Specialization.CARDIOLOGY);  // Użyj enum, a nie String
        doctor.setTelephoneNumber("987654321");
        doctor.setDoctorNumber("AI9199");

        // Zapisujemy pacjenta i doktora
        entityManager.persist(patient);
        entityManager.persist(doctor);
    }

    @Test
    @Transactional
    public void testAddVisitToPatient() {
        // Przygotowujemy czas wizyty
        LocalDateTime visitTime = LocalDateTime.of(2024, 12, 23, 10, 0);

        // Wywołanie metody z parametrami
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
}
