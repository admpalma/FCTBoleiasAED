package dataStructures;

public class LinearProbingHashTable<K, V> extends MapWithHashTable<K, V> {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	// The array of entries.
	protected Entry<K, V>[] table;

	public LinearProbingHashTable() {
		this(DEFAULTCAPACITY);
	}

	@SuppressWarnings("unchecked")
	public LinearProbingHashTable(int capacity) {
		// Load factor is 1/2 (0.5)
		int arraySize = MapWithHashTable.nextPrime((int) (2 * capacity));
		// Compiler gives a warning.
		table = (Entry<K, V>[]) new Entry[arraySize];
		for (int i = 0; i < arraySize; i++)
			table[i] = null;
		maxSize = capacity;
		currentSize = 0;
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		if (table.length == 0) {
			throw new NoElementException("Map is empty.");
		}

		return new IteratorKeys<K, Entry<K, V>>(this.iterator());
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		if (table.length == 0) {
			throw new NoElementException("Map is empty.");
		}

		return new IteratorValues<V, Entry<K, V>>(this.iterator());
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		if (table.length == 0) {
			throw new NoElementException("Map is empty.");
		}

		return new ArrayIterator<Entry<K, V>>(table, table.length);
	}

	// Returns the hash value of the specified key.
	protected int hash(K key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	@Override
	public V get(K key) {
		int pos = findPos(key);
		if (pos == -1 || isEmpty(pos))
			return null;
		return table[pos].getValue();
	}

	// returns the position (a) where is the element with this key
	// or (b) of an empty position
	// or (c) -1 the map is full and there is not element with this key
	private int findPos(K key) {
		int pos = this.hash(key);
		int hashKey = pos;
		if (!isEmpty(pos) && table[pos].getKey().equals(key))
			return pos;
		pos = (pos + 1) % table.length;
		while (hashKey != pos && !isEmpty(pos) && !table[pos].getKey().equals(key))
			pos = (pos + 1) % table.length;
		if (hashKey == pos)
			return -1;
		return pos;
	}
	
	//without remove
	private boolean isEmpty(int pos) {
		return table[pos]==null;
	}

	@Override
	//without remove
	public V insert(K key, V value) {
		if (this.isFull())
			this.rehash();
		int pos = findPos(key); // nunca será -1
		V valueOld;
		if (!isEmpty(pos))
			valueOld = table[pos].getValue();
		else {
			valueOld = null;
			currentSize++;
		}
		table[pos] = new EntryClass<K, V>(key, value);
		return valueOld;
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		Entry<K, V>[] auxTable = table;

		// Load factor is 1/2 (0.5)
		int arraySize = MapWithHashTable.nextPrime((int) (2 * maxSize*2));
		// Compiler gives a warning.
		table = (Entry<K, V>[]) new Entry[arraySize];
		currentSize = 0;
		for (int i = 0; i < auxTable.length; i++) {
			Entry<K, V> entry = auxTable[i];
			insert(entry.getKey(), entry.getValue());
		}
		maxSize *= 2;
	}

	@Override
	public V remove(K key) {
		// NOT USED
		return null;
	}

}
