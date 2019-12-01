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
		
		/**
		 * @return <code>true</code> if one of {@link RBNode this RBNode's} adjancent {@link RBNode RBNodes} is red,
		 * <code>false</code> otherwise
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
		// TODO
		// RBNode<Entry<K, V>> newNode = null; // node where the new entry is being
		// inserted (if find(key)==null)
		// insert the new Entry (if find(key)==null)
		RBNode<Entry<K, V>> closestNode = (RBNode<Entry<K, V>>) findClosest(key);
		V overriddenValue = null;

		if (closestNode != null) {
			overriddenValue = closestNode.element.getValue();
		}

		RBNode<Entry<K, V>> insertedNode = (RBNode<Entry<K, V>>) insertAux(key, value, closestNode);
		/*
		if (closestNode == insertedNode) {
			return overriddenValue;
		} else {
			// If the node is root set it to BLACK and done
			if (insertedNode == root) {
				insertedNode.setColour(BLACK);
				return null;
			}
			assert(insertedNode != root);
			insertedNode.setColour(RED);
			RBNode<E>
			if (((RBNode<Entry<K, V>>) insertedNode.getParent()).isRed()) {
				RBNode<Entry<K, V>> uncle;
			}
			rebalance(insertedNode);
			return null;
		}
		*/

		insertedNode.setColour(RED); //Red
	    if (insertedNode.getParent()==null)
	    	insertedNode.setColour(BLACK); //Black
	    else
	      remedyDoubleRed(insertedNode); // fix a double-red color violation 
		return overriddenValue;

	}

	//pre: !isRoot(posZ)
	private void remedyDoubleRed(RBNode<Entry<K, V>> posZ) {
		assert(posZ != root);
		RBNode<Entry<K,V>> posV = (RBNode<Entry<K, V>>) posZ.getParent();
		RBNode<Entry<K,V>> grandparent = (RBNode<Entry<K, V>>) posV.getParent();
	    // RED parent
	    // we have a double red: posZ and posV
    	RBNode<Entry<K, V>> uncle = posV.getLeft() != null ? (RBNode<Entry<K, V>>) posV.getLeft() : (RBNode<Entry<K, V>>) posV.getRight() ;
    	// Case black uncle ou null: trinode restructuring
    	if (uncle == null || !uncle.isRed()) {
    		RBNode<Entry<K, V>> node = (RBNode<Entry<K, V>>) restructure(posZ); // restructure
    		// recolor
    		grandparent.setColour(!grandparent.isRed);
    		node.setColour(!node.isRed);
    		// TODO might have root problems
    	}
    	// Case red uncle: recoloring
    	else {
    		// recolor
    		posV.setColour(BLACK);
    		uncle.setColour(BLACK);
    		if (grandparent != root) {
    			grandparent.setColour(RED); 
    		}
    		remedyDoubleRed(grandparent);
    		//restructure
    	}

	}

	@Override
	public V remove(K key) {		
		if (isEmpty())
			return null;

		// Remove as BST remove
		RBNode<Entry<K, V>> removedNode = (RBNode<Entry<K, V>>) removeAux(key);

		if (!removedNode.isRed()) {
			RBNode<Entry<K, V>> left = (RBNode<Entry<K, V>>) removedNode.getLeft();
			RBNode<Entry<K, V>> right = (RBNode<Entry<K, V>>) removedNode.getRight();
			// case black node without children: remedyDoubleBlack(node)
			if (removedNode.left == null && removedNode.right == null) {
				// No children
				remedyDoubleBlack(removedNode);
			// case black node with a red child: recoloring (set children black)
			} else if (removedNode.left != null && left.isRed()) {
				// Set black
				left.setColour(BLACK);
			} else {
				right.setColour(BLACK);
			}
		}
		// case red node: end
		return removedNode.element.getValue();
	}

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(RBNode<Entry<K, V>> posR) {
		RBNode<Entry<K, V>> parent = (RBNode<Entry<K, V>>) posR.getParent();
		RBNode<Entry<K, V>> sibling;

		if (parent.getLeft().equals(posR)) {
			sibling = (RBNode<Entry<K, V>>) parent.getRight();
		} else {
			sibling = (RBNode<Entry<K, V>>) parent.getLeft();
		}

		if (sibling != null) {
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
		} else {
			remedyDoubleBlack(parent);
		}

	}

	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new RBNode<Entry<K, V>>(element, (RBNode<Entry<K, V>>) parent, (RBNode<Entry<K, V>>) left,
				(RBNode<Entry<K, V>>) right);
	}
}
