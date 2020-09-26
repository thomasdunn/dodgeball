package dodgeball.events;

public interface EventListener {
	public void ballThrown(ThrowBallEvent event);
	public void ballHitGround(HitGroundEvent event);
	public void playerGotOut(GetOutEvent event);
	public void playerDodgedBall(DodgedEvent event);
}
