package Menus;

import Solitario.Clases.Carta;
import Solitario.Clases.Columna;
import Solitario.Clases.Logica;
import Solitario.Clases.Mazo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

public class CartaPanel extends JPanel {

    private Carta cartaSeleccionada = null;
    private Columna columnaOrigen = null;
    private int offsetX, offsetY;
    private int dragX, dragY;
    private Image iconCorazon, iconEspada, iconTrebol, iconDiamante;
    private int indiceCartaSeleccionada = -1;
    private final List<Carta> cartasArrastradas = new ArrayList<>();
    private int offsetYGrupo = 0;
    private final Mazo mazo;

    private boolean juegoTerminado = false;

    private JTextArea areaMensajes;

    public CartaPanel(Mazo mazo) {
        this.mazo = mazo;

        try {
            iconCorazon = ImageIO.read(getClass().getResource("/Recursos/IconoCorazon.png"));
            iconEspada  = ImageIO.read(getClass().getResource("/Recursos/IconoEspada.png"));
            iconTrebol  = ImageIO.read(getClass().getResource("/Recursos/IconoTrebol.png"));
            iconDiamante = ImageIO.read(getClass().getResource("/Recursos/IconoDiamante.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(new Color(0, 100, 0));
        setLayout(null);

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);
        areaMensajes.setBackground(new Color(240, 240, 240));

        JScrollPane scroll = new JScrollPane(areaMensajes);
        scroll.setBounds(1000, 20, 260, 600);
        scroll.setBorder(BorderFactory.createTitledBorder("Mensajes"));
        add(scroll);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (juegoTerminado) return;
                manejarMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (juegoTerminado) return;
                manejarMouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (juegoTerminado) return;
                manejarMouseDragged(e);
            }
        });

        setDoubleBuffered(true);
    }

    public void log(String msg) {
        areaMensajes.append(msg + "\n");
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cartaAncho = 80;
        int cartaAlto = 120;
        int columnaEspaciado = 60;
        int margenIzquierda = 40;
        int margenSuperior1 = 160;
        int margenSuperior2 = 20;

        Map<String, Columna> columnas = mazo.getColumnas();

        for (int i = 1; i <= 7; i++) {
            Columna col = columnas.get("Columna " + i);
            int x = margenIzquierda + (i - 1) * (cartaAncho + columnaEspaciado);
            int y = margenSuperior1;
            int offset = 25;

            for (int c = 0; c < col.getStackCarta().size(); c++) {
                Carta carta = col.getStackCarta().get(c);
                Image img = carta.getEsBocaArriba() ?
                            carta.getImagenBocaArriba() :
                            carta.getImagenBocaAbajo();
                g.drawImage(img, x, y + c * offset, cartaAncho, cartaAlto, this);
            }
        }

        for (int i = 1; i <= 2; i++) {
            Columna col = columnas.get("Columna extra " + i);
            int x = margenIzquierda + (i - 1) * (cartaAncho + columnaEspaciado);
            int y = margenSuperior2;

            if (col.getStackCarta().isEmpty()) {
                g.drawRect(x, y, cartaAncho, cartaAlto);
            } else {
                Carta carta = col.getStackCarta().peek();
                Image img = carta.getEsBocaArriba() ? carta.getImagenBocaArriba() : carta.getImagenBocaAbajo();
                g.drawImage(img, x, y, cartaAncho, cartaAlto, this);
            }
        }

        String[] palos = {"CORAZON", "ESPADA", "TREBOL", "DIAMANTE"};
        for (int i = 0; i < 4; i++) {
            Columna col = columnas.get("Columna " + palos[i]);
            int x = margenIzquierda + (i + 3) * (cartaAncho + columnaEspaciado);
            int y = margenSuperior2;

            if (col.getStackCarta().isEmpty()) {
                g.drawRect(x, y, cartaAncho, cartaAlto);
                Image icon = switch (i) {
                    case 0 -> iconCorazon;
                    case 1 -> iconEspada;
                    case 2 -> iconTrebol;
                    default -> iconDiamante;
                };
                if (icon != null) {
                    g.drawImage(icon, x + 20, y + 40, 40, 40, this);
                }
            } else {
                Carta carta = col.getStackCarta().peek();
                Image img = carta.getEsBocaArriba() ? carta.getImagenBocaArriba() : carta.getImagenBocaAbajo();
                g.drawImage(img, x, y, cartaAncho, cartaAlto, this);
            }
        }

        if (!cartasArrastradas.isEmpty()) {
            int offset = 25;
            int yy = dragY;
            for (Carta c : cartasArrastradas) {
                Image img = c.getEsBocaArriba() ?
                            c.getImagenBocaArriba() :
                            c.getImagenBocaAbajo();
                g.drawImage(img, dragX, yy, 80, 120, this);
                yy += offset;
            }
        }
    }

    private void manejarMousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        int cartaAncho = 80;
        int cartaAlto = 120;
        int columnaEspaciado = 60;
        int margenIzquierda = 40;
        int margenSuperior2 = 20;

        Map<String, Columna> columnas = mazo.getColumnas();

        Columna extra1 = columnas.get("Columna extra 1");
        Columna extra2 = columnas.get("Columna extra 2");

        int xExtra1 = margenIzquierda;
        int xExtra2 = margenIzquierda + (cartaAncho + columnaEspaciado);
        int yExtra  = margenSuperior2;

        if (mx >= xExtra1 && mx <= xExtra1 + cartaAncho &&
            my >= yExtra && my <= yExtra + cartaAlto) {

            if (extra1.getStackCarta().isEmpty()) {
                reciclarExtra2aExtra1(extra1, extra2);
                log("Se recicló extra 2 a extra 1.");
            } else {
                moverTopDeExtra1aExtra2(extra1, extra2);
                log("Se movió carta de extra 1 a extra 2.");
            }

            repaint();
            return;
        }

        if (mx >= xExtra2 && mx <= xExtra2 + cartaAncho &&
            my >= yExtra && my <= yExtra + cartaAlto) {

            if (extra2.getStackCarta().isEmpty()) {
                moverTopDeExtra1aExtra2(extra1, extra2);
                log("Se movió carta de extra 1 a extra 2.");
                repaint();
                return;
            }

            Carta carta = extra2.getStackCarta().peek();
            if (!carta.getEsBocaArriba()) return;

            cartaSeleccionada = carta;
            columnaOrigen = extra2;
            indiceCartaSeleccionada = extra2.getStackCarta().size() - 1;

            offsetX = mx - xExtra2;
            offsetY = my - yExtra;
            dragX = mx - offsetX;
            dragY = my - offsetY;

            cartasArrastradas.clear();
            cartasArrastradas.add(carta);

            repaint();
            return;
        }

        int margenSuperior1 = 160;

        for (int i = 1; i <= 7; i++) {
            int x = margenIzquierda + (i - 1) * (cartaAncho + columnaEspaciado);
            int y = margenSuperior1;

            Columna col = columnas.get("Columna " + i);
            int offset = 25;

            for (int c = col.getStackCarta().size() - 1; c >= 0; c--) {
                int cy = y + c * offset;

                if (mx >= x && mx <= x + cartaAncho &&
                    my >= cy && my <= cy + cartaAlto) {

                    Carta carta = col.getStackCarta().get(c);
                    if (!carta.getEsBocaArriba()) return;

                    cartaSeleccionada = carta;
                    columnaOrigen = col;
                    indiceCartaSeleccionada = c;

                    cartasArrastradas.clear();
                    for (int k = c; k < col.getStackCarta().size(); k++) {
                        cartasArrastradas.add(col.getStackCarta().get(k));
                    }

                    offsetX = mx - x;
                    offsetY = my - cy;
                    offsetYGrupo = my - cy;

                    dragX = mx - offsetX;
                    dragY = my - offsetY;

                    repaint();
                    return;
                }
            }
        }

        String[] palos = {"CORAZON", "ESPADA", "TREBOL", "DIAMANTE"};
        for (int i = 0; i < 4; i++) {

            int x = margenIzquierda + (i + 3) * (cartaAncho + columnaEspaciado);
            int y = margenSuperior2;

            Columna col = columnas.get("Columna " + palos[i]);

            if (col.getStackCarta().isEmpty()) continue;

            Carta carta = col.getStackCarta().peek();

            if (mx >= x && mx <= x + cartaAncho &&
                my >= y && my <= y + cartaAlto) {

                if (!carta.getEsBocaArriba()) return;

                cartaSeleccionada = carta;
                columnaOrigen = col;
                indiceCartaSeleccionada = col.getStackCarta().size() - 1;

                offsetX = mx - x;
                offsetY = my - y;
                dragX = mx - offsetX;
                dragY = my - offsetY;

                cartasArrastradas.clear();
                cartasArrastradas.add(carta);

                repaint();
                return;
            }
        }
    }

    private void manejarMouseDragged(MouseEvent e) {
        if (cartaSeleccionada != null) {
            dragX = e.getX() - offsetX;
            dragY = e.getY() - offsetY;
            repaint();
        }
    }

    private void manejarMouseReleased(MouseEvent e) {
        if (cartaSeleccionada == null) return;

        int mx = e.getX();
        int my = e.getY();

        int cartaAncho = 80;
        int cartaAlto = 120;
        int columnaEspaciado = 60;
        int margenIzquierda = 40;

        Map<String, Columna> columnas = mazo.getColumnas();
        Columna columnaDestino = null;

        int margenSuperior1 = 160;
        for (int i = 1; i <= 7; i++) {
            int x = margenIzquierda + (i - 1) * (cartaAncho + columnaEspaciado);
            int y = margenSuperior1;

            if (mx >= x && mx <= x + cartaAncho &&
                my >= y && my <= y + 400) {
                columnaDestino = columnas.get("Columna " + i);
                break;
            }
        }

        if (columnaDestino == null) {
            int margenSuperior2 = 20;
            String[] palos = {"CORAZON", "ESPADA", "TREBOL", "DIAMANTE"};

            for (int i = 0; i < 4; i++) {
                int x = margenIzquierda + (i + 3) * (cartaAncho + columnaEspaciado);
                int y = margenSuperior2;

                if (mx >= x && mx <= x + cartaAncho &&
                    my >= y && my <= y + cartaAlto) {
                    columnaDestino = columnas.get("Columna " + palos[i]);
                    break;
                }
            }
        }

        if (columnaDestino == null) {
            log("Movimiento inválido: no soltó en ninguna columna.");
            resetSeleccion();
            repaint();
            return;
        }

        if (!Logica.evaluarMovimiento(cartaSeleccionada, columnaDestino)) {
            log("Movimiento no permitido hacia " + columnaDestino.getEtiqueta());
            resetSeleccion();
            repaint();
            return;
        }

        boolean esSimbolo = columnaDestino.getEtiqueta().startsWith("Columna ")
                          && (columnaDestino.getEtiqueta().contains("CORAZON") ||
                              columnaDestino.getEtiqueta().contains("ESPADA") ||
                              columnaDestino.getEtiqueta().contains("TREBOL") ||
                              columnaDestino.getEtiqueta().contains("DIAMANTE"));

        if (esSimbolo) {
            if (!columnaDestino.equals(columnaOrigen)) {
                columnaOrigen.getStackCarta().remove(indiceCartaSeleccionada);
                columnaDestino.anadirCarta(cartaSeleccionada);
                log("Movida carta a " + columnaDestino.getEtiqueta());
            }
        } else {
            for (Carta c : cartasArrastradas) {
                columnaDestino.anadirCarta(c);
            }

            Stack<Carta> origen = columnaOrigen.getStackCarta();
            for (int i = origen.size() - 1; i >= indiceCartaSeleccionada; i--) {
                origen.remove(i);
            }

            log("Movida subcolumna a " + columnaDestino.getEtiqueta());
        }

        if (!columnaOrigen.getStackCarta().isEmpty()) {
            Carta tope = columnaOrigen.getStackCarta().peek();
            if (!tope.getEsBocaArriba()) {
                tope.setEsBocaArriba(true);
                log("Se volteó la carta superior de " + columnaOrigen.getEtiqueta());
            }
        }

        if (Logica.evaluarJuegoCompletado(mazo)) {

            juegoTerminado = true;

            JOptionPane.showMessageDialog(
                this,
                "¡Felicidades! Has completado el solitario.",
                "Juego terminado",
                JOptionPane.INFORMATION_MESSAGE
            );

            log("El jugador completó el solitario.");
            repaint();
            return;
        }

        resetSeleccion();
        repaint();
    }

    private void resetSeleccion() {
        cartaSeleccionada = null;
        cartasArrastradas.clear();
        columnaOrigen = null;
        indiceCartaSeleccionada = -1;
    }

    private void moverTopDeExtra1aExtra2(Columna extra1, Columna extra2) {
        if (extra1.getStackCarta().isEmpty()) return;

        Carta carta = extra1.getStackCarta().pop();
        carta.setEsBocaArriba(true);
        extra2.getStackCarta().push(carta);

        if (!extra1.getStackCarta().isEmpty()) {
            for (Carta c : extra1.getStackCarta()) c.setEsBocaArriba(false);
            extra1.getStackCarta().peek().setEsBocaArriba(true);
        }
    }

    private void reciclarExtra2aExtra1(Columna extra1, Columna extra2) {
        if (!extra1.getStackCarta().isEmpty()) return;
        if (extra2.getStackCarta().isEmpty()) return;

        while (!extra2.getStackCarta().isEmpty()) {
            Carta c = extra2.getStackCarta().pop();
            c.setEsBocaArriba(false);
            extra1.getStackCarta().push(c);
        }

        if (!extra1.getStackCarta().isEmpty()) {
            extra1.getStackCarta().peek().setEsBocaArriba(true);
        }
    }
}