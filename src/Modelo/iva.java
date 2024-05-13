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
public class iva {

    private int cod_iva;
    private double val_iva;
    private boolean estado;

    public iva() {
    }

    public iva(int cod_iva, double val_iva, boolean estado) {
        this.cod_iva = cod_iva;
        this.val_iva = val_iva;
        this.estado = estado;
    }

    public int getCod_iva() {
        return cod_iva;
    }

    public void setCod_iva(int cod_iva) {
        this.cod_iva = cod_iva;
    }

    public double getVal_iva() {
        return val_iva;
    }

    public void setVal_iva(double val_iva) {
        this.val_iva = val_iva;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
