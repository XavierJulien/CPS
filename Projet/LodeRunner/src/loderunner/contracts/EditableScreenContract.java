package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.services.EditableScreenService;

public class EditableScreenContract extends ScreenContract implements EditableScreenService{

	private EditableScreenService delegate;
	
	public EditableScreenContract(EditableScreenService delegate) {
		super(delegate);
		this.delegate = delegate;
	}
	
	public void checkInvariants() {
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(getCellNature(i,j) == Cell.HOL) 
					throw new InvariantError("Une case est Cell.HOL");
			}
		}
		/*for(int i = 0;i<getWidth();i++) { //vision défensive en avance lors du set de la premiere case a MTL
			if(getCellNature(i, 0) != Cell.MTL) throw new InvariantError("Une case en ("+i+",0) est différent de Cell.MTL");
		}*/
	}
	
	@Override
	public boolean isPlayable() {
		return delegate.isPlayable();
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		//1.pre
		if(x<0) throw new PreconditionError("setNature : x est inférieur à 0.");
		if(x>=getWidth()) throw new PreconditionError("setNature : x est supérieur à getWidth().");
		if(y<0) throw new PreconditionError("setNature : y est inférieur à 0.");
		if(y>=getHeight()) throw new PreconditionError("setNature : y est supérieur à getHeight().");
		//2.checkInvariants
		checkInvariants();
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
		delegate.setNature(x, y, c);
		//5.checkInvariants
		checkInvariants();
		//6.post
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(i != x && j != y) {
					if(getCellNature(i,j) != screen_capture[i][j]) 
						throw new PostconditionError("setNature : Une autre case à été modifié");
				}
				if(i == x && j == y) {
					if(delegate.getCellNature(i,j) != c) {
						throw new PostconditionError("setNature : La case n'as pas été modifiée");
					}
				}
			}
		}
	}
}
