package com.proj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.aspectj.weaver.ast.HasAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.dto.NomineeDto;
import com.proj.dto.ProposerDto;
import com.proj.dto.RequiredProposerDto;
import com.proj.entity.Nominee;
import com.proj.entity.ProposerDetails;
import com.proj.repository.NomineeRepository;
import com.proj.repository.ProposerDetailsRepository;
import com.proj.validation.Validation;

@Service
public class ProposerDetailsServiceImpl implements ProposerDetailsService {

	@Autowired
	private ProposerDetailsRepository propserRepo;

	@Autowired
	private NomineeRepository nomineeRepo;

	@Autowired
	private Validation valid;

	@Override
	public String addNewPropserWithDetails(ProposerDto proposerDto) {

		// from here we are adding proposer details with validation

		List<String> list = new ArrayList<>();

		if (proposerDto.getFirstName() == null || proposerDto.getFirstName().isEmpty()) {
			list.add("Proposer First Name is required.");
		}

		if (proposerDto.getLastName() == null || proposerDto.getLastName().isEmpty()) {
			list.add("Proposer Last Name is required.");
		}
		if (proposerDto.getAddressLine1() == null || proposerDto.getAddressLine1().isEmpty()) {
			list.add("Address Line 1 is required.");
		}
		if (proposerDto.getAddressLine2() == null || proposerDto.getAddressLine2().isEmpty()) {
			list.add("Address Line 2 is required.");
		}
		if (proposerDto.getAddressLine3() == null || proposerDto.getAddressLine3().isEmpty()) {
			list.add("Address Line 3 is required.");
		}
		if (proposerDto.getAdharNo() == null || String.valueOf(proposerDto.getAdharNo()).length() != 12) {
			list.add("Aadhar Number must be 12 digits.");
		}

		if (propserRepo.existsByAdharNo(proposerDto.getAdharNo())) {
			list.add("Sorry This Adhar id Already Existed...!");
		}

		if (proposerDto.getAlterMobileNo() == null || String.valueOf(proposerDto.getAlterMobileNo()).length() != 10) {
			list.add("Alternate Mobile Number must be 10 digits.");
		}
		if (proposerDto.getCity() == null || proposerDto.getCity().isEmpty()) {
			list.add("City is required.");
		}
		if (proposerDto.getDateOfBirth() == null) {
			list.add("Date of Birth is required.");
		}
		if (proposerDto.getEmail() == null || !proposerDto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			list.add("Invalid email format.");
		}
		if (propserRepo.existsByEmail(proposerDto.getEmail())) {
			list.add("Sorry This Email id Already Existed...!");
		}
		if (proposerDto.getMaritalStatus() == null) {
			list.add("Marital Status is required.");
		}
		if (proposerDto.getMobileNo() == null || String.valueOf(proposerDto.getMobileNo()).length() != 10) {
			list.add("Mobile Number must be 10 digits.");
		}

		if (!valid.validMobileNo(proposerDto.getMobileNo())) {
			list.add("Mobile Number must be Unique");
		}

		if (proposerDto.getPanNumber() == null || proposerDto.getPanNumber().length() != 10) {

			list.add("PAN Number must be 10 characters.");

		}
		if (!proposerDto.getPanNumber().matches("^[A-Z]{5}[0-9]{4}[A-Z]$")) {
			list.add("Invalid PAN number Formate");
		}

		if (propserRepo.existsByPanNumber(proposerDto.getPanNumber())) {
			list.add("This Pan Card Already Existed");
		}

		if (proposerDto.getPincode() == null || String.valueOf(proposerDto.getPincode()).length() != 6) {
			list.add("Pincode must be 6 digits.");
		}
		if (proposerDto.getPropsergender() == null) {
			list.add("Gender is required.");
		}
		if (proposerDto.getPropserTitle() == null) {
			list.add("Title is required.");
		}
		if (proposerDto.getState() == null || proposerDto.getState().isEmpty()) {
			list.add("State is required.");
		}

		if (!list.isEmpty()) {

			throw new IllegalArgumentException("Validation failed: " + list);

		}

		ProposerDetails proposerDetails = new ProposerDetails();

		proposerDetails.setFirstName(proposerDto.getFirstName());
		proposerDetails.setMiddleName(proposerDto.getMiddleName());
		proposerDetails.setLastName(proposerDto.getLastName());
		proposerDetails.setAddressLine1(proposerDto.getAddressLine1());
		proposerDetails.setAddressLine2(proposerDto.getAddressLine2());
		proposerDetails.setAddressLine3(proposerDto.getAddressLine3());
		proposerDetails.setAdharNo(proposerDto.getAdharNo());
		proposerDetails.setAlterMobileNo(proposerDto.getAlterMobileNo());
		proposerDetails.setCity(proposerDto.getCity());
		proposerDetails.setDateOfBirth(proposerDto.getDateOfBirth());
		proposerDetails.setEmail(proposerDto.getEmail());
		proposerDetails.setMaritalStatus(proposerDto.getMaritalStatus());

		proposerDetails.setMobileNo(proposerDto.getMobileNo());
		proposerDetails.setPanNumber(proposerDto.getPanNumber());
		proposerDetails.setPincode(proposerDto.getPincode());
		proposerDetails.setPropsergender(proposerDto.getPropsergender());
		proposerDetails.setPropserTitle(proposerDto.getPropserTitle());
		proposerDetails.setState(proposerDto.getState());

		// from here we are adding with nominee

		ProposerDetails details = propserRepo.save(proposerDetails);
		Integer proposerId = details.getProposerId();// fetch proposer id
		
		if(proposerDto.getDoYouWantToAddNominee().equalsIgnoreCase("yes"))
		{
			
			NomineeDto nomineeDto= proposerDto.getNomineeDetails();
			
			Nominee nomineeEntity=new Nominee();
			
			nomineeEntity.setAddress(nomineeDto.getAddress());
			nomineeEntity.setDateOfBirth(nomineeDto.getDateOfBirth());
			nomineeEntity.setGender(nomineeDto.getGender());
			nomineeEntity.setMobileNo(nomineeDto.getMobileNo());
			nomineeEntity.setNomineeName(nomineeDto.getNomineeName());
			nomineeEntity.setProposerId(proposerId);
			nomineeEntity.setRelationWithProposer(nomineeDto.getRelationWithProposer());
			
			nomineeRepo.save(nomineeEntity);
		}
		
		
		
		
		
		
		
		
		
		/*
		 * List<NomineeDto> nomineeDetails = proposerDto.getNomineeDetails();
		 * List<Nominee> nomineeEnitity = new ArrayList<>();
		 * 
		 * for (NomineeDto nomineeDto : nomineeDetails) {
		 * 
		 * Nominee nominee = new Nominee(); nominee.setAddress(nomineeDto.getAddress());
		 * nominee.setDateOfBirth(nomineeDto.getDateOfBirth());
		 * nominee.setGender(nomineeDto.getGender());
		 * nominee.setMobileNo(nomineeDto.getMobileNo());
		 * 
		 * nominee.setNomineeName(nomineeDto.getNomineeName());
		 * nominee.setProposerId(proposerId);
		 * nominee.setRelationWithProposer(nomineeDto.getRelationWithProposer());
		 * 
		 * nomineeEnitity.add(nominee);
		 * 
		 * }
		 * 
		 * nomineeRepo.saveAll(nomineeEnitity);
		 */

		return "Proposer Succesfully Saved along with Nominee's Details...!";

	}

	@Override
	public List<RequiredProposerDto> reportAllProposer() {

		List<ProposerDetails> proposerDetails = propserRepo.findAllByActiveStatus("yes");

		List<RequiredProposerDto> listDto = new ArrayList<>();

		for (ProposerDetails proposerDetails2 : proposerDetails) {

			RequiredProposerDto proposerDto = new RequiredProposerDto();

			proposerDto.setPropserTitle(proposerDetails2.getPropserTitle());

			StringBuilder builder = new StringBuilder();
			builder.append(proposerDetails2.getFirstName() + " ");

			if (proposerDetails2.getMiddleName() != null)

			{
				builder.append(proposerDetails2.getMiddleName() + " ");
			} else {

				builder.append("");
			}
			builder.append(proposerDetails2.getLastName());

			proposerDto.setProposerFullName(builder.toString());

			proposerDto.setAddressLine1(proposerDetails2.getAddressLine1());
			proposerDto.setAddressLine2(proposerDetails2.getAddressLine2());
			proposerDto.setAddressLine3(proposerDetails2.getAddressLine3());
			proposerDto.setEmail(proposerDetails2.getEmail());
			proposerDto.setMobileNo(proposerDetails2.getMobileNo());
			proposerDto.setAlterMobileNo(proposerDetails2.getAlterMobileNo());
			proposerDto.setCity(proposerDetails2.getCity());
			proposerDto.setState(proposerDetails2.getState());
			proposerDto.setPincode(proposerDetails2.getPincode());

			listDto.add(proposerDto);
		}

		return listDto;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public String updateProposerDetailsById(Integer proposerId, ProposerDto proposerDto) {

		
		/*
		 * if (proposerDto.getProposerFullName() == null ||
		 * proposerDto.getProposerFullName().isEmpty()) {
		 * errors.add("Proposer Full Name is required."); }
		 */
		

		

		
		
		
		
		

		
		
		List<String> errors = new ArrayList<>();

		
		

		Optional<ProposerDetails> opt = propserRepo.findByProposerIdAndActiveStatus(proposerId, "yes");
		
		if (opt.isPresent()) {
			ProposerDetails existingProposer = opt.get();

			if (proposerDto.getFirstName() !=null && !proposerDto.getFirstName().isEmpty()) {
				existingProposer.setFirstName(proposerDto.getFirstName());
			}
			if (proposerDto.getLastName() != null && !proposerDto.getLastName().isEmpty()) {
				
				existingProposer.setLastName(proposerDto.getLastName());
			}
			if (proposerDto.getAddressLine1() != null && !proposerDto.getAddressLine1().isEmpty()) {
				
				existingProposer.setAddressLine1(proposerDto.getAddressLine1());
			}
			
			if (proposerDto.getAddressLine2() != null && !proposerDto.getAddressLine2().isEmpty()) {
				
				existingProposer.setAddressLine2(proposerDto.getAddressLine2());
			}
			if (proposerDto.getAddressLine3() != null && !proposerDto.getAddressLine3().isEmpty()) {
				existingProposer.setAddressLine3(proposerDto.getAddressLine3());
			}
			if (proposerDto.getAdharNo() != null && !(String.valueOf(proposerDto.getAdharNo()).length() != 12))

			{

				existingProposer.setAdharNo(proposerDto.getAdharNo());
			}
			
			

			if (proposerDto.getAlterMobileNo() != null && !( String.valueOf(proposerDto.getAlterMobileNo()).length() != 10)) {

				existingProposer.setAlterMobileNo(proposerDto.getAlterMobileNo());
			}
			
			if (proposerDto.getCity() != null && !proposerDto.getCity().isEmpty()) {
				
				existingProposer.setCity(proposerDto.getCity());
			}
			if (proposerDto.getDateOfBirth() != null && !proposerDto.getDateOfBirth().toString().isEmpty()) {
				
				existingProposer.setDateOfBirth(proposerDto.getDateOfBirth());
			}
			
			if (proposerDto.getEmail() != null && !proposerDto.getEmail().isEmpty()) {
				
				existingProposer.setEmail(proposerDto.getEmail());
			}
			if (proposerDto.getMaritalStatus() == null && !proposerDto.getMaritalStatus().toString().isEmpty()) {

				existingProposer.setMaritalStatus(proposerDto.getMaritalStatus());
			}
			if (proposerDto.getMobileNo() != null && !(String.valueOf(proposerDto.getMobileNo()).length() != 10)) {
				
				existingProposer.setMobileNo(proposerDto.getMobileNo());
				
			}
			
			
			if (proposerDto.getPanNumber() != null && !(proposerDto.getPanNumber().length() != 10)) {
				
				if (!proposerDto.getPanNumber().matches("^[A-Z]{5}[0-9]{4}[A-Z]$")) {
					
					existingProposer.setPanNumber(proposerDto.getPanNumber());
				}
				
			}

			

			
			if (proposerDto.getPincode() != null && !(String.valueOf(proposerDto.getPincode()).length() != 6)) {
				existingProposer.setPincode(proposerDto.getPincode());
			}
			if (proposerDto.getPropsergender() != null   ) {
				errors.add("Gender is required.");
			}
			
			if (proposerDto.getPropserTitle() == null) {
				errors.add("Title is required.");
			}
			if (proposerDto.getState() == null || proposerDto.getState().isEmpty()) {
				errors.add("State is required.");
			}

			
			existingProposer.setPropserTitle(proposerDto.getPropserTitle());

			existingProposer.setMiddleName(existingProposer.getMiddleName());

			existingProposer.setPropsergender(proposerDto.getPropsergender());

			existingProposer.setState(proposerDto.getState());
			
			ProposerDetails proposerDetails=propserRepo.save(existingProposer);
			
			

			NomineeDto nomineeDto = proposerDto.getNomineeDetails();
		
			 Optional<Nominee> optional=	nomineeRepo.findByProposerIdAndActiveStatus(proposerId,"yes");
			 
			if(proposerDto.getDoYouWantToAddNominee().equalsIgnoreCase("no"))
			{
				if(proposerDto.getDoYouWantUpdateNominee().equalsIgnoreCase("yes")) {
				
				
			    
			     
			     if(optional.isPresent())
			     {
			    	 Nominee Existingnominiee=optional.get();
			    	 
			    	 Existingnominiee.setAddress(nomineeDto.getAddress());
			    	 Existingnominiee.setDateOfBirth(nomineeDto.getDateOfBirth());
			    	 Existingnominiee.setGender(nomineeDto.getGender());
			    	 Existingnominiee.setMobileNo(nomineeDto.getMobileNo());
			    	 Existingnominiee.setNomineeName(nomineeDto.getNomineeName());
			    	 Existingnominiee.setRelationWithProposer(nomineeDto.getRelationWithProposer());
			    	 
			    	 nomineeRepo.save(Existingnominiee);
			     }
			}
				
				
			}
			
			
			if(proposerDto.getDoYouWantToAddNominee().equalsIgnoreCase("yes"))
			{
				
				  Nominee  existingNominee= optional.get();
				  				  
				  existingNominee.setActiveStatus("No");
				
				  
				  
				 Nominee newNominee= new Nominee();
				 
				 NomineeDto nomineeDtoNew =proposerDto.getNomineeDetails();
				 
				 newNominee.setProposerId(proposerDetails.getProposerId());
				 newNominee.setAddress(nomineeDtoNew.getAddress());
				 newNominee.setDateOfBirth(nomineeDtoNew.getDateOfBirth());
				 newNominee.setGender(nomineeDtoNew.getGender());
				 newNominee.setMobileNo(nomineeDtoNew.getMobileNo());
				 newNominee.setNomineeName(nomineeDtoNew.getNomineeName());
				 newNominee.setRelationWithProposer(nomineeDtoNew.getRelationWithProposer());
				 
				 nomineeRepo.save(newNominee);
				 
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*
			 * if (!errors.isEmpty()) { throw new
			 * IllegalArgumentException("Validation failed: " + errors); }
			 * 
			 * propserRepo.save(existingProposer);
			 * 
			 * List<NomineeDto> nomineeDto1=proposerDto.getNomineeDetails(); List<Nominee>
			 * nomineesList =new ArrayList<>();
			 * 
			 * Optional<List<Nominee>> opt1 = nomineeRepo.findByProposerId(proposerId);
			 * 
			 * 
			 * 
			 * if(opt1.isPresent()) { List<Nominee> existingNominee =opt1.get();
			 * 
			 * for(int i=0;i<nomineeDto1.size();i++) { for (NomineeDto nomDto : nomineeDto1)
			 * {
			 * 
			 * 
			 * 
			 * existingNominee.get(i).setAddress(nomDto.getAddress());
			 * existingNominee.get(i).setDateOfBirth(nomDto.getDateOfBirth());
			 * existingNominee.get(i).setGender(nomDto.getGender());
			 * existingNominee.get(i).setMobileNo(nomDto.getMobileNo());
			 * existingNominee.get(i).setNomineeName(nomDto.getNomineeName());
			 * existingNominee.get(i).setRelationWithProposer(nomDto.getRelationWithProposer
			 * ());
			 * 
			 * 
			 * 
			 * if(i==0) { break; } }
			 * 
			 * nomineesList.add(existingNominee.get(i));
			 * 
			 * }
			 * 
			 * 
			 * nomineeRepo.saveAll(nomineesList);
			 */
				
		//	}	
			
			  
			 
			return "Your data was updated successfully!";
			
		} else {
			throw new NoSuchElementException("Proposer not found with ID: " + proposerId);
		}
		
	}

	@Override
	public String deleteProposerDetailsById(Integer proposerId) {

		Optional<ProposerDetails> opt = propserRepo.findById(proposerId);

		if (opt.isPresent()) {

			ProposerDetails existingProposer = opt.get();
			existingProposer.setActiveStatus("No");
			propserRepo.save(existingProposer);
			
			Optional<Nominee> opt1=nomineeRepo.findByProposerId(proposerId);
			Nominee existingNominee=opt1.get();
			if(opt1.isPresent())
			{
				
				
				
					existingNominee.setActiveStatus("No");
				
				
			}
			nomineeRepo.save(existingNominee);
			
			return "Proposer Details Deleted Sucesfully...!";

		} else {
			return "something went wrong/ this id Proposr may not be available";
		}
	}
	
	
	
	

	@Override
	public RequiredProposerDto fetchProposerById(Integer proposerId) {

		Optional<ProposerDetails> opt = propserRepo.findById(proposerId);
		RequiredProposerDto dto = new RequiredProposerDto();
		
		
		
		
		
		
		
		if (opt.isPresent()) {
			ProposerDetails details = opt.get();
			
			

			StringBuilder builder = new StringBuilder();
			builder.append(details.getFirstName() + " ");

			if (details.getMiddleName() != null)

			{
				builder.append(details.getMiddleName() + " ");
			} else {

				builder.append("");
			}
			builder.append(details.getLastName());
			
			dto.setProposerFullName(builder.toString());

			
			dto.setAddressLine1(details.getAddressLine1());
			dto.setAddressLine2(details.getAddressLine2());
			dto.setAddressLine3(details.getAddressLine3());
			dto.setAlterMobileNo(details.getAlterMobileNo());
			dto.setCity(details.getCity());
			dto.setEmail(details.getEmail());
			dto.setMobileNo(details.getMobileNo());
			dto.setPincode(details.getPincode());

			dto.setPropserTitle(details.getPropserTitle());
			dto.setState(details.getState());
			
			
			
			  List<Nominee> nomineeEntity= nomineeRepo.getByProposerId(proposerId);
			  List<NomineeDto> nomineeDto=new ArrayList<>();
			  
			  for(Nominee nomEntity:nomineeEntity) { NomineeDto nomDto=new NomineeDto();
			  
			  
			  
			  nomDto.setAddress(nomEntity.getAddress());
			  nomDto.setDateOfBirth(nomEntity.getDateOfBirth());
			  nomDto.setGender(nomEntity.getGender());
			  nomDto.setMobileNo(nomEntity.getMobileNo());
			  nomDto.setNomineeName(nomEntity.getNomineeName());
			  nomDto.setProposerId(proposerId);
			  nomDto.setRelationWithProposer(nomEntity.getRelationWithProposer());
			  
			  nomineeDto.add(nomDto);
			  
			  }
			  
			  dto.setNomineeDetails(nomineeDto);
			 
		}
	

		return dto;
	}
}
