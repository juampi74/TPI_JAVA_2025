package entities;

import java.time.LocalDate;

public class Person {
	private int id;
	private String fullname;
	private LocalDate birthdate;
	private String adress;
	
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
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	
	@Override
	public String toString() {
		return "\nPerson [id=" + id + ", fullname=" + fullname + ", birthdate=" + birthdate
				+ ", adress=" + adress + "]";
	}

}