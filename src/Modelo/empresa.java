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
public class empresa {
    
    private int cod_empresa;
    private String nom_empresa;
    private String ruc;
    private String nom_representante;
    private String telf;
    private String direccion;
    private String email;
    private boolean estado;

    public empresa() {
    }

    public empresa(int cod_empresa, String nom_empresa, String ruc, String nom_representante, String telf, String direccion, String email, boolean estado) {
        this.cod_empresa = cod_empresa;
        this.nom_empresa = nom_empresa;
        this.ruc = ruc;
        this.nom_representante = nom_representante;
        this.telf = telf;
        this.direccion = direccion;
        this.email = email;
        this.estado = estado;
    }

   

    public int getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(int cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getNom_empresa() {
        return nom_empresa;
    }

    public void setNom_empresa(String nom_empresa) {
        this.nom_empresa = nom_empresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNom_representante() {
        return nom_representante;
    }

    public void setNom_representante(String nom_representante) {
        this.nom_representante = nom_representante;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

  
    
    
}
