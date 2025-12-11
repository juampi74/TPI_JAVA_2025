package enums;

public enum TournamentFormat {
    
	ZONAL_ELIMINATION("Dividir los equipos en dos zonas + eliminación", "Zonas + Playoffs", true),
    ROUND_ROBIN_ONE_LEG("Todos contra todos (solo ida)", "Liga (Ida)", false),
    ROUND_ROBIN_TWO_LEGS("Todos contra todos (ida y vuelta)", "Liga (Ida y Vuelta)", false),
    WORLD_CUP("Formato mundial", "Mundial", true);

    private final String description;
    private final String shortName;
    private final boolean hasStages;

    TournamentFormat(String description, String shortName, boolean hasStages) {
    
    	this.description = description;
    	this.shortName = shortName;
    	this.hasStages = hasStages;
    
    }

    public String getDescription() {
    
    	return description;
    
    }
    
    public String getShortName() {
        
    	return shortName;
    
    }
    
    public boolean hasStages() {
        
    	return hasStages;
    
    }

}