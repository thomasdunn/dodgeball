package dodgeball;

public class Movement {

	private Vector velocity;
	private double verticalSpeed = 0;
	private double x;
	private double y;
	private double z;
	
	public Movement(double direction, double speed, int x, int y) {
		velocity = new Vector(direction, speed);
		this.x = x;
		this.y = y;
	}
	
	public Movement(double direction, double horizontalSpeed, double verticalSpeed, int x, int y, int z)
	{
		this(direction, horizontalSpeed, x, y);
		this.verticalSpeed = verticalSpeed;
		this.z = z;
	}
	
	/**
	 * simply move along the velocity vector (and drop vertical)
	 */
	public void move() {
		x += velocity.getXComponent();
		y -= velocity.getYComponent();
		
		// for now, fall at static rate
		// for simplicity, no bounce
		if (z > 0) {
			z -= verticalSpeed;
		}
	}
	
	public double getDirection() {
		return velocity.getTheta();
	}
	public void setDirection(double direction) {
		velocity.setTheta(direction);
	}
	
	public double getSpeed() {
		return velocity.getMagnitude();
	}
	public void setSpeed(double speed) {
		velocity.setMagnitude(speed);
	}
	
	public double getVerticalSpeed() {
		return verticalSpeed;
	}
	public void setVerticalSpeed(double speed) {
		verticalSpeed = speed;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public String toString() {
		return "Movement: velocity: " + velocity +
				" X: " + x +
				" Y: " + y +
				" Z: " + z;
	}
}