package Modelo;

import java.util.*;

/**
 *
 */
public class det_pedidos {

    /**
     * Default constructor
     */
    public det_pedidos() {
    }

    private String cod_pedido;
    private String cod_prod;
    private int cant_pedid;
    private Double prec_pedid;
    private Double iva_ped;
    private Double importe_pedid;
    private boolean estado;

    public det_pedidos(String cod_pedido, String cod_prod, int cant_pedid, Double prec_pedid, Double iva_ped, Double importe_pedid, boolean estado) {
        this.cod_pedido = cod_pedido;
        this.cod_prod = cod_prod;
        this.cant_pedid = cant_pedid;
        this.prec_pedid = prec_pedid;
        this.iva_ped = iva_ped;
        this.importe_pedid = importe_pedid;
        this.estado = estado;
    }

    public String getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(String cod_pedido) {
        this.cod_pedido = cod_pedido;
    }


    public String getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(String cod_prod) {
        this.cod_prod = cod_prod;
    }

    public int getCant_pedid() {
        return cant_pedid;
    }

    public void setCant_pedid(int cant_pedid) {
        this.cant_pedid = cant_pedid;
    }

    public Double getPrec_pedid() {
        return prec_pedid;
    }

    public void setPrec_pedid(Double prec_pedid) {
        this.prec_pedid = prec_pedid;
    }

    public Double getIva_ped() {
        return iva_ped;
    }

    public void setIva_ped(Double iva_ped) {
        this.iva_ped = iva_ped;
    }

    public Double getImporte_pedid() {
        return importe_pedid;
    }

    public void setImporte_pedid(Double importe_pedid) {
        this.importe_pedid = importe_pedid;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    

   
}
