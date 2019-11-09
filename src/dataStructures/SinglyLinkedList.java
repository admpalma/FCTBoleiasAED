package dataStructures;

public class SinglyLinkedList<E> implements List<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Node at the head of the list.
	protected SListNode<E> head;

	// Node at the tail of the list.
	protected SListNode<E> tail;

	// Number of elements in the list.
	protected int currentSize;

	public SinglyLinkedList() {
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
	public Iterator<E> iterator() throws NoElementException {
		return new SinglyLLIterator<E>(head);
	}

	@Override
	public int find(E element) {

		int pos = 0;

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
		if (isEmpty())
			throw new NoElementException();
		return head.getElement();
	}

	@Override
	public E getLast() throws NoElementException {
		if (isEmpty())
			throw new NoElementException();
		return tail.getElement();
	}

	@Override
	public E get(int position) throws InvalidPositionException {
		if (position < 0 || position >= currentSize)
			throw new InvalidPositionException("Invalid position.");
		return getNode(position).getElement();
	}

	private SListNode<E> getNode(int position) {
		SListNode<E> aux = head;
		for (int i = 1; i <= position; i++)
			aux = aux.getNext();
		return aux;
	}

	@Override
	public void addFirst(E element) {

		if (currentSize == 0) {
			SListNode<E> newNode = new SListNode<E>(element, null);
			head = newNode;
			tail = newNode;
		} else {
			SListNode<E> newNode = new SListNode<E>(element, head);
			head = newNode;
		}

		currentSize++;
	}

	@Override
	public void addLast(E element) {
		if (currentSize == 0) {
			addFirst(element);
		} else {
			SListNode<E> newNode = new SListNode<E>(element, null);
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

			SListNode<E> previous = getNode(position - 1);
			SListNode<E> aux = previous.getNext();

			SListNode<E> newNode = new SListNode<E>(element, aux);

			previous.setNext(newNode);

			currentSize++;

		}

	}

	@Override
	public E removeFirst() throws NoElementException {
		if (currentSize == 0)
			throw new NoElementException("No such element.");

		SListNode<E> auxHead = head;

		if (currentSize == 1) {
			head = null;
			tail = null;
		} else {
			SListNode<E> newHead = head.getNext();
			head = newHead;
		}

		currentSize--;

		return auxHead.getElement();
	}

	@Override
	public E removeLast() throws NoElementException {
		if (currentSize == 0)
			throw new NoElementException("No such element.");

		SListNode<E> auxTail = tail;

		if (currentSize == 1) {
			head = null;
			tail = null;
		} else {
			SListNode<E> newTail = getNode(currentSize - 2);
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

		SListNode<E> previous = getNode(position - 1);
		SListNode<E> aux = previous.getNext();

		previous.setNext(aux.getNext());

		currentSize--;

		return aux.getElement();
	}

}
