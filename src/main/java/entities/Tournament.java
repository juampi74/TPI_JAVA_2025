package entities;

import java.time.LocalDate;

import enums.TournamentFormat;
import enums.TournamentStage;

public class Tournament {
	
	private int id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private TournamentFormat format;
	private String season;
	private boolean finished;
	private Association association;
	
	private boolean allGroupMatchesPlayed;
    private boolean playoffsAlreadyGenerated;
    
    private String nextStageLabel;
    private boolean canGenerateNextStage;
    
    private String currentStatusLabel;
    
    private TournamentStage highestStage;
    
    private boolean hasMatchesPlayed;
	
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
	
	public boolean isFinished() {
	    
		return finished;
	
	}

	public void setFinished(boolean finished) {
	
		this.finished = finished;
	
	}

	public Association getAssociation() {
	
		return association;
	
	}

	public void setAssociation(Association association) {
	
		this.association = association;
	
	}
	
	public boolean isAllGroupMatchesPlayed() {
		
		return allGroupMatchesPlayed;
	
	}
    
	public void setAllGroupMatchesPlayed(boolean allGroupMatchesPlayed) {
		
		this.allGroupMatchesPlayed = allGroupMatchesPlayed;
	
	}

    public boolean isPlayoffsAlreadyGenerated() {
    	
    	return playoffsAlreadyGenerated;
    
    }
    
    public void setPlayoffsAlreadyGenerated(boolean playoffsAlreadyGenerated) {
    	
    	this.playoffsAlreadyGenerated = playoffsAlreadyGenerated;
    
    }
    
    public String getNextStageLabel() {
	
    	return nextStageLabel;
	
    }

	public void setNextStageLabel(String nextStageLabel) {
	
		this.nextStageLabel = nextStageLabel;
	
	}

	public boolean isCanGenerateNextStage() {
	
		return canGenerateNextStage;
	
	}

	public void setCanGenerateNextStage(boolean canGenerateNextStage) {
	
		this.canGenerateNextStage = canGenerateNextStage;
	
	}
	
	public String getCurrentStatusLabel() {
	
		return currentStatusLabel;
	
	}

	public void setCurrentStatusLabel(String currentStatusLabel) {
	
		this.currentStatusLabel = currentStatusLabel;
	
	}
	
	public TournamentStage getHighestStage() {
	
		return highestStage;
	
	}

	public void setHighestStage(TournamentStage highestStage) {
	
		this.highestStage = highestStage;
	
	}
	
	public boolean isHasMatchesPlayed() {
	
		return hasMatchesPlayed;
	
	}

	public void setHasMatchesPlayed(boolean hasMatchesPlayed) {
	
		this.hasMatchesPlayed = hasMatchesPlayed;
	
	}

	@Override
	public String toString() {
	
		return "Tournament [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", format=" + format + ", season=" + season + ", finished=" + finished + ", association="
				+ association + ", allGroupMatchesPlayed=" + allGroupMatchesPlayed + ", playoffsAlreadyGenerated="
				+ playoffsAlreadyGenerated + ", nextStageLabel=" + nextStageLabel + ", canGenerateNextStage="
				+ canGenerateNextStage + ", currentStatusLabel=" + currentStatusLabel + ", highestStage=" + highestStage
				+ ", hasMatchesPlayed=" + hasMatchesPlayed + "]";
	
	}
	
}