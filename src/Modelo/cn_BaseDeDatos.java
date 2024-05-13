package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Edgar
 */

public class cn_BaseDeDatos {

    private static Connection conn;
   // private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    public String db = "ferre_ferrocon";
    public String server_name = "localhost:3306";
    public String url = "jdbc:mysql://"+server_name+"/" + db;
    public String user = "root";
    public String pass = "722435";

    public Connection Conectar() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(this.url, this.user, this.pass);
            if (conn != null) {
                System.out.println("Conexion establecida.....");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return conn;
    }

    public void desconectar() {
        conn = null;
        if (conn == null) {
            System.out.println("Conexion terminada");
        }
    }
}
