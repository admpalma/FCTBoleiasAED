package dataStructures;

public interface TwoWayIterator<E> extends Iterator<E> {
	
	// Returns true iff the iteration has more elements in the reverse direction.
	// In other words, returns true if previous would return an element.
	boolean hasPrevious( );
	 
	// Returns the previous element in the iteration.
	// @throws NoSuchElementException if !hasPrevious() 
	E previous( ) throws NoSuchElementException;
	 
	// Restarts the iteration in the reverse direction.
	// After fullForward, if iteration is not empty, 
	//previous will return the last element.
	void fullForward( );

}
