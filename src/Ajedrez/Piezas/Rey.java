package Ajedrez.Piezas;
import Ajedrez.main.Movimiento;
import Ajedrez.main.Tablero;

import java.awt.image.BufferedImage;

public class Rey extends Piezas{
    public Rey(Tablero tablero, int col, int fil, boolean isWhite){
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = col * tablero.celdaSize;
        this.yPos = fil * tablero.celdaSize;
        this.isWhite = isWhite;
        this.name = "Rey";

        this.sprite = sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(tablero.celdaSize, tablero.celdaSize, BufferedImage.SCALE_SMOOTH);

    }
    public boolean movimientoValido(int col, int fil){
        return Math.abs((col - this.col) * (fil - this.fil)) == 1 || Math.abs(col - this.col) + Math.abs(fil - this.fil) == 1 || Enroque(col, fil);
    }


    private boolean Enroque(int col, int fil){

       if (this.fil == fil){

           if (col == 6){
               Piezas torre = tablero.getPiezas(7, fil);
               if (torre != null && torre.isFirstMove && isFirstMove){
                   return tablero.getPiezas(5, fil) == null &&
                           tablero.getPiezas(6, fil) == null &&
                           !tablero.escanerJaque.ReyEnJaque(new Movimiento(tablero, this, 5, fil));
               }
           } else if (col == 2){
               Piezas torre = tablero.getPiezas(7, fil);
               if (torre != null && torre.isFirstMove && isFirstMove){
                   return tablero.getPiezas(5, fil) == null &&
                           tablero.getPiezas(2, fil) == null &&
                           tablero.getPiezas(1, fil) == null &&
                           !tablero.escanerJaque.ReyEnJaque(new Movimiento(tablero, this, 5, fil));
               }
           }
       }

        return false;

    }
}
