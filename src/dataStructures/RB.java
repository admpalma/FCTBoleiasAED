package dataStructures;

import com.sun.corba.se.spi.ior.MakeImmutable;

import dataStructures.RB.RBNode;

public class RB<K extends Comparable<K>, V> extends AdvancedBST<K, V> implements SortedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean BLACK = false;
	private static final boolean RED = true;

	static class RBNode<E> extends BSTNode<E> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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

		/**
		 * @return <code>true</code> if one of {@link RBNode this RBNode's} adjancent
		 *         {@link RBNode RBNodes} is red, <code>false</code> otherwise
		 */
		public boolean hasAdjacentRed() {
			if (parent != null && ((RBNode<E>) parent).isRed()) {
				return true;
			} else if (right != null && ((RBNode<E>) right).isRed()) {
				return true;
			} else if (left != null && ((RBNode<E>) left).isRed()) {
				return true;
			}
			return false;
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
		RBNode<Entry<K, V>> closestNode = (RBNode<Entry<K, V>>) findClosest(key);
		V overriddenValue = null;

		if (closestNode != null) {
			overriddenValue = closestNode.element.getValue();
		}

		RBNode<Entry<K, V>> insertedNode = (RBNode<Entry<K, V>>) insertAux(key, value, closestNode);

		insertedNode.setColour(RED); // Red
		
		// TODO
		if (size() < 3) return overriddenValue;
		
		// TODO for tests
		if (insertedNode.getParent() == null || insertedNode == root) {
			insertedNode.setColour(BLACK); // Black
			return null;
		} else {
			// TODO
			if (insertedNode.getParent().getParent() == null)
				return overriddenValue;
			remedyDoubleRed(insertedNode);
		}

		return overriddenValue;
	}

	// pre: !isRoot(x)
	protected void remedyDoubleRed(RBNode<Entry<K, V>> x) {
		RBNode<Entry<K, V>> parent, grandparent, uncle;
		parent = (RBNode<Entry<K, V>>) x.getParent();

		// parent came to null in second run of this
		// meaning the if failed and grandparent has no parent

		if (parent.isRed()) {
			
			// TODO we set uncle but can't if grandparent is null
			
			grandparent = (RBNode<Entry<K, V>>) parent.getParent();
			
			// TODO GRANDPARENT NULL??
			if (grandparent == null) {
				uncle=null;
			}
			else {
				// TODO WHAT IF GRANDPARENT IS NULL?
				// TODO FAILED IN 3rd recursion cause grandparent was null but shouldn't be
				// since it's a check that we do before calling the recursion??
				if (grandparent.getLeft() != null && grandparent.getLeft().equals(parent)) {
					uncle = (RBNode<Entry<K, V>>) grandparent.getRight();
				} else {
					uncle = (RBNode<Entry<K, V>>) grandparent.getLeft();
				}
			}

			// RED parent
			assert (parent != null);
			// we have a double red: posZ and posV
			// Case black uncle ou null: trinode restructuring
			if (uncle == null || !uncle.isRed()) {
				/*if (uncle==null) {
					uncle = new RBNode<Entry<K,V>>(null, grandparent, null, null);
				}*/
				RBNode<Entry<K, V>> node = (RBNode<Entry<K, V>>) restructure(x); // restructure
				// recolor
				// grandparent.setColour(!grandparent.isRed);
				// node.setColour(!node.isRed);
				node.setColour(BLACK);
				((RBNode<Entry<K, V>>) node.getLeft()).setColour(RED);
				((RBNode<Entry<K, V>>) node.getRight()).setColour(RED);
				// TODO might have root problems
			}
			// Case red uncle: recoloring
			else {
				// recolor
				parent.setColour(BLACK);
				uncle.setColour(BLACK);
				// TODO TESTS grandparent != root
				if (grandparent.getParent() != null) {
					grandparent.setColour(RED);
					remedyDoubleRed(grandparent);
				}
			}
		}

	}

	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;

		// Remove as BST remove
		RBNode<Entry<K, V>> removedNode = (RBNode<Entry<K, V>>) removeAux(key);

		// TODO CHECK THIS SINCE BOOK TALKS ABOUT OVERWRITTING SOMETHING

		if (removedNode.isRed()) {
			removedNode.setColour(BLACK);
		}
		// TODO TESTS
		else if (removedNode.parent == null || removedNode != root) {
			// Get the sibling
			RBNode<Entry<K, V>> sibling = removedNode.getParent().getLeft().equals(removedNode)
					? (RBNode<Entry<K, V>>) removedNode.getParent().getRight()
					: (RBNode<Entry<K, V>>) removedNode.getParent().getLeft();

			if (sibling.isInternal() && (!sibling.isRed() || sibling.getLeft().isInternal())) {
				remedyDoubleBlack(removedNode);
			}

			/*
			 * RBNode<Entry<K, V>> left = (RBNode<Entry<K, V>>) removedNode.getLeft();
			 * RBNode<Entry<K, V>> right = (RBNode<Entry<K, V>>) removedNode.getRight(); //
			 * case black node without children: remedyDoubleBlack(node) if
			 * (removedNode.left == null && removedNode.right == null) { // No children
			 * remedyDoubleBlack(removedNode); // case black node with a red child:
			 * recoloring (set children black) } else if (removedNode.left != null &&
			 * left.isRed()) { // Set black left.setColour(BLACK); } else {
			 * right.setColour(BLACK); }
			 */
		}
		// case red node: end
		return removedNode.element.getValue();
	}

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(RBNode<Entry<K, V>> x) {
		RBNode<Entry<K, V>> parent = (RBNode<Entry<K, V>>) x.getParent();
		RBNode<Entry<K, V>> sibling;

		if (parent.getLeft().equals(x)) {
			sibling = (RBNode<Entry<K, V>>) parent.getRight();
		} else {
			sibling = (RBNode<Entry<K, V>>) parent.getLeft();
		}

		if (sibling.isRed()) {
			restructure(sibling);
			sibling.setColour(BLACK);
			parent.setColour(RED);
			remedyDoubleBlack(x);
		} else {
			RBNode<Entry<K, V>> left = (RBNode<Entry<K, V>>) sibling.getLeft();
			RBNode<Entry<K, V>> right = (RBNode<Entry<K, V>>) sibling.getRight();

			if ((left != null && left.isRed()) || (right != null && right.isRed())) {
				RBNode<Entry<K, V>> node = (RBNode<Entry<K, V>>) restructure(x);
				x.setColour(parent.isRed());
				((RBNode<Entry<K, V>>) node.getLeft()).setColour(BLACK);
				((RBNode<Entry<K, V>>) node.getRight()).setColour(BLACK);
			} else {
				sibling.setColour(RED); // Sibling goes red
				if (parent.isRed()) {
					parent.setColour(BLACK); // Parent goes black if was red
				} else if (parent != root || parent.getParent() != null) { // TODO TESTS
					remedyDoubleBlack(parent);
				}
			}
		}

	}

	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new RBNode<Entry<K, V>>(element, (RBNode<Entry<K, V>>) parent, (RBNode<Entry<K, V>>) left,
				(RBNode<Entry<K, V>>) right);
	}
}
