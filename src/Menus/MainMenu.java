package Menus;

import Solitario.Clases.Mazo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class MainMenu extends JFrame {

    // Configuración de la ventana
    private static final int ANCHO = 1000;
    private static final int ALTO = 600;

    public MainMenu() {
        // 1. Configuración básica de la ventana
        setTitle("Steam 2 - Universidad Tecnológica del Perú");
        setSize(ANCHO, ALTO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());

        // 2. Panel del Título (Norte)
        // CAMBIO: Usamos BorderLayout para poder poner el botón a la derecha
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes

        // -- Título Central --
        JLabel lblTitulo = new JLabel("Steam 2", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));

        // -- Botón de Información (?) --
        JButton btnInfo = new JButton("?");
        btnInfo.setFont(new Font("Arial", Font.BOLD, 18));

        btnInfo.setPreferredSize(new Dimension(45, 45));
        btnInfo.setBackground(Color.WHITE);
        btnInfo.setForeground(Color.BLACK); // <--- CAMBIO AQUÍ (Texto negro)
        btnInfo.setFocusPainted(false);
        // Acción del botón
        btnInfo.addActionListener(e -> mostrarInformacion());

        // Panel auxiliar para el botón (para que no se estire)
        JPanel panelBotonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonContainer.setOpaque(false); // Transparente
        panelBotonContainer.add(btnInfo);

        // Agregamos componentes al panel de título
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        panelTitulo.add(panelBotonContainer, BorderLayout.EAST);

        add(panelTitulo, BorderLayout.NORTH);

        // 3. Panel de Juegos (Centro)
        JPanel panelJuegos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Espacio entre los cuadros

        // --- JUEGO 1: AJEDREZ ---
        JButton btnAjedrez = crearBotonJuego(null, Color.WHITE, "/Recursos/AjedrezIcon.png");
        btnAjedrez.addActionListener(e -> abrirAjedrez());
        panelJuegos.add(crearPanelJuego(btnAjedrez, "Ajedrez"), gbc);

        // --- JUEGO 2: SOLITARIO ---
        JButton btnSolitario = crearBotonJuego(null, Color.WHITE,"/Recursos/SolitarioIcon.png");
        btnSolitario.addActionListener(e -> abrirSolitario());
        panelJuegos.add(crearPanelJuego(btnSolitario, "Solitario"), gbc);

        // --- JUEGO 3: LUDO ---
        JButton btnJuego3 = crearBotonJuego(null, Color.WHITE, "/Recursos/LudoIcon.jpg");
        btnJuego3.addActionListener(e -> abrirLudo());
        panelJuegos.add(crearPanelJuego(btnJuego3, "Ludo"), gbc);

        add(panelJuegos, BorderLayout.CENTER);
    }

    // =================================================================================
    //                            MÉTODO NUEVO: INFO
    // =================================================================================

    private void mostrarInformacion() {
        String mensaje = "<html><center><h2>Proyecto Algoritmo y estructura de datos 1</h2>" +
                "<p><b>Universidad Tecnológica del Perú</b></p><br>" +
                "<p>Facultad de Ingenieria</p><br>" +
                "<p><b>Integrantes:</b></p>" +
                "<p> - Aron Fabrizio Bamberger Buleje</p>" +
                "<p> - Santiago Alonso Capcha Esteban</p>" +
                "<p> - Panta Inga, Bernabé Robert Junior </p>" +
                "</center></html>";

        JOptionPane.showMessageDialog(this,
                mensaje,
                "Acerca de Steam 2",
                JOptionPane.PLAIN_MESSAGE);
    }

    // =================================================================================
    //                            RESTO DEL CÓDIGO
    // =================================================================================

    private JButton crearBotonJuego(String texto, Color colorFondo, String rutaIcono) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(200, 200));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setFocusPainted(false);

        if (rutaIcono != null) {
            URL imgUrl = getClass().getResource(rutaIcono);
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(scaledImage));
                btn.setHorizontalTextPosition(SwingConstants.CENTER);
                btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            } else {
                System.out.println("No se encontró la imagen: " + rutaIcono);
            }
        }
        return btn;
    }

    private JPanel crearPanelJuego(JButton boton, String nombreJuego) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(boton, BorderLayout.CENTER);

        JLabel lblNombre = new JLabel(nombreJuego, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNombre.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(lblNombre, BorderLayout.SOUTH);

        return panel;
    }

    private void abrirAjedrez() {
        this.setVisible(false);
        JFrame ventanaJuego = new JFrame("Ajedrez - Partida");
        ventanaJuego.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaJuego.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
        Ajedrez.main.Tablero tableroAjedrez = new Ajedrez.main.Tablero();
        ventanaJuego.add(tableroAjedrez);
        ventanaJuego.pack();
        ventanaJuego.setLocationRelativeTo(null);
        ventanaJuego.setVisible(true);
    }

    private void abrirSolitario() {
        this.setVisible(false);
        JFrame ventanaJuego = new JFrame("Solitario - Partida");
        ventanaJuego.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaJuego.setSize(1000, 800);
        ventanaJuego.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
        Mazo mazo = new Mazo();
        mazo.crearColumnasPrincipales();
        mazo.crearColumnasSimbolos();
        mazo.crearColumnasExtras();
        mazo.crearCartas();
        mazo.distribuirCartas();
        CartaPanel panelSolitario = new CartaPanel(mazo);
        ventanaJuego.add(panelSolitario);
        ventanaJuego.setLocationRelativeTo(null);
        ventanaJuego.setVisible(true);
    }

    private void abrirLudo() {
        this.setVisible(false);
        Ludo.Main ludoGame = new Ludo.Main();
        ludoGame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}