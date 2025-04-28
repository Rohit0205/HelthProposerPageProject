package com.proj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proj.entity.ProposerDetails;


public interface ProposerDetailsRepository extends JpaRepository<ProposerDetails ,  Integer>{

	@Query("select p from ProposerDetails p where p.activeStatus = :status")
	public List<ProposerDetails> findAllByActiveStatus(@Param("status") String status);
	
	@Query("SELECT p FROM ProposerDetails p WHERE p.activeStatus = :status")
    Page<ProposerDetails> findByActiveStatusPaginated(@Param("status") String status,Pageable pageable);


	Optional<ProposerDetails> findByProposerIdAndActiveStatus(Integer proposerId, String activeStatus);
	
	@Query("select mobileNo from ProposerDetails")
	List<Long> fetchAllMobileNumbers();
	
	
	 boolean existsByEmail(String email);
	 
	 boolean existsByAdharNo(Long adharNo);
	 
	 boolean existsByPanNumber(String panNumber);
	 
	 @Query("select p from ProposerDetails p where p.proposerId = :proposerId and p.activeStatus = :status")
	 public Optional<ProposerDetails> fetchDataById(@Param("proposerId") Integer proposerId, @Param("status") String status);
}
