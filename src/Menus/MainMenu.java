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
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        JLabel lblTitulo = new JLabel("Steam 2");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // 3. Panel de Juegos (Centro)
        JPanel panelJuegos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Espacio entre los cuadros

        // --- JUEGO 1: AJEDREZ ---
        // Pasa la ruta de la imagen como tercer argumento (ej: "/Recursos/icon_chess.png")
        // Si no tienes imagen aún, pasa null.
        JButton btnAjedrez = crearBotonJuego(null, Color.WHITE, "/Recursos/AjedrezIcon.png");
        btnAjedrez.addActionListener(e -> abrirAjedrez());
        panelJuegos.add(crearPanelJuego(btnAjedrez, "Ajedrez"), gbc);

        // --- JUEGO 2: SOLITARIO ---
        JButton btnSolitario = crearBotonJuego(null, Color.WHITE,"/Recursos/SolitarioIcon.png");
        btnSolitario.addActionListener(e -> abrirSolitario());
        panelJuegos.add(crearPanelJuego(btnSolitario, "Solitario"), gbc);

        // --- JUEGO 3: JUEGO Y ---
        JButton btnJuego3 = crearBotonJuego(null, Color.WHITE, "/Recursos/LudoIcon.jpg");
        btnJuego3.addActionListener(e -> abrirLudo());
        panelJuegos.add(crearPanelJuego(btnJuego3, "Ludo"), gbc);

        add(panelJuegos, BorderLayout.CENTER);
    }

    /**
     * Crea un botón cuadrado que soporta iconos.
     * @param rutaIcono La ruta dentro de 'src' (ej: "/Recursos/miImagen.png") o null.
     */
    private JButton crearBotonJuego(String texto, Color colorFondo, String rutaIcono) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(200, 200));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setFocusPainted(false);

        // Lógica para poner el icono si la ruta existe
        if (rutaIcono != null) {
            URL imgUrl = getClass().getResource(rutaIcono);
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                // Redimensionar la imagen para que no tape todo el botón (ej. 100x100)
                Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(scaledImage));

                // Acomodar texto e imagen (Imagen arriba, texto abajo)
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

    // =================================================================================
    //                            LOGICA PARA ABRIR JUEGOS
    // =================================================================================

    private void abrirAjedrez() {
        this.setVisible(false); // Ocultar el Hub

        JFrame ventanaJuego = new JFrame("Ajedrez - Partida");
        ventanaJuego.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Agregar listener para detectar cuando se cierra el juego
        ventanaJuego.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true); // Volver a mostrar el Hub
            }
        });

        Ajedrez.main.Tablero tableroAjedrez = new Ajedrez.main.Tablero();
        ventanaJuego.add(tableroAjedrez);
        ventanaJuego.pack();
        ventanaJuego.setLocationRelativeTo(null);
        ventanaJuego.setVisible(true);
    }

    private void abrirSolitario() {
        this.setVisible(false); // Ocultar el Hub

        // 1. Crear la ventana del juego
        JFrame ventanaJuego = new JFrame("Solitario - Partida");
        ventanaJuego.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaJuego.setSize(1000, 800); // Darle un tamaño inicial adecuado

        // 2. Configurar el evento para volver al menú al cerrar
        ventanaJuego.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true); // Volver a mostrar el Hub
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
        // Crear la instancia de la ventana del Ludo
        Ludo.Main ludoGame = new Ludo.Main();

        // Listener para volver al menú al cerrar
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