package com.proj.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.dto.ProposerDto;
import com.proj.dto.RequiredProposerDto;
import com.proj.entity.ProposerDetails;
import com.proj.pagination.Peginatior;
import com.proj.responseHandler.ProposerResponseHandler;
import com.proj.searcher.Searching;
import com.proj.service.ProposerDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/proposer_jurney")
public class ProposerPageController {

	@Autowired
	private ProposerDetailsService proposerService;

	@PostMapping("/add_propser_detail")
	public ProposerResponseHandler saveNewPropserWithDetails(@RequestBody ProposerDto proposerDto) {

		ProposerResponseHandler handler = new ProposerResponseHandler();

		try {
			String msg = proposerService.addNewPropserWithDetails(proposerDto);
			handler.setData(msg);
			handler.setStatus(true);
			handler.setMassage("sucess");

		} catch (Exception e) {
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMassage(e.getMessage());
		}

		return handler;
	}

	@GetMapping("/report_all_proposers")
	public ProposerResponseHandler reportAllProposerDetails() {

		ProposerResponseHandler handler = new ProposerResponseHandler();

		try {

			List<RequiredProposerDto> list = proposerService.reportAllProposer();

			handler.setData(list);
			handler.setMassage("sucess");
			handler.setStatus(true);
			handler.setTotalRecord(list.size());

		} catch (Exception e) {

			handler.setData(new ArrayList<>());
			handler.setMassage("Failed");
			handler.setStatus(false);

		}

		return handler;
	}

	@GetMapping("/getAllDataWithPagination")
	public ProposerResponseHandler reportAllProposerDetailsWithPagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size) {

		ProposerResponseHandler handler = new ProposerResponseHandler();

		try {
			List<RequiredProposerDto> propsersData = proposerService.reportAllProposerWithPagination(page, size);

			handler.setData(propsersData);
			handler.setStatus(true);
			handler.setMassage("sucess");
			handler.setTotalRecord(propsersData.size());

		}

		catch (IllegalArgumentException e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMassage("Failed");
		}

		catch (Exception e) {
			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMassage("Failed");
		}

		return handler;
	}

	@PutMapping("/update_proposer_by_id/{proposerId}")
	public ProposerResponseHandler updatePropserDetails(@PathVariable Integer proposerId,
			@RequestBody ProposerDto proposerDto) {

		ProposerResponseHandler handler = new ProposerResponseHandler();

		try {

			String msg = proposerService.updateProposerDetailsById(proposerId, proposerDto);
			handler.setData(msg);
			handler.setMassage("sucess");
			handler.setStatus(true);

		} catch (Exception e) {

			handler.setData(new ArrayList<>());
			handler.setMassage(e.getMessage());
			handler.setStatus(false);

		}

		return handler;
	}

	@PatchMapping("/delete_proposer_by_id/{proposerId}")
	public ResponseEntity<String> deleteProposerDetailsById(@PathVariable Integer proposerId) {
		String msg = proposerService.deleteProposerDetailsById(proposerId);

		return ResponseEntity.ok(msg);
	}

	@GetMapping("/get_proposer_byid/{id}")
	public ProposerResponseHandler getProposerDetailsById(@PathVariable Integer id) {
		ProposerResponseHandler handler = new ProposerResponseHandler();
		try {

			RequiredProposerDto dtoData = proposerService.fetchProposerById(id);

			handler.setData(dtoData);
			handler.setMassage("Sucess");
			handler.setStatus(true);

		} catch (Exception e) {

			handler.setData(e.getMessage());
			handler.setMassage("feild");
			handler.setStatus(false);
		}

		return handler;
	}

	@PostMapping("/fetch_proposer_data_paginator")
	public ProposerResponseHandler fetchAllProposerByPagination(@RequestBody Peginatior peginatior) {
		List<RequiredProposerDto> listForSize = proposerService.reportAllProposer();
		int totalReocrd = listForSize.size();

		ProposerResponseHandler handler = new ProposerResponseHandler();

		try {

			List<ProposerDetails> list = proposerService.fetchAllProposerDataWithPagination(peginatior);

			handler.setData(list);
			handler.setMassage("sucess");
			handler.setStatus(true);

			Searching searching = peginatior.getSearch();

			if (searching != null) {

				handler.setTotalRecord(list.size());

			} else {
				handler.setTotalRecord(totalReocrd);
			}

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setMassage(e.getMessage());
			handler.setStatus(false);
			handler.setTotalRecord(0);
		} catch (Exception e) {

			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setMassage(e.getMessage());
			handler.setStatus(false);
			handler.setTotalRecord(0);
		}

		return handler;
	}

	
	@PostMapping("/fetch_all_proposer_by_stringbuilder")
	public ProposerResponseHandler fetchAllProposerWithStringBuilderPagination(@RequestBody Peginatior peginatior) {

		List<RequiredProposerDto> listForSize = proposerService.reportAllProposer();
		int totalReocrd = listForSize.size();

		ProposerResponseHandler handler = new ProposerResponseHandler();

		try {
			List<ProposerDetails> list = proposerService.fetchAllProposerDataWithStringBuilder(peginatior);

			handler.setData(list);
			handler.setMassage("sucesss");
			handler.setStatus(true);

			Searching searching = peginatior.getSearch();

			if (searching != null || !searching.getFirstName().isEmpty() || !searching.getLastName().isEmpty()
					|| !searching.getCity().isEmpty() || !searching.getState().isEmpty()
					|| !searching.getMobileNo().toString().isEmpty()) {

				handler.setTotalRecord(list.size());

			} else {
				handler.setTotalRecord(totalReocrd);
			}

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setMassage(e.getMessage());
			handler.setStatus(false);
			handler.setTotalRecord(0);
		} catch (Exception e) {

			e.printStackTrace();
			handler.setData(new ArrayList<>());
			handler.setMassage(e.getMessage());
			handler.setStatus(false);
			handler.setTotalRecord(0);
		}

		return handler;
	}
	
	
	@GetMapping("/download_excel_sheet")
	public void downloadExcelFile(HttpServletResponse response) throws IOException
	{
		
		 response.setContentType("application/octet-stream");
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=proposerDetails.xlsx";
	        response.setHeader(headerKey, headerValue);
		
		
		proposerService.downloadDataWithExcel(response);
	}
	
	

}
