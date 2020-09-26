package dodgeball; 
import java.util.*;

public class Court {
	private List<Team> teams = new ArrayList<Team>();
	private List<Ball> balls = new ArrayList<Ball>();
	
	private static Court instance = null;
	protected Court() { }

	public static Court getInstance() {
		if(instance == null) {
			instance = new Court();
		}
		return instance;
	}
	
	public void addTeam(Team team) {
		teams.add(team);
	}
	
	public void addBall(Ball ball) {
		balls.add(ball);
	}
	
	public List<Player> getActivePlayers() {
		List<Player> activePlayers = new ArrayList<Player>();
		for (Team t : teams) {
			activePlayers.addAll(t.getActivePlayers());
		}
		
		return activePlayers;
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public List<Ball> getBalls() {
		return balls;
	}
}
