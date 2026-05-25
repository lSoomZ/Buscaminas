[Readme.md](https://github.com/user-attachments/files/28204325/Readme.md)
# Buscaminas en Terminal — Java

> Proyecto final del curso de Pensamiento Computacional  
> Implementación del clásico juego Buscaminas, jugable desde la terminal.

---

## Integrantes

| Nombre | Usuario GitHub |
|--------|---------------|
| Nicolás Porras Rodríguez | @Nicolas-pr |
| Lucas Orrego Martínez | @iSoomZ |
| Juan Camilo Posada | @jposada32 |

---

## Los requisitos

- Java Development Kit (JDK) 11 o superior
- Visual Studio Code (o cualquier terminal)
- GitHUb

---

## ¿Cómo obtienes el proyecto?

### 1. Clonas el repositorio

Abre la terminal y ejecuta:

```bash
git clone https://github.com/lSoomZ/Buscaminas
```

### 2. Entras a la carpeta del proyecto

```bash
cd buscaminas-java
```

### 3. Lo abres en Visual Studio Code *(opcional)*

```bash
code .
```

---

## ¿Cómo lo compilas?

Desde la raíz del proyecto, ejecuta:

```bash
javac -d out -sourcepath src src/consola/Main.java
```

Esto compila todos los archivos y deposita los `.class` en la carpeta `out/`.

> Si la carpeta `out/` no existe, créala primero con `mkdir out`

---

## ¿Cómo lo ejecutas?

```bash
java -cp out consola.Main
```

---

## 📁 Estructura del proyecto

```
src/
├── 📂 consola/
│   └── Main.java               ← Punto de entrada, interfaz con el usuario
├── 📂 logica/
│   ├── MotorJuego.java         ← Lógica del juego (minas, cascada, banderas)
│   └── GestorHistorial.java    ← Historial, BubbleSort y BinarySearch
└── 📂 modelos/
    ├── Celda.java              ← Propiedades de cada casilla
    ├── Tablero.java            ← Cuadrícula del juego
    ├── NivelDificultad.java    ← Clase abstracta base
    ├── Principiante.java       ← 9x9, 10 minas
    ├── Intermedio.java         ← 16x16, 40 minas
    ├── Experto.java            ← 16x30, 99 minas
    ├── Personalizado.java      ← Dimensiones definidas por el jugador
    └── Partida.java            ← Registro de una partida terminada
```

---

## ¿Cómo se juega?

### 1. Menú principal

Al iniciar, el programa presenta las siguientes opciones:

```
\n--- BUSCAMINAS ---

1. Principiante  (9x9,   10 minas)
2. Intermedio    (16x16, 40 minas)
3. Experto       (16x30, 99 minas)
4. Personalizado
5. Ver historial de partidas
```

### 2. Durante la partida

El jugador ingresa la **fila** y la **columna** de la casilla que desea jugar, y luego elige entre:

- **Descubrir** la casilla
- **Colocar o quitar** una bandera ⚑

### 3. Fin de partida

Al terminar, el programa ofrece:

| Opción | Acción |
|--------|--------|
| 1 | Volver a jugar |
| 2 | Ver historial ordenado por tiempo |
| 3 | Salir del programa |

---

## Símbolos en el tablero

| Símbolo | Significado                      |
|---------|----------------------------------|
| `■`     | Celda no descubierta             |
| ` `     | Celda vacía (0 minas alrededor)  |
| `1`–`8` | Cantidad de minas vecinas        |
| `⚑`     | Bandera colocada por el jugador  |
| `¤`     | Mina (visible al terminar)       |

---

## Condiciones para el fin de partida

| Resultado | Condición |
|-----------|-----------|
| **Derrota** | El jugador descubre una casilla con mina |
| **Victoria** | El jugador descubre todas las casillas sin mina |

---

## Algoritmos que se implementaron

| Algoritmo | Ubicación | Uso |
|-----------|-----------|-----|
| **BubbleSort** | `GestorHistorial.java` | Ordena el historial de partidas por tiempo |
| **BinarySearch** | `GestorHistorial.java` | Busca una partida específica por tiempo |
| **Recursión** | `MotorJuego.java` | Descubrimiento en cascada de casillas vacías |

---

## Conceptos de POO aplicados

| Concepto | Clases involucradas |
|----------|-------------------|
| **Herencia** | `NivelDificultad` → `Principiante`, `Intermedio`, `Experto`, `Personalizado` |
| **Composición** | `Tablero` contiene `Celda[][]` |
| **Agregación** | `GestorHistorial` colecciona instancias de `Partida` |
| **Encapsulamiento** | Todos los atributos son `private`, accesibles por getters y setters |

---

> Entrega para el domingo 24 de Mayo de 2026 — Curso de Pensamiento Computacional
