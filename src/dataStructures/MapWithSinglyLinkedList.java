package dataStructures;

public class MapWithSinglyLinkedList<K, V> implements Map<K, V> {
	
	private List<Entry<K, V>> list;
	
	public MapWithSinglyLinkedList() {
		this.list = new SinglyLinkedList<Entry<K,V>>();
	}
	
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		Iterator<Entry<K, V>> iter = list.iterator();
		
		List<K> l = new SinglyLinkedList<K>();
		
		while (iter.hasNext()) {
			Entry<K, V> entry = (Entry<K, V>) iter.next();
			l.addLast(entry.getKey());
		}
		
		return l.iterator();
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		Iterator<Entry<K, V>> iter = list.iterator();
		
		List<V> l = new SinglyLinkedList<V>();
		
		while (iter.hasNext()) {
			Entry<K, V> entry = (Entry<K, V>) iter.next();
			l.addLast(entry.getValue());
		}
		
		return l.iterator();
	}

	@Override
	public V find(K key) {
		Iterator<Entry<K, V>> it = list.iterator();
		while (it.hasNext()) {
			Entry<K, V> entry = (Entry<K, V>) it.next();
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public V insert(K key, V value) {
		
		/*
		 * If we search and then remove we run through it twice
		 * Might as well use remove and then add anyways
		 */
		V v = remove(key);
		
		list.addLast(new EntryClass<K, V>(key, value));

		return v;
	}

	@Override
	public V remove(K key) {
		
		Iterator<Entry<K, V>> iter = list.iterator();
		
		while (iter.hasNext()) {
			Entry<K, V> entry = (Entry<K, V>) iter.next();

			if (entry.getKey().equals(key)) {
				list.remove(list.find(entry));
				return entry.getValue();
			}
		}
		
		return null;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		return list.iterator();
	}

}
