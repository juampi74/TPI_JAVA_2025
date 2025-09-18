package entities;

import java.time.LocalDate;

public class Club {
	
	private int id;
	private String name;
	private LocalDate foundationDate;
	private String phoneNumber;
	private String email;
	private String badgeImage;
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
	
	public LocalDate getFoundationDate() {
	
		return foundationDate;
	
	}
	
	public void setFoundationDate(LocalDate foundationDate) {
	
		this.foundationDate = foundationDate;
	
	}
	
	public String getPhoneNumber() {
	
		return phoneNumber;
	
	}
	
	public void setPhoneNumber(String phoneNumber) {
	
		this.phoneNumber = phoneNumber;
	
	}
	
	public String getEmail() {
	
		return email;
	
	}
	
	public void setEmail(String email) {
	
		this.email = email;
	
	}
	
	public String getBadgeImage() {
	
		return badgeImage;
	
	}
	
	public void setBadgeImage(String badgeImage) {
	
		this.badgeImage = badgeImage;
	
	}
	
	public double getBudget() {
	
		return budget;
	
	}
	
	public void setBudget(double budget) {
	
		this.budget = budget;
	
	}

	@Override
	public String toString() {
	
		return "Club [id=" + id + ", name=" + name + ", foundationDate=" + foundationDate + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", badgeImage=" + badgeImage + ", budget=" + budget + "]";
	
	}

}