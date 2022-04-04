package com.clarence.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clarence.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
