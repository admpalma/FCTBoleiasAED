package dataStructures;

public class AVL<K extends Comparable<K>, V> extends AdvancedBST<K, V> implements SortedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static class AVLNode<E> extends BSTNode<E> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
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
			int dif = getBalance();
			return dif == 0 || dif == -1 || dif == 1;
		}
		
		public int getBalance() {
			return getHeight((AVLNode<E>) left) - getHeight((AVLNode<E>) right);
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
	 * Return a child of p with greater height, <code>null</code> if both children have the same height
	 */
	protected AVLNode<Entry<K, V>> tallerChild(AVLNode<Entry<K, V>> p) {
		AVLNode<Entry<K, V>> left = (AVLNode<Entry<K, V>>) p.left;
		AVLNode<Entry<K, V>> right = (AVLNode<Entry<K, V>>) p.right;
		//TODO I swear to god i hate crappy comments, para isto mais valia nem terem dado nada xD
		//assert(p.getHeight(left) != p.getHeight(right));
		// If left node's height is higher, return left node
		// If right node's height is higher, return right node
		if (p.getHeight(left) == p.getHeight(right)) {
			return null;
		}
		return p.getHeight(left) > p.getHeight(right) ? left : right;

	}

	@Override
	public V insert(K key, V value) {
		AVLNode<Entry<K, V>> closestNode = (AVLNode<Entry<K, V>>) findClosest(key);
		V overriddenValue = null;
		if (closestNode != null) {
			overriddenValue = closestNode.element.getValue();
		}
		AVLNode<Entry<K, V>> insertedNode = (AVLNode<Entry<K, V>>) insertAux(key, value, closestNode);
		if (closestNode == insertedNode) {
			return overriddenValue;
		} else {
			rebalance(insertedNode);
			return null;
		}
	}

	/**
	 * Rebalance method called by insert and remove. Traverses the path from zPos to
	 * the root. For each node encountered, we recompute its height and perform a
	 * trinode restructuring if it's unbalanced. the rebalance is completed with
	 * O(log n)running time
	 */
	protected void rebalance(AVLNode<Entry<K, V>> insertedNode) {
		AVLNode<Entry<K, V>> unbalancedNode = insertedNode;
		AVLNode<Entry<K, V>> balancedSubroot = null;
		do {
			unbalancedNode = findUnbalanced(unbalancedNode);
			if (unbalancedNode != null) {
				balancedSubroot = rebalanceSubtree(unbalancedNode);
				if (balancedSubroot.parent == null) {
					root = balancedSubroot;
				}
				unbalancedNode = balancedSubroot;
			}
		} while (unbalancedNode != null && root != balancedSubroot);
	}
	
	/**
	 * Rebalances a subtree rooted at z
	 * @param z root of the subtree to rebalance
	 * @return root of the balanced subtree
	 */
	private AVLNode<Entry<K, V>> rebalanceSubtree(AVLNode<Entry<K, V>> z) {
		AVLNode<Entry<K, V>> y = tallerChild(z);
		AVLNode<Entry<K, V>> x = tallerChild(y);
		if (x == null) {
			int balanceFactor = z.getBalance();
			if (balanceFactor <= 0) {
				x = (AVLNode<Entry<K, V>>) y.left;
			} else {
				x = (AVLNode<Entry<K, V>>) y.right;
			}
		}
		z = (AVLNode<Entry<K, V>>) restructure(x);
		((AVLNode<Entry<K, V>>) z.getLeft()).setHeight();
        ((AVLNode<Entry<K, V>>) z.getRight()).setHeight();
		z.setHeight();
		return z;
	}

	/**
	 * Iterates upwards from node updating its parent's heights until an unbalanced subtree is found
	 * @param node {@link AVLNode} from which to start the iteration
	 * @return root of the first found unbalanced subtree upwards from node, <code>null</code> if there's no such subtree
	 */
	private AVLNode<Entry<K, V>> findUnbalanced(AVLNode<Entry<K, V>> node) {
		if (node.isInternal()) {
			node.setHeight();
		}
		AVLNode<Entry<K, V>> current = node;
		while (current.parent != null && current.isBalance()) {
			current = (AVLNode<Entry<K, V>>) current.parent;
			current.setHeight();
		}
		if (!current.isBalance()) {
			return current;
		} else {
			return null;
		}
		
	}

	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left, BSTNode<Entry<K, V>> right) {
		//TODO isto com genericos era lit mas da um pouco de trabalho
		return new AVLNode<Entry<K, V>>(element, (AVLNode<Entry<K, V>>) parent, (AVLNode<Entry<K, V>>) left, (AVLNode<Entry<K, V>>) right);
	}

	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;

		AVLNode<Entry<K, V>> removed = (AVLNode<Entry<K, V>>) removeAux(key);
		// TODO not always needed? if we remove the root probably not
		rebalance(removed); // rebalance up from the node
		return removed.element.getValue();
	}

}
