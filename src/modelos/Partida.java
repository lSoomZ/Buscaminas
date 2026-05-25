package modelos;

public class Partida {
    private String dificultad;
    private int tiempo;
    private boolean resultado;
    private int filas;
    private int columnas;
    private int minas;

    public Partida(String dificultad, int tiempo, boolean resultado, int filas, int columnas, int minas) {
        this.dificultad = dificultad;
        this.tiempo = tiempo;
        this.resultado = resultado;
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
    }

    @Override
    // El toString hace que al imprimir un objeto Partida se muestre toda su información 
    // de forma ordenada y legible
    public String toString() {
        String estado = resultado ? "Victoria" : "Derrota";
        return "Dificultad: " + dificultad + " | Tamaño: " + filas + "x" + columnas + 
                " | Minas: " + minas + " | Tiempo: " + tiempo + "s | Resultado: " + estado;
    }

    // Getters para acceder a la información de la partida desde otras clases
    
    public String getDificultad() {
        return dificultad;
    }

    public int getTiempo() {
        return tiempo;
    }

    public boolean getResultado() {
        return resultado;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getMinas() {
        return minas;
    }
}
