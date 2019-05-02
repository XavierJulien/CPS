package loderunner.data;

public class Hole {
	
	private int x,y,t;
	
	public Hole(int x, int y, int t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}

}
