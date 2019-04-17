package loderunner.contracts;

import loderunner.decorators.PlayerDecorator;
import loderunner.services.CharacterService;

public class PlayerContract extends PlayerDecorator{

	public PlayerContract(CharacterService delegate) {
		super(delegate);
	}

	
}
