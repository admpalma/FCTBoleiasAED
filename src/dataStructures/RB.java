package dataStructures;

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
		
		public static boolean isRed(RBNode<?> node) {
			if (node == null) {
				return false;
			} else {
				return node.isRed;
			}
		}

		/**
		 * @return <code>true</code> if one of {@link RBNode this RBNode's} adjacent
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

		public boolean hasRedChild() {
			if (right != null && ((RBNode<E>) right).isRed()) {
				return true;
			} else if (left != null && ((RBNode<E>) left).isRed()) {
				return true;
			}
			return false;
		}
		
		/**
		 * TODO supports <code>null</code> as valid child
		 */
		@Override
		protected BSTNode<E> getSiblingOf(BSTNode<E> child) {
			
			BSTNode<E> sibling = child.equals(child.parent.left)
					? child.parent.right
					: child.parent.left;
			return sibling;
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
		if (closestNode == insertedNode) {
			return overriddenValue;
		} else {
			fixAfterInsertion(insertedNode);
			return null;
		}
	}

	/**
	 * Performs the needed operations to keep the RB properties of this {@link RB} after an insertion
	 * @param redNode {@link RBNode} that disrupted this {@link RB RB's} properties
	 */
	private void fixAfterInsertion(RBNode<Entry<K, V>> redNode) {
		assert(redNode.isRed());
		if (redNode == root) {
			redNode.setColour(BLACK);
		} else if (((RBNode<Entry<K,V>>) redNode.getParent()).isRed()) {
			remedyDoubleRed(redNode);
		}
	}

	/**
	 * Remedies a double red situation
	 * @param redNode {@link RBNode} that disrupted this {@link RB RB's} properties
	 */
	protected void remedyDoubleRed(RBNode<Entry<K, V>> redNode) {
		assert (redNode != root);
		assert (((RBNode<Entry<K, V>>) redNode.getParent()).isRed());
		assert (redNode.getParent().getParent() != null);
		
		RBNode<Entry<K, V>> parent = (RBNode<Entry<K, V>>) redNode.getParent();
		RBNode<Entry<K, V>> grandparent = (RBNode<Entry<K, V>>) parent.getParent();
		RBNode<Entry<K, V>> uncle = (RBNode<Entry<K, V>>) grandparent.getUncleOf(redNode);

		if (uncle == null || !uncle.isRed()) {
			fixBlackUncleCaseAfterInsertion(redNode);
		} else {
			fixRedUncleCaseAfterInsertion(parent, grandparent, uncle);
		}
	}

	/**
	 * TODO
	 * @param redNode
	 */
	private void fixBlackUncleCaseAfterInsertion(RBNode<Entry<K, V>> redNode) {
		RBNode<Entry<K, V>> node = (RBNode<Entry<K, V>>) restructure(redNode);
		node.setColour(BLACK);
		((RBNode<Entry<K, V>>) node.getLeft()).setColour(RED);
		((RBNode<Entry<K, V>>) node.getRight()).setColour(RED);
		if (node.getParent() == null) {
			root = node;
		}
	}

	/**
	 * TODO
	 * @param parent
	 * @param grandparent
	 * @param uncle
	 */
	private void fixRedUncleCaseAfterInsertion(RBNode<Entry<K, V>> parent, RBNode<Entry<K, V>> grandparent,
			RBNode<Entry<K, V>> uncle) {
		parent.setColour(BLACK);
		uncle.setColour(BLACK);
		grandparent.setColour(RED);
		fixAfterInsertion(grandparent);
	}

	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;
		RBNode<Entry<K, V>> v = (RBNode<Entry<K, V>>) findNode(root, key);
		V removedValue = v.element.getValue();
		RBNode<Entry<K, V>> p = (RBNode<Entry<K, V>>) removeAux(v);
		fixAfterRemoval(p);
		return removedValue;
	}

	/**
	 * @param p
	 */
	private void fixAfterRemoval(RBNode<Entry<K, V>> p) {
		if (p.isRed()) {
			p.setColour(BLACK);
		} else if (p.parent != null) {
			RBNode<Entry<K, V>> sibling = (RBNode<Entry<K, V>>) p.parent.getSiblingOf(p);
			if (sibling != null) {
				if (sibling.isInternal() && (!sibling.isRed() || sibling.left.isInternal())) {
					remedyDoubleBlack(p);
				} 
			}			
		}
	}

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(RBNode<Entry<K, V>> p) {
		RBNode<Entry<K, V>> z = (RBNode<Entry<K, V>>) p.getParent();		
		RBNode<Entry<K, V>> y = (RBNode<Entry<K, V>>) z.getSiblingOf(p);
		
		if (!y.isRed()) {
			if (((RBNode<Entry<K,V>>) y.left).isRed() || ((RBNode<Entry<K,V>>) y.right).isRed()) {
				RBNode<Entry<K, V>> x = ((RBNode<Entry<K,V>>) y.left).isRed() ? (RBNode<Entry<K,V>>) y.left : (RBNode<Entry<K,V>>) y.right;
				RBNode<Entry<K, V>> middle = (RBNode<Entry<K, V>>) restructure(x);
				middle.setColour(z.isRed);
				((RBNode<Entry<K,V>>) middle.left).setColour(BLACK);
				((RBNode<Entry<K,V>>) middle.right).setColour(BLACK);
			} else {
				y.setColour(RED);
				if (z.isRed()) {
					z.setColour(BLACK);
				} else if (z != root) {
					remedyDoubleBlack(z);
				}
			}
		} else {
			if (y == y.parent.left) {
				rotateLeft(y);
			} else {
				rotateRight(y);
			}
			y.setColour(BLACK);
			z.setColour(RED);
			remedyDoubleBlack(p);
		}
	}

	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new RBNode<Entry<K, V>>(element, (RBNode<Entry<K, V>>) parent, (RBNode<Entry<K, V>>) left,
				(RBNode<Entry<K, V>>) right);
	}
}
