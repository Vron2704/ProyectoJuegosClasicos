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
import java.util.Map;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CartaPanel extends JPanel{
    
    private Carta cartaSeleccionada = null;
    private Columna columnaOrigen = null;
    private int offsetX, offsetY;
    private int dragX, dragY;
    private Image iconCorazon, iconEspada, iconTrebol, iconDiamante;
    private int indiceCartaSeleccionada = -1;
    private java.util.List<Carta> cartasArrastradas = new java.util.ArrayList<>();
    private int offsetYGrupo = 0; // para mover correctamente la subcolumna

    
    private Mazo mazo;
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
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                manejarMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                manejarMouseReleased(e);
            }
            });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                manejarMouseDragged(e);
            }
        });
        setDoubleBuffered(true);
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
                Image img = carta.getEsBocaArriba() ? 
                            carta.getImagenBocaArriba() :
                            carta.getImagenBocaAbajo();
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
                    int iconWidth = 40;
                    int iconHeight = 40;
                    int iconX = x + (cartaAncho - iconWidth) / 2;
                    int iconY = y + (cartaAlto - iconHeight) / 2;

                    g.drawImage(icon, iconX, iconY, iconWidth, iconHeight, this);
                }
            }else{
                Carta carta = col.getStackCarta().peek();
                Image img = carta.getEsBocaArriba() ?
                        carta.getImagenBocaArriba() :
                        carta.getImagenBocaAbajo();
                g.drawImage(img, x, y, cartaAncho, cartaAlto, this);
            }
        }
        // Si hay una carta siendo arrastrada, dibujarla encima
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

    int xExtra1 = margenIzquierda + 0 * (cartaAncho + columnaEspaciado);
    int xExtra2 = margenIzquierda + 1 * (cartaAncho + columnaEspaciado);
    int yExtra  = margenSuperior2;

    // ================================
    //   CLICK en EXTRA 1
    // ================================
    if (mx >= xExtra1 && mx <= xExtra1 + cartaAncho &&
        my >= yExtra  && my <= yExtra  + cartaAlto) {

        if (extra1.getStackCarta().isEmpty()) {
            // EXTRA1 vacío → reciclar todas de EXTRA2
            reciclarExtra2aExtra1(extra1, extra2);
        } else {
            // EXTRA1 tiene carta → mover top a EXTRA2
            moverTopDeExtra1aExtra2(extra1, extra2);
        }

        repaint();
        return;  // <- IMPORTANTE
    }

// ================================
//   CLICK en EXTRA 2
// ================================
if (mx >= xExtra2 && mx <= xExtra2 + cartaAncho &&
    my >= yExtra  && my <= yExtra  + cartaAlto) {

    if (extra2.getStackCarta().isEmpty()) {
        // EXTRA2 vacío → mover 1 carta desde EXTRA1
        moverTopDeExtra1aExtra2(extra1, extra2);
        repaint();
        return;
    }

    // EXTRA2 tiene carta → iniciar arrastre de la carta superior
    Carta carta = extra2.getStackCarta().peek();

    if (!carta.getEsBocaArriba()) return;

    cartaSeleccionada = carta;
    columnaOrigen = extra2;
    indiceCartaSeleccionada = extra2.getStackCarta().size() - 1;

    offsetX = mx - xExtra2;
    offsetY = my - yExtra;

    dragX = mx - offsetX;
    dragY = my - offsetY;

    // IMPORTANT: al arrastrar desde extra2 también debemos poblar cartasArrastradas
    cartasArrastradas.clear();
    cartasArrastradas.add(carta);

    repaint();
    return;
}

    // === 1. BUSCAR EN LAS 7 COLUMNAS PRINCIPALES ===
    int margenSuperior1 = 160;

    for (int i = 1; i <= 7; i++) {
        int x = margenIzquierda + (i - 1) * (cartaAncho + columnaEspaciado);
        int y = margenSuperior1;

        Columna col = columnas.get("Columna " + i);
        int offset = 25;

        // recorre de arriba hacia abajo
        for (int c = col.getStackCarta().size() - 1; c >= 0; c--) {

            int cy = y + c * offset;

            if (mx >= x && mx <= x + cartaAncho &&
                my >= cy && my <= cy + cartaAlto)
            {
                Carta carta = col.getStackCarta().get(c);

                if (!carta.getEsBocaArriba()) return;

                cartaSeleccionada = carta;
                columnaOrigen = col;
                indiceCartaSeleccionada = c;

// Extraer TODAS las cartas desde la seleccionada hacia abajo
                cartasArrastradas.clear();
                for (int k = c; k < col.getStackCarta().size(); k++) {
                    cartasArrastradas.add(col.getStackCarta().get(k));
                }

// offsets
                offsetX = mx - x;
                offsetY = my - cy;

                offsetYGrupo = my - (cy); // para mantener separación entre cartas al arrastrar

                dragX = mx - offsetX;
                dragY = my - offsetY;

                repaint();
                return;
            }
        }
    }

    // === 2. BUSCAR EN COLUMNAS DE SÍMBOLO ===
    String[] palos = {"CORAZON", "ESPADA", "TREBOL", "DIAMANTE"};

    for (int i = 0; i < 4; i++) {

        int x = margenIzquierda + (i + 3) * (cartaAncho + columnaEspaciado);
        int y = margenSuperior2;

        Columna col = columnas.get("Columna " + palos[i]);

        if (col.getStackCarta().isEmpty()) continue;

        // Sólo hay 1 carta en estas columnas → el tope
        Carta carta = col.getStackCarta().peek();

        if (mx >= x && mx <= x + cartaAncho &&
            my >= y && my <= y + cartaAlto)
        {
            if (!carta.getEsBocaArriba()) return;

            cartaSeleccionada = carta;
            columnaOrigen = col;
            indiceCartaSeleccionada = col.getStackCarta().size() - 1;

            offsetX = mx - x;
            offsetY = my - y;
            dragX = mx - offsetX;
            dragY = my - offsetY;

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

    // --- Buscar columna normal ---
    int margenSuperior1 = 160;
    for (int i = 1; i <= 7; i++) {
        int x = margenIzquierda + (i - 1) * (cartaAncho + columnaEspaciado);
        int y = margenSuperior1;

        if (mx >= x && mx <= x + cartaAncho &&
            my >= y && my <= y + 400) 
        {
            columnaDestino = columnas.get("Columna " + i);
            break;
        }
    }

    // --- Buscar columnas de símbolo ---
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

    // --- Si no soltó en una zona válida ---
    if (columnaDestino == null) {

        // LIMPIAR PARA EVITAR DUPLICADOS
        cartaSeleccionada = null;
        cartasArrastradas.clear();
        columnaOrigen = null;
        indiceCartaSeleccionada = -1;

        repaint();
        return;
    }

    // --- Movimiento válido según la lógica con consideración especial para columnas símbolo ---
if (Logica.evaluarMovimiento(cartaSeleccionada, columnaDestino)) {

    // Detectar si columnaDestino es una columna de símbolo (tus etiquetas: "Columna CORAZON", etc.)
    String etiquetaDestino = columnaDestino.getEtiqueta();
    boolean esColumnaSimbolo = etiquetaDestino.equals("Columna CORAZON")
                            || etiquetaDestino.equals("Columna ESPADA")
                            || etiquetaDestino.equals("Columna TREBOL")
                            || etiquetaDestino.equals("Columna DIAMANTE");

    if (esColumnaSimbolo) {
        // SOLO mover UNA carta (la carta seleccionada)
        columnaDestino.anadirCarta(cartaSeleccionada);
        // remover esa carta del origen
        columnaOrigen.getStackCarta().remove(indiceCartaSeleccionada);
    } else {
        // mover TODAS las cartas arrastradas (subcolumna)
        for (Carta c : cartasArrastradas) {
            columnaDestino.anadirCarta(c);
        }

        // eliminar del origen desde el índice seleccionado hacia abajo
        for (int i = columnaOrigen.getStackCarta().size() - 1; i >= indiceCartaSeleccionada; i--) {
            columnaOrigen.getStackCarta().remove(i);
        }
    }

    // Voltear la carta superior del origen si quedó boca abajo
    if (!columnaOrigen.getStackCarta().isEmpty()) {
        Carta tope = columnaOrigen.getStackCarta().peek();
        if (!tope.getEsBocaArriba()) tope.setEsBocaArriba(true);
    }

} else {
    System.out.println("Movimiento no permitido");
}


    // --- Limpieza final obligatoria ---
    cartaSeleccionada = null;
    cartasArrastradas.clear();  // <---------------- EL FIX IMPORTANTE
    columnaOrigen = null;
    indiceCartaSeleccionada = -1;

    repaint();
}

    private void rotarColumnaExtra(Map<String, Columna> columnas) {

    Columna extra1 = columnas.get("Columna extra 1");
    Columna extra2 = columnas.get("Columna extra 2");

    // Si no hay cartas en extra1 → no hacemos nada por ahora
    if (extra1.getStackCarta().isEmpty()) {
        return;
    }

    // 1. Tomar la carta superior
    Carta carta = extra1.getStackCarta().pop();

    // La carta que va a extra2 siempre boca arriba
    carta.setEsBocaArriba(true);

    // 2. Mover carta a extra2
    extra2.getStackCarta().push(carta);
    carta.setColumnaActual(extra2);

    // 3. Asegurar que en extra1: 
    //    - todas boca abajo
    //    - menos la carta superior que queda boca arriba
    if (!extra1.getStackCarta().isEmpty()) {

        // todas boca abajo
        for (Carta c : extra1.getStackCarta()) {
            c.setEsBocaArriba(false);
        }

        // excepto la carta superior
        extra1.getStackCarta().peek().setEsBocaArriba(true);
    }
}
    private void moverTopDeExtra1aExtra2(Columna extra1, Columna extra2) {
    // Mover una carta de extra1 -> extra2 (top)
    if (extra1.getStackCarta().isEmpty()) return;

    Carta carta = extra1.getStackCarta().pop();
    carta.setEsBocaArriba(true);            // la carta movida siempre boca arriba
    extra2.getStackCarta().push(carta);
    carta.setColumnaActual(extra2);

    // Ajustar extra1: todas boca abajo excepto la tope
    if (!extra1.getStackCarta().isEmpty()) {
        for (Carta c : extra1.getStackCarta()) c.setEsBocaArriba(false);
        extra1.getStackCarta().peek().setEsBocaArriba(true);
    }
}

private void reciclarExtra2aExtra1(Columna extra1, Columna extra2) {
    // Solo si extra1 está vacía y extra2 no lo está
    if (!extra1.getStackCarta().isEmpty()) return;
    if (extra2.getStackCarta().isEmpty()) return;

    // Transferir directamente pop desde extra2 -> push en extra1
    while (!extra2.getStackCarta().isEmpty()) {
        Carta c = extra2.getStackCarta().pop(); // saca la carta superior de extra2
        c.setEsBocaArriba(false);               // volverla boca abajo
        extra1.getStackCarta().push(c);         // ponerla en extra1 (mantiene orden original)
        c.setColumnaActual(extra1);
    }

    // La carta superior de extra1 debe quedar boca arriba (si existe)
    if (!extra1.getStackCarta().isEmpty()) {
        extra1.getStackCarta().peek().setEsBocaArriba(true);
    }
}
}
