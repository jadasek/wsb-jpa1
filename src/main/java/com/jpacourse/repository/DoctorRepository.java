package com.jpacourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpacourse.persistence.entity.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}


