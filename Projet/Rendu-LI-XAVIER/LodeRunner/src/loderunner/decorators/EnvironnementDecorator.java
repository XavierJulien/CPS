package loderunner.decorators;

import loderunner.data.CellContent;
import loderunner.services.EditableScreenService;
import loderunner.services.EnvironnementService;

public abstract class EnvironnementDecorator extends ScreenDecorator implements EnvironnementService{

	public EnvironnementDecorator(EnvironnementService delegate) {//prend un environnnement service et as une methode gedelegate qui renvoie unn cast sur le super.getDelgate()
		super(delegate);
	}

	public EnvironnementService getDelegate() {
		return (EnvironnementService)super.getDelegate();
	}
	
	@Override
	public CellContent getCellContent(int x, int y) {
		return getDelegate().getCellContent(x, y);
	}

	@Override
	public void init(EditableScreenService e) {
		getDelegate().init(e);
	}

}
