package enums;

public enum TournamentFormat {
    
	ZONAL_ELIMINATION("Dividir los equipos en dos zonas + eliminación"),
    ROUND_ROBIN_ONE_LEG("Todos contra todos (solo ida)"),
    ROUND_ROBIN_TWO_LEGS("Todos contra todos (ida y vuelta)"),
    WORLD_CUP("Formato mundial");

    private final String description;

    TournamentFormat(String description) {
    
    	this.description = description;
    
    }

    public String getDescription() {
    
    	return description;
    
    }

}