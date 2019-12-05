package dataStructures;

import java.io.Serializable;

public interface AbstractMap<K, V> extends Serializable {

	// Returns true iff the map contains no entries.
	boolean isEmpty();

	// Returns the number of entries in the map.
	int size();
	
	// Returns an iterator of the values in the map.
	Iterator<V> values() throws NoElementException;
	
	// If there is an entry in the map whose key is the specified key,
	// returns its value; otherwise, returns null.
	V get(K key);

	// If there is an entry in the map whose key is the specified key,
	// replaces its value by the specified value and returns the old value;
	// otherwise, inserts the entry (key, value) and returns null.
	V insert(K key, V value);

	// If there is an entry in the map whose key is the specified key,
	// removes it from the map and returns its value; otherwise, returns null.
	V remove(K key);
}
