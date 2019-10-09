package dataStructures;

public class QueueInList<E> implements Queue<E> {
	
	protected List<E> elements;
	
	public QueueInList(){
		elements= new SinglyLinkedList<E>();
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
	public void enqueue(E element) {
		elements.addLast(element);
	}

	@Override
	public E dequeue() throws NoElementException {
		if (isEmpty())
			throw new NoElementException("Queue is empty.");
		return elements.removeFirst();
	}

}
