package loderunner.impl;

import loderunner.data.CellContent;
import loderunner.services.EditableScreenService;
import loderunner.services.EnvironnementService;

public class EnvironnementImpl extends ScreenImpl implements EnvironnementService{

	protected int height;
	protected int width;
	protected CellContent[][]screenContent;
	

	@Override
	public CellContent getCellContent(int x, int y) {
		return screenContent[x][y];
	}

	@Override
	public void init(EditableScreenService e) {
		super.init(e.getHeight(), e.getWidth());
		
		for(int i = 0;i<width;i++) {
			for(int j = 0;j<height;j++) {
				super.screen[i][j] = e.getCellNature(i, j);
				screenContent[i][j] = new CellContent(null,null);
			}
		}
	}
}
