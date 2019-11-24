package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructures.BST;
import dataStructures.Entry;
import dataStructures.Iterator;

class BSTTest {

	protected BST<Integer, Integer> bst;
	
	@BeforeEach
	void setUp() throws Exception {
		bst = new BST<Integer, Integer>();
	}

	@Test
	void testIterator() {
		assertThrows(NoElementException.class, () -> bst.iterator());
		testInsert();
		Iterator<Entry<Integer, Integer>> it = bst.iterator();
		int i = 0;
		while (it.hasNext()) {
			Entry<Integer, Integer> next = it.next();
			assertEquals(i++, next.getKey());
			assertEquals(i++, next.getValue());
		}
	}

	@Test
	void testKeys() {
		assertThrows(NoElementException.class, () -> bst.keys());
		testInsert();
		Iterator<Integer> it = bst.keys();
		int i = 0;
		while (it.hasNext()) {
			int next = it.next();
			assertEquals(i++, next);
		}
	}

	@Test
	void testValues() {
		assertThrows(NoElementException.class, () -> bst.values());
		testInsert();
		Iterator<Integer> it = bst.values();
		int i = 0;
		while (it.hasNext()) {
			int next = it.next();
			assertEquals(i++, next);
		}
	}

	@Test
	void testGet() {
		testInsert();
		bst = new BST<Integer, Integer>();
		testRemove();
	}

	@Test
	void testInsert() {
		assertTrue(bst.isEmpty());
		for (int i = 0; i < 10; i++) {
			assertEquals(i, bst.size());
			bst.insert(i, i);
		}
		for (int i = 19, j = 10; i >= 10; i--) {
			assertEquals(j++, bst.size());
			bst.insert(i, i);
		}
		for (int i = 0; i < 20; i++) {
			assertEquals(i, bst.get(i));
		}
		assertFalse(bst.isEmpty());
	}

	@Test
	void testRemove() {
		testInsert();
		for (int i = 9; i >= 0; i--) {
			bst.remove(i);
			assertEquals(null, bst.get(i));
			assertEquals(i, bst.size() - 10);
		}
		for (int i = 10; i < 20; i++) {
			bst.remove(i);
			assertEquals(null, bst.get(i));
			assertEquals(19 - i, bst.size());
		}
		assertTrue(bst.isEmpty());
	}

	@Test
	void testMinEntry() {
		testInsert();
		Entry<Integer, Integer> min = bst.minEntry();
		assertEquals(0, min.getKey());
		assertEquals(0, min.getValue());
		bst.insert(-1, 30);
		bst.insert(40, -1);
		min = bst.minEntry();
		assertEquals(40, min.getKey());
		assertEquals(-1, min.getValue());
	}

	@Test
	void testMaxEntry() {
		testInsert();
		Entry<Integer, Integer> max = bst.maxEntry();
		assertEquals(19, max.getKey());
		assertEquals(19, max.getValue());
		bst.insert(-1, 30);
		bst.insert(40, -1);
		max = bst.maxEntry();
		assertEquals(-1, max.getKey());
		assertEquals(30, max.getValue());
	}

	@Test
	void testIsEmpty() {
		assertTrue(bst.isEmpty());
		bst.insert(0, 0);
		assertFalse(bst.isEmpty());
		bst.remove(0);
		assertTrue(bst.isEmpty());
	}

	@Test
	void testSize() {
		for (int i = 0; i < 10; i++) {
			assertEquals(i, bst.size());
			bst.insert(i, i);
		}
		for (int i = 9; i >= 0; i--) {
			bst.remove(i);
			assertEquals(i, bst.size());
		}
	}

}
