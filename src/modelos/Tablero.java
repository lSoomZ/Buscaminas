package modelos;
public class Tablero {
    private Celda[][] cuadricula;
    private NivelDificultad nivel;
  
    //Constructor para el tablero con las dimensiones y cantidad de minas requeridas
    public Tablero(NivelDificultad nivelElegido){
        this.nivel = nivelElegido;
        this.cuadricula = new Celda[nivel.getNumFilas()][nivel.getNumColumnas()];

        for (int i = 0; i < nivel.getNumFilas(); i++) {
            for (int j = 0; j < nivel.getNumColumnas(); j++) {  
            //Inicializamos cada celda de la cuadricula con los parametros de la clase Celda
                cuadricula[i][j] = new Celda();
            }
        }
    }

    public NivelDificultad getNivel() {
        return nivel;
    }

    //Get para obtener el valor de una celda específica en la cuadricula
    public Celda getCelda(int fila, int columna) {
        return cuadricula[fila][columna];
    }
}
