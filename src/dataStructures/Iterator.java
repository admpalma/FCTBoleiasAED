/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public interface Iterator<E> {

	// Returns true iff the iteration has more elements.
	// In other words, returns true if next would return an element.
	boolean hasNext( );
	 
	// Returns the next element in the iteration.
	// @throws NoSuchElementException if !hasNext()
	E next( ) throws NoSuchElementException;
	 
	// Restarts the iteration.
	// After rewind, if the iteration is not empty, next will return the first element.
	void rewind( );
}
