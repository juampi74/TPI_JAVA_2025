package entities;

import java.time.LocalDate;

import enums.*;

public class Association {
	
	private int id;
	private String name;
	private LocalDate creationDate;
	private AssociationType type;
	private Continent continent;
	
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
	
	public LocalDate getCreationDate() {
	
		return creationDate;
	
	}
	
	public void setCreationDate(LocalDate creationDate) {
	
		this.creationDate = creationDate;
	
	}
	
	public AssociationType getType() {
		
		return type;
	
	}

	public void setType(AssociationType type) {
	
		this.type = type;
	
	}
	
	public Continent getContinent() {
		
		return continent;
	
	}

	public void setContinent(Continent continent) {
	
		this.continent = continent;
	
	}

	@Override
	public String toString() {
		
		return "Association [id=" + id + ", name=" + name + ", creationDate=" + creationDate + ", type=" + type
				+ ", continent=" + continent + "]";
	
	}
	
}