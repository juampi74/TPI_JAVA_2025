package enums;

public enum AssociationType {

	NATIONAL("Nacional"),
	CONTINENTAL("Continental"),
	INTERNATIONAL("Internacional");
	
	private final String displayName;

	AssociationType(String displayName) {
    
    	this.displayName = displayName;
    
    } 

    public String getDisplayName() {

    	return displayName;
    
    }
	
}