package dataStructures;

public interface TwoWayList<E> extends List<E> {
	
	// Returns a bidirectional iterator of the elements in the list.
	TwoWayIterator<E> iterator( ) throws NoElementException;
}
