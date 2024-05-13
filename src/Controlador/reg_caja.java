/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Validaciones;
import Modelo.apert_caja;
import Modelo.caja;
import Modelo.cn_BaseDeDatos;
import Modelo.det_caja;
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
public class reg_caja {

    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    private PreparedStatement ps = null;
    ArrayList<apert_caja> listCaja;
    //contar cantidad de registros de aperturas de caja

    Validaciones val = new Validaciones();

    public int countAperCaja() {
        return val.count("select count(ide_aper_caja) from aper_caja");
    }

    //Guardar Caja  
    public boolean insertCaja(caja c) {
        Connection cn = bd.Conectar();//conectar con la base de datos
        String insertar = "INSERT INTO caja( num_caja,descrip)"
                + "VALUES (?, ?)";
        try {
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, c.getNum_caja());
            ps.setString(2, c.getDescrip());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Caja Registrada");
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }

    //Fin de cargar caja
    //editar Caja
    public boolean updateCaja(caja c) {
        Connection cn = bd.Conectar();//conectar con la base de datos
        String update = "update caja set num_caja=?,descrip=? where id_caja=?";
        try {
            ps = cn.prepareStatement(update);
            ps.setInt(1, c.getNum_caja());
            ps.setString(2, c.getDescrip());
            ps.setInt(3, c.getId_caja());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Caja Actualizada");
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }
    //Fin editar Caja

    //Eliminar Caja 
    public boolean Eliminar(caja c) {
        Connection cn = bd.Conectar();
        String delete = "DELETE FROM caja WHERE id_caja =?";
        try {
            ps = cn.prepareStatement(delete);
            ps.setInt(1, c.getId_caja());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Registro en uso");
        }
        return true;
    }

    //cargar caja 
    DefaultTableModel dato;

    public DefaultTableModel load_label_caja() {
        String[] column = {"Cod", "Número", "Descripción"};
        dato = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return dato;
    }

    public DefaultTableModel setFilas_caja(String inf) {
        Connection cn = bd.Conectar();
        load_label_caja();
        try {
            String sql = "Select *from caja where CONCAT(num_caja,descrip)LIKE '%" + inf + "%' order by num_caja asc ";
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
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dato;
    }

    //fin de cargar caja
//GUARDA DATOS DE APERTURA DE CAJA
    public boolean insertApertCaja(apert_caja c) {
        Connection cn = bd.Conectar();//conectar con la base de datos
        String insertar = "INSERT INTO aper_caja(id_caja,id_user,fecha_hora_aper,monto_ini,fecha_hora_cierr,monto_final,estado)"
                + "VALUES (?, ?,?, ?,?, ?,?)";
        try {
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, c.getId_caja());
            ps.setInt(2, c.getId_user());
            ps.setString(3, c.getFecha_hora_aper());
            ps.setDouble(4, c.getMonto_ini());
            ps.setString(5, c.getFecha_hora_cierr());
            ps.setDouble(6, c.getMonto_final());
            ps.setBoolean(7, c.getEstado());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Caja Activada");
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }

//FIN DE GUARDAR DATOS DE APAERTURA DE CAJA
    //update cierre de caja
    public void update_aper_caja(int id_caja, String fecfin, Double montofinal, Boolean estado) {
        String modi = "UPDATE aper_caja SET fecha_hora_cierr='" + fecfin + "',monto_final=" + montofinal + ",estado=" + estado + " WHERE id_aper_caja = '" + id_caja + "'";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Caja Cerrada");
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //select para seleccionar las cajas abiertas con  id de caja existente
    public String load_caja_estado(int estado, int idcaja) {
        return "SELECT * FROM aper_caja where estado='" + estado + "'  AND id_caja='" + idcaja + "'  ";

    }

    //buscar caja qeu esta activa
    public String load_caja_activa(int cod) {
        return "SELECT * FROM aper_caja where estado='" + cod + "'";

    }

    //cargar caja
    public String load_caja(int cod) {
        return "SELECT * FROM aper_caja where id_caja='" + cod + "'";

    }
     public String load_all_caja() {
        return "SELECT * FROM aper_caja";

    }

    //fin de cierre de caja
    public ArrayList llenar_datos_caja(String sql) {
        Connection cn = bd.Conectar();
        try {
            listCaja = new ArrayList<apert_caja>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listCaja.add(new apert_caja(
                        rs.getInt("id_aper_caja"),
                        rs.getInt("id_caja"),
                        rs.getInt("id_user"),
                        rs.getString("fecha_hora_aper"),
                        rs.getDouble("monto_ini"),
                        rs.getString("fecha_hora_cierr"),
                        rs.getDouble("monto_final"),
                        rs.getBoolean("estado")));
            }

            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return listCaja;
    }
    //Guardar detalle de apertura de caja

    public boolean SaveDetalleApertCaja(det_caja dc) {
        Connection cn = bd.Conectar();//conectar con la base de datos
        String insertar = "INSERT INTO det_caja(id_per_caja, fecha, descripcion,cod_vntas,ingreso,cod_pedido,egreso)"
                + " VALUES  (?, ?,?, ?,?, ?,?)";
        try {
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, dc.getId_per_caja());
            ps.setString(2, dc.getFecha());
            ps.setString(3, dc.getDescripcion());
            ps.setString(4, dc.getCod_vntas());
            ps.setDouble(5, dc.getIngreso());
            ps.setString(6, dc.getCod_pedido());
            ps.setDouble(7, dc.getEgreso());
            ps.executeUpdate();

            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }
    //update detalle de caja

    public boolean updateDetalleApertCaja(det_caja dc) {
        Connection cn = bd.Conectar();//conectar con la base de datos

        String update = " UPDATE det_caja SET id_per_caja=?,fecha=?,descripcion=?,cod_vntas=?,ingreso=?,cod_pedido=?,egreso=? WHERE id_det=?";

        try {
            ps = cn.prepareStatement(update);

            ps.setInt(1, dc.getId_per_caja());
            ps.setString(2, dc.getFecha());
            ps.setString(3, dc.getDescripcion());
            ps.setString(4, dc.getCod_vntas());
            ps.setDouble(5, dc.getIngreso());
            ps.setString(6, dc.getCod_pedido());
            ps.setDouble(7, dc.getEgreso());

            ps.setInt(8, dc.getId_det());

            ps.executeUpdate();

            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }

    //Eliminar Gasto
    public boolean deleteDetallCaja(det_caja dc) {
        Connection cn = bd.Conectar();
        String delete = "DELETE FROM det_caja  WHERE id_det =?";
        try {
            ps = cn.prepareStatement(delete);
            ps.setInt(1, dc.getId_det());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Registro en uso");
        }
        return true;
    }

    //codigo de Caja 
    static ArrayList<apert_caja> listaperCaja;

    public int cod_caja_activa() {
        int cod = 0;
        listaperCaja = llenar_datos_caja(load_caja_activa(1));//) ru.user_active(ru.bus_user_ced(dato));     
        for (apert_caja us : listaperCaja) {

            cod = us.getId_aper_caja();
        }
        return cod;
    }
    DefaultTableModel detc;

    //LLENAR TABLA DETALLE DE CAJA
    public DefaultTableModel label_det_caja() {
        String[] column = {"Id", "Descripción", "Fecha", "Ingreso", "Egreso"};
        detc = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return detc;
    }

    //SE TIENE QEU FILTRAR DESDE LA FECHA INICIO HASTA LA FECHA FINAL
    public String operaciones_caja(String id, String dat1, String dat2) {

        return "Select *from det_caja where id_per_caja=" + id + "  AND  fecha between  '" + dat1 + "' and '" + dat2 + "'";
    }

    //seleccionar gastos 
    public String operaciones_gastos(String date1, String date2) {
        //  return "Select *from det_caja";
        return "Select *from det_caja where   fecha between  '" + date1 + "' and '" + date2 + "'";
    }

    public String operaciones_gastosAll() {
        return "Select *from det_caja";
    }

    public DefaultTableModel load_detalle_caja(String sql) {
        Connection cn = bd.Conectar();
        label_det_caja();
        try {
            // String sql = "Select *from det_caja where id_per_caja=" + inf;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[6];
            while (rs.next()) {
                datos[0] = rs.getString("id_det");
                // datos[1] = rs.getString("id_per_caja");
                datos[1] = rs.getString("descripcion");
                datos[2] = rs.getString("fecha");
                datos[3] = rs.getString("ingreso");
                datos[4] = rs.getString("egreso");

                detc.addRow(datos);
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_marca_tip.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detc;
    }

}
