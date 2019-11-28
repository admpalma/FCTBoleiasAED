package dataStructures;

import dataStructures.BST.BSTNode;

public class AVL<K extends Comparable<K>, V> extends AdvancedBST<K, V> implements SortedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static class AVLNode<E> extends BSTNode<E> {
		// Height of the node
		protected int height;

		public AVLNode(E elem) {
			super(elem);
			height = 1;
		}

		public AVLNode(E element, AVLNode<E> parent, AVLNode<E> left, AVLNode<E> right) { // char balance,
			super(element, parent, left, right);
			height = 1 + Math.max(getHeight((AVLNode<E>) left), getHeight((AVLNode<E>) right));
		}

		protected int getHeight(AVLNode<E> no) {
			if (no == null)
				return 0;
			return no.getHeight();
		}

		public int getHeight() {
			return height;
		}

		public boolean isBalance() {
			int dif = getHeight((AVLNode<E>) left) - getHeight((AVLNode<E>) right);
			return dif == 0 || dif == -1 || dif == 1;
		}

		public int setHeight() {
			height = 1 + Math.max(getHeight((AVLNode<E>) left), getHeight((AVLNode<E>) right));
			return height;
		}
	}

	protected AVL(AVLNode<Entry<K, V>> n) {
		root = n;
	}

	public AVL() {
		this(null);
	}

	/**
	 * Return a child of p with greater height
	 */
	protected AVLNode<Entry<K, V>> tallerChild(AVLNode<Entry<K, V>> p) {
		AVLNode<Entry<K, V>> left = (AVLNode<Entry<K, V>>) p.left;
		AVLNode<Entry<K, V>> right = (AVLNode<Entry<K, V>>) p.left;

		// If left node's height is higher, return left node
		// If right node's height is higher, return right node
		return p.getHeight(left) > p.getHeight(right) ? left : right;

	}

	/**
	 * Rebalance method called by insert and remove. Traverses the path from zPos to
	 * the root. For each node encountered, we recompute its height and perform a
	 * trinode restructuring if it's unbalanced. the rebalance is completed with
	 * O(log n)running time
	 */
	protected void rebalance(AVLNode<Entry<K, V>> zPos) {
		if (zPos.isInternal())
			zPos.setHeight();
		// Melhorar se possivel
		while (zPos != null) { // traverse up the tree towards the root
			zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
			zPos.setHeight();
			if (!zPos.isBalance()) {
				// perform a trinode restructuring at zPos's tallest grandchild
				// If yPos (tallerChild(zPos)) denote the child of zPos with greater height.
				// Finally, let xPos be the child of yPos with greater height
				AVLNode<Entry<K, V>> xPos = tallerChild((AVLNode<Entry<K, V>>) tallerChild(zPos));
				zPos = (AVLNode<Entry<K, V>>) restructure(xPos); // tri-node restructure (from parent class)
				((AVLNode<Entry<K, V>>) zPos.getLeft()).setHeight(); // recompute heights
				((AVLNode<Entry<K, V>>) zPos.getRight()).setHeight();
				zPos.setHeight();
			} else
				break; // TODO this? If the node is not unbalanced then none above it are
		}
	}

	@Override
	public V insert(K key, V value) {

		AVLNode<Entry<K, V>> closestNode = (AVLNode<Entry<K, V>>) findClosest(key);
		
		EntryClass<K, V> newEntry = new EntryClass<K, V>(key, value);
		BSTNode<Entry<K, V>> newNode;

		if (size() == 0) {
			newNode = new AVLNode<Entry<K, V>>(newEntry);
		} else {
			newNode = new AVLNode<Entry<K, V>>(newEntry, closestNode, null, null);
		}
		
		AVLNode<Entry<K, V>> insertedNode = (AVLNode<Entry<K, V>>) insertAux(key, value, closestNode, newNode);
		if (insertedNode == null)
			return closestNode.element.getValue();
		else {
			// TODO insertNode.parent was null pointing
			if (insertedNode!=root)
				rebalance((AVLNode<Entry<K, V>>) insertedNode);
		}
		return null;
	}

	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;

		BSTNode<Entry<K, V>> removed = removeAux(key);

		// TODO not always needed? if we remove the root probably not
		rebalance((AVLNode<Entry<K, V>>) removed); // rebalance up from the node
		return removed.element.getValue();
	}

}
