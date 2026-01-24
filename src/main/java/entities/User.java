package entities;

import enums.UserRole;

public class User {
    
	private int id;
    private String email;
    private String password;
    private UserRole role;
    private Person person;

    public int getId() {
    	
    	return id;
    	
    }
    
    public void setId(int id) {
    	
    	this.id = id;
    	
    }

    public String getEmail() {
    	
    	return email;
    	
    }
    
    public void setEmail(String email) {
    	
    	this.email = email;
    	
    }

    public String getPassword() {
    	
    	return password;
    	
    }
    
    public void setPassword(String password) {
    	
    	this.password = password;
    	
    }

    public UserRole getRole() {
    	
    	return role;
    	
    }
    
    public void setRole(UserRole role) {
    	
    	this.role = role;
    	
    }

    public Person getPerson() {
    	
    	return person;
    	
    }
    
    public void setPerson(Person person) {
    	
    	this.person = person;
    	
    }
    
}