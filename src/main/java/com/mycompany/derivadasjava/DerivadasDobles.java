/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.derivadasjava;

import static com.mycompany.derivadasjava.Calculadora.derivarFuncion;
import static com.mycompany.derivadasjava.DerivadasJava.reglaCo;
import static com.mycompany.derivadasjava.DerivadasJava.reglaDif;
import static com.mycompany.derivadasjava.DerivadasJava.reglaPro;
import static com.mycompany.derivadasjava.DerivadasJava.reglaSum;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;


/**
 *
 * @author javiz
 */
public class DerivadasDobles {
    
    public DerivadasDobles() {
        
        // Ventana principal
        JFrame ventanaPrincipal = new JFrame( "Derivadas Dobles" );
        ventanaPrincipal.setSize( 800, 550 );
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        ventanaPrincipal.setLayout( new BorderLayout( 20, 20 ) );
        ventanaPrincipal.getContentPane().setBackground( new Color( 245, 245, 245 ) ); // Color claro para la ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) ); // Layout vertical
        panel.setBackground( new Color( 255, 255, 255 ) ); // Fondo blanco
        panel.setBorder( BorderFactory.createEmptyBorder( 40, 40, 40, 40 ) ); // Espacio alrededor
        
        // Panel para organizar Derivada f(x)
        JPanel panelText = new JPanel();
        panelText.setLayout( new BoxLayout( panelText, BoxLayout.X_AXIS ) ); 
        panelText.setBackground( new Color( 255, 255, 255 ) ); // Fondo blanco
        panelText.setBorder( BorderFactory.createEmptyBorder( 40, 40, 40, 40 ) ); // Espacio alrededor
        
        // Panel para los botones en fila
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(new Color(255, 255, 255));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregamos a la ventana principal el panel creado
        ventanaPrincipal.add( panel, BorderLayout.CENTER );

        // Etiqueta de instrucciones
        JLabel instrucciones = new JLabel( "Ingrese la función f(x) y g(x) respectivamente" );
        instrucciones.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        instrucciones.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        instrucciones.setForeground( new Color( 33, 33, 33 ) ); // Color gris oscuro
        panel.add( instrucciones );

        // Campo de texto para f(x)
        JTextField funcionf = new JTextField( 20 );
        funcionf.setFont( new Font( "Segoe UI", Font.PLAIN, 16 ) );
        funcionf.setForeground( new Color( 33, 33, 33 ) );
        funcionf.setPreferredSize( new Dimension( 300, 35 ) );
        funcionf.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        funcionf.setBorder( BorderFactory.createLineBorder( new Color( 0, 122, 204 ), 2, true ) ); // Borde azul claro

        // Campo de texto para g(x)
        JTextField funciong = new JTextField( 20 );
        funciong.setFont( new Font( "Segoe UI", Font.PLAIN, 16 ) );
        funciong.setForeground( new Color( 33, 33, 33 ) );
        funciong.setPreferredSize( new Dimension( 300, 35 ) );
        funciong.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        funciong.setBorder( BorderFactory.createLineBorder( new Color( 0, 122, 204 ), 2, true ) ); // Borde azul claro
        
        // Creamos espacio entre los campos de texto
        panelText.add( funcionf );
        panelText.add( Box.createRigidArea( new Dimension( 20, 0 ) ) ); // Espacio entre los elementos
        panelText.add( funciong );
        
        // Agregamos los campos de texto al panel principal
        panel.add(panelText);
        
        // --- Botones ---
        Dimension botonTamano = new Dimension(250, 45);

        JButton calcularDerivada = new JButton("Calcular derivada");
        configurarBoton(calcularDerivada, botonTamano);

        JButton salir = new JButton("Salir");
        configurarBoton(salir, botonTamano);

        // Espaciado entre los botones
        panelBotones.add(calcularDerivada);
        panelBotones.add(Box.createRigidArea(new Dimension(20, 0)));
        panelBotones.add(salir);
        
        // Agregar el panel con botones al panel principal
        panel.add(panelBotones);
        
        // Etiqueta para mostrar la derivada f(x)
        JLabel derivada1 = new JLabel( "Derivada f(x): " );
        derivada1.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        derivada1.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        derivada1.setForeground( new Color( 0, 122, 204 ) ); // Color azul
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(derivada1);
        
        // Etiqueta para mostrar la derivada g(x)
        JLabel derivada2 = new JLabel( "Derivada g(x): " );
        derivada2.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        derivada2.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        derivada2.setForeground( new Color( 0, 122, 204 ) ); // Color azul
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(derivada2);
        
        // Etiqueta para mostrar la derivada f(x)+g(x)=f'(x)+g'(x)
        JLabel derivadaSuma = new JLabel( "Derivada f(x) + g(x): " );
        derivadaSuma.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        derivadaSuma.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        derivadaSuma.setForeground( new Color( 0, 122, 204 ) ); // Color azul
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(derivadaSuma);
        
        // Etiqueta para mostrar la derivada f(x)-g(x)=f'(x)-g'(x)
        JLabel derivadaDif = new JLabel( "Derivada f(x) - g(x): " );
        derivadaDif.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        derivadaDif.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        derivadaDif.setForeground( new Color( 0, 122, 204 ) ); // Color azul
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(derivadaDif);
        
        // Etiqueta para mostrar la derivada f(x)*g(x)=f(x)*g'(x)+g(x)*f'(x)
        JLabel derivadaProd = new JLabel( "Derivada f(x) * g(x): " );
        derivadaProd.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        derivadaProd.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        derivadaProd.setForeground( new Color( 0, 122, 204 ) ); // Color azul
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(derivadaProd);
        
        // Etiqueta para mostrar la derivada f(x)/g(x)=(g(x)*f'(x)-f(x)*g'(x))/(g(x))^2 siempre y cuando g(x) != 0
        JLabel derivadaCo = new JLabel( "Derivada f(x) / g(x): " );
        derivadaCo.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        derivadaCo.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        derivadaCo.setForeground( new Color( 0, 122, 204 ) ); // Color azul
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(derivadaCo);
        
        // Acción del botón "Calcular derivada"
        calcularDerivada.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                
                // Funciones ingresadas por el usuario
                String funcionfx = funcionf.getText();  
                String funciongx = funciong.getText();  
                try {

                    // Hallamos las derivadas
                    String derivadafx = derivarFuncion( funcionfx );
                    //System.out.println(derivadafx);
                    String derivadaGx = derivarFuncion( funciongx );
                    //System.out.println(derivadaGx);
                    String resulSum = reglaSum( derivadafx, derivadaGx ); // f'(x)+g'(x)
                    //System.out.println(resulSum);
                    String resulDif = reglaDif( derivadafx, derivadaGx ); // f'(x)-g'(x)
                    //System.out.println(resulDif);
                    String resulPro = reglaPro( funcionfx, derivadaGx, funciongx, derivadafx ); // f(x)*g'(x)+g(x)*f'(x)
                    //System.out.println(resulPro);
                    String resulCo = reglaCo( funcionfx, derivadaGx, funciongx, derivadafx ); // (g(x)*f'(x)-f(x)*g'(x))/(g(x))^2 siempre y cuando g(x) != 0
                    //System.out.println(resulCo);
                    
                    // Mostramos las derivadas
                    derivada1.setText( "Derivada f(x): " + derivadafx );
                    derivada2.setText( "Derivada g(x): " + derivadaGx );
                    derivadaSuma.setText( "Derivada f(x)+g(x): " + resulSum );
                    derivadaDif.setText( "Derivada f(x)-g(x): " + resulDif );
                    derivadaProd.setText( "Derivada f(x)*g(x): " + resulPro );
                    derivadaCo.setText( "Derivada f(x)/g(x): " + resulCo );
                    
                } catch ( Exception ex ) {
                    derivada1.setText( "Error en la función." );
                }
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ){
                // Cerramos la ventana
                ventanaPrincipal.dispose();
            }
        });
        
        ventanaPrincipal.setVisible( true );
    }
    
    // Método para aplicar estilo común a los botones
    private void configurarBoton(JButton boton, Dimension tamano) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(0, 122, 204));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(tamano);
        boton.setMaximumSize(tamano);
        boton.setMinimumSize(tamano);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
