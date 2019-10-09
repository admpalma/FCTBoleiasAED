package dataStructures;

public class OutOfCapacityException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OutOfCapacityException( )
	{
	super();
	}
	public OutOfCapacityException( String msg )
	{
	super(msg);
	}
}
