package Ajedrez.Piezas;
import Ajedrez.main.Tablero;

import java.awt.image.BufferedImage;

public class Alfil extends Piezas{
    public Alfil(Tablero tablero, int col, int fil, boolean isWhite){
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = col * tablero.celdaSize;
        this.yPos = fil * tablero.celdaSize;
        this.isWhite = isWhite;
        this.name = "Alfil";

        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(tablero.celdaSize, tablero.celdaSize, BufferedImage.SCALE_SMOOTH);

    }
    public boolean movimientoValido(int col, int fil){
        return Math.abs(this.col - col) == Math.abs(this.fil - fil);
    }


    public boolean MovimientoColision(int col, int fil) {
        //arriba-izquierda
        if (this.col > col && this.fil > fil)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(tablero.getPiezas(this.col - i, this.fil - i) != null)
                    return true;
        //arriba-derecha
        if (this.col < col && this.fil > fil)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(tablero.getPiezas(this.col + i, this.fil - i) != null)
                    return true;
        //abajo-izquierda
        if (this.col > col && this.fil < fil)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(tablero.getPiezas(this.col - i, this.fil + i) != null)
                    return true;
        //abajo-derecha
        if (this.col < col && this.fil < fil)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(tablero.getPiezas(this.col + i, this.fil + i) != null)
                    return true;



    return false;
    }
}
