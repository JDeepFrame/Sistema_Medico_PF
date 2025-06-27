package Modelo;
/**
 *
 * @author J. Vidaurre Al.
 */
public abstract class Persona {
    
    protected String nombre;
    protected String apellidos;
    protected String dni;

    protected Persona() {
    }

    protected Persona(String nombre, String apellidos, String dni) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
    }
    
    protected abstract <T> void nostrarDatos();    
}
