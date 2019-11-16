package dataStructures;

public class MapWithSinglyLinkedList<K, V> extends SinglyLinkedList<Entry<K, V>> implements Map<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapWithSinglyLinkedList() {
		super();
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		return new IteratorKeys<K, Entry<K, V>>(this.iterator());
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		return new IteratorValues<V, Entry<K, V>>(this.iterator());
	}

	@Override
	public int find(Entry<K, V> element) {
		return super.find(element);
	}

	@Override
	public V get(K key) {
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
		} catch (NullPointerException e) {
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
