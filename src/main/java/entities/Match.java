package entities;

import java.time.LocalDateTime;
import enums.TournamentStage;

public class Match {
	
	private Integer id;
	private LocalDateTime date;
	private TournamentStage stage;
	private String groupName;
	private Integer matchday;
	private String bracketCode;
	private Integer homeGoals;
	private Integer awayGoals;
	private Integer homePenalties;
    private Integer awayPenalties;
	private Tournament tournament;
	private Club home;
	private Club away;
	
	public Integer getId() {
	
		return id;
	
	}
	
	public void setId(Integer id) {
	
		this.id = id;
	
	}
	
	public LocalDateTime getDate() {
		
		return date;
		
	}
	
	public void setDate(LocalDateTime date) {
		
		this.date = date;
		
	}
	
	public TournamentStage getStage() {
	
		return stage;
	
	}

	public void setStage(TournamentStage stage) {
	
		this.stage = stage;
	
	}
	
	public String getGroupName() {
	
		return groupName;
	
	}

	public void setGroupName(String groupName) {
	
		this.groupName = groupName;
	
	}
	
	public Integer getMatchday() {
		
		return matchday;
		
	}
	
	public void setMatchday(Integer matchday) {
		
		this.matchday = matchday;
		
	}
	
	public String getBracketCode() {
		
		return bracketCode;
		
	}
	
	public void setBracketCode(String bracketCode) {
		
		this.bracketCode = bracketCode;
		
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
	
	public Integer getHomePenalties() {
	
		return homePenalties;
	
	}

	public void setHomePenalties(Integer homePenalties) {
	
		this.homePenalties = homePenalties;
	
	}

	public Integer getAwayPenalties() {
	
		return awayPenalties;
	
	}

	public void setAwayPenalties(Integer awayPenalties) {
	
		this.awayPenalties = awayPenalties;
	
	}

	public Tournament getTournament() {
		
		return tournament;
		
	}
	
	public void setTournament(Tournament tournament) {
		
		this.tournament = tournament;
		
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

	@Override
	public String toString() {
	
		return "Match [id=" + id + ", date=" + date + ", stage=" + stage + ", groupName=" + groupName + ", matchday="
				+ matchday + ", bracketCode=" + bracketCode + ", homeGoals=" + homeGoals + ", awayGoals=" + awayGoals
				+ ", homePenalties=" + homePenalties + ", awayPenalties=" + awayPenalties + ", tournament=" + tournament
				+ ", home=" + home + ", away=" + away + "]";
	
	}
	
}