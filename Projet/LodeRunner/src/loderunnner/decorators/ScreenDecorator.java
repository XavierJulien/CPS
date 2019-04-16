package loderunnner.decorators;

import loderunner.data.Cell;
import loderunner.services.ScreenService;

public abstract class ScreenDecorator implements ScreenService{

	private final ScreenService delegate;
	
	public ScreenDecorator(ScreenService delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public int getHeight() {
		return delegate.getHeight();
	}

	@Override
	public int getWidth() {
		return delegate.getWidth();
	}

	@Override
	public Cell getCellNature(int x, int y) {
		return delegate.getCellNature(x, y);
	}

	@Override
	public void init(int width, int height) {
		delegate.init(width, height);
	}

	@Override
	public void dig(int x, int y) {
		delegate.dig(x, y);
	}

	@Override
	public void fill(int x, int y) {
		delegate.fill(x, y);
	}

}
