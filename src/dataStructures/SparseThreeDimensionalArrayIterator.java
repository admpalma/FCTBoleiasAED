package dataStructures;

/**
 * Iterator of sparce three dimensional arrays
 * @param <E>
 */
public class SparseThreeDimensionalArrayIterator<E> implements Iterator<E> {

	private int size;
	private int remaining;
	private E[][][] elements;
	private int i;
	private int j;
	private int k;
	
	/**
	 * Constructs a new iterator for the given <code>elements</code> array with <code>size</code> number of elements
	 * @param elements
	 * @param size
	 */
	public SparseThreeDimensionalArrayIterator(E[][][] elements, int size) {
		this.elements = elements;
		this.size = size;
		rewind();
	}
	
	/**
	 * O(1) all cases
	 */
	@Override
	public boolean hasNext() {
		return remaining > 0;
	}

	/**
	 * Best case: O(1)
	 * Average case: O(1+y), y is fullness factor
	 * Worst case: O(n), n = Sum(length of each of the three arrays)
	 */
	@Override
	public E next() throws NoSuchElementException {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		E next = null;
		while (next == null) {
			if (elements[i][j] == null || !(k < elements[i][j].length)) {
				k = 0;
				j++;
			}
			if (elements[i] == null || !(j < elements[i].length)) {
				j = 0;
				i++;
			}
			assert (i < elements.length);
			next = elements[i][j][k++];
		}
		remaining--;
		return next;
	}

	/**
	 * O(1) all cases
	 */
	@Override
	public void rewind() {
		remaining = size;
		i = j = k = 0;
	}

}
