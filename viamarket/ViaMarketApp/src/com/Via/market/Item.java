package com.Via.market;

import java.sql.Date;

public class Item {
	
	private String id;
	private String price;
	private String idCurrency;
	private String idCategory;
	
	private String title;
	private String description;
	private String iDAsoNetUsers;
	private String ApplicationUser_Id;
	
	private Date date;
	
	private Boolean sold;
	private String[] imagesURLs;

	public Item(String id, String price, String idCurrency, String idCategory,
			String title, String description, String iDAdoNetUsers,
			String applicationUser_Id, Date date, Boolean sold, String[] imagesURLs) {
		super();
		this.id = id;
		this.price = price;
		this.idCurrency = idCurrency;
		this.idCategory = idCategory;
		this.title = title;
		this.description = description;
		this.iDAsoNetUsers = iDAsoNetUsers;
		ApplicationUser_Id = applicationUser_Id;
		this.date = date;
		this.sold = sold;
		this.imagesURLs = imagesURLs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIdCurrency() {
		return idCurrency;
	}

	public void setIdCurrency(String idCurrency) {
		this.idCurrency = idCurrency;
	}

	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getiDAsoNetUsers() {
		return iDAsoNetUsers;
	}

	public void setiDAsoNetUsers(String iDAsoNetUsers) {
		this.iDAsoNetUsers = iDAsoNetUsers;
	}

	public String getApplicationUser_Id() {
		return ApplicationUser_Id;
	}

	public void setApplicationUser_Id(String applicationUser_Id) {
		ApplicationUser_Id = applicationUser_Id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}
	public String getImage(int i)
	{
		return imagesURLs[i];
	}
	
	
	

}
