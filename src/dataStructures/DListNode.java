package dataStructures;

class DListNode<E>{
		// Element stored in the node.
		protected E element;
		// (Pointer to) the next node.
		protected DListNode<E> next;
		// (Pointer to) the previous node.
		protected DListNode<E> previous;
		
		public DListNode( E elem, DListNode<E> thePrev, DListNode<E> theNext ){
			element = elem;
			previous = thePrev;
			next = theNext;
		}
		public DListNode( E theElement ){
			this(theElement, null, null);
		}
		
		public E getElement( ){
			return element;
		}

		public DListNode<E> getNext( ){
			return next;
		}
		
		public DListNode<E> getPrevious( ){
			return previous;
		}
		
		public void setElement( E newElement ){
			element = newElement;
		}

		public void setNext( DListNode<E> newNext ){
			next = newNext;
		}
		
		public void setPrevious( DListNode<E> newPrevious ){
			previous = newPrevious;
		}

}
