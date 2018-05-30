package co.edu.javeriana.algoritmos.proyecto.jugadores.steamprocess;

public enum DireccionDisparo {

	    ARRIBA( "arriba" ), ABAJO( "abajo" ), DERECHA( "derecha" ), IZQUIERDA( "izquierda" )
	    , DIAGONALARRIBADERECHA( "diagonalarribaderecha" ), DIAGONALARRIBAIZQUIERDA( "diagonalarribaizquierda" )
	    , DIAGONALABAJODERECHA( "diagonalabajoderecha" ), DIAGONALABAJOIZQUIERDA( "diagonalabajoizquierda" )
	    , SIMPLE ("simple");
	    
	    private String letrero;

	    private DireccionDisparo( String letrero ) {
	   	 this.letrero = letrero;
	    }
	    
	    public String getLetrero() {
	   	 return letrero;
	    }
	    
	}


