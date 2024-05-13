/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Edgar
 */
public class producto_compra {

    private int num_prod;
    private String cod_prod;
    private int cod_marca;
    private int cod_tip_prend;
    private double prec_cmpra;
    private Double descuento;
    private boolean iva;
    private String descripcion;
    private String observ;

    public producto_compra() {
    }

    public producto_compra(int num_prod, String cod_prod, int cod_marca, int cod_tip_prend, double prec_cmpra, Double descuento, boolean iva, String descripcion, String observ) {
        this.num_prod = num_prod;
        this.cod_prod = cod_prod;
        this.cod_marca = cod_marca;
        this.cod_tip_prend = cod_tip_prend;
        this.prec_cmpra = prec_cmpra;
        this.descuento = descuento;
        this.iva = iva;
        this.descripcion = descripcion;
        this.observ = observ;
    }

  

    
    public int getNum_prod() {
        return num_prod;
    }

    public void setNum_prod(int num_prod) {
        this.num_prod = num_prod;
    }

    public String getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(String cod_prod) {
        this.cod_prod = cod_prod;
    }

    public int getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(int cod_marca) {
        this.cod_marca = cod_marca;
    }

    public int getCod_tip_prend() {
        return cod_tip_prend;
    }

    public void setCod_tip_prend(int cod_tip_prend) {
        this.cod_tip_prend = cod_tip_prend;
    }

    public double getPrec_cmpra() {
        return prec_cmpra;
    }

    public void setPrec_cmpra(double prec_cmpra) {
        this.prec_cmpra = prec_cmpra;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }


    public boolean isIva() {
        return iva;
    }

    public void setIva(boolean iva) {
        this.iva = iva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

   
    

}
