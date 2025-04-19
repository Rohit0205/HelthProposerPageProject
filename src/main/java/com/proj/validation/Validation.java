package com.proj.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ReportAsSingleViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proj.dto.ProposerDto;
import com.proj.entity.ProposerDetails;
import com.proj.repository.ProposerDetailsRepository;

@Component
public class Validation {

	
	/*
	 * public static boolean panCardValidation(String panCardNo) { int
	 * latterCount1=0; int digitCount=0; int lastDigitCount=0; char ch[]=
	 * panCardNo.toCharArray();
	 * 
	 * for(int j=0;j<5;j++) { if(Character.isLetter(ch[j])) { latterCount1++;
	 * 
	 * } else { return false; } }
	 * 
	 * for(int k=5;k<9;k++) { if(Character.isDigit(ch[k])) { digitCount++;
	 * 
	 * } else { return false; } }
	 * 
	 * if(!Character.isLetter(ch[9])) { return false; }
	 * 
	 * return true;
	 * 
	 * }
	 
	 */
	
	@Autowired
	private ProposerDetailsRepository repo;
	
	
	public  boolean validMobileNo(Long mobileNo)
	{
		List<Long> list= repo.fetchAllMobileNumbers();
		
		for (Long existingMobiles : list) {
			
			if(existingMobiles.equals(mobileNo))
			{
				return false;
			}
			
		}
		
		
		return true;
		
	}
}
