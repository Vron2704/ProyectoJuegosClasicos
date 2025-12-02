package Ajedrez.main;
import Ajedrez.Piezas.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Tablero extends JPanel {

    public int celdaSize = 85;

    int columnas = 8;
    int filas = 8;

    ArrayList<Piezas> piezasList = new ArrayList<>();

    public Piezas piezaSeleccionada;

    Input input = new Input(this);
    private String mensajeGameOver = "";




    public CheckScaner escanerJaque = new CheckScaner(this);


    public int enPasantTitle = -1;


    private boolean isWhiteToMove = true;
    public boolean isGameOver = false;


    public Tablero() {
        this.setPreferredSize(new Dimension(columnas * celdaSize, filas * celdaSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPieza();
    }

    public Piezas getPiezas(int col, int fil){
        for(Piezas piezas : piezasList){
            if(piezas.col == col && piezas.fil == fil){
                return piezas;
            }
        }

        return null;
    }

    public void makeMovimiento(Movimiento movimiento){

        if(movimiento.piezas.name.equals("Peon")){
            moverPeon(movimiento);
        } else if (movimiento.piezas.name.equals("Rey")) {
            moveKing((movimiento));
        }
            movimiento.piezas.col = movimiento.newCol;
            movimiento.piezas.fil = movimiento.newFil;
            movimiento.piezas.xPos = movimiento.newCol * celdaSize;
            movimiento.piezas.yPos = movimiento.newFil * celdaSize;

            movimiento.piezas.isFirstMove = false;

            captura(movimiento.captura);

            isWhiteToMove = !isWhiteToMove;

            updateGameState();
    }


    private void moveKing(Movimiento movimiento){

        if (Math.abs(movimiento.piezas.col - movimiento.newCol) == 2){
            Piezas torre;
            if(movimiento.piezas.col < movimiento.newCol){
                torre = getPiezas(7, movimiento.piezas.fil);
                torre.col = 5;
            } else {
                torre = getPiezas(0, movimiento.piezas.fil);
                torre.col = 3;
            }
            torre.xPos = torre.col * celdaSize;
        }

    }


    private void moverPeon(Movimiento movimiento) {

        int colorIndex = movimiento.piezas.isWhite ? 1 : -1;

        if (getTileNum(movimiento.newCol, movimiento.newFil) == enPasantTitle) {
            movimiento.captura = getPiezas(movimiento.newCol, movimiento.newFil + colorIndex);
        }
        if (Math.abs(movimiento.piezas.fil - movimiento.newFil) == 2){
            enPasantTitle = getTileNum(movimiento.newCol, movimiento.newFil + colorIndex);
        } else {
            enPasantTitle = -1;
        }

        // promocion
        colorIndex = movimiento.piezas.isWhite ? 0 : 7;
        if (movimiento.newFil == colorIndex){
            promocionarPeon(movimiento);
        }


    }

    private void promocionarPeon(Movimiento movimiento) {
        piezasList.add(new Reina(this, movimiento.newCol, movimiento.newFil, movimiento.piezas.isWhite));
        captura(movimiento.piezas);
    }

    public void captura(Piezas piezas){ piezasList.remove(piezas);}


    public boolean movimientoValido(Movimiento movimiento){

        if (isGameOver){
            return false;
        }

        if (movimiento.piezas.isWhite != isWhiteToMove){
            return false;
        }
        if (mismoEquipo(movimiento.piezas, movimiento.captura)){
            return false;
        }

        if (!movimiento.piezas.movimientoValido(movimiento.newCol, movimiento.newFil)){
            return false;
        }
        if (movimiento.piezas.MovimientoColision(movimiento.newCol, movimiento.newFil)){
            return false;
        }

        if (escanerJaque.ReyEnJaque(movimiento)){
            return false;
        }

        return true;
    }

    public boolean mismoEquipo(Piezas p1, Piezas p2){
        if (p1 == null || p2 == null){
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }


    public int getTileNum(int col, int fil){
        return fil * filas + col;
    }


    Piezas findRey(boolean isWhite){
        for(Piezas piezas : piezasList){
            if (isWhite == piezas.isWhite && piezas.name.equals("Rey")){
                return piezas;
            }
        }
        return null;

    }

    public void addPieza(){

        //Piezas Negras

        piezasList.add(new Torre(this, 0, 7, true));
        piezasList.add(new Caballo(this, 1, 7, true));
        piezasList.add(new Alfil(this, 2, 7, true));
        piezasList.add(new Reina(this, 3, 7, true));
        piezasList.add(new Rey(this, 4, 7, true));
        piezasList.add(new Alfil(this, 5, 7, true));
        piezasList.add(new Caballo(this, 6, 7, true));
        piezasList.add(new Torre(this, 7, 7, true));

        piezasList.add(new Peon(this, 0, 6, true));
        piezasList.add(new Peon(this, 1, 6, true));
        piezasList.add(new Peon(this, 2, 6, true));
        piezasList.add(new Peon(this, 3, 6, true));
        piezasList.add(new Peon(this, 4, 6, true));
        piezasList.add(new Peon(this, 5, 6, true));
        piezasList.add(new Peon(this, 6, 6, true));
        piezasList.add(new Peon(this, 7, 6, true));

        //Piezas Blancas

        piezasList.add(new Torre(this, 0, 0, false));
        piezasList.add(new Caballo(this, 1, 0, false));
        piezasList.add(new Alfil(this, 2, 0, false));
        piezasList.add(new Reina(this, 3, 0, false));
        piezasList.add(new Rey(this, 4, 0, false));
        piezasList.add(new Alfil(this, 5, 0, false));
        piezasList.add(new Caballo(this, 6, 0, false));
        piezasList.add(new Torre(this, 7, 0, false));

        piezasList.add(new Peon(this, 0, 1, false));
        piezasList.add(new Peon(this, 1, 1, false));
        piezasList.add(new Peon(this, 2, 1, false));
        piezasList.add(new Peon(this, 3, 1, false));
        piezasList.add(new Peon(this, 4, 1, false));
        piezasList.add(new Peon(this, 5, 1, false));
        piezasList.add(new Peon(this, 6, 1, false));
        piezasList.add(new Peon(this, 7, 1, false));
    }

    private void updateGameState() {
        Piezas rey = findRey(isWhiteToMove);

        if (escanerJaque.isGameOver(rey)) {
            if (escanerJaque.ReyEnJaque(new Movimiento(this, rey, rey.col, rey.fil))) {
                mensajeGameOver = isWhiteToMove ? "¡Las Negras Ganan!" : "¡Las Blancas Ganan!";
            } else {
                mensajeGameOver = "¡Tablas! (Ahogado)";
            }
            isGameOver = true;
            mostrarDialogoFinDeJuego(); // Llamamos al popup

        } else if (MaterialInsuficiencia(true) && MaterialInsuficiencia(false)) {
            mensajeGameOver = "Tablas por Piezas Insuficientes";
            isGameOver = true;
            mostrarDialogoFinDeJuego(); // Llamamos al popup
        }
    }

    public void mostrarDialogoFinDeJuego() {
        Object[] opciones = {"Ver Tablero", "Jugar de Nuevo", "Salir"};

        int seleccion = JOptionPane.showOptionDialog(
                this,
                mensajeGameOver,
                "Fin del Juego",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );

        switch (seleccion) {
            case 0: // Ver Tablero
                // No hacemos nada, solo cerramos el popup.
                // Como isGameOver es true, no se podrán mover piezas.
                break;
            case 1: // Jugar de Nuevo
                resetGame();
                break;
            case 2: // Salir
                System.exit(0);
                break;
        }
    }


    private void resetGame() {
        piezasList.clear();

        // --- CORRECCIÓN CRÍTICA ---
        // Debemos soltar cualquier pieza que estuviera seleccionada
        piezaSeleccionada = null;

        isWhiteToMove = true;
        isGameOver = false;
        enPasantTitle = -1;

        // Limpiamos el mensaje final
        mensajeGameOver = "";

        addPieza();
        repaint();
    }


    private boolean MaterialInsuficiencia(boolean isWhite){
        ArrayList<String> names = piezasList.stream()
                .filter(p -> p.isWhite == isWhite)
                .map(p -> p.name)
                .collect(Collectors.toCollection(ArrayList::new));
        if (names.contains("Reyna") || names.contains("Torre") || names.contains("Peon")) {
            return false;
        }
        return names.size() < 3;
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //pintar tablero

        for(int r = 0; r < filas; r++)
            for(int c = 0; c < columnas; c++) {
                g2d.setColor((c+r) % 2 == 0 ? new Color(0xFFFFFF) : new Color(0xC36844));
                g2d.fillRect(c * celdaSize, r * celdaSize, celdaSize, celdaSize);
            }

        // pintar Posible Movmientos

        if(piezaSeleccionada != null)
        for(int r = 0; r < filas; r++)
            for(int c = 0; c < columnas; c++) {

                if(movimientoValido(new Movimiento(this, piezaSeleccionada, c, r))){

                    g2d.setColor(new Color(122, 243, 31, 84));
                    g2d.fillRect(c * celdaSize, r * celdaSize, celdaSize, celdaSize);

                }
            }

        //pintar Piezas

        for(Piezas piezas : piezasList){
            piezas.paint(g2d);
        }
    }
}
