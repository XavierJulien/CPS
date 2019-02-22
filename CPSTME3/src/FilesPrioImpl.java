import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilesPrioImpl<E> implements FilesPrio<E>{

	private List<CustomPair<E>> file;
	
	@Override
	public int getSize() {
		int size = 0;
		if(!file.isEmpty()) {
			for (int i=0; i<file.size() ; i++) {
				size += file.get(i).getList().size();
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
		for(int i = 0;i<file.size();i++) {
			if(file.get(i).getList().size()>0) {
				s.add(file.get(i).getPriorite());
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
		for(int i = 0;i<file.size();i++) {
			int prio_temp = file.get(i).getPriorite();
			if(prio_temp >=prio_max) prio_max = prio_temp; 
		}
		return prio_max;
	}

	@Override
	public int getSizePrio(int priorite) {
		for(int i = 0;i<file.size();i++) {
			int prio_temp = file.get(i).getPriorite();
			if(prio_temp == priorite) return file.get(priorite).getList().size();
		}
		return -1;
	}

	@Override
	public E getPrio(int priorite) {
		return file.get(priorite).getList().get(0);
	}

	@Override
	public E getElem() {
		return null;
	}

	@Override
	public E getElemPrio(int priorite, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		file = new ArrayList<>();
	}

	@Override
	public CustomPair<E> put(E elem) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
