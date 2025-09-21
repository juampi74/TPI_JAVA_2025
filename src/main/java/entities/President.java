package entities;

public class President extends Person {

	private String managementPolicy;

	public String getManagementPolicy() {
		
		return managementPolicy;
	
	}

	public void setManagementPolicy(String managementPolicy) {
	
		this.managementPolicy = managementPolicy;
	
	}

	@Override
	public String toString() {
		
		return "President [managementPolicy=" + managementPolicy + "]";
	
	}
	
}