/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Personal;
import Modelo.Validaciones;
import Modelo.cn_BaseDeDatos;
import Modelo.tipo_usuario;
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
public class reg_personal {

    Personal per = new Personal();
    reg_pais rp = new reg_pais();
    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    Validaciones val = new Validaciones();
    private PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<tipo_usuario> tipUser;
    ArrayList<Personal> usuActivo;

    public int cod_user() {
        return val.cod_tablas("Select max(cod_usu) from personal");
    }

    public int cont_user() {
        return val.count("Select count(cod_usu) from personal");
    }

    public boolean save_customer(Personal per) {
        Connection cn = bd.Conectar();
        String insert = "insert into personal(cod_usu,cod_tip_user,password,ced_usu,nom_usu,ape_usu,dir_usu,telf_usu,email_usu,cod_ciu,estado)"
                + "values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = cn.prepareStatement(insert);
            ps.setInt(1, per.getCod_usua());
            ps.setInt(2, per.getTip_usua());
            ps.setString(3, per.getPassword());
            ps.setString(4, per.getCed());
            ps.setString(5, per.getNombre());
            ps.setString(6, per.getApellido());
            ps.setString(7, per.getDireccion());
            ps.setString(8, per.getTelefono());
            ps.setString(9, per.getCorreo_elect());
            ps.setInt(10, per.getCod_ciu());
            ps.setBoolean(11, per.getEstado());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
        // bd.desconectar();
        return true;
    }

    public boolean update_customer(Personal per) {
        Connection cn = bd.Conectar();
        String update = "UPDATE personal SET cod_tip_user=?,password=?,ced_usu=?,nom_usu=?,"
                + "ape_usu=?,dir_usu=?,telf_usu=?,email_usu=?,cod_ciu=?,estado=? WHERE cod_usu=?";
        try {
            ps = cn.prepareStatement(update);
            ps.setInt(1, per.getTip_usua());
            ps.setString(2, per.getPassword());
            ps.setString(3, per.getCed());
            ps.setString(4, per.getNombre());
            ps.setString(5, per.getApellido());
            ps.setString(6, per.getDireccion());
            ps.setString(7, per.getTelefono());
            ps.setString(8, per.getCorreo_elect());
            ps.setInt(9, per.getCod_ciu());
            ps.setBoolean(10, per.getEstado());
            ps.setInt(11, per.getCod_usua());

            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean delete_customer(Personal per) {
        Connection cn = bd.Conectar();
        String delete = " Delete from personal where cod_usu=?)";
        try {
            ps = cn.prepareStatement(delete);
            ps.setInt(1, per.getCod_usua());
            ps.executeUpdate();
bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //   bd.desconectar();
        return true;
    }

    public boolean Validar_acceso(String ced, String contra) {
        Connection cn = bd.Conectar();
        boolean aux = false;
        String usu = ced;//inicia_cesion.txt_usua_ingre.getText();
        String consulta = "SELECT * FROM personal WHERE ced_usu=? and password=? ";
        try {
            ps = cn.prepareStatement(consulta);
            ps.setString(1, usu);
            ps.setString(2, contra);
            rs = ps.executeQuery();
            if (rs.next()) {
                aux = true;
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return aux;

    }

    public void edit_estado_us(String ced, int ok) {//estado del usuario
        Connection cn = bd.Conectar();
        String update = "UPDATE Personal SET estado='" + ok + "' WHERE ced_usu = '" + ced + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(update);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  /*  public void desactivar_all_user() {//Desactivar todos los usuario
        Connection cn = bd.Conectar();
        String update = "UPDATE Personal SET estado='" + 0 + "' WHERE estado = '" + 1 + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(update);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    public String bus_cod_user(int dato) {
        return "Select *from personal where  cod_usu='" + dato + "'";
    }

    public String bus_user_ced(String dato) {
        return "Select *from personal where  ced_usu='" + dato + "'";
    }

    public String load_user_active(String ced,  String estado) {//no debe buscar por estado      
        return "Select *from personal where   ced_usu='"+ced+"' and estado='" + estado + "'";
    }

    public String bus_user_tip_user(String dato) {
        return "Select *from personal where tip_usu='" + dato + "'";
    }

    public ArrayList user_active(String sql) {
        try {
            Connection cn = bd.Conectar();
            usuActivo = new ArrayList<Personal>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                usuActivo.add(new Personal(
                        rs.getInt("cod_usu"),
                        rs.getInt("cod_tip_user"),
                        rs.getString("password"),
                        rs.getBoolean("estado"),
                        rs.getString("ced_usu"),
                        rs.getString("nom_usu"),
                        rs.getString("ape_usu"),
                        rs.getInt("cod_ciu"),
                        rs.getString("dir_usu"),
                        rs.getString("telf_usu"),//
                        rs.getString("email_usu")));
            }
            bd.desconectar();
        } catch (Exception err) {
            System.out.println(err);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuActivo;
    }

    public ArrayList tipUser(String dato) {
        try {
            Connection cn = bd.Conectar();
            String sql = "SELECT * FROM tip_user where CONCAT(cod_tip_user, descripcion) LIKE '%" + dato + "%'";
            tipUser = new ArrayList<tipo_usuario>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                tipUser.add(new tipo_usuario(rs.getInt("cod_tip_user"), rs.getString("descripcion")));
            }
            bd.desconectar();
        } catch (Exception err) {
            System.out.println(err);
        } catch (Throwable ex) {
            Logger.getLogger(reg_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return tipUser;
    }

    public String loadDescTipUser(String cod) {
        String desc = "";
        tipUser = tipUser(cod);
        for (tipo_usuario c : tipUser) {
            desc = c.getDescr();
        }
        return desc;
    }

    public int loadCodTipUser(String codigo) {
        int cod = 0;
        tipUser = tipUser(codigo);
        for (tipo_usuario c : tipUser) {
            cod = c.getCod_user();
        }
        return cod;
    }
    //obtiene la cedula de los usuarios activos 
     public String load_nom_user(int cod_usu) {
        String nomUs = "";
        usuActivo = user_active(bus_cod_user(cod_usu));
        for (Personal us : usuActivo) {
            nomUs = us.getNombre()+ "  "+us.getApellido();
        }
        return nomUs;
    }
}
