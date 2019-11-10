package dataStructures;

import fctBoleias.Wrappable;
import fctBoleias.Wrapper;

public class IteratorWrappable<E extends Wrapper, W extends Wrappable<E>> implements Iterator<E> {

	private Iterator<W> iterator;

	public IteratorWrappable(Iterator<W> iterator) {
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
