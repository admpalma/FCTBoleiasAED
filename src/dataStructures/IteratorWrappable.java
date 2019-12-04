package dataStructures;

/**
 * Iterator for {@link Wrappable} objects
 *
 * @param <E> {@link Wrapper}
 * @param <T> {@link Wrappable} object
 */
public class IteratorWrappable<E extends Wrapper, T extends Wrappable<E>> extends AbstractIteratorWrapper<E, T>
		implements Iterator<E> {

	/**
	 * Constructs an {@link IteratorWrappable}
	 * 
	 * @param iterator {@link Iterator} of {@link Wrappable Wrappables}
	 */
	public IteratorWrappable(Iterator<T> iterator) {
		super(iterator);
	}

	@Override
	public E next() throws NoSuchElementException {
		return wrappedIterator.next().wrap();
	}

}
