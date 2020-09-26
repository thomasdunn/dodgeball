package dodgeball.events;

import java.util.EventObject;
import dodgeball.*;

public class ThrowBallEvent extends EventObject {

	private Player thrower;
	private Player target;
	private Ball ball;
	
	public ThrowBallEvent(Object source, Player thrower, Player target, Ball ball) {
		super(source);
		
		this.thrower = thrower;
		this.target = target;
		this.ball = ball;
	}
	
	public Player thrower() {
		return thrower;
	}
	
	public Player target() {
		return target;
	}
	
	public Ball ball() {
		return ball;
	}
}
