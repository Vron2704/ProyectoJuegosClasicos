package Solitario.Clases;
import Solitario.Interfaces.Comando;
public class ComandoAnadirCarta implements Comando{
    public Columna columna;
    public Carta carta;
    public ComandoAnadirCarta(Columna columna, Carta carta){
        this.columna = columna;
        this.carta = carta;
    }
    @Override
    public void ejecutar(){
        this.columna.anadirCarta(carta);
    }
}