package com.Via.market;

import java.sql.Date;

public class Item {
	
	private String id;
	private String price;
	private String idCurrency;
	private String idCategory;
	private String catname;
	private String curCode;
	
	private String title;
	private String description;
	private String iDAsoNetUsers;
	private String ApplicationUser_Id;
	private String ApplicationUser_Username;
	
	private String date;
	
	private String sold;
	private String[] imagesURLs;

	public Item(String itemID,String itemTitle,String itemDesc,String itemPrice,
			String itemCreationDate,String curID, String catID, String userID,
			String userName,String curCode,String catName,String ongoing,String[] imagesURLs){
	this.id = itemID;
	this.title = itemTitle;
	this.description=itemDesc;
	this.price = itemPrice;
	this.date=itemCreationDate;
	this.idCurrency = curID;
	this.idCategory = catID;
	this.ApplicationUser_Id = userID;
	this.ApplicationUser_Username=userName;
	this.sold =ongoing;
	this.catname = catName;
	this.curCode = curCode;
	this.imagesURLs = imagesURLs;
	
	}
	public Item(String Title,String[] imagesURLs)
	{
		this.title = Title;
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
		return this.title;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSold() {
		return sold;
	}

	public void setSold(String sold) {
		this.sold = sold;
	}
	public String getImage(int i)
	{
		return imagesURLs[i];
	}
	public String getImagePreview()
	{
		if(this.imagesURLs.length > 0)
			return imagesURLs[2];
		return null;
	}
	public String[] getImagesArray()
	{
		return this.imagesURLs;
	}
	
	public String getApplicationUser_Username()
	{
		return this.ApplicationUser_Username;
	}
	
	public String getCurCode()
	{
		return this.curCode;
	}
	
	public String getCatName()
	{
		return this.catname;
	}
	
	

}
