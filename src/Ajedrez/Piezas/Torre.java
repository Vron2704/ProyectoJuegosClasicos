package Ajedrez.Piezas;
import Ajedrez.main.Tablero;

import java.awt.image.BufferedImage;

public class Torre extends Piezas{
    public Torre(Tablero tablero, int col, int fil, boolean isWhite){
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = col * tablero.celdaSize;
        this.yPos = fil * tablero.celdaSize;
        this.isWhite = isWhite;
        this.name = "Torre";

        this.sprite = sheet.getSubimage(4 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(tablero.celdaSize, tablero.celdaSize, BufferedImage.SCALE_SMOOTH);

    }
    public boolean movimientoValido(int col, int fil){

        return this.col == col || this.fil == fil;
    }

    public boolean MovimientoColision(int col, int fil){

        //izquierda
        if (this.col > col)
            for (int c = this.col - 1; c > col; c--)
                if (tablero.getPiezas(c, this.fil) != null)
                    return true;

        //derecha
        if (this.col < col)
            for (int c = this.col + 1; c < col; c++)
                if (tablero.getPiezas(c, this.fil) != null)
                    return true;

        //Arriba
        if (this.fil > fil)
            for (int r = this.fil - 1; r > fil; r--)
                if (tablero.getPiezas(this.col, r) != null)
                    return true;

        //abajo
        if (this.fil < fil)
            for (int r = this.fil + 1; r < fil; r++)
                if (tablero.getPiezas(this.col, r) != null)
                    return true;


        return false;
    }
}
