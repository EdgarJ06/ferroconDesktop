package Controlador;

import Modelo.Pedidos;
import Modelo.Validaciones;
import Modelo.cn_BaseDeDatos;
import Modelo.det_pedidos;
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

public class reg_pedidos {

    static DefaultTableModel datos;
    private PreparedStatement ps = null;
    cn_BaseDeDatos bd = new cn_BaseDeDatos();

    Validaciones val = new Validaciones();
    DefaultTableModel datosPedido;

    public String codigoPedido() {//codigo de los clientes
        return val.codPedido("select max(cod_ped) from pedidos");
    }

    public int contarVentas() {//codigo de los clientes
        return val.count("select count(cod_ped) from pedidos"); //cod_tablas("select max(cod_ped) from pedidos");
    }

    public boolean insert(Pedidos pd) {
        String sql = "Insert Into pedidos(cod_ped,cod_prov,cod_usu,fecha_ped,subtotal,iva_ped,desc_ped,total_ped)"
                + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(sql);
            ps.setString(1, pd.getCod_pedido());
            ps.setInt(2, pd.getCod_prov());
            ps.setInt(3, pd.getCod_usu());
            ps.setDate(4, pd.getFecha_pedid());
            ps.setDouble(5, pd.getSubtotal());
            ps.setDouble(6, pd.getIva_pedid());
            ps.setDouble(7, pd.getDesc_pedid());
            ps.setDouble(8, pd.getTotal_pedid());
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Compra registrada");
        } catch (SQLException ex) {
            System.out.println("Esto se muestra:" + ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  bd.desconectar();//Desconectar la base de datos
        return true;
    }

    public DefaultTableModel cargarCamposTable() {
        String[] data = {"Cod", "Descripcion", "Marca", "Precio", "Cantidad", "Iva", "Total"};
        datosPedido = new DefaultTableModel(null, data) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datosPedido;
    }
    //GUARDA DETALLE

    public boolean insert_detalle(det_pedidos dp) {
        String insertar = "INSERT INTO det_pedidos(cod_ped,cod_prod,cant_ped,prec_ped,iva_ped,importe,estado)"
                + "VALUES (?, ?, ?,?, ?, ?,?)";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(insertar);
            ps.setString(1, dp.getCod_pedido());
            ps.setString(2, dp.getCod_prod());
            ps.setInt(3, dp.getCant_pedid());
            ps.setDouble(4, dp.getPrec_pedid());
            ps.setDouble(5, dp.getIva_ped());
            ps.setDouble(6, dp.getImporte_pedid());
            ps.setBoolean(7, dp.isEstado());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println("Error:" + ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void edit_estado_prod_cmpra(String codv, String cod_prod, boolean estado) {
        int cod = Integer.parseInt(codv);
        try {
            String modi = "UPDATE det_pedidos SET estado='" + estado + "' WHERE cod_ped = " + cod + " and cod_prod = '" + cod_prod + "'";
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            //System.out.println("aqui el eror bd:"+e);
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(reg_pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //etiquetas de pedidos
    public DefaultTableModel label_table() {
        String[] column = {"Cod", "Proveedor", "Usuario", "Fecha", "Subtotal", "Iva", "Desc", "Total"};

        datos = new DefaultTableModel(null, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    public DefaultTableModel label_table_det() {
        String[] column = {"Num", "Cod_Prod", "Producto", "Cantidad", "Precio", "Iva", "Importe", "Estado"};

        datos = new DefaultTableModel(null, column) {
            @Override
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
    //cargar facturas:
    ArrayList<Pedidos> listPed;

    //Busqueda de pedidos
    public String buscCod_pedido_ord(String dato, String ord) {
        return "SELECT * FROM pedidos where CONCAT(cod_ped,cod_prov) LIKE '%" + dato + "%' order by cod_ped " + ord;

    }
    public String buscCod_pedido_date(String fec1, String fec2) {
       return "SELECT * FROM pedidos where fecha_ped between '" + fec1 + "' and '" + fec2 + "'";
    }
    
   
    public ArrayList llenar_ped(String sql) {
        try {
            Connection cn = bd.Conectar();
            listPed = new ArrayList<Pedidos>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listPed.add(new Pedidos(
                        rs.getString("cod_ped"),
                        rs.getInt("cod_prov"),
                        rs.getInt("cod_usu"),
                        rs.getDate("fecha_ped"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva_ped"),
                        rs.getDouble("desc_ped"),
                        rs.getDouble("total_ped")));
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listPed;
    }
    //cargar detalles

    ArrayList<det_pedidos> detalle;

    public String busc_det_cod_ped(String dato) {
        return "SELECT  * FROM det_pedidos where CONCAT(cod_ped) LIKE '%" + dato + "%' order by cod_ped asc";

    }

    public String busc_det_estado_ped(String estado) {
        return "SELECT  * FROM det_pedidos where estado=" + estado;
    }

    public ArrayList cargar_detalle_pedidos(String sql) {
        try {
            Connection cn = bd.Conectar();
            //String sql = "SELECT  * FROM det_pedidos where CONCAT(cod_ped) LIKE '%" + dato + "%' order by cod_ped asc";
            detalle = new ArrayList<det_pedidos>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                detalle.add(new det_pedidos(
                        rs.getString("cod_ped"),
                        rs.getString("cod_prod"),
                        rs.getInt("cant_ped"),
                        rs.getDouble("prec_ped"),
                        rs.getDouble("iva_ped"),
                        rs.getDouble("importe"),
                        rs.getBoolean("estado")));
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return detalle;
    }

}
