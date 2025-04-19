package com.proj.dto;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.proj.enumclasses.Gender;
import com.proj.enumclasses.MaritalStatus;
import com.proj.enumclasses.PropserTitile;

public class ProposerDto {

	private Integer proposerId;
	private PropserTitile propserTitle;

	private String proposerFullName;

	private String firstName;
	private String middleName;
	private String lastName;

	private Gender propsergender;
	private Date dateOfBirth;

	private String panNumber;
	private Long adharNo;

	private MaritalStatus maritalStatus;

	private String activeStatus = "yes";

	// contact details feilds

	private String email;
	private Long mobileNo;
	private Long alterMobileNo;

	// address details feilds

	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private Long pincode;
	private String city;
	private String state;

	private NomineeDto nomineeDetails;
	
	private String doYouWantToAddNominee;
	
	private String doYouWantUpdateNominee;
	
	
	
	
	
	
	

	public String getDoYouWantUpdateNominee() {
		return doYouWantUpdateNominee;
	}

	public void setDoYouWantUpdateNominee(String doYouWantUpdateNominee) {
		this.doYouWantUpdateNominee = doYouWantUpdateNominee;
	}

	

	public String getDoYouWantToAddNominee() {
		return doYouWantToAddNominee;
	}

	public void setDoYouWantToAddNominee(String doYouWantToAddNominee) {
		this.doYouWantToAddNominee = doYouWantToAddNominee;
	}

	public Integer getProposerId() {
		return proposerId;
	}

	public void setProposerId(Integer proposerId) {
		this.proposerId = proposerId;
	}

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

	public Gender getPropsergender() {
		return propsergender;
	}

	public void setPropsergender(Gender propsergender) {
		this.propsergender = propsergender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Long getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(Long adharNo) {
		this.adharNo = adharNo;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ProposerDto() {
		super();
	}

}
