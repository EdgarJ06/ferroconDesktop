/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Leonel
 */
public class apert_caja {

    private int id_aper_caja;
    private int id_caja;
    private int id_user;
    private String fecha_hora_aper;
    private Double monto_ini;
    private String fecha_hora_cierr;
    private Double monto_final;
    private Boolean estado;

    public apert_caja() {
    }

    public apert_caja(int id_aper_caja, int id_caja, int id_user, String fecha_hora_aper, Double monto_ini, String fecha_hora_cierr, Double monto_final, Boolean estado) {
        this.id_aper_caja = id_aper_caja;
        this.id_caja = id_caja;
        this.id_user = id_user;
        this.fecha_hora_aper = fecha_hora_aper;
        this.monto_ini = monto_ini;
        this.fecha_hora_cierr = fecha_hora_cierr;
        this.monto_final = monto_final;
        this.estado = estado;
    }

    public int getId_aper_caja() {
        return id_aper_caja;
    }

    public void setId_aper_caja(int id_aper_caja) {
        this.id_aper_caja = id_aper_caja;
    }

    public int getId_caja() {
        return id_caja;
    }

    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getFecha_hora_aper() {
        return fecha_hora_aper;
    }

    public void setFecha_hora_aper(String fecha_hora_aper) {
        this.fecha_hora_aper = fecha_hora_aper;
    }

    public Double getMonto_ini() {
        return monto_ini;
    }

    public void setMonto_ini(Double monto_ini) {
        this.monto_ini = monto_ini;
    }

    public String getFecha_hora_cierr() {
        return fecha_hora_cierr;
    }

    public void setFecha_hora_cierr(String fecha_hora_cierr) {
        this.fecha_hora_cierr = fecha_hora_cierr;
    }

    public Double getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(Double monto_final) {
        this.monto_final = monto_final;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    

}
