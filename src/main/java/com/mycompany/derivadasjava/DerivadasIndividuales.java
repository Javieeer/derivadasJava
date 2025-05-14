package com.mycompany.derivadasjava;

import static com.mycompany.derivadasjava.Calculadora.derivarFuncion;
import java.awt.*;
import javax.swing.*;

/**
 * Ventana para calcular derivadas individuales.
 * Autor: javiz
 */
public class DerivadasIndividuales {

    public DerivadasIndividuales() {

        // --- Ventana principal ---
        JFrame ventanaPrincipal = new JFrame("Derivadas individuales");
        ventanaPrincipal.setSize(800, 380);
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLayout(new BorderLayout(20, 20));
        ventanaPrincipal.getContentPane().setBackground(new Color(245, 245, 245));

        // --- Panel principal ---
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        ventanaPrincipal.add(panel, BorderLayout.CENTER);

        // --- Instrucciones ---
        JLabel instrucciones = new JLabel("Ingrese la función para derivar:");
        instrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        instrucciones.setForeground(new Color(33, 33, 33));
        panel.add(instrucciones);

        // --- Campo de texto ---
        JTextField campoFuncion = new JTextField(20);
        campoFuncion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoFuncion.setForeground(new Color(33, 33, 33));
        campoFuncion.setPreferredSize(new Dimension(300, 35));
        campoFuncion.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoFuncion.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 204), 2, true));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(campoFuncion);

        // --- Botones ---
        Dimension botonTamano = new Dimension(250, 45);

        JButton calcularBtn = new JButton("Calcular derivada");
        configurarBoton(calcularBtn, botonTamano);

        JButton salirBtn = new JButton("Salir");
        configurarBoton(salirBtn, botonTamano);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotones.add(calcularBtn);
        panelBotones.add(Box.createRigidArea(new Dimension(20, 0)));
        panelBotones.add(salirBtn);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(panelBotones);

        // --- Etiqueta de resultado ---
        JLabel etiquetaDerivada = new JLabel("Derivada: ");
        etiquetaDerivada.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        etiquetaDerivada.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaDerivada.setForeground(new Color(0, 122, 204));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(etiquetaDerivada);

        // --- Listeners ---
        calcularBtn.addActionListener(e -> {
            String funcionEntrada = campoFuncion.getText().trim();
            if (funcionEntrada.isEmpty()) {
                etiquetaDerivada.setText("Por favor ingrese una función.");
                return;
            }

            try {
                String derivada = derivarFuncion(funcionEntrada);
                etiquetaDerivada.setText("Derivada: " + derivada);
            } catch (Exception ex) {
                etiquetaDerivada.setText("Error al derivar. Verifique la función.");
            }
        });

        salirBtn.addActionListener(e -> ventanaPrincipal.dispose());

        ventanaPrincipal.setVisible(true);
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
