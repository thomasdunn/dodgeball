package dodgeball;
import java.util.*;

public class Team {
	private String name;
	private int totalDodged = 0;
	
	private List<Player> players = new ArrayList<Player>();
	
	public Team(String name) {
		this.name = name;
	}
	
	public void addPlayer(Player p)
	{
		players.add(p);
		p.setTeam(this);
	}
	
	public boolean contains(Player p) {
		return players.contains(p);
	}
	
	public List<Player> getActivePlayers() {
		List<Player> activePlayers = new ArrayList<Player>();
		for (Player p : players) {
			if (p.isActive()) {
				activePlayers.add(p);
			}
		}
		return activePlayers;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTotalDodged() {
		return totalDodged;
	}
	
	// make operation thread safe
	public synchronized void incrementTotalDodged() {
		totalDodged++;
	}
	
	public String toString() {
		String val = name + " { ";
		for (Player p : players) {
			if (p.isActive()) {
				val += p.toString() + ", ";
			}
		}
		val += " }";
		
		return val;
	}
}
