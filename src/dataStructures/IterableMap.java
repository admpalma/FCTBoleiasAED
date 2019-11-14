package dataStructures;

public interface IterableMap<K, V> {

	// Returns an iterator of the keys in the map.
	Iterator<K> keys() throws NoElementException;

	// Returns an iterator of the values in the map.
	Iterator<V> values() throws NoElementException;

	// Returns an iterator of the entries in the map.
	Iterator<Entry<K, V>> iterator() throws NoElementException;

}
