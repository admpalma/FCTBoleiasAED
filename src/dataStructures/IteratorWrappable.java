package dataStructures;

import fctBoleias.Wrappable;
import fctBoleias.Wrapper;

public class IteratorWrappable<W extends Wrapper, E extends Wrappable<E>> implements Iterator<E> {

	private Iterator<E> iterator;

	public IteratorWrappable(Iterator<E> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public E next() throws NoSuchElementException {
		return iterator.next().wrap();
	}

	@Override
	public void rewind() {
		iterator.rewind();
	}

}
