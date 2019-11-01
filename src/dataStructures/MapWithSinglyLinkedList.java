package dataStructures;

public class MapWithSinglyLinkedList<K, V> extends SinglyLinkedList<Entry<K, V>> implements Map<K, V> {

	public MapWithSinglyLinkedList() {
		super();
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		Iterator<Entry<K, V>> iter = this.iterator();

		List<K> l = new SinglyLinkedList<K>();

		while (iter.hasNext()) {
			Entry<K, V> entry = (Entry<K, V>) iter.next();
			l.addLast(entry.getKey());
		}

		return l.iterator();
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		Iterator<Entry<K, V>> iter = this.iterator();

		List<V> l = new SinglyLinkedList<V>();

		while (iter.hasNext()) {
			Entry<K, V> entry = (Entry<K, V>) iter.next();
			l.addLast(entry.getValue());
		}

		return l.iterator();
	}

	@Override
	public int find(Entry<K, V> element) {
		return super.find(element);
	}

	// @Override
	public V find(K key) {
		Iterator<Entry<K, V>> it = this.iterator();
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
		SListNode<Entry<K, V>> current;
		try {
			current = previous.getNext();
		} catch(NullPointerException e) {
			return null;
		}

		if (previous.getElement().getKey().equals(key)) {
			return previous.getElement().getValue();
		}

		while (current != null) {
			if (current.getElement().getKey().equals(key)) {
				previous.setNext(current.getNext());
				return current.getElement().getValue();
			}
			previous = current;
			current = current.getNext();
		}
		return null;
	}

}
