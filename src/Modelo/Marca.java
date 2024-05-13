package Modelo;

public class Marca {

    public Marca() {
    }
    private int cod_marca;
    private String nom_marca;

    public int getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(int cod_marca) {
        this.cod_marca = cod_marca;
    }

    public String getNom_marca() {
        return nom_marca;
    }

    public void setNom_marca(String nom_marca) {
        this.nom_marca = nom_marca;
    }

    public Marca(int cod_marca, String nom_marca) {
        this.cod_marca = cod_marca;
        this.nom_marca = nom_marca;
    }


}