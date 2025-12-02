package Solitario.Clases;
import java.util.Stack;
/*
Clase base de las columnas.

Contiene una columna que almacena Cartas.

Contiene metodos para inicializar la columna y devolverla.
*/
public class Columna {
    private final Stack <Carta> columna;
    private final String etiqueta;
    
    public Columna(Stack columna, String etiqueta){
        this.columna = columna;
        this.etiqueta = etiqueta;
    }
    public Stack<Carta> getStackCarta(){
        return columna;
    }
    public String getEtiqueta(){
        return etiqueta;
    }
    public void anadirCarta(Carta carta){
        columna.add(carta);
        carta.setColumnaActual(this);
    }
}