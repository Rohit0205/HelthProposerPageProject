package com.proj.service;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import com.proj.dto.ProposerDto;
import com.proj.dto.RequiredProposerDto;
import com.proj.entity.ProposerDetails;
import com.proj.pagination.Peginatior;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;



public interface ProposerDetailsService {

	public String addNewPropserWithDetails(ProposerDto proposerDto);

	public List<RequiredProposerDto> reportAllProposer();
	
	public List<RequiredProposerDto> reportAllProposerWithPagination(Integer page,Integer size);

	public String updateProposerDetailsById(Integer proposerId, ProposerDto proposerDto);

	public String deleteProposerDetailsById(Integer proposerId);
	
	public RequiredProposerDto fetchProposerById(Integer proposerId);
	
	public List<ProposerDetails> fetchAllProposerDataWithPagination(Peginatior paginator);
	
	public List<ProposerDetails> fetchAllProposerDataWithStringBuilder(Peginatior peginatior);
	
	public void downloadDataWithExcel(HttpServletResponse response)throws IOException;
}
