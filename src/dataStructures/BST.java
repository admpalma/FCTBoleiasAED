package dataStructures;

public class BST<K extends Comparable<K>, V> implements SortedMap<K, V> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static class BSTNode<E> {

		protected BSTNode<E> parent;
		protected BSTNode<E> left;
		protected BSTNode<E> right;
		protected E element;

		BSTNode(E elem, BSTNode<E> parent, BSTNode<E> left, BSTNode<E> right) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			element = elem;
		}

		BSTNode(E elem) {
			this(elem, null, null, null);
		}

		E getElement() {
			return element;
		}

		// ...
		BSTNode<E> getLeft() {
			return left;
		}

		BSTNode<E> getRight() {
			return right;
		}

		BSTNode<E> getParent() {
			return parent;
		}

		boolean isInternal() {
			// TODO
			return true;
		}

	}

	// The root
	protected BSTNode<Entry<K, V>> root;

	// Number of elements
	protected int currentSize;
	
	protected BST(BSTNode<Entry<K, V>> n) {
		root = n;
	}
	
	public BST() {
		this(null);
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new BSTOrderIterator<K, V>(root);
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

	protected BSTNode<Entry<K, V>> findNode(BSTNode<Entry<K, V>> n, K key) {
		BSTNode<Entry<K, V>> res = null;
		if (n != null) {
			int num = key.compareTo(n.getElement().getKey());
			if (num == 0)
				res = n;
			else if (num < 0)
				res = findNode(n.getLeft(), key);
			else
				res = findNode(n.getRight(), key);
		}
		return res;
	}

	@Override
	public V get(K key) {
		BSTNode<Entry<K, V>> res = findNode(root, key);
		if (res == null)
			return null;
		return res.getElement().getValue();
	}

	@Override
	public V insert(K key, V value) {
		// TODO
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO
		return null;
	}

	@Override
	public Entry<K, V> minEntry() throws NoElementException {
		// TODO
		return null;
	}

	@Override
	public Entry<K, V> maxEntry() throws NoElementException {
		if (this.isEmpty())
			throw new NoElementException();
		return this.maxNode(root).getElement();
	}

	// Precondition: node != null.
	protected BSTNode<Entry<K, V>> maxNode(BSTNode<Entry<K, V>> node) {
		if (node.getRight() == null)
			return node;
		return this.maxNode(node.getRight());
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public int size() {
		return currentSize;
	}

}
