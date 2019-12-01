package dataStructures;

import java.io.Serializable;

public class BST<K extends Comparable<K>, V> implements SortedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static class BSTNode<E> implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
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
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		if (root == null) {
			throw new NoElementException();
		}
		return new BSTOrderIterator<K, V>(root);
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		return new IteratorKeys<K, Entry<K,V>>(this.iterator());
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		return new IteratorValues<V, Entry<K,V>>(this.iterator());
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
		V overriddenValue = null;
		if (closestNode != null) {
			overriddenValue = closestNode.element.getValue();
		}
		BSTNode<Entry<K, V>> insertedNode = insertAux(key, value, closestNode);
		if (closestNode == insertedNode) {
			return overriddenValue;
		} else {
			return null;
		}
	}
	
	/**
	 * Factory method to create new {@link BSTNode BSTNodes}.<br>
	 * May be overridden by subclasses that require more specialized nodes.
	 * @param element new {@link BSTNode BSTNode's} {@link BSTNode#element}
	 * @param parent new {@link BSTNode BSTNode's} {@link BSTNode#parent}
	 * @param left new {@link BSTNode BSTNode's} {@link BSTNode#left}
	 * @param right new {@link BSTNode BSTNode's} {@link BSTNode#right}
	 * @return new {@link BSTNode} initialized with the given parameters
	 */
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left, BSTNode<Entry<K, V>> right) {
		//TODO isto com genericos era lit mas da um pouco de trabalho
		return new BSTNode<Entry<K, V>>(element, parent, left, right);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param closestNode
	 * @return
	 */
	protected BSTNode<Entry<K, V>> insertAux(K key, V value, BSTNode<Entry<K, V>> closestNode) {
		BSTNode<Entry<K, V>> insertedNode;
		if (this.isEmpty()) {
			insertedNode = nodeOf(new EntryClass<K, V>(key, value), null, null, null);
			root = insertedNode;
			currentSize++;
		} else {
			assert(closestNode != null);
			int num = key.compareTo(closestNode.element.getKey());
			if (num == 0) { // If key already existed
				closestNode.element = new EntryClass<K, V>(key, value);
				insertedNode = closestNode;
			} else {
				insertedNode = nodeOf(new EntryClass<K, V>(key, value), closestNode, null, null);
				if (num < 0) {
					closestNode.left = insertedNode;
				} else {
					closestNode.right = insertedNode;
				}
				currentSize++;
			}	
		}
		return insertedNode;
	}

	/**
	 * Finds the closest node to insert or the node with the same key
	 * 
	 * @param key key to find
	 * @return <code>null</code> if this {@link BST} is empty, otherwise:<br>
	 * 
	 * {@link BSTNode} of this {@link BST}
	 * that has an {@link Entry} with an equal <code>key</code> to the given one as it's {@link BSTNode#element element} 
	 * if there is such a {@link BSTNode};<br>
	 * 
	 * {@link BSTNode} of this {@link BST} fit to be the parent of another {@link BSTNode} 
	 * that has an {@link Entry} with the given <code>key</code> as it's {@link BSTNode#element element}
	 * if there is no {@link BSTNode} matching the previous situation.
	 */
	protected BSTNode<Entry<K, V>> findClosest(K key) {
		if (this.isEmpty()) {
			return null;
		}

		BSTNode<Entry<K, V>> current = root;
		BSTNode<Entry<K, V>> nextNode;

		do {
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
		} while (nextNode != null);
		return current;
	}

	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;
		return removeAux(key).element.getValue();
	}

	protected BSTNode<Entry<K, V>> removeAux(K key) throws NoSuchElementException {

		BSTNode<Entry<K, V>> foundNode = findNode(root, key);

		if (foundNode == null)
			throw new NoSuchElementException("Node not found!");

		BSTNode<Entry<K, V>> parent = foundNode.getParent();
		boolean isRightChild = false;
		// If the node we wish to remove is the parent's right node
		if (parent != null) {
			isRightChild = foundNode.equals(parent.getRight());
		}
		int children = 0;
		if (foundNode.getRight() != null)
			children += 1;
		if (foundNode.getLeft() != null)
			children += 2;

		switch (children) {
		case 0: // No children
			removeInternal(parent, isRightChild);
			break;
		case 1: // Has right child
			removeWithOneChild(isRightChild, parent, foundNode.getRight());
			break;
		case 2: // Has left child
			removeWithOneChild(isRightChild, parent, foundNode.getLeft());
			break;
		default: // Has both children
			BSTNode<Entry<K, V>> nodeToUpdate = foundNode;
			removeWithBothChildren(nodeToUpdate);
			break;

		}
		currentSize--;
		return foundNode;
	}

	// TODO may still not work, stuff missing?
	private void removeWithBothChildren(BSTNode<Entry<K, V>> nodeToUpdate) {
		// get min value of the right child
		// put that on the place of removal
		// add the right child as the right child of the min value node
		// add the min value as the parent of the right child
		// clean remaining stuff

		BSTNode<Entry<K, V>> minValueNode = minNode(nodeToUpdate.getRight());
		assert(minValueNode.left == null);
		
		boolean isRightChild = false;
		// If the node we wish to remove is the parent's right node
		if (minValueNode.parent != null) {
			isRightChild = minValueNode.equals(minValueNode.parent.getRight());
		}
		nodeToUpdate.element = minValueNode.element;
		if (minValueNode.right != null) {
			removeWithOneChild(isRightChild, minValueNode.parent, minValueNode.right);
		} else {
			removeInternal(minValueNode.parent, isRightChild);
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
