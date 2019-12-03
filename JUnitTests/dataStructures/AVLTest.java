package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.AVL.AVLNode;

class AVLTest extends AbstractAdvancedBSTTest {

	protected AVL<Integer, Integer> avl;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		bst = newTree();
		advancedBST = (AdvancedBST<Integer, Integer>) newTree();
		avl = (AVL<Integer, Integer>) newTree();
		nodes = (AVLNode<Entry<Integer, Integer>>[][]) new AVLNode<?>[4][7];
		for (int i = 0; i < 4; i++) {
			setNodeTree(i);
		}
		initNodes(nodes);
		for (int i = 0; i < 4; i++) {
			for (int j = 6; j >= 0; j--) {
				((AVLNode<?>) nodes[i][j]).setHeight();
			}
		}
	}

	@Test
	void testInsert() {
		super.testInsert();
		assertTrue(((AVLNode<Entry<Integer, Integer>>) bst.root).isBalance());
		for (int i = 0; i < 10; i++) {
			avl.insert(i, i);
			assertTrue(((AVLNode<Entry<Integer, Integer>>) avl.root).isBalance());
		}
		for (int i = 19; i >= 10; i--) {
			avl.insert(i, i);
			assertTrue(((AVLNode<Entry<Integer, Integer>>) avl.root).isBalance());
		}
	}
	
	@Override
	protected BST<Integer, Integer> newTree() {
		return new AVL<Integer, Integer>();
	}

	@Test
	void testRemove() {
		super.testRemove();
		testInsert();
		for (int i = 9; i >= 0; i--) {
			avl.remove(i);
			assertTrue(((AVLNode<Entry<Integer, Integer>>) avl.root).isBalance());
		}
		for (int i = 10; i < 20; i++) {
			avl.remove(i);
			if (avl.root != null) {
				assertTrue(((AVLNode<Entry<Integer, Integer>>) avl.root).isBalance());
			}
		}
	}

	@Test
	void testTallerChild() {
		for (int i = 0; i < 4; i++) {
			assertEquals(nodes[i][y], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i][z]));
			assertEquals(nodes[i][x], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i][y]));
		}
		int i = 0;
		assertEquals(nodes[i][t2], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x]));
		assertEquals(nodes[i][t3], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x]));
		assertEquals(nodes[i][t3], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x]));
		assertEquals(nodes[i][t4], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x]));
	}

	@Test
	void testRebalance() {
		for (int i = 0; i < nodes.length; i++) {
			avl = new AVL<Integer, Integer>((AVLNode<Entry<Integer, Integer>>) nodes[i][z]);
			if (i == 2 || i == 3) {
				avl.rebalance((AVLNode<Entry<Integer, Integer>>) nodes[i][t1]);
			} else {
				avl.rebalance((AVLNode<Entry<Integer, Integer>>) nodes[i][t4]);
			}
			if (i == 0 || i == 3) {
				assertEquals(nodes[i][y], avl.root);
			} else {
				assertEquals(nodes[i][x], avl.root);
			}
			for (int j = 0; j < nodes.length; j++) {
				assertTrue(((AVLNode<Entry<Integer, Integer>>) nodes[i][j]).isBalance());
			}
		}
	}
}
