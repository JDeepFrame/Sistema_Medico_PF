package Controlador;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TemaFlatLaf {
    public static void aplicarTema() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Error al aplicar FlatLaf: " + e);
        }
    }
}
