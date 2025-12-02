package Ajedrez.main;

import Ajedrez.Piezas.Piezas;

public class CheckScaner {

    Tablero tablero;


    public CheckScaner(Tablero tablero) {
        this.tablero = tablero;
    }


    public boolean ReyEnJaque(Movimiento movimiento){
        Piezas rey = tablero.findRey(movimiento.piezas.isWhite);
        assert rey != null;

        int reyCol = rey.col;
        int reyFil = rey.fil;

        if (tablero.piezaSeleccionada != null && tablero.piezaSeleccionada.name.equals("Rey")){
            reyCol = movimiento.newCol;
            reyFil = movimiento.newFil;
        }

        return  hitByTorre(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, 0, 1) ||
                hitByTorre(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, 1, 0) ||
                hitByTorre(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, 0, -1) ||
                hitByTorre(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, -1, 0) ||

                hitByAlfil(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, -1, -1) ||
                hitByAlfil(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, 1, -1) ||
                hitByAlfil(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, 1, 1) ||
                hitByAlfil(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil, -1,1) ||

                hitByCaballo(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil)||
                hitByPeon(movimiento.newCol, movimiento.newFil, rey, reyCol, reyFil)||
                hitByRey(rey, reyCol, reyFil);

    }

    private boolean hitByTorre(int col, int fil, Piezas rey, int reyCol, int reyFil, int colVal, int filVal){
        for (int i = 1; i < 8; i++){
            if (reyCol + (i * colVal) == col && reyFil + (i * filVal) == fil){
                break;
            }
            Piezas piezas = tablero.getPiezas(reyCol +( i * colVal), reyFil +( i * filVal));
            if (piezas != null && piezas != tablero.piezaSeleccionada){
                if(!tablero.mismoEquipo(piezas, rey) && (piezas.name.equals("Torre") || piezas.name.equals("Reina"))){
                    return true;
                }
                break;
            }

        }
        return false;
    }


    private boolean hitByAlfil(int col, int fil, Piezas rey, int reyCol, int reyFil, int colVal, int filVal){
        for (int i = 1; i < 8; i++){
            if (reyCol - (i * colVal) == col && reyFil - (i * filVal) == fil){
                break;
            }
            Piezas piezas = tablero.getPiezas(reyCol - ( i * colVal), reyFil - ( i * filVal));
            if (piezas != null && piezas != tablero.piezaSeleccionada){
                if(!tablero.mismoEquipo(piezas, rey) && (piezas.name.equals("Alfil") || piezas.name.equals("Reina"))){
                    return true;
                }
                break;
            }

        }
        return false;
    }

    private boolean hitByCaballo(int col, int fil, Piezas rey, int reyCol, int reyFil){

        return  jaqueCaballo(tablero.getPiezas(reyCol - 1, reyFil -2), rey, col, fil) ||
                jaqueCaballo(tablero.getPiezas(reyCol + 1, reyFil -2), rey, col, fil) ||
                jaqueCaballo(tablero.getPiezas(reyCol + 2, reyFil -1), rey, col, fil) ||
                jaqueCaballo(tablero.getPiezas(reyCol + 2, reyFil +1), rey, col, fil) ||
                jaqueCaballo(tablero.getPiezas(reyCol + 1, reyFil +2), rey, col, fil) ||
                jaqueCaballo(tablero.getPiezas(reyCol - 1, reyFil +2), rey, col, fil) ||
                jaqueCaballo(tablero.getPiezas(reyCol - 2, reyFil +1), rey, col, fil) ||
                jaqueCaballo(tablero.getPiezas(reyCol - 2, reyFil -1), rey, col, fil);


    }


    private boolean jaqueCaballo(Piezas p, Piezas k, int col, int fil){
        return p != null && !tablero.mismoEquipo(p, k) && p.name.equals("Caballo") && !(p.col == col && p.fil == fil);

    }


    private boolean hitByRey(Piezas rey, int reyCol, int reyFil){
        return checkRey(tablero.getPiezas(reyCol - 1, reyFil -1), rey) ||
                checkRey(tablero.getPiezas(reyCol + 1, reyFil -1), rey) ||
                checkRey(tablero.getPiezas(reyCol, reyFil -1), rey) ||
                checkRey(tablero.getPiezas(reyCol - 1, reyFil), rey) ||
                checkRey(tablero.getPiezas(reyCol + 1, reyFil), rey) ||
                checkRey(tablero.getPiezas(reyCol - 1, reyFil +1), rey) ||
                checkRey(tablero.getPiezas(reyCol + 1, reyFil +1), rey) ||
                checkRey(tablero.getPiezas(reyCol, reyFil + 1), rey);
    }

    private boolean checkRey(Piezas p, Piezas k){
        return p != null && !tablero.mismoEquipo(p, k) && p.name.equals("Rey");
    }

    private boolean hitByPeon(int col, int fil, Piezas rey, int reyCol, int reyFil){
        int colorVal = rey.isWhite ? -1 : 1;
        return checkPeon(tablero.getPiezas(reyCol +1, reyFil + colorVal), rey, col, fil) ||
                checkPeon(tablero.getPiezas(reyCol -1, reyFil + colorVal), rey, col, fil);
    }


    private boolean checkPeon(Piezas p, Piezas k, int col, int fil){
        return p != null && !tablero.mismoEquipo(p, k) && p.name.equals("Peon");
    }


    public boolean isGameOver(Piezas rey){

        for (Piezas piezas : tablero.piezasList){
            if (tablero.mismoEquipo(piezas, rey)) {
                tablero.piezaSeleccionada = piezas == rey ? rey :null;
                for (int fil = 0; fil < tablero.filas; fil ++){
                    for (int col = 0; col < tablero.columnas; col++){
                        Movimiento movimiento = new Movimiento(tablero, piezas, col, fil);
                        if (tablero.movimientoValido(movimiento)){
                            return false;
                        }
                    }
                }
            }

        }
        return true;
    }

}
