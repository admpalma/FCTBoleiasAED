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
			BSTNode<E> sibling = (child == left) ? right : left;
			return sibling;
		}

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
		RBNode<Entry<K, V>> deletedNode = (RBNode<Entry<K, V>>) removeAux(v);
		v.setColour(deletedNode.isRed());
		RBNode<Entry<K, V>> x = getFromReplacementNode(deletedNode);
		fixAfterRemoval(deletedNode, x);
		return removedValue;
	}

	/**
	 * Initial steps: part 1
	 * @param deletedNode
	 * @return
	 */
	private RBNode<Entry<K, V>> getFromReplacementNode(RBNode<Entry<K, V>> deletedNode) {
		// TODO Auto-generated method stub
		if (deletedNode.right != null) {
			return (RBNode<Entry<K, V>>) deletedNode.right;
		} else if (deletedNode.left != null) {
			return (RBNode<Entry<K, V>>) deletedNode.left;
		} else {
			return null;
		}
	}

	private enum RemovalFixes {
		x_isRed, x_isBlackAndSibling_isRed, x_isBlackAndSibling_isBlackAndBothChildren_areBlack,
		third_A, third_B, fourth_A, fourth_B;

		public static RemovalFixes identifyFix(RBNode<?> x, RBNode<?> w, RBNode<?> deletedNode) {
			// TODO Auto-generated method stub
			if (RBNode.isRed(x)) {
				return x_isRed;
			} else {
				if (RBNode.isRed(w)) {
					return x_isBlackAndSibling_isRed;
				} else {
					if (!RBNode.isRed((RBNode<?>) w.right) && !RBNode.isRed((RBNode<?>) w.left)) {
						return x_isBlackAndSibling_isBlackAndBothChildren_areBlack;
					} else if (x == deletedNode.left) {
						if (RBNode.isRed((RBNode<?>) w.right)) {
							return fourth_A;
						} else if (RBNode.isRed((RBNode<?>) w.left) && !RBNode.isRed((RBNode<?>) w.right)) {
							return third_A;
						}
					} else if (x == deletedNode.right) {
						if (RBNode.isRed((RBNode<?>) w.left)) {
							return fourth_A;
						} else if (RBNode.isRed((RBNode<?>) w.right) && !RBNode.isRed((RBNode<?>) w.left)) {
							return third_A;
						}
					}
				}
			}
			return null;
		}
		
	}
	
	/**
	 * @param u
	 * @param v 
	 */
	private void fixAfterRemoval(RBNode<Entry<K, V>> deletedNode, RBNode<Entry<K, V>> x) {
		if (deletedNode.isRed() && (RBNode.isRed(x) || x == null)) {
			//done
		} else if (!deletedNode.isRed() && (!RBNode.isRed(x) || x == null)) {
			RBNode<Entry<K, V>> sibling = (RBNode<Entry<K, V>>) deletedNode.parent.getSiblingOf(x);
			RemovalFixes fix = RemovalFixes.identifyFix(x, sibling, deletedNode);
			remedyDoubleBlack(x, sibling, deletedNode, fix);
		} else if (deletedNode.isRed() && !x.isRed()) {
			x.setColour(RED);
			RBNode<Entry<K, V>> sibling = (RBNode<Entry<K, V>>) deletedNode.getSiblingOf(x);
			RemovalFixes fix = RemovalFixes.identifyFix(x, sibling, deletedNode);
			remedyDoubleBlack(x, sibling, deletedNode, fix);
		} else if (!deletedNode.isRed() && x.isRed()) {
			x.setColour(BLACK);
			//done
		}
	}

	/** Remedies a double black violation at a given node caused by removal. 
	 * @param v 
	 * @param deletedNode 
	 * @param fix */
	protected void remedyDoubleBlack(RBNode<Entry<K, V>> x, RBNode<Entry<K, V>> w, RBNode<Entry<K, V>> deletedNode, RemovalFixes fix) {
		switch (fix) {
		case fourth_A:
			w.setColour(((RBNode<?>) w.parent).isRed());
			((RBNode<?>) w.parent).setColour(BLACK);
			if (w.right != null) {
				((RBNode<?>) w.right).setColour(BLACK);
			}
			rotateLeft(w.parent);
			break;
		case fourth_B:
			w.setColour(((RBNode<?>) x.parent).isRed());
			((RBNode<?>) x.parent).setColour(BLACK);
			((RBNode<?>) w.left).setColour(BLACK);
			rotateRight(x.parent);
			break;
		case third_A:
			((RBNode<?>) w.left).setColour(BLACK);
			w.setColour(RED);
			rotateRight(w);
			w = (RBNode<Entry<K, V>>) w.parent.right;
			remedyDoubleBlack(x, w, deletedNode, RemovalFixes.fourth_A);
			break;
		case third_B:
			((RBNode<?>) w.right).setColour(BLACK);
			w.setColour(RED);
			rotateLeft(w);
			w = (RBNode<Entry<K, V>>) x.parent.left;
			remedyDoubleBlack(x, w, deletedNode, RemovalFixes.fourth_B);
			break;
		case x_isBlackAndSibling_isBlackAndBothChildren_areBlack:
			w.setColour(RED);
			x = (RBNode<Entry<K, V>>) w.parent;
			if (x.isRed()) {
				x.setColour(RED);
			} else {
				RBNode<Entry<K, V>> sibling = (RBNode<Entry<K, V>>) w.parent.getSiblingOf(x);
				RemovalFixes refix = RemovalFixes.identifyFix(x, sibling, (RBNode<?>) x.parent);
				remedyDoubleBlack(x, sibling, (RBNode<Entry<K, V>>) x.parent, refix);
			}
			break;
		case x_isBlackAndSibling_isRed:
			w.setColour(BLACK);
			((RBNode<?>) w.parent).setColour(BLACK);
			if (x == w.parent.left) {
				rotateLeft(w.parent);
				w = (RBNode<Entry<K, V>>) w.parent.right;
			} else if (x == w.parent.right) {
				rotateRight(w.parent);
				w = (RBNode<Entry<K, V>>) w.parent.left;
			} else {
				throw new AssertionError();
			}
			RemovalFixes refix = RemovalFixes.identifyFix(x, w, deletedNode);
			remedyDoubleBlack(x, w, deletedNode, refix);
			break;
		case x_isRed:
			x.setColour(BLACK);
			break;
		default:
			break;
		}
	}

	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new RBNode<Entry<K, V>>(element, (RBNode<Entry<K, V>>) parent, (RBNode<Entry<K, V>>) left,
				(RBNode<Entry<K, V>>) right);
	}
}
