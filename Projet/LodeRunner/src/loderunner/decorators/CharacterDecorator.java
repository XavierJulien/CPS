package loderunner.decorators;

import loderunner.services.CharacterService;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;

public abstract class CharacterDecorator implements CharacterService{

	protected final CharacterService delegate;
	
	public CharacterDecorator(CharacterService delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public EnvironnementService getEnvi() {
		return delegate.getEnvi();
	}

	@Override
	public int getHgt() {
		return delegate.getHgt();
	}

	@Override
	public int getWdt() {
		return delegate.getWdt();
	}
	
	@Override
	public void setPos(int wdt, int hgt) {
		delegate.setPos(wdt, hgt);
	}
	
	@Override
	public void init(ScreenService s, int x, int y, int id) {
		delegate.init(s, x, y, id);
	}

	@Override
	public void goLeft() {
		delegate.goLeft();
	}

	@Override
	public void goRight() {
		delegate.goRight();
	}

	@Override
	public void goUp() {
		delegate.goUp();
	}

	@Override
	public void goDown() {
		delegate.goDown();
	}

}
