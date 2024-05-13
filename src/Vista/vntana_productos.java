package Vista;

import Controlador.reg_marca_tip;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_productos;
import Controlador.reg_proveedor;
import Modelo.Marca;
import Modelo.Permisos;
import Modelo.Producto;
import Modelo.Proveedores;
import Modelo.tipo;
import Modelo.Validaciones;
import Modelo.producto_compra;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class vntana_productos extends javax.swing.JDialog {

    reg_marca_tip rmt = new reg_marca_tip();
    reg_productos rp = new reg_productos();
    Producto pr = new Producto();
    reg_proveedor rprov = new reg_proveedor();
    DefaultTableModel datos;
    ArrayList<Producto> listprod;
    String cod_vnta;
    public static boolean actCant = true;
    boolean delet = false;
    boolean update = false;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    ArrayList<Permisos> listPerm;
    String ced;
    ArrayList<Marca> listMarc;
    ArrayList<Proveedores> listProv;
    ArrayList<tipo> listTipPrend;

    public vntana_productos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());
        blockBotones(false);//desactivado todos los botones
        ced = menu_principal.ced_usu;
        loadPermisos(ced);
        try {
            tabla_load(rp.busc_cod(""));
            cargarCombMarca("");
            cargarCombPrendas();
            text_cont.setText(rp.countProd() + "");
            bloquear_campos(false);
            cargarProveedores("");
            lbl_porcIva.setText(rp.ObtValIva("1") + "");
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_regist()) {
                btn_new.setEnabled(true);
            }
            if (per.isPer_udate()) {
                btn_actualizar.setEnabled(true);
                update = true;
            }
            if (per.isPer_delete()) {
                btn_delete.setEnabled(true);
                delet = true;
            }
        }
    }

    public boolean actBotones(boolean upda, boolean delet) {
        btn_actualizar.setEnabled(upda);//true
        btn_delete.setEnabled(delet);//true
        return true;
    }

    public void blockBotones(boolean ok) {
        btn_new.setEnabled(ok);
        btn_agregar.setEnabled(ok);
        btn_actualizar.setEnabled(ok);
        btn_delete.setEnabled(ok);
    }

    public void tabla_load(String dato) {
        load_table(dato);
        modelo_tabla();
    }

    public void modelo_tabla() {
        try {
            table_prod.getColumnModel().getColumn(0).setPreferredWidth(5);
            table_prod.getColumnModel().getColumn(1).setPreferredWidth(15);
            table_prod.getColumnModel().getColumn(2).setPreferredWidth(35);
            table_prod.getColumnModel().getColumn(3).setPreferredWidth(35);
            table_prod.getColumnModel().getColumn(4).setPreferredWidth(40);
            table_prod.getColumnModel().getColumn(5).setPreferredWidth(40);
            table_prod.getColumnModel().getColumn(6).setPreferredWidth(25);
            table_prod.getColumnModel().getColumn(7).setPreferredWidth(25);
            table_prod.getColumnModel().getColumn(8).setPreferredWidth(200);
            table_prod.getColumnModel().getColumn(9).setPreferredWidth(200);
            table_prod.getTableHeader().setBackground(Color.decode("#666666"));
            table_prod.getTableHeader().setForeground(Color.WHITE);
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            table_prod.getColumnModel().getColumn(0).setCellRenderer(tcr);
            DefaultTableCellRenderer tcr1 = new DefaultTableCellRenderer();
            tcr1.setHorizontalAlignment(SwingConstants.RIGHT);
            table_prod.getColumnModel().getColumn(3).setCellRenderer(tcr1);
            table_prod.getColumnModel().getColumn(4).setCellRenderer(tcr1);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void cargarProveedores(String dato) {//poner dato
        cbox_prov.removeAllItems();
        cbox_prov.addItem("");
        listProv = rprov.llenar_Proveedores(rprov.sql_prove(dato));
        for (Proveedores p : listProv) {
            cbox_prov.addItem(p.getNom_prove());
        }
    }

    public void cargarCombMarca(String cod) {
        combo_marca.removeAllItems();
        combo_marca.addItem("");
        cmbo_marca.removeAllItems();
        cmbo_marca.addItem("");
        listMarc = rmt.CargarListMarca(cod);
        for (Marca m : listMarc) {
            combo_marca.addItem(m.getNom_marca());
            cmbo_marca.addItem(m.getNom_marca());
        }
    }

    public void cargarCombPrendas() {
        combo_tip.removeAllItems();
        combo_tip.addItem("");
        comb_tipo1.removeAllItems();
        comb_tipo1.addItem("");
        listTipPrend = rmt.LoadListTip("");
        for (tipo tp : listTipPrend) {
            combo_tip.addItem(tp.getDescrip());
            comb_tipo1.addItem(tp.getDescrip());
        }
    }

    public void load_table(String dato) {
        datos = rp.label_table();
        Object[] data = new Object[11];
        listprod = rp.llenar_prod_vnta(dato);
        for (Producto pr : listprod) {
            try {
                data[0] = pr.getNum_prod();
                data[1] = pr.getCod_prod();
                data[2] = rmt.ObtNomMarca(pr.getCod_marca() + "");
                data[3] = rmt.ObtNomTip(pr.getCod_tip_prend() + "");
                data[4] = pr.getPrec_cmpra();
                data[5] = pr.getPrec_vnta();
                data[6] = pr.getStok();
                data[7] = pr.isIva();
                data[8] = pr.getDescripcion();
                data[9] = pr.getObserv();
                datos.addRow(data);
                table_prod.setModel(datos);
            } catch (Exception ex) {
                Logger.getLogger(vntana_productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void limpiar() {
        // num_prod.setText("");
        combo_marca.setSelectedItem("");
        combo_tip.setSelectedItem("");
        prec_cmpra.setText("");
        text_cant.setText("");
        chec_iva.setSelected(false);
        prec_vnta.setText("");
        text_descripcion.setText("");
        cbox_prov.setSelectedItem("");
        text_color.setText("");
        txtcod_prod.setText("");

    }

    public void pinta_text() {
        Validaciones.pinta_text(text_descripcion);
        Validaciones.pinta_text(text_cant);
        Validaciones.pinta_text(prec_cmpra);
        Validaciones.pinta_text(prec_vnta);
        Validaciones.pinta_Combo(combo_tip);
        Validaciones.pinta_Combo(combo_marca);
        Validaciones.pinta_text(text_color);
        Validaciones.pinta_text(txtcod_prod);
        Validaciones.pinta_text(txt_desc);
        limpiar();
    }

    public void bloquear_campos(boolean ok) {
        //  text_cod.setText(ok);
        combo_marca.setEnabled(ok);
        combo_tip.setEnabled(ok);
        prec_cmpra.setEnabled(ok);
        text_cant.setEnabled(ok);
        chec_iva.setEnabled(ok);
        prec_vnta.setEnabled(ok);
        text_descripcion.setEnabled(ok);
        text_color.setEnabled(ok);
        cbox_prov.setEnabled(ok);
        txt_desc.setEnabled(ok);
    }

    public void botones(boolean ok, boolean ok1) {
        btn_agregar.setEnabled(ok);
        btn_actualizar.setEnabled(ok1);
        btn_delete.setEnabled(ok1);
    }

    public boolean form_validado() {
        boolean ok = true;
        //validar requerido
        if (!Validaciones.esRequerido(text_descripcion)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_cant)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(prec_cmpra)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(prec_vnta)) {
            ok = false;
        }
        if (!Validaciones.es_Combo(combo_tip)) {
            ok = false;
        }
        if (!Validaciones.es_Combo(combo_marca)) {
            ok = false;
        }
        /* if (!Validaciones.es_Combo(cm_numero)) {
         ok = false;
         }*/
        if (!Validaciones.es_Combo(cbox_prov)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(text_color)) {
            ok = false;

        }
        if (!Validaciones.esRequerido(txtcod_prod)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(txt_desc)) {
            ok = false;
        }
        return ok;
    }

    //actualizar datos
    public void update_prod() {
        // if (form_validado()) {
        pr.setNum_prod(Integer.parseInt(num_prod.getText()));
        pr.setCod_prod(txtcod_prod.getText());
        pr.setCod_prove(rprov.ObtCodProv(cbox_prov.getSelectedItem().toString()));
        pr.setCod_marca(rmt.ObtCodMarca(combo_marca.getSelectedItem().toString()));
        pr.setCod_tip_prend(rmt.ObtCodTip(combo_tip.getSelectedItem().toString()));
        pr.setPrec_cmpra((Double.parseDouble(prec_cmpra.getText())));
        pr.setDescuento(Double.parseDouble(txt_desc.getText()));//Modificar
        pr.setPrec_vnta(Double.parseDouble(prec_vnta.getText()));
        //obetrner la cantidad   
        if (!actCant) {
            //   JOptionPane.showMessageDialog(this, "si ingresa:" + text_cant.getText() + rp.ObtCantProd(txtcod_prod.getText()));
            pr.setStok(Integer.parseInt(text_cant.getText()) + rp.ObtCantProd(txtcod_prod.getText()));//debo sumar la cantidad existente    
        } else {
            pr.setStok(Integer.parseInt(text_cant.getText()));
        }

        pr.setIva(chec_iva.isSelected());
        pr.setDescripcion(text_descripcion.getText());
        pr.setObserv(text_color.getText());
        rp.update(pr);
        bloquear_campos(true);
        // pinta_text();
        tabla_load(rp.busc_cod(""));
        botones(false, false);
        actCant = true;
        // }
    }

    public void subtotalWithIva() {
        Double precCompra = Double.parseDouble(prec_cmpra.getText());
        double iva = 0.0;
        if (chec_iva.isSelected()) {
            iva = Double.parseDouble(prec_cmpra.getText()) * rp.ObtValIva(1 + "") /*AQUI DEBE CONFIGURARSE EL IVA*/;
        }
        precCompra = precCompra + iva;
        // text_total.setText(precCompra + "");
    }
    ArrayList<producto_compra> listProd;

    //Traer el codigo del producto de venta
  /*  public int numProdVenta(String cod_prod) {
     int num = 0;

     if (!isRegProd(cod_prod).equals("")) {
     listProdVenta = rp.llenar_prod_vnta(rp.busc_cod(cod_prod));//buscar por el codigo del producto
     for (Producto pr : listProdVenta) {
     num = pr.getNum_prod();
     }
     } else {
     num = rp.codProductos();
     }
     return num;
     }*/

    /*  public void cargarCamposProd_ped(String cod_produ, String cant, int cod_prove) {

     listProd = rpc.llenar_prod(rpc.busc_cod_c(cod_produ));//busca el producto
     for (producto_compra pr : listProd) {
     num_prod.setText(numProdVenta(cod_produ) + ""); //no lo necesito
     txtcod_prod.setText(pr.getCod_prod());
     text_descripcion.setText(pr.getDescripcion());
     chec_iva.setSelected(pr.isIva());
     prec_cmpra.setText(pr.getPrec_cmpra() + "");
     text_cant.setText(cant);
     text_color.setText(pr.getObserv());
     txt_desc.setText(pr.getDescuento() + "");
     combo_marca.setSelectedItem(rmt.ObtNomMarca(pr.getCod_marca() + ""));
     combo_tip.setSelectedItem(rmt.ObtNomTipCloth(pr.getCod_tip_prend() + ""));
     cbox_prov.setSelectedItem(rprov.ObtNomProv(cod_prove));//carga el nombre del proveedor
     }
     }*/

    /*  public String isRegProd(String dato) {//revisa si existe el codiog en los productos
     String codProd = "";
     listprod = rp.llenar_prod_vnta(rp.busc_cod(dato));
     for (Producto pr : listprod) {
     try {
     codProd = pr.getCod_prod();
     } catch (Exception ex) {
     System.out.println(ex);
     }
     }
     return codProd;
     }*/
    //validar si edito o guardo el producto
 /*   public boolean esEdit() {
     boolean ok = true;
     if (txtcod_prod.getText().equals(isRegProd(txtcod_prod.getText()))) {
     ok = false;
     }
     return ok;
     }*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_all = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        text_cont = new javax.swing.JLabel();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        bnt_cancel = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_load = new javax.swing.JButton();
        btn_ivntario = new javax.swing.JButton();
        btn_prove = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        lbl_porcIva = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_prod = new javax.swing.JTable();
        pnel_inf1 = new javax.swing.JPanel();
        lbl_busc_todo = new javax.swing.JLabel();
        text_buscar = new javax.swing.JTextField();
        comb_tipo1 = new javax.swing.JComboBox();
        cmbo_marca = new javax.swing.JComboBox();
        lblprec_vnta1 = new javax.swing.JLabel();
        lblprec_vnta2 = new javax.swing.JLabel();
        pnel_inf = new javax.swing.JPanel();
        lblproveedor = new javax.swing.JLabel();
        text_descripcion = new javax.swing.JTextField();
        num_prod = new javax.swing.JTextField();
        lblstok = new javax.swing.JLabel();
        lbliva = new javax.swing.JLabel();
        text_cant = new javax.swing.JTextField();
        prec_vnta = new javax.swing.JTextField();
        chec_iva = new javax.swing.JCheckBox();
        combo_tip = new javax.swing.JComboBox();
        combo_marca = new javax.swing.JComboBox();
        lblmarca = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        lbldesc = new javax.swing.JLabel();
        text_color = new javax.swing.JTextField();
        lblcod = new javax.swing.JLabel();
        lblprec_cmpra = new javax.swing.JLabel();
        btn_crear_codigo = new javax.swing.JButton();
        txtcod_prod = new javax.swing.JTextField();
        prec_cmpra = new javax.swing.JTextField();
        codProd = new javax.swing.JLabel();
        lblprec_vnta = new javax.swing.JLabel();
        lblDescrip = new javax.swing.JLabel();
        cbox_prov = new javax.swing.JComboBox<>();
        txt_desc = new javax.swing.JTextField();
        btn_mrca = new javax.swing.JButton();
        btn_cat = new javax.swing.JButton();
        btn_iva = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Productos");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_all.setBackground(new java.awt.Color(204, 204, 204));
        panel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Seleccionar dando doble clic");
        panel_all.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, -1));

        jLabel13.setText("Cantidad:");
        panel_all.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 60, -1));

        text_cont.setText("0");
        panel_all.add(text_cont, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, 30, -1));

        getContentPane().add(panel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 990, 40));

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Productos");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(931, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, -1));

        jToolBar1.setBackground(new java.awt.Color(204, 204, 204));
        jToolBar1.setRollover(true);

        btn_new.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new.setText("Nuevo");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_new);

        btn_agregar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_agregar.setText("Guardar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_agregar);

        btn_actualizar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_actualizar);

        bnt_cancel.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        bnt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        bnt_cancel.setText("Limpiar");
        bnt_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_cancelActionPerformed(evt);
            }
        });
        jToolBar1.add(bnt_cancel);

        btn_delete.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.setText("Eliminar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_delete);

        btn_load.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sincronizar.png"))); // NOI18N
        btn_load.setText("Recargar");
        btn_load.setFocusable(false);
        btn_load.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loadActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_load);

        btn_ivntario.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_ivntario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos.png"))); // NOI18N
        btn_ivntario.setText("Inventario ");
        btn_ivntario.setFocusable(false);
        btn_ivntario.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_ivntario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ivntarioActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_ivntario);

        btn_prove.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_prove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedor.png"))); // NOI18N
        btn_prove.setText("Proveedores");
        btn_prove.setFocusable(false);
        btn_prove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_prove);
        jToolBar1.add(jSeparator2);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel2.setText("Porcentaje Iva:");
        jToolBar1.add(jLabel2);

        lbl_porcIva.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbl_porcIva.setText("0");
        jToolBar1.add(lbl_porcIva);

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1000, 40));

        table_prod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table_prod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_prodMouseClicked(evt);
            }
        });
        table_prod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_prodKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_prod);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 980, 190));

        pnel_inf1.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_busc_todo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbl_busc_todo.setText("Buscar:");
        pnel_inf1.add(lbl_busc_todo, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 13, -1, -1));

        text_buscar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_buscarActionPerformed(evt);
            }
        });
        text_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_buscarKeyReleased(evt);
            }
        });
        pnel_inf1.add(text_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(76, 11, 250, -1));

        comb_tipo1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        comb_tipo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comb_tipo1KeyReleased(evt);
            }
        });
        pnel_inf1.add(comb_tipo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 130, -1));

        cmbo_marca.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        cmbo_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmbo_marcaKeyReleased(evt);
            }
        });
        pnel_inf1.add(cmbo_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 150, -1));

        lblprec_vnta1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblprec_vnta1.setText("Tipo");
        pnel_inf1.add(lblprec_vnta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, 20));

        lblprec_vnta2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblprec_vnta2.setText("Marca:");
        pnel_inf1.add(lblprec_vnta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, 20));

        getContentPane().add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 760, 40));

        pnel_inf.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblproveedor.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblproveedor.setText("Proveedor:");
        pnel_inf.add(lblproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        text_descripcion.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_descripcionKeyReleased(evt);
            }
        });
        pnel_inf.add(text_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 540, 30));

        num_prod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        num_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        num_prod.setEnabled(false);
        pnel_inf.add(num_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 50, -1));

        lblstok.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblstok.setText("Stok:");
        pnel_inf.add(lblstok, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, 20));

        lbliva.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbliva.setText("Iva:");
        pnel_inf.add(lbliva, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, -1, 20));

        text_cant.setBackground(new java.awt.Color(204, 204, 204));
        text_cant.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cant.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cantKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                text_cantKeyTyped(evt);
            }
        });
        pnel_inf.add(text_cant, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 50, -1));

        prec_vnta.setBackground(new java.awt.Color(204, 204, 204));
        prec_vnta.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        prec_vnta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                prec_vntaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                prec_vntaKeyTyped(evt);
            }
        });
        pnel_inf.add(prec_vnta, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 100, 80, -1));

        chec_iva.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        chec_iva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                chec_ivaKeyReleased(evt);
            }
        });
        pnel_inf.add(chec_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 20, 20));

        combo_tip.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        combo_tip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipActionPerformed(evt);
            }
        });
        pnel_inf.add(combo_tip, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 200, -1));

        combo_marca.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        combo_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_marcaActionPerformed(evt);
            }
        });
        pnel_inf.add(combo_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 190, -1));

        lblmarca.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblmarca.setText("Marca:");
        pnel_inf.add(lblmarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, -1, 20));

        lblTipo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblTipo.setText("Tipo:");
        pnel_inf.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        lblColor.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblColor.setText("Observación:");
        pnel_inf.add(lblColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 20));

        lbldesc.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbldesc.setText("Descuento:");
        pnel_inf.add(lbldesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, -1, 20));

        text_color.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_color.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_colorKeyReleased(evt);
            }
        });
        pnel_inf.add(text_color, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 810, -1));

        lblcod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblcod.setText("Código:");
        lblcod.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblcodComponentHidden(evt);
            }
        });
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblprec_cmpra.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblprec_cmpra.setText("Precio Compra:");
        lblprec_cmpra.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblprec_cmpraComponentHidden(evt);
            }
        });
        pnel_inf.add(lblprec_cmpra, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 40, -1, 20));

        btn_crear_codigo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_crear_codigo.setText("Generar código");
        btn_crear_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear_codigoActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_crear_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 130, -1));

        txtcod_prod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        txtcod_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtcod_prod.setEnabled(false);
        pnel_inf.add(txtcod_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 60, -1));

        prec_cmpra.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        prec_cmpra.setText("0.0");
        prec_cmpra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prec_cmpraActionPerformed(evt);
            }
        });
        prec_cmpra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                prec_cmpraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                prec_cmpraKeyTyped(evt);
            }
        });
        pnel_inf.add(prec_cmpra, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 80, -1));

        codProd.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        codProd.setText("Código:");
        codProd.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                codProdComponentHidden(evt);
            }
        });
        pnel_inf.add(codProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 20));

        lblprec_vnta.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblprec_vnta.setText("Precio de Venta:");
        pnel_inf.add(lblprec_vnta, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, -1, 20));

        lblDescrip.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblDescrip.setText("Descripción:");
        pnel_inf.add(lblDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 30));

        cbox_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        cbox_prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbox_provActionPerformed(evt);
            }
        });
        pnel_inf.add(cbox_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 550, -1));

        txt_desc.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        txt_desc.setText("0.0");
        txt_desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_descActionPerformed(evt);
            }
        });
        txt_desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_descKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descKeyTyped(evt);
            }
        });
        pnel_inf.add(txt_desc, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 70, 80, -1));

        btn_mrca.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_mrca.setText("---");
        btn_mrca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mrcaActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_mrca, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 40, -1));

        btn_cat.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_cat.setText("---");
        btn_cat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_catActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_cat, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 40, -1));

        btn_iva.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_iva.setText("---");
        btn_iva.setFocusable(false);
        btn_iva.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_iva.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_iva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ivaActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 40, 20));

        getContentPane().add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 980, 170));

        jButton3.setBackground(new java.awt.Color(204, 51, 0));
        jButton3.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Todo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 270, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 153, 102));
        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 270, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed

        if (form_validado()) {
            // if (esEdit()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Registrar Producto",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                pr.setNum_prod(Integer.parseInt(num_prod.getText()));//aqui debe cambiar
                pr.setCod_prod(txtcod_prod.getText());
                pr.setCod_prove(rprov.ObtCodProv(cbox_prov.getSelectedItem().toString()));//proveedor
                pr.setCod_marca(rmt.ObtCodMarca(combo_marca.getSelectedItem().toString()));
                pr.setCod_tip_prend(rmt.ObtCodTip(combo_tip.getSelectedItem().toString()));
                pr.setPrec_cmpra((Double.parseDouble(prec_cmpra.getText())));
                pr.setDescuento(Double.parseDouble(txt_desc.getText()));//Modificar
                pr.setPrec_vnta(Double.parseDouble(prec_vnta.getText()));
                pr.setStok(Integer.parseInt(text_cant.getText()));//SE DEBE AUMENTAR LA NUEVA CANTIDAD               
                pr.setIva(chec_iva.isSelected());
                pr.setDescripcion(text_descripcion.getText());
                pr.setObserv(text_color.getText());
                rp.insert(pr);
                bloquear_campos(false);
                pinta_text();
                tabla_load(rp.busc_cod(""));
                botones(false, false);
            }
            //} /*else {
                /*
             int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar Producto",
             "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
             new Object[]{"Si    ", "   No",}, "Si");
             if (seleccion == JOptionPane.YES_OPTION) {
             actCant = false;
             update_prod();
             //JOptionPane.showMessageDialog(null, cod_vnta + "  " + cod_prod.getText());
             rped.edit_estado_prod_cmpra(cod_vnta, txtcod_prod.getText(), false);

             bloquear_campos(false);
             pinta_text();
             tabla_load(rp.busc_cod(""));
             botones(false, false);

             } else {
             System.out.println("producto no actualizado");
             }
             }*/
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void text_descripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_descripcionKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_descripcion)) {
            Validaciones.pinta_text(text_descripcion);
        }
    }//GEN-LAST:event_text_descripcionKeyReleased

    private void text_cantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cantKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_cant)) {
            Validaciones.pinta_text(text_cant);
        }

    }//GEN-LAST:event_text_cantKeyReleased

    private void prec_vntaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prec_vntaKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(prec_vnta)) {
            Validaciones.pinta_text(prec_vnta);
        }

    }//GEN-LAST:event_prec_vntaKeyReleased

    private void text_cantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cantKeyTyped
        // TODO add your handling code here:

        char car = evt.getKeyChar();

        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '.')) {
            evt.consume();
        }
        /* if (car == '.' && text_cant.getText().contains(".")) {
         evt.consume();
         }*/
    }//GEN-LAST:event_text_cantKeyTyped

    private void prec_vntaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prec_vntaKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '.')) {
            evt.consume();
        }
        if (car == '.' && text_cant.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_prec_vntaKeyTyped

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        cargarCombMarca("");
        cargarCombPrendas();
        cargarProveedores("");
        num_prod.setText(rp.codProductos() + "");
        bloquear_campos(true);
        pinta_text();
        botones(true, false);

    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar Producto",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                update_prod();
                pinta_text();

            } else {
                System.out.println("producto no actualizado");
            }
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            pr.setNum_prod(Integer.parseInt(num_prod.getText()));
            rp.Eliminar(pr);
            tabla_load("");
            botones(false, false);
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void table_prodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_prodMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pinta_text();
            num_prod.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 0).toString());
            String codProd = table_prod.getValueAt(table_prod.getSelectedRow(), 1).toString();
            txtcod_prod.setText(codProd);
            combo_marca.setSelectedItem(table_prod.getValueAt(table_prod.getSelectedRow(), 2).toString());
            combo_tip.setSelectedItem(table_prod.getValueAt(table_prod.getSelectedRow(), 3).toString());
            prec_cmpra.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 4).toString());
            prec_vnta.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 5).toString());
            text_cant.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 6).toString());
            chec_iva.setSelected(Boolean.parseBoolean(table_prod.getValueAt(table_prod.getSelectedRow(), 7).toString()));
            text_descripcion.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 8).toString());
            text_color.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 9).toString());
            cbox_prov.setSelectedItem(rprov.ObtNomProv(rp.cod_proveedor(codProd)));
            bloquear_campos(true);
            if (delet == true && update == true) {
                botones(false, true);
            } else {
                actBotones(update, delet);
            }
        }
    }//GEN-LAST:event_table_prodMouseClicked

    private void table_prodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_prodKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_table_prodKeyPressed

    private void bnt_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_cancelActionPerformed
        //cancelar
        pinta_text();
        bloquear_campos(false);
        botones(false, false);


    }//GEN-LAST:event_bnt_cancelActionPerformed

    private void text_colorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_colorKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_color)) {
            Validaciones.pinta_text(text_color);
        }
    }//GEN-LAST:event_text_colorKeyReleased

    private void btn_mrcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mrcaActionPerformed
        // TODO add your handling code here:

        vntana_marca dlg = new vntana_marca(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        // loadPermisos(ced);
        cargarCombMarca("");

    }//GEN-LAST:event_btn_mrcaActionPerformed

    private void btn_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_catActionPerformed
        // TODO add your handling code here:
        vntana_tipos dlg = new vntana_tipos(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        // loadPermisos(ced);
        //load produc
        cargarCombPrendas();


    }//GEN-LAST:event_btn_catActionPerformed

    private void lblcodComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblcodComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodComponentHidden

    private void codProdComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_codProdComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_codProdComponentHidden

    private void lblprec_cmpraComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblprec_cmpraComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblprec_cmpraComponentHidden

    private void btn_crear_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_codigoActionPerformed
        try {
            int numero = (int) (Math.random() * 89999 + 10000);
            txtcod_prod.setText(numero + "");
            if (Validaciones.esRequerido(txtcod_prod)) {
                Validaciones.pinta_text(txtcod_prod);
            }
        } catch (Exception err) {
            System.out.println(err);
        }

    }//GEN-LAST:event_btn_crear_codigoActionPerformed

    private void prec_cmpraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prec_cmpraKeyReleased
        // TODO add your handling code here:
        try {
            subtotalWithIva();
            if (Validaciones.esRequerido(prec_cmpra)) {
                Validaciones.pinta_text(prec_cmpra);
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_prec_cmpraKeyReleased

    private void chec_ivaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chec_ivaKeyReleased
        // TODO add your handling code here:
        try {
            subtotalWithIva();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_chec_ivaKeyReleased

    private void prec_cmpraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prec_cmpraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prec_cmpraActionPerformed

    private void text_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_buscarActionPerformed

    private void text_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscarKeyReleased
        // TODO add your handling code here: 
        tabla_load(rp.busc_cod(text_buscar.getText()));

    }//GEN-LAST:event_text_buscarKeyReleased

    private void btn_ivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ivaActionPerformed
        // TODO add your handling code here:
        vntana_editIva dlg = new vntana_editIva(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        loadPermisos(ced);
        lbl_porcIva.setText(rp.ObtValIva("1") + "");
    }//GEN-LAST:event_btn_ivaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            int dato = rmt.ObtCodMarca(cmbo_marca.getSelectedItem().toString());
            int dato1 = rmt.ObtCodTip(comb_tipo1.getSelectedItem().toString());
            tabla_load(rp.busc_marca_tipo(dato + "", dato1 + ""));
        } catch (Exception err) {
            System.out.println(err);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void combo_tipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(combo_tip);
    }//GEN-LAST:event_combo_tipActionPerformed

    private void combo_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_marcaActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(combo_marca);
    }//GEN-LAST:event_combo_marcaActionPerformed

    private void cbox_provActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbox_provActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(cbox_prov);
    }//GEN-LAST:event_cbox_provActionPerformed

    private void btn_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loadActionPerformed
        // TODO add your handling code here:
        tabla_load(rp.busc_cod(""));
        cargarCombMarca("");
        cargarCombPrendas();
        text_cont.setText(rp.countProd() + "");
        //bloquear_campos(false);

        cargarProveedores("");
        lbl_porcIva.setText(rp.ObtValIva("1") + "");


    }//GEN-LAST:event_btn_loadActionPerformed

    private void btn_ivntarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ivntarioActionPerformed
        // TODO add your handling code here:
        vntana_inventario dlg = new vntana_inventario(null, false);
      //  dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);


    }//GEN-LAST:event_btn_ivntarioActionPerformed

    private void comb_tipo1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comb_tipo1KeyReleased
        // TODO add your handling code here:
        int dato = rmt.ObtCodTip(comb_tipo1.getSelectedItem().toString());
        tabla_load(rp.busc_tip(dato));
    }//GEN-LAST:event_comb_tipo1KeyReleased

    private void cmbo_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbo_marcaKeyReleased
        // TODO add your handling code here:

        int dato = rmt.ObtCodMarca(cmbo_marca.getSelectedItem().toString());

        tabla_load(rp.busc_marca(dato));
    }//GEN-LAST:event_cmbo_marcaKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        tabla_load(rp.busc_cod(""));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveActionPerformed
        vntana_proveedores dlg = new vntana_proveedores(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        cargarProveedores("");
    }//GEN-LAST:event_btn_proveActionPerformed

    private void txt_descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_descActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descActionPerformed

    private void prec_cmpraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prec_cmpraKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '.')) {
            evt.consume();
        }
        if (car == '.' && text_cant.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_prec_cmpraKeyTyped

    private void txt_descKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '.')) {
            evt.consume();
        }
        if (car == '.' && text_cant.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_descKeyTyped

    private void txt_descKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(txt_desc)) {
            Validaciones.pinta_text(txt_desc);
        }
    }//GEN-LAST:event_txt_descKeyReleased

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
            java.util.logging.Logger.getLogger(vntana_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_productos dialog = new vntana_productos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bnt_cancel;
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_cat;
    private javax.swing.JButton btn_crear_codigo;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_iva;
    private javax.swing.JButton btn_ivntario;
    private javax.swing.JButton btn_load;
    private javax.swing.JButton btn_mrca;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton btn_prove;
    private javax.swing.JComboBox<String> cbox_prov;
    private javax.swing.JCheckBox chec_iva;
    private javax.swing.JComboBox cmbo_marca;
    private javax.swing.JLabel codProd;
    private javax.swing.JComboBox comb_tipo1;
    private javax.swing.JComboBox combo_marca;
    private javax.swing.JComboBox combo_tip;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblDescrip;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lbl_busc_todo;
    private javax.swing.JLabel lbl_porcIva;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lbldesc;
    private javax.swing.JLabel lbliva;
    private javax.swing.JLabel lblmarca;
    private javax.swing.JLabel lblprec_cmpra;
    private javax.swing.JLabel lblprec_vnta;
    private javax.swing.JLabel lblprec_vnta1;
    private javax.swing.JLabel lblprec_vnta2;
    private javax.swing.JLabel lblproveedor;
    private javax.swing.JLabel lblstok;
    private javax.swing.JTextField num_prod;
    private javax.swing.JPanel panel_all;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTextField prec_cmpra;
    private javax.swing.JTextField prec_vnta;
    private javax.swing.JTable table_prod;
    private javax.swing.JTextField text_buscar;
    private javax.swing.JTextField text_cant;
    private javax.swing.JTextField text_color;
    private javax.swing.JLabel text_cont;
    private javax.swing.JTextField text_descripcion;
    private javax.swing.JTextField txt_desc;
    private javax.swing.JTextField txtcod_prod;
    // End of variables declaration//GEN-END:variables
}
