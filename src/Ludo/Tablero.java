package Ludo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Tablero extends JPanel {
    private Main mainFrame;
    private List<Jugador> jugadores;
    private int turnoActualIndex = 0;

    // Dimensiones lógicas del Ludo estándar (15x15 casillas)
    private final int GRID_SIZE = 15;
    private int cellSize = 40;
    private int boardX = 0;
    private int boardY = 0;

    // Camino común (perímetro)
    private final List<Point> perimetroComun;

    public Tablero(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.jugadores = new ArrayList<>();

        // 1. Generar camino lógico
        this.perimetroComun = crearPerimetro();

        // 2. Inicializar jugadores
        inicializarJuego();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClic(e.getX(), e.getY());
            }
        });
    }

    private List<Point> crearPerimetro() {
        List<Point> p = new ArrayList<>();
        // Sector ROJO (Izquierda -> Arriba)
        for (int i=1; i<=5; i++) p.add(new Point(i, 6)); // Recta salida
        for (int i=5; i>=0; i--) p.add(new Point(6, i)); // Subida
        p.add(new Point(7, 0)); p.add(new Point(8, 0));  // Giro arriba

        // Sector AZUL (Arriba -> Derecha)
        for (int i=1; i<=5; i++) p.add(new Point(8, i)); // Bajada
        for (int i=9; i<=14; i++) p.add(new Point(i, 6)); // Derecha
        p.add(new Point(14, 7)); p.add(new Point(14, 8)); // Giro derecha

        // Sector AMARILLO (Derecha -> Abajo)
        for (int i=13; i>=9; i--) p.add(new Point(i, 8)); // Izquierda
        for (int i=9; i<=14; i++) p.add(new Point(8, i)); // Bajada
        p.add(new Point(7, 14)); p.add(new Point(6, 14)); // Giro abajo

        // Sector VERDE (Abajo -> Izquierda)
        for (int i=13; i>=9; i--) p.add(new Point(6, i)); // Subida
        for (int i=5; i>=0; i--) p.add(new Point(i, 8)); // Izquierda final
        p.add(new Point(0, 7)); p.add(new Point(0, 6));  // Giro final

        return p;
    }

    private void inicializarJuego() {
        // Inicializamos jugadores con sus coordenadas de base y rutas
        jugadores.add(new Jugador("ROJO", new Color(231, 76, 60), generarRutaParaJugador(0, 0), new Point(0, 0)));
        jugadores.add(new Jugador("AZUL", new Color(52, 152, 219), generarRutaParaJugador(13, 1), new Point(9, 0)));
        jugadores.add(new Jugador("AMARILLO", new Color(241, 196, 15), generarRutaParaJugador(26, 2), new Point(9, 9)));
        jugadores.add(new Jugador("VERDE", new Color(46, 204, 113), generarRutaParaJugador(39, 3), new Point(0, 9)));
    }

    private List<Point> generarRutaParaJugador(int indiceInicio, int tipoRectaFinal) {
        List<Point> ruta = new ArrayList<>();
        int totalCasillas = perimetroComun.size();

        // 1. Recorrido perimetral (51 pasos)
        for (int i = 0; i < 51; i++) {
            int indiceActual = (indiceInicio + i) % totalCasillas;
            ruta.add(perimetroComun.get(indiceActual));
        }

        // 2. Recta Final (Hacia el centro)
        // Se añaden 6 casillas para que la ficha llegue visualmente al centro
        if (tipoRectaFinal == 0) { // ROJO
            for(int i=1; i<=6; i++) ruta.add(new Point(i, 7));
        } else if (tipoRectaFinal == 1) { // AZUL
            for(int i=1; i<=6; i++) ruta.add(new Point(7, i));
        } else if (tipoRectaFinal == 2) { // AMARILLO
            for(int i=13; i>=8; i--) ruta.add(new Point(i, 7));
        } else if (tipoRectaFinal == 3) { // VERDE
            for(int i=13; i>=8; i--) ruta.add(new Point(7, i));
        }

        // 3. Meta (Posición final 57)
        ruta.add(new Point(7, 7));
        return ruta;
    }

    // --- LÓGICA DEL JUEGO ---
    public boolean jugadorActualTieneMovimientos(int dado) {
        Jugador j = getJugadorActual();
        for (Ficha f : j.fichas) {
            if (f.enCasa && dado == 6) return true;
            if (!f.enCasa && !f.enMeta && (f.posicionRuta + dado < j.rutaCoordenadas.size())) return true;
        }
        return false;
    }

    public Jugador getJugadorActual() { return jugadores.get(turnoActualIndex); }

    public void cambiarTurno() { turnoActualIndex = (turnoActualIndex + 1) % 4; repaint(); }

    private void manejarClic(int x, int y) {
        if (!mainFrame.isDadoLanzado()) return;

        Jugador actual = getJugadorActual();
        int dado = mainFrame.getValorDado();

        for (Ficha f : actual.fichas) {
            Point p = obtenerCoordenadaPixel(f, actual);

            if (Math.abs(x - p.x) < cellSize/1.5 && Math.abs(y - p.y) < cellSize/1.5) {
                boolean movio = false;

                if (f.enCasa && dado == 6) {
                    f.enCasa = false;
                    f.posicionRuta = 0;
                    movio = true;
                } else if (!f.enCasa && !f.enMeta) {
                    if (f.posicionRuta + dado < actual.rutaCoordenadas.size()) {
                        f.posicionRuta += dado;
                        if (f.posicionRuta == actual.rutaCoordenadas.size() - 1) {
                            f.enMeta = true;
                            JOptionPane.showMessageDialog(this, "¡Ficha llegó a la meta!");
                        }
                        movio = true;
                    }
                }

                if (movio) {
                    mainFrame.finalizarMovimiento();
                    repaint();
                }
                return;
            }
        }
    }

    private Point obtenerCoordenadaPixel(Ficha f, Jugador j) {
        if (f.enCasa) {
            // Alineación de fichas en casa (2x2 grid relativo a la base)
            int dx = (f.id % 2) + 2;
            int dy = (f.id / 2) + 2;
            return gridToPixel(j.casaBase.x + dx, j.casaBase.y + dy);
        }
        if (f.posicionRuta >= 0 && f.posicionRuta < j.rutaCoordenadas.size()) {
            Point coordenadaGrid = j.rutaCoordenadas.get(f.posicionRuta);
            return gridToPixel(coordenadaGrid.x, coordenadaGrid.y);
        }
        return gridToPixel(7, 7);
    }

    private Point gridToPixel(int col, int row) {
        return new Point(boardX + col * cellSize + cellSize/2, boardY + row * cellSize + cellSize/2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calcular tamaño dinámico
        int minDimension = Math.min(getWidth(), getHeight());
        cellSize = (minDimension - 60) / GRID_SIZE;
        boardX = (getWidth() - (cellSize * GRID_SIZE)) / 2;
        boardY = (getHeight() - (cellSize * GRID_SIZE)) / 2;

        // 1. Dibujar Bases
        dibujarBase(g2, jugadores.get(0).color, 0, 0);   // Rojo
        dibujarBase(g2, jugadores.get(1).color, 9, 0);   // Azul
        dibujarBase(g2, jugadores.get(2).color, 9, 9);   // Amarillo
        dibujarBase(g2, jugadores.get(3).color, 0, 9);   // Verde

        // 2. Dibujar Cuadrícula (bordes negros)
        g2.setStroke(new BasicStroke(1));
        // Vertical
        for(int x=6; x<=8; x++) {
            for(int y=0; y<15; y++) {
                if((y<6) || (y>8)) dibujarCasilla(g2, x, y, Color.WHITE);
            }
        }
        // Horizontal
        for(int y=6; y<=8; y++) {
            for(int x=0; x<15; x++) {
                if((x<6) || (x>8)) dibujarCasilla(g2, x, y, Color.WHITE);
            }
        }

        // 3. COLOREAR CAMINOS (AQUÍ ESTÁ LA CORRECCIÓN VISUAL)
        colorearCaminos(g2);

        // 4. Dibujar Triángulo Central (Encima de los caminos coloreados)
        int centerStart = 6 * cellSize;
        int centerSize = 3 * cellSize;
        g2.setColor(Color.WHITE);
        g2.fillRect(boardX + centerStart, boardY + centerStart, centerSize, centerSize);
        g2.setColor(Color.BLACK);
        g2.drawRect(boardX + centerStart, boardY + centerStart, centerSize, centerSize);

        dibujarTriangulosCentro(g2);

        // 5. Dibujar Fichas
        for (Jugador j : jugadores) {
            for (Ficha f : j.fichas) {
                Point p = obtenerCoordenadaPixel(f, j);
                int radio = (int)(cellSize * 0.7);

                g2.setColor(new Color(0,0,0,50)); // Sombra
                g2.fillOval(p.x - radio/2 + 2, p.y - radio/2 + 2, radio, radio);

                g2.setColor(Color.WHITE); // Borde blanco
                g2.fillOval(p.x - radio/2 - 2, p.y - radio/2 - 2, radio+4, radio+4);

                g2.setColor(j.color); // Color ficha
                g2.fillOval(p.x - radio/2, p.y - radio/2, radio, radio);

                g2.setColor(Color.BLACK); // Contorno negro
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(p.x - radio/2, p.y - radio/2, radio, radio);
            }
        }
    }

    private void dibujarCasilla(Graphics2D g2, int x, int y, Color color) {
        g2.setColor(color);
        g2.fillRect(boardX + x*cellSize, boardY + y*cellSize, cellSize, cellSize);
        g2.setColor(Color.BLACK);
        g2.drawRect(boardX + x*cellSize, boardY + y*cellSize, cellSize, cellSize);
    }

    // --- CORRECCIÓN EN ESTE MÉTODO ---
    private void colorearCaminos(Graphics2D g2) {
        // ROJO (Izquierda): Start en (1,6). Camino en fila 7.
        dibujarCasilla(g2, 0, 6, jugadores.get(0).color); // Casilla Salida
        // Pintamos hasta i <= 6 (antes era < 6). Esto pinta la casilla DEBAJO del triángulo rojo.
        for(int i=0; i<=6; i++) dibujarCasilla(g2, i, 7, jugadores.get(0).color);

        // AZUL (Arriba): Start en (8,1). Camino en columna 7.
        dibujarCasilla(g2, 8, 0, jugadores.get(1).color);
        for(int i=0; i<=6; i++) dibujarCasilla(g2, 7, i, jugadores.get(1).color);

        // AMARILLO (Derecha): Start en (13,8). Camino en fila 7.
        dibujarCasilla(g2, 14, 8, jugadores.get(2).color);
        // Pintamos hasta i >= 8 (cubre la casilla debajo del triángulo amarillo)
        for(int i=14; i>=8; i--) dibujarCasilla(g2, i, 7, jugadores.get(2).color);

        // VERDE (Abajo): Start en (6,13). Camino en columna 7.
        dibujarCasilla(g2, 6, 14, jugadores.get(3).color);
        for(int i=14; i>=8; i--) dibujarCasilla(g2, 7, i, jugadores.get(3).color);
    }

    private void dibujarBase(Graphics2D g2, Color color, int col, int row) {
        int x = boardX + col * cellSize;
        int y = boardY + row * cellSize;
        int size = 6 * cellSize;

        g2.setColor(color);
        g2.fillRect(x, y, size, size);
        g2.setColor(Color.WHITE);
        int margin = cellSize;
        g2.fillRect(x + margin, y + margin, size - 2*margin, size - 2*margin);

        // Círculos de casas
        g2.setColor(color);
        int circleSize = (int)(cellSize * 0.8);
        int offset = (cellSize - circleSize) / 2;

        g2.fillOval(x + 2*cellSize + offset, y + 2*cellSize + offset, circleSize, circleSize);
        g2.fillOval(x + 3*cellSize + offset, y + 2*cellSize + offset, circleSize, circleSize);
        g2.fillOval(x + 2*cellSize + offset, y + 3*cellSize + offset, circleSize, circleSize);
        g2.fillOval(x + 3*cellSize + offset, y + 3*cellSize + offset, circleSize, circleSize);
    }

    private void dibujarTriangulosCentro(Graphics2D g2) {
        int cx = boardX + (int)(7.5 * cellSize);
        int cy = boardY + (int)(7.5 * cellSize);
        int half = (int)(1.5 * cellSize);

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        xPoints[0] = cx;
        yPoints[0] = cy;

        // Rojo (Izq)
        g2.setColor(jugadores.get(0).color);
        xPoints[1] = cx - half; yPoints[1] = cy - half;
        xPoints[2] = cx - half; yPoints[2] = cy + half;
        g2.fillPolygon(xPoints, yPoints, 3);

        // Azul (Arriba)
        g2.setColor(jugadores.get(1).color);
        xPoints[1] = cx - half; yPoints[1] = cy - half;
        xPoints[2] = cx + half; yPoints[2] = cy - half;
        g2.fillPolygon(xPoints, yPoints, 3);

        // Amarillo (Derecha)
        g2.setColor(jugadores.get(2).color);
        xPoints[1] = cx + half; yPoints[1] = cy - half;
        xPoints[2] = cx + half; yPoints[2] = cy + half;
        g2.fillPolygon(xPoints, yPoints, 3);

        // Verde (Abajo)
        g2.setColor(jugadores.get(3).color);
        xPoints[1] = cx - half; yPoints[1] = cy + half;
        xPoints[2] = cx + half; yPoints[2] = cy + half;
        g2.fillPolygon(xPoints, yPoints, 3);
    }
}