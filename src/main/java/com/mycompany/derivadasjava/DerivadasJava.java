
package com.mycompany.derivadasjava;

/**
 *
 * @author javiz
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class DerivadasJava {

    public static void main(String[] args) {
        
        // Ventana principal
        JFrame ventanaPrincipal = new JFrame("Derivadas del mejor Cipas");
        ventanaPrincipal.setSize(800, 600);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLayout(new BorderLayout(20, 20));
        ventanaPrincipal.getContentPane().setBackground(new Color(245, 245, 245)); // Color claro para la ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Layout vertical
        panel.setBackground(new Color(255, 255, 255)); // Fondo blanco
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Espacio alrededor

        ventanaPrincipal.add(panel, BorderLayout.CENTER);

        // Etiqueta de instrucciones
        JLabel instrucciones = new JLabel("Ingrese la función para derivar:");
        instrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instrucciones.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        instrucciones.setForeground(new Color(33, 33, 33)); // Color gris oscuro
        panel.add(instrucciones);

        // Campo de texto para la función
        JTextField funcion = new JTextField(20);
        funcion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        funcion.setForeground(new Color(33, 33, 33));
        funcion.setPreferredSize(new Dimension(300, 35));
        funcion.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        funcion.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 204), 2, true)); // Borde azul claro
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre los elementos
        panel.add(funcion);

        // Botón para calcular la derivada
        JButton calcularDerivada = new JButton("Calcular derivada");
        calcularDerivada.setFont(new Font("Segoe UI", Font.BOLD, 16));
        calcularDerivada.setBackground(new Color(0, 122, 204)); // Fondo azul
        calcularDerivada.setForeground(Color.WHITE); // Texto blanco
        calcularDerivada.setFocusPainted(false);
        calcularDerivada.setPreferredSize(new Dimension(200, 40));
        calcularDerivada.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcularDerivada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        calcularDerivada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cambio de cursor
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre los elementos
        panel.add(calcularDerivada);

        // Etiqueta para mostrar la derivada
        JLabel derivadaEtiqueta = new JLabel("Derivada: ");
        derivadaEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        derivadaEtiqueta.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        derivadaEtiqueta.setForeground(new Color(0, 122, 204)); // Color azul
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre los elementos
        panel.add(derivadaEtiqueta);

        // Acción del botón "Calcular derivada"
        calcularDerivada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String funcionEntrada = funcion.getText();  // Función ingresada por el usuario
                try {
                    // Crear la expresión a partir de la función
                    Expression exp = new ExpressionBuilder(funcionEntrada)
                            .variable("x")
                            .build();

                    // Derivada de la función (usando una lógica ficticia por ahora)
                    String derivada = derivarFuncion(funcionEntrada);

                    // Mostrar la derivada
                    derivadaEtiqueta.setText("Derivada: " + derivada);
                } catch (Exception ex) {
                    derivadaEtiqueta.setText("Error en la función.");
                }
            }
        });

        ventanaPrincipal.setVisible(true);
    }

    // Método para derivar la función (por ahora devuelve un texto fijo)
    public static String derivarFuncion(String funcion) {
        try {
            // Crear la expresión a partir de la función
            Expression exp = new ExpressionBuilder(funcion)
                    .variable("x")
                    .build();

            // Aquí va la lógica para calcular la derivada, por ahora se devuelve un texto fijo
            return "f'(x) = 2x"; // Ficticia, reemplázala por la lógica real
        } catch (Exception e) {
            return "Error en derivada.";
        }
    }
}