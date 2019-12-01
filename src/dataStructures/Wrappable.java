package dataStructures;

/**
 * An interface which extends {@link Wrappable} is such that its implementation
 * class may have a wrapper which may be wrapped in a type E, which must extend
 * {@link Wrapper}
 * 
 * @see Wrapper
 * @param <E> is the ObjectWrapper
 */
public interface Wrappable<E extends Wrapper> {

	/**
	 * Wraps this object in a type E wrapper
	 * 
	 * @return E ObjectWrapper
	 */
	E wrap();

}
