package dataStructures;

public class DoublyLinkedList<E> implements TwoWayList<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Node at the head of the list.
	protected DListNode<E> head;

	// Node at the tail of the list.
	protected DListNode<E> tail;

	// Number of elements in the list.
	protected int currentSize;

	public DoublyLinkedList() {
		head = null;
		tail = null;
		currentSize = 0;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public TwoWayIterator<E> iterator() throws NoElementException {
		if (currentSize == 0)
			throw new NoElementException("List is empty.");
		return new DoublyLLIterator<E>(head, tail);
	}

	@Override
	public int find(E element) {
		int pos = 0;
		//DListNode<E> auxNode;
		//boolean found = false;
		
		while (pos < currentSize) {
			if (get(pos).equals(element)) {
				return pos;
			} else {
				pos++;
			}
		}
		
		return -1;
	}

	@Override
	public E getFirst() throws NoElementException {
		if (currentSize == 0)
			throw new NoElementException("No such element.");
		return getNode(0).getElement();
	}

	@Override
	public E getLast() throws NoElementException {
		if (currentSize == 0)
			throw new NoElementException("No such element.");
		return getNode(currentSize - 1).getElement();
	}

	@Override
	public E get(int position) throws InvalidPositionException {
		if (position < 0 || position >= currentSize)
			throw new InvalidPositionException("Invalid position.");
		return getNode(position).getElement();
	}

	@Override
	public void addFirst(E element) {

		DListNode<E> newNode = new DListNode<E>(element, null, head);

		if (currentSize == 0) {
			head = newNode;
			tail = newNode;
		} else {
			head.setPrevious(newNode);
			head = newNode;
		}
		
		currentSize++;
		
	}

	@Override
	public void addLast(E element) {

		if (currentSize == 0) {
			addFirst(element);
		} else {
			DListNode<E> newNode = new DListNode<E>(element, tail, null);
			tail.setNext(newNode);
			tail = newNode;
			currentSize++;
		}

	}

	@Override
	public void add(int position, E element) throws InvalidPositionException {
		if (position < 0 || position > currentSize)
			throw new InvalidPositionException("Invalid Position.");
		if (position == 0)
			addFirst(element);
		else if (position == currentSize)
			addLast(element);
		else {
			addMiddle(position, element);
		}

	}

	private void addMiddle(int position, E element) {

		DListNode<E> aux = getNode(position);
		DListNode<E> previous = aux.getPrevious();

		DListNode<E> newNode = new DListNode<E>(element, previous, aux);

		previous.setNext(newNode);
		aux.setPrevious(newNode);
		
		currentSize++;
		
	}

	private E removeMiddle(int position) {
		DListNode<E> aux = getNode(position);

		DListNode<E> auxNext = aux.getNext();
		DListNode<E> auxPrevious = aux.getPrevious();

		auxNext.setPrevious(auxPrevious);
		auxPrevious.setNext(auxNext);
		
		currentSize--;
		
		return aux.getElement();
	}

	private DListNode<E> getNode(int position) {
		DListNode<E> aux = head;
		for (int i = 1; i <= position; i++)
			aux = aux.getNext();
		return aux;
	}

	@Override
	public E removeFirst() throws NoElementException {
		if (currentSize == 0)
			throw new NoElementException("No such element.");

		DListNode<E> auxHead = head;

		if (currentSize == 1) {
			head = null;
			tail = null; // unchecked
		} else {
			DListNode<E> newHead = head.getNext();
			newHead.setPrevious(null);
			head = newHead;
		}
		
		currentSize--;
		
		return auxHead.getElement();
	}

	@Override
	public E removeLast() throws NoElementException {
		if (currentSize == 0)
			throw new NoElementException("No such element.");

		DListNode<E> auxTail = tail;

		if (currentSize == 1) {
			head = null; // unchecked
			tail = null;
		} else {
			DListNode<E> newTail = tail.getPrevious();
			newTail.setNext(null);
			tail = newTail;
		}
		
		currentSize--;
		
		return auxTail.getElement();

	}

	@Override
	public E remove(int position) throws InvalidPositionException {
		if (position < 0 || position >= currentSize)
			throw new InvalidPositionException("Invalid position.");
		if (position == 0)
			return removeFirst();
		if (position == currentSize - 1)
			return removeLast();
		return removeMiddle(position);
	}

}
