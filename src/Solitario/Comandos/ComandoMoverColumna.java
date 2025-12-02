package Solitario.Comandos;
import Solitario.Clases.Carta;
import Solitario.Clases.Columna;
import Solitario.Interfaces.Comando;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
public class ComandoMoverColumna implements Comando{
    
    @Override
    public void ejecutar(Carta carta, Columna columna){
        int indexCarta = carta.getColumnaActual().getStackCarta().indexOf(carta);
        Stack<Carta>stack = carta.getColumnaActual().getStackCarta();
        List<Carta> subcolumna = new ArrayList<>(stack.subList(indexCarta, stack.size()));

        while (stack.size()>indexCarta){
            stack.pop();
        }

        for (Carta c : subcolumna) {
            columna.anadirCarta(c);
            c.setColumnaActual(columna);
        }
    }
    
}
