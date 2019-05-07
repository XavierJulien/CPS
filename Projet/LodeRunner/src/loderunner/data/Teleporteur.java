package loderunner.data;

public class Teleporteur {

	private Coord posA,posB;
	
	public Teleporteur(Coord posA,Coord posB) {
		this.posA = posA;
		this.posB = posB;
	}
	
	public Coord getPosA() {
		return posA;
	}
	public Coord getPosB() {
		return posB;
	}
}
