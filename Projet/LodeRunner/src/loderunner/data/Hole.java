package loderunner.data;

import java.util.concurrent.atomic.AtomicInteger;

public class Hole {
	
	private int x,y,t,id;
	private static AtomicInteger cpt = new AtomicInteger(0);
	
	public Hole(int x, int y, int t, int id) {
		this.x = x;
		this.y = y;
		this.t = t;
		if (id == -1) {
			this.id = cpt.getAndIncrement();
		}else {
			this.id = id;
		}
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
	public int getId() {
		return id;
	}
	public void setT(int t) {
		this.t = t;
	}

}
