package Modelo;


/**
 * 
 */
public class tipo {

    /**
     * Default constructor
     */
    public tipo() {
    }

    private int cod_tip;
    private String descrip;

    public tipo(int cod_tip, String descrip) {
        this.cod_tip = cod_tip;
        this.descrip = descrip;
    }

    public int getCod_tip() {
        return cod_tip;
    }

    public void setCod_tip(int cod_tip) {
        this.cod_tip = cod_tip;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

   


}