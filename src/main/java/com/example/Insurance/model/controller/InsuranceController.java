package com.example.Insurance.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.Insurance.security.InsuranceService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class InsuranceController {
	
	Logger logger = LoggerFactory.getLogger(InsuranceController.class);

	
	@Autowired
	InsuranceService insuranceService;
	
	@GetMapping("/insurance/Users/{userId}")
	public ResponseEntity<List<Insurance>> getAllInsurance(@PathVariable (name="userId") long userId) {

		logger.info("Inside getAllInsurance"+userId);

		  if(userId==5) { 
			  			  
			  return new ResponseEntity<>(insuranceService.findAll(), HttpStatus.OK);
		  }
		  else { 
			  return new ResponseEntity<>(insuranceService.findByUserId(userId), HttpStatus.OK);
			  
  
		  
		  }
		
	}
	
	
	@GetMapping("/insurance/searchInsurance/{policyTypeCode}")
	public ResponseEntity<List<Insurance>> getAllInsuranceByPolicyTypeCode(@PathVariable (name="policyTypeCode") String policyTypeCode) {
		
		logger.info("Inside getAllEmployeesByFirstName"+policyTypeCode);

	//	return insuranceService.getInsuranceByPolicyTypeCode(policyTypeCode);
		
		  return new ResponseEntity<>(insuranceService.getInsuranceByPolicyTypeCode(policyTypeCode), HttpStatus.OK);
		
	}
	@PostMapping("/insurance")
	public ResponseEntity<Insurance> createInsurance(@RequestBody Insurance insurance) {
		logger.info("Inside createInsurance"+insurance);

		//return insuranceService.save(insurance);
		return ResponseEntity.ok(insuranceService.save(insurance));
	}
	
	@GetMapping("/insurance/getInsuranceId")
	public Long getInsuranceId()
	{
		
		logger.info("Inside getInsuranceId");

		Insurance insurance= insuranceService.getInsuranceId();
		
		return insurance.getId() +1;
		
	}

	@PutMapping("/insurance/{id}")
	public ResponseEntity<Insurance> updateInsurance(@PathVariable(value = "id") Long insuranceId,

			 @RequestBody Insurance insurance) throws ResourceNotFoundException {
		
		logger.info("Inside UpdateEmployee",insuranceId);

		Insurance insurances = insuranceService.findById(insuranceId);

		insurances.setAmount(insurance.getAmount());
		insurances.setMaturityAmount(insurance.getMaturityAmount());
		insurances.setValidity(insurance.getValidity());
		
		insurances.setPolicyTypeCode(insurance.getPolicyTypeCode());
		insurances.setPolicyName(insurance.getPolicyName());

		insurances.setDescription(insurance.getDescription());
		insurances.setYearsOfPayment(insurance.getYearsOfPayment());
		insurances.setMaturityPariod(insurance.getMaturityPariod());

		
		final Insurance updateInsurance = insuranceService.save(insurance);
		return ResponseEntity.ok(updateInsurance);
	}

	@DeleteMapping("/insurance/{id}")
	public Map<String, Boolean> deleteInsurance(@PathVariable(value = "id") Long insuranceId)
			throws ResourceNotFoundException {
		
		logger.info("Inside deleteEmployee", insuranceId);

		Insurance insurance = insuranceService.findById(insuranceId);

		insuranceService.delete(insurance);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	@GetMapping("/insurance/{id}")
	public ResponseEntity<Insurance> getInsuranceId(@PathVariable(value = "id") Long insuranceId)
			throws ResourceNotFoundException {
		
		logger.info("Inside getEmployeeById");

		Insurance insurance = insuranceService.findById(insuranceId);
		return ResponseEntity.ok().body(insurance);
	}
	
}
