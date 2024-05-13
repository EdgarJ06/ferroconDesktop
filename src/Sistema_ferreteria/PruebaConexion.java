
package Sistema_ferreteria;

import Modelo.cn_BaseDeDatos;

/**
 *
 * @author eroda
 */
public class PruebaConexion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        cn_BaseDeDatos con = new cn_BaseDeDatos();
        if (con.Conectar() != null) {
            System.out.println("Se estableció conexion");
        } else {
            System.out.println("No hay conexion");
        }
    }
    
}
