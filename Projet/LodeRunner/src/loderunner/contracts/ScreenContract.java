package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.services.ScreenService;
import loderunnner.decorators.ScreenDecorator;

public class ScreenContract extends ScreenDecorator{

	public ScreenContract(ScreenService delegate) {
		super(delegate);
	}
	
	public void checkInvariants() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getHeight() {
		//1.pre
		//2.run
		return super.getHeight();
	}

	@Override
	public int getWidth() {
		//1.pre
		//2.run
		return super.getWidth();
	}

	@Override
	public Cell getCellNature(int x, int y) {
		//1.pre
		//2.run
		return super.getCellNature(x, y);
	}

	@Override
	public void init(int width, int height) {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.init(width, height);
		//5.checkInvariants
		//6.post
		
	}

	@Override
	public void dig(int x, int y) {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.dig(x, y);
		//5.checkInvariants
		//6.post
		
	}

	@Override
	public void fill(int x, int y) {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		super.fill(x, y);
		//5.checkInvariants
		//6.post
		
	}


}
