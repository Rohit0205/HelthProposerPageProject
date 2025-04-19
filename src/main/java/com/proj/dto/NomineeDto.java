package com.proj.dto;

import java.sql.Date;

import com.proj.enumclasses.Gender;

public class NomineeDto {

	private Integer proposerId;

	private String nomineeName;

	private Gender gender;

	private Date dateOfBirth;

	private String relationWithProposer;

	private Long mobileNo;

	private String address;


	public Integer getProposerId() {
		return proposerId;
	}


	public void setProposerId(Integer proposerId) {
		this.proposerId = proposerId;
	}


	public String getNomineeName() {
		return nomineeName;
	}


	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getRelationWithProposer() {
		return relationWithProposer;
	}


	public void setRelationWithProposer(String relationWithProposer) {
		this.relationWithProposer = relationWithProposer;
	}


	public Long getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	
	
	
	
}
