package com.example.Insurance.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Insurance.model.Insurance;
import com.example.Insurance.model.exception.ResourceNotFoundException;
import com.example.Insurance.repositoy.InsuranceRepository;
import com.example.Insurance.security.MyUserDetails;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class InsuranceController {
	
	@Autowired
	InsuranceRepository insuranceRepository;
	
	@GetMapping("/insurance")
	public List<Insurance> getAllEmployeess() {

        return (List<Insurance>) insuranceRepository.findAll();
		// return null;0
	}
	
	
	@GetMapping("/insurance/searchInsurance/{policyTypeCode}")
	public List<Insurance> getAllEmployeesByFirstName(@PathVariable (name="policyTypeCode") String policyTypeCode) {
		System.out.println("start inside getAllEmployeesByFirstName");

		//System.out.println(employeeRepository.getEmployeeByFirstName(firstName));
		System.out.println("inside serachEmployees");
		return insuranceRepository.getInsuranceByPolicyTypeCode(policyTypeCode);
		// return null;
	}
	@PostMapping("/insurance")
	public Insurance createEmployee(@RequestBody Insurance insurance) {
		
		
		return insuranceRepository.save(insurance);
	}

	@PutMapping("/insurance/{id}")
	public ResponseEntity<Insurance> updateEmployee(@PathVariable(value = "id") Long insuranceId,

			 @RequestBody Insurance insurance) throws ResourceNotFoundException {
		Insurance insurances = insuranceRepository.findById(insuranceId)
				.orElseThrow(() -> new ResourceNotFoundException("Insurance not found for this id :: " + insuranceId));

		insurances.setAmount(insurance.getAmount());
		insurances.setMaturityAmount(insurance.getMaturityAmount());
		insurances.setValidity(insurance.getValidity());
		
		insurances.setPolicyTypeCode(insurance.getPolicyTypeCode());
		insurances.setPolicyName(insurance.getPolicyName());

		insurances.setDescription(insurance.getDescription());
		insurances.setYearsOfPayment(insurance.getYearsOfPayment());
		insurances.setMaturityPariod(insurance.getMaturityPariod());

		
		final Insurance updateInsurance = insuranceRepository.save(insurance);
		return ResponseEntity.ok(updateInsurance);
	}

	@DeleteMapping("/insurance/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long insuranceId)
			throws ResourceNotFoundException {
		Insurance employee = insuranceRepository.findById(insuranceId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + insuranceId));

		insuranceRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	@GetMapping("/insurance/{id}")
	public ResponseEntity<Insurance> getEmployeeById(@PathVariable(value = "id") Long insuranceId)
			throws ResourceNotFoundException {
		Insurance Insurance = insuranceRepository.findById(insuranceId)
				.orElseThrow(() -> new ResourceNotFoundException("Insurance not found for this id :: " + insuranceId));
		return ResponseEntity.ok().body(Insurance);
	}
	
}
