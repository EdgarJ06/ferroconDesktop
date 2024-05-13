package Modelo;


/**
 * 
 */
public class Pais {

    public Pais() {
    }


    private int cod_pais;
    private String nom_pais;

    public int getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(int cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getNom_pais() {
        return nom_pais;
    }

    public void setNom_pais(String nom_pais) {
        this.nom_pais = nom_pais;
    }

    public Pais(int cod_pais, String nom_pais) {
        this.cod_pais = cod_pais;
        this.nom_pais = nom_pais;
    }


}