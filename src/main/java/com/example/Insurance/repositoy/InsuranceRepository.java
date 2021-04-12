package com.example.Insurance.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.Insurance.model.Insurance;


@CrossOrigin(origins = "http://localhost:4200")
public interface InsuranceRepository extends CrudRepository<Insurance, Long>{

	@Query("SELECT u from Insurance u where u.policyTypeCode LIKE :policyTypeCode")
	public List<Insurance> getInsuranceByPolicyTypeCode(@Param("policyTypeCode") String policyTypeCode);
	
	
	
	public Insurance findTopByOrderByIdDesc();
	
	public List<Insurance> findByUserId(Long userId);
	
	
}
