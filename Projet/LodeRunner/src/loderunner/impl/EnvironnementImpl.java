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
		super.init(e.getHeight(), e.getWidth());
		this.height = super.getHeight();
		this.width = super.getWidth();
		screenContent = new CellContent[width][height];
		for(int i = 0;i<width;i++) {
			for(int j = 0;j<height;j++) {
				super.screen[i][j] = e.getCellNature(i, j);
				screenContent[i][j] = new CellContent();
			}
		}
	}
	
	public String cellcont(CellContent c) {
		if (c.getCharacter() != null) return "&";
		if (c.getCharacter() == null) {
			if(c.getItem() == null) return "0";
			return "@";
		}
		return null;
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
				if(getCellContent(j, i).getItem() != null) s +="@";
				if(getCellContent(j, i).getCharacter() != null) {s +="&";}
				else{s+=cellnat(getCellNature(j, i));}
			}
			s+= "\n";
		}
		s+="\n\n";
		for(int i = height-1; i >= 0;i--) {
			for(int j = 0;j < width;j++) {
				s+=" "+cellcont(getCellContent(j, i))+" ";
			}
			s+= "\n";
		}
		return s;
	}
}
