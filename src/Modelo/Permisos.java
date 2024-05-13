package Modelo;

/**
 *
 * @author Leonel
 */
public class Permisos {
    private int id_permisos;
    private String ced_usua;
    private boolean per_delete;
    private boolean per_regist;
    private boolean per_udate;
    private int cod_disc;
    private int id_ima;

    public Permisos() {
    }

    public Permisos(int id_permisos, String ced_usua, boolean per_delete, boolean per_regist, boolean per_udate, int cod_disc, int id_ima) {
        this.id_permisos = id_permisos;
        this.ced_usua = ced_usua;
        this.per_delete = per_delete;
        this.per_regist = per_regist;
        this.per_udate = per_udate;
        this.cod_disc = cod_disc;
        this.id_ima = id_ima;
    }

    public int getCod_disc() {
        return cod_disc;
    }

    public void setCod_disc(int cod_disc) {
        this.cod_disc = cod_disc;
    }

    public int getId_ima() {
        return id_ima;
    }

    public void setId_ima(int id_ima) {
        this.id_ima = id_ima;
    }

   

    public int getId_permisos() {
        return id_permisos;
    }

    public void setId_permisos(int id_permisos) {
        this.id_permisos = id_permisos;
    }

    public String getCed_usua() {
        return ced_usua;
    }

    public void setCed_usua(String ced_usua) {
        this.ced_usua = ced_usua;
    }

    public boolean isPer_delete() {
        return per_delete;
    }

    public void setPer_delete(boolean per_delete) {
        this.per_delete = per_delete;
    }

    public boolean isPer_regist() {
        return per_regist;
    }

    public void setPer_regist(boolean per_regist) {
        this.per_regist = per_regist;
    }

    public boolean isPer_udate() {
        return per_udate;
    }

    public void setPer_udate(boolean per_udate) {
        this.per_udate = per_udate;
    } 
}
