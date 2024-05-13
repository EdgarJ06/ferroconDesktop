/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Validaciones;
import Modelo.cn_BaseDeDatos;
import Modelo.empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class reg_empresa {

    private PreparedStatement ps = null;
    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    Validaciones val = new Validaciones();
    public int count() {
        return val.count("select count(cod_empresa) from empresa");
    }

    public boolean insert(empresa em) {
        Connection cn = bd.Conectar();//conectar con la base de datos
        String insertar = "INSERT INTO empresa(nom_empresa,ruc,nom_representante,telf,direccion,email,estado)"
                + "VALUES (?, ?, ?, ?, ?,?, ?)";
        try {
            ps = cn.prepareStatement(insertar);;
            ps.setString(1, em.getNom_empresa());
            ps.setString(2, em.getRuc());
            ps.setString(3, em.getNom_representante());
            ps.setString(4, em.getTelf());
            ps.setString(5, em.getDireccion());
            ps.setString(6, em.getEmail());
            ps.setBoolean(7, em.isEstado());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Empresa Registrada");
            bd.desconectar();
            //Desconectar la base de datos
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return true;
    }

    public boolean update(empresa em) throws Throwable {
        Connection cn = bd.Conectar();
        String update = "UPDATE empresa SET nom_empresa=?, ruc=?, nom_representante=?,telf=?,direccion=?,email=?,estado=?"
                + " WHERE cod_empresa=?";
        try {
            ps = cn.prepareStatement(update);
            ps.setString(1, em.getNom_empresa());
            ps.setString(2, em.getRuc());
            ps.setString(3, em.getNom_representante());
            ps.setString(4, em.getTelf());
            ps.setString(5, em.getDireccion());
            ps.setString(6, em.getEmail());
            ps.setBoolean(7, em.isEstado());
            ps.setInt(8, em.getCod_empresa());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos Actualizado");
            // JOptionPane.showMessageDialog(null, "Registro Guardado");
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }

    public boolean Eliminar(empresa em) {
        Connection cn = bd.Conectar();
        String delete = "DELETE FROM empresa WHERE cod_empresa =?";
        try {
            ps = cn.prepareStatement(delete);
            ps.setInt(1, em.getCod_empresa());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }
    DefaultTableModel datos;

    public DefaultTableModel load_label_empresa() {
        String[] column = {"Cod", "Nombre", "Ruc", "Representante","Teléfono", "Dirección","Email","Estado"};
        datos = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
             @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        return datos;
    }
    public String sql_estado_empresa(String dato) {
        return "SELECT * FROM empresa where  estado =" + dato;
    }

    public String sql_load_empresa(String dato) {
        return "SELECT * FROM empresa where CONCAT(nom_empresa, ruc, nom_representante) LIKE '%" + dato + "%' order by cod_empresa asc";
    }
    ArrayList<empresa> listEmp;

    public ArrayList llenar_empresa(String sql) {
        Connection cn = bd.Conectar();
        try {
            listEmp = new ArrayList<empresa>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listEmp.add(new empresa(
                        rs.getInt("cod_empresa"),
                        rs.getString("nom_empresa"),
                        rs.getString("ruc"),
                        rs.getString("nom_representante"),
                        rs.getString("telf"),
                        rs.getString("direccion"),
                        rs.getString("email"),
                        rs.getBoolean("estado")));
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return listEmp;
    }
    
    //Editar el estado de todas las empresas
       public void desactivar_all_empresas() {//Desactivar todos los usuario
        Connection cn = bd.Conectar();
        String update = "UPDATE empresa SET estado='" + 0 + "' WHERE estado = '" + 1 + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(update);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
}
