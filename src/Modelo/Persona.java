package Modelo;


import java.util.*;

/**
 * 
 */
public class Persona {

    /**
     * Default constructor
     */
    public Persona() {
    }

    public String ced;
    public String nombre;
    public String apellido;
    public int cod_ciu;
    public String direccion;
    public String telefono;
    public String correo_elect;

   
      public Persona(Persona ob){
     this.ced = ob.ced;
     this.nombre = ob.nombre;
     this.apellido = ob.apellido;
     this.cod_ciu = ob.cod_ciu;
     this.correo_elect = ob.correo_elect;
     this.direccion = ob.direccion;
     this.telefono = ob.telefono;
     
     };

    public Persona(String ced, String nombre, String apellido, int cod_ciu, String direccion, String telefono, String correo_elect) {
        this.ced = ced;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cod_ciu = cod_ciu;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo_elect = correo_elect;
    }

    public String getCed() {
        return ced;
    }

    public void setCed(String ced) {
        this.ced = ced;
    }

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

    public int getCod_ciu() {
        return cod_ciu;
    }

    public void setCod_ciu(int cod_ciu) {
        this.cod_ciu = cod_ciu;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo_elect() {
        return correo_elect;
    }

    public void setCorreo_elect(String correo_elect) {
        this.correo_elect = correo_elect;
    }
   
   

}