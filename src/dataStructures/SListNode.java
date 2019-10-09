/**
 * 
 */
package dataStructures;

class SListNode<E>{
	// Element stored in the node.
			protected E element;
			// (Pointer to) the next node.
			protected SListNode<E> next;
			
			public SListNode( E elem, SListNode<E> theNext ){
				element = elem;
				next = theNext;
			}
			
			public SListNode( E theElement ){
				this(theElement, null);
			}
			
			public E getElement( ){
				return element;
			}

			public SListNode<E> getNext( ){
				return next;
			}
			
			public void setElement( E newElement ){
				element = newElement;
			}

			public void setNext( SListNode<E> newNext ){
				next = newNext;
			}

}
