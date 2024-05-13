package Modelo;


import java.util.*;

/**
 * 
 */
public class Ciudad {

    public Ciudad() {
    }

    private int cod_ciu;
    private int cod_prov;
    private String nom_ciu;

    public Ciudad(int cod_ciu, int cod_prov, String nom_ciu) {
        this.cod_ciu = cod_ciu;
        this.cod_prov = cod_prov;
        this.nom_ciu = nom_ciu;
    }

    public int getCod_ciu() {
        return cod_ciu;
    }

    public void setCod_ciu(int cod_ciu) {
        this.cod_ciu = cod_ciu;
    }

    public int getCod_prov() {
        return cod_prov;
    }

    public void setCod_prov(int cod_prov) {
        this.cod_prov = cod_prov;
    }

    public String getNom_ciu() {
        return nom_ciu;
    }

    public void setNom_ciu(String nom_ciu) {
        this.nom_ciu = nom_ciu;
    }





}