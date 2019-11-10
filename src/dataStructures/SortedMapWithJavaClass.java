package dataStructures;

import java.util.Collection;
import java.util.TreeMap;

public class SortedMapWithJavaClass<K extends Comparable<K>, V> implements SortedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TreeMap<K, V> map;

	public SortedMapWithJavaClass() {
		this.map = new TreeMap<K, V>();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		return new IteratorKeysValues<K>(map.keySet());
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		Collection<V> values = map.values();
		if (values.isEmpty()) {
			throw new NoElementException();
		}
		return new IteratorKeysValues<V>(values);
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		return new IteratorEntries<K, V>(map);
	}

	@Override
	public V find(K key) {
		return map.get(key);
	}

	@Override
	public V insert(K key, V value) {
		return map.put(key, value);
	}

	@Override
	public V remove(K key) {
		return map.remove(key);
	}

	@Override
	public Entry<K, V> minEntry() throws NoElementException {
		java.util.Map.Entry<K, V> javaEntry = map.firstEntry();
		return new EntryClass<K, V>(javaEntry.getKey(), javaEntry.getValue());
	}

	@Override
	public Entry<K, V> maxEntry() throws NoElementException {
		java.util.Map.Entry<K, V> javaEntry = map.lastEntry();
		return new EntryClass<K, V>(javaEntry.getKey(), javaEntry.getValue());
	}

}
