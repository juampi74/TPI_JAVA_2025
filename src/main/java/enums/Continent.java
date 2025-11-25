package enums;

public enum Continent {

	AFRICA("África"),
    ASIA("Asia"),
    EUROPE("Europa"),
    NORTH_AMERICA("América del Norte y Central"),
    OCEANIA("Oceanía"),
    SOUTH_AMERICA("América del Sur");

    private final String displayName;

    Continent(String displayName) {
    
    	this.displayName = displayName;
    
    } 

    public String getDisplayName() {

    	return displayName;
    
    }
	
}