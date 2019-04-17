package loderunner.decorators;

import loderunner.data.Cell;
import loderunner.services.EditableScreenService;
import loderunner.services.ScreenService;

public abstract class EditableScreenDecorator extends ScreenDecorator implements EditableScreenService{

	public EditableScreenDecorator(ScreenService delegate) {
		super(delegate);
	}

	public EditableScreenService getDelegate() {
		return (EditableScreenService)super.getDelegate();
	}
	@Override
	public boolean isPlayable() {
		return getDelegate().isPlayable();
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		getDelegate().setNature(x, y, c);
	}
}
