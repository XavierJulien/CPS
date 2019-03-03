import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FilesPrioImpl<T> implements FilesPrio<T>{

	private HashMap<Integer, ArrayList<T>> file;
	
	@Override
	public int getSize() {
		int size = 0;
		if(!file.isEmpty()) {
			for (Integer k : file.keySet()){
				size += file.get(k).size();
			}
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		return getSize() == 0;
	}

	@Override
	public Set<Integer> getActivePrios() {
		Set<Integer> s = new HashSet<>();
		for (Integer k : file.keySet()) {
			if(file.get(k).size()>0) {
				s.add(k);
			}
		}
		return s;
	}

	@Override
	public boolean isActive(int priorite) {
		if (getActivePrios().contains(priorite)) return true;
		return false;
	}

	@Override
	public int getMaxPrio() {
		int prio_max = 0;
		for (Integer k : file.keySet()) {
			if (k > prio_max) prio_max = k; 
		}
		return prio_max;
	}

	@Override
	public int getSizePrio(int priorite) {
		if (file.containsKey(priorite)) return file.get(priorite).size();
		return 0;
	}

	@Override
	public T getPrio(int priorite) {
		return getElemPrio(priorite, 1);
	}

	@Override
	public T getElem() {
		return getPrio(getMaxPrio());
	}

	@Override
	public T getElemPrio(int priorite, int index) {
		return file.get(priorite).get(index-1);
	}

	@Override
	public void init() {
		file = new HashMap<Integer,ArrayList<T>>();
	}

	@Override
	public FilesPrio<T> put(T elem) {
		putPrio(getMaxPrio(),elem);
		return this;
	}

	@Override
	public FilesPrio<T> putPrio(int priorite, T elem) {
		if(file.containsKey(priorite)) {
			file.get(priorite).add(0, elem);
		}else{
			ArrayList<T> l = new ArrayList<T>();
			l.add(elem);
			file.put(priorite, l);
		}
		return this;
	}

	@Override
	public FilesPrio<T> remove() {
		return removePrio(getMaxPrio());
	}

	@Override
	public FilesPrio<T> removePrio(int priorite) {
		file.get(priorite).remove(file.get(priorite).size()-1);
		return this;
	}
	

}
