package com.clarence.clinicals.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clarence.clinicals.controller.util.BMICalculator;
import com.clarence.clinicals.model.ClinicalData;
import com.clarence.clinicals.model.Patient;
import com.clarence.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	
	@Autowired
	PatientRepository patientRepository;
	
	Map<String, String> filter = new HashMap<String, String>();
	
	@GetMapping(value="/patients")
	public List<Patient> getPatients(){
		return patientRepository.findAll();
	}
	
	@GetMapping(value="/patients/{id}")
	public Patient getPatient(@PathVariable("id") int id){
		return patientRepository.findById(id).get();
	}
	
	@PostMapping(value="/patients")
	public Patient savePatient(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	}
	
	@GetMapping(value="/patients/analyze/{id}")
	public Patient analyze(@PathVariable("id") int id) {
		Patient patient = patientRepository.findById(id).get();
		List<ClinicalData> clinicalDataList = patient.getClinicalData();
		ArrayList<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalDataList);
		for (ClinicalData clinicalData: duplicateClinicalData) {
			
			if (filter.containsKey(clinicalData.getComponentName())) {
				clinicalDataList.remove(clinicalData);
				continue;
			} else {
				filter.put(clinicalData.getComponentName(), null);
			}
			
			BMICalculator.calculateBMI(clinicalDataList, clinicalData);
		}
		filter.clear();
		return patient;
	}

	

}
