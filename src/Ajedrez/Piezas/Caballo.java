package Ajedrez.Piezas;
import Ajedrez.main.Tablero;

import java.awt.image.BufferedImage;

public class Caballo extends Piezas{
    public Caballo(Tablero tablero, int col, int fil, boolean isWhite){
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = col * tablero.celdaSize;
        this.yPos = fil * tablero.celdaSize;
        this.isWhite = isWhite;
        this.name = "Caballo";

        this.sprite = sheet.getSubimage(3 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(tablero.celdaSize, tablero.celdaSize, BufferedImage.SCALE_SMOOTH);

    }

    public boolean movimientoValido(int col, int fil){
        return Math.abs(col - this.col) * Math.abs(fil - this.fil) == 2;
    }

}
