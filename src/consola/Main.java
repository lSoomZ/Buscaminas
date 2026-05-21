package consola;
import modelos.Celda;
import modelos.Tablero;
import java.util.Scanner;

public class Main{
  public static void main (String[] args){
   Scanner orden = new Scanner(System.in);
    //int filas = orden.nextInt();
    //int columnas = orden.nextInt();

    

  }

  public static void imprimirTablero(Tablero tablero) {
    int sizeFilas = tablero.getNivel().getNumFilas();
    int sizeColumnas = tablero.getNivel().getNumColumnas();
    //⚑ ¤

    System.out.print("   ");

    for (int i = 0; i < sizeColumnas; i++){
      System.out.print(i + " ");
    }
    System.out.println();

    for (int i = 0; i < sizeFilas; i++){
      if (i <10){
        System.out.print(i + "  ");
      } else{
        System.out.print(i + " ");
      }
      
      for (int j = 0; j < sizeColumnas; j++){
        Celda celdaActual = tablero.getCelda(i, j);
        System.out.print("■ ");
      }

      System.out.println();

    }
    
  }
  
}