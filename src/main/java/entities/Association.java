package entities;

import java.time.LocalDate;

public class Association {
	
	private int id;
	private String name;
	private LocalDate creationDate;
	
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

	@Override
	public String toString() {
		
		return "Association [id=" + id + ", name=" + name + ", creationDate=" + creationDate + "]";
	
	}
		
}