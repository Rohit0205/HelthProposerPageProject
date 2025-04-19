package com.proj.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.proj.enumclasses.Gender;
import com.proj.enumclasses.MaritalStatus;
import com.proj.enumclasses.PropserTitile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_details_tab")
public class ProposerDetails implements Serializable {

	// propser personal details feilds
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer proposerId;

	@Enumerated(EnumType.STRING)
	@Column(name = "proposer_title")
	private PropserTitile propserTitle;

	/*
	 * @Column(name = "proposer_full_name") private String proposerFullName;
	 */
	@Column(name = "proposer_first_name")
	private String firstName;
	@Column(name = "proposer_middle_name")
	private String middleName;
	@Column(name = "proposer_last_name")
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "proposer_gender")
	private Gender propsergender;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	 
	@Column(name = "pancard_no")
	private String panNumber;

	@Column(name = "adhar_no")
	private Long adharNo;

	@Enumerated(EnumType.STRING)
	@Column(name = "marital_status")
	private MaritalStatus maritalStatus;

	@Column(name = "active_status")
	private String activeStatus = "yes";

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDate createdAt;

	@UpdateTimestamp
	@Column(name = "update_at")
	private LocalDate updateAt;

	// contact details feilds

	@Column(name = "email")
	private String email;

	@Column(name = "mobile_no")
	private Long mobileNo;

	@Column(name = "alter_mobile_no")
	private Long alterMobileNo;

	// address details feilds

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "address_line3")
	private String addressLine3;

	@Column(name = "pincode")
	private Long pincode;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;
	
	
	
	
	//private List<Nominee> nomineeDetails;
	
	
	
	
	
	
	
	
	
	
	
	

	/*
	 * public List<Nominee> getNomineeDetails() { return nomineeDetails; }
	 * 
	 * public void setNomineeDetails(List<Nominee> nomineeDetails) {
	 * this.nomineeDetails = nomineeDetails; }
	 */

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getProposerId() {
		return proposerId;
	}

	public void setProposerId(Integer proposerId) {
		this.proposerId = proposerId;
	}

	public PropserTitile getPropserTitle() {
		return propserTitle;
	}

	public void setPropserTitle(PropserTitile propserTitle) {
		this.propserTitle = propserTitle;
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

	
	
	public ProposerDetails(Integer proposerId, PropserTitile propserTitle, String firstName, String middleName,
			String lastName, Gender propsergender, Date dateOfBirth, String panNumber, Long adharNo,
			MaritalStatus maritalStatus, String activeStatus, LocalDate createdAt, LocalDate updateAt, String email,
			Long mobileNo, Long alterMobileNo, String addressLine1, String addressLine2, String addressLine3,
			Long pincode, String city, String state) {
		super();
		this.proposerId = proposerId;
		this.propserTitle = propserTitle;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.propsergender = propsergender;
		this.dateOfBirth = dateOfBirth;
		this.panNumber = panNumber;
		this.adharNo = adharNo;
		this.maritalStatus = maritalStatus;
		this.activeStatus = activeStatus;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.email = email;
		this.mobileNo = mobileNo;
		this.alterMobileNo = alterMobileNo;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.pincode = pincode;
		this.city = city;
		this.state = state;

	}

	public ProposerDetails() {
		super();
	}

	

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDate updateAt) {
		this.updateAt = updateAt;
	}

	
	
	
	
}
