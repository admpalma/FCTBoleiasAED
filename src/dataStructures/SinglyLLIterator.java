package dataStructures;

public class SinglyLLIterator<E> implements Iterator<E> {

	private SListNode<E> firstNode;
	private SListNode<E> nextToReturn;

	public SinglyLLIterator(SListNode<E> head) {
		firstNode = head;
		rewind();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public boolean hasNext() {
		return nextToReturn != null;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public E next() throws NoSuchElementException {
		if (!this.hasNext()) {
			throw new NoSuchElementException("No more elements.");
		}
		E element = nextToReturn.getElement();

		nextToReturn = nextToReturn.getNext();

		return element;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public void rewind() {
		nextToReturn = firstNode;
	}

}
