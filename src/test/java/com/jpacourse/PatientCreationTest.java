package com.jpacourse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpacourse.dto.DoctorTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.enums.Specialization;
import com.jpacourse.persistence.enums.TreatmentType;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientCreationTest {
    @Autowired
    private MockMvc mockMvc;

    private Long patientId;
    private Long doctorId;

    // @Test
    // public void testCreatePatient() throws Exception {
    //     // Przekazujemy dane pacjenta jako JSON
    //     String patientJson = "{" +
    //             "\"firstName\": \"Adam\"," +
    //             "\"lastName\": \"Kowalski\"," +
    //             "\"telephoneNumber\": \"123456789\"," +
    //             "\"insuranceNumber\": 123456," +
    //             "\"email\": \"adam.kowalski@example.com\"," +
    //             "\"patientNumber\": \"P12345\"," +
    //             "\"dateOfBirth\": \"1980-01-01\"" +
    //             "}";
    
    //     // Wywołanie endpointu POST, aby dodać pacjenta
    //     mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
    //                     .contentType(MediaType.APPLICATION_JSON)
    //                     .content(patientJson))  // Przekazujemy bezpośrednio JSON
    //             .andDo(MockMvcResultHandlers.print())
    //             .andExpect(MockMvcResultMatchers.status().isCreated())  // Oczekujemy statusu 201 Created
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Adam"))  // Sprawdzamy imię
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Kowalski"));  // Sprawdzamy nazwisko
    // }


    @Test
    public void testCreatePatient() throws Exception {
        // Tworzymy obiekt PatientTO

        
        PatientTO patientTO = new PatientTO();
        patientTO.setFirstName("Adam");
        patientTO.setLastName("Kowalski");
        patientTO.setTelephoneNumber("123456789");
        patientTO.setInsuranceNumber(123456);
        patientTO.setEmail("adam.kowalski@example.com");
        patientTO.setPatientNumber("P12345");
        patientTO.setDateOfBirth(LocalDate.parse("1980-01-01"));

        // Wywołanie endpointu POST, aby dodać pacjenta
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patientTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Oczekujemy statusu 201 Created
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Adam"))  // Sprawdzamy imię
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Kowalski"));  // Sprawdzamy nazwisko
    }

    @Test
    public void testCreateDoctor() throws Exception {
        // Tworzymy obiekt DoctorTO
        DoctorTO doctorTO = new DoctorTO();
        doctorTO.setFirstName("Jan");
        doctorTO.setLastName("Nowak");
        doctorTO.setTelephoneNumber("987654321");
        doctorTO.setEmail("jan.nowak@example.com");
        doctorTO.setDoctorNumber("D54321");
        doctorTO.setSpecialization(Specialization.CARDIOLOGY);

        // Wywołanie endpointu POST, aby dodać lekarza
        mockMvc.perform(MockMvcRequestBuilders.post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(doctorTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Oczekujemy statusu 201 Created
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jan"))  // Sprawdzamy imię
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Nowak"))  // Sprawdzamy nazwisko
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value(Specialization.CARDIOLOGY.name()));  // Sprawdzamy specjalizację
    }

    @Test
    public void testCreateVisit() throws Exception {
        // Tworzymy obiekt VisitTO
        VisitTO visitTO = new VisitTO();
        visitTO.setTime(LocalDateTime.of(2024, 12, 22, 10, 30));
        visitTO.setDoctorId(doctorId);  // Używamy wcześniej stworzonego ID lekarza
        visitTO.setPatientId(patientId);  // Używamy wcześniej stworzonego ID pacjenta
        visitTO.setDescription("Wizyta kontrolna");
        visitTO.setTreatmentTypes(List.of(TreatmentType.EKG));  // Ustawiamy listę procedur

        // Wywołanie endpointu POST, aby dodać wizytę
        mockMvc.perform(MockMvcRequestBuilders.post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(visitTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Oczekujemy statusu 201 Created
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value(visitTO.getTime().toString()))  // Sprawdzamy czas wizyty
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorId").value(doctorId))  // Sprawdzamy ID lekarza
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientId").value(patientId))  // Sprawdzamy ID pacjenta
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Wizyta kontrolna"))  // Sprawdzamy opis wizyty
                .andExpect(MockMvcResultMatchers.jsonPath("$.treatmentTypes[0]").value(TreatmentType.EKG.name()));  // Sprawdzamy procedury
    }

    @Test
    public void testDeletePatient() throws Exception {
        // Wywołanie endpointu DELETE, aby usunąć pacjenta
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients/{id}", patientId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());  // Oczekujemy statusu 204 No Content

        // Próba pobrania pacjenta powinna zwrócić status 404, ponieważ pacjent został usunięty
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patients/{id}", patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());  // Oczekujemy statusu 404 Not Found
    }
}
