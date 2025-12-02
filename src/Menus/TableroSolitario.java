package Menus;

import Solitario.Clases.Mazo;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class TableroSolitario extends JFrame {

    public TableroSolitario() {
        super("Solitario");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Mazo mazo = new Mazo();
        mazo.crearColumnasPrincipales();
        mazo.crearColumnasSimbolos();
        mazo.crearColumnasExtras();
        mazo.crearCartas();
        mazo.distribuirCartas();

        CartaPanel panel = new CartaPanel(mazo);
        add(panel, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TableroSolitario();
    }
}
