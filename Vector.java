package dodgeball;

public class Vector {

	private double theta;
	private double magnitude;
	
	public Vector(double theta, double magnitude) {
		this.theta = theta;
		this.magnitude = magnitude;
	}
	
	public double getXComponent() {
		return this.magnitude * Math.cos(this.theta);
	}
	
	public double getYComponent() {
		return this.magnitude * Math.sin(this.theta);
	}
	
	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public String toString() {
		return "Vector: theta: " + theta +
				" magnitude: " + magnitude;
	}
}
