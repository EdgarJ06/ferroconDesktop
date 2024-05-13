package Controlador;

import Modelo.Producto;
import Modelo.Validaciones;
import Modelo.cn_BaseDeDatos;
import Modelo.iva;
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
public class reg_productos {

    reg_marca_tip rp = new reg_marca_tip();
    static DefaultTableModel datos;
    private PreparedStatement ps = null;
    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    Validaciones val = new Validaciones();
    ArrayList<Producto> list;

    public int countProd() {
        return val.count("select count(cod_prod) from producto");
    }

    public int codIva() {
        return val.cod_tablas("select max(cod_iva) from tb_iva");
    }

    public int codProductos() {//codigo de los clientes
        return val.cod_tablas("select max(num_prod) from producto");
    }

    public boolean insert(Producto prod) {
        String insertar = "INSERT INTO producto(num_prod,cod_prod,cod_prove,cod_marca,cod_tip,prec_compra,descuento,prec_vnta,stock,iva_prod,"
                + "descrip,observ)"
                + "VALUES (?, ?, ?, ?, ?,?, ?, ?,?,?,?,?)";
        //  FileInputStream fi = null;
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(insertar);
            ps.setInt(1, prod.getNum_prod());
            ps.setString(2, prod.getCod_prod());//aqui el codigo
            ps.setInt(3, prod.getCod_prove());
            ps.setInt(4, prod.getCod_marca());
            ps.setInt(5, prod.getCod_tip_prend());
            ps.setDouble(6, prod.getPrec_cmpra());
            ps.setDouble(7, prod.getDescuento());
            ps.setDouble(8, prod.getPrec_vnta());
            ps.setInt(9, prod.getStok());
            ps.setBoolean(10, prod.isIva());
            ps.setString(11, prod.getDescripcion());           
            ps.setString(12, prod.getObserv());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  bd.desconectar();//Desconectar la base de datos
        return true;
    }

    public boolean update(Producto prod) {

        String update = "update producto SET cod_prod=?,cod_prove=?,cod_marca=?,cod_tip=?,prec_compra=?,descuento=?,prec_vnta=?,stock=?,"
                + "iva_prod=?,descrip=?,"
                + "observ=?"
                + "WHERE num_prod =?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(update);
            ps.setString(1, prod.getCod_prod());//aqui el codigo
            ps.setInt(2, prod.getCod_prove());
            ps.setInt(3, prod.getCod_marca());
            ps.setInt(4, prod.getCod_tip_prend());
            ps.setDouble(5, prod.getPrec_cmpra());
            ps.setDouble(6, prod.getDescuento());
            ps.setDouble(7, prod.getPrec_vnta());
            ps.setInt(8, prod.getStok());
            ps.setBoolean(9, prod.isIva());
            ps.setString(10, prod.getDescripcion());
            ps.setString(11, prod.getObserv());
            ps.setInt(12, prod.getNum_prod());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean Eliminar(Producto prod) {
        String delete = "DELETE FROM producto WHERE num_prod =?";
        try {
            Connection cn = bd.Conectar();
            ps = cn.prepareStatement(delete);
            ps.setInt(1, prod.getNum_prod());
            ps.executeUpdate();
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public DefaultTableModel label_det_vnta() {
        //String[] column = {"Cod", "Producto", "Marca", "Precio", "Cant", "Total"};
        //consulta anterior
        String[] column = {"Cod", "Producto", "Marca", "Precio", "Cant.", "Iva", "Total"};
        datos = new DefaultTableModel(null, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return datos;
    }

    public DefaultTableModel label_table() {
        //String[] column = {"Num", "Cod", "Marca", "Tipo", "P. Compra", "P. Venta", "Stok", "Descripcion", "Observ."};

        //consulta anterior
        String[] column = {"Num", "Cod", "Marca", "Tipo", "P. Compra", "P. Venta", "Stok", "Iva", "Descripcion", "Observ."};

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

    public String busc_tip(int dato) {
        return "SELECT * FROM producto where cod_tip='" + dato + "' ";
    }

    public String busc_marca(int dato) {
        return "SELECT * FROM producto where cod_marca='" + dato + "' ";
    }

    public String busc_cod(String dato) {
        return ("SELECT * FROM producto where CONCAT(cod_prod) LIKE '%" + dato + "%' order by num_prod asc");
    }

    public String busc_marca_tipo(String cod1, String cod2) {
        return "SELECT * FROM producto WHERE cod_marca='" + cod1 + "' and cod_tip='" + cod2 + "'";
    }

    public ArrayList llenar_prod_vnta(String sql) {
        try {
            Connection cn = bd.Conectar();
            list = new ArrayList<Producto>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new Producto(
                        rs.getInt("num_prod"),
                        rs.getString("cod_prod"),
                        rs.getInt("cod_prove"),
                        rs.getInt("cod_marca"),
                        rs.getInt("cod_tip"),
                        rs.getDouble("prec_compra"),
                        rs.getDouble("descuento"),
                        rs.getDouble("prec_vnta"),
                        rs.getInt("stock"),
                        rs.getBoolean("iva_prod"),                    
                        rs.getString("descrip"),                       
                        rs.getString("observ")));
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println("error aqui:" + ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    int codigos, cant, cantProd;

    //Descontar productos durante cada venta
    public void prod_stok(String codigo, int can) {
        Connection cn = bd.Conectar();
        codigos = Integer.parseInt(codigo);//asignacion de codigo 
        cant = can;
        String consul = "SELECT * FROM producto WHERE  cod_prod='" + codigo + "'";//codigo
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consul);
            while (rs.next()) {
                cantProd = rs.getInt(9);//la tabla debe ser 8
            }
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void descont_stok() {
        int desfinal = 0;
        desfinal = cantProd - cant;
        String modi = "UPDATE producto SET stock='" + desfinal + "' WHERE cod_prod = '" + codigos + "'";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Aumentar Stok
    
     public void aumentar_stok() {
        int desfinal = 0;
        desfinal = cantProd + cant;
        String modi = "UPDATE producto SET stock='" + desfinal + "' WHERE cod_prod = '" + codigos + "'";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println(e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //metodo para devolver proveedor por medio del codigo del producto
    public int cod_proveedor(String dato) {//codigo del proveedor
        int cod_prove = 0;
        list = llenar_prod_vnta(busc_cod(dato));
        for (Producto p : list) {
            cod_prove = p.getCod_prove();
        }
        return cod_prove;
    }

    public int ObtCantProd(String dato) {//cantidad de productos codigo del producto
        int cant = 0;
        list = llenar_prod_vnta(busc_cod(dato));
        for (Producto p : list) {
            cant = p.getStok();
        }
        return cant;
    }

    //editar IVA
    public void edit_iva(int cod_iva, double val_iva, int estad) {
        String update = "UPDATE tb_iva SET val_iva='" + val_iva + "', estado='" + estad + "' WHERE cod_iva= '" + cod_iva + "'";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(update);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println("Aqui esta el error del iva:" + e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Agregar Iva

    public void save_iva(int codiva, double valIva) {

        String save = "INSERT INTO tb_iva(cod_iva,val_iva) VALUES(" + codiva + "," + valIva + ") ";
        try {
            Connection cn = bd.Conectar();
            PreparedStatement pst = cn.prepareStatement(save);
            pst.executeUpdate();
            bd.desconectar();
        } catch (Exception e) {
            System.out.println("Aqui esta el error del iva:" + e);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultTableModel label_table_iva() {
        String[] column = {"Cod", "Valor", "Estado"};

        datos = new DefaultTableModel(null, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        return datos;
    }

    ArrayList<iva> listiva;

    public String busc_cod_iva(String cod_iva) {
        return ("SELECT * FROM tb_iva WHERE CONCAT(cod_iva) like '%" + cod_iva + "%'  order by cod_iva asc");
    }

    public String busc_estado_iva(String estado) {
        return "SELECT * FROM tb_iva WHERE estado= '" + estado + "'  order by cod_iva asc  ";
    }

    public ArrayList load_iva_tbl(String sql) {
        //  JOptionPane.showMessageDialog(null, estado);
        try {
            Connection cn = bd.Conectar();
            listiva = new ArrayList<iva>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                listiva.add(new iva(
                        rs.getInt("cod_iva"),
                        rs.getDouble("val_iva"),
                        rs.getBoolean("estado")));
            }
            bd.desconectar();
        } catch (Exception ex) {
            System.out.println("error aqui:" + ex);
        } catch (Throwable ex) {
            Logger.getLogger(reg_productos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listiva;
    }

    public Double ObtValIva(String dato) {//cantidad de productos codigo del producto
        double val = 0.0;
        listiva = load_iva_tbl(busc_estado_iva(dato));
        for (iva p : listiva) {
            val = p.getVal_iva();
        }
        return val*100;
    }

    public void desactivar_all_iva() {//Desactivar todos los usuario
        Connection cn = bd.Conectar();
        String update = "UPDATE tb_iva SET estado='" + 0 + "' WHERE estado = '" + 1 + "'";
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
}
