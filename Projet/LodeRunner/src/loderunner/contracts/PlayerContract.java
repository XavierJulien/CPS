package loderunner.contracts;

import loderunner.services.CharacterService;
import loderunnner.decorators.PlayerDecorator;

public class PlayerContract extends PlayerDecorator{

	public PlayerContract(CharacterService delegate) {
		super(delegate);
	}

	
}
