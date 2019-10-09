/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public interface Queue<E> {
	// Returns true iff the queue contains no elements.
	boolean isEmpty( );
	 
	// Returns the number of elements in the queue.
	int size( );
	 
	// Inserts the specified element at the rear of the queue.
	void enqueue( E element );
	 
	// Removes and returns the element at the front of the queue.
	//@throws NoElementException if isEmpty()
	E dequeue( ) throws NoElementException;

}
