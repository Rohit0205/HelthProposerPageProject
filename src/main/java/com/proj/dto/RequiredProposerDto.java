package com.proj.dto;

import com.proj.enumclasses.PropserTitile;

public class RequiredProposerDto {

	private PropserTitile propserTitle;
	private String proposerFullName;
	private String email;
	private Long mobileNo;
	private Long alterMobileNo;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private Long pincode;
	private String city;
	private String state;
	
	
	private NomineeDto nomineeDetails;
	
	
	
	
	
	
	public NomineeDto getNomineeDetails() {
		return nomineeDetails;
	}
	public void setNomineeDetails(NomineeDto nomineeDetails) {
		this.nomineeDetails = nomineeDetails;
	}
	public PropserTitile getPropserTitle() {
		return propserTitle;
	}
	public void setPropserTitle(PropserTitile propserTitle) {
		this.propserTitle = propserTitle;
	}
	public String getProposerFullName() {
		return proposerFullName;
	}
	public void setProposerFullName(String proposerFullName) {
		this.proposerFullName = proposerFullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Long getAlterMobileNo() {
		return alterMobileNo;
	}
	public void setAlterMobileNo(Long alterMobileNo) {
		this.alterMobileNo = alterMobileNo;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public Long getPincode() {
		return pincode;
	}
	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
