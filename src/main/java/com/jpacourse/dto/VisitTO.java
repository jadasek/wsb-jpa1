package com.jpacourse.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.jpacourse.persistence.enums.TreatmentType;

public class VisitTO {
    private Long id;
 
    private LocalDateTime time;
    private Long doctorId; 
    private Long patientId; 
    private List<TreatmentType> treatmentTypes; 
    private String description; 
    private String descriptionTreatment;

    // Nowe pola
    private String doctorFirstName;
    private String doctorLastName;

    // Gettery i settery
    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public List<TreatmentType> getTreatmentTypes() {
        return treatmentTypes;
    }

    public void setTreatmentTypes(List<TreatmentType> treatmentTypes) {
        this.treatmentTypes = treatmentTypes;
    }

    public String getDescriptionTreatment() {
        return descriptionTreatment;
    }

    public void setDescriptionTreatment(String descriptionTreatment) {
        this.descriptionTreatment = descriptionTreatment;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
