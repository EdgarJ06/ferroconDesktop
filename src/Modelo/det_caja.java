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
public class det_caja {
    private int id_det;
    private int id_per_caja;
    private String fecha;
    private String descripcion;
    private String cod_vntas;
    private Double ingreso;
    private String cod_pedido;
    private Double egreso;

    public det_caja() {
    }

    public det_caja(int id_per_caja, String fecha, String descripcion, String cod_vntas, Double ingreso, String cod_pedido, Double egreso) {
        this.id_per_caja = id_per_caja;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.cod_vntas = cod_vntas;
        this.ingreso = ingreso;
        this.cod_pedido = cod_pedido;
        this.egreso = egreso;
    }

    public int getId_det() {
        return id_det;
    }

    public void setId_det(int id_det) {
        this.id_det = id_det;
    }

  

    public int getId_per_caja() {
        return id_per_caja;
    }

    public void setId_per_caja(int id_per_caja) {
        this.id_per_caja = id_per_caja;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCod_vntas() {
        return cod_vntas;
    }

    public void setCod_vntas(String cod_vntas) {
        this.cod_vntas = cod_vntas;
    }

    public Double getIngreso() {
        return ingreso;
    }

    public void setIngreso(Double ingreso) {
        this.ingreso = ingreso;
    }

    public String getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(String cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public Double getEgreso() {
        return egreso;
    }

    public void setEgreso(Double egreso) {
        this.egreso = egreso;
    }
    
}
