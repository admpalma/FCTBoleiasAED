package fctBoleias.trip;

public class TripIsFullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TripIsFullException(int position) {
		super(String.format("Ficou na fila de espera (posicao %d).", position));
	}
	
	public TripIsFullException(String message) {
		super(message);
	}

}
