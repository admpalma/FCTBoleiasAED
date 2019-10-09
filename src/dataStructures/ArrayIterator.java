/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public class ArrayIterator<E> implements Iterator<E> {

	private E[] vector;
	private int counter;
	private int current;

	public ArrayIterator(E[] vector, int counter) {
		this.vector = vector;
		this.counter = counter;
		rewind();
	}
	
	@Override
	public boolean hasNext() {
		return current < counter;
	}

	@Override
	public E next() throws NoSuchElementException {
		if (!hasNext()) 
			throw new NoSuchElementException("No more elements.");
		return vector[current++];
	}

	@Override
	public void rewind() {
		current=0;
	}

}
