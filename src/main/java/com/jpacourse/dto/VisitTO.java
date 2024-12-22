package com.jpacourse.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.jpacourse.persistence.enums.TreatmentType;

public class VisitTO {

    private LocalDateTime time;
    private String doctorName;
    private List<TreatmentType> treatmentTypes;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public List<TreatmentType> getTreatmentTypes() {
        return treatmentTypes;
    }

    public void setTreatmentTypes(List<TreatmentType> treatmentTypes) {
        this.treatmentTypes = treatmentTypes;
    }
}