package com.example.Insurance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "insurance_policy")
public class Insurance implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private String policyTypeCode;
	private String policyName;
	private String description;
	private int yearsOfPayment;
	private double amount;
	private int maturityPariod;
	private double maturityAmount;
	private int validity;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "policy_id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@JsonBackReference
	private User user;
	
 
	@ManyToOne
    @JoinColumn(name="id", nullable=false)
	public User getUser() {
		return user;
	}
	 
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "policy_type_code")
	public String getPolicyTypeCode() {
		return policyTypeCode;
	}
	public void setPolicyTypeCode(String policyTypeCode) {
		this.policyTypeCode = policyTypeCode;
	}
	@Column(name = "Policy_name")
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "yearsofpayements")
	public int getYearsOfPayment() {
		return yearsOfPayment;
	}
	public void setYearsOfPayment(int yearsOfPayment) {
		this.yearsOfPayment = yearsOfPayment;
	}
	@Column(name = "amount")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Column(name = "maturityperiod")
	public int getMaturityPariod() {
		return maturityPariod;
	}
	public void setMaturityPariod(int maturityPariod) {
		this.maturityPariod = maturityPariod;
	}
	@Column(name = "maturityamount")
	public double getMaturityAmount() {
		return maturityAmount;
	}
	public void setMaturityAmount(double maturityAmount) {
		this.maturityAmount = maturityAmount;
	}
	@Column(name = "validity")
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	
	
 
	
	
	
	
	
	

}
