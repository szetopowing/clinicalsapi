package com.clarence.clinicals.controller.util;

import java.util.List;

import com.clarence.clinicals.model.ClinicalData;

public class BMICalculator {
	
	public static void calculateBMI(List<ClinicalData> clinicalDataList, ClinicalData clinicalData) {
		if (clinicalData.getComponentName().equals("hw")) {
			String[] heightAndWeight = clinicalData.getComponentValue().split("/");
			
			if (heightAndWeight != null  && heightAndWeight.length > 1) {
				float heightInMetres = Float.parseFloat(heightAndWeight[0])*0.4536F;
				float BMI = Float.parseFloat(heightAndWeight[1])/(heightInMetres * heightInMetres);
				ClinicalData bmiData = new ClinicalData();
				bmiData.setComponentName("bmi");
				bmiData.setComponentValue(Float.toString(BMI));
				clinicalDataList.add(bmiData);
			}
			
		}
	}

}
