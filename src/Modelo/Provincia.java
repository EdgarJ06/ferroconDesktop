package Modelo;


public class Provincia {


    public Provincia() {
    }

    private int cod_prov;
    private int cod_pais;
    private String nom_prov;

    public int getCod_prov() {
        return cod_prov;
    }

    public void setCod_prov(int cod_prov) {
        this.cod_prov = cod_prov;
    }

    public int getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(int cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getNom_prov() {
        return nom_prov;
    }

    public void setNom_prov(String nom_prov) {
        this.nom_prov = nom_prov;
    }

    public Provincia(int cod_prov, int cod_pais, String nom_prov) {
        this.cod_prov = cod_prov;
        this.cod_pais = cod_pais;
        this.nom_prov = nom_prov;
    }




}