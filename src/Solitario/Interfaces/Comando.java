package Solitario.Interfaces;
import Solitario.Clases.Carta;
import Solitario.Clases.Columna;

public interface Comando {
    void ejecutar(Carta carta, Columna columna);
}