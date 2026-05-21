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
    public String toString() {
        String estado = resultado ? "Victoria" : "Derrota";
        return "Dificultad: " + dificultad + " | Tamaño: " + filas + "x" + columnas + 
                " | Minas: " + minas + " | Tiempo: " + tiempo + "s | Resultado: " + estado;
    }

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
