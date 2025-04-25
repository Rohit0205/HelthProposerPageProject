package com.proj.pagination;

import com.proj.searcher.Searching;

public class Peginatior {

	//private List<T> content;
	private Integer pageNo;
	private Integer pageSize;
	private String sortBy;
	private String sortOreder;
	
	private Searching search;
	
	
	   
	

	
	public Searching getSearch() {
		return search;
	}
	public void setSearch(Searching search) {
		this.search = search;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortOreder() {
		return sortOreder;
	}
	public void setSortOreder(String sortOreder) {
		this.sortOreder = sortOreder;
	}
	public Peginatior(Integer pageNo, Integer pageSize, String sortBy, String sortOreder) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.sortOreder = sortOreder;
	}
	
	
	
}
