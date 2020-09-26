package dodgeball;
import java.text.DecimalFormat;

import dodgeball.events.EventGenerator;

public class Ball extends GameObject {
	private static final int ballRadius = 3;
	private static final double verticalSpeed = 0.5;
	private static final double frictionSpeedReduction = 0.3;
	
	private Player heldBy;
	private Player thrownBy;
	
	public Ball(String name, int x, int y) {
		super(name, x, y);
		movement.setVerticalSpeed(verticalSpeed);
		radius = ballRadius;
	}
	
	public void move() {
		if (isHeld()) {
			moveTo(heldBy.movement.getX(), heldBy.movement.getY());
		}
		else {
			// slows down due to friction once hits ground
			double speed = movement.getSpeed();
			boolean onGroundBeforeMove = isOnGround();
			if (onGroundBeforeMove && speed > 0) {
				movement.setSpeed(speed - frictionSpeedReduction);
			}
			
			super.move();
			
			if (! onGroundBeforeMove && isOnGround()) {
				EventGenerator.getInstance().fireHitGroundEvent(this);
			}
		}
	}
	
	public boolean isHeld() {
		return heldBy != null;
	}

	public void setHeld(Player p) {
		this.heldBy = p;
	}
	
	public Player getThrownBy() {
		return thrownBy;
	}

	public void setThrownBy(Player thrownBy) {
		this.thrownBy = thrownBy;
	}

	public boolean isOnGround() {
		return movement.getZ() <= 0;
	}
	
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.#");
		return getName() + " (" + df.format(movement.getX()) + ", " + df.format(movement.getY()) + ", " + df.format(movement.getZ()) + ")" +
				(isHeld() ? " Held by " + heldBy.getName() : "");
	}
}
