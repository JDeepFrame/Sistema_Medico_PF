package Modelo;
/**
 *
 * @author J. Vidaurre Al.
 */

//Molde para crear personas
public abstract class Persona {
    
    protected String nombre;
    protected String apellido;
    protected String dni;

    protected Persona() {
    }

    protected Persona(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    
}
