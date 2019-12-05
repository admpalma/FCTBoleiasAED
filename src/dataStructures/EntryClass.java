/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public class EntryClass<K, V> implements Entry<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private K key;
	private V value;

	public EntryClass(K k, V v) {
		key = k;
		value = v;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public K getKey() {
		return key;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public V getValue() {
		return value;
	}

}
