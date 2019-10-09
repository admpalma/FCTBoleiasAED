package dataStructures;

public class SepChainHashTable<K, V> extends MapWithHashTable<K,V>{
	
	// The array of maps.
	protected Map<K,V>[] table;
	
	public SepChainHashTable(){
		this(DEFAULTCAPACITY);
	}

	@SuppressWarnings("unchecked")
	public SepChainHashTable(int capacity) {
		// Load factor is 1/1.1 (0.91)
		int arraySize = MapWithHashTable.nextPrime((int) (1.1 * capacity));
		// Compiler gives a warning.
		table = (Map<K,V>[]) new Map[arraySize];
		for ( int i = 0; i < arraySize; i++ )
			table[i] = new MapWithSinglyLinkedList<K,V>();
		maxSize = capacity;
		currentSize = 0;
	}

	// Returns the hash value of the specified key.
	protected int hash( K key ){
		return Math.abs( key.hashCode() ) % table.length;
	}
	
	// If there is an entry in the map whose key is the specified key,
	// returns its value; otherwise, returns null.
	@Override
	public V find( K key ){
		return table[ this.hash(key) ].find(key);
	}
	
	// If there is an entry in the map whose key is the specified key,
	// replaces its value by the specified value and returns the old value;
	// otherwise, inserts the entry (key, value) and returns null.
	@Override
	public V insert( K key, V value ){
		if ( this.isFull() )
			rehash();
		 V valueOld=table[this.hash(key)].insert(key, value);
	     if (valueOld == null)
	    	 currentSize++;
	     return valueOld;
	}
	
	private void rehash() {
		// TODO 
	}

	@Override
	public V remove(K key) {
		// TODO
		return null;
	}
	
	@Override
	public Iterator<K> keys() throws NoElementException {
		// TODO
		return null;
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		// TODO
		return null;
	}
	
	@Override
	public Iterator<Entry<K,V>> iterator() throws NoElementException{
		// TODO
		return null;
	}

}
