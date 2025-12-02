package Solitario.Comandos;
import Solitario.Clases.Carta;
import Solitario.Clases.Columna;
import Solitario.Interfaces.Comando;
public class ComandoVoltearCarta implements Comando{
        
    @Override
    public void ejecutar(Carta carta, Columna columna){
        if(carta.getEsBocaArriba()){
            carta.setEsBocaArriba(false);
        }else{
            carta.setEsBocaArriba(true);
        }
    } 
}