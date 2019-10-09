package dataStructures;

public interface MinPriorityQueue<K, E> {
	// Returns true if the priority queue contains no elements.
	boolean isEmpty( );
	 
	// Returns the number of elements in the priority queue.
	int size( );
	 
	// Returns an entry with the smallest key in the priority queue.
	//@throws NoElementException if isEmpty()
	Entry<K,E> minEntry( ) throws NoElementException;
	 
	// Inserts the entry (key, value) in the priority queue.
	void insert( K key, E value );
	 
	// Removes an entry with the smallest key from the priority queue and returns that entry.
	//@throws NoElementException if isEmpty()
	Entry<K,E> removeMin( ) throws NoElementException;
}
