

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
        
        // Asociar botones de la vista principal para abrir las ventanas
        vistaPrincipal.getBotonAgregar().addActionListener(e -> abrirVentanaRegistro());
        vistaPrincipal.getBotonEliminar().addActionListener(e -> abrirEliminarPaciente());
        vistaPrincipal.getBotonMostrar().addActionListener(e -> abrirVentanaMostrarLista());
        vistaPrincipal.getBotonEditar().addActionListener(e -> abrirVentanaEdicion());
        vistaPrincipal.getBotonBuscar().addActionListener(e -> abrirVentanaBusquedaPaciente());
        
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
    
    
    private void abrirVentanaMostrarLista() {
        
        MostrarListaPacientes ventana = new MostrarListaPacientes();

        String contenido = modelo.obtenerContenidoArchivoComoTexto();

        ventana.setCampoLista(contenido);
        ventana.setVisible(true);
    }
    
    
    private void abrirVentanaEdicion() {
        
        EditarDatos1 editarForm = new EditarDatos1();
        editarForm.setVisible(true);

        editarForm.getBotonBuscar().addActionListener(e -> {
        String dni = editarForm.getDniIngresado();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(editarForm, "Debe ingresar el DNI.");
            return;
        }

        Paciente paciente = modelo.buscarPaciente(dni);

        if (paciente == null) {
            JOptionPane.showMessageDialog(editarForm, "No se encontró ningún paciente con ese DNI.");
            return;
        }

        // Puedes mostrar datos directamente o pasarlos a otra ventana
        String datos = String.format("DNI: %s\nNombre: %s\nApellido: %s\nEspecialidad: %s\nTiene SIS: %s",
                paciente.getDni(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getEspecialidad().getDescripcion(),
                paciente.getTieneSIS());

        JOptionPane.showMessageDialog(editarForm, datos);

        // Si quieres abrir otra ventana para editar los datos:
        abrirFormularioEdicionFinal(paciente);
        
        editarForm.dispose(); // Cierra esta ventana si ya no la necesitas
        });               
    }
    
    
    private void abrirFormularioEdicionFinal(Paciente paciente) {
        
        EditarDatosForm formulario = new EditarDatosForm();

        formulario.setDatosPaciente(paciente);  // <-- Aquí se cargan los datos

        formulario.setVisible(true);

        formulario.getBotonGuardar().addActionListener(e -> {
        Paciente editado = formulario.obtenerPacienteEditado();

        if (editado == null) {
            JOptionPane.showMessageDialog(formulario, "Por favor complete todos los campos correctamente.");
            return;
        }

        modelo.editarDatosPaciente(editado);
        JOptionPane.showMessageDialog(formulario, "Paciente actualizado exitosamente.");
        formulario.dispose();
        });
    }

    
    private void abrirVentanaBusquedaPaciente() {
        
        BuscarPaciente buscarPaciente = new BuscarPaciente();
        buscarPaciente.setVisible(true);

        buscarPaciente.getBotonBuscarPaciente().addActionListener(e -> {
        String dniIngresado = buscarPaciente.getDniIngresado();

        if (dniIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(buscarPaciente, "Debe ingresar un DNI.");
            return;
        }

        Paciente paciente = modelo.buscarPaciente(dniIngresado);

        if (paciente == null) {
            JOptionPane.showMessageDialog(buscarPaciente, "No se encontró ningún paciente con ese DNI.");
            return;
            }

            buscarPaciente.dispose(); // Cerrar ventana de búsqueda

            mostrarPacienteEncontrado(paciente);
        });
    }
    
    
    private void mostrarPacienteEncontrado(Paciente paciente) {
        
        PacienteEncontrado ventana = new PacienteEncontrado();

        ventana.setNombre(paciente.getNombre());
        ventana.setApellido(paciente.getApellido());
        ventana.setDni(paciente.getDni());
        ventana.setSis(paciente.getTieneSIS());
        ventana.setEspecialidad(paciente.getEspecialidad().getDescripcion());

        ventana.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        
        // Aplicar el tema antes de cargar interfaces
        TemaFlatLaf.aplicarTema();

        java.awt.EventQueue.invokeLater(() -> {
            new SistemaMedico(); // Controlador inicia todo
        });        
    }    
}