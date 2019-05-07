package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.decorators.ScreenDecorator;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.services.ScreenService;

public class ScreenContract extends ScreenDecorator {

	public ScreenContract(ScreenService delegate) {
		super(delegate);
	}
	
	public void checkInvariants() {
		//rien à vérifier
	}
	
	@Override
	public int getHeight() {
		//1.pre
		//none
		//2.run
		return super.getHeight();
	}

	@Override
	public int getWidth() {
		//1.pre
		//none
		//2.run
		return super.getWidth();
	}

	@Override
	public Cell getCellNature(int x, int y) {
		//1.pre
		if(x<0) throw new PreconditionError("cellnature : x est inférieur à 0.");
		if(x>=getWidth()) throw new PreconditionError("cellnature : x est supérieur à getWidth().");
		if(y<0) throw new PreconditionError("cellnature : y est inférieur à 0.");
		if(y>=getHeight()) throw new PreconditionError("cellnature : y est supérieur à getHeight().");
		//2.run
		return super.getCellNature(x, y);
	}

	@Override
	public void init(int width, int height) {
		//1.pre
		if(width<0) throw new PreconditionError("init : width est inférieur à 0.");
		if(height<0) throw new PreconditionError("init : height est inférieur à 0.");
		//2.checkInvariants
		//none
		//3.capture
		//none
		//4.run
		super.init(width, height);
		//5.checkInvariants
		//none
		//6.post
		if(getHeight() != height) throw new PostconditionError("init : height est différent de getHeight()");
		if(getWidth() != width) throw new PostconditionError("init : width est différent de getWidth()");
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(getCellNature(i,j) != Cell.EMP)
					throw new PostconditionError("init : Une case n'est pas EMP");
			}
		}
	}

	@Override
	public void dig(int x, int y) {
		//1.pre
		if(getCellNature(x, y) != Cell.PLT) throw new PreconditionError("dig : la case n'est pas un Cell.PLT");
		//2.checkInvariants
		//none
		//3.capture
		Cell[][] screen_capture = new Cell[getWidth()][getHeight()];
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(i != x && j != y) {
					screen_capture[i][j] = getCellNature(i,j);
				}
			}
		}
		//none
		//4.run
		super.dig(x, y);
		//5.checkInvariants
		//none
		//6.post
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(i != x && j != y) {
					if(getCellNature(i,j) != screen_capture[i][j]) 
						throw new PostconditionError("dig : Une autre case à été modifié");
				}
				if(i == x && j == y) {
					if(getCellNature(i,j) != Cell.HOL) 
							throw new PostconditionError("dig : La case n'as pas été modifiée");
				}
			}
		}
	}

	@Override
	public void fill(int x, int y) {
		//1.pre
		if(getCellNature(x, y) != Cell.HOL) throw new PreconditionError("dig : la case n'est pas un Cell.HOL");
		//2.checkInvariants
		//none
		//3.capture
		Cell[][] screen_capture = new Cell[getWidth()][getHeight()];
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(i != x && j != y) {
					screen_capture[i][j] = getCellNature(i,j);
				}
			}
		}
		//4.run
		super.fill(x, y);
		//5.checkInvariants
		//none
		//6.post
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(i != x && j != y) {
					if(getCellNature(i,j) != screen_capture[i][j]) 
						throw new PostconditionError("fill : Une autre case à été modifié");
				}
				if(i == x && j == y) {
					if(getCellNature(i,j) != Cell.PLT) 
						throw new PostconditionError("fill : La case n'as pas été modifiée");
				}
			}
		}
	}


}
