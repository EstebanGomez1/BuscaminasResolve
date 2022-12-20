#include <iostream>
#include <cstdlib>
#include <ctime>
#include <thread>
#include <windows.h>


using namespace std;

const int FILAS = 10;
const int COLUMNAS = 10;

char tablero_abierto[FILAS][COLUMNAS];
// Crea una matriz de caracteres para representar el tablero del juego
char tablero[FILAS][COLUMNAS];


void mostrarTablero( char tablero[FILAS][COLUMNAS] )
{
    // Muestra el tablero
    for (int fila = 0; fila < FILAS; fila++) {
        printf("      ");
        for (int columna = 0; columna < COLUMNAS; columna++) {
            char salida = tablero[fila][columna];
            if ( salida == '9'){
                char valor = 254; //220
                cout << valor << " ";
            }else{
            cout << tablero[fila][columna] << " ";
            }
        }
        cout << endl;
    }
}

void generarMinas( char tablero[FILAS][COLUMNAS] )
{
    // Genera aleatoriamente las minas en el tablero
    for (int i = 0; i < 10; i++) {
        int filaAleatoria = rand() % FILAS;
        int columnaAleatoria = rand() % COLUMNAS;
        tablero[filaAleatoria][columnaAleatoria] = '*';
    }
}

void generarNumeros( char tablero[FILAS][COLUMNAS])
{
    //Generamos los numero de las casillas dependiendo de las minas
    for (int fila = 0; fila < FILAS; fila++) {
        for (int columna = 0; columna < COLUMNAS; columna++) {
            if (tablero[fila][columna] != '*') {
                int contador = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (0 <= fila + i && fila + i < FILAS && 0 <= columna + j && columna + j < COLUMNAS) {
                            if (tablero[fila + i][columna + j] == '*') {
                                contador++;
                            }
                        }
                    }
                }
                tablero[fila][columna] = contador + '0';
            }
        }
    }
}

void inicializarTablero( char tablero[FILAS][COLUMNAS], int estado )
{
    char valor;
    if ( estado == 0 ) //si estado = 0 se inicializa con valores vacios ' '
    {
        valor = ' ';
    }else //si estado = 1 se inicializa con valores ascii = 211
    {
        valor = '9';
    }
    for (int fila = 0; fila < FILAS; fila++) {
        for (int columna = 0; columna < COLUMNAS; columna++) {
            tablero[fila][columna] = valor;
        }
    }
}

int abrirCasilla( char tablero[FILAS][COLUMNAS], char tablero_abierto[FILAS][COLUMNAS], int fila, int columna)
{
    tablero_abierto[fila][columna] = tablero[fila][columna];
    if (tablero[fila][columna] == '*') 
    {
        // La casilla es una mina, informa al usuario y finaliza la ejecución de la función
        return 1;
    }
    
    
    // Calcula el número de minas adyacentes y asigna ese valor a la casilla
    int contador = 0;
    for (int i = -1; i <= 1; i++) 
    {
        for (int j = -1; j <= 1; j++) 
        {
            if (0 <= fila + i && fila + i < FILAS && 0 <= columna + j && columna + j < COLUMNAS) 
            {
                if (tablero[fila + i][columna + j] == '*')
                {
                    contador++;
                }
            }
        }
    }
    // Si la casilla no tiene minas adyacentes, abre las casillas adyacentes de forma recursiva
    if (contador == 0) 
    {
        for (int i = -1; i <= 1; i++) 
        {
            for (int j = -1; j <= 1; j++)
            {              
                if (0 <= fila + i && fila + i < FILAS && 0 <= columna + j && columna + j < COLUMNAS)
                {
                    if ( tablero_abierto[fila+i][columna+j] == '9')
                    {
                        //comprobamos que no se ha abierto la casilla previamente
                        abrirCasilla(tablero, tablero_abierto, fila +i, columna+j);
                    }
                }           
            }
        }
    }else
    {
        // Asignamos valor a la casilla en el tablero abierto    
        return 0;
    }
    return 0;
}

int main() {
    // Inicializa el generador de números aleatorios con la hora del sistema como semilla
    srand(time(NULL));

    

    // Inicializa el tablero con caracteres vacíos
    inicializarTablero( tablero, 0);

    //Generamos las minas
    generarMinas( tablero );
    //Generamos los numeros
    generarNumeros( tablero );
    //Mostramos el tablero por pantalla
    //mostrarTablero( tablero );

    //Mostramos el tablero oculto
    inicializarTablero( tablero_abierto, 1);
    //mostrarTablero( tablero_abierto );
    
    //Abrimos el tablero
    //printf("\n abrimos tablero: \n");
    //abrirCasilla( tablero, tablero_abierto, 3, 3 );
    //mostrarTablero( tablero_abierto );

    //bucle principal
    bool exit = false;
    while(!exit)
    {   std::system("cls");
        printf("\n ============= tablero ============== \n");
        mostrarTablero( tablero );
        int fila =-1, columna=-1;
        std::this_thread::sleep_for(std::chrono::seconds(1));
        printf("\n ==================================== \n");
        mostrarTablero( tablero_abierto);
        //Pedimos introducir fila o exit
        printf("\n -- Introduzca 0 para salir -- \n");
        printf("Introduzca columna\n");
        cin >> columna;
        printf("Introduzca fila \n");
        cin >> fila;
        fila--;
        columna--;
        if( fila == -1 || columna == -1 )
        {
            //salimos del bucle
            exit = true;
        }
        else if (fila < 0 || fila > FILAS || columna < 0|| columna > COLUMNAS)
        {
            printf("Coordenadas no validas");
        }
        else
        {
            if( abrirCasilla( tablero, tablero_abierto, fila, columna) == 1)
            {
                cout << "Has perdido el juego" << endl;
                mostrarTablero( tablero_abierto );
                exit = true;
            }
        }
    }
    char salir;
    cin >> salir;
    return 0;
}
