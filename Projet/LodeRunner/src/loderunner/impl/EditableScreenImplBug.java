package loderunner.impl;

import loderunner.data.Cell;
import loderunner.services.EditableScreenService;

public class EditableScreenImplBug extends ScreenImpl implements EditableScreenService {

	
	@Override
	public boolean isPlayable() {
		return true;
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		super.screen[x][y] = c;
	}

	public String cellres(Cell c) {
		switch(c) {
			case EMP : return "0";
			case PLT : return "P";
			case HOL : return "H";
			case MTL : return "M";
			case LAD : return "L";
			case HDR : return "R";
		}
		return null;
	}
	
	public String toString() {
		String s = "";
		for(int i = getHeight()-1; i>=0;i--) {
			for(int j = getWidth()-1;j>=0;j--) {
				s+=" "+cellres(getCellNature(j, i))+" ";
			}
			s+= "\n";
		}
		return s;
	}
}
