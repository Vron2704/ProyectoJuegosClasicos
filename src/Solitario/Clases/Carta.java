package Solitario.Clases;
import java.awt.Image;
import javax.imageio.ImageIO;

/*
Clase base de las cartas.

Contiene valores int para los numeros y Palo para los simbolos y los colores.

Contiene metodos para inicializar la carta y devolver sus valores.
*/


public class Carta {
    final private Palo palo;
    final private int valor;
    private Columna columnaActual;
    private boolean esBocaArriba = false;
    private final Image imagenBocaArriba;
    private static Image imagenBocaAbajo;
    // Bloque estático: se ejecuta 1 sola vez al cargar la clase
    static {
        try {
            imagenBocaAbajo = ImageIO.read(Carta.class.getResource("/Recursos/EspaldaCarta.png"));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("No se pudo cargar la imagen de la carta boca abajo.");
        }
    }
    
    public Carta(Palo palo, int valor, Image imagen){
        this.valor = valor;
        this.palo = palo;
        imagenBocaArriba = imagen;
    }
    public Palo getPalo(){
        return palo;
    }
    public int getValor(){
        return valor;
    }
    public boolean getEsBocaArriba(){
        return esBocaArriba;
    }
    public void setEsBocaArriba(boolean valor){
        esBocaArriba = valor;
    }
    public void setColumnaActual(Columna columnaActual){
        this.columnaActual = columnaActual;
    }
    public Columna getColumnaActual(){
        return columnaActual;
    }
    public Image getImagenBocaArriba(){
        return imagenBocaArriba;
    }
    public Image getImagenBocaAbajo(){
        return imagenBocaAbajo;
    }

    public boolean isBocaArriba() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}