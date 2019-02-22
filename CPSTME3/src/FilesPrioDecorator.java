import java.util.Set;

public class FilesPrioDecorator<E> implements FilesPrio<E> {
	
	private FilesPrioImpl<E> delegate;
	public FilesPrioDecorator(FilesPrioImpl<E> f) {delegate = f;}
	@Override
	public int getSize() {return delegate.getSize();}
	@Override
	public boolean isEmpty() {return delegate.isEmpty();}
	@Override
	public Set<Integer> getActivePrios() {return delegate.getActivePrios();}
	@Override
	public boolean isActive(int priorite) {return delegate.isActive(priorite);}
	@Override
	public int getMaxPrio() {return delegate.getMaxPrio();}
	@Override
	public int getSizePrio(int priorite) {return delegate.getSizePrio(priorite);}
	@Override
	public E getPrio(int priorite) {return delegate.getPrio(priorite);}
	@Override
	public E getElem() {return delegate.getElem();}
	@Override
	public E getElemPrio(int priorite, int index) {return delegate.getElemPrio(priorite, index);}
	@Override
	public CustomPair<E> put(E elem) {return delegate.put(elem);}
	@Override
	public void init() {delegate.init();}
}
