package loderunnner.decorators;

import loderunner.services.CharacterService;
import loderunner.services.PlayerService;

public class PlayerDecorator extends CharacterDecorator implements PlayerService{

	//private final PlayerService delegate;
	
	public PlayerDecorator(CharacterService delegate) {
		super(delegate);
	}
	
}
