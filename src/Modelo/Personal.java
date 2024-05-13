package Modelo;


/**
 * 
 */
public class Personal extends Persona {

    /**
     * Default constructor
     */
    private int cod_usua;
    private int tip_usua;
    private String password;
    private boolean estado;
    
    

    public Personal() {
    }

    public Personal(int cod_usua, int tip_usua, String password, boolean estado, String ced, String nombre, String apellido, int cod_ciu, String direccion, String telefono, String correo_elect) {
        super(ced, nombre, apellido, cod_ciu, direccion, telefono, correo_elect);
        this.cod_usua = cod_usua;
        this.tip_usua = tip_usua;
        this.password = password;
        this.estado = estado;
    }

   



    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    

    public int getCod_usua() {
        return cod_usua;
    }

    public void setCod_usua(int cod_usua) {
        this.cod_usua = cod_usua;
    }

    public int getTip_usua() {
        return tip_usua;
    }

    public void setTip_usua(int tip_usua) {
        this.tip_usua = tip_usua;
    }

  

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}