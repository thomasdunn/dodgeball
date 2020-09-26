package dodgeball.events;

import java.util.EventObject;
import dodgeball.*;

public class GetOutEvent extends EventObject {

	private Player player;
	
	public GetOutEvent(Object source, Player player) {
		super(source);
		
		this.player = player;
	}
	
	public Player player() {
		return player;
	}
}
