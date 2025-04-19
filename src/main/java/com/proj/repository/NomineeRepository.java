package com.proj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.entity.Nominee;

public interface NomineeRepository extends JpaRepository<Nominee,Integer> {

	public Optional <Nominee> findByProposerId(Integer proposerId);
	
	public List<Nominee> getByProposerId(Integer proposerId);
	
	public Optional <Nominee> findByProposerIdAndActiveStatus(Integer proposerId,String status);


}
