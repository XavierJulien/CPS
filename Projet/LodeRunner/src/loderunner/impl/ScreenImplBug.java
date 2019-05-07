package loderunner.impl;

import loderunner.data.Cell;
import loderunner.services.ScreenService;

public class ScreenImplBug implements ScreenService{

	protected int height;
	protected int width;
	protected Cell[][] screen;
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Cell getCellNature(int x, int y) {
		return screen[x][y];
	}

	@Override
	public void init(int width, int height) {
		this.height = height;
		this.width = width;
		screen = new Cell[width][height];
		for(int i = 0;i<width;i++) {
			for(int j = 0;j<height;j++) {
				screen[i][j] = Cell.EMP;
			}
		}
	}

	@Override
	public void dig(int x, int y) {//bug
		if(screen[x][y] == Cell.HOL)
			screen[x][y] = Cell.PLT;
	}

	@Override
	public void fill(int x, int y) {//bug
		if(screen[x][y] == Cell.PLT)
			screen[x][y] = Cell.HOL;
	}

}
