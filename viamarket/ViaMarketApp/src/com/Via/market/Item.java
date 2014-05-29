package com.Via.market;

import java.sql.Date;

public class Item {
	
	int id;
	int price;
	int idCurrency;
	int idCategory;
	
	String title;
	String description;
	String iDAsoNetUsers;
	String ApplicationUser_Id;
	
	Date date;
	
	Boolean sold;

	public Item(int id, int price, int idCurrency, int idCategory,
			String title, String description, String iDAsoNetUsers,
			String applicationUser_Id, Date date, Boolean sold) {
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
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getIdCurrency() {
		return idCurrency;
	}

	public void setIdCurrency(int idCurrency) {
		this.idCurrency = idCurrency;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
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
	
	
	

}
