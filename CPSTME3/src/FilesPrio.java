import java.util.Set;

public interface FilesPrio<E> {

	/*Observators*/
	public int getSize();
	public boolean isEmpty();
	public Set<Integer> getActivePrios();
	public boolean isActive(int priorite);
	public int getMaxPrio();
	public int getSizePrio(int priorite);
	// \pre getSizePrio(priorite) > 0 
	public E getPrio(int priorite);
	// \pre getSize() > 0 
	public E getElem();
	// \pre priorite \in activePrios()
	// \pre index>0 
	// \pre index<=getSizePrio(priorite)
	public E getElemPrio(int priorite, int index); 
	
	/*Invariants*/
	
	
	/*Constructors*/
	public void init();
	
	/*Operators*/
	// \pre elem!=null
	public CustomPair<E> put(E elem);
	
}
