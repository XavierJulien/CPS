package loderunnner.decorators;

import loderunner.data.CellContent;
import loderunner.services.EditableScreenService;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;

public class EnvironnementDecorator extends ScreenDecorator implements EnvironnementService{

	//private final EnvironnementService delegate;
	
	public EnvironnementDecorator(ScreenService delegate) {
		super(delegate);
		//this.delegate = (EnvironnementService) delegate;
	}

	@Override
	public CellContent getCellContent(int x, int y) {
		return null;
		//return delegate.getCellContent(x, y);
	}

	@Override
	public void init(EditableScreenService e) {
		//delegate.init(e);
	}

}
