package Controlador;

import Modelo.Marca;
import Modelo.tipo;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class reg_marca_tip {

    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    
    private PreparedStatement ps = null;
    Validaciones val = new Validaciones();
    tipo tp = new tipo();
    private ArrayList<Marca> listMarca;
    private ArrayList<tipo> listPrend;

    public boolean insert(Marca mar) {
        Connection cn = bd.Conectar();
        String insert = "insert into marca(cod_marca,nom_marca) values (?,?)";
        try {
            ps = cn.prepareStatement(insert);
            ps.setInt(1, mar.getCod_marca());
            ps.setString(2, mar.getNom_marca());
            ps.executeUpdate();
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
       // bd.desconectar();
        return true;
    }

    public boolean Update(Marca mar) {
        Connection cn = bd.Conectar();
        String update = "update marca set nom_marca=? where cod_marca=?";
        try {
            ps = cn.prepareStatement(update);
            ps.setString(1, mar.getNom_marca());
            ps.setInt(2, mar.getCod_marca());
            ps.executeUpdate();
            cn.close();
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean Delete(Marca mar) {
        Connection cn = bd.Conectar();
        String delete = "delete from marca where cod_marca =?";
        try {
            ps = cn.prepareStatement(delete);
            ps.setInt(1, mar.getCod_marca());
            ps.executeUpdate();
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  bd.desconectar();
        return true;
    }
    static DefaultTableModel dato;

    public static int etique_tabla() {
        String[] column = {"Codigo", "Nombre"};
        dato = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return 0;
    }

    // ArrayList<String> list = new ArrayList<String>();
    public DefaultTableModel setFilas_marca(String inf) {
        Connection cn = bd.Conectar();
        etique_tabla();
        try {
            String sql = "Select *from marca where CONCAT(cod_marca,nom_marca)LIKE '%" + inf + "%' order by cod_marca asc ";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[2];
            while (rs.next()) {
                for (int i = 0; i < 2; i++) {
                    datos[i] = rs.getObject(i + 1);
                }
                dato.addRow(datos);
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dato;
    }

    public int ced_Proveedor(String dato) {//obtnego el codigo de la marca
        int cod = 0;
        listMarca = CargarListMarca(dato);
        for (Marca p : listMarca) {
            cod = p.getCod_marca();
        }
        return cod;
    }

    public int ObtCodMarca(String dato) {//obtnego el codigo de la marca
        int cod = 0;
        listMarca = CargarListMarca(     dato);
        for (Marca p : listMarca) {
            cod = p.getCod_marca();
        }
        return cod;
    }

    public String ObtNomMarca(String dato) {//obtnego el codigo de la marca
        String nom = "";
        listMarca = CargarListMarca(dato);
        for (Marca p : listMarca) {
            nom = p.getNom_marca();
        }
        return nom;
    }

    //obtengo las marcas  inicio
    public ArrayList CargarListMarca(String dato) {//Verificar si puedo llenar tablas con este metodo
        try {
            Connection cn = bd.Conectar();
            listMarca = new ArrayList<Marca>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select * from marca where CONCAT(cod_marca,nom_marca) LIKE '%" + dato + "%'");
            while (rs.next()) {
                listMarca.add(new Marca(rs.getInt(1), rs.getString(2)));
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return listMarca;
    }//fin

    public int cod_marca() {//codigo de la marca
        return val.cod_tablas("select max(cod_marca) from marca");
    }

    public int count_marca() {
        return val.count("select count(cod_marca) from marca");
    }

    //TIPO DE PRENDAS
    public boolean insertTip(tipo tip) {
        Connection cn = bd.Conectar();
        String insert = "insert into tipo(id_tipo,descrip) values (?,?)";
        try {
            ps = cn.prepareStatement(insert);
            ps.setInt(1, tip.getCod_tip());
            ps.setString(2, tip.getDescrip());
            ps.executeUpdate();
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
     //   bd.desconectar();
        return true;
    }

    public boolean UpdateTip(tipo tip) {
        Connection cn = bd.Conectar();
        String update = "update tipo set descrip=? where id_tipo=?";
        try {
            ps = cn.prepareStatement(update);
            ps.setString(1, tip.getDescrip());
            ps.setInt(2, tip.getCod_tip());
            ps.executeUpdate();
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  bd.desconectar();
        return true;
    }

    public boolean Delete(tipo tip) {
        Connection cn = bd.Conectar();
        String delete = "delete from tipo where id_tipo =?";
        try {
            ps = cn.prepareStatement(delete);
            ps.setInt(1, tip.getCod_tip());
            ps.executeUpdate();
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
     //   bd.desconectar();
        return true;
    }
    static DefaultTableModel datoTipPrend;

    public static int etique_tabla_tip() {
        String[] column = {"Codigo", "Nombre"};
        dato = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return 0;
    }

    public DefaultTableModel setFilas_tip_prend(String inf) {
        Connection cn = bd.Conectar();
        etique_tabla();
        try {
            String sql = "Select *from tipo where CONCAT(id_tipo,descrip)LIKE '%" + inf + "%' order by id_tipo asc ";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[2];
            while (rs.next()) {
                for (int i = 0; i < 2; i++) {
                    datos[i] = rs.getObject(i + 1);
                }
                dato.addRow(datos);
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dato;
    }

    public int cod_tip_prend() {//codigo de la marca
        return val.cod_tablas("select max(id_tipo) from tipo");
    }

    public int count_tip_prend() {
        return val.count("select count(id_tipo) from tipo");
    }

    public int ObtCodTip(String dato) {
        int cod = 0;
        listPrend = LoadListTip(dato);
        for (tipo tp : listPrend) {
            cod = tp.getCod_tip();
        }
        return cod;
    }

    public String ObtNomTip(String dato) {
        String nom = "";
        listMarca = LoadListTip(dato);
        for (tipo tp : listPrend) {
            nom = tp.getDescrip();
        }
        return nom;
    }

    public ArrayList LoadListTip(String dato) {
        Connection cn = bd.Conectar();
        try {
            listPrend = new ArrayList<tipo>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select * from tipo where CONCAT(id_tipo,descrip) LIKE '%" + dato + "%'");
            while (rs.next()) {
                listPrend.add(new tipo(rs.getInt(1), rs.getString(2)));
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listPrend;
    }//fin
}



