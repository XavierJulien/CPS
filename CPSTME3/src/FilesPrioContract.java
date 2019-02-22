import java.util.Set;

public class FilesPrioContract<E> extends FilesPrioDecorator<E>{
	public FilesPrioContract(FilesPrioImpl<E> e) {

		super(e);
	}
	@Override
	public CustomPair<E> put(E elem) {
		return null;
		
	}
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set<Integer> getActivePrios() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isActive(int priorite) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getMaxPrio() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getSizePrio(int priorite) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public E getPrio(int priorite) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public E getElem() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public E getElemPrio(int priorite, int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
