package dataStructures;

public class MapWithSinglyLinkedList<K, V> extends SinglyLinkedList<Entry<K, V>> implements Map<K, V> {

	private List<Entry<K, V>> list;

	public MapWithSinglyLinkedList() {
		this.list = new SinglyLinkedList<Entry<K, V>>();
	}

	/*
	 * @Override public boolean isEmpty() { return list.isEmpty(); }
	 * 
	 * @Override public int size() { return list.size(); }
	 */
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
	public int find(Entry<K, V> element) {
		// TODO Auto-generated method stub
		return super.find(element);
	}
	
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

		SListNode<Entry<K, V>> e = head;

		while (e != null) {
			if (e.getElement().getKey().equals(key)) {
				V oldValue = e.getElement().getValue();
				e.setElement(new EntryClass<K, V>(key, value));
				return oldValue;
			}
			e = e.getNext();
		}

		addLast(new EntryClass<K, V>(key, value));

		return null;
	}

	@Override
	public V remove(K key) {

		SListNode<Entry<K, V>> previous = head;
		SListNode<Entry<K, V>> e = previous.getNext();

		if (previous.getElement().getKey().equals(key)) {
			return previous.getElement().getValue();
		}

		while (e != null) {
			if (e.getElement().getKey().equals(key)) {
				previous.setNext(e.getNext());
				return e.getElement().getValue();
			}
			previous = e;
			e = e.getNext();
		}
		return null;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		return list.iterator();
	}

}
