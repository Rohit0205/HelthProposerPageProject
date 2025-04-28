package com.proj.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.proj.dto.NomineeDto;
import com.proj.dto.ProposerDto;
import com.proj.dto.RequiredProposerDto;
import com.proj.entity.Nominee;
import com.proj.entity.ProposerDetails;
import com.proj.pagination.Peginatior;
import com.proj.repository.NomineeRepository;
import com.proj.repository.ProposerDetailsRepository;
import com.proj.searcher.Searching;
import com.proj.validation.Validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class ProposerDetailsServiceImpl implements ProposerDetailsService {

	@Autowired
	private ProposerDetailsRepository propserRepo;

	@Autowired
	private NomineeRepository nomineeRepo;

	@Autowired
	private Validation valid;
	
	

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
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

		if (proposerDto.getDoYouWantToAddNominee().equalsIgnoreCase("yes")) {

			NomineeDto nomineeDto = proposerDto.getNomineeDetails();

			Nominee nomineeEntity = new Nominee();

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

	/*
	 * @Override public List<RequiredProposerDto>
	 * reportAllProposerWithPagination(ProposerListing proposerListing) {
	 * 
	 * //Pageable pageable=PageRequest.of(page, size);
	 * 
	 * 
	 * 
	 * Page<ProposerDetails> proposerDetails =
	 * propserRepo.findByActiveStatusPaginated("yes",proposerListing);
	 * 
	 * List<RequiredProposerDto> listDto = new ArrayList<>();
	 * 
	 * for (ProposerDetails proposerDetails2 : proposerDetails) {
	 * 
	 * RequiredProposerDto proposerDto = new RequiredProposerDto();
	 * 
	 * proposerDto.setPropserTitle(proposerDetails2.getPropserTitle());
	 * 
	 * StringBuilder builder = new StringBuilder();
	 * builder.append(proposerDetails2.getFirstName() + " ");
	 * 
	 * if (proposerDetails2.getMiddleName() != null)
	 * 
	 * { builder.append(proposerDetails2.getMiddleName() + " "); } else {
	 * 
	 * builder.append(""); } builder.append(proposerDetails2.getLastName());
	 * 
	 * proposerDto.setProposerFullName(builder.toString());
	 * 
	 * proposerDto.setAddressLine1(proposerDetails2.getAddressLine1());
	 * proposerDto.setAddressLine2(proposerDetails2.getAddressLine2());
	 * proposerDto.setAddressLine3(proposerDetails2.getAddressLine3());
	 * proposerDto.setEmail(proposerDetails2.getEmail());
	 * proposerDto.setMobileNo(proposerDetails2.getMobileNo());
	 * proposerDto.setAlterMobileNo(proposerDetails2.getAlterMobileNo());
	 * proposerDto.setCity(proposerDetails2.getCity());
	 * proposerDto.setState(proposerDetails2.getState());
	 * proposerDto.setPincode(proposerDetails2.getPincode());
	 * 
	 * listDto.add(proposerDto); }
	 * 
	 * return listDto;
	 * 
	 * 
	 * 
	 * }
	 */

	@Override
	@Transactional
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

			if (proposerDto.getFirstName() != null && !proposerDto.getFirstName().isEmpty()) {
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

			if (proposerDto.getAlterMobileNo() != null
					&& !(String.valueOf(proposerDto.getAlterMobileNo()).length() != 10)) {

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
			if (proposerDto.getPropsergender() != null) {
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

			ProposerDetails proposerDetails = propserRepo.save(existingProposer);

			NomineeDto nomineeDto = proposerDto.getNomineeDetails();

			Optional<Nominee> optional = nomineeRepo.findByProposerIdAndActiveStatus(proposerId, "yes");

			if (proposerDto.getDoYouWantToAddNominee().equalsIgnoreCase("no")) {
				if (proposerDto.getDoYouWantUpdateNominee().equalsIgnoreCase("yes")) {

					if (optional.isPresent()) {
						Nominee Existingnominiee = optional.get();

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

			if (proposerDto.getDoYouWantToAddNominee().equalsIgnoreCase("yes")) {

				Nominee existingNominee = optional.get();

				existingNominee.setActiveStatus("No");

				Nominee newNominee = new Nominee();

				NomineeDto nomineeDtoNew = proposerDto.getNomineeDetails();

				newNominee.setProposerId(proposerDetails.getProposerId());
				newNominee.setAddress(nomineeDtoNew.getAddress());
				newNominee.setDateOfBirth(nomineeDtoNew.getDateOfBirth());
				newNominee.setGender(nomineeDtoNew.getGender());
				newNominee.setMobileNo(nomineeDtoNew.getMobileNo());
				newNominee.setNomineeName(nomineeDtoNew.getNomineeName());
				newNominee.setRelationWithProposer(nomineeDtoNew.getRelationWithProposer());

				nomineeRepo.save(newNominee);

				// we are adding to comment to check git updation

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

			// }

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

			Optional<Nominee> opt1 = nomineeRepo.findByProposerId(proposerId);
			Nominee existingNominee = opt1.get();
			if (opt1.isPresent()) {

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

		Optional<ProposerDetails> opt = propserRepo.fetchDataById(proposerId, "yes");
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

			// List<Nominee> nomineeEntity= nomineeRepo.getByProposerId(proposerId);

			NomineeDto nomDto = new NomineeDto();
			Optional<Nominee> optional = nomineeRepo.findByProposerIdAndActiveStatus(proposerId, "yes");

			if (optional.isPresent()) {

				Nominee nomEntity = optional.get();

				nomDto.setAddress(nomEntity.getAddress());
				nomDto.setDateOfBirth(nomEntity.getDateOfBirth());
				nomDto.setGender(nomEntity.getGender());
				nomDto.setMobileNo(nomEntity.getMobileNo());
				nomDto.setNomineeName(nomEntity.getNomineeName());
				nomDto.setProposerId(proposerId);
				nomDto.setRelationWithProposer(nomEntity.getRelationWithProposer());

			}

			dto.setNomineeDetails(nomDto);

		}

		return dto;
	}

	@Override
	public List<RequiredProposerDto> reportAllProposerWithPagination(Integer page, Integer size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<ProposerDetails> proposerDetails = propserRepo.findByActiveStatusPaginated("yes", pageable);

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
	public List<ProposerDetails> fetchAllProposerDataWithPagination(Peginatior paginator) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<ProposerDetails> cq = cb.createQuery(ProposerDetails.class);

		Root<ProposerDetails> root = cq.from(ProposerDetails.class);

		List<Predicate> predicate = new ArrayList<>();

		predicate.add(cb.equal(root.get("activeStatus"), "yes"));

		Searching searching = paginator.getSearch();

		if (searching != null) {

			if (searching.getFirstName() != null && !searching.getFirstName().isEmpty()) {
				predicate.add(
						cb.like(cb.lower(root.get("firstName")), "%" + searching.getFirstName().toLowerCase() + "%"));
			}

			if (searching.getLastName() != null && !searching.getLastName().isEmpty()) {
				predicate.add(
						cb.like(cb.lower(root.get("lastName")), "%" + searching.getLastName().toLowerCase() + "%"));
			}

			if (searching.getCity() != null && !searching.getCity().isEmpty()) {
				predicate.add(cb.like(cb.lower(root.get("city")), "%" + searching.getCity().toLowerCase() + "%"));
			}

			if (searching.getState() != null && !searching.getState().isEmpty()) {
				predicate.add(cb.like(cb.lower(root.get("state")), "%" + searching.getState().toLowerCase() + "%"));
			}

			if (searching.getMobileNo() != null && !searching.getMobileNo().toString().isEmpty() && searching.getMobileNo()!=0) {
				predicate.add(cb.equal(root.get("mobileNo"), searching.getMobileNo()));
			}
		}

		cq.where(cb.and(predicate.toArray(new Predicate[0])));

		String sortBy = paginator.getSortBy();
		String sortOrder = paginator.getSortOreder();

		if (sortBy == null || sortBy.toString().isEmpty()) {
			sortBy = "proposerId";
		}

		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = "desc";
		}

		if (sortOrder.equalsIgnoreCase("desc")) {
			cq.orderBy(cb.desc(root.get(sortBy)));
		} else {

			cq.orderBy(cb.asc(root.get(sortBy)));
		}

		int page = paginator.getPageNo();
		int size = paginator.getPageSize();

		TypedQuery<ProposerDetails> query = entityManager.createQuery(cq);

		int startIndex = ((page - 1) * size);
		int endIndex = (startIndex + size);
		;

		if (page >= 0 && size > 0)

		{

			if (page == 0 && size > 0) {
				throw new IllegalArgumentException("please Enter Valid Number(Zero Not allowed)");

			}

			query.setFirstResult(startIndex);
			query.setMaxResults(size);
		}

		return query.getResultList();

	}

	@Override
	public List<ProposerDetails> fetchAllProposerDataWithStringBuilder(Peginatior paginatior) {

		StringBuilder builder = new StringBuilder("select p from ProposerDetails p where p.activeStatus = 'yes'");

		String sortBy = paginatior.getSortBy();
		String sortByOrder = paginatior.getSortOreder();

		String sort = (sortBy!=null && !sortBy.isBlank()) ? sortBy : "proposerId";
		
		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "proposerId";
		}

		if (sortByOrder == null || sortByOrder.isEmpty()) {
			sortByOrder = "desc";
		}

		Searching searching = paginatior.getSearch();
		
		if(searching==null)
		{
			searching=new Searching();
			paginatior.setSearch(searching);
		}

		if (searching != null) {

			String firstName = searching.getFirstName();
			String lastName = searching.getLastName();
			String city = searching.getCity();
			String state = searching.getState();
			Long mobileNo = searching.getMobileNo();

			if (firstName != null && !firstName.isEmpty()) {
				builder.append(" and lower(firstName) like ").append("'%" + firstName.toLowerCase() + "%'");
			}

			if (lastName != null && !lastName.isEmpty()) {
				builder.append(" and lower(lastName) like ").append("'%" + lastName.toLowerCase() + "%'");
			}

			if (city != null && !city.isEmpty()) {
				builder.append(" and lower(city) like ").append("'%" + city.toLowerCase() + "%'");
			}

			if (state != null && !state.isEmpty()) {
				builder.append(" and lower(state) like ").append("'%" + state.toLowerCase() + "%'");
			}

			if (mobileNo != null && !mobileNo.toString().isEmpty() && mobileNo!=0) {
				builder.append(" and mobileNo = ").append(mobileNo);

			}

		}
		
		builder.append(" order by p.").append(sortBy).append(" ").append(sortByOrder);

		TypedQuery<ProposerDetails> query = entityManager.createQuery(builder.toString(), ProposerDetails.class);

		int page = paginatior.getPageNo();
		int size = paginatior.getPageSize();

		int startIndext = ((page - 1) * size);
		int endIndex = (startIndext + size);
		
		if (page > 0 && size > 0)

		{

			query.setFirstResult(startIndext);
			query.setMaxResults(size);
		} else if ((size == 0 && page > 0) || (size > 0 && page == 0)) {
			throw new IllegalArgumentException("please Enter Valid Number(Zero Not allowed)");

		}

	
		return query.getResultList();

	}

	@Override
	public void downloadDataWithExcel(HttpServletResponse response) throws IOException {
		
		
		List<ProposerDetails> list= propserRepo.findAllByActiveStatus("yes");
		
		XSSFWorkbook workbook= new XSSFWorkbook();
		
		XSSFSheet sheet=workbook.createSheet("ProposerDetails");
		
		XSSFRow row=sheet.createRow(0);
		
		row.createCell(0).setCellValue("prposer_Id");
		row.createCell(1).setCellValue("prop_First_Name");
		row.createCell(2).setCellValue("prop_Middle_Name");
		row.createCell(3).setCellValue("prop_Last_Name");
		row.createCell(4).setCellValue("Mobile_No");
		row.createCell(5).setCellValue("Email");
		row.createCell(6).setCellValue("prop_Address");
		
		int indexRow=1;
		
		for(ProposerDetails data:list)
		{
			XSSFRow dataRow=sheet.createRow(indexRow);
			
			dataRow.createCell(0).setCellValue(data.getProposerId());
			dataRow.createCell(1).setCellValue(data.getFirstName());
			dataRow.createCell(2).setCellValue(data.getMiddleName());
			dataRow.createCell(3).setCellValue(data.getLastName());
			dataRow.createCell(4).setCellValue(data.getMobileNo());
			dataRow.createCell(5).setCellValue(data.getEmail());
			dataRow.createCell(6).setCellValue(data.getAddressLine1());
			
			indexRow++;
		}
		
		ServletOutputStream ops=response.getOutputStream();
		workbook.write(ops);
		
		workbook.close();
		ops.close();
		
		
	}

}
