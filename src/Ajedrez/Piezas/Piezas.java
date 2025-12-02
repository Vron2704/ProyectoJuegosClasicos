package Ajedrez.Piezas;

import Ajedrez.main.Tablero;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Piezas {
    public int col, fil;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public int value;
    public boolean isFirstMove = true ;



    BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("Ajedrez/resources/piezas.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth() / 6;

    Image sprite;
    Tablero tablero;

    public Piezas(Tablero tablero) { this.tablero = tablero;}

    public boolean movimientoValido(int col, int fil){return true;}
    public boolean MovimientoColision(int col, int fil){return false;}


    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
