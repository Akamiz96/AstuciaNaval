package co.edu.javeriana.algoritmos.proyecto.jugadores.steamprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import co.edu.javeriana.algoritmos.proyecto.*;

public class Player implements Jugador, Tablero {

	private Double tablero[][];
	private Double inf;
	
	private int tableroRival[][];
    private ResumenTablero resumenRival;
    private int barcos_vivosRival;
    private Casilla disparoAnterior;
    private DireccionDisparo direccion = DireccionDisparo.SIMPLE;
    private int disparosAcertados = 0;
    private static int distanciaMinima = 2;

	
	private double dimension ; 
	private ResumenTablero resumen ;
	private int barcos_vivos ;
	
	
	public Player () {
		this.inf = Double.POSITIVE_INFINITY;
		this.disparoAnterior = null;
	}

	@Override
	public ResumenTablero obtenerResumen() {
		return resumen;
	}

	@Override
	public List<Casilla> obtenerCasillasOcupadasPorBarco(int numeroBarco) {
		
		List<Casilla> lista = new ArrayList<Casilla>() ;
		for ( int i = 0 ; i < dimension; i++) {
			for (int j = 0 ; j < dimension; j++) {
				if (tablero[i][j] == numeroBarco || tablero[i][j] == 	(numeroBarco * -1) ) {
					lista.add(new Casilla(i, j)) ;
				}
			}
		}
		return lista; 
	}

	@Override
	public RespuestaJugada dispararACasilla(Casilla casilla) {
		RespuestaJugada res = RespuestaJugada.AGUA ;
		if (tablero[casilla.getFila()][casilla.getColumna()] != this.inf) {
			int contador = 0; 
			List<Casilla> casillas = obtenerCasillasOcupadasPorBarco(  (int)(tablero[casilla.getFila()][casilla.getColumna()]).doubleValue()) ;
			tablero[casilla.getFila()][casilla.getColumna()] = this.inf;//tablero[casilla.getFila()][casilla.getColumna()] * -1 ;
			for(int i = 0 ; i < casillas.size() ; i++) {
				if (tablero[casillas.get(i).getFila()][casillas.get(i).getColumna()] == inf) {
					contador += 1 ; 
				}
			}
			if (contador == casillas.size()) {
				res = RespuestaJugada.HUNDIDO;
				this.barcos_vivos = this.barcos_vivos - 1 ; 
			}else {
				res = RespuestaJugada.IMPACTO;
			}
			
		}
		return res ;  

	}

	@Override
	/**
	 * Inicia el jugador con la dimensión y los barcos indicados.
	 * @param dimension Tamaño del lado del tablero.
	 * @param barcos La iésima casilla de este arreglo representa el tamaño en celdas del iésimo barco.
	 * @return Un tablero legal del tamaño indicado con todos los barcos dispuestos
	 */
	public Tablero iniciarTablero(int dimension, int[] barcos) {
		
		this.tablero= new Double [dimension][dimension];
		
		this.tableroRival = new int[dimension][dimension];
		
		for (int i = 0; i < this.tablero.length; i++) {
			for (int j = 0; j < this.tablero.length; j++) {
				this.tablero [i][j]= inf;
				this.tableroRival[i][j] = -1;
			}
		}
		//***************************
		
		this.resumen = new ResumenTablero(new int[dimension] , new int[dimension]);
		this.barcos_vivos = barcos.length ; 
		this.barcos_vivosRival = barcos.length ;
		this.dimension = dimension;
		//*********************************
		/*
		Double aux[][]= {
				{inf, 2.0, inf, inf, 2.0},
				{inf, 2.0, inf, inf, inf},
				{inf, 2.0, inf, inf, inf},
				{inf, inf, inf, inf, inf},
				{inf, inf, inf, inf, inf}
				};
		*/
		
		//System.out.println(validarHorizontal(aux, 2, 2, 0));
		//System.out.println(validarVertical(aux, 2, 4, 1));
		//System.out.println(validarDiagonal(aux, 2, 1, 3));
		//ponerBarco(aux, 3, 1, 1, 3.0);
		
		poner_Barcos(tablero, barcos, 0, 0, 0);
		return null;
	}

//-----------------------------------------------------------------------------------------
	private void poner_Barcos(Double [][] tablero, int barcos[], int b, int f, int c){
		
		Random r = new Random();
		int n;
		for (int i = f; i < tablero.length; i++) {
			for (int j = c; j < tablero.length; j++) {
				
				n = r.nextInt(4);
				
				if(b>=barcos.length){
					break;
				}else
				{
				switch(n){
				case 1:
					if(validarHorizontal(tablero, barcos[b], i, j)){
						ponerBarco(tablero, 1, i, j,(barcos[b]*1.0), b*1.0);
						//poner_Barcos(tablero, barcos, b, i, j);
						b++;
						break;
					}
					
					
				case 2:
					if(validarVertical(tablero, barcos[b], i, j)){
						ponerBarco(tablero, 2, i, j,(barcos[b]*1.0), b*1.0);
						//poner_Barcos(tablero, barcos, b, i, j);
						b++;
						break;
					}
					
					
				case 3:
					if(validarDiagonal(tablero, barcos[b], i, j)){
						ponerBarco(tablero, 3, i, j,(barcos[b]*1.0), b*1.0);
						//poner_Barcos(tablero, barcos, b, i, j);
						b++;
						break;
					}
					
					
				}
				
				}
			}
			
		}
		
	}
	
	private void ponerBarco(Double [][] tablero, int dir, int fila, int col, Double l, Double b){
		//Horizontal
		if(dir==1){
			for(int i=0;i<l;i++)
				tablero [fila][col+i]=(double) b;
		
		}
		//Vertical
		if(dir==2){
			//System.out.println(fila+"--"+col+">>"+l);
			for(int i=0;i<l;i++)
				tablero [fila+i][col]=(double) b;
		}
		
		//Diagonal
		if(dir==3)
				for(int i=0;i<l;i++)
					tablero [fila+i][col+i]=(double) b;
		
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				if(tablero [i][j]== Double.POSITIVE_INFINITY){
					System.out.print("-"+"\t");
				}else
					System.out.print(tablero [i][j]+"\t");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
	}
	
	
	
	private void borrarBarco(Double [][] tablero, int dir, int fila, int col, Double l){
		//Horizontal
		if(dir==1){
			for(int i=0;i<l;i++)
				tablero [fila][col+i]=this.inf;
		
		}
		//Vertical
		if(dir==2)
			for(int i=0;i<l;i++)
				tablero [fila+i][col]=this.inf;
		
		//Diagonal
		if(dir==3)
				for(int i=0;i<l;i++)
					tablero [fila+i][col+i]=this.inf;
		
		
		
	}
	
	private boolean validarHorizontal(Double [][] tablero, int l, int fila, int col){
		
		if((col+l)>=(tablero.length)){
			return false;
		}
		
		if(  (fila < (tablero.length-1)) && ((col+l) < (tablero.length)) ){
		
			if((fila-1)<0)
				return false;
			
			if((tablero [fila][col+1]) != this.inf )
					return false;
			
			for(int i=0;i<l;i++){
					if((tablero [fila][col+i] != this.inf ) 
							|| (tablero [fila+1][col+i] != this.inf )
								|| (tablero [fila-1][col+i] != this.inf ) )
						return false;		
			}
		}
		
		if(fila==0 && ((col+l) < (tablero.length-1))){
			for(int i=0;i<l;i++){
					if((tablero [fila][col+i] != this.inf ) 
							|| (tablero [fila+1][col+i] != this.inf ) )
						return false;
				
			}
		}
		
		
		if(fila ==(tablero.length-1)){
			for(int i=0;i<l;i++){
					if((tablero [fila][col+i] != this.inf ) 
								|| (tablero [fila-1][col+i] != this.inf ) )
						return false;
				
			}
		}
		
		
		if(col==0){
			
			if( ((fila+1) < (tablero.length)) && ((fila-1) > 0) )
			for(int i=0;i<l;i++){
				if((tablero [fila][col+i] != this.inf ) 
							|| (tablero [fila-1][col+i] != this.inf )
								|| (tablero [fila+1][col+i] != this.inf ))
					return false;
		}
		}
		
		if(col>0){
			if(tablero [fila][col-1] != this.inf)
				return false;
			
			if(fila>0)
			if(tablero [fila-1][col-1] != this.inf)
				return false;
			
			if((fila+l) < tablero.length)
			if(tablero [fila+1][col-1] != this.inf)
				return false;
		}
		
		if((col+l) < tablero.length){
			
			if(tablero [fila][col+l] != this.inf)
				return false;
			
			if(fila>0)
			if(tablero [fila-1][col+l] != this.inf)
				return false;
			
			if(((col+1) < (tablero.length)) && ((fila+1) < (tablero.length)))
			if(tablero [fila+1][col+l] != this.inf)
				return false;
		}
		
		
		
		return true;
	}
	
	
	
private boolean validarVertical(Double [][] tablero, int l, int fila, int col){
		
	if((fila+l) >= (tablero.length) )
		return false;
	
		if( ((fila+l) < (tablero.length)) && ((col+1) < (tablero.length))  ){
			
			if((col-1) < 0)
				return false;
			
			for(int i=0;i<l;i++){
				//System.out.println(fila+"--"+col+">>"+l);
					if((tablero [fila+i][col] != this.inf ) 
							|| (tablero [fila+i][col+1] != this.inf )
								|| (tablero [fila+i][col-1] != this.inf ) )
						return false;
				
			}
		}
		
		
		if(col==0 && ((fila+l) < (tablero.length)) ){
			for(int i=0;i<l;i++){
					if((tablero [fila+i][col] != this.inf ) 
							|| (tablero [fila+i][col+1] != this.inf ) )
						return false;
				
			}
		}
		
		if(col== (tablero.length-1)){
			for(int i=0;i<l;i++){
					if((tablero [fila+i][col] != this.inf ) 
							|| (tablero [fila+i][col-1] != this.inf ) )
						return false;
				
			}
		}
		
		
		if(fila ==(tablero.length-1)){
			if(l>1)
				return false;
		}
		
		
		if(fila>0){
			if(tablero [fila-1][col] != this.inf)
				return false;
			
			if(col>0)
			if(tablero [fila-1][col-1] != this.inf)
				return false;
			
			if(col <(tablero.length-1))
			if(tablero [fila-1][col+1] != this.inf)
				return false;
		}
		
		if((fila+l) < (tablero.length)){
			
			
			if(tablero [fila+l][col] != this.inf)
				return false;
			
			//System.out.println(fila+"--"+col);
			
			
			if(col>0)
			if(tablero [fila+l][col-1] != this.inf)
				return false;
			
			if(((col+1) < (tablero.length)) && ((fila+1) < (tablero.length)))
			if(tablero [fila+l][col+1] != this.inf)
				return false;
		}
		
		
		
		return true;
	}
	
	
private boolean validarDiagonal(Double [][] tablero, int l, int fila, int col){
	
	//System.out.println(fila+"--"+col);
	
	if( (col>0) && (fila>0) && ((fila+l) < (tablero.length)) && ((col) < (tablero.length)) && ((col+l) < (tablero.length))  ){
		
		for(int i=0;i<l;i++){
			//System.out.println(col);
				if((tablero [fila+i][col+i] != this.inf ) 
						|| (tablero [fila+i][col+i+1] != this.inf )
							|| (tablero [fila+i][col+i-1] != this.inf ) 
								|| (tablero [fila+i+1][col+i-1] != this.inf ) 
									|| (tablero [fila+i-1][col+i+1] != this.inf ) )
					return false;
			
		}
	}
	
	if(fila==0){
		if(tablero [fila][col] != this.inf )
			return false;
		
		if(col>0)
		if(tablero [fila][col-1] != this.inf )
			return false;
	
		if(col< (tablero.length-1))
		if(tablero [fila][col+1] != this.inf )
			return false;
		
	}
	
	if(col==0 && ((fila+l) < (tablero.length))){
		
		if(tablero [fila][col] != this.inf )
			return false;
		
		if(tablero [fila][col+1] != this.inf )
			return false;
		
		for(int i=1;i<l;i++){
			//System.out.println(fila);
				if((tablero [fila+i][col+i] != this.inf ) 
						|| (tablero [fila+i][col+i+1] != this.inf )
							|| (tablero [fila+i][col+i-1] != this.inf ) 
								|| (tablero [fila+i+1][col+i-1] != this.inf ) 
									|| (tablero [fila+i-1][col+i+1] != this.inf )
							)
					return false;
			
		}
	}
	
	
	if((fila ==(tablero.length-1)) || col ==(tablero.length-1)){
		if(l>1)
			return false;
	}
	
	
	if(fila>0){
		
		if(tablero [fila-1][col] != this.inf)
			return false;
		
		if(col>0)
		if(tablero [fila-1][col-1] != this.inf)
			return false;
		
		if(col <(tablero.length-2))
		if(tablero [fila-1][col+1] != this.inf)
			return false;
	}
	
	if((fila+l) < (tablero.length)){
		
		//System.out.println(col+l);
		
		if(tablero [fila+l][col] != this.inf)
			return false;
		
		//System.out.println(fila+"--"+col);
		
		
		if(col>0)
		if(tablero [fila+l][col-1] != this.inf)
			return false;
		
		if(((fila+l) < (tablero.length)) && ((col+1) < (tablero.length)))
		if(tablero [fila+l][col+1] != this.inf)
			return false;
	}
	
		if((col+l) >= (tablero.length)){
				return false;
			}
		if((fila+l) >= (tablero.length)){
			return false;
		}
	
	return true;
}
	
	
//-----------------------------------------------------------------------------------------	
	
	
	
	@Override
	public void recibirResumenRival(ResumenTablero resumen) {
		resumenRival = resumen;
	}

	@Override
	public RespuestaJugada registrarDisparoAPosicion(Casilla posicion) {
		RespuestaJugada respuesta  ; 
		respuesta = dispararACasilla(posicion);
		return respuesta;		
	}

	@Override
    // Se utiliza el metodo de pariedad empezando en la esquina superior izquierda
    // Se dispara con distancia de (distanciaminima) entre disparos.
    public Casilla realizarDisparo() {
        if (disparoAnterior == null) {
            this.disparoAnterior = new Casilla(0, 0);
            return this.disparoAnterior;
        }
        // Si el disparo anterior fue agua
        if (tableroRival[disparoAnterior.getFila()][disparoAnterior.getColumna()] != 1) {
            // Si no esta en modo de haber encontrado un disparo valido
            if (direccion == DireccionDisparo.SIMPLE) {
                if (disparoAnterior.getColumna() + Player.distanciaMinima < dimension) {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila(),
                            disparoAnterior.getColumna() + distanciaMinima);
                } else {
                    // Si ya se disparo en la primera columna de esa fila
                    // se disparara en la siguiente fila pero en la segunda columna
                    if (tableroRival[this.disparoAnterior.getFila()][0] != -1) {
                        this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1, 1);
                    } else {
                        this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1, 0);
                    }
                }
                if (yaSeRealizoDisparoACasilla(this.disparoAnterior))
                    this.disparoAnterior = this.realizarDisparo();
                return this.disparoAnterior;
            } else {
                switch (this.direccion) {
                case ABAJO:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getFila() + 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1,
                                    disparoAnterior.getColumna());
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() - this.disparosAcertados - 1,
                                    disparoAnterior.getColumna());
                            this.direccion = DireccionDisparo.ARRIBA;
                        }
                    } else {
                        this.direccion = DireccionDisparo.ARRIBA;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila() - 1,
                                this.disparoAnterior.getColumna());
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                case ARRIBA:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getFila() - 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() - 1,
                                    disparoAnterior.getColumna());
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() + this.disparosAcertados + 1,
                                    disparoAnterior.getColumna());
                            this.direccion = DireccionDisparo.ABAJO;
                        }
                    } else {
                        this.direccion = DireccionDisparo.DERECHA;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila() + 1,
                                this.disparoAnterior.getColumna());
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                case DERECHA:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getColumna() + 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila(),
                                    disparoAnterior.getColumna() + 1);
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila(),
                                    disparoAnterior.getColumna() - this.disparosAcertados - 1);
                            this.direccion = DireccionDisparo.IZQUIERDA;
                        }
                    } else {
                        this.direccion = DireccionDisparo.IZQUIERDA;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila(),
                                this.disparoAnterior.getColumna() - 1);
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                case IZQUIERDA:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getColumna() - 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila(),
                                    disparoAnterior.getColumna() - 1);
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila(),
                                    disparoAnterior.getColumna() + this.disparosAcertados + 1);
                            this.direccion = DireccionDisparo.DERECHA;
                        }
                    } else {
                        this.direccion = DireccionDisparo.DIAGONALABAJODERECHA;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila(),
                                this.disparoAnterior.getColumna() + 1);
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                case DIAGONALABAJODERECHA:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getFila() + 1 < this.dimension
                                && disparoAnterior.getColumna() + 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1,
                                    disparoAnterior.getColumna() + 1);
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() - this.disparosAcertados - 1,
                                    disparoAnterior.getColumna() - this.disparosAcertados - 1);
                            this.direccion = DireccionDisparo.DIAGONALARRIBAIZQUIERDA;
                        }
                    } else {
                        this.direccion = DireccionDisparo.DIAGONALABAJOIZQUIERDA;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila() - 1,
                                this.disparoAnterior.getColumna() - 1);
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                case DIAGONALABAJOIZQUIERDA:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getFila() + 1 < this.dimension
                                && disparoAnterior.getColumna() - 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1,
                                    disparoAnterior.getColumna() - 1);
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() - this.disparosAcertados - 1,
                                    disparoAnterior.getColumna() + this.disparosAcertados + 1);
                            this.direccion = DireccionDisparo.DIAGONALARRIBADERECHA;
                        }
                    } else {
                        this.direccion = DireccionDisparo.DIAGONALARRIBADERECHA;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila() - 1,
                                this.disparoAnterior.getColumna() + 1);
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                case DIAGONALARRIBADERECHA:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getFila() - 1 < this.dimension
                                && disparoAnterior.getColumna() + 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() - 1,
                                    disparoAnterior.getColumna() + 1);
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() + this.disparosAcertados + 1,
                                    disparoAnterior.getColumna() - this.disparosAcertados - 1);
                            this.direccion = DireccionDisparo.DIAGONALARRIBADERECHA;
                        }
                    } else {
                        this.direccion = DireccionDisparo.DIAGONALARRIBAIZQUIERDA;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila() + 1,
                                this.disparoAnterior.getColumna() - 1);
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                case DIAGONALARRIBAIZQUIERDA:
                    if (this.disparosAcertados > 1) {
                        if (disparoAnterior.getFila() - 1 < this.dimension
                                && disparoAnterior.getColumna() - 1 < this.dimension)
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() - 1,
                                    disparoAnterior.getColumna() - 1);
                        else {
                            this.disparoAnterior = new Casilla(disparoAnterior.getFila() + this.disparosAcertados + 1,
                                    disparoAnterior.getColumna() + this.disparosAcertados + 1);
                            this.direccion = DireccionDisparo.DIAGONALABAJODERECHA;
                        }
                    }else {
                        this.direccion = DireccionDisparo.SIMPLE;
                        this.disparoAnterior = new Casilla(this.disparoAnterior.getFila() + 1,
                                this.disparoAnterior.getColumna() + 1);
                        this.disparoAnterior = this.realizarDisparo();
                    }
                    break;
                default:
                    this.disparoAnterior = new Casilla((int)this.dimension/2,(int)this.dimension/2);
                    break;
                }
                return this.disparoAnterior;
            }
        }
        // Si el disparo anterior dio en algun blanco
        if (tableroRival[disparoAnterior.getFila()][disparoAnterior.getColumna()] == 1) {
            this.disparosAcertados++;
            switch (this.direccion) {
            case SIMPLE:
                this.direccion = DireccionDisparo.ABAJO;
            case ABAJO:
                if (disparoAnterior.getFila() + 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1, disparoAnterior.getColumna());
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() - this.disparosAcertados - 1,
                            disparoAnterior.getColumna());
                    this.direccion = DireccionDisparo.ARRIBA;
                }
                break;
            case ARRIBA:
                if (disparoAnterior.getFila() - 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() - 1, disparoAnterior.getColumna());
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() + this.disparosAcertados + 1,
                            disparoAnterior.getColumna());
                    this.direccion = DireccionDisparo.ABAJO;
                }
                break;
            case DERECHA:
                if (disparoAnterior.getColumna() + 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila(), disparoAnterior.getColumna() + 1);
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila(),
                            disparoAnterior.getColumna() - this.disparosAcertados - 1);
                    this.direccion = DireccionDisparo.IZQUIERDA;
                }
                break;
            case IZQUIERDA:
                if (disparoAnterior.getColumna() - 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila(), disparoAnterior.getColumna() - 1);
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila(),
                            disparoAnterior.getColumna() + this.disparosAcertados + 1);
                    this.direccion = DireccionDisparo.DERECHA;
                }
                break;
            case DIAGONALABAJODERECHA:
                if (disparoAnterior.getFila() + 1 < this.dimension && disparoAnterior.getColumna() + 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1, disparoAnterior.getColumna() + 1);
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() - this.disparosAcertados - 1,
                            disparoAnterior.getColumna() - this.disparosAcertados - 1);
                    this.direccion = DireccionDisparo.DIAGONALARRIBAIZQUIERDA;
                }
                break;
            case DIAGONALABAJOIZQUIERDA:
                if (disparoAnterior.getFila() + 1 < this.dimension && disparoAnterior.getColumna() - 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() + 1, disparoAnterior.getColumna() - 1);
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() - this.disparosAcertados - 1,
                            disparoAnterior.getColumna() + this.disparosAcertados + 1);
                    this.direccion = DireccionDisparo.DIAGONALARRIBADERECHA;
                }
                break;
            case DIAGONALARRIBADERECHA:
                if (disparoAnterior.getFila() - 1 < this.dimension && disparoAnterior.getColumna() + 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() - 1, disparoAnterior.getColumna() + 1);
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() + this.disparosAcertados + 1,
                            disparoAnterior.getColumna() - this.disparosAcertados - 1);
                    this.direccion = DireccionDisparo.DIAGONALARRIBADERECHA;
                }
                break;
            case DIAGONALARRIBAIZQUIERDA:
                if (disparoAnterior.getFila() - 1 < this.dimension && disparoAnterior.getColumna() - 1 < this.dimension)
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() - 1, disparoAnterior.getColumna() - 1);
                else {
                    this.disparoAnterior = new Casilla(disparoAnterior.getFila() + this.disparosAcertados + 1,
                            disparoAnterior.getColumna() + this.disparosAcertados + 1);
                    this.direccion = DireccionDisparo.DIAGONALABAJODERECHA;
                }
                break;
            }
            return this.disparoAnterior;
        }
        return null;
    }
	
	private boolean yaSeRealizoDisparoACasilla(Casilla casilla) {
        int fila = casilla.getFila();
        int columna = casilla.getColumna();
        if (this.tableroRival[fila][columna] == -1) {
            return false;
        }
        return true;
    }


	@Override
    public void procesarResultadoDisparo(RespuestaJugada resultado) {
        if (disparoAnterior != null) {
            switch (resultado) {
            case AGUA:
                tableroRival[disparoAnterior.getFila()][disparoAnterior.getColumna()] = 0;
                break;
            case HUNDIDO:
                tableroRival[disparoAnterior.getFila()][disparoAnterior.getColumna()] = 1;
                barcos_vivosRival--;
                this.disparosAcertados = 0;
                this.direccion = DireccionDisparo.SIMPLE;
                break;
            case IMPACTO:
                tableroRival[disparoAnterior.getFila()][disparoAnterior.getColumna()] = 1;
                break;
            }
        }
    }


	@Override
	public int numeroBarcosNoHundidos() {
		return barcos_vivos;
	}

	@Override
	public String obtenerNombre() {
		return " ...Steam process... ";
	}
	
}