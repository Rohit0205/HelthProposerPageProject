package com.proj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.dto.ProposerDto;
import com.proj.dto.RequiredProposerDto;
import com.proj.entity.ProposerDetails;
import com.proj.responseHandler.ProposerResponseHandler;
import com.proj.service.ProposerDetailsService;

@RestController
@RequestMapping("/proposer_jurney")
public class ProposerPageController {

	@Autowired
	private ProposerDetailsService proposerService;

	@PostMapping("/add_propser_detail")
	public ProposerResponseHandler saveNewPropserWithDetails( @RequestBody ProposerDto proposerDto) {

		
		ProposerResponseHandler handler=new  ProposerResponseHandler();
		
		try {
		String msg = proposerService.addNewPropserWithDetails(proposerDto);
		handler.setData(msg);
		handler.setStatus(true);
		handler.setMassage("sucess");
	
		
		}
		catch(Exception e)
		{
			handler.setData(new ArrayList<>());
			handler.setStatus(false);
			handler.setMassage(e.getMessage());
		}

		return handler;
	}

	@GetMapping("/report_all_proposers")
	public ProposerResponseHandler reportAllProposerDetails() {

		ProposerResponseHandler handler=new ProposerResponseHandler();
		
      try {
		
  		List<RequiredProposerDto> list = proposerService.reportAllProposer();
  		handler.setData(list);
  		handler.setMassage("sucess");
  		handler.setStatus(true);

    	  
	} catch (Exception e) {
		
		handler.setData(new ArrayList<>());
  		handler.setMassage("Failed");
  		handler.setStatus(false);
	}
		
      return handler;
	}

	@PutMapping("/update_proposer_by_id/{proposerId}")
	public ProposerResponseHandler updatePropserDetails(@PathVariable Integer proposerId,
			@RequestBody ProposerDto proposerDto) {
		
		 ProposerResponseHandler handler=new ProposerResponseHandler();
		 
		try {
			
			String msg = proposerService.updateProposerDetailsById(proposerId, proposerDto);
			handler.setData(msg);
			handler.setMassage("sucess");
			handler.setStatus(true);
			
			
		}
		catch (Exception e) {
			
			handler.setData(new ArrayList<>());
			handler.setMassage(e.getMessage());
			handler.setStatus(false);
			
		}
		
		
		
		
		return handler;
	}

	@PatchMapping("     {proposerId}")
	public ResponseEntity<String> deleteProposerDetailsById(@PathVariable Integer proposerId) {
		String msg = proposerService.deleteProposerDetailsById(proposerId);

		return ResponseEntity.ok(msg);
	}
	
	
	@GetMapping("/get_proposer_byid/{id}")
	public ProposerResponseHandler getProposerDetailsById(@PathVariable Integer id)
	{
		ProposerResponseHandler handler=new ProposerResponseHandler();
		try {
			
		RequiredProposerDto dtoData=	proposerService.fetchProposerById(id);
			
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
}
