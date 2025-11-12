package Solitario.Clases;
import Solitario.Interfaces.Comando;

public class ComandoSacarCarta implements Comando{
    
    public Columna columna;
    
    public ComandoSacarCarta(Columna columna){
        this.columna = columna;
    }
    @Override
    public void ejecutar(){
        this.columna.sacarCarta();
    }
}
