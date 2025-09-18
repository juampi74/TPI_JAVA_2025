package entities;

import java.time.LocalDate;

public class Club {
	private int id;
	private String name;
	private LocalDate fundation_date;
	private String phone_number;
	private String email;
	private String badge_image;
	private double budget;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getFundation_date() {
		return fundation_date;
	}
	public void setFundation_date(LocalDate fundation_date) {
		this.fundation_date = fundation_date;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBadge_image() {
		return badge_image;
	}
	public void setBadge_image(String badge_image) {
		this.badge_image = badge_image;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
}
