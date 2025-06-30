package Controlador;

import Modelo.Paciente;
import Modelo.ListaPacientes;
import Modelo.Especialidad;
import Vista.*;
import javax.swing.JOptionPane;


public class SistemaMedico {
    
    private VentanaPrincipal vistaPrincipal;
    private ListaPacientes modelo;

    public SistemaMedico() {
        this.vistaPrincipal = new VentanaPrincipal();
        this.modelo = new ListaPacientes();
        
        // Asociar botón de la vista principal para abrir el registro de pacientes
        vistaPrincipal.getBotonAgregar().addActionListener(e -> abrirVentanaRegistro());
        vistaPrincipal.getBotonEliminar().addActionListener(e -> abrirEliminarPaciente());
        vistaPrincipal.getBotonMostrar().addActionListener(e -> modelo.mostrarContenidoArchivo());
        vistaPrincipal.setVisible(true);
    }
    
    private void abrirVentanaRegistro() {
        //Crea una nueva instancia de la clase registro
        RegistroPaciente registro = new RegistroPaciente();
        registro.setVisible(true);
        
        registro.getBotonAgregar().addActionListener(e ->{
            String nombre = registro.getNombreIngresado();
            String apellido = registro.getApellidoIngresado();
            String dni = registro.getDniIngresado();
            String especialidad = registro.getEspecialidadIngresada();
            String sis = registro.getTieneSIS();
            
        //Validar campos básicos
        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() 
            ||especialidad == null || sis.isEmpty()) {
            JOptionPane.showMessageDialog(registro, "Debe completar todos los campos.");
            return;
        }
            
        // Conversión manual de String a Enum
        Especialidad especialidadEnum = null;

        for (Especialidad esp : Especialidad.values()) {
            if (esp.getDescripcion().equals(especialidad)) {
                especialidadEnum = esp;
                break;
                }
        }

        if (especialidadEnum == null) {
            JOptionPane.showMessageDialog(registro, "Especialidad inválida. Verifique la selección.");
            return;
        }

        // Crear y registrar paciente
        Paciente nuevo = new Paciente(sis, especialidadEnum, nombre, apellido, dni);
        modelo.agregarPaciente(nuevo);

        JOptionPane.showMessageDialog(registro, "Paciente registrado correctamente.");

        registro.dispose(); //Cierra después de guardar
            
        });
    }
    
    private void abrirEliminarPaciente() {
        //Crea una nueva instancia de la clase EliminarPaciente
        EliminarPaciente eliminarP = new EliminarPaciente();
        eliminarP.setVisible(true);
        
        eliminarP.getBotonEliminar().addActionListener(e -> {
            String dni = eliminarP.getDniIngresado();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(eliminarP, "Ingrese el DNI del paciente a eliminar.");
            return;
        }

        Paciente paciente = modelo.buscarPaciente(dni);

        if (paciente == null) {
            JOptionPane.showMessageDialog(eliminarP, "No se encontró ningún paciente con ese DNI.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                eliminarP,
                "¿Está seguro de que desea eliminar al paciente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            modelo.eliminarPaciente(paciente);
            JOptionPane.showMessageDialog(eliminarP, "Paciente eliminado correctamente.");
            eliminarP.dispose(); // cerrar la ventana
            }          
        });
    }
    
    public static void main(String[] args) {
        
        // Aplicar el tema antes de cargar interfaces
        TemaFlatLaf.aplicarTema();

        java.awt.EventQueue.invokeLater(() -> {
            new SistemaMedico(); // Controlador inicia todo
        });              
    }
}