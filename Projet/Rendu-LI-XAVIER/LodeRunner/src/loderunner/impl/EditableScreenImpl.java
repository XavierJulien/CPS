package loderunner.impl;

import loderunner.data.Cell;
import loderunner.services.EditableScreenService;

public class EditableScreenImpl extends ScreenImpl implements EditableScreenService {

	
	@Override
	public boolean isPlayable() {
		for(int i = 0;i<super.getWidth();i++) {
			if(super.getCellNature(i, 0)!=Cell.MTL) {
				 return false;
			}
			for(int j = 1;j<super.getHeight();i++) {
				if(super.getCellNature(i, j)==Cell.HOL) {
					 return false;
				}
			}
		}
		return true;
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		super.screen[x][y] = c;
	}

}
