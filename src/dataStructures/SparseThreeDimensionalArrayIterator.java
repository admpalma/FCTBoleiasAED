package dataStructures;

/**
 * TODO
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
	 * 
	 * @param elements
	 * @param size
	 */
	public SparseThreeDimensionalArrayIterator(E[][][] elements, int size) {
		this.elements = elements;
		this.size = size;
		rewind();
	}
	
	@Override
	public boolean hasNext() {
		return remaining > 0;
	}

	@Override
	public E next() throws NoSuchElementException {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		E next = null;
		while (next == null) {
			if (!(k < elements[i][j].length)) {
				k = 0;
				j++;
			}
			if (!(j < elements[i].length)) {
				j = 0;
				i++;
			}
			assert (i < elements.length);
			next = elements[i][j][k++];
		}
		remaining--;
		return next;
	}

	@Override
	public void rewind() {
		remaining = size;
		i = j = k = 0;
	}

}
