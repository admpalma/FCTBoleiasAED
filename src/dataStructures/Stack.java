/**
 * 
 */
package dataStructures;

import java.io.Serializable;

/**
 * @author AED_19_20
 *
 */
public interface Stack<E> extends Serializable {
	// Returns true iff the stack contains no elements.
	boolean isEmpty( );
	 
	// Returns the number of elements in the stack.
	int size( );
	 
	// Returns the element at the top of the stack.
	//@throws NoElementException if isEmpty()
	E top( ) throws NoElementException;
	 
	// Inserts the specified element onto the top of the stack.
	void push( E element );
	 
	// Removes and returns the element at the top of the stack.
	//@throws NoElementException if isEmpty()
	E pop( ) throws NoElementException;
}
