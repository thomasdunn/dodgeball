package dodgeball.events;

import java.util.*;
import dodgeball.*;

public class DodgeDetectionListener implements EventListener {

	// for keeping track of if thrown ball hits ground before target gets out
	private HashMap<Ball, Player> potentialDodges = new HashMap<Ball, Player>();
	
	@Override
	public void ballThrown(ThrowBallEvent event) {
		// add to potential dodges map when ball is thrown
		potentialDodges.put(event.ball(),  event.target());
	}

	@Override
	public void ballHitGround(HitGroundEvent event) {
		Player target = potentialDodges.remove(event.ball());
		if (target != null) {
			// ball hit ground before player got out, so target dodged the ball!
			EventGenerator.getInstance().fireDodgedEvent(target,  event.ball());
		}
	}

	@Override
	public void playerGotOut(GetOutEvent event) {
		List<Ball> toRemove = new ArrayList<Ball>();
		if (potentialDodges.containsValue(event.player())) {
			// loop through key/vals and record matching player for removal
			Iterator<Ball> keysIter = potentialDodges.keySet().iterator();
			while (keysIter.hasNext()) {
				Ball ball = keysIter.next();
				if (potentialDodges.get(ball) == event.player()) {
					toRemove.add(ball);
				}
			}
			for (Ball b : toRemove) {
				potentialDodges.remove(b);				
			}
		}
	}

	@Override
	public void playerDodgedBall(DodgedEvent event) {
		System.out.println("!!! " + event.target().getName() + " dodged " + event.ball());
	}

}
