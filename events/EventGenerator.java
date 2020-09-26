package dodgeball.events;

import java.util.*;
import dodgeball.*;

public class EventGenerator {
	
	private List<EventListener> listeners = new ArrayList<EventListener>();
	private List<EventListener> asyncListeners = new ArrayList<EventListener>();

	private static EventGenerator instance = null;
	protected EventGenerator() { }

	public static EventGenerator getInstance() {
		if(instance == null) {
			instance = new EventGenerator();
		}
		return instance;
	}
	
	public synchronized void addListener(EventListener listener) {
		listeners.add(listener);
	}

	public synchronized void removeListener(EventListener listener) {
		listeners.remove(listener);
	}

	public synchronized void addAsyncListener(EventListener listener) {
		asyncListeners.add(listener);
	}

	public synchronized void removeAsyncListener(EventListener listener) {
		asyncListeners.remove(listener);
	}

	public synchronized void fireThrowBallEvent(Player thrower, Player target, Ball ball) {
		ThrowBallEvent event = new ThrowBallEvent(this, thrower, target, ball);
		Iterator<EventListener> listenersIter = listeners.iterator();
		while (listenersIter.hasNext()) {
			listenersIter.next().ballThrown(event);
		}
	}
	
	public synchronized void fireHitGroundEvent(Ball ball) {
		HitGroundEvent event = new HitGroundEvent(this, ball);
		Iterator<EventListener> listenersIter = listeners.iterator();
		while (listenersIter.hasNext()) {
			listenersIter.next().ballHitGround(event);
		}
	}
	
	public synchronized void fireGetOutEvent(Player player) {
		GetOutEvent event = new GetOutEvent(this, player);
		Iterator<EventListener> listenersIter = listeners.iterator();
		while (listenersIter.hasNext()) {
			listenersIter.next().playerGotOut(event);
		}
	}
		
	public synchronized void fireDodgedEvent(Player target, Ball ball) {
		DodgedEvent event = new DodgedEvent(this, target, ball);
		Iterator<EventListener> listenersIter = listeners.iterator();
		while (listenersIter.hasNext()) {
			listenersIter.next().playerDodgedBall(event);
		}

		// Could repeat this pattern of creating a Runnable command class
		// and executing command in new thread for all events that need to be handled async
		Iterator<EventListener> asyncListenersIter = asyncListeners.iterator();
		while (asyncListenersIter.hasNext()) {
			EventListener asyncListener = asyncListenersIter.next();
			DodgedCommand command = new DodgedCommand(asyncListener, event);
			new Thread(command).start();
		}
	}
	
	public class DodgedCommand implements Runnable {
		private EventListener listener;
		private DodgedEvent event;
		
		public DodgedCommand(EventListener listener, DodgedEvent event) {
			this.listener = listener;
			this.event = event;
		}
		
		@Override
		public void run() {
			listener.playerDodgedBall(event);
		}
	}
}
