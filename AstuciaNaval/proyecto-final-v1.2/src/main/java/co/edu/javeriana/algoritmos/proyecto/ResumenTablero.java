/**
 * 
 */
package co.edu.javeriana.algoritmos.proyecto;

/**
 * @author danilo
 *
 */
public class ResumenTablero 
{
	private int[] casillasOcupadasFila, casillasOcupadasColumna;

	public ResumenTablero( int[] casillasOcupadasFila, int[] casillasOcupadasColumna ) {
		super();
		this.casillasOcupadasFila = casillasOcupadasFila;
		this.casillasOcupadasColumna = casillasOcupadasColumna;
	}

	public int getCasillasOcupadasEnFila( int fila ) {
		return casillasOcupadasFila[fila];
	}

	public int getCasillasOcupadasEnColumna( int columna ) {
		return casillasOcupadasColumna[columna];
	}

}
