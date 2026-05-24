package consola;

import modelos.Celda;
import modelos.Tablero;
import modelos.NivelDificultad;
import modelos.Principiante;
import modelos.Intermedio;
import modelos.Partida;
import modelos.Experto;
import modelos.Personalizado;
import logica.MotorJuego;
import logica.GestorHistorial;
import java.util.Scanner;

//⚑ ¤
public class Main {
  public static void main(String[] args) {
    Scanner orden = new Scanner(System.in);
    GestorHistorial gestorHistorial = new GestorHistorial();
    NivelDificultad nivelActual = null;
    int opcion = 0;

    // Menú de selección
    do {
      System.out.println("\n--- BUSCAMINAS ---");
      System.out.println("1. Principiante");
      System.out.println("2. Intermedio");
      System.out.println("3. Experto");
      System.out.println("4. Personalizado");
      System.out.print("Seleccione una dificultad: ");

      opcion = orden.nextInt();

      // Bloque switch para instanciar la clase concreta correcta
      switch (opcion) {
        case 1:
          nivelActual = new Principiante();
          break;

        case 2:
          nivelActual = new Intermedio();
          break;

        case 3:
          nivelActual = new Experto();
          break;

        case 4:
          System.out.print("Ingrese número de filas: ");
          int f = orden.nextInt();
          System.out.print("Ingrese número de columnas: ");
          int c = orden.nextInt();
          System.out.print("Ingrese número de minas: ");
          int m = orden.nextInt();
          // Instanciación dinámica con los parámetros del usuario
          nivelActual = new Personalizado(f, c, m);
          break;

        default:
          System.out.println("Opción inválida. Ingrese un número del 1 al 4.");
          break;
      }
    } while (opcion < 1 || opcion > 4);

    // Inyección de dependencias y arranque del juego
    Tablero tablero = new Tablero(nivelActual);
    MotorJuego motor = new MotorJuego();

    motor.distribuirMinas(tablero);
    motor.calcularMinasAdyacentes(tablero);

    System.out.println("¡Juego iniciado!");

    // Inicio del control del tiempo
    long tiempoInicio = System.currentTimeMillis();
    boolean juegoActivo = true;
    boolean resultado = false;

    while (juegoActivo) {
      // Renderizado de la matriz actual
      System.out.println("\n=================================");
      imprimirTablero(tablero);
      System.out.println("=================================");

      // Captura de Coordenadas
      int limiteFilas = tablero.getNivel().getNumFilas() - 1;
      int limiteColumnas = tablero.getNivel().getNumColumnas() - 1;

      System.out.print("Ingrese fila (0 a " + limiteFilas + "): ");
      int fila = orden.nextInt();
      System.out.print("Ingrese columna (0 a " + limiteColumnas + "): ");
      int columna = orden.nextInt();

      // Validación de Memoria Física (Crítico)
      if (fila < 0 || fila > limiteFilas || columna < 0 || columna > limiteColumnas) {
        System.out.println("Error fatal: Coordenadas fuera de los límites de la matriz.");
        continue; // Corta esta iteración y vuelve a pedir los datos
      }

      // Captura de Acción
      System.out.print("Acción a realizar (1: Descubrir, 2: Poner/Quitar Bandera): ");
      int accion = orden.nextInt();

      Celda celdaObjetivo = tablero.getCelda(fila, columna);

      // Procesamiento lógico
      if (accion == 1) {
        // Caso A: Intenta descubrir una celda bloqueada
        if (celdaObjetivo.getBandera()) {
          System.out.println("Denegado: La celda está protegida por una bandera.");
        }
        // Caso B: Condición de Derrota Inmediata
        else if (celdaObjetivo.getEsMina()) {
          celdaObjetivo.setEstaRevelada(true); // Se revela para el render final
          resultado = false;
          juegoActivo = false;
        }
        // Caso C: Expansión segura
        else {
          motor.descubrirCelda(fila, columna, tablero);
          // Verificación constante de resultado tras alterar el tablero
          if (motor.verificarResultado(tablero)) {
            resultado = true;
            juegoActivo = false;
          }
        }
      } else if (accion == 2) {
        motor.alternarBandera(fila, columna, tablero);
      } else {
        System.out.println("Error: Código de acción no reconocido.");
      }
    }

    // Fase de cierre y resultados
    long tiempoFinal = System.currentTimeMillis();
    int segundosJugados = (int) ((tiempoFinal - tiempoInicio) / 1000);

    System.out.println("\n=================================");
    // Revelar todo el tablero para la vista final
    for (int i = 0; i < tablero.getNivel().getNumFilas(); i++) {
      for (int j = 0; j < tablero.getNivel().getNumColumnas(); j++) {
        tablero.getCelda(i, j).setEstaRevelada(true);
      }
    }
    imprimirTablero(tablero);
    System.out.println("=================================");

    // Notificación de estado
    if (resultado) {
      System.out.println("¡SISTEMA LIMPIO! Has ganado la partida en " + segundosJugados + " segundos.");
    } else {
      System.out.println("¡DETONACIÓN! Has pisado una mina. Juego terminado.");
    }

    // Registro de la partida en el historial
    String nombreDificultad = nivelActual.getClass().getSimpleName();

    Partida nuevaPartida = new Partida(nombreDificultad, segundosJugados, resultado,
        nivelActual.getNumFilas(), nivelActual.getNumColumnas(), nivelActual.getNumMinas());

    // Inserción en el arreglo estático del historial
    gestorHistorial.agregarPartida(nuevaPartida);
  }

  // Metodo para imprimir el tablero en consola con los símbolos correspondientes
  public static void imprimirTablero(Tablero tablero) {
    int sizeFilas = tablero.getNivel().getNumFilas();
    int sizeColumnas = tablero.getNivel().getNumColumnas();

    // Dejo un margen al principio para que los números cuadren
    System.out.print("    ");

    // Imprimo los números de arriba (columnas). Les doy un espacio fijo a todos para que no se corran
    for (int j = 0; j < sizeColumnas; j++) {
      if (j < 10) {
        System.out.print(j + "  ");
      } else {
        System.out.print(j + " ");
      }
    }
    System.out.println();

    // Empiezo a dibujar el tablero fila por fila
    for (int i = 0; i < sizeFilas; i++) {
      // Imprimo el número de la fila a la izquierda, ajustando el espacio si tiene
      // dos cifras
      if (i < 10) {
        System.out.print(i + "   ");
      } else {
        System.out.print(i + "  ");
      }

      // Reviso qué hay en cada celda para saber qué dibujar
      for (int j = 0; j < sizeColumnas; j++) {
        Celda celdaActual = tablero.getCelda(i, j);

        // A todos los símbolos les pongo dos espacios al lado para que midan lo mismo
        // que los números de arriba
        if (celdaActual.getBandera()) {
          System.out.print("⚑  ");
        } else if (!celdaActual.getEstaRevelada()) {
          System.out.print("■  ");
        } else if (celdaActual.getEsMina()) {
          System.out.print("¤  ");
        } else if (celdaActual.getMinasAlrededor() == 0) {
          System.out.print("-  "); // Pongo un guion en vez de un 0 para que no confunda
        } else {
          System.out.print(celdaActual.getMinasAlrededor() + "  ");
        }
      }
      System.out.println(); // Salto de línea para pasar a la siguiente fila
    }
  }
}