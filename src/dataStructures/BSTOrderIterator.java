package dataStructures;

import dataStructures.BST.BSTNode;

public class BSTOrderIterator<K, V> implements Iterator<Entry<K, V>> {

	protected Stack<BSTNode<Entry<K, V>>> stack;
	/** For rewind */
	private BSTNode<Entry<K, V>> root;

	public BSTOrderIterator(BSTNode<Entry<K, V>> root) {
		this.root = root;
		if (root == null) {
			stack = new StackInList<BSTNode<Entry<K, V>>>();
		} else {
			rewind();
		}
	}
	
	/**
	 * Best case: O(1) if node is null
	 * Average case: O(h), h is height
	 * Worst case: O(h), h is height
	 * Pushes to the stack every node from given node following their lefts
	 * @param node node from which to push the left subtree to the stack
	 */
	private void pushLeftSubtree(BSTNode<Entry<K, V>> node) {
		while (node != null) {
			stack.push(node);
			node = node.left;
		}
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	/**
	 * Best case: O(1)
	 * Average case: O(h), h is height
	 * Worst case: O(h), h is height
	 */
	public Entry<K, V> next() throws NoSuchElementException {

		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		BSTNode<Entry<K, V>> node = stack.pop();
		Entry<K, V> next = node.element;

		if (node.right != null) {
			node = node.right;
			pushLeftSubtree(node);
		}

		return next;
	}

	@Override
	/**
	 * Best case: O(1) if node is null
	 * Average case: O(h)
	 * Worst case: O(h), h is height
	 */
	public void rewind() {
		stack = new StackInList<BSTNode<Entry<K, V>>>();
		pushLeftSubtree(root);
	}

}
