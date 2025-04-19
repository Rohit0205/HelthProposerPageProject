package com.proj.service;

import java.util.List;

import com.proj.dto.ProposerDto;
import com.proj.dto.RequiredProposerDto;

public interface ProposerDetailsService {

	public String addNewPropserWithDetails(ProposerDto proposerDto);

	public List<RequiredProposerDto> reportAllProposer();

	public String updateProposerDetailsById(Integer proposerId, ProposerDto proposerDto);

	public String deleteProposerDetailsById(Integer proposerId);
	
	public RequiredProposerDto fetchProposerById(Integer proposerId);
}
