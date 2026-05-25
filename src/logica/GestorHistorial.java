package logica;

import modelos.Partida;

public class GestorHistorial {
    // Arreglo de partidas para guardar el historial de las partidas jugadas
    private Partida[] historial;
    // Numero de partidas que hay hasta el momento
    private int numPartidas;
    // numero maximo de partidas que se pueden guardar en el historial
    private final int maxPartidas = 100;

    public GestorHistorial() {
        historial = new Partida[maxPartidas];
        numPartidas = 0;
    }

    public void agregarPartida(Partida partida) {
        if (numPartidas < maxPartidas) {
            historial[numPartidas] = partida;
            numPartidas++;
        } else {
            System.out.println("El historial está lleno. No se pueden agregar más partidas.");
        }
    }

    public void ordenarPorTiempo() {
        // variable temporal para el intercambio en la ordenación
        Partida temp;
        // Si la cantidad de partidas es menor o igual a 1, no hay nada que ordenar
        if (numPartidas <= 1) {
            System.out.println("No hay partidas en el historial para ordenar.");
            return;
        }

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

    // Método para mostrar en la pantalla todas las partidas que se han jugado
    public void mostrarHistorial() {
        ordenarPorTiempo(); // Primero ordeno las partidas por tiempo

        // Si el contador está en cero significa que todavía no hay partidas guardadas
        if (numPartidas == 0) {
            System.out.println("\nEl historial está vacío. Juega una partida primero.");
            return;
        }

        System.out.println("\n=== HISTORIAL DE PARTIDAS (Ordenado por mejor tiempo) ===");
        System.out.println("Dificultad | Tiempo (s) | Resultado | Tamaño | Minas");
        System.out.println("------------------------------------------------------");

        // Recorro el arreglo usando 'numPartidas' para no tocar las casillas vacías
        // (null)
        for (int i = 0; i < numPartidas; i++) {
            Partida p = historial[i];
            // Reviso el booleano del resultado para mostrar un texto amigable
            String textoResultado = p.getResultado() ? "Ganada" : "Perdida";

            // Imprimo los datos de la partida en una sola línea
            System.out.println(p.getDificultad() + " | " +
                    p.getTiempo() + "s | " +
                    textoResultado + " | " +
                    p.getFilas() + "x" + p.getColumnas() + " | " +
                    p.getMinas());
        }
        System.out.println("------------------------------------------------------");
    }

    // Método para localizar una partida por su duración en segundos
    public Partida buscarPartidaPorTiempo(int tiempoObjetivo) {
        ordenarPorTiempo(); // Me aseguro que el arreglo esté en el orden correcto para la búsqueda binaria

        // Inicialización de punteros basada exclusivamente en los datos válidos
        int limiteInferior = 0;
        int limiteSuperior = numPartidas - 1;

        // El ciclo iterará dividiendo el arreglo hasta encontrar el dato o hasta que
        // los límites se crucen
        while (limiteInferior <= limiteSuperior) {
            int indiceMedio = limiteInferior + (limiteSuperior - limiteInferior) / 2;
            // Extracción del atributo sobre el cual se basará la comparación
            int tiempoMedio = historial[indiceMedio].getTiempo(); 

            // Casos base de la Búsqueda Binaria
            if (tiempoMedio == tiempoObjetivo) {
                return historial[indiceMedio]; // Retorna la referencia de memoria del objeto encontrado

            } else if (tiempoMedio < tiempoObjetivo) {
                limiteInferior = indiceMedio + 1; // Descarta la mitad izquierda. Se mueve el límite inferior

            } else {
                limiteSuperior = indiceMedio - 1; // Descarta la mitad derecha. Se mueve el límite superior
            }
        }
        return null;
    }
}
