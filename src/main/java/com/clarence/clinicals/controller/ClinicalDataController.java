package com.clarence.clinicals.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clarence.clinicals.controller.dtto.ClinicalDataRequest;
import com.clarence.clinicals.controller.util.BMICalculator;
import com.clarence.clinicals.model.ClinicalData;
import com.clarence.clinicals.model.Patient;
import com.clarence.clinicals.repos.ClinicalDataRepository;
import com.clarence.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	ClinicalDataRepository clinicalDataRepository;
	

	@PostMapping(value = "/clinicals")
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest clinicalDataRequest) {
		
		Patient patient = patientRepository.findById(clinicalDataRequest.getPatientId()).get();
		
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(clinicalDataRequest.getComponentName());
		clinicalData.setComponentValue(clinicalDataRequest.getComponentValue());
		clinicalData.setPatient(patient);
		
		return clinicalDataRepository.save(clinicalData);
	}
	
	@GetMapping(value = "/clinicals/{patientId}/{componentName}")
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId, @PathVariable("componentName") String componentName){
		
		if (componentName.equals("bmi")) {
			componentName = "hw";
		}
		List<ClinicalData> clinicalDataList = clinicalDataRepository.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId,componentName);
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalDataList);
		
		for (ClinicalData clinicalData : duplicateClinicalData) {
			BMICalculator.calculateBMI(clinicalDataList, clinicalData);
		}
		
		return clinicalDataList;		
	}
}
