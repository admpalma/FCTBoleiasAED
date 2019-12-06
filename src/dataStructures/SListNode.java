/**
 * 
 */
package dataStructures;

import java.io.Serializable;

class SListNode<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Element stored in the node.
	protected E element;
	// (Pointer to) the next node.
	protected SListNode<E> next;

	public SListNode(E elem, SListNode<E> theNext) {
		element = elem;
		next = theNext;
	}

	public SListNode(E theElement) {
		this(theElement, null);
	}

	/**
	 * O(1)
	 * */
	public E getElement() {
		return element;
	}
	
	/**
	 * O(1)
	 * */
	public SListNode<E> getNext() {
		return next;
	}

	/**
	 * O(1)
	 * */
	public void setElement(E newElement) {
		element = newElement;
	}

	/**
	 * O(1)
	 * */
	public void setNext(SListNode<E> newNext) {
		next = newNext;
	}

}
