package dodgeball.events;

import java.util.EventObject;
import dodgeball.*;

public class HitGroundEvent extends EventObject {

	private Ball ball;
	
	public HitGroundEvent(Object source, Ball ball) {
		super(source);
		
		this.ball = ball;
	}
	
	public Ball ball() {
		return ball;
	}
}
