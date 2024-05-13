package Vista;

import Controlador.reg_caja;
import Controlador.reg_empresa;
import Controlador.reg_marca_tip;
import Controlador.reg_pedidos;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_prod_compras;
import Controlador.reg_productos;
import Controlador.reg_proveedor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import Modelo.Pedidos;
import Modelo.Permisos;
import Modelo.Personal;
import Modelo.Proveedores;
import Modelo.Validaciones;
import Modelo.det_caja;
import Modelo.det_pedidos;
import Modelo.empresa;
import Modelo.producto_compra;
import static Vista.Reportes_g.getformPresupuesto;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class vntana_pedidos extends javax.swing.JDialog {

    static reg_pedidos rped = new reg_pedidos();
    reg_proveedor rprov = new reg_proveedor();
    static reg_prod_compras rp = new reg_prod_compras();
    reg_marca_tip rmt = new reg_marca_tip();
    DefaultTableModel datosVenta;
    reg_personal ru = new reg_personal();
    Proveedores prove = new Proveedores();
    static det_pedidos detp = new det_pedidos();
    static Pedidos ped = new Pedidos();
    reg_permisos rperm = new reg_permisos();
    ArrayList<Permisos> listPerm;
    //String ced_user;
    reg_productos rpiv = new reg_productos();
    String ced;
    DecimalFormat fd = new DecimalFormat("0.00");

    //los codigos que van a ir al otro formulari
    public static double montoTotal = 0.0;
    public static String codCmpra = "";
    public static String codprov = "";
    static int esPedido = 0;

    public vntana_pedidos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());
        ced = menu_principal.ced_usu;
        loadPermisos(ced);
        text_fec.setText(Validaciones.fechaActual());

        load_user(ced);
        botones(false);
        cargarTable();

    }

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_regist()) {
                // btn_new.setEnabled(true);
            }
            if (per.isPer_udate()) {

            }
            if (per.isPer_delete()) {

            }
        }
    }

    public void cargarTable() {
        //cargarTablaVentas();
        labelTable();
        modelo_tabla();

    }

    public void modelo_tabla() {
        try {

            table_fac.getColumnModel().getColumn(0).setPreferredWidth(1);
            table_fac.getColumnModel().getColumn(1).setPreferredWidth(50);
            table_fac.getColumnModel().getColumn(2).setPreferredWidth(15);
            table_fac.getColumnModel().getColumn(3).setPreferredWidth(15);
            table_fac.getColumnModel().getColumn(4).setPreferredWidth(15);
            table_fac.getColumnModel().getColumn(5).setPreferredWidth(15);
            table_fac.getColumnModel().getColumn(6).setPreferredWidth(15);
            table_fac.getTableHeader().setBackground(Color.decode("#666666"));
            table_fac.getTableHeader().setForeground(Color.white);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    ArrayList<Personal> listuser;
    static int cod_user;

    public void load_user(String dato) {
        listuser = ru.user_active(ru.bus_user_ced(dato));
        for (Personal us : listuser) {
            text_usuario.setText(us.getNombre() + "  " + us.getApellido());
            // ced_user = us.getCed();
            cod_user = us.getCod_usua();

        }
    }

    ArrayList<Proveedores> listProv;
    ArrayList<producto_compra> listProd;

    public void cargarCamposProv(int cod) {
        listProv = rprov.llenar_Proveedores(rprov.sql_nom_prove(cod));
        for (Proveedores pr : listProv) {
            text_cod_prov.setText(pr.getCod_prove() + "");
            text_prov.setText(pr.getNom_prove());
            text_dir_prov.setText(pr.getDir_prov());
            text_ruc_prov.setText(pr.getCed_ruc());
            text_telf.setText(pr.getTelefono());
        }
    }

    public void cargarCamposProd(String dato) {
        listProd = rp.llenar_prod(rp.busc_cod_c(dato));
        for (producto_compra pr : listProd) {
            text_cod_pro.setText(pr.getCod_prod());
            text_desc_prod.setText(pr.getDescripcion());
            text_marc_prod.setText(rmt.ObtNomMarca(pr.getCod_marca() + ""));
            chec_iva_prod.setSelected(pr.isIva());
            text_prec_prod.setText(pr.getPrec_cmpra() + "");
        }
    }

    public void labelTable() {
        datosVenta = rp.label_det_compra();
        table_fac.getTableHeader().setReorderingAllowed(false);
        table_fac.setModel(datosVenta);

    }

    public void cargarTablaVentas() {
        try {

            String[] data = new String[7];
            data[0] = text_cod_pro.getText();
            data[1] = text_desc_prod.getText();
            data[2] = text_marc_prod.getText();
            data[3] = text_prec_prod.getText();
            data[4] = text_cant.getText();
            data[5] = fd.format(calcularIva()).replace(",", ".");
            data[6] = fd.format(calcularTotal()).replace(",", ".");
            datosVenta.addRow(data);
            table_fac.setModel(datosVenta);
        } catch (Exception err) {
            System.out.println("error:" + err);
        }
    }

    public static void LimpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) table_fac.getModel();
        int a = table_fac.getRowCount() - 1;
        int i;
        for (i = a; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public static void limpiar_campos() {
        text_ruc_prov.setText("");
        text_prov.setText("");
        text_cod_prov.setText("0");
        text_dir_prov.setText("");
        text_subtotal.setText("0.0");
        text_iva.setText("0.0");
        text_desc.setText("0.0");
        text_total.setText("0.0");
        text_cod_pro.setText("");
        text_desc_prod.setText("");
        text_marc_prod.setText("");
        text_prec_prod.setText("");
        text_cant.setText("");
        text_telf.setText("");
    }

    public Double calcularIva() {
        double aux = 0, iva = 0;
        if (chec_iva_prod.isSelected()) {
            aux = rpiv.ObtValIva(1 + "");
        }
        int cant = Integer.parseInt(text_cant.getText());
        double prec = Double.parseDouble(text_prec_prod.getText());
        iva = cant * prec * (aux / 100);

        return iva;
    }

    public Double calcularTotal() {
        Double prec = Double.parseDouble(text_prec_prod.getText());
        int cant = Integer.parseInt(text_cant.getText());
        return prec * cant;
    }

    public double descuento() {
        double des;
        des = Double.parseDouble(text_desc.getText());
        return des;
    }

    public void calcularDet() {
        Double iva = 0.0, importe = 0.0, auxV = 0.0, auxImp = 0.0;
        for (int i = 0; i < table_fac.getRowCount(); i++) {
            iva = Double.parseDouble(table_fac.getValueAt(i, 5).toString());
            importe = Double.parseDouble(table_fac.getValueAt(i, 6).toString());
            auxV = iva + auxV;
            auxImp = importe + auxImp;
        }
        text_subtotal.setText(fd.format(auxImp).replace(",", "."));
        text_iva.setText(fd.format(auxV).replace(",", "."));
        //descuento
        text_total.setText(fd.format(-descuento() + auxImp + auxV).replace(",", "."));
    }

    public boolean verf_prod(String cod_prod) {
        boolean estado = true;
        try {
            for (int i = 0; i < table_fac.getRowCount(); i++) {
                String cod_produc = table_fac.getValueAt(i, 0).toString();

                if (cod_prod.equals(cod_produc)) {
                    estado = false;
                }
            }
        } catch (Exception err) {
        }
        return estado;
    }

    public void pintar_campos_prod() {
        Validaciones.pinta_text(text_cod_pro);
        Validaciones.pinta_text(text_desc_prod);
        Validaciones.pinta_text(text_marc_prod);
        Validaciones.pinta_text(text_prec_prod);
        Validaciones.pinta_text(text_cant);
    }

    public void pintar_campos() {
        Validaciones.pinta_text(text_prov);
        Validaciones.pinta_text(text_dir_prov);
        Validaciones.pinta_text(text_ruc_prov);
        Validaciones.pinta_text(text_telf);
        Validaciones.pinta_text(text_subtotal);
        Validaciones.pinta_text(text_iva);
        Validaciones.pinta_text(text_total);

    }

    public static void save_cmpras() {

        ped.setCod_pedido((text_cod_fac.getText()));
        ped.setCod_prov(Integer.parseInt(text_cod_prov.getText()));
        ped.setCod_usu(cod_user);//guardar usuario

        ped.setFecha_pedid(Date.valueOf(text_fec.getText()));
        ped.setSubtotal(Double.parseDouble(text_subtotal.getText()));
        ped.setIva_pedid(Double.parseDouble(text_iva.getText()));
        ped.setDesc_pedid(Double.parseDouble(text_desc.getText()));
        ped.setTotal_pedid(Double.parseDouble(text_total.getText()));
        // JOptionPane.showMessageDialog(this, "Compra registrada");
        rped.insert(ped);
        save_detall_Ped();
        guardarDetalleCaja();
    }

    public static void save_detall_Ped() {
        Double prec, iva, importe;
        int cant;
        String cod_prod;
        for (int i = 0; i < table_fac.getRowCount(); i++) {

            cod_prod = (table_fac.getValueAt(i, 0).toString());
            //JOptionPane.showMessageDialog(this, cod_prod);
            prec = Double.parseDouble(table_fac.getValueAt(i, 3).toString());
            cant = Integer.parseInt(table_fac.getValueAt(i, 4).toString());
            iva = Double.parseDouble(table_fac.getValueAt(i, 5).toString());
            importe = Double.parseDouble(table_fac.getValueAt(i, 6).toString());

            detp.setCod_pedido((text_cod_fac.getText()));
            detp.setCod_prod(cod_prod);
            detp.setCant_pedid(cant);
            detp.setPrec_pedid(prec);
            detp.setIva_ped(iva);
            detp.setImporte_pedid(importe);
            detp.setEstado(true);
            //JOptionPane.showMessageDialog(this, "Detalle guardado");
            rped.insert_detalle(detp);
        }
    }

    public void botones(Boolean ok) {
        btn_bus_prov.setEnabled(ok);
        btn_bus_prod.setEnabled(ok);
    }

    public boolean form_validado_client() {
        boolean ok = true;
        if (!Validaciones.esRequerido(text_prov)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_dir_prov)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_ruc_prov)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_telf)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_subtotal)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_iva)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_total)) {
            ok = false;
        }
        form_validado_prod();
        return ok;
    }

    public boolean form_validado_prod() {
        boolean ok = true;
        if (!Validaciones.esRequerido(text_cod_pro)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_desc_prod)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_marc_prod)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_prec_prod)) {
            ok = false;
        }
        if (!Validaciones.esFlotante(text_cant)) {
            ok = false;
        }
        return ok;
    }

    public void imprimirTiket() {
        JOptionPane.showMessageDialog(this, "Realizando operacion.......Espere un momento");
        try {
            String num = text_cod_fac.getText();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cod_ped", num);
            System.out.println(num);
            Reportes_g.generarReporte("tiket_cmpras", params);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void imprimirFacturaCmpra() {
        JOptionPane.showMessageDialog(this, "Realizando operacion.......Espere un momento");
        try {
            String num = text_cod_fac.getText();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cod_ped", num);
            System.out.println(num);
            Reportes_g.generarReporte("rep_cmpras", params);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static det_caja dtc = new det_caja();
    static reg_caja rc = new reg_caja();
    //cargar cajas activas

    public static void guardarDetalleCaja() {

        dtc.setId_per_caja(rc.cod_caja_activa());//codigo de la caja activa 
        dtc.setFecha(Validaciones.fechaActual()); //fecha actual
        dtc.setDescripcion("Pedidos de Mercaderia"); //venta de algo
        dtc.setCod_vntas(null);//codigo de la venta
        dtc.setIngreso(0.0);//total
        dtc.setCod_pedido((text_cod_fac.getText()));//si es pedido codiog
        dtc.setEgreso(Double.parseDouble(text_total.getText()));//total del pago por pedido
        rc.SaveDetalleApertCaja(dtc);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btn_clean = new javax.swing.JButton();
        btn_cmpras = new javax.swing.JButton();
        btn_provee = new javax.swing.JButton();
        btn_prod = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        text_cod_fac = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        text_fec = new javax.swing.JTextField();
        pnel_inf = new javax.swing.JPanel();
        text_prov = new javax.swing.JTextField();
        lblProveedor = new javax.swing.JLabel();
        lblced = new javax.swing.JLabel();
        text_ruc_prov = new javax.swing.JTextField();
        lbldir = new javax.swing.JLabel();
        text_dir_prov = new javax.swing.JTextField();
        text_usuario = new javax.swing.JTextField();
        lblus = new javax.swing.JLabel();
        text_telf = new javax.swing.JTextField();
        btn_bus_prov = new javax.swing.JButton();
        lbltelf = new javax.swing.JLabel();
        lblProveedor1 = new javax.swing.JLabel();
        text_cod_prov = new javax.swing.JTextField();
        pnl_inf1 = new javax.swing.JPanel();
        lblprod = new javax.swing.JLabel();
        text_desc_prod = new javax.swing.JTextField();
        lblcantidad = new javax.swing.JLabel();
        lbliva = new javax.swing.JLabel();
        text_prec_prod = new javax.swing.JTextField();
        lblprecio = new javax.swing.JLabel();
        lblcodprod = new javax.swing.JLabel();
        text_cod_pro = new javax.swing.JTextField();
        text_cant = new javax.swing.JTextField();
        chec_iva_prod = new javax.swing.JCheckBox();
        lblmarca = new javax.swing.JLabel();
        text_marc_prod = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        btn_bus_prod = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_fac = new javax.swing.JTable();
        btn_agregar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pnel_inf1 = new javax.swing.JPanel();
        text_subtotal = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        text_iva = new javax.swing.JTextField();
        text_desc = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        text_total = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Compras");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Pedidos");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 30));

        jToolBar2.setBackground(new java.awt.Color(204, 204, 204));
        jToolBar2.setRollover(true);
        jToolBar2.setEnabled(false);

        btn_new.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new.setText("Nuevo");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_new);

        btn_clean.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_clean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_clean.setText("Limpiar");
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_clean);

        btn_cmpras.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_cmpras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/carros.png"))); // NOI18N
        btn_cmpras.setText("Compras");
        btn_cmpras.setFocusable(false);
        btn_cmpras.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btn_cmpras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_cmpras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cmprasActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_cmpras);

        btn_provee.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_provee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedor.png"))); // NOI18N
        btn_provee.setText("Proveedores");
        btn_provee.setFocusable(false);
        btn_provee.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btn_provee.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveeActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_provee);

        btn_prod.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos.png"))); // NOI18N
        btn_prod.setText("Productos");
        btn_prod.setFocusable(false);
        btn_prod.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btn_prod.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prodActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_prod);

        getContentPane().add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 780, 40));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Factura Número");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 30));

        text_cod_fac.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        text_cod_fac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_cod_fac.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_fac.setEnabled(false);
        getContentPane().add(text_cod_fac, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 110, 30));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Fecha de emisión");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, -1, 30));

        text_fec.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(text_fec, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 130, -1));

        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_prov.setEnabled(false);
        pnel_inf.add(text_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 290, -1));

        lblProveedor.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblProveedor.setText("Proveedor:");
        pnel_inf.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        lblced.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblced.setText("Ced/NIT:");
        pnel_inf.add(lblced, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, 20));

        text_ruc_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_ruc_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_ruc_prov.setEnabled(false);
        pnel_inf.add(text_ruc_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 130, -1));

        lbldir.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbldir.setText("Dirección:");
        pnel_inf.add(lbldir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        text_dir_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_dir_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_dir_prov.setEnabled(false);
        text_dir_prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_dir_provActionPerformed(evt);
            }
        });
        pnel_inf.add(text_dir_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 410, -1));

        text_usuario.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_usuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_usuario.setEnabled(false);
        pnel_inf.add(text_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 650, -1));

        lblus.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblus.setText("Realizado por:");
        pnel_inf.add(lblus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        text_telf.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_telf.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_telf.setEnabled(false);
        pnel_inf.add(text_telf, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 130, -1));

        btn_bus_prov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cliente.png"))); // NOI18N
        btn_bus_prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_provActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_bus_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 50, 50));

        lbltelf.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbltelf.setText("Teléfono:");
        pnel_inf.add(lbltelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, 20));

        lblProveedor1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblProveedor1.setText("Proveedor:");
        pnel_inf.add(lblProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 20));

        text_cod_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cod_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_prov.setEnabled(false);
        pnel_inf.add(text_cod_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 40, -1));

        getContentPane().add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 760, 110));

        pnl_inf1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnl_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblprod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblprod.setText("Producto:");
        pnl_inf1.add(lblprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, 20));

        text_desc_prod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_desc_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_desc_prod.setEnabled(false);
        pnl_inf1.add(text_desc_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 390, -1));

        lblcantidad.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblcantidad.setText("Cantidad:");
        pnl_inf1.add(lblcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, 20));

        lbliva.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbliva.setText("Iva:");
        pnl_inf1.add(lbliva, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, 20));

        text_prec_prod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_prec_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_prec_prod.setEnabled(false);
        pnl_inf1.add(text_prec_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 54, -1));

        lblprecio.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblprecio.setText("Precio compra Q:");
        pnl_inf1.add(lblprecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, 20));

        lblcodprod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblcodprod.setText("Código:");
        pnl_inf1.add(lblcodprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, 20));

        text_cod_pro.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cod_pro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_pro.setEnabled(false);
        pnl_inf1.add(text_cod_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 50, -1));

        text_cant.setBackground(new java.awt.Color(255, 255, 153));
        text_cant.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cantKeyReleased(evt);
            }
        });
        pnl_inf1.add(text_cant, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 90, -1));

        chec_iva_prod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        pnl_inf1.add(chec_iva_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        lblmarca.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblmarca.setText("Marca:");
        pnl_inf1.add(lblmarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, 20));

        text_marc_prod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_marc_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_marc_prod.setEnabled(false);
        pnl_inf1.add(text_marc_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 120, -1));

        jButton5.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnl_inf1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 40, 40));

        jButton9.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        pnl_inf1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 40, 40));

        btn_bus_prod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_bus_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/zoom.png"))); // NOI18N
        btn_bus_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_prodActionPerformed(evt);
            }
        });
        pnl_inf1.add(btn_bus_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 50));

        getContentPane().add(pnl_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 760, 70));

        table_fac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table_fac);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 760, 100));

        btn_agregar.setBackground(new java.awt.Color(5, 59, 86));
        btn_agregar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_agregar.setText("Guardar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 170, 40));

        jButton2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imprimir.png"))); // NOI18N
        jButton2.setText("Imprimir Pedido");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, 170, 40));

        pnel_inf1.setBackground(new java.awt.Color(51, 51, 51));

        text_subtotal.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Subtotal:");

        jLabel29.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Iva:");

        text_iva.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N

        text_desc.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_desc.setText("0.0");
        text_desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_descKeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Descuento:");

        text_total.setBackground(new java.awt.Color(0, 102, 102));
        text_total.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_total.setForeground(new java.awt.Color(255, 51, 51));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Total:");

        javax.swing.GroupLayout pnel_inf1Layout = new javax.swing.GroupLayout(pnel_inf1);
        pnel_inf1.setLayout(pnel_inf1Layout);
        pnel_inf1Layout.setHorizontalGroup(
            pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_inf1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnel_inf1Layout.createSequentialGroup()
                        .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnel_inf1Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnel_inf1Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_total, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnel_inf1Layout.setVerticalGroup(
            pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_inf1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_subtotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 430, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_dir_provActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_dir_provActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_dir_provActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // Nueva compra
        text_cod_fac.setText(rped.codigoPedido());
        botones(true);
        limpiar_campos();
        LimpiarTabla();

    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_bus_provActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_provActionPerformed
        // TODO add your handling code here:  
        vntana_ver_prove dlg = new vntana_ver_prove(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        int codProve = vntana_ver_prove.codigoProve;
        cargarCamposProv(codProve);
        pintar_campos();


    }//GEN-LAST:event_btn_bus_provActionPerformed

    private void btn_bus_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_prodActionPerformed
        // TODO add your handling code here:
        vntana_ver_prod_compras dlg = new vntana_ver_prod_compras(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        text_cant.setText("");
        pintar_campos_prod();
        String codigo = vntana_ver_prod_compras.codigo;
        cargarCamposProd(codigo);


    }//GEN-LAST:event_btn_bus_prodActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try {
            if (form_validado_prod()) {
                if (verf_prod(text_cod_pro.getText())) {
                    cargarTablaVentas();
                    calcularDet();
                    pintar_campos();
                } else {
                    JOptionPane.showMessageDialog(null, "Producto ya fue ingresado, "
                            + "\n para editarlo debes quitarlo y volverlo a ingresar");
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
            //JOptionPane.showMessageDialog(null, "Existe un error");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:

        if (table_fac.getRowCount() > 0) {
            if (form_validado_client()) {
                save_cmpras();
                pintar_campos();

                int seleccion = JOptionPane.showOptionDialog(null, "Modo de Impresion",
                        "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new Object[]{"Tiket ", "Factura ",}, "Si");
                if (seleccion == JOptionPane.YES_OPTION) {
                    imprimirTiket();
                }
                if (seleccion == JOptionPane.NO_OPTION) {
                    imprimirFacturaCmpra();
                }

                limpiar_campos();
                LimpiarTabla();
                botones(false);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar al menos un producto");
        }


    }//GEN-LAST:event_btn_agregarActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel modelo = (DefaultTableModel) table_fac.getModel();
            int fila = table_fac.getSelectedRow();
            if (fila >= 0) {
                modelo.removeRow(fila);
                calcularDet();
            } else {
                JOptionPane.showMessageDialog(null, "No Selecciono ninguna fila");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void text_cantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cantKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esFlotante(text_cant)) {
            Validaciones.pinta_text(text_cant);
        }
    }//GEN-LAST:event_text_cantKeyReleased

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        LimpiarTabla();
        botones(false);
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void btn_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prodActionPerformed
        // TODO add your handling code here:

        vntana_productos_bodega dlg = new vntana_productos_bodega(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);


    }//GEN-LAST:event_btn_prodActionPerformed

    private void btn_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveeActionPerformed
        // TODO add your handling code here:

        vntana_proveedores dlg = new vntana_proveedores(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);


    }//GEN-LAST:event_btn_proveeActionPerformed

    private void btn_cmprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cmprasActionPerformed

        vntana_ver_cmpras dlg = new vntana_ver_cmpras(null, false);
        // dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);


    }//GEN-LAST:event_btn_cmprasActionPerformed

    private void text_descKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_descKeyReleased
        // TODO add your handling code here

        try {
            calcularDet();
        } catch (Exception err) {
            System.out.println(err);
        }

    }//GEN-LAST:event_text_descKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (table_fac.getRowCount() > 0) {

            ArrayList<empresa> listem;
            reg_empresa rem = new reg_empresa();
            Document document = new Document(PageSize.A4, 35, 30, 50, 50);
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getformPresupuesto() + text_cod_fac.getText() + ".pdf"));
                document.open();

                /* Image portada = Image.getInstance(getPathImagenes() + "LOGO.png");
                 portada.setAlignment(Element.ALIGN_CENTER);
                 portada.scalePercent(150f);// tamaño de imagen

                 document.add(portada);*/
                listem = rem.llenar_empresa(rem.sql_estado_empresa(1 + ""));
                for (empresa em : listem) {
                    Paragraph encabezado = new Paragraph(em.getNom_empresa() + "\n" + em.getDireccion()
                            + "\n Ruc:" + em.getRuc() + " Télefono: " + em.getTelf() + "\n Email" + em.getEmail());
                    encabezado.setAlignment(Element.ALIGN_CENTER);
                    document.add(encabezado);

                }
                document.add(new Paragraph(" "));

                Paragraph encabezado = new Paragraph("| PRESUPUESTO DE COMPRAS |");
                encabezado.setAlignment(Element.ALIGN_CENTER);
                document.add(encabezado);

                document.add(new Paragraph("Numero Fact. : " + text_cod_fac.getText()));
                document.add(new Paragraph("Fecha : [ " + text_fec.getText() + " ]  "));
                document.add(new Paragraph("Cliente : " + text_prov.getText() + " " + " - DNI : " + text_ruc_prov.getText()));
                document.add(new Paragraph("Direccion : " + text_dir_prov.getText()));

                document.add(new Paragraph(" "));
                // document.add(new Paragraph("Subtotal : [ " + txtsubtotal.getText() + " ] - Descuento: [" + textDesc.getText() + "] - IVG: [" + text_iva.getText() + "]  - Total a Pagar : $ " + text_total.getText() + " soles"));

                document.add(new Paragraph(" "));
                PdfPTable table = new PdfPTable(6);
                float[] medidaCeldas = {3.55f, 1.00f, 0.80f, 0.80f, 0.80f, 0.80f};
                table.addCell("Descripcion");
                table.addCell("Marca");
                table.addCell("Precio");
                table.addCell("Cant.");
                table.addCell("Iva");
                table.addCell("Total");

                for (int i = 0; i < table_fac.getRowCount(); i++) {
                    table.addCell(table_fac.getValueAt(i, 1).toString());
                    table.addCell(table_fac.getValueAt(i, 2).toString());
                    table.addCell(table_fac.getValueAt(i, 3).toString());
                    table.addCell(table_fac.getValueAt(i, 4).toString());
                    table.addCell(table_fac.getValueAt(i, 5).toString());
                    table.addCell(table_fac.getValueAt(i, 6).toString());
                    table.setWidths(medidaCeldas);

                }
                table.setWidthPercentage(100f);
                document.add(table);

                Paragraph parrafo = new Paragraph("Subtotal : [$ " + text_subtotal.getText() + " ] \n "
                        + " Descuento: [$" + text_desc.getText() + "]\n"
                        + "IVA: [$" + text_iva.getText() + "]\n"
                        + "Total a Pagar : $ " + text_total.getText() + " ");

                parrafo.setAlignment(Element.ALIGN_RIGHT);
                document.add(parrafo);

//cierra el documento
                document.close();
                JOptionPane.showMessageDialog(null, "Generando Proforma de Pedido........");

                try {
                    File path = new File(getformPresupuesto() + "/" + text_cod_fac.getText() + ".pdf");
                    Desktop.getDesktop().open(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Documento esta abierto");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar al menos un producto");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vntana_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_pedidos dialog = new vntana_pedidos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_bus_prod;
    private javax.swing.JButton btn_bus_prov;
    private javax.swing.JButton btn_clean;
    private javax.swing.JButton btn_cmpras;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton btn_prod;
    private javax.swing.JButton btn_provee;
    private javax.swing.JCheckBox chec_iva_prod;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblProveedor1;
    private javax.swing.JLabel lblcantidad;
    private javax.swing.JLabel lblced;
    private javax.swing.JLabel lblcodprod;
    private javax.swing.JLabel lbldir;
    private javax.swing.JLabel lbliva;
    private javax.swing.JLabel lblmarca;
    private javax.swing.JLabel lblprecio;
    private javax.swing.JLabel lblprod;
    private javax.swing.JLabel lbltelf;
    private javax.swing.JLabel lblus;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JPanel pnl_inf1;
    public static javax.swing.JTable table_fac;
    public static javax.swing.JTextField text_cant;
    public static javax.swing.JTextField text_cod_fac;
    public static javax.swing.JTextField text_cod_pro;
    public static javax.swing.JTextField text_cod_prov;
    public static javax.swing.JTextField text_desc;
    public static javax.swing.JTextField text_desc_prod;
    public static javax.swing.JTextField text_dir_prov;
    public static javax.swing.JTextField text_fec;
    public static javax.swing.JTextField text_iva;
    public static javax.swing.JTextField text_marc_prod;
    public static javax.swing.JTextField text_prec_prod;
    public static javax.swing.JTextField text_prov;
    public static javax.swing.JTextField text_ruc_prov;
    public static javax.swing.JTextField text_subtotal;
    public static javax.swing.JTextField text_telf;
    public static javax.swing.JTextField text_total;
    private javax.swing.JTextField text_usuario;
    // End of variables declaration//GEN-END:variables
}
