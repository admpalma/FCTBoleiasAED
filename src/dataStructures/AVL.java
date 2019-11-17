package dataStructures;

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
		// TODO
		return null;
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
				//If yPos (tallerChild(zPos)) denote the child of zPos with greater height. 
				//Finally, let xPos be the child of yPos with greater height
				AVLNode<Entry<K, V>> xPos = tallerChild((AVLNode<Entry<K, V>>) tallerChild(zPos));
				zPos = (AVLNode<Entry<K, V>>) restructure(xPos); // tri-node restructure (from parent class)
				((AVLNode<Entry<K, V>>) zPos.getLeft()).setHeight(); // recompute heights
				((AVLNode<Entry<K, V>>) zPos.getRight()).setHeight();
				zPos.setHeight();
			}
		}
	}

	@Override
	public V insert(K key, V value) {
		// TODO
		V valueToReturn = null;
		AVLNode<Entry<K, V>> newNode = null; // node where the new entry is being inserted (if find(key)==null)
		// insert the new Entry (if find(key)==null)
		// or set the new value (if find(key)!=null)
		if (newNode != null) // (if find(key)==null)
			rebalance(newNode); // rebalance up from the insertion node
		return valueToReturn;
	}

	@Override
	public V remove(K key) {
		// TODO
		V valueToReturn = null;
		AVLNode<Entry<K, V>> node = null; // father of node where the key was
		// removeNode is the BST remove(key)
		if (node != null) // (if find(key)==null)
			rebalance(node); // rebalance up from the node
		return valueToReturn;
	}

}
