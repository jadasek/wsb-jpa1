package com.jpacourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpacourse.persistence.entity.VisitEntity;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
    void deleteAllByPatientId(Long patientId);
}


