package Ajedrez.Piezas;
import Ajedrez.main.Tablero;

import java.awt.image.BufferedImage;

public class Peon extends Piezas{
    public Peon(Tablero tablero, int col, int fil, boolean isWhite){
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = col * tablero.celdaSize;
        this.yPos = fil * tablero.celdaSize;
        this.isWhite = isWhite;
        this.name = "Peon";

        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(tablero.celdaSize, tablero.celdaSize, BufferedImage.SCALE_SMOOTH);

    }
    public boolean movimientoValido(int col, int fil){
         int colorIndex = isWhite ? 1 : -1;


         //push peon 1
        if (this.col == col && fil == this.fil - colorIndex && tablero.getPiezas(col, fil) == null)
            return true;


        //pushPawn2

        if (isFirstMove && this.col == col && fil == this.fil - colorIndex * 2 && tablero.getPiezas(col, fil) == null && tablero.getPiezas(col, fil + colorIndex) == null)
            return true;

        //captura
        if (col == this.col - 1 && fil == this.fil - colorIndex && tablero.getPiezas(col, fil) != null)
            return true;
        if (col == this.col + 1 && fil == this.fil - colorIndex && tablero.getPiezas(col, fil) != null)
            return true;

        //En pasantia left
        if(tablero.getTileNum(col, fil) == tablero.enPasantTitle && col == this.col - 1 && fil == this.fil - colorIndex && tablero.getPiezas(col, fil + colorIndex) != null){
            return true;
        }
        //En pasantia right
        if(tablero.getTileNum(col, fil) == tablero.enPasantTitle && col == this.col + 1 && fil == this.fil - colorIndex && tablero.getPiezas(col, fil + colorIndex) != null){
            return true;
        }



        return false;

    }

}
