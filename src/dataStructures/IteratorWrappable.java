package dataStructures;

import fctBoleias.Wrappable;
import fctBoleias.Wrapper;

/**
 * Iterator for {@link Wrappable} objects
 *
 * @param <E> {@link Wrapper}
 * @param <T> {@link Wrappable} object
 */
public class IteratorWrappable<E extends Wrapper, T extends Wrappable<E>> implements Iterator<E> {

	private Iterator<T> iterator;

	/**
	 * Constructs an {@link IteratorWrappable}
	 * 
	 * @param iterator {@link Iterator} of {@link Wrappable Wrappables}
	 */
	public IteratorWrappable(Iterator<T> iterator) {
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
