package Solitario.Comandos;
import Solitario.Clases.Carta;
import Solitario.Clases.Columna;
import Solitario.Clases.Palo;
import java.util.*;
import Solitario.Interfaces.Comando;
public class ControladorComandos {
    
    final private Map<String, Comando> comandos = new HashMap<>();
    
    public void registrar(String llave, Comando cmd) {
        comandos.put(llave, cmd);
    }
    public void activar(String llave, Carta carta, Columna columna) {
        Comando cmd = comandos.get(llave);
        if (cmd != null){
            cmd.ejecutar(carta, columna);
        }else{
            System.out.println("Comando no registrado: " + llave);
        }
    }
}