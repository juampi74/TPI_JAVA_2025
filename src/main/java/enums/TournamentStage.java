package enums;

public enum TournamentStage {

	GROUP_STAGE("Fase de Grupos"),
    ROUND_OF_16("Octavos de Final"),
    QUARTER_FINAL("Cuartos de Final"),
    SEMI_FINAL("Semifinales"),
    FINAL("Final"),
    THIRD_PLACE("Tercer Puesto");

    private final String displayName;

    TournamentStage(String displayName) {
    
    	this.displayName = displayName;
    
    } 

    public String getDisplayName() {

    	return displayName;
    
    }
	
}