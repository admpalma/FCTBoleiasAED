package dataStructures;

public class QueueInArray<E> implements Queue<E> {
	
	private static final int DEFAULTCAPACITY = 1000;
	// Memory of the queue: a circular array.
	protected E[] elementos;
	// Index of the element at the front of the queue.
	protected int front;
	// Index of the element at the rear of the queue.
	protected int rear;
	//Number of elements in the queue.
	protected int counter;
	
	@SuppressWarnings("unchecked")
	public QueueInArray(int cap) {
		elementos = (E []) new Object[cap];
		front=0;
		rear=cap-1;
		counter=0;
	}
	
	public QueueInArray() {
		this(DEFAULTCAPACITY);
	}
	@Override
	public boolean isEmpty() {
		return counter==0;
	}

	@Override
	public int size() {
		return counter;
	}

	// Increments with wrap around.
	protected int nextIndex( int index ){
		return ( index + 1 ) % elementos.length;
	}
	
	@Override
	public void enqueue(E element) throws OutOfCapacityException{
		if ( counter==elementos.length) 
			throw new OutOfCapacityException("Queue is full.");
		rear=nextIndex(rear);
		elementos[rear]=element;
		counter++;
	}

	@Override
	public E dequeue() throws NoElementException {
		if (isEmpty())
			throw new NoElementException("Queue is empty.");
		E elem=elementos[front];
		front=nextIndex(front);
		counter--;
		return elem;
	}

}
