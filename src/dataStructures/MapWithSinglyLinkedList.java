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
		if (!this.isEmpty()) {
			Iterator<Entry<K, V>> it = this.iterator();
			while (it.hasNext()) {
				Entry<K, V> entry = it.next();
				if (entry.getKey().equals(key)) {
					return entry.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public V insert(K key, V value) {
		SListNode<Entry<K, V>> e = head;
		while (e != null) {
			Entry<K, V> oldElem = e.getElement();
			if (oldElem.getKey().equals(key)) {
				e.setElement(new EntryClass<K, V>(key, value));
				return oldElem.getValue();
			}
			e = e.getNext();
		}
		addLast(new EntryClass<K, V>(key, value));
		return null;
	}

	@Override
	public V remove(K key) {
		if (isEmpty()) {
			return null;
		} else if (head.getElement().getKey().equals(key)) {
			return removeFirst().getValue();
		} else if (tail.getElement().getKey().equals(key)) {
			return removeLast().getValue();
		} else {
			SListNode<Entry<K, V>> previous = head;
			SListNode<Entry<K, V>> current = previous.getNext();
			while (current != null) {
				Entry<K, V> currentElem = current.getElement();
				if (currentElem.getKey().equals(key)) {
					previous.setNext(current.getNext());
					currentSize--;
					return currentElem.getValue();
				}
				previous = current;
				current = current.getNext();
			}
			return null;
		}
	}

}
