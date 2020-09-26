package dodgeball;
import java.text.DecimalFormat;
import dodgeball.events.*;

public class Player extends GameObject {
	private boolean active = true;
	private Team team;
	private Ball ball;
	
	private static final int moveSpeed = 1;
	private static final int playerRadius = 6;
	
	private static final int throwRange = 45;
	private static final int throwSpeed = 3;
	private static final int ballHeight = 10;
	
	public Player(String name, int x, int y) {
		super(name, x, y);
		movement.setSpeed(moveSpeed);
		
		setActive(true);

		radius = playerRadius;
	}

	public void updateBehavior() {
		if (hasBall()) {
			// move toward nearest opponent
			Player opponent = findClosestOpponent();
			double angleToOpponent = 0;
			if (opponent != null) {
				angleToOpponent = angle(opponent);
				movement.setDirection(angleToOpponent);
			}
			move();

			// if close enough, throw ball
			if (opponent != null && distance(opponent) <= throwRange) {
				throwBall(angleToOpponent, opponent);
			}
		}
		else {
			// move toward nearest unheld ball on ground
			Ball closestAvailableBall = findClosestAvailableBall();
			if (closestAvailableBall != null) {
				double angleToBall = angle(closestAvailableBall);
				movement.setDirection(angleToBall);
			}
			move();
			
			// pick up the ball if you can
			if (closestAvailableBall != null && touches(closestAvailableBall)) {
				pickUpBall(closestAvailableBall);
			}
		}
	}

	protected Player findClosestOpponent() {
		double leastDistance = Double.MAX_VALUE;
		Player closestOpponent = null;
		
		for (Player p : Court.getInstance().getActivePlayers()) {
			if (! isTeammate(p)) {
				double opponentDistance = distance(p);
				if (opponentDistance < leastDistance) { 
					leastDistance = opponentDistance;
					closestOpponent = p;
				}
			}
		}
		
		return closestOpponent;
	}
	
	protected Ball findClosestAvailableBall() {
		double leastDistance = Double.MAX_VALUE;
		Ball closestAvailableBall = null;
		
		for (Ball b : Court.getInstance().getBalls()) {
			// if the ball is unheld and on the ground
			if (! b.isHeld() && b.isOnGround()) {
				double ballDistance = distance(b);
				if (ballDistance < leastDistance) { 
					leastDistance = ballDistance;
					closestAvailableBall = b;
				}
			}
		}
		
		return closestAvailableBall;
	}

	protected void throwBall(double angle, Player target) {
		ball.movement.setDirection(angle);
		ball.movement.setSpeed(throwSpeed);
		ball.setHeld(null);
		ball.setThrownBy(this);

		System.out.println("-> " + ball.getName() + " thrown by " + getName() + " at " + target.getName() + ", angle=" + angle);
		
		EventGenerator.getInstance().fireThrowBallEvent(this, target, ball);

		ball = null;
	}
	
	protected void pickUpBall(Ball ball) {
		ball.movement.setZ(ballHeight);
		ball.setHeld(this);
		this.ball = ball;
		
		System.out.println("^^ " + ball.getName() + " picked up by " + getName());
	}

	protected void dropBall() {
		if (ball != null) {
			ball.movement.setSpeed(0);
			ball.setHeld(null);
			ball = null;
		}
	}
	
	public boolean isTeammate(Player p) {
		return team.contains(p);
	}
	
	private boolean hasBall() {
		return ball != null;
	}
	
	public void hit() {
		dropBall();
		setActive(false);
		
		EventGenerator.getInstance().fireGetOutEvent(this);
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("#.#");
		return getName() + (isActive() ? " @ (" + df.format(movement.getX()) + ", " + df.format(movement.getY()) + ")" : " (out)");
	}
}
