package entities;

import java.time.LocalDateTime;

public class Match {
	
	private Integer id;
	private Club home;
	private Club away;
	private Tournament tournament;
	private LocalDateTime date;
	private Integer homeGoals;
	private Integer awayGoals;
	private Integer matchday;
	
	public Integer getId() {
	
		return id;
	
	}
	
	public void setId(Integer id) {
	
		this.id = id;
	
	}
	
	public Club getHome() {
	
		return home;
	
	}
	
	public void setHome(Club home) {
	
		this.home = home;
	
	}
	
	public Club getAway() {
	
		return away;
	
	}
	
	public void setAway(Club away) {
	
		this.away = away;
	
	}
	
	public Tournament getTournament() {
	
		return tournament;
	
	}
	
	public void setTournament(Tournament tournament) {
	
		this.tournament = tournament;
	
	}
	
	public LocalDateTime getDate() {
	
		return date;
	
	}
	
	public void setDate(LocalDateTime date) {
	
		this.date = date;
	
	}
	
	public Integer getHomeGoals() {
	
		return homeGoals;
	
	}
	
	public void setHomeGoals(Integer homeGoals) {
	
		this.homeGoals = homeGoals;
	
	}
	
	public Integer getAwayGoals() {
	
		return awayGoals;
	
	}
	
	public void setAwayGoals(Integer awayGoals) {
	
		this.awayGoals = awayGoals;
	
	}
	
	public Integer getMatchday() {
	
		return matchday;
	
	}
	
	public void setMatchday(Integer matchday) {
	
		this.matchday = matchday;
	
	}

}