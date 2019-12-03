package dataStructures;

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
		RBNode<Entry<K, V>> u = (RBNode<Entry<K, V>>) removeAux(v);
		if (u == v) {
			v = null;
		}
		fixAfterRemoval(u, v);
		return removedValue;
	}

	/**
	 * @param u
	 * @param v 
	 */
	private void fixAfterRemoval(RBNode<Entry<K, V>> u, RBNode<Entry<K, V>> v) {
		// TODO CHECK THIS SINCE BOOK TALKS ABOUT OVERWRITTING SOMETHING
		
		

		if (u.isRed() || RBNode.isRed(v)) {
			u.setColour(BLACK);
		} else if (!u.isRed() && !RBNode.isRed(v)) {
			u.setColour(BLACK);
			remedyDoubleBlack(u);
		}
		// throw new Ass
			
		/*if (u.parent == null || u != root) {
			// Get the sibling
			RBNode<Entry<K, V>> sibling = u.getParent().getLeft().equals(u)
					? (RBNode<Entry<K, V>>) u.getParent().getRight()
					: (RBNode<Entry<K, V>>) u.getParent().getLeft();

			if (sibling.isInternal() && (!sibling.isRed() || sibling.getLeft().isInternal())) {
				remedyDoubleBlack(u);
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
		//}
		// case red node: end
	}

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(RBNode<Entry<K, V>> u) {
		RBNode<Entry<K, V>> parent = (RBNode<Entry<K, V>>) u.getParent();		
		RBNode<Entry<K, V>> sibling = (RBNode<Entry<K, V>>) parent.getSiblingOf(u);
		if (sibling == null || !sibling.isRed()) {
			
			if (sibling.hasRedChild()) {
				
			}
			
			
			
			
			
			RBNode<Entry<K, V>> left = (RBNode<Entry<K, V>>) sibling.getLeft();
			RBNode<Entry<K, V>> right = (RBNode<Entry<K, V>>) sibling.getRight();

			if ((left != null && left.isRed()) || (right != null && right.isRed())) {
				RBNode<Entry<K, V>> node = (RBNode<Entry<K, V>>) restructure(u);
				u.setColour(parent.isRed());
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
		} else {
			restructure(sibling);
			sibling.setColour(BLACK);
			parent.setColour(RED);
			remedyDoubleBlack(u);
		}
	}

	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new RBNode<Entry<K, V>>(element, (RBNode<Entry<K, V>>) parent, (RBNode<Entry<K, V>>) left,
				(RBNode<Entry<K, V>>) right);
	}
}
