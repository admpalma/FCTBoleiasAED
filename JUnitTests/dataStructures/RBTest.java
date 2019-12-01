package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.RB.RBNode;

class RBTest extends AbstractAdvancedBSTTest {
	
	private static final boolean BLACK = false;
	private static final boolean RED = true;
	
	protected RB<Integer, Integer> rb;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		bst = newTree();
		advancedBST = (AdvancedBST<Integer, Integer>) newTree();
		rb = (RB<Integer, Integer>) newTree();
		nodes = (RBNode<Entry<Integer, Integer>>[][]) new RBNode<?>[4][7];
		for (int i = 0; i < 4; i++) {
			setNodeTree(i);
		}
		initNodes(nodes);
		for (int i = 0; i < 4; i++) {
			colourNodes(i);
		}
	}
	
	@Override
	protected BST<Integer, Integer> newTree() {
		return new RB<Integer, Integer>();
	}

	private void colourNodes(int i) {
		switch (i) {
		case 0:
			((RBNode<?>) nodes[i][t1]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t2]).setColour(RED);
			((RBNode<?>) nodes[i][y]).setColour(RED);
			((RBNode<?>) nodes[i][t3]).setColour(BLACK);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t4]).setColour(BLACK);
			break;
		case 1:
			((RBNode<?>) nodes[i][t1]).setColour(BLACK);
			((RBNode<?>) nodes[i][y]).setColour(RED);
			((RBNode<?>) nodes[i][t2]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t3]).setColour(RED);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t4]).setColour(BLACK);
			break;
		case 2:
			((RBNode<?>) nodes[i][t1]).setColour(BLACK);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t2]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t3]).setColour(RED);
			((RBNode<?>) nodes[i][y]).setColour(RED);
			((RBNode<?>) nodes[i][t4]).setColour(BLACK);
			break;
		case 3:
			((RBNode<?>) nodes[i][t1]).setColour(BLACK);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t2]).setColour(RED);;
			((RBNode<?>) nodes[i][y]).setColour(BLACK);
			((RBNode<?>) nodes[i][t3]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t4]).setColour(RED);
			break;
		default:
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void verifyRBProperties() {
		// Root is always black
		assertTrue(!((RBNode<Entry<Integer, Integer>>) rb.root).isRed());
		BSTOrderIterator<Integer, Integer> it = (BSTOrderIterator<Integer, Integer>) rb.iterator();
		List<Integer> blackNodesInEachPath = new SinglyLinkedList<Integer>();
		while (it.hasNext()) {
			RBNode<?> stackTop = (RBNode<?>) it.stack.top();
			if (stackTop.isRed()) {
				// There are no two adjacent red nodes
				assertTrue(!stackTop.hasAdjacentRed());
			}
			if (stackTop.left == null || stackTop.right == null) {
				int numberOfBlackNodes = 1; // Root is always black
				while (stackTop.parent != null) {
					if (!stackTop.isRed()) {
						numberOfBlackNodes++;
					}
					stackTop = (RBNode<?>) stackTop.parent;
				}
				blackNodesInEachPath.addLast(numberOfBlackNodes);
			}
			it.next();
		}
		Iterator<Integer> blackNodes = blackNodesInEachPath.iterator();
		int previous = -1;
		if (blackNodes.hasNext()) {
			previous = blackNodes.next();
		}
		while (blackNodes.hasNext()) {
			// Every path from a node (including root) to any of its descendant NULL node has the same number of black nodes.
			assertEquals(previous, blackNodes.next());
		}
	}

	@Test
	void testInsert() {
		super.testInsert();
		assertTrue(!((RBNode<Entry<Integer, Integer>>) bst.root).isRed());
		for (int i = 0; i < 10; i++) {
			rb.insert(i, i);
			verifyRBProperties();
		}
		for (int i = 19; i >= 10; i--) {
			rb.insert(i, i);
			verifyRBProperties();
		}
	}

	@Test
	void testRemove() {
		super.testRemove();
		for (int i = 9; i >= 0; i--) {
			rb.remove(i);
			verifyRBProperties();
		}
		for (int i = 10; i < 20; i++) {
			rb.remove(i);
			if (rb.root != null) {
				verifyRBProperties();
			}
		}
	}

	@Test
	void testRemedyDoubleRed() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testRemedyDoubleBlack() {
		fail("Not yet implemented"); // TODO
	}

}
