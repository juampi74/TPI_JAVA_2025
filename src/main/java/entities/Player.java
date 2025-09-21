package entities;

public class Player extends Person {

	private String dominantFoot;
	private int jerseyNumber;
	private double height;
	private double weight;
	
	public String getDominantFoot() {
		return dominantFoot;
	}
	
	public void setDominantFoot(String dominantFoot) {
		this.dominantFoot = dominantFoot;
	}
	
	public int getJerseyNumber() {
		return jerseyNumber;
	}
	
	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Player [dominantFoot=" + dominantFoot + ", jerseyNumber=" + jerseyNumber + ", height=" + height
				+ ", weight=" + weight + "]";
	}
	
}