/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public class Array<E> implements List<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_SIZE = 50;

	/**
	 * O array generico
	 */
	protected E[] array;

	/**
	 * O numero de elementos actual
	 */
	protected int counter;

	/** Construtor que define um array com uma dada dimensao inicial */

	@SuppressWarnings("unchecked")
	public Array(int capacity) {
		array = (E[]) new Object[capacity];
		counter = 0;
	}

	public Array() {
		this(DEFAULT_SIZE);
	}

	@Override
	/**
	 * Best case: O(1)
	 * Average case: O(1)
	 * Worst case: O(n) if we resize
	 */
	public void addLast(E elem) {
		if (isFull())
			resize();
		array[counter++] = elem;
	}

	@Override
	/**
	 * Best case: O(n) shift all to the right
	 * Average case: O(n) shift left elements to the right
	 * Worst case: O(n) resize and shift
	 */
	public void add(int pos, E elem) throws InvalidPositionException {
		if (pos < 0 || pos > counter)
			throw new InvalidPositionException("Invalid Position.");
		if (isFull())
			resize();
		for (int i = counter - 1; i >= pos; i--)
			array[i + 1] = array[i];
		array[pos] = elem;
		counter++;
	}

	@Override
	/**
	 * O(n) all cases
	 */
	public void addFirst(E elem) {
		if (isFull())
			resize();
		add(0, elem);
	}

	/** Metodo auxiliar para duplicar o tamanho do vector. */
	@SuppressWarnings("unchecked")
	/**
	 * O(n) all cases
	 */
	private void resize() {
		E[] tmp = (E[]) new Object[counter * 2];
		for (int i = 0; i < counter; i++)
			tmp[i] = array[i];
		array = tmp;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public E removeLast() throws NoElementException {
		if (counter == 0)
			throw new NoElementException("No such element.");
		return array[--counter];
	}

	@Override
	/**
	 * Best case: O(1) remove last
	 * Average case: O(n) shift to the left
	 * Worst case: O(n) shift to the left
	 */
	public E remove(int pos) throws InvalidPositionException {
		if (pos < 0 || pos >= counter)
			throw new InvalidPositionException("Invalid position.");
		E elem = array[pos];
		for (int i = pos; i < counter - 1; i++)
			array[i] = array[i + 1];
		counter--;
		return elem;
	}

	@Override
	/**
	 * Best case: O(1) if counter == 1
	 * Average case: O(n)
	 * Worst case: O(n)
	 */
	public E removeFirst() throws NoElementException {
		if (counter == 0)
			throw new NoElementException("No such element.");
		return remove(0);
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public int size() {
		return counter;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public E get(int pos) throws InvalidPositionException {
		if (pos < 0 || pos >= counter)
			throw new InvalidPositionException("Invalid position.");
		;
		return array[pos];
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public Iterator<E> iterator() throws NoElementException {
		if (counter == 0)
			throw new NoElementException("Array is empty.");
		return new ArrayIterator<E>(array, counter);
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public E getFirst() throws NoElementException {
		if (counter == 0)
			throw new NoElementException("No such element.");
		return get(0);
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public E getLast() throws NoElementException {
		if (counter == 0)
			throw new NoElementException("No such element.");
		return get(counter - 1);
	}

	@Override
	/**
	 * Best case: O(1)
	 * Average case: O(n)
	 * Worst case: O(n)
	 */
	public int find(E elem) {
		boolean found = false;
		int i = 0;
		while (i < counter && !found)
			if (array[i].equals(elem))
				found = true;
			else
				i++;
		if (found)
			return i;
		else
			return -1;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public boolean isEmpty() {
		return counter == 0;
	}

	/**
	 * O(1) all cases
	 */
	public boolean isFull() {
		return counter == array.length;
	}

	/**
	 * O(1) all cases
	 */
	public int capacity() {
		return array.length;
	}
}
