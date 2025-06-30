package Modelo;

/**
 *
 * @author J. Vidaurre Al.
 */

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class ListaPacientes {
    
    //Atributos
    private List<Paciente> listaPacientes;
    private Map<String, Paciente> mapPacientes;
    private final String rutaArchivo = "data/pacientes.txt";
    
    //Constructor
    public ListaPacientes () {
        this.listaPacientes = new ArrayList<>();
        this.mapPacientes = new HashMap<>();
        crearArchivoSiNoExiste();
        cargarPacientesDesdeArchivo();
    }
    
    //Métodos Principales (CRUD)
    public ListaPacientes agregarPaciente(Paciente element) {
        this.listaPacientes.add(element);
        this.mapPacientes.put(element.getDni(), element);
        escribirPacienteEnArchivo(element);
        return this;
    }
    
    public ListaPacientes eliminarPaciente(Paciente element) {
        if (element != null) {
            this.listaPacientes.remove(element);
            this.mapPacientes.remove(element.getDni());
            sobrescribirArchivoCompleto();
        }       
        return this;
    }
    
    public Paciente buscarPaciente(String dni) {
        return this.mapPacientes.get(dni);
        //Si el DNI no existe, devuelve null.
    }
    
    public void obtenerTodos() {
        for (Paciente element : this.listaPacientes) {
            System.out.println(element);
        }
    }
    
    public ListaPacientes editarDatosPaciente(Paciente element) {
        if (element != null) {
            Paciente existente = this.mapPacientes.get(element.getDni());
            if (existente != null) {
                existente.setNombre(element.getNombre());
                existente.setApellido(element.getApellido());
                existente.setDni(element.getDni());
                existente.setTieneSIS(element.getTieneSIS());
                existente.setEspecialidad(element.getEspecialidad());
                //Dentro de este if:
                //Agregamos setters y getters según atributos de Paciente.
                //Atributos: nombre, apellido, dni, tieneSIS, especialidad
                
                sobrescribirArchivoCompleto();
                //Reescribe archivo completo con los datos editados.
            }
        }   return this;
    }
        
    // ---------------------------
    // Manejo de archivo de texto
    // ---------------------------
    
    // Crea el archivo y carpeta si no existen
    private void crearArchivoSiNoExiste() {
        File archivo = new File(rutaArchivo);
        try {
            File carpeta = archivo.getParentFile();
            if (carpeta != null && !carpeta.exists()) {
                carpeta.mkdirs(); // crea carpeta "data/"
            }
            if (!archivo.exists()) {
                archivo.createNewFile(); // crea archivo vacío
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                //writer.write("DNI || Nombre || Apellido || Especialidad || Tiene SIS");
                String cabecera = String.format("%-10s || %-20s || %-20s || %-20s || %-10s",
                "DNI", "Nombre", "Apellido", "Especialidad", "Tiene SIS");
                writer.write(cabecera);
                writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo de pacientes: " + e.getMessage());
        }
    }
    
    //Método para leer datos del archivo
    private void cargarPacientesDesdeArchivo() {
    File archivo = new File(rutaArchivo);
    if (!archivo.exists()) return;

    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        int lineaActual = 0;

        while ((linea = reader.readLine()) != null) {
            lineaActual++;

            // Dividir por separador ||, tolerando espacios alrededor
            String[] partes = linea.split("\\s*\\|\\|\\s*");

            if (partes.length != 5) {
                System.err.printf("Línea %d ignorada: formato inválido%n", lineaActual);
                continue;
            }

            String dni = partes[0];
            String nombre = partes[1];
            String apellido = partes[2];
            String especialidadStr = partes[3];
            String sisStr = partes[4];

            // Validar campos no vacíos
            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                    || especialidadStr.isEmpty() || sisStr.isEmpty()) {
                System.err.printf("Línea %d ignorada: campos vacíos%n", lineaActual);
                continue;
            }

            // Convertir especialidad a Enum
            Especialidad especialidad = null;
            for (Especialidad esp : Especialidad.values()) {
                if (esp.getDescripcion().equalsIgnoreCase(especialidadStr)) {
                    especialidad = esp;
                    break;
                }
            }

            if (especialidad == null) {
                System.err.printf("Línea %d ignorada: especialidad desconocida '%s'%n", lineaActual, especialidadStr);
                continue;
            }

            // Construir y guardar paciente
            Paciente paciente = new Paciente(sisStr, especialidad, nombre, apellido, dni);
            listaPacientes.add(paciente);
            mapPacientes.put(dni, paciente);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Agrega una línea al archivo (modo append)
    private void escribirPacienteEnArchivo(Paciente p) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            writer.write(formatoPaciente(p));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Reescribe el archivo completo con la lista actual de pacientes
    private void sobrescribirArchivoCompleto() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            //writer.write("DNI || Nombre || Apellido || Especialidad || Tiene SIS");
            String cabecera = String.format("%-10s || %-20s || %-20s || %-20s || %-10s",
            "DNI", "Nombre", "Apellido", "Especialidad", "Tiene SIS");
            writer.write(cabecera);
            writer.newLine();
            for (Paciente p : listaPacientes) {
                writer.write(formatoPaciente(p));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al sobrescribir archivo: " + e.getMessage());
        }
    }

    // Define el formato en que se guarda cada paciente (CSV)
    private String formatoPaciente(Paciente p) {
        return String.format("%-10s || %-20s || %-20s || %-20s || %-10s",
                p.getDni(),
                p.getNombre(),
                p.getApellido(),
                p.getEspecialidad(),
                p.getTieneSIS());
    }
    
    //Método para mostrar el contenido del archivo .txt (por consola)
    public void mostrarContenidoArchivo() {
    File archivo = new File(rutaArchivo);
    if (!archivo.exists()) {
        System.out.println("El archivo no existe.");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        System.out.println("Contenido del archivo:");
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }
    }
    catch (IOException e) {
        System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
}
