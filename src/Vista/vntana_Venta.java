/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_caja;
import java.sql.Date;
import Controlador.reg_clientes;
import Controlador.reg_empresa;
import Controlador.reg_marca_tip;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_productos;
import Controlador.reg_vntas;
import Modelo.Cliente;
import Modelo.Permisos;
import Modelo.Personal;
import Modelo.Producto;
import Modelo.Validaciones;
import Modelo.Ventas;
import Modelo.det_caja;
import Modelo.det_vntas;
import Modelo.empresa;
import static Vista.Reportes_g.getPathImagenes;
import static Vista.Reportes_g.getformPresupuesto;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class vntana_Venta extends javax.swing.JDialog {

    static int esVenta = 0;
    static reg_vntas rv = new reg_vntas();
    static det_vntas dv = new det_vntas();
    static Ventas vn = new Ventas();
    reg_clientes recli = new reg_clientes();
    static reg_productos rp = new reg_productos();
    reg_marca_tip rmt = new reg_marca_tip();
    DefaultTableModel datosVenta;
    reg_personal ru = new reg_personal();
    reg_permisos rperm = new reg_permisos();
    ArrayList<Permisos> listPerm;
    ArrayList<Cliente> listCli;
    ArrayList<Producto> listProd;
    ArrayList<Personal> listuser;
    static int cod_usu;
    String ced;
    DecimalFormat fd = new DecimalFormat("0.00");

    //los codigos que van a ir al otro formulari
    public static double montoTotal = 0.0;
    public static String codVenta = "";
    public static int codCli = 0;

    public vntana_Venta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());

        ced = menu_principal.ced_usu;
        loadPermisos(ced);
        text_fec.setText(Validaciones.fechaActual());
        botones(false);
        load_user(ced);
        cargarTable();

    }

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_regist()) {
                btn_new.setEnabled(true);
            }
            if (per.isPer_udate()) {

            }
            if (per.isPer_delete()) {

            }
        }
    }

    public void load_user(String dato) {
        listuser = ru.user_active(ru.bus_user_ced(dato));
        for (Personal us : listuser) {
            text_usuario.setText(us.getNombre() + "  " + us.getApellido());
            // ced_user = us.getCed();
            cod_usu = us.getCod_usua();

        }
    }

    public void cargarCamposProv(String cod) {
        listCli = recli.llenar_Clientes(recli.load_cliente(cod));
        for (Cliente pr : listCli) {
            txt_cod_cli.setText(pr.getCod_clien() + "");
            text_prov.setText(pr.getNombre() + " " + pr.getApellido());
            text_dir_prov.setText(pr.getDireccion());
            text_ruc_prov.setText(pr.getCed());
            text_telf.setText(pr.getTelefono());
        }
    }

    public void cargarTable() {
        labelTable();
        modelo_tabla();
    }

    public void modelo_tabla() {
        try {

            table_fac.getColumnModel().getColumn(0).setPreferredWidth(1);
            table_fac.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_fac.getColumnModel().getColumn(2).setPreferredWidth(10);
            table_fac.getColumnModel().getColumn(3).setPreferredWidth(10);
            table_fac.getColumnModel().getColumn(4).setPreferredWidth(10);
            table_fac.getColumnModel().getColumn(5).setPreferredWidth(5);
            table_fac.getColumnModel().getColumn(6).setPreferredWidth(10);
            table_fac.getTableHeader().setBackground(Color.decode("#666666"));
            table_fac.getTableHeader().setForeground(Color.white);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void cargarCamposProd(String dato) {
        listProd = rp.llenar_prod_vnta(dato);
        for (Producto pr : listProd) {
            text_cod_pro.setText(pr.getCod_prod());
            text_desc_prod.setText(pr.getDescripcion());
            text_marc_prod.setText(rmt.ObtNomMarca(pr.getCod_marca() + ""));
            chec_iva_prod.setSelected(pr.isIva());
            text_prec_prod.setText(pr.getPrec_vnta() + "");
            text_stok.setText(pr.getStok() + "");
        }
    }

    public void labelTable() {
        datosVenta = rp.label_det_vnta();
        table_fac.getTableHeader().setReorderingAllowed(false);
        table_fac.setModel(datosVenta);
    }

    public void cargarTablaVentas() {
        try {
            //FALTA ARREGLAR ESTO LOS CALCULOS
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
            System.out.println("aquie el error:" + err);
        }
    }

    public Double calcularIva() {
        double aux = 0;
        if (chec_iva_prod.isSelected()) {
            aux = rp.ObtValIva(1 + "");

        }
        int cant = Integer.parseInt(text_cant.getText());
        Double prec = Double.parseDouble(text_prec_prod.getText());

        double iva = cant * prec * (aux / 100);
        return iva;
    }

    public Double calcularTotal() {
        Double prec = Double.parseDouble(text_prec_prod.getText());
        int cant = Integer.parseInt(text_cant.getText());
        return prec * cant;
    }

    public void calcularDet() {

        Double iva = 0.0, importe = 0.0, auxV = 0.0, auxImp = 0.0, desc;
        for (int i = 0; i < table_fac.getRowCount(); i++) {

            iva = Double.parseDouble(table_fac.getValueAt(i, 5).toString());
            importe = Double.parseDouble(table_fac.getValueAt(i, 6).toString());
            auxV = iva + auxV;
            auxImp = importe + auxImp;

        }
        text_subtotal.setText(fd.format(auxImp).replace(",", "."));
        text_iva.setText(fd.format(auxV).replace(",", "."));//salir el valor en menos decimales   
        text_total.setText(fd.format(-descuento() + auxImp + auxV).replace(",", "."));
    }

    public static void limpiar_campos() {
        txt_cod_cli.setText("0");
        // text_cod_fac.setText("");
        text_ruc_prov.setText("");
        text_prov.setText("");
        // text_usuario.setText("");
        text_dir_prov.setText("");
        // text_fec.setText("");
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
        text_stok.setText("");

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

    public boolean verf_prod(String cod_prod) {
        boolean estado = true;
        for (int i = 0; i < table_fac.getRowCount(); i++) {
            String cod_produc = table_fac.getValueAt(i, 0).toString();
            if (cod_prod.equals(cod_produc)) {
                estado = false;
            }
        }
        return estado;
    }

    public static void save_detall_vntas() {
        Double prec, iva, importe;
        int cant;
        String cod_prod;
        for (int i = 0; i < table_fac.getRowCount(); i++) {
            cod_prod = (table_fac.getValueAt(i, 0).toString());
            prec = Double.parseDouble(table_fac.getValueAt(i, 3).toString());
            cant = Integer.parseInt(table_fac.getValueAt(i, 4).toString());
            iva = Double.parseDouble(table_fac.getValueAt(i, 5).toString());
            importe = Double.parseDouble(table_fac.getValueAt(i, 6).toString());

            rp.prod_stok(cod_prod, cant);

            rp.descont_stok();//descontar productos
            dv.setCod_vnta(text_cod_fac.getText());
            dv.setCod_prod(cod_prod);
            dv.setCant(cant);
            dv.setPrecio(prec);
            dv.setIva(iva);
            dv.setImporte(importe);
            rv.insert_detalle(dv);
        }
    }

    public static void save_vntas() {
        vn.setCod_vnta(text_cod_fac.getText());
        vn.setCod_cli(Integer.parseInt(txt_cod_cli.getText()));
        vn.setName_cli(text_prov.getText());
        vn.setCod_usu(cod_usu); //guardar codigo del usuario 
        vn.setFecha(Date.valueOf(Validaciones.fechaActualSql()));
        //vn.setFecha(Date.valueOf(text_fec.getText()));
        vn.setSubtotal(Double.parseDouble(text_subtotal.getText()));
        vn.setIva_vntas(Double.parseDouble(text_iva.getText()));
        vn.setDesc(Double.parseDouble(text_desc.getText()));
        vn.setTotal(Double.parseDouble(text_total.getText()));
        rv.insert(vn);
        save_detall_vntas();
        guardarDetalleCaja();
    }

    public static void LimpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) table_fac.getModel();
        int a = table_fac.getRowCount() - 1;
        int i;
        for (i = a; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public void botones(Boolean ok) {
        btn_bus_cli.setEnabled(ok);
        btn_bus_prod.setEnabled(ok);
    }

    public double descuento() {
        double des;
        des = Double.parseDouble(text_desc.getText());
        return des;
    }

    public void imprimirFacturaVenta() {
        JOptionPane.showMessageDialog(this, "Realizando operacion.......Espere un momento");
        try {
            String num = text_cod_fac.getText();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cod_vntas", num);
            System.out.println(num);
            Reportes_g.generarReporte("rep_vntas", params);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void imprimirTiketVenta() {
        JOptionPane.showMessageDialog(this, "Realizando operacion.......Espere un momento");
        try {
            String num = text_cod_fac.getText();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cod_vntas", num);
            System.out.println(num);
            Reportes_g.generarReporte("tiket_vntas", params);
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
        dtc.setDescripcion("Ventas de Mercaderia"); //venta de algo
        dtc.setCod_vntas(text_cod_fac.getText());//codigo de la venta
        dtc.setIngreso(Double.parseDouble(text_total.getText()));//total
        dtc.setCod_pedido(null);//si es pedido codiog
        dtc.setEgreso(0.0);//total del pago por pedido
        rc.SaveDetalleApertCaja(dtc);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        text_cod_fac = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
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
        btn_bus_cli = new javax.swing.JButton();
        lbltelf = new javax.swing.JLabel();
        lblProveedor1 = new javax.swing.JLabel();
        txt_cod_cli = new javax.swing.JTextField();
        pnl_inf1 = new javax.swing.JPanel();
        lblprod = new javax.swing.JLabel();
        text_desc_prod = new javax.swing.JTextField();
        lblstok = new javax.swing.JLabel();
        lbliva = new javax.swing.JLabel();
        text_prec_prod = new javax.swing.JTextField();
        lblprecio = new javax.swing.JLabel();
        lblcodprod = new javax.swing.JLabel();
        text_cod_pro = new javax.swing.JTextField();
        text_cant = new javax.swing.JTextField();
        chec_iva_prod = new javax.swing.JCheckBox();
        lblmarca = new javax.swing.JLabel();
        text_marc_prod = new javax.swing.JTextField();
        lblcantidad = new javax.swing.JLabel();
        text_stok = new javax.swing.JTextField();
        btn_bus_prod = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_fac = new javax.swing.JTable();
        btn_agregar = new javax.swing.JButton();
        pnel_inf1 = new javax.swing.JPanel();
        text_subtotal = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        text_iva = new javax.swing.JTextField();
        text_desc = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        text_total = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        btn_new = new javax.swing.JButton();
        btn_clean = new javax.swing.JButton();
        text_fec = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventas");
        setBackground(new java.awt.Color(255, 204, 51));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ventas");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(718, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Factura Número");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 41, -1, 30));

        text_cod_fac.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        text_cod_fac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_cod_fac.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        text_cod_fac.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_fac.setEnabled(false);
        getContentPane().add(text_cod_fac, new org.netbeans.lib.awtextra.AbsoluteConstraints(381, 40, 120, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel14.setText("Fecha de emision ");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(519, 40, -1, 31));

        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_prov.setEnabled(false);
        pnel_inf.add(text_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 270, -1));

        lblProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblProveedor.setText("Código:");
        pnel_inf.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        lblced.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblced.setText("Cédula/DPI:");
        pnel_inf.add(lblced, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, 20));

        text_ruc_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_ruc_prov.setEnabled(false);
        pnel_inf.add(text_ruc_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 130, -1));

        lbldir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbldir.setText("Dirección:");
        pnel_inf.add(lbldir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        text_dir_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_dir_prov.setEnabled(false);
        text_dir_prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_dir_provActionPerformed(evt);
            }
        });
        pnel_inf.add(text_dir_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 370, -1));

        text_usuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_usuario.setEnabled(false);
        text_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_usuarioActionPerformed(evt);
            }
        });
        pnel_inf.add(text_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 640, -1));

        lblus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblus.setText("Atendido por:");
        pnel_inf.add(lblus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        text_telf.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_telf.setEnabled(false);
        pnel_inf.add(text_telf, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 130, 20));

        btn_bus_cli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cliente.png"))); // NOI18N
        btn_bus_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_cliActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_bus_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, 50, 50));

        lbltelf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbltelf.setText("Teléfono:");
        pnel_inf.add(lbltelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, 20));

        lblProveedor1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblProveedor1.setText("Cliente:");
        pnel_inf.add(lblProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 20));

        txt_cod_cli.setEnabled(false);
        pnel_inf.add(txt_cod_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 40, -1));

        getContentPane().add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 750, 100));

        pnl_inf1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnl_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblprod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblprod.setText("Producto:");
        pnl_inf1.add(lblprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 12, -1, 20));

        text_desc_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_desc_prod.setEnabled(false);
        pnl_inf1.add(text_desc_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 13, 245, -1));

        lblstok.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblstok.setText("Stok :");
        pnl_inf1.add(lblstok, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 12, 50, 20));

        lbliva.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbliva.setText("Iva:");
        pnl_inf1.add(lbliva, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 38, -1, 20));

        text_prec_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_prec_prod.setEnabled(false);
        pnl_inf1.add(text_prec_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 40, 54, -1));

        lblprecio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblprecio.setText("Precio:");
        pnl_inf1.add(lblprecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 38, -1, 20));

        lblcodprod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcodprod.setText("Código:");
        pnl_inf1.add(lblcodprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 12, -1, 20));

        text_cod_pro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_pro.setEnabled(false);
        pnl_inf1.add(text_cod_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 13, 42, -1));

        text_cant.setBackground(new java.awt.Color(255, 255, 153));
        text_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cantKeyReleased(evt);
            }
        });
        pnl_inf1.add(text_cant, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 40, 70, -1));

        chec_iva_prod.setEnabled(false);
        pnl_inf1.add(chec_iva_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 38, -1, -1));

        lblmarca.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblmarca.setText("Marca:");
        pnl_inf1.add(lblmarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 39, -1, 20));

        text_marc_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_marc_prod.setEnabled(false);
        pnl_inf1.add(text_marc_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(348, 40, 137, -1));

        lblcantidad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcantidad.setText("Cantidad:");
        pnl_inf1.add(lblcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 39, 60, 20));

        text_stok.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_stok.setEnabled(false);
        pnl_inf1.add(text_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 13, 70, -1));

        btn_bus_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lupa.png"))); // NOI18N
        btn_bus_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_prodActionPerformed(evt);
            }
        });
        pnl_inf1.add(btn_bus_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, 57, 47));

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        pnl_inf1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 12, 40, 40));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnl_inf1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(691, 12, 40, 40));

        getContentPane().add(pnl_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 750, 70));

        table_fac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table_fac);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 750, 110));

        btn_agregar.setBackground(new java.awt.Color(0, 153, 102));
        btn_agregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_agregar.setText("G U A R D A R");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 170, 50));

        pnel_inf1.setBackground(new java.awt.Color(51, 51, 51));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_subtotal.setText("0.0");
        text_subtotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_subtotal.setEnabled(false);
        pnel_inf1.add(text_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 11, 90, -1));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Subtotal:");
        pnel_inf1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 11, -1, 20));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Iva:");
        pnel_inf1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 37, -1, 20));

        text_iva.setText("0.0");
        text_iva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_iva.setEnabled(false);
        pnel_inf1.add(text_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 37, 90, -1));

        text_desc.setText("0.0");
        text_desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_descKeyReleased(evt);
            }
        });
        pnel_inf1.add(text_desc, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 64, 90, -1));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Descuento:");
        pnel_inf1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 63, -1, 20));

        text_total.setBackground(new java.awt.Color(0, 102, 102));
        text_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        text_total.setForeground(new java.awt.Color(255, 51, 51));
        text_total.setText("0.0");
        text_total.setDisabledTextColor(new java.awt.Color(0, 51, 153));
        text_total.setEnabled(false);
        pnel_inf1.add(text_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 90, -1));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Total:");
        pnel_inf1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 92, -1, 20));

        getContentPane().add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 390, 190, 130));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/pdf.png"))); // NOI18N
        jButton3.setText("<html>\n\n<p>Imprimir</p>\n<p>Presupuesto</p>\n\n</html>\n");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 150, 50));

        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new.setText("Nuevo");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        getContentPane().add(btn_new, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 40));

        btn_clean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        btn_clean.setText("Limpiar");
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clean, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 30, -1, 40));

        text_fec.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        text_fec.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text_fec.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_fec.setEnabled(false);
        getContentPane().add(text_fec, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, 120, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_dir_provActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_dir_provActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_dir_provActionPerformed

    private void btn_bus_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_cliActionPerformed
        // TODO add your handling code here:
        vntana_ver_cli dlg = new vntana_ver_cli(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        String codcli = vntana_ver_cli.codigoCli;
        cargarCamposProv(codcli);
        pintar_campos();
    }//GEN-LAST:event_btn_bus_cliActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (form_validado_prod()) {
            if (Integer.parseInt(text_stok.getText()) >= Integer.parseInt(text_cant.getText())) {
                if (verf_prod(text_cod_pro.getText())) {//verifica si el producto ya esta ingresado
                    cargarTablaVentas();
                    calcularDet();
                    pintar_campos();
                } else {
                    JOptionPane.showMessageDialog(null, "Producto ya fue ingresado, "
                            + "\n para editarlo debes quitarlo y volverlo a ingresar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No existe la contidad solicitada en el stok");
                text_cant.setText("");
            }
        }


    }//GEN-LAST:event_jButton5ActionPerformed

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

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // Nueva compra
        // text_cod_fac.setText(rv.codigoVentas() + "");
        text_cod_fac.setText(rv.codFact());

        botones(true);
        pintar_campos();
        limpiar_campos();
        LimpiarTabla();
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_bus_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_prodActionPerformed
        // TODO add your handling code here:

        vntana_ver_prod dlg = new vntana_ver_prod(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        text_cant.setText("");
        pintar_campos_prod();

        String codigo = vntana_ver_prod.codigo;
        cargarCamposProd(rp.busc_cod(codigo));
        //}
    }//GEN-LAST:event_btn_bus_prodActionPerformed

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        LimpiarTabla();
        botones(false);
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void text_cantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cantKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esFlotante(text_cant)) {
            Validaciones.pinta_text(text_cant);
        }
    }//GEN-LAST:event_text_cantKeyReleased

    private void text_descKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_descKeyReleased
        // TODO add your handling code here:
        try {
            calcularDet();
        } catch (Exception err) {
            System.out.println(err);
        }
    }//GEN-LAST:event_text_descKeyReleased

    private void text_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_usuarioActionPerformed
    protected PdfNumber orientation = PdfPage.PORTRAIT;

    //GENERA PDF SOBRE EL REGISTRO DE VENTAS 
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (table_fac.getRowCount() > 0) {

            ArrayList<empresa> listem;
            reg_empresa rem = new reg_empresa();
            Document document = new Document(PageSize.A4, 35, 30, 50, 50);
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getformPresupuesto() + text_cod_fac.getText() + ".pdf"));
                document.open();

                Image portada = Image.getInstance(getPathImagenes() + "ferrocon.jpg");
                 portada.setAlignment(Element.ALIGN_CENTER);
                 portada.scalePercent(15f);// tamaño de imagen

                 document.add(portada);
                listem = rem.llenar_empresa(rem.sql_estado_empresa(1 + ""));
                for (empresa em : listem) {
                    Paragraph encabezado = new Paragraph(em.getNom_empresa() + "\n" + em.getDireccion()
                            + "\n Ruc:" + em.getRuc() + " Télefono: " + em.getTelf() + "\n Email" + em.getEmail());
                    encabezado.setAlignment(Element.ALIGN_CENTER);
                    document.add(encabezado);

                }
                document.add(new Paragraph(" "));

                Paragraph encabezado = new Paragraph("| PRESUPUESTO DE VENTAS |");
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
                    table.addCell(table_fac.getValueAt(i, 5).toString());
                    table.setWidths(medidaCeldas);

                }
                table.setWidthPercentage(100f);
                document.add(table);

                Paragraph parrafo = new Paragraph("Subtotal : [Q " + text_subtotal.getText() + " ] \n "
                        + " Descuento: [Q" + text_desc.getText() + "]\n"
                        + "IVA: [Q" + text_iva.getText() + "]\n"
                        + "Total a Pagar : Q " + text_total.getText() + " ");

                parrafo.setAlignment(Element.ALIGN_RIGHT);
                document.add(parrafo);

//cierra el documento
                document.close();
                JOptionPane.showMessageDialog(null, "Generando Proforma de Venta........");

                try {
                    File path = new File(getformPresupuesto() + "/" + text_cod_fac.getText() + ".pdf");
                    Desktop.getDesktop().open(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Documento esta abierto...Cerrar para continuar con la operación", "Error", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar al menos un producto", "Error", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        if (table_fac.getRowCount() > 0) {
            if (form_validado_client()) {
                save_vntas();
                //Guardar el detalle de caja
                pintar_campos();
                botones(false);
                //imprimir factura
                int seleccion = JOptionPane.showOptionDialog(null, "Modo de Impresion",
                    " Mensaje ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Tiket ", "Factura ",}, "Si");
                if (seleccion == JOptionPane.YES_OPTION) {
                    imprimirTiketVenta();
                }
                if (seleccion == JOptionPane.NO_OPTION) {
                    imprimirFacturaVenta();
                }
                limpiar_campos();
                LimpiarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar al menos un producto", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_btn_agregarActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_Venta dialog = new vntana_Venta(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_bus_cli;
    private javax.swing.JButton btn_bus_prod;
    private javax.swing.JButton btn_clean;
    private javax.swing.JButton btn_new;
    private javax.swing.JCheckBox chec_iva_prod;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel lblstok;
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
    public static javax.swing.JTextField text_desc;
    public static javax.swing.JTextField text_desc_prod;
    public static javax.swing.JTextField text_dir_prov;
    public static javax.swing.JTextField text_fec;
    public static javax.swing.JTextField text_iva;
    public static javax.swing.JTextField text_marc_prod;
    public static javax.swing.JTextField text_prec_prod;
    public static javax.swing.JTextField text_prov;
    public static javax.swing.JTextField text_ruc_prov;
    public static javax.swing.JTextField text_stok;
    public static javax.swing.JTextField text_subtotal;
    public static javax.swing.JTextField text_telf;
    public static javax.swing.JTextField text_total;
    private javax.swing.JTextField text_usuario;
    public static javax.swing.JTextField txt_cod_cli;
    // End of variables declaration//GEN-END:variables
}
