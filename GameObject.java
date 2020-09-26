package dodgeball;

public class GameObject {

	private String name;
	public int radius = 10;
	public final Movement movement = new Movement(0,  0,  0,  0);

	public GameObject(String name, int x, int y) {
		this.name = name;
		movement.setX(x);
		movement.setY(y);
	}
	
	public void move() {
		movement.move();
	}
	
	public void moveTo(double x, double y) {
		movement.setX(x);
		movement.setY(y);
	}

	public boolean touches(GameObject other) {
		if (this == other) {
			// don't collide with self
			return false;
		}
		return (distance(movement.getX(),
							movement.getY(),
							other.movement.getX(),
							other.movement.getY())
							<= (radius + other.radius));
	}
	
	private static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}
	
	public double distance(GameObject other) {
		return distance(movement.getX(), movement.getY(),
				other.movement.getX(), other.movement.getY());
	}
	
	public double angle(GameObject other) {
		 return Math.atan2(movement.getY() - other.movement.getY(),
				 other.movement.getX() - movement.getX());
	}
	
	public String getName() {
		return name;
	}
}
