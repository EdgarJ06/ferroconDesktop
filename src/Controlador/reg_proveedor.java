/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Proveedores;
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
public class reg_proveedor {

    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    PreparedStatement ps = null;
    Validaciones val = new Validaciones();
    ArrayList<Proveedores> listProve;
    private ArrayList<Proveedores> listProv;

    //codigo para los proveedores

    public int cod_prov() {
        return val.cod_tablas("select max(cod_prove)from proveedor");
    }

    public int contProve() {
        return val.count("select count(cod_prove) from proveedor");
    }

    public boolean guardar(Proveedores pr) {
        String insert = "insert into proveedor(cod_prove,cod_ciu,ced_ruc,nom_prove,dir_prove,email_prove,telf_prove)"
                + "values(?,?,?,?,?,?,?)";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(insert);
            ps.setInt(1, pr.getCod_prove());
            ps.setInt(2, pr.getCod_ciu());
            ps.setString(3, pr.getCed_ruc());
            ps.setString(4, pr.getNom_prove());
            ps.setString(5, pr.getDir_prov());
            ps.setString(6, pr.getCorreo_prove());
            ps.setString(7, pr.getTelefono());         
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Proveedor Guardado");
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Error Verificar Datos");
        } catch (Throwable ex) {
            Logger.getLogger(reg_proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean update(Proveedores pr) {
        String update = "UPDATE proveedor SET cod_ciu=?,ced_ruc=?,nom_prove=?,dir_prove=?,email_prove=?,telf_prove=?"
                + "where cod_prove=?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(update);
            ps.setInt(1, pr.getCod_ciu());
            ps.setString(2, pr.getCed_ruc());
            ps.setString(3, pr.getNom_prove());
            ps.setString(4, pr.getDir_prov());
            ps.setString(5, pr.getCorreo_prove());
            ps.setString(6, pr.getTelefono());
            ps.setInt(7, pr.getCod_prove());
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Proveedor Actualizado");
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Error Verificar Datos");
        } catch (Throwable ex) {
            Logger.getLogger(reg_proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean delete(Proveedores pr) {
        String delete = "Delete from proveedor where cod_prove=?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(delete);
            ps.setInt(1, pr.getCod_prove());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Proveedor Eliminado");
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Proveedor Activo no se puede eliminar");
        } catch (Throwable ex) {
            Logger.getLogger(reg_proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
    //cargar datos a la tabla
    static DefaultTableModel datos;

    public DefaultTableModel label_Proveedores() {
        String[] column = {"Cod", "Ciudad", "Ruc", "Nombre", "Dirección", "Email", "Teléfono"};
        datos = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    //sql para obtener el nombre del proveedor por medio del codigo
    public String sql_nom_prove(int dato) {
        return "Select * from proveedor where cod_prove = '" + dato + "'";
    }

    public String sql_prove(String dato) {
        return "SELECT * FROM proveedor where CONCAT(ced_ruc, nom_prove) LIKE '%" + dato + "%' order by cod_prove asc";
    }

    public ArrayList llenar_Proveedores(String sql) {
        try {
            Connection cn = bd.Conectar();
            listProve = new ArrayList<Proveedores>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listProve.add(new Proveedores(
                        rs.getInt("cod_prove"),
                        rs.getInt("cod_ciu"),
                        rs.getString("ced_ruc"),
                        rs.getString("nom_prove"),
                        rs.getString("dir_prove"),
                        rs.getString("email_prove"),
                        rs.getString("telf_prove")));
            }
             bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return listProve;
    }

    public int ObtCodProv(String dato) {//obtener el codigo del proveedor
        int cod_prov = 0;
        listProv = llenar_Proveedores(sql_prove(dato));
        for (Proveedores p : listProv) {
            cod_prov = p.getCod_prove();
        }
        return cod_prov;
    }

    //Obtener el nombre del proveedor por medio del codigo
    public String ObtNomProv(int dato) {
        String nomProv = "";
         listProv = llenar_Proveedores(sql_nom_prove(dato));
        for (Proveedores p : listProv) {
            nomProv = p.getNom_prove();
        }
        return nomProv;
    }

}
