package Ludo;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Jugador {
    String nombre;
    Color color;
    List<Ficha> fichas;
    List<Point> rutaCoordenadas;
    Point casaBase;

    public Jugador(String nombre, Color color, List<Point> ruta, Point casaBase) {
        this.nombre = nombre;
        this.color = color;
        this.rutaCoordenadas = ruta;
        this.casaBase = casaBase;
        this.fichas = new ArrayList<>();
        // Crear 4 fichas para el jugador
        for (int i = 0; i < 4; i++) {
            this.fichas.add(new Ficha(i));
        }
    }

    public Color getColorAwt() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }
}