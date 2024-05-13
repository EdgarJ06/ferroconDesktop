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
public class tipo_usuario {
    private int cod_user;
    private String descr;

    public tipo_usuario() {
    }

    public tipo_usuario(int cod_user, String descr) {
        this.cod_user = cod_user;
        this.descr = descr;
    }

    public int getCod_user() {
        return cod_user;
    }

    public void setCod_user(int cod_user) {
        this.cod_user = cod_user;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    
    
    
}
