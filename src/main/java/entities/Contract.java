package entities;

import java.time.LocalDate;

public class Contract {

	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private double salary;
	private double releaseClause;
	private Person person;
	private Club club;
	
	public int getId() {
	
		return id;
	
	}
	
	public void setId(int id) {
	
		this.id = id;
	
	}
	
	public LocalDate getStartDate() {
	
		return startDate;
	
	}
	
	public void setStartDate(LocalDate startDate) {
	
		this.startDate = startDate;
	
	}
	
	public LocalDate getEndDate() {
	
		return endDate;
	
	}
	
	public void setEndDate(LocalDate endDate) {
	
		this.endDate = endDate;
	
	}
	
	public double getSalary() {
	
		return salary;
	
	}
	
	public void setSalary(double salary) {
	
		this.salary = salary;
	
	}
	
	public double getReleaseClause() {
	
		return releaseClause;
	
	}
	
	public void setReleaseClause(double releaseClause) {
	
		this.releaseClause = releaseClause;
	
	}
	
	public Person getPerson() {
		
		return person;
	
	}

	public void setPerson(Person person) {
	
		this.person = person;
	
	}

	public Club getClub() {
	
		return club;
	
	}

	public void setClub(Club club) {
	
		this.club = club;
	
	}

	@Override
	public String toString() {
		
		return "Contract [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", salary=" + salary
				+ ", releaseClause=" + releaseClause + ", person=" + person + ", club=" + club + "]";
	
	}
	
}