package Modelo;

import java.sql.Date;

/**
 *
 */
public class Ventas {
    
    private static String cod_vnta;
    private int cod_cli;
    private String name_cli;
    private int cod_usu;
    private Date fecha;
    private Double subtotal;
    private Double iva_vntas;
    private Double desc;
    private Double total;

   public Ventas() {
    }
    
    

    public Ventas(String cod_vnta, int cod_cli, String name_cli, int cod_usu, Date fecha, Double subtotal, Double iva_vntas, Double desc, Double total) {
        this.cod_vnta = cod_vnta;
        this.name_cli = name_cli;
        this.cod_cli = cod_cli;
        this.cod_usu = cod_usu;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva_vntas = iva_vntas;
        this.desc = desc;
        this.total = total;
    }

    public String getCod_vnta() {
        return cod_vnta;
    }

    public void setCod_vnta(String cod_vnta) {
        this.cod_vnta = cod_vnta;
    }
    
    public String getName_cli(){
        return name_cli;
    }
    
    public void setName_cli(String name_cli){
        this.name_cli = name_cli;
    }

    public int getCod_cli() {
        return cod_cli;
    }

    public void setCod_cli(int cod_cli) {
        this.cod_cli = cod_cli;
    }

    public int getCod_usu() {
        return cod_usu;
    }

    public void setCod_usu(int cod_usu) {
        this.cod_usu = cod_usu;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva_vntas() {
        return iva_vntas;
    }

    public void setIva_vntas(Double iva_vntas) {
        this.iva_vntas = iva_vntas;
    }

    public Double getDesc() {
        return desc;
    }

    public void setDesc(Double desc) {
        this.desc = desc;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
