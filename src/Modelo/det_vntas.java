package Modelo;


import java.util.*;

/**
 * 
 */
public class det_vntas {

    /**
     * Default constructor
     */
    public det_vntas() {
    }
  

    private String cod_vnta;
    private String cod_prod;
    private int cant;
    private Double precio;
    private Double iva;
    private Double importe;

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }
    

    public String getCod_vnta() {
        return cod_vnta;
    }

    public void setCod_vnta(String cod_vnta) {
        this.cod_vnta = cod_vnta;
    }

    public String getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(String cod_prod) {
        this.cod_prod = cod_prod;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public det_vntas(String cod_vnta, String cod_prod, int cant, Double precio, Double iva, Double importe) {
        this.cod_vnta = cod_vnta;
        this.cod_prod = cod_prod;
        this.cant = cant;
        this.precio = precio;
        this.iva = iva;
        this.importe = importe;
    }
    

   


}