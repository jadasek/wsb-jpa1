package com.jpacourse.persistence.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private String descriptionTreatment;
	@Column(nullable = false)
	private LocalDateTime time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionTreatment() {
        return descriptionTreatment;
    }

    public void setDescriptionTreatment(String descriptionTreatment) {
        this.descriptionTreatment = descriptionTreatment;
    }
	
	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	// Relacja jednostronna: `VisitEntity` jest stroną dziecka (wskazuje właściciela relacji w bazie danych)

	@ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

	// Relacja jednostronna: `VisitEntity` jest stroną dziecka (wskazuje właściciela relacji w bazie danych)

	@ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}


	// Relacja dwustronna: `VisitEntity` jest stroną rodzica, `MedicalTreatmentEntity` jest dzieckiem (właścicielem relacji)
	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalTreatmentEntity> treatment = new ArrayList<>();

	public List<MedicalTreatmentEntity> getTreatment() {
		return treatment;
	}

	public void setTreatment(List<MedicalTreatmentEntity> treatment) {
		this.treatment = treatment;
	}



}
