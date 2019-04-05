import java.util.Set;

public abstract class FilesPrioDecorator<T> implements FilesPrio<T> {
	
	private FilesPrio<T> delegate;
	
	public FilesPrioDecorator(FilesPrioImpl<T> f) {this.delegate = f;}
	
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
	public T getPrio(int priorite) {return delegate.getPrio(priorite);}
	@Override
	public T getElem() {return delegate.getElem();}
	@Override
	public T getElemPrio(int priorite, int index) {return delegate.getElemPrio(priorite, index);}
	@Override
	public void init() {delegate.init();}
	@Override
	public FilesPrio<T> put(T elem) {return delegate.put(elem);}
	@Override
	public FilesPrio<T> putPrio(int priorite, T elem) {return delegate.putPrio(priorite, elem);}
	@Override
	public FilesPrio<T> remove() {return delegate.remove();}
	@Override
	public FilesPrio<T> removePrio(int priorite) {return delegate.removePrio(priorite);}
}
