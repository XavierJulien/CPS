package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.data.CellContent;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.services.CharacterService;
import loderunner.services.EditableScreenService;
import loderunner.services.EnvironnementService;

public class EnvironnementContract extends ScreenContract implements EnvironnementService{
	
	private EnvironnementService delegate;
	
	public EnvironnementContract(EnvironnementService delegate) {
		super(delegate);
		this.delegate = delegate;
	}
	
	public void checkInvariants() {
		for(int i=0;i<getWidth();i++) {
			for(int j=0;j<getHeight();j++) {
				CharacterService c1 = getCellContent(i, j).getCharacter();
				CharacterService c2 = getCellContent(i, j).getCharacter();
				if(c1 != c2) throw new InvariantError(" deux client extraits de la même case sont différents");
				if(getCellNature(i, j) == Cell.MTL || getCellNature(i, j) == Cell.PLT || getCellNature(i, j) == Cell.TLP) {
					if(getCellContent(i, j).getCharacter() != null || getCellContent(i, j).getGuard() != null || getCellContent(i, j).getItem() != null) throw new InvariantError(" une case qui est un Cell.MTL ou Cell.PLT ou Cell.TLP à son contenu qui est différent de null");
				}
				if(getCellContent(i, j).getItem() != null) {
					if(getCellNature(i, j) != Cell.EMP || (getCellNature(i, j-1) != Cell.MTL && 
														   getCellNature(i, j-1) != Cell.PLT && 
														   getCellNature(i, j-1) != Cell.TLP &&
														   getCellContent(i, j-1).getGuard() == null)) {
						throw new InvariantError("la cellule contient un trésor mais pourtant la case est différent de Cell.EMP et la case à height-1 est différente de Cell.MTL ou Cell.PLT ou Cell.TLP");
					}
				}
			}
		}
	}
	
	@Override
	public CellContent getCellContent(int x, int y) {
		//1.pre
		if(x<0) throw new PreconditionError("cellcontent : x est inférieur à 0.");
		if(x>=getWidth()) throw new PreconditionError("cellcontent : x est supérieur à getWidth().");
		if(y<0) throw new PreconditionError("cellcontent : y est inférieur à 0.");
		if(y>=getHeight()) throw new PreconditionError("cellcontent : y est supérieur à getHeight().");
		//2.run
		return delegate.getCellContent(x, y);
	}

	@Override
	public String toString() {
		//1.pre
		//none
		//2.run
		return delegate.toString();
	}
	
	@Override
	public void init(EditableScreenService e) {
		//1.pre
		//none
		//2.run
		delegate.init(e);
		//3.checkInvariants
		checkInvariants();
		//4.post
		for(int i = 0;i<getWidth();i++) {
			for(int j = 0;j<getHeight();j++) {
				if(getCellNature(i,j) != e.getCellNature(i, j)) 
					throw new PostconditionError("init : Les cases on mal été initialisés");
			}
		}
		
	}

}
