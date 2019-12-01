package dataStructures;

/**
 * Iterator for the values of the inner of two nested {@link Map Maps}
 *
 * @param <V> inner of the two nested {@link Map Maps'} values
 * @param <T> inner nested {@link Map}
 */
public class NestedMapValuesIterator<V, T extends Map<?, V>> implements Iterator<V> {

	private Iterator<T> nestedMapIterator;
	private Iterator<V> lastIterator;
	
	/**
	 * Constructs an {@link NestedMapValuesIterator}
	 * @param iterator {@link Iterator} of the set of inner nested {@link Map Maps}
	 */
	public NestedMapValuesIterator(Iterator<T> iterator) {
		nestedMapIterator = iterator;
		lastIterator = nestedMapIterator.next().values();
	}
	
	/**
	 * Constructs an {@link NestedMapValuesIterator}
	 * @param outerMap outer nested {@link Map}
	 */
	public NestedMapValuesIterator(Map<?,T> outerMap) {
		this(outerMap.values());
	}

	@Override
	public boolean hasNext() {
		boolean hasNext = lastIterator.hasNext();
		if (!hasNext) {
			hasNext = nestedMapIterator.hasNext();
		}
		return hasNext;
	}

	@Override
	public V next() throws NoSuchElementException {
		if (lastIterator.hasNext()) {
			return lastIterator.next();
		} else {
			lastIterator = nestedMapIterator.next().values();
			return lastIterator.next();
		}
	}

	@Override
	public void rewind() {
		nestedMapIterator.rewind();
		lastIterator = nestedMapIterator.next().values();
	}

}
