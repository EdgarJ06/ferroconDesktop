package Modelo;

import java.util.*;

/**
 *
 */
public class Proveedores {

    /**
     * Default constructor
     */
    private int cod_prove;
    private int cod_ciu;
    private String ced_ruc;
    private String nom_prove;
    private String dir_prov;
    private String correo_prove;
    private String telefono;

    public Proveedores() {
    }
    
    

    public Proveedores(int cod_prove, int cod_ciu, String ced_ruc, String nom_prove, String dir_prov, String correo_prove, String telefono) {
        this.cod_prove = cod_prove;
        this.cod_ciu = cod_ciu;
        this.ced_ruc = ced_ruc;
        this.nom_prove = nom_prove;
        this.dir_prov = dir_prov;
        this.correo_prove = correo_prove;
        this.telefono = telefono;
    }

    public int getCod_prove() {
        return cod_prove;
    }

    public void setCod_prove(int cod_prove) {
        this.cod_prove = cod_prove;
    }

    public int getCod_ciu() {
        return cod_ciu;
    }

    public void setCod_ciu(int cod_ciu) {
        this.cod_ciu = cod_ciu;
    }

    public String getCed_ruc() {
        return ced_ruc;
    }

    public void setCed_ruc(String ced_ruc) {
        this.ced_ruc = ced_ruc;
    }

    public String getNom_prove() {
        return nom_prove;
    }

    public void setNom_prove(String nom_prove) {
        this.nom_prove = nom_prove;
    }

    public String getDir_prov() {
        return dir_prov;
    }

    public void setDir_prov(String dir_prov) {
        this.dir_prov = dir_prov;
    }

    public String getCorreo_prove() {
        return correo_prove;
    }

    public void setCorreo_prove(String correo_prove) {
        this.correo_prove = correo_prove;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
