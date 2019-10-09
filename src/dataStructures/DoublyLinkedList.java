package dataStructures;

public class DoublyLinkedList<E> implements TwoWayList<E>  {
	// Node at the head of the list.
	protected DListNode<E> head;

	// Node at the tail of the list.
	protected DListNode<E> tail;

	// Number of elements in the list.
	protected int currentSize;
	
	public DoublyLinkedList( ){
		head = null;
		tail = null;
		currentSize = 0;
	}

	@Override
	public boolean isEmpty() {
		//TODO
		return false;
	}

	@Override
	public int size() {
		//TODO
		return 0;
	}
	

	@Override
	public TwoWayIterator<E> iterator() throws NoElementException {
		if (currentSize==0) throw new NoElementException("List is empty.");
		return new DoublyLLIterator<E>(head,tail);
	}
	

	@Override
	public int find(E element) {
		int pos=0;
		DListNode<E> auxNo;
		boolean found=false;
		//TODO
		return -1;
	}
	
	@Override
	public E getFirst() throws NoElementException {
		if (currentSize==0) throw new NoElementException("No such element.");
		return getNode(0).getElement();
	}
	
	@Override
	public E getLast() throws NoElementException {
		if (currentSize==0) throw new NoElementException("No such element.");
		return getNode(currentSize-1).getElement();
	}
	
	@Override
	public E get(int position) throws InvalidPositionException {
		if (position<0 || position>=currentSize) 
			throw new InvalidPositionException("Invalid position.");
		return getNode(position).getElement();
	}
	
	@Override
	public void addFirst(E element) {
	//TODO
	}

	@Override
	public void addLast(E element) {
	// TODO
	}
	
	@Override
	public void add(int position, E element) throws InvalidPositionException {
		if (position<0 || position >currentSize) 
			throw new InvalidPositionException("Invalid Position.");
		if (position==0) 
			addFirst(element);
		else if (position==currentSize) 
				addLast(element);
			else {
				addMiddle(position,element);
			}
		
	}
	

	private void addMiddle(int position, E element) {
		DListNode<E> aux=getNode(position);
		//TODO
	}

	private E removeMiddle(int position) {
		DListNode<E> aux=getNode(position);
		//TODO
		return null;
	}

	private DListNode<E> getNode(int position){
		DListNode<E> aux=head;
		for(int i=1;i<=position;i++)
			aux=aux.getNext();
		return aux;
	}
	@Override
	public E removeFirst() throws NoElementException {
		if (currentSize==0) 
			throw new NoElementException("No such element.");
		// TODO
		// Cuidado: lista com 1 elemento
		return null;
	}


	@Override
	public E removeLast() throws NoElementException {
		if (currentSize==0) 
			throw new NoElementException("No such element.");
		// TODO
		// Cuidado: lista com 1 elemento
		return null;
	}

	@Override
	public E remove(int position) throws InvalidPositionException {
		if(position<0 || position>=currentSize)
			throw new InvalidPositionException("Invalid position.");
		if (position==0)
			return removeFirst();
		if (position==currentSize-1)
			return removeLast();
		return removeMiddle(position);
	}


}
