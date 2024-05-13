package Controlador;

import Modelo.Ciudad;
import Modelo.Cliente;
import Modelo.Validaciones;
import Modelo.cn_BaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class reg_clientes {

    reg_pais rp = new reg_pais();
    DefaultTableModel datos;
    private PreparedStatement ps = null;
    ArrayList<Ciudad> lisCiu;
    cn_BaseDeDatos bd = new cn_BaseDeDatos();

    // Connection cn = bd.Conectar();//conectar con la base de datos
    Validaciones val = new Validaciones();

    public int count() {
        return val.count("select count(cod_cli) from cliente");
    }

    public int codigoclientes() {//codigo de los clientes
        return val.cod_tablas("select max(cod_cli) from cliente");
    }

    public boolean insert(Cliente cli) {
        Connection cn = bd.Conectar();//conectar con la base de datos
        String insertar = "INSERT INTO cliente(cod_cli, ced_cli, nom_cli, ape_cli,dir_cli,telf_cli,nit_cli,cod_ciu)"
                + "VALUES (?, ?, ?, ?, ?,?, ?, ?)";
        try {
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, cli.getCod_clien());//aqui el codigo
            ps.setString(2, cli.getCed());
            ps.setString(3, cli.getNombre());
            ps.setString(4, cli.getApellido());
            ps.setString(5, cli.getDireccion());
            ps.setString(6, cli.getTelefono());
            ps.setString(7, cli.getCorreo_elect());
            ps.setInt(8, cli.getCod_ciu());
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            bd.desconectar();
            //Desconectar la base de datos
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Cliente ya fue Registrado","Error", JOptionPane.ERROR_MESSAGE);
        } catch (Throwable ex) {
            Logger.getLogger(reg_clientes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean update(Cliente cli) throws Throwable {
        Connection cn = bd.Conectar();
        String update = "UPDATE cliente SET ced_cli=?, nom_cli=?, ape_cli=?,dir_cli=?,telf_cli=?,nit_cli=?,cod_ciu=?"
                + " WHERE cod_cli=?";
        try {
            ps = cn.prepareStatement(update);
            ps.setString(1, cli.getCed());
            ps.setString(2, cli.getNombre());
            ps.setString(3, cli.getApellido());
            ps.setString(4, cli.getDireccion());
            ps.setString(5, cli.getTelefono());
            ps.setString(6, cli.getCorreo_elect());
            ps.setInt(7, cli.getCod_ciu());
            ps.setInt(8, cli.getCod_clien());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente Actualizado");
            // JOptionPane.showMessageDialog(null, "Registro Guardado");
            bd.desconectar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ID de cliente ya existe","Error", JOptionPane.ERROR_MESSAGE);
           
            System.out.println(ex);
        }
        return true;
    }

    public boolean Eliminar(Cliente cli) {
        Connection cn = bd.Conectar();
        String delete = "DELETE FROM cliente WHERE cod_cli =?";
        try {
            ps = cn.prepareStatement(delete);
            ps.setInt(1, cli.getCod_clien());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Registro en uso");
        }
        return true;
    }

    public DefaultTableModel load_clientes() {
        String[] column = {"Cod", "Cédula", "Nombre", "Apellido", "Dirección", "Teléfono", "NIT", "Sector"};
        datos = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    public String obtCiu(String data) {
        String ciu = "";
        lisCiu = rp.CargarListCiu(data);
        for (Ciudad c : lisCiu) {
            ciu = c.getNom_ciu();
        }
        return ciu;
    }

    ArrayList<Cliente> listCli;

    public String load_nom_cliente(int cod) {
        return "SELECT * FROM cliente where cod_cli='" + cod + "'";

    }

    public String load_cliente(String dato) {
        return "SELECT * FROM cliente where CONCAT(ced_cli, nom_cli, ape_cli) LIKE '%" + dato + "%' order by cod_cli asc";
    }

    public ArrayList llenar_Clientes(String sql) {
        Connection cn = bd.Conectar();
        try {
           // String sql = "SELECT * FROM cliente where CONCAT(ced_cli, nom_cli, ape_cli) LIKE '%" + dato + "%' order by cod_cli asc";
            listCli = new ArrayList<Cliente>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listCli.add(new Cliente(
                        rs.getInt("cod_cli"),
                        rs.getString("ced_cli"),
                        rs.getString("nom_cli"),
                        rs.getString("ape_cli"),
                        rs.getInt("cod_ciu"),
                        rs.getString("dir_cli"),
                        rs.getString("telf_cli"),
                        rs.getString("nit_cli")));
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCli;
    }
     ArrayList<Cliente> Lcliente;
    public String load_nom_cli(int cod_usu) {
        String nomCli = "";
        Lcliente = llenar_Clientes(load_nom_cliente(cod_usu));
        for (Cliente cl : Lcliente) {
            nomCli = cl.getNombre()+ "  "+cl.getApellido();
        }
        return nomCli;
    }

}
