package enums;

public enum DominantFoot {
    
	LEFT("Zurdo"),
	RIGHT("Diestro"),
    AMBIDEXTROUS("Ambidiestro");

    private final String displayName;

    DominantFoot(String displayName) {
        
    	this.displayName = displayName;
    
    }

    public String getDisplayName() {
    
    	return displayName;
    
    }

}