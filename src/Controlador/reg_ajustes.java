/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Validaciones;
import Modelo.cn_BaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class reg_ajustes {

    Validaciones val = new Validaciones();
    cn_BaseDeDatos bd = new cn_BaseDeDatos();
//agregar ajustes
    public void agregarAjuste(int idmotivo, String codprod, int iduser, int cant) {
        String insert = "INSERT INTO tbajustes(idmotivo,codprod,id_user,cantidad)VALUES (" + idmotivo + ",'" + codprod + "'," + iduser + "," + cant + ")";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(insert);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//editar ajuste
     public void editarAjuste(int idajuste ,int idmotivo, String codprod, int iduser, int cant) {
       String update = " UPDATE tbajustes SET "
               + "idmotivo="+idmotivo+",codprod='"+codprod+"',id_user="+iduser+",cantidad="+cant+" WHERE idajuste="+idajuste;
        
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(update);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
  //eliminar ajuste
     
     
      public void EliminarAjuste(int idajuste) {
         // DELETE FROM `tbajustes` WHERE 0
       String delete = "DELETE FROM tbajustes WHERE idmotivo="+idajuste;
              
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(delete);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     
     
     
   //llenar tabla ajustes
    static DefaultTableModel datoAjustes;

    public static int etique_tabla_ajustes() {
        String[] column = {"IdAjuste", "ID Motivo", "COD PRODUCTO", "ID USER", "Cantidad"};
        datoAjustes = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return 0;
    }

    public DefaultTableModel setFilas_ajustes(String inf) {
        Connection cn = bd.Conectar();
        
        etique_tabla_ajustes();
        
        
        try {
            String sql = "Select *from  tbajustes where CONCAT(codprod,idajuste)LIKE '%" + inf + "%' order by idajuste asc ";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[5];
            while (rs.next()) {
                for (int i = 0; i < 5; i++) {
                    datos[i] = rs.getObject(i + 1);
                }
                datoAjustes.addRow(datos);
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        return datoAjustes;
    }
    
    
    //registro de motivo de ajustes
    public int count_motivos_ajuste() {
        return val.count("select count(idmotivo) from tbmotivoajustes");
    }

    public void agregar_motivo_Ajuste(String motivo, String tipo) {
        String insert = "INSERT INTO tbmotivoajustes(motivo,tipo)VALUES ('" + motivo + "','" + tipo + "')";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(insert);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //editar motivo ajustes
     public void editar_motivo_Ajuste(int id_motivo,String motivo, String tipo) {
         //
        String update = "UPDATE tbmotivoajustes SET motivo='"+motivo+"',tipo='"+tipo+"' WHERE idmotivo="+id_motivo;
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(update);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //eliminar motivo ajustes
       public void eliminar_motivo_Ajuste(int id_motivo) {
         //DELETE FROM `tbmotivoajustes` WHERE 0
        String delete = "DELETE FROM tbmotivoajustes  WHERE idmotivo="+id_motivo;
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(delete);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     
     
    

    //cargar datos  a la tabla
    static DefaultTableModel dato;

    public static int etique_tabla() {
        String[] column = {"Codigo", "Motivo", "Tipo"};
        dato = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return 0;
    }

    public DefaultTableModel setFilas_motivo(String inf) {
        Connection cn = bd.Conectar();
        etique_tabla();
        try {
            String sql = "Select *from tbmotivoajustes where CONCAT(idmotivo,tipo)LIKE '%" + inf + "%' order by idmotivo asc ";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[3];
            while (rs.next()) {
                for (int i = 0; i < 3; i++) {
                    datos[i] = rs.getObject(i + 1);
                }
                dato.addRow(datos);
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } 
        return dato;
    }

}
