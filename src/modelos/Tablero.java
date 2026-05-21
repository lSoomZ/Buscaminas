package modelos;
public class Tablero {
    private Celda[][] cuadricula;
    private int numFilas;
    private int numColumnas;
    private int numMinas;
    
    //Constructor para el tablero con las dimensiones y cantidad de minas requeridas
    public Tablero(int numFilas, int numColumnas, int numMinas){
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        this.cuadricula = new Celda[numFilas][numColumnas];

        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {  
            //Inicializamos cada celda de la cuadricula con los parametros de la clase Celda
                cuadricula[i][j] = new Celda();
            }
        }
    }

    //Get para la cantidad de Filas que hay en la cuadricula
    public int getNumFilas(){
        return numFilas;
    }

    //Get para la cantidad de Columnas que hay en la cuadricula
    public int getNumColumnas(){
        return numColumnas;
    }

    //Get para la cantidad de Minas que hay en la cuadricula
    public int getNumMinas(){
        return numMinas;
    }

    //Get para obtener el valor de una celda específica en la cuadricula
    public Celda getCuadricula(int fila, int columna) {
        return cuadricula[fila][columna];
    }

}
