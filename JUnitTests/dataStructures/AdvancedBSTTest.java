package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.BST.BSTNode;

class AdvancedBSTTest extends BSTTest {

	protected static final int x  = 2;
	protected static final int y  = 1;
	protected static final int z  = 0;
	protected static final int t1 = 3;
	protected static final int t2 = 4;
	protected static final int t3 = 5;
	protected static final int t4 = 6;

	/**
	 * Where z = 0, y = 1, x = 2
	 * (j indexes)
	 */
	protected BSTNode<Entry<Integer, Integer>>[][] nodes;

	protected AdvancedBST<Integer, Integer> advancedBST;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		bst = new AdvancedBST<Integer, Integer>();
		advancedBST = new AdvancedBST<Integer, Integer>();
		nodes = (BSTNode<Entry<Integer, Integer>>[][]) new BSTNode<?>[4][7];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				nodes[i][j] = new BSTNode<Entry<Integer, Integer>>(new EntryClass<Integer, Integer>(i, j));
			}
		}
		initNodes(nodes);
	}

	protected void initNodes(BSTNode<Entry<Integer, Integer>>[][] nodes) {
		int i = 0;
		/** Set first tree in {@link AdvancedBST#restructure()} */
		setRightChildOf(nodes[i][z], nodes[i][t4]);
		setRightChildOf(nodes[i][y], nodes[i][t3]);
		setRightChildOf(nodes[i][x], nodes[i][t2]);
		setLeftChildOf(nodes[i][z], nodes[i][y]);
		setLeftChildOf(nodes[i][y], nodes[i][x]);
		setLeftChildOf(nodes[i][x], nodes[i++][t1]);
		/** Set second tree in {@link AdvancedBST#restructure()} */
		setRightChildOf(nodes[i][z], nodes[i][t4]);
		setRightChildOf(nodes[i][y], nodes[i][x]);
		setRightChildOf(nodes[i][x], nodes[i][t3]);
		setLeftChildOf(nodes[i][z], nodes[i][y]);
		setLeftChildOf(nodes[i][y], nodes[i][t1]);
		setLeftChildOf(nodes[i][x], nodes[i++][t2]);
		/** Set third tree in {@link AdvancedBST#restructure()} */
		setRightChildOf(nodes[i][z], nodes[i][y]);
		setRightChildOf(nodes[i][y], nodes[i][t4]);
		setRightChildOf(nodes[i][x], nodes[i][t3]);
		setLeftChildOf(nodes[i][z], nodes[i][t1]);
		setLeftChildOf(nodes[i][y], nodes[i][x]);
		setLeftChildOf(nodes[i][x], nodes[i++][t2]);
		/** Set fourth tree in {@link AdvancedBST#restructure()} */
		setRightChildOf(nodes[i][z], nodes[i][y]);
		setRightChildOf(nodes[i][y], nodes[i][x]);
		setRightChildOf(nodes[i][x], nodes[i][t4]);
		setLeftChildOf(nodes[i][z], nodes[i][t1]);
		setLeftChildOf(nodes[i][y], nodes[i][t2]);
		setLeftChildOf(nodes[i][x], nodes[i][t3]);
	}

	protected void setLeftChildOf(BSTNode<Entry<Integer, Integer>> parent, BSTNode<Entry<Integer, Integer>> leftChild) {
		parent.left = leftChild;
		leftChild.parent = parent;
	}

	protected void setRightChildOf(BSTNode<Entry<Integer, Integer>> parent, BSTNode<Entry<Integer, Integer>> rightChild) {
		parent.right = rightChild;
		rightChild.parent = parent;
	}

	@Test
	void testRotateLeft() {
		int i = 1;
		/** Rotate second tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateLeft(nodes[i][y]);
		assertEquals(nodes[i][t4], nodes[i][z].right);
		assertEquals(nodes[i][x], nodes[i][z].left);
		assertEquals(nodes[i][t2], nodes[i][y].right);
		assertEquals(nodes[i][t1], nodes[i][y].left);
		assertEquals(nodes[i][t3], nodes[i][x].right);
		assertEquals(nodes[i][y], nodes[i][x].left);
		assertEquals(null, nodes[i][z].parent);	//Incomplete testing
		assertEquals(nodes[i][z], nodes[i][x].parent);
		assertEquals(nodes[i][x], nodes[i][y].parent);
		assertEquals(nodes[i][y], nodes[i][t1].parent);
		assertEquals(nodes[i][y], nodes[i][t2].parent);
		assertEquals(nodes[i][x], nodes[i][t3].parent);
		assertEquals(nodes[i][z], nodes[i][t4].parent);
		i = 3;
		/** Rotate fourth tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateLeft(nodes[i][z]);
		assertEquals(nodes[i][t2], nodes[i][z].right);
		assertEquals(nodes[i][t1], nodes[i][z].left);
		assertEquals(nodes[i][x], nodes[i][y].right);
		assertEquals(nodes[i][z], nodes[i][y].left);
		assertEquals(nodes[i][t4], nodes[i][x].right);
		assertEquals(nodes[i][t3], nodes[i][x].left);
		assertEquals(null, nodes[i][y].parent);	//Incomplete testing
		assertEquals(nodes[i][y], nodes[i][z].parent);
		assertEquals(nodes[i][y], nodes[i][x].parent);
		assertEquals(nodes[i][z], nodes[i][t1].parent);
		assertEquals(nodes[i][z], nodes[i][t2].parent);
		assertEquals(nodes[i][x], nodes[i][t3].parent);
		assertEquals(nodes[i][x], nodes[i][t4].parent);
	}

	@Test
	void testRotateRight() {
		int i = 0;
		/** Rotate first tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateRight(nodes[i][z]);
		assertEquals(nodes[i][z], nodes[i][y].right);
		assertEquals(nodes[i][x], nodes[i][y].left);
		assertEquals(nodes[i][t4], nodes[i][z].right);
		assertEquals(nodes[i][t3], nodes[i][z].left);
		assertEquals(nodes[i][t2], nodes[i][x].right);
		assertEquals(nodes[i][t1], nodes[i][x].left);
		assertEquals(null, nodes[i][y].parent);	//Incomplete testing
		assertEquals(nodes[i][y], nodes[i][z].parent);
		assertEquals(nodes[i][y], nodes[i][x].parent);
		assertEquals(nodes[i][x], nodes[i][t1].parent);
		assertEquals(nodes[i][x], nodes[i][t2].parent);
		assertEquals(nodes[i][z], nodes[i][t3].parent);
		assertEquals(nodes[i][z], nodes[i][t4].parent);
		i = 2;
		/** Rotate third tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateRight(nodes[i][y]);
		assertEquals(nodes[i][x], nodes[i][z].right);
		assertEquals(nodes[i][t1], nodes[i][z].left);
		assertEquals(nodes[i][t4], nodes[i][y].right);
		assertEquals(nodes[i][t3], nodes[i][y].left);
		assertEquals(nodes[i][y], nodes[i][x].right);
		assertEquals(nodes[i][t2], nodes[i][x].left);
		assertEquals(null, nodes[i][z].parent);	//Incomplete testing
		assertEquals(nodes[i][x], nodes[i][y].parent);
		assertEquals(nodes[i][z], nodes[i][x].parent);
		assertEquals(nodes[i][z], nodes[i][t1].parent);
		assertEquals(nodes[i][x], nodes[i][t2].parent);
		assertEquals(nodes[i][y], nodes[i][t3].parent);
		assertEquals(nodes[i][y], nodes[i][t4].parent);
	}

	@Test
	void testRestructure() {
		testRotateRight();
		testRotateLeft();
		int i = 1;
		/** Rotate second tree in {@link AdvancedBST#restructure()} again */
		advancedBST.rotateRight(nodes[i][z]);
		assertEquals(nodes[i][z], nodes[i][x].right);
		assertEquals(nodes[i][y], nodes[i][x].left);
		assertEquals(nodes[i][t4], nodes[i][z].right);
		assertEquals(nodes[i][t3], nodes[i][z].left);
		assertEquals(nodes[i][t2], nodes[i][y].right);
		assertEquals(nodes[i][t1], nodes[i][y].left);
		assertEquals(null, nodes[i][x].parent);	//Incomplete testing
		assertEquals(nodes[i][x], nodes[i][z].parent);
		assertEquals(nodes[i][x], nodes[i][y].parent);
		assertEquals(nodes[i][y], nodes[i][t1].parent);
		assertEquals(nodes[i][y], nodes[i][t2].parent);
		assertEquals(nodes[i][z], nodes[i][t3].parent);
		assertEquals(nodes[i][z], nodes[i][t4].parent);
		i = 2;
		/** Rotate third tree in {@link AdvancedBST#restructure()} again */
		advancedBST.rotateLeft(nodes[i][z]);
		assertEquals(nodes[i][y], nodes[i][x].right);
		assertEquals(nodes[i][z], nodes[i][x].left);
		assertEquals(nodes[i][t2], nodes[i][z].right);
		assertEquals(nodes[i][t1], nodes[i][z].left);
		assertEquals(nodes[i][t4], nodes[i][y].right);
		assertEquals(nodes[i][t3], nodes[i][y].left);
		assertEquals(null, nodes[i][x].parent);	//Incomplete testing
		assertEquals(nodes[i][x], nodes[i][y].parent);
		assertEquals(nodes[i][x], nodes[i][z].parent);
		assertEquals(nodes[i][z], nodes[i][t1].parent);
		assertEquals(nodes[i][z], nodes[i][t2].parent);
		assertEquals(nodes[i][y], nodes[i][t3].parent);
		assertEquals(nodes[i][y], nodes[i][t4].parent);
	}

}
