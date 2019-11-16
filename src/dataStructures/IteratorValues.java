package dataStructures;

public class IteratorValues<V, T extends Entry<?, V>> extends AbstractIteratorWrapper<V, T> implements Iterator<V> {
	
	public IteratorValues(Iterator<T> entries) {
		super(entries);
	}

	@Override
	public V next() throws NoSuchElementException {
		return wrappedIterator.next().getValue();
	}

}
