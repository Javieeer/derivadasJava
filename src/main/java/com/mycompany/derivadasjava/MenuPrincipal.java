package com.mycompany.derivadasjava;

import static com.mycompany.derivadasjava.DerivadasJava.ventanaDerivadasDobles;
import static com.mycompany.derivadasjava.DerivadasJava.ventanaDerivadasIndividuales;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPrincipal {

    private JFrame ventanaPrincipal;

    public MenuPrincipal() {
        ventanaPrincipal = new JFrame("Derivadas del mejor CIPAS");
        configurarVentana();
    }

    private void configurarVentana() {

        ventanaPrincipal.setSize(500, 400);
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLayout(new BorderLayout(20, 20));
        ventanaPrincipal.getContentPane().setBackground(new Color(245, 245, 245));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        ventanaPrincipal.add(panel, BorderLayout.CENTER);

        JLabel instrucciones = new JLabel("Menú principal");
        instrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        instrucciones.setForeground(new Color(33, 33, 33));
        panel.add(instrucciones);

        Dimension botonTamano = new Dimension(250, 45);

        JButton funcionSola = crearBoton("Derivar una función única", botonTamano);
        JButton devFunciones = crearBoton("Derivar dos funciones", botonTamano);
        JButton salir = crearBoton("Salir", botonTamano);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(funcionSola);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(devFunciones);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(salir);

        // Listeners
        funcionSola.addActionListener(e -> ventanaDerivadasIndividuales());
        devFunciones.addActionListener(e -> ventanaDerivadasDobles());
        salir.addActionListener(e -> ventanaPrincipal.dispose());

        ventanaPrincipal.setVisible(true);
    }

    private JButton crearBoton(String texto, Dimension tamano) {
        JButton boton = new JButton(texto);
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
        return boton;
    }
}
