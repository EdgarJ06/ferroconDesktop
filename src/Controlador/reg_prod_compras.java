/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Validaciones;
import Modelo.cn_BaseDeDatos;
import Modelo.producto_compra;
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
public class reg_prod_compras {

    reg_marca_tip rp = new reg_marca_tip();
    static DefaultTableModel datos;
    private PreparedStatement ps = null;
    cn_BaseDeDatos bd = new cn_BaseDeDatos();

    Validaciones val = new Validaciones();

    public int countProd() {
        return val.count("select count(cod_prod) from productos_compras");
    }

    public int codProductos() {//codigo de los clientes
        return val.cod_tablas("select max(num_prod) from productos_compras");
    }

    public boolean insert(producto_compra prod) {
        String insertar = "INSERT INTO productos_compras(num_prod,cod_prod,cod_marca,cod_tip,prec_compra,descuento,iva_prod,"
                + "descripcion,observ)"
                + "VALUES (?, ?, ?, ?, ?,?, ?, ?,?)";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, prod.getNum_prod());
            ps.setString(2, prod.getCod_prod());
            ps.setInt(3, prod.getCod_marca());
            ps.setInt(4, prod.getCod_tip_prend());
            ps.setDouble(5, prod.getPrec_cmpra());
            ps.setDouble(6, prod.getDescuento());
            ps.setBoolean(7, prod.isIva());
            ps.setString(8, prod.getDescripcion());
            ps.setString(9, prod.getObserv());
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Registro Guardado");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
            JOptionPane.showMessageDialog(null, "Existe un Error Revisar los Datos");
        } catch (Throwable ex) {
            Logger.getLogger(reg_prod_compras.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean update(producto_compra prod) {

        String update = "update productos_compras SET cod_prod=?,cod_marca=?,cod_tip=?,prec_compra=?,descuento=?,"
                + "iva_prod=?,descripcion=?,"
                + "observ=?"
                + "WHERE num_prod =?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(update);
            ps.setString(1, prod.getCod_prod());
            ps.setInt(2, prod.getCod_marca());
            ps.setInt(3, prod.getCod_tip_prend());
            ps.setDouble(4, prod.getPrec_cmpra());
            ps.setDouble(5, prod.getDescuento());
            ps.setBoolean(6, prod.isIva());
            ps.setString(7, prod.getDescripcion());
            ps.setString(8, prod.getObserv());
            ps.setInt(9, prod.getNum_prod());
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Registro Actualizado");
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Existe un Error Revisar los Datos");
        } catch (Throwable ex) {
            Logger.getLogger(reg_prod_compras.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean Eliminar(producto_compra prod) {
        String delete = "DELETE FROM productos_compras WHERE cod_prod =?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(delete);
            ps.setString(2, prod.getCod_prod());
            ps.executeUpdate();
            bd.desconectar();
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Producto no se puede eliminar");
        } catch (Throwable ex) {
            Logger.getLogger(reg_prod_compras.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public DefaultTableModel label_det_compra() {
        String[] column = {"Cod", "Producto", "Marca", "Precio", "Cantiad", "Iva", "Total"};
        datos = new DefaultTableModel(null, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    public DefaultTableModel label_table_compra() {
        String[] column = {"Num", "Cod", "Marca", "Tipo", "P. Compra", "Descuento", "Iva", "Descripcion", "Observ"};

        datos = new DefaultTableModel(null, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        return datos;
    }

    ArrayList<producto_compra> list;

    public String busc_tip_c(int dato) {
        return "SELECT * FROM productos_compras where cod_tip='" + dato + "' ";
    }

    public String busc_marca_c(int dato) {
        return "SELECT * FROM productos_compras where cod_marca='" + dato + "' ";
    }

    public String busc_cod_c(String dato) {
        return ("SELECT * FROM productos_compras where CONCAT(cod_prod) LIKE '%" + dato + "%' order by num_prod asc");
    }

    public String busc_marca_tipo_c(String cod1, String cod2) {
        return "SELECT * FROM productos_compras WHERE cod_marca='" + cod1 + "' and cod_tip='" + cod2 + "'";
    }

    
    
    
    
    public ArrayList llenar_prod(String sql) {
        try {
            Connection cn = bd.Conectar();
            list = new ArrayList<producto_compra>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new producto_compra(
                        rs.getInt("num_prod"),
                        rs.getString("cod_prod"),
                        rs.getInt("cod_marca"),
                        rs.getInt("cod_tip"),
                        rs.getDouble("prec_compra"),
                        rs.getDouble("descuento"),
                        rs.getBoolean("iva_prod"),
                        rs.getString("descripcion"),                    
                        rs.getString("observ")));
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_prod_compras.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
