package com.jpacourse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jpacourse.dto.DoctorTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.enums.Specialization;
import com.jpacourse.persistence.enums.TreatmentType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientCreationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testCreatePatient() throws Exception {
        PatientTO patientTO = new PatientTO();
        patientTO.setFirstName("Adam");
        patientTO.setLastName("Kowalski");
        patientTO.setTelephoneNumber("123456789");
        patientTO.setInsuranceNumber(123456);
        patientTO.setEmail("adam.kowalski@example.com");
        patientTO.setPatientNumber("P12345");
        patientTO.setDateOfBirth(LocalDate.parse("1980-01-01"));
    
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    
        // Wykonanie żądania i przechwycenie wyniku
        String responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    
        // Przekształcenie odpowiedzi na obiekt PatientTO
        PatientTO createdPatient = objectMapper.readValue(responseContent, PatientTO.class);
    
        // Użycie pacjenta w kolejnych testach
        assertThat(createdPatient).isNotNull();
        assertThat(createdPatient.getId()).isNotNull();  // Sprawdzamy, czy ID zostało przypisane
    }
    
    @Test
    @Order(2)
    public void testCreateDoctor() throws Exception {
        DoctorTO doctorTO = new DoctorTO();
        doctorTO.setFirstName("Jan");
        doctorTO.setLastName("Nowak");
        doctorTO.setTelephoneNumber("987654321");
        doctorTO.setEmail("jan.nowak@example.com");
        doctorTO.setDoctorNumber("D54321");
        doctorTO.setSpecialization(Specialization.CARDIOLOGY);
    
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    
        // Wykonanie żądania i przechwycenie wyniku
        String responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    
        // Przekształcenie odpowiedzi na obiekt DoctorTO
        DoctorTO createdDoctor = objectMapper.readValue(responseContent, DoctorTO.class);
    
        // Użycie lekarza w kolejnych testach
        assertThat(createdDoctor).isNotNull();
        assertThat(createdDoctor.getId()).isNotNull();  // Sprawdzamy, czy ID zostało przypisane
    }
    
    @Test
    @Order(3)
    public void testCreateandRemoveVisit() throws Exception {
        // Tworzymy pacjenta
        PatientTO patientTO = new PatientTO();
        patientTO.setFirstName("Adam");
        patientTO.setLastName("Kowalski");
        patientTO.setTelephoneNumber("123456789");
        patientTO.setInsuranceNumber(123456);
        patientTO.setEmail("adam.kowalski@example.com");
        patientTO.setPatientNumber("P12345");
        patientTO.setDateOfBirth(LocalDate.parse("1980-01-01"));
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // Wywołanie POST do dodania pacjenta
        String patientResponseContent = mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        // Przekształcamy odpowiedź na obiekt PatientTO
        PatientTO createdPatient = objectMapper.readValue(patientResponseContent, PatientTO.class);
    
        // Tworzymy lekarza
        DoctorTO doctorTO = new DoctorTO();
        doctorTO.setFirstName("Jan");
        doctorTO.setLastName("Nowak");
        doctorTO.setTelephoneNumber("987654321");
        doctorTO.setEmail("jan.nowak@example.com");
        doctorTO.setDoctorNumber("D54321");
        doctorTO.setSpecialization(Specialization.CARDIOLOGY);
        
        // Wywołanie POST do dodania lekarza
        String doctorResponseContent = mockMvc.perform(MockMvcRequestBuilders.post("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        // Przekształcamy odpowiedź na obiekt DoctorTO
        DoctorTO createdDoctor = objectMapper.readValue(doctorResponseContent, DoctorTO.class);
        
        assertNotNull(createdPatient.getId());
        assertNotNull(createdDoctor.getId());

    // Tworzymy wizytę
    VisitTO visitTO = new VisitTO();
    visitTO.setTime(LocalDateTime.of(2024, 12, 22, 10, 30));  // Dodajemy czas
    visitTO.setDoctorId(createdDoctor.getId());  // Używamy ID nowo utworzonego lekarza
    visitTO.setPatientId(createdPatient.getId());  // Używamy ID nowo utworzonego pacjenta
    visitTO.setDescription("Routine checkup and vaccination");  // Dodajemy opis wizyty
    visitTO.setDescriptionTreatment("aaaaa");  // Dodajemy opis leczenia
    visitTO.setTreatmentTypes(List.of(TreatmentType.EKG));

    // Wywołanie POST do dodania wizyty
    String visitResponseContent = mockMvc.perform(MockMvcRequestBuilders.post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(visitTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.time").value("2024-12-22T10:30:00"))  
            .andExpect(MockMvcResultMatchers.jsonPath("$.doctorId").value(createdDoctor.getId()))  
            .andExpect(MockMvcResultMatchers.jsonPath("$.patientId").value(createdPatient.getId()))  
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Routine checkup and vaccination")) 
            .andExpect(MockMvcResultMatchers.jsonPath("$.descriptionTreatment").value("aaaaa")) 
            .andExpect(MockMvcResultMatchers.jsonPath("$.treatmentTypes[0]").value(TreatmentType.EKG.name()))
            .andReturn()
            .getResponse()
            .getContentAsString();

    VisitTO createdVisit = objectMapper.readValue(visitResponseContent, VisitTO.class);

    // Wywołanie DELETE, aby usunąć pacjenta
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients/{id}", createdPatient.getId()))
    .andExpect(MockMvcResultMatchers.status().isNoContent());  // Oczekujemy statusu 204 No Content

    // Próba pobrania wizyty powinna zwrócić status 404, ponieważ pacjent został usunięty
    mockMvc.perform(MockMvcRequestBuilders.get("/api/visits/{id}", createdVisit.getId()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());  // Oczekujemy statusu 404 Not Found

    // Próba pobrania pacjenta powinna zwrócić status 404, ponieważ pacjent został usunięty
    mockMvc.perform(MockMvcRequestBuilders.get("/api/patients/{id}", createdPatient.getId()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());  // Oczekujemy statusu 404 Not Found
        
    // Próba pobrania doktora powinna zwrócić status 200, ponieważ doktor nie jest usuwany wraz z pacjentem
    mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/{id}", createdDoctor.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk());  // Oczekujemy statusu 200 OK
    }
}


