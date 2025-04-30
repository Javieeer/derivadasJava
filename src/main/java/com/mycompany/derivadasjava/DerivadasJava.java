
package com.mycompany.derivadasjava;

/**
 *
 * @author javiz
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.*;

public class DerivadasJava {

    public static void main( String[] args ) {
        
        // Ventana principal
        JFrame ventanaPrincipal = new JFrame( "Derivadas del mejor Cipas" );
        ventanaPrincipal.setSize( 800, 300 );
        ventanaPrincipal.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        ventanaPrincipal.setLayout( new BorderLayout( 20, 20 ) );
        ventanaPrincipal.getContentPane().setBackground( new Color( 245, 245, 245 ) ); // Color claro para la ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) ); // Layout vertical
        panel.setBackground( new Color( 255, 255, 255 ) ); // Fondo blanco
        panel.setBorder( BorderFactory.createEmptyBorder( 40, 40, 40, 40 ) ); // Espacio alrededor

        // Agregamos a la ventana principal el panel creado
        ventanaPrincipal.add( panel, BorderLayout.CENTER );

        // Etiqueta de instrucciones
        JLabel instrucciones = new JLabel( "Ingrese la función para derivar:" );
        instrucciones.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        instrucciones.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        instrucciones.setForeground( new Color( 33, 33, 33 ) ); // Color gris oscuro
        panel.add( instrucciones );

        // Campo de texto para la función
        JTextField funcion = new JTextField( 20 );
        funcion.setFont( new Font( "Segoe UI", Font.PLAIN, 16 ) );
        funcion.setForeground( new Color( 33, 33, 33 ) );
        funcion.setPreferredSize( new Dimension( 300, 35 ) );
        funcion.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        funcion.setBorder( BorderFactory.createLineBorder( new Color( 0, 122, 204 ), 2, true ) ); // Borde azul claro
        panel.add( Box.createRigidArea( new Dimension( 0, 20 ) ) ); // Espacio entre los elementos
        panel.add( funcion );

        // Botón para calcular la derivada
        JButton calcularDerivada = new JButton( "Calcular derivada" );
        calcularDerivada.setFont( new Font( "Segoe UI", Font.BOLD, 16 ) );
        calcularDerivada.setBackground( new Color( 0, 122, 204 ) ); // Fondo azul
        calcularDerivada.setForeground( Color.WHITE ); // Texto blanco
        calcularDerivada.setFocusPainted( false );
        calcularDerivada.setPreferredSize( new Dimension( 200, 40 ) );
        calcularDerivada.setAlignmentX( Component.CENTER_ALIGNMENT );
        calcularDerivada.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        calcularDerivada.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) ); // Cambio de cursor
        panel.add( Box.createRigidArea( new Dimension( 0, 20 ) ) ); // Espacio entre los elementos
        panel.add( calcularDerivada );

        // Etiqueta para mostrar la derivada
        JLabel derivadaEtiqueta = new JLabel( "Derivada: " );
        derivadaEtiqueta.setFont( new Font( "Segoe UI", Font.PLAIN, 18 ) );
        derivadaEtiqueta.setAlignmentX( Component.CENTER_ALIGNMENT ); // Centrado
        derivadaEtiqueta.setForeground( new Color( 0, 122, 204 ) ); // Color azul
        panel.add( Box.createRigidArea( new Dimension( 0, 20 ) ) ); // Espacio entre los elementos
        panel.add( derivadaEtiqueta );

        // Acción del botón "Calcular derivada"
        calcularDerivada.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                String funcionEntrada = funcion.getText();  // Función ingresada por el usuario
                try {

                    // Hallamos la derivada
                    String derivada = derivarFuncion( funcionEntrada );

                    // Mostrar la derivada
                    derivadaEtiqueta.setText( "Derivada: " + derivada );
                    
                } catch ( Exception ex ) {
                    derivadaEtiqueta.setText( "Error en la función." );
                }
            }
        });

        ventanaPrincipal.setVisible( true );
    }

    // Método para derivar la función
    public static String derivarFuncion( String funcion ) {
        try {
            // Limpiamos la funcion
            funcion = funcion.replace( " ", "" );

            // Aseguramos que todos los términos tengan signo para poder dividir correctamente
            if ( funcion.charAt( 0 ) != '-' ) funcion = "+" + funcion;

            // Separamos términos y los metemos en un arreglo
            List<String> terminos = new ArrayList<>();
            StringBuilder actual = new StringBuilder();
            for ( int i = 0; i < funcion.length(); i++ ) {
                char caracter = funcion.charAt( i );
                if ( ( caracter == '+' || caracter == '-' ) && actual.length() > 0 ) {
                    terminos.add( actual.toString() ); // Guarda el término construido hasta ahora
                    actual = new StringBuilder(); // Limpia "actual" para empezar a construir el siguiente término
                }
                actual.append( caracter );
            }
            terminos.add( actual.toString() );

            // Creamos variable para almacenar la derivada de cada termino
            StringBuilder resultado = new StringBuilder();
            
            // Derivar cada término
            for ( String termino : terminos ) {
                termino = termino.trim(); // Elimina espacios en blanco
                if ( termino.isEmpty() ) continue; // Si el termino esta vacio lo salta y sigue con el siguiente

                String derivado = "";
                if ( !termino.contains( "x" ) ) {
                    // Es constante, derivada = 0
                    derivado = "";
                } else {
                    String sinConst = reglaDeConstante( termino );
                    derivado = reglaDePotencia( sinConst );

                    // Recuperar signo
                    if ( termino.charAt( 0 ) == '-' && !derivado.startsWith( "-" ) ) {
                        derivado = "-" + derivado;
                    } else if ( termino.charAt( 0 ) == '+' && !derivado.startsWith( "-" ) ) {
                        derivado = "+" + derivado;
                    } else if ( !derivado.startsWith( "-" ) && !derivado.startsWith( "+" ) ) {
                        derivado = "+" + derivado;
                    }
                }

                resultado.append( derivado ); // Se guardan los resultados en el arreglo por termino
            }

            // Limpieza final: eliminar primer '+' si lo hay
            String finalResultado = resultado.toString();
            if (finalResultado.startsWith("+")) {
                finalResultado = finalResultado.substring(1);
            }

            return finalResultado;

        } catch (Exception e) {
            return "Error en derivada.";
        }
    }
    
    // Metodo para encontrar las constantes en una funcion
    public static String encontrarConstantes( String funcion ){
        
        // Creamos la variable para almacenar la constante
        String constante = "";

        // Quitamos espacios
        funcion = funcion.replace( " ", "" );

        // Expresión regular para capturar cada término
        Pattern pattern = Pattern.compile( "[+-]?[^+-]+()" );
        Matcher matcher = pattern.matcher( funcion );

        while ( matcher.find() ) {
            String termino = matcher.group();
            // Si el término NO contiene la variable, lo consideramos constante
            if ( !termino.contains( "x" ) ) {
                constante = termino;
            }
        }
        
        return constante;
    }
    
    // Metodo para encontrar las potencias por ejemplo: 4x^2
    public static String encontrarPotencias( String funcion ) {
        
        String potencia = "";

        // Eliminamos espacios
        funcion = funcion.replace( " ", "" ); 

        // Detectamos potencias con coeficientes
        Pattern pattern = Pattern.compile( "[-+]?\\d*\\.?\\d*x\\^\\d+" );
        Matcher matcher = pattern.matcher( funcion );

        while ( matcher.find() ) {
            potencia = matcher.group();
        }

        return potencia;
    }
    
    // Metodo para encontrar potencias elevadas a 1 por ejemplo 5x
    public static String encontrarPotenciaSinExponente ( String funcion ){
        
        String potencia = "";

        // Eliminamos espacios
        funcion = funcion.replace( " ", "" ); 

        // Detectamos potencias a la 1 con coeficientes
        Pattern pattern = Pattern.compile( "[-+]?\\d*\\.?\\d*x(?!\\^)" );
        Matcher matcher = pattern.matcher( funcion );

        while ( matcher.find() ) {
            potencia = matcher.group();
        }
        
        return potencia;
    }
    
    // Metodo para evaluar regla de la constante
    public static String reglaDeConstante( String funcion ){
        
        // Volvemos a eliminar espacios en caso de tenerlos en la función
        funcion = funcion.replace( " ", "" );
        String constante = encontrarConstantes( funcion );
        
        // Limpiamos espacios de la constante en caso de tenerlos
        constante = constante.replace( " ", "" );
        String funcionSinConstante = funcion.replace( constante, "" );
        
        return funcionSinConstante;
    }
    
    // Metodo para evaluar la regla de potencia
    public static String reglaDePotencia( String funcion ) {
        
        funcion = funcion.replace( " ", "" ); // Limpieza

        if ( !funcion.contains( "x" ) ) return ""; // No es una potencia

        // Obtener coeficiente (antes de 'x')
        int xIndex = funcion.indexOf( "x" );
        String coefStr = funcion.substring( 0, xIndex );
        if ( coefStr.isEmpty() ) coefStr = "1";
        else if ( coefStr.equals( "+" ) ) coefStr = "1";
        else if ( coefStr.equals( "-" ) ) coefStr = "-1";

        double coef = Double.parseDouble( coefStr );

        // Obtener exponente (después de '^')
        int exp = 1;
        if ( funcion.contains( "^" ) ) {
            int powIndex = funcion.indexOf( "^" );
            String expStr = funcion.substring( powIndex + 1 );
            exp = Integer.parseInt( expStr );
        }

        double nuevoCoef = coef * exp;
        int nuevoExp = exp - 1;

        // Formatear resultado
        if ( nuevoExp == 0 ) {
            return String.valueOf( nuevoCoef );
        } else if ( nuevoExp == 1 ) {
            return formatearCoef( nuevoCoef ) + "x";
        } else {
            return formatearCoef( nuevoCoef ) + "x^" + nuevoExp;
        }
    }
    
    // Metodo para formatear coeficientes de potencias
    private static String formatearCoef(double coef) {
        if (coef == 1.0) return "";
        if (coef == -1.0) return "-";
        if (coef == (int) coef) return String.valueOf((int) coef);
        return String.valueOf(coef); // Para decimales
    }
}