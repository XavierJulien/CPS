package loderunner.contracts;

import loderunner.services.CharacterService;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;
import loderunnner.decorators.CharacterDecorator;

public class CharacterContract extends CharacterDecorator {

	public CharacterContract(CharacterService delegate) {
		super(delegate);
	}
	
	public void checkInvariants() {
		
	}
	
	public EnvironnementService getEnvi() {
		//1.pre
		//2.run
		return super.getEnvi();
	}

	@Override
	public int getHgt() {
		//1.pre
		//2.run
		return super.getHgt();
	}

	@Override
	public int getWdt() {
		//1.pre
		//2.run
		return super.getWdt();
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.init(s, x, y);
		//5.checkInvariants
		//6.post
		
	}

	@Override
	public void goLeft() {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.goLeft();
		//5.checkInvariants
		//6.post
		
	}

	@Override
	public void goRight() {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.goRight();
		//5.checkInvariants
		//6.post
	}

	@Override
	public void goUp() {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.goUp();
		//5.checkInvariants
		//6.post
		
	}

	@Override
	public void goDown() {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.goDown();
		//5.checkInvariants
		//6.post
		
	}


}
