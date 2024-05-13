/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Ciudad;
import Modelo.Pais;
import Modelo.Provincia;
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
public class reg_pais {

    private PreparedStatement ps = null;
    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    
    static Pais p = new Pais();
    Validaciones val = new Validaciones();
    private ArrayList<Pais> listPais;
    private ArrayList<Provincia> listProv;
    private ArrayList<Ciudad> listCiu;
    DefaultTableModel datos;

    public boolean insert_pais(Pais p) {
        String insertar = "INSERT INTO pais(cod_pais, nom_pais)VALUES (?, ?)";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, p.getCod_pais());
            ps.setString(2, p.getNom_pais());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        //bd.desconectar();//Desconectar la base de datos
        return true;
    }

    public boolean update_pais(Pais p) {
        String update = "UPDATE pais SET nom_pais=? WHERE cod_pais=?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(update);
            ps.setString(1, p.getNom_pais());
            ps.setInt(2, p.getCod_pais());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean delete_pais(Pais p) {
        String delete = "DELETE FROM pais WHERE cod_pais =?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(delete);
            ps.setInt(1, p.getCod_pais());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        //   bd.desconectar();
        return true;
    }

    public int cod_pais() {
        return val.cod_tablas("Select max(cod_pais) from pais");
    }

    public int count_pais() {
        return val.count("select count(cod_pais) from pais");
    }

    public int ObtCodPais(String dato) {
        int cod = 0;
        listPais = CargarListPais(dato);
        for (Pais p : listPais) {
            cod = p.getCod_pais();
        }
        return cod;
    }

    //label de campos
    public DefaultTableModel label_pais() {
        String[] column = {"Cod", "Pais"};
        datos = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    //llenar los combos de pais
    public ArrayList CargarListPais(String dato) {
        try {
            Connection cn = bd.Conectar();
            listPais = new ArrayList<Pais>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select * from pais where CONCAT(cod_pais,nom_pais) LIKE '%" + dato + "%'");
            while (rs.next()) {
                listPais.add(new Pais(rs.getInt(1), rs.getString(2)));
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return listPais;
    }

    //Registrar Provincia
    public boolean insert_prov(Provincia prov) {
        String insertar = "INSERT INTO provincia(cod_prov,cod_pais,nom_prov)VALUES (?, ?,?)";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, prov.getCod_prov());
            ps.setInt(2, prov.getCod_pais());
            ps.setString(3, prov.getNom_prov());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        //   bd.desconectar();//Desconectar la base de datos
        return true;
    }

    public boolean update_prov(Provincia prov) {
        String update = "UPDATE provincia SET cod_pais=?,nom_prov=? WHERE cod_prov=?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(update);
            ps.setInt(1, prov.getCod_pais());
            ps.setString(2, prov.getNom_prov());
            ps.setInt(3, prov.getCod_prov());
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Estado Actualizado");
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  bd.desconectar();
        return true;
    }

    public boolean delete_prov(Provincia prov) {
        String delete = "DELETE FROM provincia WHERE cod_prov =?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(delete);
            ps.setInt(1, prov.getCod_prov());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        // bd.desconectar();
        return true;
    }

    public int ObtCodProv(String dato) {
        int cod = 0;
        listProv = CargarListProvincia(dato);
        for (Provincia p : listProv) {
            cod = p.getCod_prov();
        }
        return cod;
    }

    public DefaultTableModel label_Prov() {
        String[] column = {"Cod", "Pais", "Departamento"};
        datos = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    public DefaultTableModel label_Ciudad() {
        String[] column = {"Cod", "Pais", "Departamento", "Ciudad"};
        datos = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    public ArrayList CargarListProvincia(String dato) {
        try {
            Connection cn = bd.Conectar();
            listProv = new ArrayList<Provincia>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM provincia where CONCAT(cod_pais,nom_prov) LIKE '%" + dato + "%'");
            while (rs.next()) {
                listProv.add(new Provincia(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return listProv;
    }

    //cargar provincia
    public int ObtCodPais_of_Provincia(String dato) {
        int codp = 0;
        listProv = CargarListProvincia(dato);
        for (Provincia c : listProv) {
            codp = c.getCod_pais();
        }
        return codp;
    }

    //cargar provincia
    public ArrayList loadProvincia(String dato) {
        try {
            Connection cn = bd.Conectar();
            listProv = new ArrayList<Provincia>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM provincia where CONCAT(cod_prov,nom_prov) LIKE '%" + dato + "%'");
            while (rs.next()) {
                listProv.add(new Provincia(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        return listProv;
    }

    public String ObtNomProvincia(String dato) {//obtnego el codigo de la marca
        String nom = "";
        listProv = loadProvincia(dato);
        for (Provincia c : listProv) {
            nom = c.getNom_prov();
        }
        return nom;
    }

    public int cod_prov() {
        return val.cod_tablas("Select max(cod_prov) from provincia");
    }
    //Registro ciudad

    public boolean insert_ciu(Ciudad ciu) {
        String insertar = "INSERT INTO ciudad(cod_ciu, cod_prov,nom_ciu)VALUES (?, ?,?)";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, ciu.getCod_ciu());
            ps.setInt(2, ciu.getCod_prov());
            ps.setString(3, ciu.getNom_ciu());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        // bd.desconectar();//Desconectar la base de datos
        return true;
    }

    public boolean update_ciu(Ciudad ciu) {
        String update = "UPDATE ciudad SET cod_prov=?,nom_ciu=? WHERE cod_ciu=?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(update);
            ps.setInt(1, ciu.getCod_prov());
            ps.setString(2, ciu.getNom_ciu());
            ps.setInt(3, ciu.getCod_ciu());
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Ciudad Actualizado");
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean delete_ciu(Ciudad ciu) {
        String delete = "DELETE FROM ciudad WHERE cod_ciu =?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(delete);
            ps.setInt(1, ciu.getCod_ciu());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public int ObtCodCiu(String dato) {
        int cod = 0;
        listCiu = CargarListCiu(dato);
        for (Ciudad c : listCiu) {
            cod = c.getCod_ciu();
        }
        return cod;
    }

    public String ObtNomCiu(String dato) {//obtnego el codigo de la marca
        String nom = "";
        listCiu = CargarListCiu(dato);
        for (Ciudad c : listCiu) {
            nom = c.getNom_ciu();
        }
        return nom;
    }
//para ciudad del cliente y los demas

    public ArrayList CargarListCiu(String dato) {//aqui problemas por coidogs foraneos igualesmmmmm
        try {
            Connection cn = bd.Conectar();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select *from ciudad where CONCAT(cod_ciu,nom_ciu) LIKE '%" + dato + "%'");
            listCiu = new ArrayList<Ciudad>();
            while (rs.next()) {
                listCiu.add(new Ciudad(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3)));
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return listCiu;
    }

    public String cod_ciu() {
        return val.cod_tablasCiudad("Select count(cod_ciu) from ciudad");
    }
    //cargar combos

    public int ObtCodCiuC(String dato) {
        int cod = 0;
        listCiu = CargarListCiu(dato);
        for (Ciudad c : listCiu) {
            cod = c.getCod_ciu();
        }
        return cod;
    }

    public String ObtNomCiuC(String dato) {//obtnego el codigo de la marca
        String nom = "";
        listCiu = CargarListCiu(dato);
        for (Ciudad c : listCiu) {
            nom = c.getNom_ciu();
        }
        return nom;
    }

    public ArrayList CargarListCiuCombos(String dato) {//aqui problemas por coidogs foraneos igualesmmmmm
        try {
            Connection cn = bd.Conectar();
            listCiu = new ArrayList<Ciudad>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select *from ciudad where CONCAT(cod_prov,nom_ciu) LIKE '%" + dato + "%'");
            while (rs.next()) {
                listCiu.add(new Ciudad(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pais.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return listCiu;
    }
}
