package Modelo;

import java.util.*;

public class Cliente extends Persona {

    public int cod_clien;

    public Cliente() {
    }

    public Cliente(Cliente cl) {
        this.cod_clien = cl.cod_clien;
    }

    public Cliente(int cod_clien, Persona ob) {
        super(ob);
        this.cod_clien = cod_clien;
    }

    public Cliente(Persona ob) {
        super(ob);
    }

    public Cliente(int cod_clien, String ced, String nombre, String apellido, int cod_ciu, String direccion, String telefono, String correo_elect) {
        super(ced, nombre, apellido, cod_ciu, direccion, telefono, correo_elect);
        this.cod_clien = cod_clien;
    }

    public int getCod_clien() {
        return cod_clien;
    }

    public void setCod_clien(int cod_clien) {
        this.cod_clien = cod_clien;
    }

}
