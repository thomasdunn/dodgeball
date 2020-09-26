package dodgeball.events;

public class AsyncMetricsListener implements EventListener {

	@Override
	public void playerDodgedBall(DodgedEvent event) {
		event.target().getTeam().incrementTotalDodged();
	}
	
	@Override
	public void ballThrown(ThrowBallEvent event) { }
	@Override
	public void ballHitGround(HitGroundEvent event) { }
	@Override
	public void playerGotOut(GetOutEvent event) { }
}
