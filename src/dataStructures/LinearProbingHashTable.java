package dataStructures;

public class LinearProbingHashTable<K, V> extends MapWithHashTable<K,V>{
	
		// The array of entries.
		protected Entry<K,V>[] table;
		
		public LinearProbingHashTable(){
			this(DEFAULTCAPACITY);
		}

		@SuppressWarnings("unchecked")
		public LinearProbingHashTable(int capacity) {
			// Load factor is 1/2 (0.5)
			int arraySize = MapWithHashTable.nextPrime((int) (2 * capacity));
			// Compiler gives a warning.
			table = (Entry<K,V>[]) new Entry[arraySize];
			for ( int i = 0; i < arraySize; i++ )
				table[i] = null;
			maxSize = capacity;
			currentSize = 0;
		}

	@Override
	public Iterator<K> keys() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V find(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V insert(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

}
