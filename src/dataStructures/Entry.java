/**
 * 
 */
package dataStructures;

import java.io.Serializable;

/**
 * @author AED_19_20
 *
 */
public interface Entry<K, V> extends Serializable {
	// Returns the key in the entry.
	K getKey( );
	 
	// Returns the value in the entry.
	V getValue( );
}
