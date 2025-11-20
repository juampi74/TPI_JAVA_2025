package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Match {
	
	private Integer id;
	private Club home;
	private Club away;
	private Tournament tournament;
	private LocalDateTime date;
	private Integer home_goals;
	private Integer away_goals;
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
		return home_goals;
	}
	public void setHomeGoals(Integer home_goals) {
		this.home_goals = home_goals;
	}
	public Integer getAwayGoals() {
		return away_goals;
	}
	public void setAwayGoals(Integer away_goals) {
		this.away_goals = away_goals;
	}
	public Integer getMatchday() {
		return matchday;
	}
	public void setMatchday(Integer matchday) {
		this.matchday = matchday;
	}
	
}