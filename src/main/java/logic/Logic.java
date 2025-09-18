package logic;

import java.util.LinkedList;

import data.*;
import entities.*;

public class Logic {
	
	private DataPerson dp;
	private DataStadium ds;
	
	public Logic() {
		
		dp = new DataPerson();
		ds = new DataStadium();
	
	}

	public LinkedList<Person> getAllPeople() {
		
		return dp.getAll();
	
	}
	
	public Person getPersonById(Person p) {
		
		return dp.getById(p);
	
	}
	
	public LinkedList<Person> getPersonByFullname(String fullname) {
		
		return dp.getByFullname(fullname);
	
	}
	
	public void addPerson(Person p) {
		
		dp.add(p);
	
	}
	
	public void updatePerson(Person p) {
		
		dp.update(p);
	
	}
	
	public void deletePerson(Person p) {
		
		dp.delete(p);
	
	}
	
	public LinkedList<Stadium> getAllStadiums() {
		
		return ds.getAll();
	
	}
	
	public Stadium getStadiumById(Stadium s) {
		
		return ds.getById(s);
	
	}
	
	public LinkedList<Stadium> getStadiumByName(String name) {
		
		return ds.getByName(name);
	
	}
	
	public void addStadium(Stadium s) {
		
		ds.add(s);
	
	}
	
	public void updateStadium(Stadium s) {
		
		ds.update(s);
	
	}
	
	public void deleteStadium(Stadium s) {
		
		ds.delete(s);
	
	}

}