package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.BST.BSTNode;
import dataStructures.RB.RBNode;

class RBTest extends AbstractAdvancedBSTTest {
	
	/** Uncle*/
	protected static final int u  = 6;
	/** Inserted*/
	protected static final int x  = 2;
	/** Parent*/
	protected static final int p  = 1;
	/** Grandfather*/
	protected static final int g  = 0;
	protected static final int T1 = 3;
	protected static final int T2 = 4;
	protected static final int T3 = 5;
	protected static final int T4 = 7;
	protected static final int T5 = 8;
	
	private static final boolean BLACK = false;
	private static final boolean RED = true;
	
	protected RB<Integer, Integer> rb;
	RBNode<Entry<Integer, Integer>>[][] RBNodes;

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
		RBNodes = (RBNode<Entry<Integer, Integer>>[][]) new RBNode<?>[5][9];
		for (int i = 0; i < 5; i++) {
			setNodeTree(i, RBNodes);
			initRBNodes(i, RBNodes);
			colourNodes(i, RBNodes);
		}
	}

	protected void setNodeTree(int i, BSTNode<Entry<Integer, Integer>>[][] nodes) {
		int j = 0;
		switch (i) {
		case 0:
		case 4:
			nodes[i][T1] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][x] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T2] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][p] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T3] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][g] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T4] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][u] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T5] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			break;
		case 1:
			nodes[i][T1] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][p] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T2] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][x] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T3] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][g] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T4] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][u] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T5] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			break;
		case 2:
			nodes[i][T1] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][u] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T2] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][g] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T3] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][x] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T4] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][p] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T5] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			break;
		case 3:
			nodes[i][T1] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][u] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T2] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][g] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T3] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][p] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T4] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][x] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			nodes[i][T5] = rb.nodeOf(new EntryClass<Integer, Integer>(j++, i), null, null, null);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Modifies {@link AdvancedBST#restructure()} trees to suit {@link RB} tests
	 * @param nodes 
	 */
	protected void initRBNodes(int i, BSTNode<Entry<Integer, Integer>>[][] nodes) {
		switch (i) {
		case 0:	/** Set first tree in {@link AdvancedBST#restructure()} */
		case 4: /** Set fifth tree (copy of first in {@link AdvancedBST#restructure()}) */
			setRightChildOf(nodes[i][x], nodes[i][T2]);
			setRightChildOf(nodes[i][p], nodes[i][T3]);
			setRightChildOf(nodes[i][g], nodes[i][u]);
			setRightChildOf(nodes[i][u], nodes[i][T5]);
			setLeftChildOf(nodes[i][x], nodes[i][T1]);
			setLeftChildOf(nodes[i][p], nodes[i][x]);
			setLeftChildOf(nodes[i][g], nodes[i][p]);
			setLeftChildOf(nodes[i][u], nodes[i][T4]);
			break;
		case 1:
			/** Set second tree in {@link AdvancedBST#restructure()} */
			setRightChildOf(nodes[i][x], nodes[i][T3]);
			setRightChildOf(nodes[i][p], nodes[i][x]);
			setRightChildOf(nodes[i][g], nodes[i][u]);
			setRightChildOf(nodes[i][u], nodes[i][T5]);
			setLeftChildOf(nodes[i][x], nodes[i][T2]);
			setLeftChildOf(nodes[i][p], nodes[i][T1]);
			setLeftChildOf(nodes[i][g], nodes[i][p]);
			setLeftChildOf(nodes[i][u], nodes[i][T4]);
			break;
		case 2: /** Set third tree in {@link AdvancedBST#restructure()} */
			setRightChildOf(nodes[i][u], nodes[i][T2]);
			setRightChildOf(nodes[i][g], nodes[i][p]);
			setRightChildOf(nodes[i][p], nodes[i][T5]);
			setRightChildOf(nodes[i][x], nodes[i][T4]);
			setLeftChildOf(nodes[i][u], nodes[i][T1]);
			setLeftChildOf(nodes[i][g], nodes[i][u]);
			setLeftChildOf(nodes[i][p], nodes[i][x]);
			setLeftChildOf(nodes[i][x], nodes[i][T3]);
			break;
		case 3: /** Set fourth tree in {@link AdvancedBST#restructure()} */
			setRightChildOf(nodes[i][u], nodes[i][T2]);
			setRightChildOf(nodes[i][g], nodes[i][p]);
			setRightChildOf(nodes[i][p], nodes[i][x]);
			setRightChildOf(nodes[i][x], nodes[i][T5]);
			setLeftChildOf(nodes[i][u], nodes[i][T1]);
			setLeftChildOf(nodes[i][g], nodes[i][u]);
			setLeftChildOf(nodes[i][p], nodes[i][T3]);
			setLeftChildOf(nodes[i][x], nodes[i][T4]);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected BST<Integer, Integer> newTree() {
		return new RB<Integer, Integer>();
	}

	private void colourNodes(int i, BSTNode<Entry<Integer, Integer>>[][] nodes) {
		switch (i) {
		case 0:
			/*((RBNode<?>) nodes[i][t1]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t2]).setColour(RED);
			((RBNode<?>) nodes[i][y]).setColour(RED);
			((RBNode<?>) nodes[i][t3]).setColour(BLACK);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t4]).setColour(BLACK);*/
		case 1:
			/*((RBNode<?>) nodes[i][t1]).setColour(BLACK);
			((RBNode<?>) nodes[i][y]).setColour(RED);
			((RBNode<?>) nodes[i][t2]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t3]).setColour(RED);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t4]).setColour(BLACK);*/
		case 2:
			/*((RBNode<?>) nodes[i][t1]).setColour(BLACK);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t2]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t3]).setColour(RED);
			((RBNode<?>) nodes[i][y]).setColour(RED);
			((RBNode<?>) nodes[i][t4]).setColour(BLACK);*/
		case 3:
			((RBNode<?>) nodes[i][x]).setColour(RED);
			((RBNode<?>) nodes[i][p]).setColour(RED);
			((RBNode<?>) nodes[i][g]).setColour(BLACK);
			((RBNode<?>) nodes[i][u]).setColour(BLACK);
			/*((RBNode<?>) nodes[i][t1]).setColour(BLACK);
			((RBNode<?>) nodes[i][z]).setColour(BLACK);
			((RBNode<?>) nodes[i][t2]).setColour(RED);;
			((RBNode<?>) nodes[i][y]).setColour(BLACK);
			((RBNode<?>) nodes[i][t3]).setColour(RED);
			((RBNode<?>) nodes[i][x]).setColour(BLACK);
			((RBNode<?>) nodes[i][t4]).setColour(RED);*/
			break;
		case 4:
			((RBNode<?>) nodes[i][x]).setColour(RED);
			((RBNode<?>) nodes[i][p]).setColour(RED);
			((RBNode<?>) nodes[i][g]).setColour(BLACK);
			((RBNode<?>) nodes[i][u]).setColour(RED);
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
		List<Integer> blackHeightInEachPath = new SinglyLinkedList<Integer>();
		while (it.hasNext()) {
			RBNode<?> stackTop = (RBNode<?>) it.stack.top();
			if (stackTop.isRed()) {
				// There are no two adjacent red nodes
				assertTrue(!stackTop.hasAdjacentRed());
			}
			if (stackTop.left == null || stackTop.right == null) {
				int blackHeight = 1; // Root is always black
				while (stackTop.parent != null) {
					if (!stackTop.isRed()) {
						blackHeight++;
					}
					stackTop = (RBNode<?>) stackTop.parent;
				}
				blackHeightInEachPath.addLast(blackHeight);
			}
			it.next();
		}
		Iterator<Integer> blackHeights = blackHeightInEachPath.iterator();
		int previous = -1;
		if (blackHeights.hasNext()) {
			previous = blackHeights.next();
		}
		while (blackHeights.hasNext()) {
			// Every path from a node (including root) to any of its descendant NULL node has the same number of black nodes.
			assertEquals(previous, blackHeights.next());
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

	@SuppressWarnings("unchecked")
	@Test
	void testRemedyDoubleRed() {
		RBNode<Entry<Integer, Integer>>[][] clone = (RBNode<Entry<Integer, Integer>>[][])new RBNode<?>[5][9];
		RBNode<Entry<Integer, Integer>>[][] fifthCaseExtention = (RBNode<Entry<Integer, Integer>>[][])new RBNode<?>[5][9];
		for (int i = 0; i < 5; i++) {
			setNodeTree(i, clone);
			initRBNodes(i, clone);
			colourNodes(i, clone);
		}
		for (int i = 0; i < nodes.length; i++) {
			if (i != 4) {
				rb = new RB<Integer, Integer>((RBNode<Entry<Integer, Integer>>) RBNodes[i][g]);
			} else {
				for (int j = 0; j < 5; j++) {
					setNodeTree(j, clone);
					initRBNodes(j, clone);
					colourNodes(j, clone);
				}
				fifthCaseExtention[4][x] = (RBNode<Entry<Integer, Integer>>) RBNodes[4][g];
				rb = new RB<Integer, Integer>(fifthCaseExtention[4][x]);
			}
			rb.restructure(clone[i][x]);
			// TODO x ou g consoante o contrato
			rb.remedyDoubleRed((RBNode<Entry<Integer, Integer>>) RBNodes[i][x]);
			switch (i) {
			case 0:
			case 3:
				assertTrue(((RBNode<Entry<Integer, Integer>>) RBNodes[i][x]).isRed());
				assertFalse(((RBNode<Entry<Integer, Integer>>) RBNodes[i][p]).isRed());
				assertTrue(((RBNode<Entry<Integer, Integer>>) RBNodes[i][g]).isRed());
				assertFalse(((RBNode<Entry<Integer, Integer>>) RBNodes[i][u]).isRed());
				break;
			case 1:
			case 2:
				assertFalse(((RBNode<Entry<Integer, Integer>>) RBNodes[i][x]).isRed());
				assertTrue(((RBNode<Entry<Integer, Integer>>) RBNodes[i][p]).isRed());
				assertTrue(((RBNode<Entry<Integer, Integer>>) RBNodes[i][g]).isRed());
				assertFalse(((RBNode<Entry<Integer, Integer>>) RBNodes[i][u]).isRed());
				break;
			case 4:
				assertTrue(((RBNode<Entry<Integer, Integer>>) RBNodes[i][x]).isRed());
				assertFalse(((RBNode<Entry<Integer, Integer>>) RBNodes[i][p]).isRed());
				assertTrue(((RBNode<Entry<Integer, Integer>>) RBNodes[i][g]).isRed());
				assertFalse(((RBNode<Entry<Integer, Integer>>) RBNodes[i][u]).isRed());
				assertFalse((fifthCaseExtention[i][p]).isRed());
				assertFalse((fifthCaseExtention[i][g]).isRed());
				assertFalse((fifthCaseExtention[i][u]).isRed());
				break;
			default:
				break;
			}
			for (int j = 0; j < clone.length; j++) {
				assertEquals(clone[i][j].element.getKey(), RBNodes[i][j].element.getKey());
				if (clone[i][j].parent != null || RBNodes[i][j].parent != null) {
					assertEquals(clone[i][j].parent.element.getKey(), RBNodes[i][j].parent.element.getKey());
				}
				if (clone[i][j].right != null || RBNodes[i][j].right != null) {
					assertEquals(clone[i][j].right.element.getKey(), RBNodes[i][j].right.element.getKey());
				}
				if (clone[i][j].left != null || RBNodes[i][j].left != null) {
					assertEquals(clone[i][j].left.element.getKey(), RBNodes[i][j].left.element.getKey());
				}
			}
		}
	}

	@Test
	void testRemedyDoubleBlack() {
		// TODO fail("Not yet implemented");
	}

}
