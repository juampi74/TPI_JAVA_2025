package enums;

public enum TournamentFormat {
    
	ZONAL_ELIMINATION("Dividir los equipos en dos zonas + eliminación", true),
    ROUND_ROBIN_ONE_LEG("Todos contra todos (solo ida)", false),
    ROUND_ROBIN_TWO_LEGS("Todos contra todos (ida y vuelta)", false),
    WORLD_CUP("Formato mundial", true);

    private final String description;
    private final boolean hasStages;

    TournamentFormat(String description, boolean hasStages) {
    
    	this.description = description;
    	this.hasStages = hasStages;
    
    }

    public String getDescription() {
    
    	return description;
    
    }
    
    public boolean hasStages() {
        
    	return hasStages;
    
    }

}