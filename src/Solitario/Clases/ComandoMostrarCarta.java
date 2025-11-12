package Solitario.Clases;
import Solitario.Interfaces.Comando;
public class ComandoMostrarCarta implements Comando{
    
    public Columna columna;
    
    public ComandoMostrarCarta(Columna columna){
        this.columna = columna;
    }
    @Override
    public void ejecutar(){
        columna.mostrarCarta();
    }
    
}
