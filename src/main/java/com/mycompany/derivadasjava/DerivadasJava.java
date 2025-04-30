
package com.mycompany.derivadasjava;

/**
 *
 * @author javiz
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            /*// Crear la expresión a partir de la función
            Expression exp = new ExpressionBuilder( funcion )
                    .variable( "x" )
                    .build();*/
            
            // Comenzamos a evaluar cada regla
            String funcionConstante = reglaDeConstante( funcion );
            String funcionPotencias = reglaDePotencia( funcionConstante );
            
            return funcionPotencias;
            
        } catch ( Exception e ) {
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
    public static String reglaDePotencia( String funcion ){
        
        // Limpiamos la función en caso de que tenga espacios
        funcion = funcion.replace( " ", "" );
        String potencia = encontrarPotencias( funcion );
        String copiaPotencia = potencia;
        String potenciaSinExpotente = "";
        String copiaPotenciaSinExpotente = "";
        System.out.println(!encontrarPotenciaSinExponente( funcion ).isEmpty());
        if ( !encontrarPotenciaSinExponente( funcion ).isEmpty() ){
            potenciaSinExpotente = encontrarPotenciaSinExponente( funcion );
            copiaPotenciaSinExpotente = potenciaSinExpotente;
        }
        
        
        //Limpiamos la potencia en caso de que tenga espacios
        potencia = potencia.replace( " ", "" );
        
        // Hallamos la potencia y la ponemos de primero reducida 1 unidad
        int posicionPotencia = funcion.indexOf("^") + 1;
        String exponente = potencia.substring( posicionPotencia, posicionPotencia + 1 );
        potencia = exponente + potencia.substring( 0, potencia.length() - 1 );
        int exponenteNum = Integer.parseInt(exponente) - 1;
        exponente = String.valueOf(exponenteNum);
        potencia = potencia + exponente;
        String potenciaTemp = potencia;
        
        // Recorremos la función para detectar números seguidos
        for ( int i = 0; i < potencia.length() - 1; i++ ) {
            
            char actual = potencia.charAt( i );
            char siguiente = potencia.charAt( i + 1 );
            
            // Evaluamos si ambos son números y estan seguidos
            if ( Character.isDigit( actual ) && Character.isDigit( siguiente ) ){
                // Creamos variable para el resultado
                int resultado = 1;

                // Multiplicamos siempre que hallan múmeros seguidos
                resultado *= Character.getNumericValue( potencia.charAt( i ) );
                resultado *= Character.getNumericValue( potencia.charAt( i + 1 ) );
                
                // Borramos los números de potencia
                int ubicaciontemp = potenciaTemp.indexOf( potencia );
                potenciaTemp = potenciaTemp.substring( ubicaciontemp + 2 );
                String resultadoTemp = String.valueOf( resultado );
                potencia = resultadoTemp + potenciaTemp;
                i++;
            }
        }
        
        // Evaluamos si la potencia es 1 entonces no la nombramos y dejamos la variable sola
        posicionPotencia = potencia.indexOf( "^" ) + 1;
        if ( potencia.charAt( posicionPotencia ) == '1' ){
            potencia = potencia.substring( 0, potencia.length() - 2 );
        }
        
        String funcionTemp = funcion.replace( copiaPotencia, potencia );
        
        // Tratamos potencias elexadas a la 1
        if ( !encontrarPotenciaSinExponente( funcion ).isEmpty() ){
            potenciaSinExpotente = potenciaSinExpotente.substring( 0, potenciaSinExpotente.length() - 1 );
            funcionTemp = funcionTemp.replace(copiaPotenciaSinExpotente, potenciaSinExpotente);
        }
        
        
        return funcionTemp;
    }
}