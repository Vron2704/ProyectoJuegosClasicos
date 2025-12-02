package Solitario.Clases;
/*
Clase enum para poder establecel un color fijo para cada simbolo.

Contiene los cuatro simbolos posibles de la baraja.

Contiene metodos para inicializar el palo y para devolver el color.
*/
public enum Palo {
    ESPADA("NEGRO"){
        public String getSimbolo(){
            return "Espada";
        }
    },
    TREBOL("NEGRO"){
        public String getSimbolo(){
            return "Trebol";
        }
    },
    CORAZON("ROJO"){
        public String getSimbolo(){
            return "Corazon";
        }
    },
    DIAMANTE("ROJO"){
        public String getSimbolo(){
            return "Diamante";
        }
    };
    final private String color;
    Palo(String color){
        this.color = color;
    }
    public String getColor(){
        return color;
    }
    
}
