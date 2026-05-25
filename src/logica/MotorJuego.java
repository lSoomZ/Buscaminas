package logica;
import java.util.Random;
import modelos.Tablero;
import modelos.Celda;

public class MotorJuego {

    public void distribuirMinas(Tablero tablero) {
        int minasPedidas = tablero.getNivel().getNumMinas();
        int filas = tablero.getNivel().getNumFilas();
        int columnas = tablero.getNivel().getNumColumnas();
        Random generador = new Random();
        int minasColocadas = 0;
        
        // Validación de la cantidad de minas con base en el tamaño
        if (minasPedidas > (filas * columnas)) {
            System.out.println("Elija un número de minas menor.");
            return; 
        }

        // El ciclo se mantiene activo estrictamente hasta cumplir con la cantidad de minas requerida
        while (minasColocadas < minasPedidas) {
            int filaAleatoria = generador.nextInt(filas);
            int columnaAleatoria = generador.nextInt(columnas);

            // Se extrae la referencia a la celda en esa coordenada exacta
            Celda celdaActual = tablero.getCelda(filaAleatoria, columnaAleatoria);

            // Verificación de colisión en memoria
            if (!celdaActual.getEsMina()) {
                // Mutación del objeto Celda
                celdaActual.setEsMina(true);
                minasColocadas++;
            }
        }
    }

    public void calcularMinasAdyacentes(Tablero tablero) {
        int filas = tablero.getNivel().getNumFilas();
        int columnas = tablero.getNivel().getNumColumnas();

        //Recorrido total de la matriz
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Celda celdaActual = tablero.getCelda(i, j);
                int contadorMinas = 0;

                // Si la celda es una mina, no se calcula nada y salta a la siguiente
                if (celdaActual.getEsMina()) {
                    continue;
                }

                //Exploración de vecinos mediante matriz de 3x3 (desplazamientos -1, 0, 1)
                for (int desplazamientoFila = -1; desplazamientoFila <= 1; desplazamientoFila++) {
                    for (int desplazamientoColumna = -1; desplazamientoColumna <= 1; desplazamientoColumna++) {
                        
                        int filaVecina = i + desplazamientoFila;
                        int columnaVecina = j + desplazamientoColumna;

                        //Control de los límites de la matriz
                        boolean filaValida = filaVecina >= 0 && filaVecina < filas;
                        boolean columnaValida = columnaVecina >= 0 && columnaVecina < columnas;

                        if (filaValida && columnaValida) {
                            Celda vecino = tablero.getCelda(filaVecina, columnaVecina);
                            if (vecino.getEsMina()) {
                                contadorMinas++;
                            }
                        }
                    }
                }
                //Se guarda el resultado en el objeto Celda
                celdaActual.setMinasAlrededor(contadorMinas);
            }
        }
    }

    // Método que recibe las coordenadas elegidas por el jugador
    public void descubrirCelda(int fila, int columna, Tablero tablero) {
        //Caso Base: Límites de la matriz
        if (fila < 0 || fila >= tablero.getNivel().getNumFilas() || columna < 0 || columna >= tablero.getNivel().getNumColumnas()) {
            return;
        }

        Celda celdaActual = tablero.getCelda(fila, columna);

        //Caso Base: Evitar bucles infinitos y proteger celdas
        if (celdaActual.getEstaRevelada()) {
            return;
        }

        //Mutación de estado
        celdaActual.setEstaRevelada(true);

        //Lógica de expansión condicional
        if (celdaActual.getMinasAlrededor() == 0 && !celdaActual.getEsMina()) {
            
            // Recorrido de los 8 vecinos mediante desplazamientos
            for (int desplazamientoFila = -1; desplazamientoFila <= 1; desplazamientoFila++) {
                for (int desplazamientoColumna = -1; desplazamientoColumna <= 1; desplazamientoColumna++) {
                    
                    if (desplazamientoFila != 0 || desplazamientoColumna != 0) {
                        
                        // LLAMADA RECURSIVA a los vecinos
                        descubrirCelda(fila + desplazamientoFila, columna + desplazamientoColumna, tablero);
                    }
                }
            }
        }
    }

    // Método para colocar o quitar una bandera
    public void alternarBandera(int fila, int columna, Tablero tablero) {
        // Filtro de límites
        if (fila < 0 || fila >= tablero.getNivel().getNumFilas() || columna < 0 || columna >= tablero.getNivel().getNumColumnas()) {
            System.out.println("Coordenada fuera de los límites.");
            return;
        }

        Celda celdaActual = tablero.getCelda(fila, columna);

        // Filtro de estado
        if (celdaActual.getEstaRevelada()) {
            System.out.println("No puedes poner una bandera en una celda descubierta.");
            return;
        }

        // Mutación de estado (inversión booleana)
        boolean estadoActual = celdaActual.getBandera();
        celdaActual.setBandera(!estadoActual);
    }

    // Método para verificar la condición de victoria
    public boolean verificarResultado(Tablero tablero) {
        int celdasOcultas = 0;
        int filas = tablero.getNivel().getNumFilas();
        int columnas = tablero.getNivel().getNumColumnas();

        // Recorrido de la matriz para contar celdas no descubiertas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!tablero.getCelda(i, j).getEstaRevelada()) {
                    celdasOcultas++;
                }
            }
        }

        // Si las únicas celdas ocultas que quedan son exactamente las minas, es victoria
        return celdasOcultas == tablero.getNivel().getNumMinas();
    }
}