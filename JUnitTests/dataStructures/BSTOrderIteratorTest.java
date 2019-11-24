package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.BST;
import dataStructures.BSTOrderIterator;

class BSTOrderIteratorTest {

	private BST<Integer, Integer> bst;
	private BSTOrderIterator<Integer, Integer> bstIt;
	private BSTOrderIterator<Integer, Integer> bstItFail;
	
	@BeforeEach
	void setUp() throws Exception {
		bst = new BST<Integer, Integer>();
		for (int i = 0; i < 20; i++) {
			bst.insert(i, i);
		}
		bstIt = new BSTOrderIterator<Integer, Integer>(bst.root);
		bstItFail = new BSTOrderIterator<Integer, Integer>(null);
	}

	@Test
	void testBSTOrderIterator() {
		assertFalse(bstItFail.hasNext());
		assertThrows(NoSuchElementException.class, () -> bstItFail.next());
		assertTrue(bstIt.hasNext());
		int i = 0;
		while (bstIt.hasNext()) {
			assertEquals(i++, bstIt.next().getKey());
		}
		assertThrows(NoSuchElementException.class, () -> bstIt.next());
	}

	@Test
	void testHasNext() {
		assertFalse(bstItFail.hasNext());
		for (int i = 0; i < 20; i++) {
			assertTrue(bstIt.hasNext());
			bstIt.next();
		}
		assertThrows(NoSuchElementException.class, () -> bstIt.next());
	}

	@Test
	void testNext() {
		assertThrows(NoSuchElementException.class, () -> bstItFail.next());
		int i = 0;
		while (bstIt.hasNext()) {
			assertEquals(i++, bstIt.next().getKey());
		}
		assertThrows(NoSuchElementException.class, () -> bstIt.next());
	}

	@Test
	void testRewind() {
		bstIt.rewind();
		testHasNext();
		testNext();
	}

}
