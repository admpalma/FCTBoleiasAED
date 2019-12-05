package dataStructures;

import basicDateTime.BasicDateTime;

/**
 * TODO
 * @param <K>
 * @param <V>
 */
public class BasicDateSortedMap<K extends BasicDateTime, V> implements DateIndexedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DAYS_IN_MONTH = 31;
	private static final int MONTHS = 12;
	private static final int INITIAL_YEARS = 2;
	private static final int BASE_YEAR = 2017;
	
	private int currentSize;
	private V[][][] values;

	/**
	 * TODO
	 */
	public BasicDateSortedMap() {
		this(INITIAL_YEARS);
	}
	
	/**
	 * TODO
	 * @param yearCapacity
	 */
	@SuppressWarnings("unchecked")
	public BasicDateSortedMap(int yearCapacity) {
		values = (V[][][]) new Object[yearCapacity][MONTHS][DAYS_IN_MONTH];
		currentSize = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public int size() {
		return currentSize;
	}
	
	/**
	 * TODO
	 * @param key
	 * @return
	 */
	private boolean fits(K key) {
		return key.getYear() - BASE_YEAR < values.length;
	}

	@Override
	public V get(K key) {
		if (fits(key)) {
			return values[key.getYear() - BASE_YEAR][key.getMonth() - 1][key.getDay() - 1];
		} else {
			return null;
		}
	}

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
	 * TODO
	 * @param key
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

	@Override
	public Iterator<V> values() throws NoElementException {
		if (this.isEmpty()) {
			throw new NoElementException();
		}
		return new SparseThreeDimensionalArrayIterator<V>(values, currentSize);
	}

}
