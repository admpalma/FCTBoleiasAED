package dataStructures;

import basicDateTime.BasicDateTime;

/**
 * Indexed Map by {@link BasicDateTime}
 * @param <K> keys of this Map, has to extend {@link BasicDateTime} 
 * @param <V> values of this Map
 */
public class BasicDateSortedMap<K extends BasicDateTime, V> implements DateIndexedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constants
	 */
	private static final int DAYS_IN_MONTH = 31;
	private static final int MONTHS = 12;
	private static final int INITIAL_YEARS = 2;
	private static final int BASE_YEAR = 2017;
	
	private int currentSize;
	private V[][][] values;

	/**
	 * Constructs a new {@link BasicDateSortedMap} with {@link BasicDateSortedMap#INITIAL_YEARS default capacity}<p>
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 */
	public BasicDateSortedMap() {
		this(INITIAL_YEARS);
	}
	
	/**
	 * Constructs a new {@link BasicDateSortedMap} with the given <code>yearCapacity</code><p>
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 * @param yearCapacity number of years expected to store
	 */
	@SuppressWarnings("unchecked")
	public BasicDateSortedMap(int yearCapacity) {
		values = (V[][][]) new Object[yearCapacity][MONTHS][DAYS_IN_MONTH];
		currentSize = 0;
	}
	
	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 */
	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 */
	@Override
	public int size() {
		return currentSize;
	}
	
	/**
	 * Checks if a given <code>key</code> fits in this map<p>
	 * O(1)
	 * @param key given key to check if fits
	 * @return <code>true</code> if the given key fits in this map, <code>false</code> otherwise
	 */
	private boolean fits(K key) {
		return key.getYear() - BASE_YEAR < values.length;
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 */
	@Override
	public V get(K key) {
		if (fits(key)) {
			return values[key.getYear() - BASE_YEAR][key.getMonth() - 1][key.getDay() - 1];
		} else {
			return null;
		}
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(n) if we resize<p>
	 */
	@Override
	public V insert(K key, V value) {
		V overriddenValue = get(key);
		if (!fits(key)) {
			resizeToFit(key);
		}
		if (overriddenValue == null) {
			currentSize++;
		}
		values[key.getYear() - BASE_YEAR][key.getMonth() - 1][key.getDay() - 1] = value;
		return overriddenValue;
	}

	/**
	 * Resizes this map to fit the given key<p>
	 *
	 * Best case: O(n)<p>
	 * Average case: O(n)<p>
	 * Worst case: O(n)<p>
	 * @param key given key
	 */
	@SuppressWarnings("unchecked")
	private void resizeToFit(K key) {
		V[][][] temp = (V[][][]) new Object[key.getYear() - BASE_YEAR + 1][MONTHS][DAYS_IN_MONTH];
		int i = 0;
		for (V[][] v : values) {
			temp[i++] = v;
		}
		values = temp;
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 */
	@Override
	public V remove(K key) {
		V deletedValue = get(key);
		if (deletedValue != null) {
			values[key.getYear() - BASE_YEAR][key.getMonth() - 1][key.getDay() - 1] = null;
			currentSize--;
			return deletedValue;
		} else {
			return null;
		}
		
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 */
	@Override
	public Iterator<V> values() throws NoElementException {
		if (this.isEmpty()) {
			throw new NoElementException();
		}
		return new SparseThreeDimensionalArrayIterator<V>(values, currentSize);
	}

}
