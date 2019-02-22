import java.util.List;

public class CustomPair<T> {
	private int prio;
	private List<T> value;
	public CustomPair(int prio,List<T> l) {
		this.prio = prio;
		this.value = l;
	}
	public int getPriorite() {
		return prio;
	}
	public List<T> getList() {
		return value;
	}
}
