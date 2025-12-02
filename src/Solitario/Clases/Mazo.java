package Solitario.Clases;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.imageio.ImageIO;
/*
Clase que crea las cartas y las guarda en una lista, crea las columnas y distribuye las cartas entre las columnas.

Contiene una lista que almacena Cartas distinta a una Columna y un mapa que almacena las Columnas.

Contiene metodos para crear las cartas y guardarlas en la lista, para crear todas las columnas, repartir las cartas
entre las columnas, para devolver el mapa de Columnas y para devolver el Mazo.
*/
public class Mazo {
    
    final private List<Carta> mazo = new ArrayList<>();
    final private Map<String, Columna> mapColumnas = new HashMap<>();

    public void crearColumnasPrincipales(){
        for(int i=1; i<=7;i++){
            Stack<Carta>stack = new Stack<>();
            Columna col = new Columna(stack, "Columna "+String.valueOf(i));
            mapColumnas.put("Columna "+String.valueOf(i), col);
        }
    }
    public void crearColumnasSimbolos(){
        Palo[] palos = {Palo.CORAZON, Palo.DIAMANTE, Palo.ESPADA, Palo.TREBOL};
        for(Palo p : palos){
            Stack<Carta>stack = new Stack<>();
            Columna col = new Columna(stack, "Columna "+p.name());
            mapColumnas.put("Columna "+p.name(), col);

        }
    }
    public void crearColumnasExtras(){
        for(int i=1; i<=2;i++){
            Stack<Carta>stack = new Stack<>();
            Columna col = new Columna(stack, "Columna extra "+String.valueOf(i));
            mapColumnas.put("Columna extra "+String.valueOf(i), col);
        }
    }
    public void crearCartas(){
        Palo[] palos = {Palo.CORAZON, Palo.DIAMANTE, Palo.ESPADA, Palo.TREBOL};
        for(Palo p : palos){
            for(int i=1; i<=13;i++){
                String nombrePalo = p.name().substring(0, 1) + p.name().substring(1).toLowerCase();
                String ruta = "/Recursos/" + nombrePalo + i + ".png";
                Image img = null;
                try {
                    img = ImageIO.read(getClass().getResource(ruta));
                } catch (Exception e) {
                    System.out.println("No se encontro la imagen: " + ruta);
                }
                Carta c = new Carta(p, i, img);
                mazo.add(c);
            }
        }
    }
    public void distribuirCartas(){
        Collections.shuffle(mazo);
        Columna col;
        for(int i=1; i<=7; i++){
            col = mapColumnas.get("Columna "+String.valueOf(i));
            for(int j=1; j<=i; j++){
                Carta car = mazo.remove(0);
                col.anadirCarta(car);
                if(j == i){
                    car.setEsBocaArriba(true);
                }
            }
        }
        col = mapColumnas.get("Columna extra 1");
        while(!mazo.isEmpty()){
            Carta car = mazo.remove(0);
            col.anadirCarta(car);
        }
        // al final de distribuirCartas(), después de haber repartido:
        for (int i = 1; i <= 2; i++) {
            col = mapColumnas.get("Columna extra " + i);
            if (!col.getStackCarta().isEmpty()) {
        col.getStackCarta().peek().setEsBocaArriba(true);
    }
}

    }
    public Map<String, Columna> getColumnas(){
        return mapColumnas;
    }
    public List<Carta> getMazo(){
        return mazo;
    }
}