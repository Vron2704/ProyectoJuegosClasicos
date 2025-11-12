package Solitario.Clases;

public class Carta {
    final private String palo, valor;
    
    public Carta(String color, String valor){
        this.palo = color;
        this.valor = valor;
    }
    public String getterPalo(){
        return palo;
    }
    public String getterValor(){
        return valor;
    }
}