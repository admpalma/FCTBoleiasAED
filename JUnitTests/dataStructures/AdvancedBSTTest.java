package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.BST.BSTNode;

class AdvancedBSTTest extends BSTTest {

	/**
	 * Where z = 0, y = 1, x = 2
	 * (j indexes)
	 */
	protected BSTNode<Entry<Integer, Integer>>[][] nodes;

	protected AdvancedBST<Integer, Integer> advancedBST;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		advancedBST = new AdvancedBST<Integer, Integer>();
		bst = advancedBST;
		nodes = (BSTNode<Entry<Integer, Integer>>[][]) new BSTNode<?>[4][7];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				nodes[i][j] = new BSTNode<Entry<Integer, Integer>>(new EntryClass<Integer, Integer>(i, j));
			}
		}
		int i = 0;
		/** Set first tree in {@link AdvancedBST#restructure()} */
		setRightChildOf(nodes[i][0], nodes[i][6]);
		setRightChildOf(nodes[i][1], nodes[i][5]);
		setRightChildOf(nodes[i][2], nodes[i][4]);
		setLeftChildOf(nodes[i][0], nodes[i][1]);
		setLeftChildOf(nodes[i][1], nodes[i][2]);
		setLeftChildOf(nodes[i][2], nodes[i++][3]);
		/** Set second tree in {@link AdvancedBST#restructure()} */
		setRightChildOf(nodes[i][0], nodes[i][6]);
		setRightChildOf(nodes[i][1], nodes[i][2]);
		setRightChildOf(nodes[i][2], nodes[i][5]);
		setLeftChildOf(nodes[i][0], nodes[i][1]);
		setLeftChildOf(nodes[i][1], nodes[i][3]);
		setLeftChildOf(nodes[i][2], nodes[i++][4]);
		/** Set third tree in {@link AdvancedBST#restructure()} */
		setLeftChildOf(nodes[i][0], nodes[i][1]);
		setLeftChildOf(nodes[i][1], nodes[i][6]);
		setLeftChildOf(nodes[i][2], nodes[i][5]);
		setRightChildOf(nodes[i][0], nodes[i][3]);
		setRightChildOf(nodes[i][1], nodes[i][2]);
		setRightChildOf(nodes[i][2], nodes[i++][4]);
		/** Set fourth tree in {@link AdvancedBST#restructure()} */
		setLeftChildOf(nodes[i][0], nodes[i][3]);
		setLeftChildOf(nodes[i][1], nodes[i][4]);
		setLeftChildOf(nodes[i][2], nodes[i][5]);
		setRightChildOf(nodes[i][0], nodes[i][1]);
		setRightChildOf(nodes[i][1], nodes[i][2]);
		setRightChildOf(nodes[i][2], nodes[i][6]);
	}

	private void setLeftChildOf(BSTNode<Entry<Integer, Integer>> parent, BSTNode<Entry<Integer, Integer>> leftChild) {
		parent.left = leftChild;
		leftChild.parent = parent;
	}

	private void setRightChildOf(BSTNode<Entry<Integer, Integer>> parent, BSTNode<Entry<Integer, Integer>> rightChild) {
		parent.right = rightChild;
		rightChild.parent = parent;
	}

	@Test
	void testRotateLeft() {
		int i = 1;
		/** Rotate second tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateLeft(nodes[i][0]);
		assertEquals(nodes[i][0], nodes[i][1].right);
		assertEquals(nodes[i][2], nodes[i][1].left);
		assertEquals(nodes[i][4], nodes[i][0].right);
		assertEquals(nodes[i][3], nodes[i][0].left);
		assertEquals(nodes[i][6], nodes[i][2].right);
		assertEquals(nodes[i][5], nodes[i][2].left);
		assertEquals(null, nodes[i][1].parent);	//Incomplete testing
		assertEquals(nodes[i][1], nodes[i][0].parent);
		assertEquals(nodes[i][1], nodes[i][2].parent);
		assertEquals(nodes[i][0], nodes[i][3].parent);
		assertEquals(nodes[i][0], nodes[i][4].parent);
		assertEquals(nodes[i][2], nodes[i][5].parent);
		assertEquals(nodes[i][2], nodes[i][6].parent);
		i++;
		/** Rotate third tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateLeft(nodes[i][1]);
		assertEquals(nodes[i][6], nodes[i][0].right);
		assertEquals(nodes[i][2], nodes[i][0].left);
		assertEquals(nodes[i][4], nodes[i][1].right);
		assertEquals(nodes[i][3], nodes[i][1].left);
		assertEquals(nodes[i][5], nodes[i][2].right);
		assertEquals(nodes[i][1], nodes[i][2].left);
		assertEquals(null, nodes[i][0].parent);	//Incomplete testing
		assertEquals(nodes[i][2], nodes[i][1].parent);
		assertEquals(nodes[i][0], nodes[i][2].parent);
		assertEquals(nodes[i][1], nodes[i][3].parent);
		assertEquals(nodes[i][1], nodes[i][4].parent);
		assertEquals(nodes[i][2], nodes[i][5].parent);
		assertEquals(nodes[i][0], nodes[i][6].parent);
	}

	@Test
	void testRotateRight() {
		int i = 0;
		/** Rotate first tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateRight(nodes[i][0]);
		assertEquals(nodes[i][0], nodes[i][1].right);
		assertEquals(nodes[i][2], nodes[i][1].left);
		assertEquals(nodes[i][6], nodes[i][0].right);
		assertEquals(nodes[i][5], nodes[i][0].left);
		assertEquals(nodes[i][4], nodes[i][2].right);
		assertEquals(nodes[i][3], nodes[i][2].left);
		assertEquals(null, nodes[i][1].parent);	//Incomplete testing
		assertEquals(nodes[i][1], nodes[i][0].parent);
		assertEquals(nodes[i][1], nodes[i][2].parent);
		assertEquals(nodes[i][2], nodes[i][3].parent);
		assertEquals(nodes[i][2], nodes[i][4].parent);
		assertEquals(nodes[i][0], nodes[i][5].parent);
		assertEquals(nodes[i][0], nodes[i][6].parent);
		i += 3;
		/** Rotate fourth tree in {@link AdvancedBST#restructure()} */
		advancedBST.rotateRight(nodes[i][1]);
		assertEquals(nodes[i][2], nodes[i][0].right);
		assertEquals(nodes[i][3], nodes[i][0].left);
		assertEquals(nodes[i][6], nodes[i][1].right);
		assertEquals(nodes[i][5], nodes[i][1].left);
		assertEquals(nodes[i][1], nodes[i][2].right);
		assertEquals(nodes[i][4], nodes[i][2].left);
		assertEquals(null, nodes[i][0].parent);	//Incomplete testing
		assertEquals(nodes[i][2], nodes[i][1].parent);
		assertEquals(nodes[i][0], nodes[i][2].parent);
		assertEquals(nodes[i][0], nodes[i][3].parent);
		assertEquals(nodes[i][2], nodes[i][4].parent);
		assertEquals(nodes[i][1], nodes[i][5].parent);
		assertEquals(nodes[i][1], nodes[i][6].parent);
	}

	@Test
	void testRestructure() {
		testRotateRight();
		testRotateLeft();
		int i = 2;
		/** Rotate third tree in {@link AdvancedBST#restructure()} again */
		advancedBST.rotateRight(nodes[i][0]);
		assertEquals(nodes[i][0], nodes[i][2].right);
		assertEquals(nodes[i][1], nodes[i][2].left);
		assertEquals(nodes[i][4], nodes[i][0].right);
		assertEquals(nodes[i][5], nodes[i][0].left);
		assertEquals(nodes[i][6], nodes[i][1].right);
		assertEquals(nodes[i][3], nodes[i][1].left);
		assertEquals(null, nodes[i][2].parent);	//Incomplete testing
		assertEquals(nodes[i][2], nodes[i][0].parent);
		assertEquals(nodes[i][2], nodes[i][1].parent);
		assertEquals(nodes[i][1], nodes[i][3].parent);
		assertEquals(nodes[i][1], nodes[i][6].parent);
		assertEquals(nodes[i][0], nodes[i][5].parent);
		assertEquals(nodes[i][0], nodes[i][4].parent);
		i++;
		/** Rotate fourth tree in {@link AdvancedBST#restructure()} again */
		advancedBST.rotateLeft(nodes[i][0]);
		assertEquals(nodes[i][0], nodes[i][2].right);
		assertEquals(nodes[i][1], nodes[i][2].left);
		assertEquals(nodes[i][6], nodes[i][0].right);
		assertEquals(nodes[i][3], nodes[i][0].left);
		assertEquals(nodes[i][4], nodes[i][1].right);
		assertEquals(nodes[i][5], nodes[i][1].left);
		assertEquals(null, nodes[i][2].parent);	//Incomplete testing
		assertEquals(nodes[i][2], nodes[i][0].parent);
		assertEquals(nodes[i][2], nodes[i][1].parent);
		assertEquals(nodes[i][0], nodes[i][3].parent);
		assertEquals(nodes[i][0], nodes[i][6].parent);
		assertEquals(nodes[i][1], nodes[i][5].parent);
		assertEquals(nodes[i][1], nodes[i][4].parent);
	}

}
