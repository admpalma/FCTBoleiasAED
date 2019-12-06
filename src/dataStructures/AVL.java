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
			height = 1 + Math.max(getHeight(left), getHeight(right));
		}
		
		/**
		 * O(1)
		 */
		protected int getHeight(AVLNode<E> no) {
			if (no == null)
				return 0;
			return no.getHeight();
		}

		/**
		 * O(1)
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * O(1)
		 */
		public boolean isBalance() {
			int dif = getBalance();
			return dif == 0 || dif == -1 || dif == 1;
		}

		/**
		 * O(1)
		 */
		public int getBalance() {
			return getHeight((AVLNode<E>) left) - getHeight((AVLNode<E>) right);
		}

		/**
		 * O(1)
		 */
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
	 * O(1)
	 * Return a child of p with greater height, <code>right</code> if both children
	 * have the same height
	 */
	protected AVLNode<Entry<K, V>> tallerChild(AVLNode<Entry<K, V>> p) {
		AVLNode<Entry<K, V>> left = (AVLNode<Entry<K, V>>) p.left;
		AVLNode<Entry<K, V>> right = (AVLNode<Entry<K, V>>) p.right;
		return p.getHeight(left) > p.getHeight(right) ? left : right;
	}

	/**
	 * Best case: O(1)
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public V insert(K key, V value) {
		AVLNode<Entry<K, V>> closestNode = (AVLNode<Entry<K, V>>) findClosest(key);
		V overriddenValue = null;
		if (closestNode != null) {
			overriddenValue = closestNode.element.getValue();
		}
		AVLNode<Entry<K, V>> insertedNode = (AVLNode<Entry<K, V>>) insertAux(key, value, closestNode);
		if (closestNode == insertedNode || insertedNode == root) {
			return overriddenValue;
		} else {
			rebalance(insertedNode);
			return null;
		}
	}

	/**
	 * O(log n)
	 * Rebalance method called by insert and remove. Traverses the path from zPos to
	 * the root while it finds unbalanced nodes. For each unbalanced node
	 * encountered, we perform a trinode restructuring and recompute its height. The
	 * rebalance is completed within (<=) O(log n) running time
	 */
	protected void rebalance(AVLNode<Entry<K, V>> z) {

		if (z.isInternal()) {
			z.setHeight();
		}

		while (z.parent != null && z.isBalance()) {
			z = (AVLNode<Entry<K, V>>) z.parent;
		}

		// Loop while z != root
		while (z.parent != null) {
			z = (AVLNode<Entry<K, V>>) z.parent;
			z.setHeight();
			if (!z.isBalance()) {
				z = (AVLNode<Entry<K, V>>) restructure(tallerChild(tallerChild(z)));
				((AVLNode<Entry<K, V>>) z.getLeft()).setHeight();
				((AVLNode<Entry<K, V>>) z.getRight()).setHeight();
				z.setHeight();

				if (z.parent == null) {
					// Might need to change root
					root = z;
				}
			} else {
				// If we find a balanced node we stop, we know no other node will be unbalanced
				break;
			}
		}
	}

	/**
	 * O(1)
	 */
	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new AVLNode<Entry<K, V>>(element, (AVLNode<Entry<K, V>>) parent, (AVLNode<Entry<K, V>>) left,
				(AVLNode<Entry<K, V>>) right);
	}

	/**
	 * Best case: O(1)
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;
		AVLNode<Entry<K, V>> foundNode = (AVLNode<Entry<K, V>>) findNode(root, key);
		if (foundNode == null)
			return null;
		V removedValue = foundNode.element.getValue();
		AVLNode<Entry<K, V>> removedNode = (AVLNode<Entry<K, V>>) removeAux(foundNode);
		rebalance(removedNode); // rebalance up from the node
		return removedValue;
	}

}
