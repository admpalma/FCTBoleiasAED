package dataStructures;

public class StackInArray<E> implements Stack<E> {

	// Default capacity of the stack.
	private static final int DEFAULTCAPACITY = 1000;
	
	// Memory of the stack: an array.
	protected List<E> elements;
	
	public StackInArray(int capacity) {
		elements=new Array<E>(capacity);
	}
	
	public StackInArray() {
		this(DEFAULTCAPACITY);
	}
		
	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public E top() throws NoElementException {
		if (isEmpty())
			throw new NoElementException("Stack is empty.");
		return elements.getLast();
	}

	@Override
	public void push(E element) throws OutOfCapacityException{
		if ( ((Array<E>) elements).isFull()) 
			throw new OutOfCapacityException("Stack is full.");
		elements.addLast(element);
	}

	@Override
	public E pop() throws NoElementException {
		if (isEmpty())
			throw new NoElementException("Stack is empty.");
		return elements.removeLast();
	}

}
