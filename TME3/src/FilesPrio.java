import java.util.Set;

public interface FilesPrio<T> {

	/*Observators*/

	public int getSize();
	public boolean isEmpty();
	public Set<Integer> getActivePrios();
	public boolean isActive(int priorite);
	public int getMaxPrio();
	public int getSizePrio(int priorite);
	// \pre getSizePrio(priorite) > 0
	public T getPrio(int priorite);
	// \pre getSize() > 0
	public T getElem();
	// \pre priorite \in activePrios()
	// \pre index > 0
	// \pre index <= getSizePrio(priorite)
	public T getElemPrio(int priorite, int index);


	/*Invariants*/

	// \inv \forall i \in getActivePrios() {getSize() == \sum getSizePrio(i)}
	// \inv isEmpty() == (getSize() == 0)
	// \inv \forall int i \with i>=0 {isActive(i) == (i \isin getActivePrios())}
	// \inv getMaxPrio() == \max getActivesPrios()
	// \inv \forall i \with getSizePrio(i) > 0 {getPrio(i) == getElemPrio(i,1)}
	// \inv getElem() == getPrio(getMaxPrio())
	// \inv \forall i \in getActivePrios() {getSizePrio(i) > 0}
	// \inv \forall int i \notin getActivePrios() {getSizePrio(i) == 0}
	// \inv \forall i \in getActivePrios() \forall int k \from 1 \to getSizePrio(i) {getElemPrio(i,k) != null}


	/*Constructors*/

	// \post getSize() == 0
	// \post getActivePrios() == {}
	public void init();


	/*Operators*/

	// \pre elem != null
	// \post getActivePrios() == getActivePrios()@pre
	public FilesPrio<T> put(T elem);

	// \pre i >= 0 && elem != null
	// \post isActive(priorite) \imp (getActivePrios(priorite) == getActivePrios(priorite)@pre)
	// \post !isActive(priorite) \imp (getActivePrios(priorite) == (getActivePrios(priorite)@pre \\union {priorite}))
	// \post getSizePrio(priorite) == getSizePrio(priorite)@pre +1
	// \post \forall j \in getActivePrios() \without {priorite} {getSizePrio(j) = getSizePrio(j)@pre}
	// \post getPrio(i) == elem
	// \post \forall k \from 2 \to getSizePio(priorite)+1 {getElemPrio(priorite,k) == getElemPrio(priorite,k-1)@pre}
	// \post \forall j \in getActivePrios \without {priorite} \forall k \from 1 \to getSizePrio(j)
	// {getElemPrio(j,k) == getElemPrio(j,k)@pre}
	public FilesPrio<T> putPrio(int priorite, T elem);

	// \pre getSize() > 0
	// \post remove() == removePrio(getMaxPrio()) (@pre le getMaxPrio() ??)
	public FilesPrio<T> remove();

	// \pre getSizePrio(priorite) > 0
	// \post getSizePrio(priorite) > 1 \imp (getActivePrios() == getActivePrios()@pre)
	// \post getSizePrio(priorite) == 1 \imp (getActivePrios() == getActivePrios()@pre) \without {priorite})
	// \post getSizePrio(priorite) == getSizePrio(priorite)@pre -1
	// \post \forall j \in getActivePrios() \without {priorite} (getSizePrio(j) == getSizePrio(j)@pre)
	// \post \forall k \from 1 \to getSizePrio(priorite)-1 (getElemPrio(priorite,k) == getElemPrio(priorite,k)@pre)
	// \post \forall i \in getActivePrios() \without {priorite} \forall k \from 1 \to getSizePrio(j)
	// {getElemPrio(i,k) == getElemePrio(i,k)@pre}
	public FilesPrio<T> removePrio(int priorite);

}
