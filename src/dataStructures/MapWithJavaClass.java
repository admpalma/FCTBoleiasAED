package dataStructures;

public class MapWithJavaClass<K,V> implements Map<K,V> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected java.util.Map<K,V> elementos;
	protected int capPrevista;

	public MapWithJavaClass(int prevusers) {
		elementos = new java.util.HashMap<K,V>(prevusers);
		capPrevista =prevusers;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
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
