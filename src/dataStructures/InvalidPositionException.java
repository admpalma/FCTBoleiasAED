/**
 * 
 */
package dataStructures;

/**
 * @author AED_19_20
 *
 */
public class InvalidPositionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidPositionException( )
	{
	super();
	}
	public InvalidPositionException( String msg )
	{
	super(msg);
	}
}
