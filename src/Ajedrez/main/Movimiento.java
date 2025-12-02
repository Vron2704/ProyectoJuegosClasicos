package Ajedrez.main;

import Ajedrez.Piezas.Piezas;

public class Movimiento {
    int oldCol;
    int oldFil;
    int newCol;
    int newFil;

    Piezas piezas;
    Piezas captura;

    public Movimiento(Tablero tablero, Piezas piezas, int newCol, int newFil){
        this.oldCol = piezas.col;
        this.oldFil = piezas.fil;
        this.newCol = newCol;
        this.newFil = newFil;
        this.piezas = piezas;
        this.captura = tablero.getPiezas(newCol, newFil);
    }
}
