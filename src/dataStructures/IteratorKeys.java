package dataStructures;

public class IteratorKeys<K, T extends Entry<K, ?>> extends AbstractIteratorWrapper<K, T> implements Iterator<K> {
	
	public IteratorKeys(Iterator<T> entries) {
		super(entries);
	}

	@Override
	public K next() throws NoSuchElementException {
		return wrappedIterator.next().getKey();
	}

}
