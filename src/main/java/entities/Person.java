package entities;

import java.time.LocalDate;
import enums.PersonRole;

public abstract class Person {
	
	private int id;
	private String fullname;
	private LocalDate birthdate;
	private String address;
	private PersonRole role;
	private String photo;
	private Nationality nationality;
	
	public int getId() {
		
		return id;
	
	}
	
	public void setId(int id) {
	
		this.id = id;
	
	}
	
	public String getFullname() {
	
		return fullname;
	
	}
	
	public void setFullname(String fullname) {
	
		this.fullname = fullname;
	
	}
	
	public LocalDate getBirthdate() {
	
		return birthdate;
	
	}
	
	public void setBirthdate(LocalDate birthdate) {
	
		this.birthdate = birthdate;
	
	}
	
	public String getAddress() {
	
		return address;
	
	}
	
	public void setAddress(String address) {
	
		this.address = address;
	
	}
	
	public PersonRole getRole() {
	
		return role;
	
	}

	public void setRole(PersonRole role) {
	
		this.role = role;
	
	}
	
	public String getPhoto() {
		
		return photo;
	
	}
	
	public void setPhoto(String photo) {
	
		this.photo = photo;
	
	}

	public Nationality getNationality() {
		
		return nationality;
	
	}

	public void setNationality(Nationality nationality) {
	
		this.nationality = nationality;
	
	}

	@Override
	public String toString() {

		return "Person [id=" + id + ", fullname=" + fullname + ", birthdate=" + birthdate + ", address=" + address
				+ ", role=" + role + ", nationality=" + nationality + "]";
	
	}

}