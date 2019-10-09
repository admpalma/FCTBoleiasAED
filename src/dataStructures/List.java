/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public interface List<E> {
	// Returns true iff the list contains no elements.
	boolean isEmpty( );
	 
	// Returns the number of elements in the list.
	int size( );
	 
	// Returns an iterator of the elements in the list (in proper sequence).
	Iterator<E> iterator( ) throws NoElementException;
	 
	// Returns the position of the first occurrence of the specified element in the list,
	//  if the list contains the element. Otherwise, returns -1.
	int find( E element );
	
	// Returns the first element of the list.
	E getFirst( ) throws NoElementException;
	 
	// Returns the last element of the list.
	E getLast( ) throws NoElementException;
	 
	// Returns the element at the specified position in the list.
	// Range of valid positions: 0, ..., size()-1. 
	// If the specified position is 0, get corresponds to getFirst.
	// If the specified position is size()-1, get corresponds to getLast.
	// @throws InvalidPositionException if position<0 || position >=size()
	E get( int position ) throws InvalidPositionException;

	// Inserts the specified element at the first position in the list.
	void addFirst( E element );
	 
	// Inserts the specified element at the last position in the list.
	void addLast( E element );
	 
	// Inserts the specified element at the specified position in the list.
	// Range of valid positions: 0, ..., size(). 
	// If the specified position is 0, add corresponds to addFirst.
	// If the specified position is size(), add corresponds to addLast.
	// @throws InvalidPositionException if position<0 || position >size()
	void add( int position, E element ) throws InvalidPositionException;

	// Removes and returns the element at the first position in the list.
	E removeFirst( ) throws NoElementException;
	 
	// Removes and returns the element at the last position in the list.
	E removeLast( ) throws NoElementException;
	 
	// Removes and returns the element at the specified position in the list.
	// Range of valid positions: 0, ..., size()-1.
	// If the specified position is 0, remove corresponds to removeFirst.
	// If the specified position is size()-1, remove corresponds to removeLast.
	// @throws InvalidPositionException if position<0 || position >=size()
	E remove( int position ) throws InvalidPositionException;

}
