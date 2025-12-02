package Ajedrez.main;

import Ajedrez.Piezas.Piezas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input extends MouseAdapter {

    Tablero tablero;
    public Input(Tablero tablero){
        this.tablero = tablero;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


        if (tablero.isGameOver) {
            tablero.mostrarDialogoFinDeJuego();
            return;
        }

        int col = e.getX() / tablero.celdaSize;
        int fil = e.getY() / tablero.celdaSize;

        Piezas piezasXY = tablero.getPiezas(col, fil);
        if(piezasXY != null){
            tablero.piezaSeleccionada = piezasXY;
        }
    }



    @Override
    public void mouseDragged(MouseEvent e) {

        if(tablero.piezaSeleccionada != null){
            tablero.piezaSeleccionada.xPos = e.getX() - tablero.celdaSize / 2;
            tablero.piezaSeleccionada.yPos = e.getY() - tablero.celdaSize / 2;
            tablero.repaint();
        }

    }



    @Override
    public void mouseReleased(MouseEvent e) {

        int col = e.getX() / tablero.celdaSize;
        int fil = e.getY() / tablero.celdaSize;

        if(tablero.piezaSeleccionada != null){
            Movimiento movimiento = new Movimiento(tablero, tablero.piezaSeleccionada, col, fil);

            if (tablero.movimientoValido(movimiento)){
                tablero.makeMovimiento(movimiento);
            } else {

                tablero.piezaSeleccionada.xPos = tablero.piezaSeleccionada.col * tablero.celdaSize;
                tablero.piezaSeleccionada.yPos = tablero.piezaSeleccionada.fil * tablero.celdaSize;

            }
        }
        tablero.piezaSeleccionada = null;
        tablero.repaint();
    }


}
