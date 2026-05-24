package logica;
import modelos.Partida;

public class GestorHistorial {
    //Arreglo de partidas para guardar el historial de las partidas jugadas
    private Partida[] historial;
    //Numero de partidas que hay hasta el momento
    private int numPartidas;
    //numero maximo de partidas que se pueden guardar en el historial
    private final int maxPartidas = 100;

    public GestorHistorial() {
        historial = new Partida[maxPartidas];
        numPartidas = 0;
    }

    public void agregarPartida(Partida partida){
          if( numPartidas < maxPartidas){
            historial[numPartidas] = partida;
            numPartidas++;
        } else {
            System.out.println("El historial está lleno. No se pueden agregar más partidas.");
        }
    }

    public void ordenarPorTiempo(){
        //Si la cantidad de partidas es menor o igual a 1, no hay nada que ordenar
        if (numPartidas <= 1) {
            System.out.println("No hay partidas en el historial para ordenar.");
            return;
        }

        //variable temporal para el intercambio
        Partida temp;

        for (int i = 0; i < numPartidas - 1; i++) {
     
            for (int j = 0; j < numPartidas - i - 1; j++) {
                int tiempoActual = historial[j].getTiempo();
                int tiempoSiguiente = historial[j + 1].getTiempo();

                // Evaluación: Si el tiempo actual es mayor, se intercambian
                if (tiempoActual > tiempoSiguiente) {
                    // Paso 1: Resguardar el objeto actual
                    temp = historial[j];
                    // Paso 2: Sobrescribir la posición actual con el objeto siguiente
                    historial[j] = historial[j + 1];                   
                    // Paso 3: Asignar el objeto resguardado a la posición siguiente
                    historial[j + 1] = temp;
                }
            }
        }
    }
}
