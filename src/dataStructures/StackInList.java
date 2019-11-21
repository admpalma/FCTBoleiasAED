package dataStructures;

public class StackInList<E> implements Stack<E> {

	private List<E> list;
	
	public StackInList() {
		list = new SinglyLinkedList<E>();
	}
	
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public E top() throws NoElementException {
		return list.getLast();
	}

	@Override
	public void push(E element) {
		list.addLast(element);
	}

	@Override
	public E pop() throws NoElementException {
		return list.removeLast();
	}

}
