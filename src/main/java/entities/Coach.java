package entities;

import java.time.LocalDate;

public class Coach extends Person {

	private String preferredFormation;
	private String coachingLicense;
	private LocalDate licenseObtainedDate;
	
	public String getPreferredFormation() {
	
		return preferredFormation;
	}
	
	public void setPreferredFormation(String preferredFormation) {
	
		this.preferredFormation = preferredFormation;
	}
	
	public String getCoachingLicense() {
	
		return coachingLicense;
	}
	
	public void setCoachingLicense(String coachingLicense) {
	
		this.coachingLicense = coachingLicense;
	}
	
	public LocalDate getLicenseObtainedDate() {
	
		return licenseObtainedDate;
	}
	
	public void setLicenseObtainedDate(LocalDate licenseObtainedDate) {
	
		this.licenseObtainedDate = licenseObtainedDate;
	}

	@Override
	public String toString() {
		
		return "TechnicalDirector [preferredFormation=" + preferredFormation + ", coaching_license=" + coachingLicense
				+ ", license_obtained_date=" + licenseObtainedDate + "]";
	
	}
	
}