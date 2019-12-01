package dataStructures;

import dataStructures.BST.BSTNode;

public class RB<K extends Comparable<K>, V> extends AdvancedBST<K, V> implements SortedMap<K, V> {
	
	private static final boolean BLACK = false;
	private static final boolean RED = true;

	class RBNode<E> extends BSTNode<E> {
		
		
		protected boolean isRed;// we add a color field to a BTNode

		public RBNode(E elem) {
			super(elem);
			isRed = true;
		}

		public RBNode(E element, RBNode<E> parent, RBNode<E> left, RBNode<E> right) {
			super(element, parent, left, right);
			isRed = true;
		}

		public void setColour(boolean colour) {
			isRed = colour;
		}

		public boolean isRed() {
			return isRed;
		}

	}

	protected RB(RBNode<Entry<K, V>> n) {
		root = n;
	}

	public RB() {
		this(null);
	}

	@Override
	public V insert(K key, V value) {
		// TODO
		V valueToReturn = null;
		//RBNode<Entry<K, V>> newNode = null; // node where the new entry is being inserted (if find(key)==null)
		// insert the new Entry (if find(key)==null)
		RBNode<Entry<K, V>> closestNode = (RB<K, V>.RBNode<Entry<K, V>>) findClosest(key);
		
		EntryClass<K, V> newEntry = new EntryClass<K, V>(key, value);
		
		//BSTNode<Entry<K, V>> newNode = new BSTNode<Entry<K,V>>(, parent, left, right);
		RBNode<Entry<K, V>> newNode;

		if (size() == 0) {
			newNode = new RBNode<Entry<K, V>>(newEntry);
		} else {
			newNode = new RBNode<Entry<K, V>>(newEntry, closestNode, null, null);
		}
		
		
		RBNode<Entry<K, V>> insertNode = (RBNode<Entry<K, V>>) insertAux(key, value, closestNode, newNode);
		// or set the new value (if find(key)!=null)
		insertNode.setColour(RED); // Red
		if (insertNode.getParent() == null)
			insertNode.setColour(BLACK); // Black
		else
			remedyDoubleRed(insertNode); // fix a double-red color violation
		return valueToReturn;
	}

	// pre: !isRoot(posZ)
	private void remedyDoubleRed(RBNode<Entry<K, V>> posZ) {
		// TODO
		RBNode<Entry<K, V>> posV = (RB<K, V>.RBNode<Entry<K, V>>) posZ.getParent();
		if (posV.isRed()) {
			// we have a double red: posZ and posV
			// Case black uncle ou null: trinode restructuring
			// Case red uncle: recoloring
		}
	}

	@Override
	public V remove(K key) {
		// TODO
		// Remove as BST remove
		RBNode<Entry<K, V>> removedNode = (RBNode<Entry<K, V>>) removeAux(key);
		RBNode<Entry<K, V>> left = (RBNode<Entry<K, V>>) removedNode.getLeft();
		RBNode<Entry<K, V>> right = (RBNode<Entry<K, V>>) removedNode.getRight();
			
		// TODO cast breaks this? and refactor statement
		if (!removedNode.isRed()) {
			if (removedNode.left == null && removedNode.right == null) {
				// No children
				remedyDoubleBlack(removedNode);
			} else if (removedNode.left != null && left.isRed()) {
				// Set black
				left.setColour(BLACK);
			} else {
				right.setColour(BLACK);
			}
		}
		// case red node: end
		// case black node with a red child: recoloring (set children black)
		// case black node without children: remedyDoubleBlack(node)
		return removedNode.element.getValue();
	}

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(RBNode<Entry<K, V>> posR) {
		//RBNode<Entry<K, V>> posX, posY, posZ;
		// TODO
		RBNode<Entry<K, V>> parent = (RBNode<Entry<K, V>>) posR.getParent();
		RBNode<Entry<K, V>> sibling;

		if (parent.getLeft().equals(posR)) {
			sibling = (RBNode<Entry<K, V>>) parent.getRight();
		} else {
			sibling =  (RBNode<Entry<K, V>>) parent.getLeft();
		}

		if (sibling != null) {
			if (!posR.isRed()) {
				if (sibling.isRed()) {
					restructure(posR);
				} else {
					RBNode<Entry<K, V>> left = (RBNode<Entry<K, V>>) sibling.getLeft();
					RBNode<Entry<K, V>> right = (RBNode<Entry<K, V>>) sibling.getRight();

					if ((left != null && left.isRed()) || (right != null && right.isRed())) {
						restructure(posR);
					} else {
						sibling.setColour(RED); // Sibling goes red
						if (parent.isRed()) {
							parent.setColour(BLACK); // Parent goes black if was red
						} else {
							remedyDoubleBlack(parent);
						}
					}
				}
			} 
		} else {
			remedyDoubleBlack(parent);
		}

	}
}
