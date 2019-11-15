/**
 * 
 */
package dataStructures;

import java.io.Serializable;

/**
 * @author AED_19_20
 *
 */
public interface Map<K, V> extends Serializable {
	// Returns true iff the map contains no entries.
	boolean isEmpty();

	// Returns the number of entries in the map.
	int size();

	// Returns an iterator of the keys in the map.
	Iterator<K> keys() throws NoElementException;

	// Returns an iterator of the values in the map.
	Iterator<V> values() throws NoElementException;

	// Returns an iterator of the entries in the map.
	Iterator<Entry<K, V>> iterator() throws NoElementException;

	// If there is an entry in the map whose key is the specified key,
	// returns its value; otherwise, returns null.
	V find(K key);

	// If there is an entry in the map whose key is the specified key,
	// replaces its value by the specified value and returns the old value;
	// otherwise, inserts the entry (key, value) and returns null.
	V insert(K key, V value);

	// If there is an entry in the map whose key is the specified key,
	// removes it from the map and returns its value; otherwise, returns null.
	V remove(K key);
}
