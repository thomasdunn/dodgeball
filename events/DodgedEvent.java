package dodgeball.events;

import java.util.EventObject;
import dodgeball.*;

public class DodgedEvent extends EventObject {

	private Player target;
	private Ball ball;
	
	public DodgedEvent(Object source, Player target, Ball ball) {
		super(source);
		
		this.target = target;
		this.ball = ball;
	}
	
	public Player target() {
		return target;
	}
	
	public Ball ball() {
		return ball;
	}
}
