package Modelo;
/**
 *
 * @author J. Vidaurre Al.
 */
public abstract class Persona {
    
    protected String nombre;
    protected String apellidos;
    protected String dni;
    protected boolean tieneSIS;

    protected Persona() {
    }

    protected Persona(String nombre, String apellidos, String dni, boolean tieneSIS) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.tieneSIS = tieneSIS;
    }
    
    public abstract <F> void nostrarDatos(F nombre, F apellidos, F dni, F tieneSIS);    
}
