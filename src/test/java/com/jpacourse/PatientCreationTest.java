package com.jpacourse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.enums.Specialization;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientCreationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreatePatient() {
        // Tworzymy obiekt PatientTO
        PatientTO patientTO = new PatientTO();
        patientTO.setFirstName("Adam");
        patientTO.setLastName("Kowalski");
        patientTO.setTelephoneNumber("123456789");
        patientTO.setInsuranceNumber(123456);
        patientTO.setEmail("adam.kowalski@example.com");
        patientTO.setPatientNumber("P12345");
        patientTO.setDateOfBirth(LocalDate.of(1980, 1, 1));

        // Wywołanie endpointu POST, aby dodać pacjenta
        ResponseEntity<PatientTO> response = restTemplate.postForEntity("/api/patients", patientTO, PatientTO.class);

        // Sprawdzamy, czy pacjent został poprawnie dodany
        assertThat(response.getStatusCodeValue()).isEqualTo(201);  // Oczekujemy statusu 201 Created
        assertThat(response.getBody()).isNotNull();  // Oczekujemy, że odpowiedź nie będzie pusta

        // Zapisanie pacjenta z odpowiedzi, by sprawdzić jego ID
        PatientTO createdPatient = response.getBody();
        assertThat(createdPatient.getId()).isNotNull();  // Sprawdzamy, czy pacjent ma przypisane ID
        assertThat(createdPatient.getFirstName()).isEqualTo("Adam");  // Sprawdzamy imię pacjenta
        assertThat(createdPatient.getLastName()).isEqualTo("Kowalski");  // Sprawdzamy nazwisko pacjenta
    }

    @Test
    public void testCreateDoctor() {
        // Tworzymy obiekt DoctorTO
        DoctorTO doctorTO = new DoctorTO();
        doctorTO.setFirstName("Jan");
        doctorTO.setLastName("Nowak");
        doctorTO.setTelephoneNumber("987654321");
        doctorTO.setEmail("jan.nowak@example.com");
        doctorTO.setDoctorNumber("D54321");
        doctorTO.setSpecialization(Specialization.CARDIOLOGY);

        // Wywołanie endpointu POST, aby dodać lekarza
        ResponseEntity<DoctorTO> response = restTemplate.postForEntity("/api/doctors", doctorTO, DoctorTO.class);

        // Sprawdzamy, czy lekarz został poprawnie dodany
        assertThat(response.getStatusCodeValue()).isEqualTo(201);  // Oczekujemy statusu 201 Created
        assertThat(response.getBody()).isNotNull();  // Oczekujemy, że odpowiedź nie będzie pusta

        // Zapisanie lekarza z odpowiedzi, by sprawdzić jego ID
        DoctorTO createdDoctor = response.getBody();
        assertThat(createdDoctor.getId()).isNotNull();  // Sprawdzamy, czy lekarz ma przypisane ID
        assertThat(createdDoctor.getFirstName()).isEqualTo("Jan");  // Sprawdzamy imię lekarza
        assertThat(createdDoctor.getLastName()).isEqualTo("Nowak");  // Sprawdzamy nazwisko lekarza
        assertThat(createdDoctor.getSpecialization()).isEqualTo(Specialization.CARDIOLOGY);  // Sprawdzamy specjalizację lekarza
    }
}
