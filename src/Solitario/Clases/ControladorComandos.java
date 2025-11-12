package Solitario.Clases;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import Solitario.Interfaces.Comando;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ControladorComandos {
    
    final private Map<String, Comando> comandos = new HashMap<>();
    final private Map<String, Columna> columnas = new HashMap<>();
    final private List<Carta> mazo = new ArrayList<>();
    
    public void registrar(String llave, Comando cmd) {
        comandos.put(llave, cmd);
    }
    public void activar(String llave) {
        Comando cmd = comandos.get(llave);
        if (cmd != null){
            cmd.ejecutar();
        }else{
            System.out.println("Comando no registrado: " + llave);
        }
    }
    public void crearCartas(){
        String[] palos = {"Espada", "Trebol", "Corazon", "Diamante"};
        for(String arreglo : palos){
            for(int i=1; i<=13;i++){
                String valorI = String.valueOf(i);
                Carta c = new Carta(arreglo, valorI);
                mazo.add(c);   
            }
            System.out.println("Cartas de "+arreglo+" creadas");
        }
    }
    public void crearColumnas(){
        for(int i=1; i<=7;i++){
            System.out.println("Columna "+i+" creada");
            Stack<Carta>stack = new Stack<>();
            Columna col = new Columna(stack);
            columnas.put(String.valueOf(i), col);
        }
    }
    public void distribuirCartas(){
        Collections.shuffle(mazo);
        int contador = 0;
        for(int i=1; i<=7; i++){
            Columna col = columnas.get(String.valueOf(i));
            for(int j=1; j<=i; j++){
                Carta car = mazo.get(contador);
                col.anadirCarta(car);
                contador++;
            }
        }
        System.out.println("Cartas repartidas correctamente");
    }
    public void verColumnas(){
        for(int i=1; i<=7;i++){
            System.out.println("Columna "+i+":");
            System.out.println(columnas.get(String.valueOf(i)).imprimirCarta());
        }
    }
    public static void main(String[] args) {
        ControladorComandos ctl = new ControladorComandos();
        ctl.crearCartas();
        ctl.crearColumnas();
        ctl.distribuirCartas();
        ctl.verColumnas();
    }
}