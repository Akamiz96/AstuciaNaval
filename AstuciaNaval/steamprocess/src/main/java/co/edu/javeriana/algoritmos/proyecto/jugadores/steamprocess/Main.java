package co.edu.javeriana.algoritmos.proyecto.jugadores.steamprocess;

import co.edu.javeriana.algoritmos.proyecto.*;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Player player = new Player();
		int b[]={3,1,2};
		player.iniciarTablero( 6, b);
		/*
		System.out.println(player.dispararA_Casilla(new Casilla(1,1)));
		System.out.println(player.numeroBarcosNoHundidos());
		System.out.println(player.dispararA_Casilla(new Casilla(0,0)));
		System.out.println(player.numeroBarcosNoHundidos());
		System.out.println(player.dispararA_Casilla(new Casilla(2,2)));
		System.out.println(player.numeroBarcosNoHundidos());*/
		ResumenTablero r= player.obtenerResumen();
		
		for(int i=0;i<6;i++){
			System.out.println(r.getCasillasOcupadasEnFila(i)+"-->> "+i);
		}
	}

}
