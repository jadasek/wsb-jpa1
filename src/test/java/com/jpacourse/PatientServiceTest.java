package com.jpacourse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import com.jpacourse.repository.DoctorRepository;
import com.jpacourse.repository.PatientRepository;
import com.jpacourse.repository.VisitRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc  // Dodajemy tę adnotację, aby MockMvc był dostępny
public class PatientServiceTest {

    @Autowired
    private MockMvc mockMvc;  // Bean MockMvc będzie automatycznie dostępny dzięki @AutoConfigureMockMvc

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private PatientEntity patient;
    private VisitEntity visit;
    private DoctorEntity doctor;

    @BeforeEach
    public void setUp() {
        // Przygotowanie danych testowych

        // Dodajemy pacjenta
        patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setTelephoneNumber("123456789");
        patient.setInsuranceNumber(123456);
        patient.setEmail("john.doe@example.com");
        patient.setPatientNumber("P12345");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient = patientRepository.saveAndFlush(patient);

        // Dodajemy lekarza
        doctor = new DoctorEntity();
        doctor.setFirstName("Dr.Smith");
        doctor.setLastName("Johnson");
        doctor.setTelephoneNumber("987654321");
        doctor.setEmail("dr.smith@example.com");
        doctor.setDoctorNumber("D12345");
        doctor.setSpecialization(Specialization.CARDIOLOGY);
        doctor = doctorRepository.saveAndFlush(doctor);

        // Dodajemy wizytę
        visit = new VisitEntity();
        visit.setDescription("Routine checkup");
        visit.setTime(LocalDateTime.now());
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visitRepository.saveAndFlush(visit);
    }

    @Test  // Zapewnia zatwierdzenie transakcji po teście
    public void testDeletePatient_ShouldCascadeDeleteVisits_AndNotDeleteDoctors() throws Exception {
        // when
        // Usuwamy pacjenta za pomocą endpointu
        mockMvc.perform(delete("/api/patients/{id}", patient.getId()))
               .andExpect(status().isNoContent());  // Oczekujemy statusu 204 (No Content)
    
        // then
        // Sprawdzamy, czy pacjent i wizyta zostały usunięte
        patient = patientRepository.findById(patient.getId()).orElse(null);  // Pobieramy pacjenta
        assertThat(patient).isNull();  // Pacjent nie powinien istnieć

        // Sprawdzamy, czy wizyta została usunięta
        VisitEntity visitFromDb = visitRepository.findById(visit.getId()).orElse(null); // Pobieramy wizytę
        assertThat(visitFromDb).isNull();  // Wizyta powinna być usunięta

        // Sprawdzamy, czy lekarz nie został usunięty
        DoctorEntity doctorFromDb = doctorRepository.findById(doctor.getId()).orElse(null);  // Pobieramy lekarza
        assertThat(doctorFromDb).isNotNull();  // Lekarz powinien istnieć
    }
    
}
