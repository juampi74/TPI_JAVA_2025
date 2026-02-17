package entities;

import enums.DominantFoot;

public class Player extends Person {

	private DominantFoot dominantFoot;
	private int jerseyNumber;
	private Double height;
	private Double weight;
	
	public DominantFoot getDominantFoot() {
	
		return dominantFoot;
	
	}
	
	public void setDominantFoot(DominantFoot dominantFoot) {
	
		this.dominantFoot = dominantFoot;
	
	}
	
	public int getJerseyNumber() {
	
		return jerseyNumber;
	
	}
	
	public void setJerseyNumber(int jerseyNumber) {
	
		this.jerseyNumber = jerseyNumber;
	
	}
	
	public Double getHeight() {
	
		return height;
	
	}
	
	public void setHeight(Double height) {
	
		this.height = height;
	
	}
	
	public Double getWeight() {
	
		return weight;
	
	}
	
	public void setWeight(Double weight) {
	
		this.weight = weight;
	
	}

	@Override
	public String toString() {
	
		return "Player [dominantFoot=" + dominantFoot + ", jerseyNumber=" + jerseyNumber + ", height=" + height
				+ ", weight=" + weight + "]";
	
	}
	
}