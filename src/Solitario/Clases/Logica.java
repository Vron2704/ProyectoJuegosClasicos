package Solitario.Clases;
import Solitario.Comandos.ControladorComandos;
/*
Clase encargada de almacenar la logica del juego.
*/
public class Logica {
    public static void main(String[] args){
        ControladorComandos control = new ControladorComandos();
        Mazo m = new Mazo();
        m.crearColumnasPrincipales();
        m.crearColumnasSimbolos();
        m.crearColumnasExtras();
        m.crearCartas();
        m.distribuirCartas();  
    }
    public static boolean evaluarMovimiento(Carta carta, Columna columna){
        String etiqueta = columna.getEtiqueta();
        boolean esColumnaExtra = etiqueta.equals("Columna extra 1") || etiqueta.equals("Columna extra 2");
        boolean esColumnaSimbolo = etiqueta.equals("Columna ESPADA") || etiqueta.equals("Columna CORAZON") ||
                                   etiqueta.equals("Columna DIAMANTE") || etiqueta.equals("Columna TREBOL");
        boolean esVacio = columna.getStackCarta().isEmpty();
        Carta columnaTope = esVacio ? null : columna.getStackCarta().peek();
        
        if(esColumnaExtra){
            System.out.println("Error: No puedes colocar cartas alli.");
            return false;
        }
        if (esColumnaSimbolo){
            if (esVacio) {
                if (carta.getValor() == 1 &&
                        etiqueta.equals("Columna " + carta.getPalo().name())) {
                    return true;
                } else{
            System.out.println("Error: Solo puedes colocar el As del palo correspondiente en esta columna.");
            return false;
        }
    }

    if (carta.getPalo().equals(columnaTope.getPalo()) &&
        carta.getValor() == columnaTope.getValor() + 1) {
        return true;
    }

    System.out.println("Error: El orden en columnas de símbolo es ascendente y del mismo palo.");
    return false;
        }

        
        if(esVacio){
            if(carta.getValor()==13){
                return true;
            }
            System.out.println("Error: Solo puedes colocar reyes en las columnas vacias.");
            return false;
        }
        if(carta.getPalo().getColor()!=columnaTope.getPalo().getColor() &&
        carta.getValor()==columnaTope.getValor()-1){
            return true;
        }
        
        System.out.println("Error: El orden de las columnas es descendente y de colores alternados.");
        return false;
    }
    public static boolean evaluarJuegoCompletado(Mazo m){
        Columna[] colSim = {
            m.getColumnas().get("Columna CORAZON"),
            m.getColumnas().get("Columna TREBOL"),
            m.getColumnas().get("Columna DIAMANTE"),
            m.getColumnas().get("Columna ESPADA")
        };
        
        for (Columna c : colSim) {
            if (c.getStackCarta().isEmpty()){
                return false;
            }
            if (c.getStackCarta().peek().getValor() != 13){
                return false;
            }
        }
        System.out.println("Juego Terminado: Todas las columnas de simbolo llegaron al rey.");
        return true;
    } 
}