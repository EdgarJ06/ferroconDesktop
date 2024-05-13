package Modelo;

import java.sql.Date;



/**
 *
 */
public class Pedidos {

    /**
     * Default constructor
     */
    public Pedidos() {
    }
    private String cod_pedido;
    private int cod_prov;
    private int cod_usu;
    private Date fecha_pedid;
    private Double subtotal;
    private Double iva_pedid;
    private Double desc_pedid;
    private Double total_pedid;

    public Pedidos(String cod_pedido, int cod_prov, int cod_usu, Date fecha_pedid, Double subtotal, Double iva_pedid, Double desc_pedid, Double total_pedid) {
        this.cod_pedido = cod_pedido;
        this.cod_prov = cod_prov;
        this.cod_usu = cod_usu;
        this.fecha_pedid = fecha_pedid;
        this.subtotal = subtotal;
        this.iva_pedid = iva_pedid;
        this.desc_pedid = desc_pedid;
        this.total_pedid = total_pedid;
    }

   

    public Date getFecha_pedid() {
        return fecha_pedid;
    }

    public void setFecha_pedid(Date fecha_pedid) {
        this.fecha_pedid = fecha_pedid;
    }

    public String getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(String cod_pedido) {
        this.cod_pedido = cod_pedido;
    }
    
   

    public int getCod_prov() {
        return cod_prov;
    }

    public void setCod_prov(int cod_prov) {
        this.cod_prov = cod_prov;
    }

    public int getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(int cod_usu) {
        this.cod_usu = cod_usu;
    }


    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva_pedid() {
        return iva_pedid;
    }

    public void setIva_pedid(Double iva_pedid) {
        this.iva_pedid = iva_pedid;
    }

    public Double getDesc_pedid() {
        return desc_pedid;
    }

    public void setDesc_pedid(Double desc_pedid) {
        this.desc_pedid = desc_pedid;
    }

    public Double getTotal_pedid() {
        return total_pedid;
    }

    public void setTotal_pedid(Double total_pedid) {
        this.total_pedid = total_pedid;
    }

  

 



   

}
