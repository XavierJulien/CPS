import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class FilesPrioContract<T> extends FilesPrioDecorator<T> {

	public FilesPrioContract(FilesPrioImpl<T> f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void checkInvariants() {
		// \inv \forall i \in getActivePrios() {getSize() == \sum getSizePrio(i)}
		int sommeSize = 0;
		for (int i : getActivePrios()) {
			sommeSize += getSizePrio(i);
		}
		if (getSize() != sommeSize) 
			throw new InvariantError("Invariant failed : getSize() = \\sum getSizePrio(i)");
		
		// \inv isEmpty() == (getSize() == 0)
		if (isEmpty() != (getSize()==0))
			throw new InvariantError("Invariant failed : isEmpty() = (getSize() == 0)" );
		
		// \inv \forall int i \with i>=0 {isActive(i) == (i \isin getActivePrios())}
		int[] prios = {0,10,50};
		for (int i : prios) {
			if (isActive(i) != (getActivePrios().contains(i)))
				throw new InvariantError("Invariant failed : \\forall int i \\with i>=0 isActive(i) = (i \\isin getActivePrios())");
		}
			
		// \inv getMaxPrio() = \max getActivesPrios() 
		if (getMaxPrio() != Collections.max(getActivePrios()))
			throw new InvariantError("Invariant failed : getMaxPrio() = \\max getActivesPrios()");
		
		// \inv \forall int i \with getSizePrio(i) > 0 {getPrio(i) == getElemPrio(i,1)}
		for (int i=0;i<20;i++) {
			if (getSizePrio(i)==0)
				continue;
			else {
				if (getPrio(i) != getElemPrio(i, 1))
					throw new InvariantError("Invariant failed : \\forall int i \\with getSizePrio(i) > 0 {getPrio(i) = getElemPrio(i,1)}");
			}
		}
			
		// \inv getElem() == getPrio(getMaxPrio())
		if (getElem() != getPrio(getMaxPrio()))
			throw new InvariantError("Invariant failed : getElem() = getPrio(getMaxPrio())");
		
		// \inv \forall i \in getActivePrios() {getSizePrio(i) > 0}
		for (int i : getActivePrios()) {
			if (getSizePrio(i) >= 0)
				throw new InvariantError("Invariant failed : \\forall i \\in getActivePrios() {getSizePrio(i) > 0}");
		}
		// \inv \forall int i \notin getActivePrios() {getSizePrio(i) == 0}
		for (int i=0; i<30; i++) {
			if (getActivePrios().contains(i))
				continue;
			if (getSizePrio(i) != 0)
				throw new InvariantError("Invariant failed : \\forall int i \\notin getActivePrios() {getSizePrio(i) = 0}");
		}
		
		// \inv \forall i \in getActivePrios() \forall int k \from 1 \to getSizePrio(i) {getElemPrio(i,k) != null}
		for (int i : getActivePrios()) {
			for (int k=1; k<getSizePrio(i); k++) {
				if (getElemPrio(i,k) == null)
					throw new InvariantError("Invariant failed : \\forall i \\in getActivePrios() \\forall int k \\from 1 \\to getSizePrio(i) {getElemPrio(i,k) != null}");
			}
		}
	}
	
	
	@Override
	public int getSize() {
		// appel 
		return super.getSize();
	}

	@Override
	public boolean isEmpty() {
		// appel
		return super.isEmpty();
	}

	@Override
	public Set<Integer> getActivePrios() {
		// appel
		return super.getActivePrios();
	}

	@Override
	public boolean isActive(int priorite) {
		// appel
		return super.isActive(priorite);
	}

	@Override
	public int getMaxPrio() {
		// appel
		return super.getMaxPrio();
	}

	@Override
	public int getSizePrio(int priorite) {
		// appel
		return super.getSizePrio(priorite);
	}

	@Override
	public T getPrio(int priorite) {
		// \pre getSizePrio(priorite) > 0
		if (super.getSizePrio(priorite) <= 0)
			throw new PrecondError("Pre failed : getSizePrio(priorite) > 0");
		// appel
		return super.getPrio(priorite);
	}

	@Override
	public T getElem() {
		// \pre getSize() > 0
		if (super.getSize() <= 0)
			throw new PrecondError("Pre failed : getSize() > 0");
		// appel
		return super.getElem();
	}

	@Override
	public T getElemPrio(int priorite, int index) {
		// \pre priorite \in getActivePrios()
		if (!getActivePrios().contains(priorite))
			throw new PrecondError("Pre failed : priorite \\in getActivePrios()");
		// \pre 0 < index && index <= getSizePrio(priorite)
		if (index <= 0 || index > super.getSizePrio(priorite))
			throw new PrecondError("Pre failed :  0 < index <= getSizePrio(priorite)");
		// appel
		return super.getElemPrio(priorite, index);
	}

	@Override
	public void init() {
		// appel
		super.init();
		// post-invariant
		checkInvariants();
		// \post getSize() == 0
		if (super.getSize() != 0)
			throw new PostcondError("Post failed : getSize() == 0");
		// \post getActivePrios() == {}
		if (!(super.getActivePrios()).isEmpty())
			throw new PostcondError("Post failed : getActivePrios() == {}");
	}

	@Override
	public FilesPrio<T> put(T elem) {
		// \pre elem != null
		if (elem == null)
			throw new PrecondError("Pre failed : elem == null");
		// pre-invariant 
		checkInvariants();
		// capture
		// appel
		super.put(elem);
		// post-invariant
		checkInvariants();
		// post-condition
		// que faire du def ? est ce qu'il faut refaire les meme post que putPrio ?
		return null;
	}

	@Override
	public FilesPrio<T> putPrio(int priorite, T elem) {
		// \pre i >= 0 && elem != null
		if (priorite < 0 || elem == null)
			throw new PrecondError("Pre failed : i >= 0 && elem != null");
		// pre-invariant 
		checkInvariants();
		// capture
		Set<Integer> getActivePrios_at_pre = getActivePrios();
		
		int getSizePrio_at_pre = getSizePrio(priorite);
		
		HashMap<Integer,Integer> map_prio_getSizePrio_at_pre = new HashMap<>();
		int cpt = 0;
		for (int i=0;i<100;i++) {
			if (i==priorite)
				continue;
			if (getActivePrios().contains(i))
				map_prio_getSizePrio_at_pre.put(i,getSizePrio(i));
			cpt++;
			if (cpt == 5)
				break;
		}
		
		cpt=0;
		HashMap<Integer,T> map_index_getElemPrio_at_pre = new HashMap<>();
		for (int i=2; i<=getSizePrio(priorite)+1; i++) {
			map_index_getElemPrio_at_pre.put(i, getElemPrio(priorite, i-1));
			cpt++;
			if (cpt == 5) {
				break;
			}
		}


		// appel
		FilesPrio<T> res = super.putPrio(priorite, elem);
		// post-invariant
		checkInvariants();
		// \post isActive(priorite) \imp (getActivePrios(priorite) == getActivePrios(priorite)@pre)
		if (isActive(priorite) && (!getActivePrios().equals(getActivePrios_at_pre)))
			throw new PostcondError("Post failed : isActive(priorite) \\imp (getActivePrios(priorite) == getActivePrios(priorite)@pre)");
		// \post !isActive(priorite) \imp (getActivePrios(priorite) == (getActivePrios(priorite)@pre \\union {priorite}))
		getActivePrios_at_pre.add(priorite);
		if ((!isActive(priorite)) && (!(getActivePrios().equals(getActivePrios_at_pre))))
			throw new PostcondError("Post failed : !isActive(priorite) \\imp (getActivePrios(priorite) == (getActivePrios(priorite)@pre \\\\union {priorite}))");
		// \post getSizePrio(priorite) == getSizePrio(priorite)@pre +1
		if (getSizePrio(priorite) != getSizePrio_at_pre+1)
			throw new PostcondError("Post failed : getSizePrio(priorite) == getSizePrio(priorite)@pre +1");
		// \post \forall j \in getActivePrios() \without {priorite} {getSizePrio(j) = getSizePrio(j)@pre}
		for (int j : map_prio_getSizePrio_at_pre.keySet()) {
			if (getSizePrio(j) != map_prio_getSizePrio_at_pre.get(j))
				throw new PostcondError("Post failed :  \\forall j \\in getActivePrios() \\without {priorite} {getSizePrio(j) = getSizePrio(j)@pre}");
		}
		// \post getPrio(i) == elem
		if (!getPrio(priorite).equals(elem))
			throw new PostcondError("Post failed : getPrio(i) == elem");
		// \post \forall k \from 2 \to getSizePio(priorite)+1 {getElemPrio(priorite,k) == getElemPrio(priorite,k-1)@pre}
		for (int k : map_index_getElemPrio_at_pre.keySet()) {
			if (!getElemPrio(priorite, k).equals(map_index_getElemPrio_at_pre.get(k)))
				throw new PostcondError("Post failed : \\forall k \\from 2 \\to getSizePio(priorite)+1 {getElemPrio(priorite,k) == getElemPrio(priorite,k-1)@pre}");
		}
		// \post \forall j \in getActivePrios \without {priorite} \forall k \from 1 \to getSizePrio(j)
		// {getElemPrio(j,k) == getElemPrio(j,k)@pre}
		
		
		return res;
	}

	@Override
	public FilesPrio<T> remove() {
		// pre-condition
		// pre-invariant 
		// capture
		// appel
		// post-invariant
		// post-condition
		return null;
	}

	@Override
	public FilesPrio<T> removePrio(int priorite) {
		// pre-condition
		// pre-invariant 
		// capture
		// appel
		// post-invariant
		// post-condition
		return null;
	}
	
}
