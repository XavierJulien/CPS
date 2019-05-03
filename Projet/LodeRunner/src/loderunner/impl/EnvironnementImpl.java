package loderunner.impl;

import loderunner.data.Cell;
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
		super.init(e.getWidth(), e.getHeight());
		this.height = super.getHeight();
		this.width = super.getWidth();
		screenContent = new CellContent[width][height];
		for(int i = height-1;i>=0;i--) {
			for(int j = 0;j<width;j++) {
				super.screen[j][i] = e.getCellNature(j, i);
				screenContent[j][i] = new CellContent();
			}
		}
	}
	
	public String cellcont(CellContent c) {
		if (c.getGuard() != null) return "G";
		if (c.getCharacter() != null) return "&";
		if (c.getItem() != null) return "@";
		return "";
	}
	
	public String cellnat(Cell c) {
		switch(c) {
			case EMP : return " ";
			case PLT : return "=";
			case HOL : return "U";
			case MTL : return "X";
			case LAD : return "H";
			case HDR : return "-";
		}
		return null;
	}
	
	public String toString() {
		String s = "";
		for(int i = height-1; i >= 0;i--) {
			for(int j = 0;j < width;j++) {
				if(getCellContent(j, i).getItem() != null) s +=cellcont(getCellContent(j, i));
				else{
					if(getCellContent(j, i).getGuard() != null) {
						s +=cellcont(getCellContent(j, i));
					}else {
						if(getCellContent(j, i).getCharacter() != null) {s +=cellcont(getCellContent(j, i));}
						else{s+=cellnat(getCellNature(j, i));}
					}
				}
			}
			s+= "\n";
		}
		/*for(int i = height-1; i >= 0;i--) {
			for(int j = 0;j < width;j++) {
				s+=" "+cellcont(getCellContent(j, i))+" ";
			}
			s+= "\n";
		}*/
		return s;
	}
}
