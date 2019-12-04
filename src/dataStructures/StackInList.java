package dataStructures;

public class StackInList<E> implements Stack<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Memory of the stack: an array.
	protected List<E> elements;

	public StackInList() {
		elements = new SinglyLinkedList<E>();
	}

	@Override
	/**
	 * O(1)
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	/**
	 * O(1)
	 */
	public int size() {
		return elements.size();
	}

	@Override
	/**
	 * O(1)
	 */
	public E top() throws NoElementException {
		if (isEmpty())
			throw new NoElementException("Stack is empty.");
		return elements.getFirst();
	}

	@Override
	/**
	 * O(1)
	 */
	public void push(E element) {
		elements.addFirst(element);
	}

	@Override
	/**
	 * O(1)
	 */
	public E pop() throws NoElementException {
		if (isEmpty())
			throw new NoElementException("Stack is empty.");
		return elements.removeFirst();
	}

}
