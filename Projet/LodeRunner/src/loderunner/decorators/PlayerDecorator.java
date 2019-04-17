package loderunner.decorators;

import loderunner.services.CharacterService;
import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public abstract class PlayerDecorator extends CharacterDecorator implements PlayerService{

	private PlayerService delegate;
	
	public PlayerDecorator(CharacterService delegate) {
		super(delegate);
		this.delegate = (PlayerService)delegate;
	}

	@Override
	public EngineService getEngine() {
		return delegate.getEngine();
	}

	@Override
	public void step() {
		delegate.step();
	}
	
}
