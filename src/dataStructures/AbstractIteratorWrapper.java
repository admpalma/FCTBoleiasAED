package dataStructures;

/**
 * An {@link AbstractIteratorWrapper} is intended to be used when it is preferable
 * to change the return type of {@link Iterator#next()} during iteration rather
 * than during constrution 
 * @param <E> converted {@link Iterator#next() next()} return type
 * @param <T> original {@link Iterator#next() next()} return type
 */
public abstract class AbstractIteratorWrapper<E, T> implements Iterator<E>, Wrapper {

	protected Iterator<T> wrappedIterator;
	
	public AbstractIteratorWrapper(Iterator<T> iterator) {
		wrappedIterator = iterator;
	}
	
	@Override
	public boolean hasNext() {
		return wrappedIterator.hasNext();
	}
	
	@Override
	public abstract E next() throws NoSuchElementException;

	@Override
	public void rewind() {
		wrappedIterator.rewind();
	}

}
