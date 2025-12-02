package Solitario.Comandos;
import Solitario.Clases.Carta;
import Solitario.Clases.Columna;
import Solitario.Interfaces.Comando;
public class ComandoMoverCarta implements Comando{
        
    @Override
    public void ejecutar(Carta carta, Columna columna){
        columna.getStackCarta().add(carta);
    }
    
}
