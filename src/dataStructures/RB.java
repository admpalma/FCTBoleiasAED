package dataStructures;

public class RB<K extends Comparable<K>, V> extends AdvancedBST<K, V> implements SortedMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean BLACK = false;
	private static final boolean RED = true;

	static class RBNode<E> extends BSTNode<E> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected boolean isRed;// we add a color field to a BTNode

		public RBNode(E elem) {
			super(elem);
			isRed = true;
		}

		public RBNode(E element, RBNode<E> parent, RBNode<E> left, RBNode<E> right) {
			super(element, parent, left, right);
			isRed = true;
		}

		public void setColour(boolean colour) {
			isRed = colour;
		}

		public boolean isRed() {
			return isRed;
		}
		
		public static <E> boolean isRed(RBNode<E> node) {
			if (node == null) {
				return false;
			} else {
				return node.isRed;
			}
		}

		/**
		 * @return <code>true</code> if one of {@link RBNode this RBNode's} adjacent
		 *         {@link RBNode RBNodes} is red, <code>false</code> otherwise
		 */
		public boolean hasAdjacentRed() {
			if (parent != null && ((RBNode<E>) parent).isRed()) {
				return true;
			} else if (right != null && ((RBNode<E>) right).isRed()) {
				return true;
			} else if (left != null && ((RBNode<E>) left).isRed()) {
				return true;
			}
			return false;
		}

		public boolean hasRedChild() {
			if (right != null && ((RBNode<E>) right).isRed()) {
				return true;
			} else if (left != null && ((RBNode<E>) left).isRed()) {
				return true;
			}
			return false;
		}
		
		/**
		 * TODO supports <code>null</code> as valid child
		 */
		@Override
		protected BSTNode<E> getSiblingOf(BSTNode<E> child) {
			BSTNode<E> sibling = (child == left) ? right : left;
			return sibling;
		}

		protected RBNode<E> getRedChild() {
			if (isRed((RBNode<E>) right)) {
				return (RBNode<E>) right;
			} else if (isRed((RBNode<E>) left)) {
				return (RBNode<E>) left;
			} else {
				return null;
			}
		}

	}

	protected RB(RBNode<Entry<K, V>> n) {
		root = n;
	}

	public RB() {
		this(null);
	}

	@Override
	public V insert(K key, V value) {
		RBNode<Entry<K, V>> closestNode = (RBNode<Entry<K, V>>) findClosest(key);
		V overriddenValue = null;
		if (closestNode != null) {
			overriddenValue = closestNode.element.getValue();
		}
		RBNode<Entry<K, V>> insertedNode = (RBNode<Entry<K, V>>) insertAux(key, value, closestNode);
		if (closestNode == insertedNode) {
			return overriddenValue;
		} else {
			fixAfterInsertion(insertedNode);
			return null;
		}
	}

	/**
	 * Performs the needed operations to keep the RB properties of this {@link RB} after an insertion
	 * @param redNode {@link RBNode} that disrupted this {@link RB RB's} properties
	 */
	private void fixAfterInsertion(RBNode<Entry<K, V>> redNode) {
		assert(redNode.isRed());
		if (redNode == root) {
			redNode.setColour(BLACK);
		} else if (((RBNode<Entry<K,V>>) redNode.getParent()).isRed()) {
			remedyDoubleRed(redNode);
		}
	}

	/**
	 * Remedies a double red situation
	 * @param redNode {@link RBNode} that disrupted this {@link RB RB's} properties
	 */
	protected void remedyDoubleRed(RBNode<Entry<K, V>> redNode) {
		assert (redNode != root);
		assert (((RBNode<Entry<K, V>>) redNode.getParent()).isRed());
		assert (redNode.getParent().getParent() != null);
		
		RBNode<Entry<K, V>> parent = (RBNode<Entry<K, V>>) redNode.getParent();
		RBNode<Entry<K, V>> grandparent = (RBNode<Entry<K, V>>) parent.getParent();
		RBNode<Entry<K, V>> uncle = (RBNode<Entry<K, V>>) grandparent.getUncleOf(redNode);

		if (uncle == null || !uncle.isRed()) {
			fixBlackUncleCaseAfterInsertion(redNode);
		} else {
			fixRedUncleCaseAfterInsertion(parent, grandparent, uncle);
		}
	}

	/**
	 * TODO
	 * @param redNode
	 */
	private void fixBlackUncleCaseAfterInsertion(RBNode<Entry<K, V>> redNode) {
		RBNode<Entry<K, V>> node = (RBNode<Entry<K, V>>) restructure(redNode);
		node.setColour(BLACK);
		((RBNode<Entry<K, V>>) node.getLeft()).setColour(RED);
		((RBNode<Entry<K, V>>) node.getRight()).setColour(RED);
		if (node.getParent() == null) {
			root = node;
		}
	}

	/**
	 * TODO
	 * @param parent
	 * @param grandparent
	 * @param uncle
	 */
	private void fixRedUncleCaseAfterInsertion(RBNode<Entry<K, V>> parent, RBNode<Entry<K, V>> grandparent,
			RBNode<Entry<K, V>> uncle) {
		parent.setColour(BLACK);
		uncle.setColour(BLACK);
		grandparent.setColour(RED);
		fixAfterInsertion(grandparent);
	}

	@Override
	public V remove(K key) {
		if (isEmpty())
			return null;
		RBNode<Entry<K, V>> v = (RBNode<Entry<K, V>>) findNode(root, key);
		if (v == null) {
			return null;
		}
		V removedValue = v.element.getValue();
		removeNode(v);
		currentSize--;
//		RBNode<Entry<K, V>> deletedNode = (RBNode<Entry<K, V>>) removeAux(v);
//		v.setColour(deletedNode.isRed());
//		RBNode<Entry<K, V>> x = getFromReplacementNode(deletedNode);
//		fixAfterRemoval(deletedNode, x);
		return removedValue;
	}

    static <K,V> RBNode<Entry<K, V>> successor(RBNode<Entry<K, V>> t) {
        if (t == null)
            return null;
        else if (t.right != null) {
        	RBNode<Entry<K, V>> p = (RBNode<Entry<K, V>>) t.right;
            while (p.left != null)
                p = (RBNode<Entry<K, V>>) p.left;
            return p;
        } else {
        	RBNode<Entry<K, V>> p = (RBNode<Entry<K, V>>) t.parent;
        	RBNode<Entry<K, V>> ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = (RBNode<Entry<K, V>>) p.parent;
            }
            return p;
        }
    }
	
	private void removeNode(RBNode<Entry<K, V>> p) {
        // If strictly internal, copy successor's element to p and then make p
        // point to successor.
        if (p.left != null && p.right != null) {
            RBNode<Entry<K, V>> s = (RBNode<Entry<K, V>>) successor(p);
            p.element = s.element;
            p = s;
        } // p has 2 children

        // Start fixup at replacement node, if it exists.
        RBNode<Entry<K, V>> replacement = (p.left != null ? (RBNode<Entry<K, V>>) p.left : (RBNode<Entry<K, V>>) p.right);

        if (replacement != null) {
            // Link replacement to parent
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left  = replacement;
            else
                p.parent.right = replacement;

            // Null out links so they are OK to use by fixAfterDeletion.
            p.left = p.right = p.parent = null;

            // Fix replacement
            if (p.isRed == BLACK)
                fixAfterDeletion(replacement);
        } else if (p.parent == null) { // return if we are the only node.
            root = null;
        } else { //  No children. Use self as phantom replacement and unlink.
            if (p.isRed == BLACK)
                fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
	}
	
    private static <K,V> boolean colourOf(RBNode<Entry<K, V>> p) {
        return (p == null ? BLACK : p.isRed);
    }

    private static <K,V> RBNode<Entry<K, V>> parentOf(RBNode<Entry<K, V>> p) {
        return (p == null ? null: (RBNode<Entry<K, V>>) p.parent);
    }

    private static <K,V> void setColour(RBNode<Entry<K, V>> p, boolean c) {
        if (p != null)
            p.isRed = c;
    }

    private static <K,V> RBNode<Entry<K, V>> leftOf(RBNode<Entry<K, V>> p) {
        return (p == null) ? null: (RBNode<Entry<K, V>>) p.left;
    }

    private static <K,V> RBNode<Entry<K, V>> rightOf(RBNode<Entry<K, V>> p) {
        return (p == null) ? null: (RBNode<Entry<K, V>>) p.right;
    }
    
    private void fixAfterDeletion(RBNode<Entry<K, V>> x) {
        while (x != root && colourOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
            	RBNode<Entry<K, V>> sib = rightOf(parentOf(x));

                if (colourOf(sib) == RED) {
                    setColour(sib, BLACK);
                    setColour(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    if (x.parent.parent.parent == null) {
						root = x.parent.parent;
					}
                    sib = rightOf(parentOf(x));
                }

                if (colourOf(leftOf(sib))  == BLACK &&
                    colourOf(rightOf(sib)) == BLACK) {
                    setColour(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colourOf(rightOf(sib)) == BLACK) {
                        setColour(leftOf(sib), BLACK);
                        setColour(sib, RED);
                        rotateRight(sib);
                        if (x.parent.parent.parent == null) {
    						root = x.parent.parent;
    					}
                        sib = rightOf(parentOf(x));
                    }
                    setColour(sib, colourOf(parentOf(x)));
                    setColour(parentOf(x), BLACK);
                    setColour(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    if (x.parent.parent.parent == null) {
						root = x.parent.parent;
					}
                    x = (RBNode<Entry<K, V>>) root;
                }
            } else { // symmetric
            	RBNode<Entry<K, V>> sib = leftOf(parentOf(x));

                if (colourOf(sib) == RED) {
                    setColour(sib, BLACK);
                    setColour(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    if (x.parent.parent.parent == null) {
						root = x.parent.parent;
					}
                    sib = leftOf(parentOf(x));
                }

                if (colourOf(rightOf(sib)) == BLACK &&
                    colourOf(leftOf(sib)) == BLACK) {
                    setColour(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colourOf(leftOf(sib)) == BLACK) {
                        setColour(rightOf(sib), BLACK);
                        setColour(sib, RED);
                        rotateLeft(sib);
                        if (x.parent.parent.parent == null) {
    						root = x.parent.parent;
    					}
                        sib = leftOf(parentOf(x));
                    }
                    setColour(sib, colourOf(parentOf(x)));
                    setColour(parentOf(x), BLACK);
                    setColour(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    if (x.parent.parent.parent == null) {
						root = x.parent.parent;
					}
                    x = (RBNode<Entry<K, V>>) root;
                }
            }
        }

        setColour(x, BLACK);
    }

	@Override
	protected BSTNode<Entry<K, V>> nodeOf(Entry<K, V> element, BSTNode<Entry<K, V>> parent, BSTNode<Entry<K, V>> left,
			BSTNode<Entry<K, V>> right) {
		return new RBNode<Entry<K, V>>(element, (RBNode<Entry<K, V>>) parent, (RBNode<Entry<K, V>>) left,
				(RBNode<Entry<K, V>>) right);
	}
}
