package Ajedrez.Piezas;
import Ajedrez.main.Tablero;

import java.awt.image.BufferedImage;

public class Reina extends Piezas{
    public Reina(Tablero tablero, int col, int fil, boolean isWhite){
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = col * tablero.celdaSize;
        this.yPos = fil * tablero.celdaSize;
        this.isWhite = isWhite;
        this.name = "Reina";

        this.sprite = sheet.getSubimage(sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(tablero.celdaSize, tablero.celdaSize, BufferedImage.SCALE_SMOOTH);

    }

    public boolean movimientoValido(int col, int fil){

        return this.col == col || this.fil == fil || Math.abs(this.col - col) == Math.abs(this.fil - fil);
    }

    public boolean MovimientoColision(int col, int fil){
        if (this.col == col || this.fil == fil) {

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
        } else {
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
        }


        return false;
    }

}
