package dataStructures;

public class QueueInList<E> implements Queue<E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<E> elements;
	
	public QueueInList(){
		elements= new SinglyLinkedList<E>();
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
	public void enqueue(E element) {
		elements.addLast(element);
	}

	@Override
	/**
	 * O(1)
	 */
	public E dequeue() throws NoElementException {
		if (isEmpty())
			throw new NoElementException("Queue is empty.");
		return elements.removeFirst();
	}

}
