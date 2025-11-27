package entities;

import java.time.LocalDate;

import enums.TournamentFormat;

public class Tournament {
	
	private int id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private TournamentFormat format;
	private String season;
	private Association association;
	
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
	
	public TournamentFormat getFormat() {
	
		return format;
	
	}
	
	public void setFormat(TournamentFormat format) {
	
		this.format = format;
	
	}
	
	public String getSeason() {
	
		return season;
	
	}
	
	public void setSeason(String season) {
	
		this.season = season;
	
	}

	public Association getAssociation() {
	
		return association;
	
	}

	public void setAssociation(Association association) {
	
		this.association = association;
	
	}

	@Override
	public String toString() {
	
		return "Tournament [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", format=" + format.getDescription() + ", season=" + season + ", association=" + association + "]";
	
	}
	
}