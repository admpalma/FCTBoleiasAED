package dataStructures;

public class SinglyLinkedList<E> implements List<E> {
	// Node at the head of the list.
	protected SListNode<E> head;

	// Node at the tail of the list.
	protected SListNode<E> tail;

	// Number of elements in the list.
	protected int currentSize;
		

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<E> iterator() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int find(E element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public E getFirst() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E getLast() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E get(int position) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFirst(E element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLast(E element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(int position, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub

	}

	@Override
	public E removeFirst() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E removeLast() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E remove(int position) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

}
