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

		public boolean isBlack() {
			return !isRed;
		}

		public boolean isRed() {
			return isRed;
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * @param node given {@link RBNode}
		 * @return <code>false</code> if node == <code>null</code> or node is {@link RB#BLACK}, <code>true</code> if node is {@link RB#RED}
		 */
		protected static <K, V> boolean colourOf(RBNode<Entry<K, V>> node) {
			return node == null ? BLACK : node.isRed;
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * @param parent parent of the child we wish to get
		 * @param right child selector (<code>true</code> if right, <code>false</code> if left)
		 * @return child of the given <code>parent</code>, <code>null</code> it the parent is <code>null</code>
		 */
		protected static <K, V> RBNode<Entry<K, V>> childOf(RBNode<Entry<K, V>> parent, boolean right) {
			if (parent != null)
				return right ? (RBNode<Entry<K, V>>) parent.right : (RBNode<Entry<K, V>>) parent.left;
			else return null; 
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * Changes the colour of the given {@link RBNode node} to the given <code>colour</code>
		 * @param node given {@link RBNode node}
		 * @param colour given colour
		 */
		protected static <K, V> void setColour(RBNode<Entry<K, V>> node, boolean colour) {
			if (node != null) {
				node.isRed = colour;
			}
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * @param node {@link RBNode} to check if is red
		 * @return <code>true</code> if the given node is red, <code>false</code> otherwise
		 */
		protected static <E> boolean isRed(RBNode<E> node) {
			return node == null ? false : node.isRed;
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * @return <code>true</code> if one of {@link RBNode this RBNode's} adjacent
		 *         {@link RBNode RBNodes} is red, <code>false</code> otherwise
		 */
		protected boolean hasAdjacentRed() {
			if (parent != null && ((RBNode<E>) parent).isRed()) {
				return true;
			} else if (right != null && ((RBNode<E>) right).isRed()) {
				return true;
			} else if (left != null && ((RBNode<E>) left).isRed()) {
				return true;
			}
			return false;
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * @return <code>true</code> if this {@link RBNode} has a red child, <code>false</code> otherwise
		 */
		protected boolean hasRedChild() {
			if (right != null && ((RBNode<E>) right).isRed()) {
				return true;
			} else if (left != null && ((RBNode<E>) left).isRed()) {
				return true;
			}
			return false;
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * Supports <code>null</code> as a valid child
		 */
		@Override
		protected BSTNode<E> getSiblingOf(BSTNode<E> child) {
			BSTNode<E> sibling = (child == left) ? right : left;
			return sibling;
		}

		/**
		 * Best case: O(1)<p>
		 * Average case: O(1)<p>
		 * Worst case: O(1)<p>
		 * @return {@link RB#RED} child of this {@link RBNode}, <code>null</code> if this node has no children
		 */
		protected RBNode<E> getRedChild() {
			if (isRed((RBNode<E>) right)) {
				return (RBNode<E>) right;
			} else if (isRed((RBNode<E>) left)) {
				return (RBNode<E>) left;
			} else {
				return null;
			}
		}

	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 * Constructs a new {@link RB} with n as {@link BST#root}
	 * @param n new root
	 */
	protected RB(RBNode<Entry<K, V>> n) {
		root = n;
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 * Constructs a new {@link RB} a <code>null</code> {@link BST#root}
	 */
	public RB() {
		this(null);
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(log n)<p>
	 * Worst case: O(log n)<p>
	 */
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
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 *
	 * Performs the needed operations to keep the RB properties of this {@link RB}
	 * after an insertion
	 * 
	 * @param redNode {@link RBNode} that disrupted this {@link RB RB's} properties
	 */
	private void fixAfterInsertion(RBNode<Entry<K, V>> redNode) {
		assert (redNode.isRed());
		if (redNode == root) {
			redNode.setColour(BLACK);
		} else if (((RBNode<Entry<K, V>>) redNode.getParent()).isRed()) {
			remedyDoubleRed(redNode);
		}
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 *
	 * Remedies a double red situation
	 * 
	 * @param redNode {@link RBNode} that disrupted this {@link RB RB's} properties
	 */
	protected void remedyDoubleRed(RBNode<Entry<K, V>> redNode) {
		assert (redNode != root);
		assert (((RBNode<Entry<K, V>>) redNode.getParent()).isRed());
		assert (redNode.getParent().getParent() != null);

		RBNode<Entry<K, V>> parent = (RBNode<Entry<K, V>>) redNode.getParent();
		RBNode<Entry<K, V>> grandparent = (RBNode<Entry<K, V>>) parent.getParent();
		RBNode<Entry<K, V>> uncle = (RBNode<Entry<K, V>>) grandparent.getUncleOf(redNode);

		if (uncle == null || uncle.isBlack()) {
			fixBlackUncleCaseAfterInsertion(redNode);
		} else {
			fixRedUncleCaseAfterInsertion(parent, grandparent, uncle);
		}
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 *
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
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 *
	 * 
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

	/**
	 * Best case: O(1)<p>
	 * Average case: O(log n)<p>
	 * Worst case: O(2 log n)<p>
	 */
	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;
		RBNode<Entry<K, V>> foundNode = (RBNode<Entry<K, V>>) findNode(root, key);
		if (foundNode == null) {
			return null;
		}
		V removedValue = foundNode.element.getValue();
		removeNode(foundNode);
		return removedValue;
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(log n)<p>
	 * Worst case: O(log n)<p>
	 * Removes the given node element from the tree
	 */
	private void removeNode(RBNode<Entry<K, V>> removedNode) {
		// TODO refactor removeAux "reversion"
		boolean removedNodeWasRightChild = false;
		removedNode = (RBNode<Entry<K, V>>) removeAux(removedNode);
		if (removedNode.parent != null) {
			removedNodeWasRightChild = 0 < removedNode.element.getKey().compareTo(removedNode.parent.element.getKey());
		}
		RBNode<Entry<K, V>> replacement = (removedNode.right != null ? (RBNode<Entry<K, V>>) removedNode.right : (RBNode<Entry<K, V>>) removedNode.left);
		if (replacement != null) {
			// Break connections to let remedyDoubleBlack work
			removedNode.parent = removedNode.right = removedNode.left = null;
			if (removedNode.isRed == BLACK) {
				remedyDoubleBlack(replacement);
			}
		} else if (removedNode.parent != null) {
			// Relink connection broken by removeAux for remedyDoubleBlack to work
			if (removedNodeWasRightChild) {
				removedNode.parent.right = removedNode;
			} else {
				removedNode.parent.left = removedNode;
			}
			if (removedNode.isRed == BLACK) {
				remedyDoubleBlack(removedNode);
			}
			// Break connection only meant to let remedyDoubleBlack work
			removeExternal(removedNode.parent, removedNodeWasRightChild);
		}
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 * Performs a rotation rooted at Y based on <code>right</code>, {@link AdvancedBST#rotateRight(BSTNode)} if <code>right</code> 
	 * @param Y root of the chosen rotation
	 * @param right 
	 * @return
	 */
	private RBNode<Entry<K, V>> rotate(RBNode<Entry<K, V>> Y, boolean right) {
		return right ? (RBNode<Entry<K, V>>) rotateRight(Y) : (RBNode<Entry<K, V>>) rotateLeft(Y);
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(h/2)<p>
	 * Worst case: O(h), h as height of this tree<p>
	 *
	 * Remedies a double black violation at <code>doubleBlack</code>
	 * @param doubleBlack
	 */
	protected void remedyDoubleBlack(RBNode<Entry<K, V>> doubleBlack) {
		while (doubleBlack != root && RBNode.colourOf(doubleBlack) == BLACK) {
			boolean right = doubleBlack == RBNode.childOf((RBNode<Entry<K, V>>) doubleBlack.parent, true);
			RBNode<Entry<K, V>> sibling = RBNode.childOf((RBNode<Entry<K, V>>) doubleBlack.parent, !right);
			if (RBNode.colourOf(sibling) == RED) {
				RBNode.setColour(sibling, BLACK);
				RBNode.setColour((RBNode<Entry<K, V>>) doubleBlack.parent, RED);
				RBNode<Entry<K, V>> subRoot = rotate((RBNode<Entry<K, V>>) doubleBlack.parent,
						right);
				root = subRoot.parent == null ? root = subRoot : root;
				sibling = RBNode.childOf((RBNode<Entry<K, V>>) doubleBlack.parent, !right);
			}
			if (RBNode.colourOf(RBNode.childOf(sibling, right)) == BLACK
					&& RBNode.colourOf(RBNode.childOf(sibling, !right)) == BLACK) {
				RBNode.setColour(sibling, RED);
				doubleBlack = (RBNode<Entry<K, V>>) doubleBlack.parent;
			} else {
				if (RBNode.colourOf(RBNode.childOf(sibling, !right)) == BLACK) {
					RBNode.setColour(RBNode.childOf(sibling, right), BLACK);
					RBNode.setColour(sibling, RED);
					RBNode<Entry<K, V>> subRoot = rotate(sibling, !right);
					root = subRoot.parent == null ? root = subRoot : root;
					sibling = RBNode.childOf((RBNode<Entry<K, V>>) doubleBlack.parent, !right);
				}
				RBNode.setColour(sibling, RBNode.colourOf((RBNode<Entry<K, V>>) doubleBlack.parent));
				RBNode.setColour(RBNode.childOf(sibling, !right), BLACK);
				RBNode.setColour((RBNode<Entry<K, V>>) doubleBlack.parent, BLACK);
				RBNode<Entry<K, V>> subRoot = rotate((RBNode<Entry<K, V>>) doubleBlack.parent,
						right);
				root = subRoot.parent == null ? root = subRoot : root;
				doubleBlack = (RBNode<Entry<K, V>>) root;
			}
		}
		RBNode.setColour(doubleBlack, BLACK);
	}

	/**
	 * Best case: O(1)<p>
	 * Average case: O(1)<p>
	 * Worst case: O(1)<p>
	 */
	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new RBNode<Entry<K, V>>(element, (RBNode<Entry<K, V>>) parent, (RBNode<Entry<K, V>>) left,
				(RBNode<Entry<K, V>>) right);
	}
}
