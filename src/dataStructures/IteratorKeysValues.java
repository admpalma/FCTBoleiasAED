package dataStructures;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

public class IteratorKeysValues<E> implements Iterator<E> {
	
	private Collection<E> set;
	private java.util.Iterator<E> iterator;

	public IteratorKeysValues(Collection<E> set) {
		this.set = set;
		rewind();
	}


	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public E next() throws NoSuchElementException {
		return iterator.next();
	}

	@Override
	public void rewind() {
		this.iterator = set.iterator();
	}

}
