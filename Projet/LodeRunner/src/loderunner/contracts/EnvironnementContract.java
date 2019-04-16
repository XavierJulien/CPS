package loderunner.contracts;

import loderunner.data.CellContent;
import loderunner.services.EditableScreenService;
import loderunner.services.ScreenService;
import loderunnner.decorators.EnvironnementDecorator;

public class EnvironnementContract extends EnvironnementDecorator{

	public EnvironnementContract(ScreenService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	public void checkInvariants() {
		
	}
	
	@Override
	public CellContent getCellContent(int x, int y) {
		//1.pre
		//2.run
		return null;
		//return delegate.getCellContent(x, y);
	}

	@Override
	public void init(EditableScreenService e) {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		//super.init(e);
		//5.checkInvariants
		//6.post
		
	}

}
