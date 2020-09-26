package dodgeball;

import java.util.ArrayList;
import java.util.List;

import dodgeball.events.AsyncMetricsListener;
import dodgeball.events.DodgeDetectionListener;
import dodgeball.events.EventGenerator;

public class DodgeballMain {

	private static Court court = Court.getInstance();
	private static final int timeLimit = 500;
	
	public static void main(String[] args) {
		init();

		int t;
		for (t = 0; t < timeLimit && getWinner() == null; t++) {
			mainLoop();
		}
		
		showResults(t);
	}

	private static void init() {
		initPlayers();
		initBalls();
		initStatistics();
	}
	
	private static void mainLoop() { 
		updateBehaviors();
		updateBalls();
		updateStatistics();
	}
	
	private static void initPlayers() {
		Team t1 = new Team("Team 1");
		Team t2 = new Team("Team 2");
		
		t1.addPlayer(new Player("AI 01", 20, 10));
		t1.addPlayer(new Player("AI 02", 50, 10));
		t1.addPlayer(new Player("AI 03", 80, 10));
		t1.addPlayer(new Player("AI 04", 20, 30));
		t1.addPlayer(new Player("AI 05", 50, 30));
		t1.addPlayer(new Player("AI 06", 80, 30));
		
		t2.addPlayer(new Player("AI 07", 20, 70));
		t2.addPlayer(new Player("AI 08", 50, 70));
		t2.addPlayer(new Player("AI 09", 80, 70));
		t2.addPlayer(new Player("AI 10", 20, 90));
		t2.addPlayer(new Player("AI 11", 50, 90));
		t2.addPlayer(new Player("AI 12", 80, 90));
		
		court.addTeam(t1);
		court.addTeam(t2);
	}
	
	private static void updateBehaviors() {
		for (Player p : court.getActivePlayers()) {
			p.updateBehavior();
		}
	}
	
	private static void updateBalls() {
		for (Ball b : court.getBalls()) {
			b.move();
			
			// if ball is in the air
			if (! b.isHeld() && ! b.isOnGround()) {
				// check each player for a hit
				for (Player p : court.getActivePlayers()) {
					// did the ball hit the player
					if (b.touches(p) ) {
						// it hit, slow down the ball
						b.movement.setSpeed(b.movement.getSpeed() * 0.5);
						
						if (! b.getThrownBy().isTeammate(p)) {
							// the thrower is an opponent of the hit player
							p.hit();
							System.out.println("X  " + b.getThrownBy().getName() +
												" hit " + p.getName() + " with " + b.getName());
						}
					}
				}
			}
		}
	}
	
	private static void initBalls() {
		court.addBall(new Ball("Ball 1", randomInt(100), randomInt(100)));
		court.addBall(new Ball("Ball 2", randomInt(100), randomInt(100)));
		court.addBall(new Ball("Ball 3", randomInt(100), randomInt(100)));
		court.addBall(new Ball("Ball 4", randomInt(100), randomInt(100)));
	}
	
	private static void initStatistics() {
		EventGenerator.getInstance().addListener(new DodgeDetectionListener());
		EventGenerator.getInstance().addAsyncListener(new AsyncMetricsListener());
	}
	
	private static void updateStatistics() {
		// for now just print out game object details
		System.out.println("");

		for (Team t : court.getTeams()) {
			System.out.println(t);
		}
		
		for (Ball b : court.getBalls()) {
			System.out.println(b);
		}
	}
	
	private static void showResults(int ticks) {
		System.out.println("\n---------------------------------------");
		System.out.println("Match ends after " + ticks + " ticks.");

		for (Team t : court.getTeams()) {
			System.out.println(t.getName() + " dodge count: " + t.getTotalDodged());
		}
		
		Team winner = getWinner();
		if (winner != null) {
			System.out.println(winner.getName() + " wins!!!");
		}
		else {
			System.out.println("No definitive winner declared.");
		}
	}
	
	private static Team getWinner() {
		List<Team> activeTeams = new ArrayList<Team>();
		for (Team t : court.getTeams()) {
			if (! t.getActivePlayers().isEmpty()) {
				activeTeams.add(t);
			}
		}
		
		return (activeTeams.size() == 1) ? activeTeams.get(0) : null; 
	}
	
	private static int randomInt(int max) {
		return (int)(Math.random() * max);
	}
}
