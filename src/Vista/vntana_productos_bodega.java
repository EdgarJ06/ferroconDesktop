/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_marca_tip;
import Controlador.reg_pais;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_prod_compras;
import Modelo.Marca;
import Modelo.Permisos;
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
public class vntana_productos_bodega extends javax.swing.JDialog {

    /**
     * Creates new form vntana_productos_bodega
     */
    reg_pais rprov = new reg_pais();
    reg_marca_tip rmt = new reg_marca_tip();
    reg_prod_compras rp = new reg_prod_compras();
    producto_compra pr = new producto_compra();

    DefaultTableModel datos;
    ArrayList<producto_compra> listprod;

    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    ArrayList<Permisos> listPerm;
    ArrayList<Marca> listMarc;
    ArrayList<tipo> listTipPrend;
    boolean delet = false;
    boolean update = false;
    String ced;

    public vntana_productos_bodega(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(null);
       setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
        blockBotones(false);//desactivado todos los botones
        ced = menu_principal.ced_usu;
        loadPermisos(ced);

        try {
            tabla_load(rp.busc_cod_c(""));
            cargarCombMarca();
            cargarCombPrendas();
            text_cont.setText(rp.countProd() + "");
            bloquear_campos(false);

        } catch (Exception err) {
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
                System.out.println("Ingresa al eliminar");
                btn_delete.setEnabled(true);
                delet = true;
            }
        }
    }

    public void blockBotones(boolean ok) {
        btn_new.setEnabled(ok);
        btn_agregar.setEnabled(ok);
        btn_actualizar.setEnabled(ok);
        btn_delete.setEnabled(ok);

    }

    public boolean actBotones(boolean upda, boolean delet) {

        btn_actualizar.setEnabled(upda);//true
        btn_delete.setEnabled(delet);//true

        return true;
    }

    public void tabla_load(String dato) {
        load_table(dato);
        modelo_tabla();
    }

    public void modelo_tabla() {
        try {
            table_prod.getColumnModel().getColumn(0).setPreferredWidth(5);
            table_prod.getColumnModel().getColumn(1).setPreferredWidth(35);
            table_prod.getColumnModel().getColumn(2).setPreferredWidth(50);
            table_prod.getColumnModel().getColumn(3).setPreferredWidth(35);
            table_prod.getColumnModel().getColumn(4).setPreferredWidth(20);
            table_prod.getColumnModel().getColumn(5).setPreferredWidth(30);
            table_prod.getColumnModel().getColumn(6).setPreferredWidth(25);
            table_prod.getColumnModel().getColumn(7).setPreferredWidth(150);
            table_prod.getColumnModel().getColumn(8).setPreferredWidth(60);
            //table_prod.getColumnModel().getColumn(9).setPreferredWidth(40);

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
        }
    }

   

    public void cargarCombMarca() {
        combo_marca.removeAllItems();
        combo_marca.addItem("");
        cmbo_marca.removeAllItems();
        cmbo_marca.addItem("");
        listMarc = rmt.CargarListMarca("");
        for (Marca m : listMarc) {
            combo_marca.addItem(m.getNom_marca());
            cmbo_marca.addItem(m.getNom_marca());

        }
    }

    public void cargarCombPrendas() {
        combo_tip.removeAllItems();
        combo_tip.addItem("");
        comb_tipo_cloth.removeAllItems();
        comb_tipo_cloth.addItem("");
        listTipPrend = rmt.LoadListTip("");
        for (tipo tp : listTipPrend) {
            combo_tip.addItem(tp.getDescrip());
            comb_tipo_cloth.addItem(tp.getDescrip());
        }
    }

    public void load_table(String dato) {
        datos = rp.label_table_compra();
        Object[] data = new Object[10];
        listprod = rp.llenar_prod(dato);
        for (producto_compra pr : listprod) {
            try {
                data[0] = pr.getNum_prod();
                data[1] = pr.getCod_prod();
                data[2] = rmt.ObtNomMarca(pr.getCod_marca() + "");
                data[3] = rmt.ObtNomTip(pr.getCod_tip_prend() + "");
                data[4] = pr.getPrec_cmpra();
                data[5] = pr.getDescuento();
                data[6] = pr.isIva();
                data[7] = pr.getDescripcion();
                data[8] = pr.getObserv();
                datos.addRow(data);
                table_prod.setModel(datos);
            } catch (Exception ex) {
                Logger.getLogger(vntana_productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void rellenar_text(String cod) {
        listprod = rp.llenar_prod(cod);
        for (producto_compra pr : listprod) {
            num_prod.setText(pr.getCod_prod());
            combo_marca.setSelectedItem(rmt.ObtNomMarca(pr.getCod_marca() + ""));
            combo_tip.setSelectedItem(rmt.ObtNomTip(pr.getCod_tip_prend() + ""));
            prec_cmpra.setText(pr.getPrec_cmpra() + "");
            chec_iva.setSelected(pr.isIva());
            text_descripcion.setText(pr.getDescripcion());
        }
    }

    public void limpiar() {
        combo_marca.setSelectedItem("");
        combo_tip.setSelectedItem("");
        prec_cmpra.setText("");
        text_descripcion.setText("");
       
        text_color.setText("");
        cod_prod.setText("");
        chec_iva.setSelected(false);
        txt_desc.setText("0.0");
    }

    public void pinta_text() {
        Validaciones.pinta_text(text_descripcion);
        Validaciones.pinta_text(prec_cmpra);
        Validaciones.pinta_Combo(combo_tip);
        Validaciones.pinta_Combo(combo_marca);
        Validaciones.pinta_text(text_color);
        Validaciones.pinta_text(cod_prod);
        Validaciones.pinta_text(txt_desc);

        limpiar();
    }

    public void bloquear_campos(boolean ok) {
        combo_marca.setEnabled(ok);
        combo_tip.setEnabled(ok);
        prec_cmpra.setEnabled(ok);
        chec_iva.setEnabled(ok);
        text_descripcion.setEnabled(ok);
        text_color.setEnabled(ok);
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
        if (!Validaciones.esRequerido(cod_prod)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(prec_cmpra)) {
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
        if (!Validaciones.esRequerido(text_color)) {
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
        pr.setCod_prod(cod_prod.getText());
        pr.setCod_marca(rmt.ObtCodMarca(combo_marca.getSelectedItem().toString()));
        pr.setCod_tip_prend(rmt.ObtCodTip(combo_tip.getSelectedItem().toString()));
        pr.setPrec_cmpra((Double.parseDouble(prec_cmpra.getText())));
        pr.setDescuento(Double.parseDouble(txt_desc.getText()));
        pr.setIva(chec_iva.isSelected());
        pr.setDescripcion(text_descripcion.getText());
        pr.setObserv(text_color.getText());
        rp.update(pr);
        bloquear_campos(true);
        pinta_text();
        botones(false, false);
        tabla_load(rp.busc_cod_c(""));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnel_inf = new javax.swing.JPanel();
        text_descripcion = new javax.swing.JTextField();
        num_prod = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        chec_iva = new javax.swing.JCheckBox();
        combo_tip = new javax.swing.JComboBox();
        combo_marca = new javax.swing.JComboBox();
        lblmarca = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        text_color = new javax.swing.JTextField();
        lblcod = new javax.swing.JLabel();
        lblDesc = new javax.swing.JLabel();
        btn_crear_codigo = new javax.swing.JButton();
        cod_prod = new javax.swing.JTextField();
        prec_cmpra = new javax.swing.JTextField();
        codProd = new javax.swing.JLabel();
        lblDescrip = new javax.swing.JLabel();
        txt_desc = new javax.swing.JTextField();
        lblPrecCom1 = new javax.swing.JLabel();
        bnt_cat = new javax.swing.JButton();
        btn_marca = new javax.swing.JButton();
        btn_iva = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        btn_load = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        text_cont = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_prod = new javax.swing.JTable();
        pnel_inf1 = new javax.swing.JPanel();
        lblBuscTodo = new javax.swing.JLabel();
        text_busc = new javax.swing.JTextField();
        comb_tipo_cloth = new javax.swing.JComboBox();
        cmbo_marca = new javax.swing.JComboBox();
        btn_buscar = new javax.swing.JButton();
        lblDesc1 = new javax.swing.JLabel();
        lblDesc2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Productos");

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
                .addContainerGap(787, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        pnel_inf.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_descripcionKeyReleased(evt);
            }
        });
        pnel_inf.add(text_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 560, 30));

        num_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        num_prod.setEnabled(false);
        pnel_inf.add(num_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 50, -1));

        lblIva.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIva.setText("Iva:");
        pnel_inf.add(lblIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, -1, 20));

        chec_iva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                chec_ivaKeyReleased(evt);
            }
        });
        pnel_inf.add(chec_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 20, 20));

        combo_tip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipActionPerformed(evt);
            }
        });
        pnel_inf.add(combo_tip, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 220, -1));

        combo_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_marcaActionPerformed(evt);
            }
        });
        pnel_inf.add(combo_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 200, -1));

        lblmarca.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblmarca.setText("Marca:");
        pnel_inf.add(lblmarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, 20));

        lblTipo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTipo.setText("Tipo:");
        pnel_inf.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        lblColor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblColor.setText("Observación:");
        pnel_inf.add(lblColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        text_color.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_colorKeyReleased(evt);
            }
        });
        pnel_inf.add(text_color, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 730, -1));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Num:");
        lblcod.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblcodComponentHidden(evt);
            }
        });
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblDesc.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblDesc.setText("Descuento:");
        lblDesc.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblDescComponentHidden(evt);
            }
        });
        pnel_inf.add(lblDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 70, -1, 20));

        btn_crear_codigo.setText("Generar código");
        btn_crear_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear_codigoActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_crear_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 140, 20));

        cod_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cod_prod.setEnabled(false);
        pnel_inf.add(cod_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 70, -1));

        prec_cmpra.setText("0.0");
        prec_cmpra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                prec_cmpraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                prec_cmpraKeyTyped(evt);
            }
        });
        pnel_inf.add(prec_cmpra, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 80, -1));

        codProd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        codProd.setText("Código:");
        codProd.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                codProdComponentHidden(evt);
            }
        });
        pnel_inf.add(codProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 20));

        lblDescrip.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblDescrip.setText("Descripcion:");
        pnel_inf.add(lblDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        txt_desc.setText("0.0");
        txt_desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_descKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descKeyTyped(evt);
            }
        });
        pnel_inf.add(txt_desc, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 80, -1));

        lblPrecCom1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblPrecCom1.setText("Precio:");
        lblPrecCom1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblPrecCom1ComponentHidden(evt);
            }
        });
        pnel_inf.add(lblPrecCom1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, -1, 20));

        bnt_cat.setText("---");
        bnt_cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_catActionPerformed(evt);
            }
        });
        pnel_inf.add(bnt_cat, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        btn_marca.setText("---");
        btn_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_marcaActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 50, -1));

        btn_iva.setText("---");
        btn_iva.setFocusable(false);
        btn_iva.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_iva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ivaActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 50, -1));

        jToolBar1.setBackground(new java.awt.Color(204, 204, 204));
        jToolBar1.setRollover(true);

        btn_new.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new.setText("Nuevo");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_new);

        btn_agregar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_agregar.setText("Guardar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_agregar);

        btn_actualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_actualizar);

        btn_cancel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        btn_cancel.setText("Cancelar");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_cancel);

        btn_load.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        btn_delete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.setText("Borrar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_delete);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Dar doble clic para seleccionar");

        jLabel13.setText("Cantidad:");

        text_cont.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 430, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

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

        pnel_inf1.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblBuscTodo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblBuscTodo.setText("Buscar:");

        text_busc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_buscKeyReleased(evt);
            }
        });

        comb_tipo_cloth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comb_tipo_clothKeyReleased(evt);
            }
        });

        cmbo_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmbo_marcaKeyReleased(evt);
            }
        });

        btn_buscar.setBackground(new java.awt.Color(0, 153, 102));
        btn_buscar.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        lblDesc1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblDesc1.setText("Marca:");
        lblDesc1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblDesc1ComponentHidden(evt);
            }
        });

        lblDesc2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblDesc2.setText("Tipo:");
        lblDesc2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblDesc2ComponentHidden(evt);
            }
        });

        javax.swing.GroupLayout pnel_inf1Layout = new javax.swing.GroupLayout(pnel_inf1);
        pnel_inf1.setLayout(pnel_inf1Layout);
        pnel_inf1Layout.setHorizontalGroup(
            pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_inf1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscTodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_busc, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDesc1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbo_marca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDesc2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comb_tipo_cloth, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_buscar)
                .addGap(30, 30, 30))
        );
        pnel_inf1Layout.setVerticalGroup(
            pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_inf1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnel_inf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscTodo)
                    .addComponent(text_busc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comb_tipo_cloth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbo_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar)
                    .addComponent(lblDesc2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDesc1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnel_inf, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(pnel_inf1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnel_inf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_descripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_descripcionKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_descripcion)) {
            Validaciones.pinta_text(text_descripcion);
        }
    }//GEN-LAST:event_text_descripcionKeyReleased

    private void chec_ivaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chec_ivaKeyReleased

    }//GEN-LAST:event_chec_ivaKeyReleased

    private void text_colorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_colorKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_color)) {
            Validaciones.pinta_text(text_color);
        }
    }//GEN-LAST:event_text_colorKeyReleased

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        try {
            if (form_validado()) {
                pr.setNum_prod(Integer.parseInt(num_prod.getText()));
                pr.setCod_prod(cod_prod.getText());
                pr.setCod_marca(rmt.ObtCodMarca(combo_marca.getSelectedItem().toString()));
                pr.setCod_tip_prend(rmt.ObtCodTip(combo_tip.getSelectedItem().toString()));
                pr.setPrec_cmpra((Double.parseDouble(prec_cmpra.getText())));//double 
                pr.setDescuento(Double.parseDouble(txt_desc.getText()));
                pr.setIva(chec_iva.isSelected());
                pr.setDescripcion(text_descripcion.getText());              
                pr.setObserv(text_color.getText());
                rp.insert(pr);
                bloquear_campos(false);
                pinta_text();
                botones(false, false);
                tabla_load(rp.busc_cod_c(""));
            }
        } catch (Exception err) {
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar Producto",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                update_prod();

            } else {

                System.out.println("producto no actualizado");
            }

        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void lblcodComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblcodComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodComponentHidden

    private void lblDescComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblDescComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDescComponentHidden

    private void btn_crear_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_codigoActionPerformed
        try {
            int numero = (int) (Math.random() * 89999 + 10000);//aleatorio
            cod_prod.setText(numero + "");
            if (Validaciones.esRequerido(cod_prod)) {
                Validaciones.pinta_text(cod_prod);
            }
        } catch (Exception err) {
            System.out.println(err);

        }
    }//GEN-LAST:event_btn_crear_codigoActionPerformed

    private void prec_cmpraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prec_cmpraKeyReleased
        // TODO add your handling code here:

        if (Validaciones.esRequerido(prec_cmpra)) {
            Validaciones.pinta_text(prec_cmpra);
        }
    }//GEN-LAST:event_prec_cmpraKeyReleased

    private void codProdComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_codProdComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_codProdComponentHidden

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        cargarCombMarca();
        cargarCombPrendas();

        num_prod.setText(rp.codProductos() + "");
        bloquear_campos(true);
        pinta_text();
        botones(true, false);

    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        //cancelar
        pinta_text();
        bloquear_campos(false);
        botones(false, false);

    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Producto",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                pr.setCod_prod(cod_prod.getText());
                rp.Eliminar(pr);
                botones(false, false);
            } else {
                System.out.println("producto no Eliminado");
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void bnt_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_catActionPerformed
        // TODO add your handling code here:
        vntana_tipos dlg = new vntana_tipos(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
       // loadPermisos(ced);
        cargarCombPrendas();

    }//GEN-LAST:event_bnt_catActionPerformed

    private void btn_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_marcaActionPerformed
        // TODO add your handling code here:
        vntana_marca dlg = new vntana_marca(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
       // loadPermisos(ced);
        cargarCombMarca();
    }//GEN-LAST:event_btn_marcaActionPerformed

    private void table_prodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_prodMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pinta_text();
            num_prod.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 0).toString());
            cod_prod.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 1).toString());
            combo_marca.setSelectedItem(table_prod.getValueAt(table_prod.getSelectedRow(), 2).toString());
            combo_tip.setSelectedItem(table_prod.getValueAt(table_prod.getSelectedRow(), 3).toString());
            prec_cmpra.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 4).toString());
            txt_desc.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 5).toString());
            chec_iva.setSelected(Boolean.parseBoolean(table_prod.getValueAt(table_prod.getSelectedRow(), 6).toString()));
            text_descripcion.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 7).toString());           
            text_color.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 8).toString());
            bloquear_campos(true);
            if (delet == true && update == true) {
                botones(false, true);

            } else {
                actBotones(update, delet);
            }
        }
    }//GEN-LAST:event_table_prodMouseClicked

    private void table_prodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_prodKeyPressed

    }//GEN-LAST:event_table_prodKeyPressed

    private void text_buscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscKeyReleased

        tabla_load(rp.busc_cod_c(text_busc.getText()));

    }//GEN-LAST:event_text_buscKeyReleased

    private void lblPrecCom1ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblPrecCom1ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPrecCom1ComponentHidden

    private void btn_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loadActionPerformed
        // TODO add your handling code here:
        cargarCombMarca();
        cargarCombPrendas();
    }//GEN-LAST:event_btn_loadActionPerformed

    private void btn_ivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ivaActionPerformed
        // TODO add your handling code here:

        vntana_editIva dlg = new vntana_editIva(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        loadPermisos(ced);


    }//GEN-LAST:event_btn_ivaActionPerformed

    private void lblDesc1ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblDesc1ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDesc1ComponentHidden

    private void lblDesc2ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblDesc2ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDesc2ComponentHidden

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        // TODO add your handling code here:
        try {
            int dato = rmt.ObtCodMarca(cmbo_marca.getSelectedItem().toString());
            int dato1 = rmt.ObtCodTip(comb_tipo_cloth.getSelectedItem().toString());
            tabla_load(rp.busc_marca_tipo_c(dato + "", dato1 + ""));
        } catch (Exception err) {
            System.out.println(err);
        }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void comb_tipo_clothKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comb_tipo_clothKeyReleased
        // TODO add your handling code here:
        int dato = rmt.ObtCodTip(comb_tipo_cloth.getSelectedItem().toString());
        tabla_load(rp.busc_tip_c(dato));
    }//GEN-LAST:event_comb_tipo_clothKeyReleased

    private void cmbo_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbo_marcaKeyReleased
        // TODO add your handling code here:
        int dato = rmt.ObtCodMarca(cmbo_marca.getSelectedItem().toString());
        tabla_load(rp.busc_marca_c(dato));
    }//GEN-LAST:event_cmbo_marcaKeyReleased

    private void txt_descKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descKeyReleased
        // TODO add your handling code here:
       if (Validaciones.esRequerido(txt_desc)) {
            Validaciones.pinta_text(txt_desc);
        }


    }//GEN-LAST:event_txt_descKeyReleased

    private void combo_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_marcaActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(combo_marca);
    }//GEN-LAST:event_combo_marcaActionPerformed

    private void combo_tipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(combo_tip);
    }//GEN-LAST:event_combo_tipActionPerformed

    private void prec_cmpraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prec_cmpraKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '.')) {
            evt.consume();
        }
        if (car == '.' && prec_cmpra.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_prec_cmpraKeyTyped

    private void txt_descKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '.')) {
            evt.consume();
        }
        if (car == '.' && txt_desc.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_descKeyTyped

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
            java.util.logging.Logger.getLogger(vntana_productos_bodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_productos_bodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_productos_bodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_productos_bodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_productos_bodega dialog = new vntana_productos_bodega(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bnt_cat;
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_crear_codigo;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_iva;
    private javax.swing.JButton btn_load;
    private javax.swing.JButton btn_marca;
    private javax.swing.JButton btn_new;
    private javax.swing.JCheckBox chec_iva;
    private javax.swing.JComboBox cmbo_marca;
    private javax.swing.JLabel codProd;
    private javax.swing.JTextField cod_prod;
    private javax.swing.JComboBox comb_tipo_cloth;
    private javax.swing.JComboBox combo_marca;
    private javax.swing.JComboBox combo_tip;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblBuscTodo;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JLabel lblDesc1;
    private javax.swing.JLabel lblDesc2;
    private javax.swing.JLabel lblDescrip;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblPrecCom1;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblmarca;
    private javax.swing.JTextField num_prod;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTextField prec_cmpra;
    private javax.swing.JTable table_prod;
    private javax.swing.JTextField text_busc;
    private javax.swing.JTextField text_color;
    private javax.swing.JLabel text_cont;
    private javax.swing.JTextField text_descripcion;
    private javax.swing.JTextField txt_desc;
    // End of variables declaration//GEN-END:variables
}
