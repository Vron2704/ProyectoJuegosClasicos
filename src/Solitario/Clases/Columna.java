package Solitario.Clases;
import java.util.Stack;
public class Columna {
    final Stack <Carta> columna;
    
    public Columna(Stack columna){
        this.columna = columna;
    }
    public Carta sacarCarta(){
        return columna.pop();
    }
    public Carta mostrarCarta(){
        return columna.peek();
    }
    public void anadirCarta(Carta carta){
        columna.add(carta);
    }
    public String imprimirCarta(){
        return "Carta: "+columna.peek().getterPalo()+" "+columna.peek().getterValor();
    }
}
