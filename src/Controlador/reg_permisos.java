/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Permisos;
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

/**
 *
 * @author Edgar
 */
public class reg_permisos {

    //Permisos perm= new Permisos(); 
    cn_BaseDeDatos bd = new cn_BaseDeDatos();

    Validaciones val = new Validaciones();
    Permisos perm = new Permisos();
    private PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<Permisos> lisPermisos;

    public boolean save_Permisos(Permisos perm) {
        Connection cn = bd.Conectar();
        String insert = "insert into permisos(id_per,ced_usu,per_delete,per_add,per_update,cod_discolor,id_ima)"
                + "values (?,?,?,?,?,?,?)";
        try {
            ps = cn.prepareStatement(insert);
            ps.setInt(1, perm.getId_permisos());
            ps.setString(2, perm.getCed_usua());
            ps.setBoolean(3, perm.isPer_delete());
            ps.setBoolean(4, perm.isPer_regist());
            ps.setBoolean(5, perm.isPer_udate());
            ps.setInt(6, perm.getCod_disc());
            ps.setInt(7, perm.getId_ima());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_permisos.class.getName()).log(Level.SEVERE, null, ex);
        }
        // bd.desconectar();
        return true;
    }

    public boolean update_Permisos(Permisos perm) {

        String update = "UPDATE permisos SET ced_usu=?,per_delete=?,per_add=?,per_update=?,cod_discolor=?,"
                + "id_ima=? WHERE id_per=?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(update);
            ps.setString(1, perm.getCed_usua());
            ps.setBoolean(2, perm.isPer_delete());
            ps.setBoolean(3, perm.isPer_regist());
            ps.setBoolean(4, perm.isPer_udate());
            ps.setInt(5, perm.getCod_disc());
            ps.setInt(6, perm.getId_ima());
            ps.setInt(7, perm.getId_permisos());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_permisos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  bd.desconectar();
        return true;
    }

    public ArrayList cargar_permisos(String dato) {
        try {
            Connection cn = bd.Conectar();
            String sql = "SELECT * FROM permisos where CONCAT(id_per,ced_usu) LIKE '%" + dato + "%'";
            lisPermisos = new ArrayList<Permisos>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lisPermisos.add(new Permisos(rs.getInt("id_per"), rs.getString("ced_usu"), rs.getBoolean("per_delete"), rs.getBoolean("per_add"), rs.getBoolean("per_update"), rs.getInt("cod_discolor"), rs.getInt("id_ima")));
            }
            bd.desconectar();
        } catch (Exception err) {
            System.out.println(err);
        } catch (Throwable ex) {
            Logger.getLogger(reg_permisos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lisPermisos;
    }
    //editar una parte de los permisos

    public void edit_perm_disenio(String ced, int cod_disc, int id_ima) {//estado del usuario

        String update = "UPDATE permisos SET cod_discolor='" + cod_disc + "',"
                + "id_ima='" + id_ima + "' WHERE ced_usu = '" + ced + "'";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(update);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_permisos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
