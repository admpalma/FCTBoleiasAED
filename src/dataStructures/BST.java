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
			return (left != null || right != null);
		}

	}

	// The root
	protected BSTNode<Entry<K, V>> root;

	// Number of elements
	protected int currentSize;
	
	public BST() {
		root = null;
		currentSize = 0;
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

		BSTNode<Entry<K, V>> closestNode = findClosest(key);

		// Key already existed
		if (insertAux(key, value, closestNode) == null)
			return closestNode.element.getValue();

		currentSize++;
		return null;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param closestNode
	 * @return or null if it replaces the value of an existing entry (key already
	 *         existed)
	 */
	protected BSTNode<Entry<K, V>> insertAux(K key, V value, BSTNode<Entry<K, V>> closestNode) {

		BSTNode<Entry<K, V>> newNode = null; // node where the new entry is being inserted (if find(key)==null)
		Entry<K, V> newEntry = new EntryClass<K, V>(key, value);

		if (size() == 0) {
			// If the tree was empty we insert as root
			newNode = new BSTNode<Entry<K, V>>(newEntry);
			root = newNode;
		} else {
			newNode = new BSTNode<Entry<K, V>>(newEntry, closestNode, null, null);

			int num = key.compareTo(closestNode.getElement().getKey());
			if (num == 0) { // If key already existed
				newNode = new BSTNode<Entry<K, V>>(newEntry, closestNode.parent, null, null);
				closestNode = newNode;
				return null;
			} else if (num < 0)
				closestNode.left = newNode;
			else
				closestNode.right = newNode;
		}
		return newNode;
	}

	/**
	 * Finds the closest node to insert or the node with the same key
	 * 
	 * @param key key to find
	 * @return
	 */
	protected BSTNode<Entry<K, V>> findClosest(K key) {

		BSTNode<Entry<K, V>> current = root;
		BSTNode<Entry<K, V>> nextNode = root; // initialize to something

		while (nextNode != null) {
			int num = key.compareTo(current.getElement().getKey());

			if (num == 0) {
				return current;
			} else if (num < 0) {
				nextNode = current.left;
			} else {
				nextNode = current.right;
			}
			if (nextNode == null) {
				return current;
			}
			current = nextNode;
		}
		return current;
	}

	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;
		currentSize--;
		return removeAux(key).element.getValue();
	}

	protected BSTNode<Entry<K, V>> removeAux(K key) throws NoSuchElementException {

		BSTNode<Entry<K, V>> foundNode = findNode(root, key);

		if (foundNode == null)
			throw new NoSuchElementException("Node not found!");

		BSTNode<Entry<K, V>> parent = foundNode.getParent();
		boolean hasRightChild = false;
		// If the node we wish to remove is the parent's right node
		if (parent != null) {
			hasRightChild = foundNode.equals(parent.getRight());
		}
		int children = 0;
		if (foundNode.getRight() != null)
			children += 1;
		else if (foundNode.getLeft() != null)
			children += 2;

		switch (children) {
		case 0: // No children
			removeInternal(parent, hasRightChild);
			break;
		case 1: // Has right child
			removeWithOneChild(true, parent, foundNode.getRight());
			break;
		case 2: // Has left child
			removeWithOneChild(false, parent, foundNode.getLeft());
			break;
		default: // Has both children
			removeWithBothChildren(foundNode);
			break;

		}
		return foundNode;
	}

	// TODO may still not work, stuff missing?
	private void removeWithBothChildren(BSTNode<Entry<K, V>> foundNode) {
		// get min value of the right child
		// put that on the place of removal
		// add the right child as the right child of the min value node
		// add the min value as the parent of the right child
		// clean remaining stuff

		BSTNode<Entry<K, V>> rightChild = foundNode.getRight();
		BSTNode<Entry<K, V>> minValueNode = minNode(rightChild);
		BSTNode<Entry<K, V>> removedNodeParent = foundNode.getParent();

		minValueNode.right = rightChild;
		rightChild.parent = minValueNode;

		// TODO CHECK THIS CLEANUP
		minValueNode.parent.left = null;

		if (removedNodeParent != null) {
			removedNodeParent.right = minValueNode;
		} else {
			root = minValueNode;
		}

	}

	private void removeWithOneChild(boolean amRightChild, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> child) {
		if (parent == null) {
			root = child;
		} else if (amRightChild) {
			parent.right = child;
		} else {
			parent.left = child;
		}
		child.parent = parent;
	}

	private void removeInternal(BSTNode<Entry<K, V>> parent, boolean amRightChild) {
		if (parent == null) {
			root = null;
		} else if (amRightChild) {
			parent.right = null;
		} else {
			parent.left = null;
		}
	}

	@Override
	public Entry<K, V> minEntry() throws NoElementException {
		if (this.isEmpty())
			throw new NoElementException();
		return this.minNode(root).getElement();
	}

	// Precondition: node != null.
	protected BSTNode<Entry<K, V>> minNode(BSTNode<Entry<K, V>> node) {
		if (node.getLeft() == null)
			return node;
		return this.minNode(node.getLeft());
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
