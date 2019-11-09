package dataStructures;

public class IteratorEntries<K, V> implements Iterator<Entry<K, V>> {
	
	private java.util.Map<K, V> map;
	private java.util.Iterator<java.util.Map.Entry<K, V>> iterator;
	
	public IteratorEntries(java.util.Map<K, V> map) {
		this.map = (java.util.Map<K, V>) map;
		rewind();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Entry<K, V> next() throws NoSuchElementException {
		java.util.Map.Entry<K, V> javaEntry = iterator.next(); 
		Entry<K, V> entry = new EntryClass<K, V>(javaEntry.getKey(), javaEntry.getValue());
		return entry;
	}

	@Override
	public void rewind() {
		this.iterator = map.entrySet().iterator();
	}

}
