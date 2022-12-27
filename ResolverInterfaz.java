/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Clases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Clases.Tablero;
import static java.awt.Color.blue;

/**
 *
 * @author esteb
 */
public class ResolverInterfaz extends javax.swing.JFrame {

    /**
     * Creates new form ResolverInterfaz
     */
    private int FILAS = 10;
    private int COLUMNAS = 10;
    private Tablero tablero_original = new Tablero(10,10);
    private Tablero tablero_abierto = new Tablero(10,10);
    private JButton[][] buttons;
    JFrame frame;
    
    public void centrarVentana()
    {
        this.setSize(500,525);
        Dimension tamañoMonitor = Toolkit.getDefaultToolkit().getScreenSize();
        int posicionX = (tamañoMonitor.width - 500) / 2;
        int posicionY = (tamañoMonitor.height - 525) / 2;
        this.setLocation(posicionX, posicionY);
    }
    
    public void partidaTerminada(int estado)
    {
        for (int i = 0; i<FILAS; i++)
        {
            for ( int j=0; j<COLUMNAS; j++)
            {
                buttons[i][j].setEnabled(false);
                if( buttons[i][j].getText() == "X"){
                    buttons[i][j].setForeground(Color.red);
                }
                else if ( tablero_abierto.verValor(i,j) == '9'){
                    //codigo para los botones no pulsado al terminar la partida
                    if (estado == 0){
                        buttons[i][j].setText("?");
                        buttons[i][j].setForeground(Color.black);
                    }else{
                        buttons[i][j].setText("F");
                        buttons[i][j].setForeground(blue);
                    }
                }else
                {
                    buttons[i][j].setForeground(Color.black);
                }
            }
        }
        new MensajeFinPartida( frame, estado).setVisible(true);
    }
    
    public void crearBotones()
    {
        int numButtons = 10;
        this.buttons = new JButton[numButtons][numButtons];
        int x = 10;  // Coordenada x inicial de los botones
        int y = 10;  // Coordenada y inicial de los botones
        int width = 40;  // Ancho de los botones
        int height = 40;  // Alto de los botones
        int gap = 5;  // Espacio entre los botones
        for (int j = 0; j< 10; j++)
        {
            for (int i = 0; i < 10; i++) {
              JButton button = new JButton("Boton " +j+i);
              button.setText("");
              button.setBounds(x, y, width, height);
              button.setMargin(new Insets(1, 1, 1, 1));
              button.setMinimumSize(new Dimension(width, height));
              button.setPreferredSize(new Dimension(width, height));
              buttons[j][i] = button;
              this.jPanel1.add(buttons[j][i]);

              // Actualizar la coordenada x para el siguiente botón
              x += width + gap;
              
            }
            jPanel1.setLayout(new FlowLayout());
            
        }
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                final int fila = j;
                final int columna = i;
                if (buttons[j][i] != null) {  // Verificar que el botón existe
                    buttons[j][i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    
                        int resultado = tablero_original.abrirCasilla(tablero_abierto, fila, columna);
                        tablero_abierto.mostrarTablero( buttons);
                        if (resultado == 1)
                        {
                            //partida terminada
                            partidaTerminada(0);
                        }else{
                            int resultadoPartida = tablero_abierto.comprobarEstadoPartida();
                            if (resultadoPartida == 1){
                                System.out.print("has ganado");
                                partidaTerminada(1);
                            }
                        }
                    }
                });
              }
            }
        }
    }
    
    public ResolverInterfaz() throws InterruptedException {
        centrarVentana();
        initComponents();
        
        //añadimos los botones
        crearBotones();
        
        //iniciamos el tablero
        
        tablero_original.inicializarTablero(0);
        tablero_original.generarMinas();
        tablero_original.generarNumeros();
        tablero_abierto.inicializarTablero(1);
        
        // Obtener una referencia al JFrame actual
        this.frame = (JFrame) SwingUtilities.getWindowAncestor(this.jPanel1);
        // Añadir el panel al JFrame
        frame.add(this.jPanel1);
        frame.setVisible(true);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 255, 255));

        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );

        jLabel1.setText("Buscaminas V0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
