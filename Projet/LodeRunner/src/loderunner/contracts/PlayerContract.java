package loderunner.contracts;

import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public class PlayerContract extends CharacterContract implements PlayerService{

	private final PlayerService delegate;
	
	public PlayerContract(PlayerService delegate) {
		super(delegate);
		this.delegate = delegate;
	}
	
	/*@Override
	protected PlayerService getDelegate() {
		return (PlayerService) super.getDelegate();
	}*/

	public void checkInvariants() {
		//todo
	}
	@Override
	public EngineService getEngine() {
		//1.pre
		//none
		//2.run
		return delegate.getEngine();
	}

	@Override
	public void step() {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		//3.capture
		//4.run
		delegate.step();
		//5.checkInvariants
		checkInvariants();
		//6.post
	}

	
}
