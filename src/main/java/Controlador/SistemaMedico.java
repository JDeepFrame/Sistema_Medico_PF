package Controlador;

import Vista.VentanaPrincipal;

public class SistemaMedico {

    public static void main(String[] args) {
        
        //Aplicar el tema antes de cargar interfaces
        TemaFlatLaf.aplicarTema();
        
        //Iniciar la vista principal
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
        
        //Esta parte del código debe estar comentada paso a paso
    }
}