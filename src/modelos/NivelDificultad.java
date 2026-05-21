package modelos;

public abstract class NivelDificultad {
    private int numFilas = 0;
    private int numColumnas = 0;
    private int numMinas = 0;

    public NivelDificultad(int numFilas, int numColumnas, int numMinas){
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
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
    

}
