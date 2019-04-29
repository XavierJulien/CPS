package loderunner.impl;

import loderunner.services.CharacterService;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;

public class CharacterImpl implements CharacterService{

	private EnvironnementService envi;
	private int hgt,wdt;
	
	@Override
	public EnvironnementService getEnvi() {
		return envi;
	}

	@Override
	public int getHgt() {
		return hgt;
	}

	@Override
	public int getWdt() {
		return wdt;
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		envi = (EnvironnementService)s;
		wdt = x;
		hgt = y;
	}

	@Override
	public void goLeft() {
		wdt -= 1;
	}

	@Override
	public void goRight() {
		wdt += 1;
	}

	@Override
	public void goUp() {
		hgt +=1;
	}

	@Override
	public void goDown() {
		hgt -= 1;
	}
}
