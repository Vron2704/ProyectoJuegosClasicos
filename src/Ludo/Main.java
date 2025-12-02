package Ludo;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Tablero tablero;
    private JPanel panelControl;
    private JButton btnLanzarDado;
    private JLabel lblTurno;
    private JLabel lblDado;
    private JTextArea areaLog; // Para el registro de acciones

    private int valorDado = 0;
    private boolean dadoLanzado = false;

    public Main() {
        setTitle("Ludo");
        setSize(1100, 750); // Hacemos la ventana más ancha
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Inicializar tablero
        tablero = new Tablero(this);
        tablero.setBackground(Color.WHITE); // Fondo blanco para que resalte
        add(tablero, BorderLayout.CENTER);

        // 2. Panel de control (Lado Derecho - EAST)
        panelControl = new JPanel();
        panelControl.setPreferredSize(new Dimension(300, 0)); // Ancho fijo
        panelControl.setBackground(new Color(245, 245, 245));
        panelControl.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.LIGHT_GRAY));
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.Y_AXIS));

        // Componentes del panel
        lblTurno = new JLabel("Turno: ROJO");
        lblTurno.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTurno.setForeground(Color.RED);
        lblTurno.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel para el dado (Cuadro visual)
        lblDado = new JLabel("?");
        lblDado.setFont(new Font("Segoe UI", Font.BOLD, 60));
        lblDado.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        lblDado.setPreferredSize(new Dimension(100, 100));
        lblDado.setMaximumSize(new Dimension(100, 100));
        lblDado.setHorizontalAlignment(SwingConstants.CENTER);
        lblDado.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLanzarDado = new JButton("Tirar Dado");
        btnLanzarDado.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLanzarDado.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLanzarDado.addActionListener(e -> lanzarDado());

        // Area de registro (Log)
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Registro"));

        // Añadir espacios y componentes
        panelControl.add(Box.createVerticalStrut(30));
        panelControl.add(lblTurno);
        panelControl.add(Box.createVerticalStrut(20));
        panelControl.add(lblDado);
        panelControl.add(Box.createVerticalStrut(20));
        panelControl.add(btnLanzarDado);
        panelControl.add(Box.createVerticalStrut(30));
        panelControl.add(scrollLog);
        panelControl.add(Box.createVerticalStrut(20));

        add(panelControl, BorderLayout.EAST); // <--- AQUI ESTÁ EL CAMBIO CLAVE

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void lanzarDado() {
        if (dadoLanzado) return;

        valorDado = (int) (Math.random() * 6) + 1;
        lblDado.setText(String.valueOf(valorDado));
        log("Dado arrojó: " + valorDado);

        dadoLanzado = true;
        btnLanzarDado.setEnabled(false);

        if (!tablero.jugadorActualTieneMovimientos(valorDado)) {
            log("No hay movimientos posibles. Pasa turno.");
            // Timer pequeño para dar fluidez
            Timer t = new Timer(1500, e -> siguienteTurno());
            t.setRepeats(false);
            t.start();
        } else {
            log("Selecciona una ficha para mover.");
            tablero.repaint();
        }
    }

    public void finalizarMovimiento() {
        if (valorDado != 6) {
            siguienteTurno();
        } else {
            log("¡Sacaste 6! Repites turno.");
            resetDado();
        }
    }

    private void siguienteTurno() {
        tablero.cambiarTurno();
        Jugador j = tablero.getJugadorActual();
        lblTurno.setText("Turno: " + j.getNombre());
        lblTurno.setForeground(j.getColorAwt());
        log("--- Turno de " + j.getNombre() + " ---");
        resetDado();
    }

    private void resetDado() {
        dadoLanzado = false;
        valorDado = 0;
        lblDado.setText("?");
        btnLanzarDado.setEnabled(true);
    }

    public void log(String mensaje) {
        areaLog.append(mensaje + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    public int getValorDado() { return valorDado; }
    public boolean isDadoLanzado() { return dadoLanzado; }

    public static void main(String[] args) {
        // Configuramos la UI para que se vea más moderna
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(Main::new);
    }
}