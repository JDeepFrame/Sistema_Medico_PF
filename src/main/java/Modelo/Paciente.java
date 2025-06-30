package Modelo;

/**
 *
 * @author J. Vidaurre Al.
 */

public class Paciente extends Persona {
    
    //Atributos
    private String tieneSIS;
    private Especialidad especialidad; //Enum
    
    //Constructores
    public Paciente() {
    }

    public Paciente(String tieneSIS, Especialidad especialidad, String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
        this.tieneSIS = tieneSIS;
        this.especialidad = especialidad;
    }
    
    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTieneSIS() {
        return tieneSIS;
    }

    public void setTieneSIS(String tieneSIS) {
        this.tieneSIS = tieneSIS;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    //toString()
    @Override
    public String toString() {
        return "Paciente: \n"
                + "Nombre(s): " + nombre + "\n"
                + "Apellido(s): " + apellido + "\n"
                + "DNI: " + dni + "\n"
                + "SIS: " + tieneSIS + "\n"
                + "Especialidad: " + especialidad + "\n";
    }
    
    //equals() y hashCode()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Mismo objeto en memoria
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Paciente otro = (Paciente) obj;
        return dni != null && dni.equals(otro.dni);
    }

    @Override
    public int hashCode() {
        return dni != null ? dni.hashCode() : 0;
    }
        
    //Métodos adicionales (si hubiera)
    
}
