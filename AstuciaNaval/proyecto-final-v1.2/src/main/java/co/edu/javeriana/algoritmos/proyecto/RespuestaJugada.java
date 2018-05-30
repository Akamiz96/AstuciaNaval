/**
 * 
 */
package co.edu.javeriana.algoritmos.proyecto;

/**
 * @author danilo
 *
 */
public enum RespuestaJugada 
{
	AGUA( "agua" ), IMPACTO( "impacto" ), HUNDIDO( "hundido" );
	
	private String letrero;

	private RespuestaJugada( String letrero ) {
		this.letrero = letrero;
	}
	
	public String getLetrero() {
		return letrero;
	}
	
}
