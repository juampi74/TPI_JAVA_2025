package entities;

public class TeamStats implements Comparable<TeamStats> {
    
	private Club club;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    
    public TeamStats(Club club) {
    
    	this.club = club;
    
    }

    public void addWin(int gf, int ga) {
        
    	played++; won++; goalsFor += gf; goalsAgainst += ga;
    
    }
    
    public void addDraw(int gf, int ga) {
        
    	played++; drawn++; goalsFor += gf; goalsAgainst += ga;
    
    }
    
    public void addLoss(int gf, int ga) {
    
    	played++; lost++; goalsFor += gf; goalsAgainst += ga;
    
    }

    public int getPoints() {

    	return (won * 3) + (drawn * 1);
    
    }

    public int getGoalDifference() {
    
    	return goalsFor - goalsAgainst;
    
    }

    @Override
    public int compareTo(TeamStats other) {

    	if (this.getPoints() != other.getPoints()) {
    	
    		return other.getPoints() - this.getPoints();
    	
    	}
        
    	if (this.getGoalDifference() != other.getGoalDifference()) {
    	
    		return other.getGoalDifference() - this.getGoalDifference();
    	
    	}
        
        if (this.getGoalsFor() != other.getGoalsFor()) {
        
        	return other.getGoalsFor() - this.getGoalsFor();
        
        }

        return this.getClub().getName().compareToIgnoreCase(other.getClub().getName());
    
    }

    public Club getClub() {
    	
    	return club;
    	
    }
    
    public int getPlayed() {
    	
    	return played;
    
    }
    
    public int getWon() {
    
    	return won;
    
    }
    
    public int getDrawn() {
    
    	return drawn;
    
    }
    
    public int getLost() {
    	
    	return lost;
    
    }
    
    public int getGoalsFor() {
    	
    	return goalsFor;
    
    }
    
    public int getGoalsAgainst() {
    	
    	return goalsAgainst;
    
    }

}