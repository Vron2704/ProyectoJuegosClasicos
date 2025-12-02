package Menus;

import Ajedrez.main.Tablero;

import javax.swing.*;
import java.awt.*;

public class TableroAjedrez extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame .getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);
        Ajedrez.main.Tablero tablero = new Tablero();
        frame.add(tablero);
        frame.setVisible(true);
    }
}
