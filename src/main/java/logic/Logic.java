package logic;

import java.util.LinkedList;

import data.*;
import entities.*;

public class Logic {
private DataPerson dp;
private DataStadium dt;
	
	public Logic() {
		dp=new DataPerson();
		dt=new DataStadium();
	}

	public LinkedList<Person> getAllPeople(){
		return dp.getAll();
	}
	
	public Person getPersonById(Person per) {
		return dp.getById(per);
	}
	
	
	public LinkedList<Person> getPersonByFullname(String fullname) {
		return dp.getByFullname(fullname);
	}
	
	public void addPerson(Person p) {
		dp.add(p);
	}
	
	
	public void updatePerson(Person per) {
		dp.updatePersona(per);
	}
	
	public void deletePerson(Person per) {
		dp.deletePersona(per);
	}
	
	public LinkedList<Stadium> getAllStadiums(){
		return dt.getAll();
	}
	
	public Stadium getStadiumById(Stadium st) {
		return dt.getById(st);
	}
	
	
	public LinkedList<Stadium> getStadiumByName(String name) {
		return dt.getByName(name);
	}
	
	public void addStadium(Stadium st) {
		dt.addStadium(st);
	}
	
	
	public void updateStadium(Stadium st) {
		dt.updateStadium(st);
	}
	
	public void deleteStadium(Stadium st) {
		dt.deleteStadium(st);
	}

}
