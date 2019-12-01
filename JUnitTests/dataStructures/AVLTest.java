package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.AVL.AVLNode;

class AVLTest extends AdvancedBSTTest {

	protected AVL<Integer, Integer> avl;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		bst = new AVL<Integer, Integer>();
		advancedBST = new AVL<Integer, Integer>();
		avl = new AVL<Integer, Integer>();
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
			assertTrue(((AVLNode<Entry<Integer, Integer>>) avl.root).isBalance());
		}
	}

	@Test
	void testTallerChild() {
		for (int i = 0; i < 4; i++) {
			assertEquals(nodes[i][y], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i][z]));
			assertEquals(nodes[i][x], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i][y]));
		}
		//int i = 0;
		//assertEquals(nodes[i][t1], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x])); //TODO t1 ou t2?
		//assertEquals(nodes[i][t2], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x])); //TODO t2 ou t3?
		//assertEquals(nodes[i][t2], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x])); //TODO t2 ou t3?
		//assertEquals(nodes[i][t3], avl.tallerChild((AVLNode<Entry<Integer, Integer>>) nodes[i++][x])); //TODO t3 ou t4?
	}

	@Test
	void testRebalance() {
		fail("Not yet implemented");
	}

}
