package entities;

import enums.Continent;

public class Nationality {

	private int id;
	private String name;
	private String isoCode;
	private String flagImage;
	private Continent continent;
	
	public int getId() {
	
		return id;
	
	}

	public void setId(int id) {
	
		this.id = id;
	
	}

	public String getName() {
	
		return name;
	
	}

	public void setName(String name) {
	
		this.name = name;
	
	}

	public String getIsoCode() {
	
		return isoCode;
	}

	
	public void setIsoCode(String isoCode) {
	
		this.isoCode = isoCode;
	
	}
	
	public String getFlagImage() {
	
		return flagImage;
	
	}

	public void setFlagImage(String flagImage) {
	
		this.flagImage = flagImage;
	
	}

	public Continent getContinent() {
	
		return continent;
	
	}

	public void setContinent(Continent continent) {
	
		this.continent = continent;
	
	}

	@Override
	public String toString() {
	
		return "Nationality [id=" + id + ", name=" + name + ", isoCode=" + isoCode + ", flagImage=" + flagImage
				+ ", continent=" + continent + "]";
	
	}

}