package dataStructures;

public class AdvancedBST<K extends Comparable<K>, V> extends BST<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String RIGHTRIGHT = "11";
	private static final String RIGHTLEFT = "10";
	private static final String LEFTRIGHT = "01";
	private static final String LEFTLEFT = "00";

	// metodos comuns a arvores binarias de pesquisa avancadas
	// Operacoes basicas para trocar a forma da arvore tratando
	// de reduzir a sua altura

	/**
	 * Performs a single left rotation rooted at Y node. Node X was a right child of
	 * Y before the rotation, then Y becomes the left child of X after the rotation.
	 * 
	 * @param Y - root of the rotation
	 * @pre: Y has a right child
	 */
	protected void rotateLeft(BSTNode<Entry<K, V>> Y) {
		// a single rotation modifies a constant number of parent-child relationships,
		// it can be implemented in O(1)time
		BSTNode<Entry<K, V>> pivot = Y.right;
		BSTNode<Entry<K, V>> rootParent = Y.parent;
		//TODO readability refactor
		Y.parent = pivot;
		pivot.parent = rootParent;
		if (rootParent != null) {
			if (rootParent.element.getKey().compareTo(pivot.element.getKey()) < 0) {
				rootParent.right = pivot;
			} else {
				rootParent.left = pivot;
			}
		}
		Y.right = pivot.left;
		if (pivot.left != null) {
			pivot.left.parent = Y;
		}
		pivot.left = Y;
		
		
	}

	/**
	 * Performs a single right rotation rooted at Y node. Node X was a left child of
	 * Y before the rotation, then Y becomes the right child of X after the
	 * rotation.
	 * 
	 * @param Y - root of the rotation
	 * @pre: Y has a left child
	 */
	protected void rotateRight(BSTNode<Entry<K, V>> Y) {
		// a single rotation modifies a constant number of parent-child relationships,
		// it can be implemented in O(1)time
		BSTNode<Entry<K, V>> pivot = Y.left;
		BSTNode<Entry<K, V>> rootParent = Y.parent;
		//TODO readability refactor
		if (pivot.right != null) {
			pivot.right.parent = Y;
		}
		Y.left = pivot.right;
		pivot.right = Y;
		Y.parent = pivot;
		pivot.parent = rootParent;
		if (rootParent != null) {
			if (rootParent.element.getKey().compareTo(pivot.element.getKey()) < 0) {
				rootParent.right = pivot;
			} else {
				rootParent.left = pivot;
			}
		}
	}
	
	/**
	 * This method does the mutual part of the rotation for both rotateLeft and rotateRight
	 * Checks if our root of rotation is the tree's root
	 * @param Y - root of rotation
	 */
	private void rotateAux(BSTNode<Entry<K, V>> Y) {
		//TODO O comentado simula que Y e root, mas tbh isto devia ser abstrato e verificar a root torna-se nonsense
		if (Y == root || Y.parent == null) {
			root = Y.left;
		} else if (Y.parent.left == Y) {
			Y.parent.left = Y.left;
		} else {
			Y.parent.right = Y.left;
		}
	}

	/**
	 * Performs a tri-node restructuring (a single or double rotation rooted at X
	 * node). Assumes the nodes are in one of following configurations:
	 *
	 * @param X - root of the rotation
	 * 
	 *          <pre>
	 *          z=c       z=c        z=a         z=a
	 *         /  \      /  \       /  \        /  \
	 *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
	 *      /  \      /  \           /  \         /  \
	 *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
	 *   /  \          /  \       /  \             /  \
	 *  t1  t2        t2  t3     t2  t3           t3  t4
	 *          </pre>
	 * 
	 * @return the new root of the restructured subtree
	 */
	protected BSTNode<Entry<K, V>> restructure(BSTNode<Entry<K, V>> x) {
		// the modification of a tree T caused by a trinode restructuring operation
		// can be implemented through case analysis either as a single rotation or as a
		// double rotation.
		// The double rotation arises when position x has the middle of the three
		// relevant keys
		// and is first rotated above its parent Y, and then above what was originally
		// its grandparent Z.
		// In any of the cases, the trinode restructuring is completed with O(1)running
		// time							
		
		/*
		 * a) y is left child of z and x is left child of y (Left Left Case) return y
		 * b) y is left child of z and x is right child of y (Left Right Case) return x
		 * c) y is right child of z and x is right child of y (Right Right Case) return y
		 * d) y is right child of z and x is left child of y (Right Left Case) return x
		 */

		BSTNode<Entry<K, V>> y = x.parent; // PARENT
		BSTNode<Entry<K, V>> z = y.parent; // GRANDPARENT

		BSTNode<Entry<K, V>> newRoot = x;

		String bitSequence = generateBits(x, y, z);

		/*
		 * bitSequence is a String representing a two bit number bit 1 represents parent
		 * relationship between y and z bit 0 represents parent relationship between x
		 * and y when the former is the latter's right child, bit is set to 1 when the
		 * former is the latter's left child, bit is set to 0
		 */

		switch (bitSequence) {
		case LEFTLEFT:
			rotateRight(z);
			newRoot = y;
			break;
		case LEFTRIGHT:
			rotateLeft(y);
			rotateRight(z);
			// newRoot was initialized to x to save operation
			break;
		case RIGHTLEFT:
			rotateRight(y);
			rotateLeft(z);
			// newRoot was initialized to x to save operation
			break;
		case RIGHTRIGHT:
			rotateLeft(z);
			newRoot = y;
			break;
		}

		return newRoot;
	}

	/**
	 * Generates a String representing a two bit number based on parent
	 * relationships between the nodes
	 * 
	 * @param x base of restructure
	 * @param y {@link BSTNode x}'s {@link BSTNode parent}
	 * @param z {@link BSTNode y}'s {@link BSTNode parent}
	 * @return {@link String bit sequence}
	 */
	private String generateBits(BSTNode<Entry<K, V>> x, BSTNode<Entry<K, V>> y, BSTNode<Entry<K, V>> z) {
		char[] sequence = new char[2];
		sequence[0] = y.equals(z.getRight()) ? '1' : '0'; // BIT 1
		sequence[1] = x.equals(y.getRight()) ? '1' : '0'; // BIT 0
		String res = new String(sequence);
		return res;
	}
}
