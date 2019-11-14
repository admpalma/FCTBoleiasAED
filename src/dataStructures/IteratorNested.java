package dataStructures;

import fctBoleias.Wrappable;
import fctBoleias.Wrapper;

/**
 * Iterator for {@link Wrappable} objects
 *
 * @param <E> {@link Wrapper}
 * @param <T> {@link Wrappable} object
 */
public class IteratorNested<E, T extends IterableMap<?, ?>> implements Iterator<E> {

	private Iterator<T>[] iterators;
	private Iterator<E> lastIterator;
	
	/**
	 * Constructs an {@link IteratorNested}
	 * 
	 * @param iterator {@link Iterator} of {@link Wrappable Wrappables}
	 */
	@SuppressWarnings("unchecked")
	public IteratorNested(Iterator<T> iterator) {
		int depth = 0;
		Queue<Iterator<T>> its = new QueueInList<Iterator<T>>();
		Iterator<T> newIt = iterator;
		Object it = newIt.next();
		its.enqueue(iterator);
		while (it instanceof IterableMap<?, ?>) {
			newIt = (Iterator<T>) ((T) it).values();
			its.enqueue(newIt);
			depth++;
			it = newIt.next();
		}
		
		iterators = (Iterator<T>[]) new Iterator<?>[depth];
		
		for (int i = iterators.length - 1; i >= 0; i--) {
			iterators[i] = (Iterator<T>) its.dequeue();
		}
		lastIterator = (Iterator<E>) its.dequeue();
		rewind();
	}

	@Override
	public boolean hasNext() {
		boolean hasNext = lastIterator.hasNext();
		if (!hasNext) {
			int i = 0;
			while (!hasNext && i < iterators.length) {
				hasNext = iterators[i++].hasNext();
			} 
		}
		return hasNext;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E next() throws NoSuchElementException {
		if (lastIterator.hasNext()) {
			return lastIterator.next();
		} else {
			boolean hasNext = false;
			int i = 0;
			while (!hasNext && i < iterators.length) {
				hasNext = iterators[i++].hasNext();
			}
			if (hasNext) {
				for (int j = i - 1; j > 0; j--) {
					iterators[j - 1] = (Iterator<T>) iterators[j].next().values();
				}
				lastIterator = (Iterator<E>) iterators[0].next().values();
				return lastIterator.next();
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void rewind() {
		//TODO
		for (int i = iterators.length - 1; i > 0; i--) {
			iterators[i].rewind();
			iterators[i - 1] = (Iterator<T>) iterators[i].next().values();
			iterators[i].rewind();
		}
		lastIterator.rewind();
	}

}
