/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public interface Map<K, V> extends AbstractMap<K, V> {

	// Returns an iterator of the keys in the map.
	Iterator<K> keys() throws NoElementException;

	// Returns an iterator of the entries in the map.
	Iterator<Entry<K, V>> iterator() throws NoElementException;

}
