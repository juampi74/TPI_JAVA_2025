package enums;

public enum UserRole {

	ADMIN("ADMINISTRADOR"),
	PLAYER("JUGADOR"),
	COACH("DIRECTOR TÉCNICO"),
	PRESIDENT("PRESIDENTE");
	
	private final String displayName;

	UserRole(String displayName) {
        
    	this.displayName = displayName;
    
    }

    public String getDisplayName() {
    
    	return displayName;
    
    }
	
}