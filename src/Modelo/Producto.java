package Modelo;

//import java.util.*;

/**
 *
 */
public class Producto extends producto_compra {

    private int cod_prove;
    private Double prec_vnta;
    private int stok;

    public Producto() {
    
    }

    
     
    public Producto( int num_prod, String cod_prod, int cod_prove, int cod_marca, int cod_tip_prend, double prec_cmpra, Double descuento,  Double prec_vnta, int stok,boolean iva, String descripcion, String observ) {
        super(num_prod, cod_prod, cod_marca, cod_tip_prend, prec_cmpra, descuento, iva, descripcion, observ);
        this.cod_prove = cod_prove;
        this.prec_vnta = prec_vnta;
        this.stok = stok;
    }
  
    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getCod_prove() {
        return cod_prove;
    }

    public void setCod_prove(int cod_prove) {
        this.cod_prove = cod_prove;
    }
    
    public Double getPrec_vnta() {
        return prec_vnta;
    }

    public void setPrec_vnta(Double prec_vnta) {
        this.prec_vnta = prec_vnta;
    }

}
