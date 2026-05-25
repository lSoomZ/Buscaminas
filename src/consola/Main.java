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

public class Main {
  public static void main(String[] args) {
    Scanner orden = new Scanner(System.in);

    // Creo el historial una sola vez al arrancar para que las partidas se acumulen
    // y no se borren
    GestorHistorial gestorHistorial = new GestorHistorial();

    // Esta variable mantiene todo el programa encendido hasta que decida salir
    boolean programaActivo = true;

    while (programaActivo) {
      NivelDificultad nivelActual = null;
      int opcion = 0;

      // Muestro el menú principal y obligo al jugador a elegir una opción válida
      do {
        System.out.println("\n--- BUSCAMINAS ---");
        System.out.println("1. Dificultad Principiante");
        System.out.println("2. Dificultad Intermedio");
        System.out.println("3. Dificultad Experto");
        System.out.println("4. Dificultad Personalizado");
        System.out.println("5. Ver el historial de partidas");
        System.out.print("Seleccione una de las opciones: ");

        opcion = orden.nextInt();

        // Dependiendo del número que ingrese, creo el nivel que corresponde
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

            // Calculo el máximo de minas permitidas para dejar al menos una casilla libre y
            // poder ganar
            int limiteMinas = (f * c) - 1;
            int m;

            // Si intenta poner más minas que el espacio disponible, le vuelvo a preguntar
            do {
              System.out.print("Ingrese número de minas (entre 1 y " + limiteMinas + "): ");
              m = orden.nextInt();

              if (m < 1 || m > limiteMinas) {
                System.out
                    .println("Error: No puedes poner esa cantidad de minas en un tablero de " + f + "x" + c + ".");
              }
            } while (m < 1 || m > limiteMinas);

            // Creo el nivel a la medida con los datos que ingresó
            nivelActual = new Personalizado(f, c, m);
            break;
          case 5:
            //Imprimo la lista de partidas ordenadas
            gestorHistorial.mostrarHistorial();
            break;

          default:
            System.out.println("Opción inválida. Ingrese un número del 1 al 4.");
            break;
        }
      } while (opcion < 1 || opcion > 4);

      // Preparo el tablero y dejo que el motor reparta las minas y calcule los
      // números
      Tablero tablero = new Tablero(nivelActual);
      MotorJuego motor = new MotorJuego();

      motor.distribuirMinas(tablero);
      motor.calcularMinasAdyacentes(tablero);

      System.out.println("¡Juego iniciado!");

      // Tomo el tiempo exacto en el que empieza la partida
      long tiempoInicio = System.currentTimeMillis();
      boolean juegoActivo = true;
      boolean resultado = false;

      // Este es el ciclo de la partida. Da vueltas hasta que gane o pise una mina
      while (juegoActivo) {

        System.out.println("\n=================================");
        imprimirTablero(tablero);
        System.out.println("=================================");

        // Saco los límites del tablero para avisarle al usuario hasta qué número puede
        // ingresar
        int limiteFilas = tablero.getNivel().getNumFilas() - 1;
        int limiteColumnas = tablero.getNivel().getNumColumnas() - 1;

        System.out.print("Ingrese fila (0 a " + limiteFilas + "): ");
        int fila = orden.nextInt();
        System.out.print("Ingrese columna (0 a " + limiteColumnas + "): ");
        int columna = orden.nextInt();

        // Reviso que las coordenadas de verdad existan para que el juego no tire error
        // y se caiga
        if (fila < 0 || fila > limiteFilas || columna < 0 || columna > limiteColumnas) {
          System.out.println("Error: Coordenadas fuera de los límites del tablero.");
          continue; // Vuelvo al principio del ciclo para pedir las coordenadas otra vez
        }

        System.out.print("Acción a realizar (1: Descubrir, 2: Poner/Quitar Bandera): ");
        int accion = orden.nextInt();

        // Saco la celda específica que el jugador quiere tocar
        Celda celdaObjetivo = tablero.getCelda(fila, columna);

        if (accion == 1) {
          // Si la celda tiene una bandera, bloqueo la acción para que no pierda por
          // accidente
          if (celdaObjetivo.getBandera()) {
            System.out.println("Denegado: La celda está protegida por una bandera.");
          }
          // Si tocó una mina sin bandera, revelo la mina y termina el juego perdiendo
          else if (celdaObjetivo.getEsMina()) {
            celdaObjetivo.setEstaRevelada(true);
            resultado = false;
            juegoActivo = false;
          }
          // Si es una celda segura, uso el motor para destaparla (y a sus vecinas si es
          // un 0)
          else {
            motor.descubrirCelda(fila, columna, tablero);

            // Después de cada movimiento, reviso si ya destapó todo lo necesario para ganar
            if (motor.verificarResultado(tablero)) {
              resultado = true;
              juegoActivo = false;
            }
          }
        } else if (accion == 2) {
          // Si elige bandera, el motor se encarga de ponerla o quitarla
          motor.alternarBandera(fila, columna, tablero);
        } else {
          System.out.println("Error: Código de acción no reconocido.");
        }
      }

      // --- FIN DE LA PARTIDA ---

      // Calculo los segundos exactos que tardó jugando
      long tiempoFinal = System.currentTimeMillis();
      int segundosJugados = (int) ((tiempoFinal - tiempoInicio) / 1000);

      System.out.println("\n=================================");

      // Destapo todo el tablero a la fuerza para que el jugador vea dónde estaban escondidas las minas
      for (int i = 0; i < tablero.getNivel().getNumFilas(); i++) {
        for (int j = 0; j < tablero.getNivel().getNumColumnas(); j++) {
          tablero.getCelda(i, j).setEstaRevelada(true);
        }
      }

      imprimirTablero(tablero);
      System.out.println("=================================");

      // Aviso si ganó o perdió
      if (resultado) {
        System.out.println("¡SISTEMA LIMPIO! Has ganado la partida en " + segundosJugados + " segundos.");
      } else {
        System.out.println("¡DETONACIÓN! Has pisado una mina. Juego terminado.");
      }

      // Saco el nombre de la clase (por ejemplo, "Principiante") para guardarlo en texto
      String nombreDificultad = nivelActual.getClass().getSimpleName();

      // Empaqueto todos los datos de lo que acaba de pasar en un nuevo objeto Partida
      Partida nuevaPartida = new Partida(nombreDificultad, segundosJugados, resultado,
          nivelActual.getNumFilas(), nivelActual.getNumColumnas(), nivelActual.getNumMinas());

      // Agrego la partida a la lista del historial
      gestorHistorial.agregarPartida(nuevaPartida);

      // Menú post-partida para decidir qué hacer a continuación
      int opcionSalida = 0;
      do {
        System.out.println("\n--- ¿QUÉ DESEAS HACER AHORA? ---");
        System.out.println("1. Volver a jugar");
        System.out.println("2. Ver el historial de partidas");
        System.out.println("3. Salir del programa");
        System.out.print("Seleccione una opción: ");
        opcionSalida = orden.nextInt();

        switch (opcionSalida) {
          case 1:
            //El ciclo principal volverá a arrancar
            break;
          case 2:
            // Imprimo la lista de partidas ordenadas
            gestorHistorial.mostrarHistorial();
            // Pongo la opción en 0 para obligar al menú a mostrarse de nuevo
            opcionSalida = 0;
            break;
          case 3:
            // Apago la variable principal para que el programa termine
            System.out.println("Gracias por jugar. Cerrando sistema.");
            programaActivo = false; 
            break;
          default:
            System.out.println("Opción inválida.");
            break;
        }
      } while (opcionSalida < 1 || opcionSalida > 3);
    }
  }

  // Metodo para imprimir el tablero en consola usando caracteres especiales
  public static void imprimirTablero(Tablero tablero) {
    int sizeFilas = tablero.getNivel().getNumFilas();
    int sizeColumnas = tablero.getNivel().getNumColumnas();

    //Espacio en blanco al inicio para alinear las columnas con la cuadricula
    System.out.print("    ");

    // Dibujo los números de la cabecera (las columnas)
    for (int j = 0; j < sizeColumnas; j++) {
      if (j < 10) {
        System.out.print(j + "  "); 
      } else {
        System.out.print(j + " ");
      }
    }
    System.out.println();

    for (int i = 0; i < sizeFilas; i++) {
      // Dibujo el número de la fila actual al lado izquierdo
      if (i < 10) {
        System.out.print(i + "   ");
      } else {
        System.out.print(i + "  ");
      }

      // Recorro todas las columnas de la fila actual
      for (int j = 0; j < sizeColumnas; j++) {
        Celda celdaActual = tablero.getCelda(i, j);

        // Reviso el estado de la celda y decido qué símbolo imprimir. A todos les sumo dos 
        // espacios vacíos para que cuadren perfecto con los números de arriba.
        if (celdaActual.getBandera()) {
          System.out.print("⚑  ");
        } else if (!celdaActual.getEstaRevelada()) {
          System.out.print("■  ");
        } else if (celdaActual.getEsMina()) {
          System.out.print("¤  ");
        } else if (celdaActual.getMinasAlrededor() == 0) {
          System.out.print("   "); 
        } else {
          System.out.print(celdaActual.getMinasAlrededor() + "  "); 
        }
      }
      System.out.println(); //Salto de línea al terminar la fila
    }
  }
}