/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;

public class Tablero {
    private int FILAS;
    private int COLUMNAS;
    private char[][] tablero;

    public Tablero(int filas, int columnas) {
        this.FILAS = filas;
        this.COLUMNAS = columnas;
        this.tablero = new char[filas][columnas];
    }

    public void añadir(int fila, int columna, char valor) {
        this.tablero[fila][columna] = valor;
    }

    public char verValor(int fila, int columna) {
        return this.tablero[fila][columna];
    }
    /*
    public void mostrarTablero() {
        // Muestra el tablero
        for (int fila = 0; fila < this.FILAS; fila++) {
            System.out.print("      ");
            for (int columna = 0; columna < this.COLUMNAS; columna++) {
                char salida = this.tablero[fila][columna];
                if (salida == '9') {
                    char valor = 254; //220
                    System.out.print(valor + " ");
                } else {
                    System.out.print(this.tablero[fila][columna] + " ");
                }
            }
            System.out.println();
        }
    }
    */
    
    public void mostrarTablero(JButton[][] buttons) {
    // Muestra el tablero en los botones correspondientes
        for (int fila = 0; fila < this.FILAS; fila++) {
            for (int columna = 0; columna < this.COLUMNAS; columna++) {
                char salida = this.tablero[fila][columna];
                if (salida == '9') {
                    char valor = 254; //220
                    //buttons[fila][columna].setText(String.valueOf(valor));
                    buttons[fila][columna].setText("");
                }else if (salida == '0'){
                    buttons[fila][columna].setText("");
                    buttons[fila][columna].setBackground(Color.white);
                }else if (salida == 'X'){
                    buttons[fila][columna].setText("X");;
                    buttons[fila][columna].setBackground(Color.red);
                } 
                else {
                    buttons[fila][columna].setText(String.valueOf(this.tablero[fila][columna]));
                }
            }
        }
    }

    public void generarMinas() {
        // Inicializa el generador de números aleatorios con la hora del sistema como semilla
        Random rand = new Random();
        // Genera aleatoriamente las minas en el tablero
        for (int i = 0; i < 10; i++) {
            int filaAleatoria = rand.nextInt(this.FILAS);
            int columnaAleatoria = rand.nextInt(this.COLUMNAS);
            this.tablero[filaAleatoria][columnaAleatoria] = 'X';
        }
    }
    
    public void generarNumeros() {
        // Generamos los números de las casillas dependiendo de las minas
        for (int fila = 0; fila < this.FILAS; fila++) {
            for (int columna = 0; columna < this.COLUMNAS; columna++) {
                if (this.tablero[fila][columna] != 'X') {
                    int contador = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (0 <= fila + i && fila + i < this.FILAS && 0 <= columna + j && columna + j < this.COLUMNAS) {
                                if (this.tablero[fila + i][columna + j] == 'X') {
                                    contador++;
                                }
                            }
                        }
                    }
                    this.tablero[fila][columna] = (char)(contador + '0');
                }
            }
        }
    }
    
    public int comprobarEstadoPartida()
    {
        //usar esta funcion en el tablero_abierto
        int contador =0;
        for (int i=0; i<FILAS; i++){
            for (int j=0; j<COLUMNAS; j++){
                if (tablero[i][j] == '9'){
                    contador++;
                }
            }
        }
        if (contador == 10){
            return 1; //partida finalizada, el jugador gana
        }else{
            return 0;
        }
    }

    public void inicializarTablero(int estado) {
        char valor;
        if (estado == 0) { // si estado = 0 se inicializa con valores vacíos ' '
            valor = ' ';
        } else { // si estado = 1 se inicializa con valores ascii = 211
            valor = '9';
        }
        for (int fila = 0; fila < this.FILAS; fila++) {
            for (int columna = 0; columna < this.COLUMNAS; columna++) {
                this.tablero[fila][columna] = valor;
            }
        }
    }
    
    public int abrirCasilla(Tablero tableroAbierto, int fila, int columna) {
        tableroAbierto.añadir(fila, columna, this.tablero[fila][columna]);
        if (this.tablero[fila][columna] == 'X') {
            // La casilla es una mina, informa al usuario y finaliza la ejecución de la función
            return 1;
        }

        // Calcula el número de minas adyacentes y asigna ese valor a la casilla
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (0 <= fila + i && fila + i < this.FILAS && 0 <= columna + j && columna + j < this.COLUMNAS) {
                    if (this.tablero[fila + i][columna + j] == 'X') {
                        contador++;
                    }
                }
            }
        }
        // Si la casilla no tiene minas adyacentes, abre las casillas adyacentes de forma recursiva
        if (contador == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (0 <= fila + i && fila + i < this.FILAS && 0 <= columna + j && columna + j < this.COLUMNAS) {
                        if (tableroAbierto.verValor(fila + i, columna + j) == '9') {
                            // comprobamos que no se ha abierto la casilla previamente
                            abrirCasilla(tableroAbierto, fila + i, columna + j);
                        }
                    }
                }
            }
        } else {
            // Asignamos valor a la casilla en el tablero abierto
            return 0;
        }
        return 0;
    }

}

    
                         
