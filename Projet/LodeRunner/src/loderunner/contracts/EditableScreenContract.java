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
		boolean ok_es = true;
		for (int i=0;i<getWidth();i++) {
			for (int j=0; j<getHeight();j++) {
				if (j==0) {
					if (getCellNature(i, j) != Cell.MTL)
						ok_es = false;
				}else {
					if (getCellNature(i, j) == Cell.HOL)
						ok_es = false;
				}
			}	
		}
		if(isPlayable() != ok_es) throw new InvariantError("Invariants : IsPlayable pose problème car est faux");
	}
	
	@Override
	public boolean isPlayable() {
		//1.pre
		//none
		//2.run
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
