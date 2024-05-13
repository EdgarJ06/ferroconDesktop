/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_ajustes;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_productos;
import Modelo.Permisos;
import Modelo.Personal;
import Modelo.Producto;
import Modelo.Validaciones;
import static Vista.vntana_Venta.cod_usu;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class vntana_ajust_invent extends javax.swing.JDialog {

    reg_productos rp = new reg_productos();
    Producto pr = new Producto();
    DefaultTableModel datos;
    boolean delet = false;
    boolean update = false;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    ArrayList<Permisos> listPerm;
    String ced;
    String id_ajuste;
    String tipo;
    reg_ajustes rajus = new reg_ajustes();
    ArrayList<Personal> listuser;
    reg_personal ru = new reg_personal();
    ArrayList<Producto> listProd;

    /**
     * Creates new form vntana_ajust_invent
     */
    public vntana_ajust_invent(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());

        blockBotones(false);//desactivado todos los botones

        ced = menu_principal.ced_usu;
        load_user(ced);
        loadPermisos(ced);
        fill_table();
        //cargar tabla

        bloquear_campos(false);

    }

    public void load_user(String dato) {
        listuser = ru.user_active(ru.bus_user_ced(dato));
        for (Personal us : listuser) {
            textuser.setText(us.getNombre() + "  " + us.getApellido());
            id_user.setText(us.getCod_usua() + "");
            // ced_user = us.getCed();
            cod_usu = us.getCod_usua();
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

    public void fill_table() {
        try {
            table_ajuste.setModel(rajus.setFilas_ajustes(""));
            modelo_tabla();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void modelo_tabla() {
        table_ajuste.getColumnModel().getColumn(0).setPreferredWidth(30);
        table_ajuste.getColumnModel().getColumn(1).setPreferredWidth(200);
        table_ajuste.getColumnModel().getColumn(2).setPreferredWidth(100);
        table_ajuste.getColumnModel().getColumn(3).setPreferredWidth(200);
        table_ajuste.getColumnModel().getColumn(4).setPreferredWidth(100);
        table_ajuste.getTableHeader().setBackground(Color.decode("#666666"));
        table_ajuste.getTableHeader().setForeground(Color.WHITE);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        table_ajuste.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public void pinta_text() {
        Validaciones.pinta_text(text_cant);
        Validaciones.pinta_text(textuser);
        Validaciones.pinta_text(textmotivo);
        Validaciones.pinta_text(nom_prod);
        Validaciones.pinta_text(id_motivo);
        Validaciones.pinta_text(id_prod);
        limpiar();
    }

    public void limpiar() {
        // num_prod.setText("");   
        //textuser.setText("");
        text_cant.setText("");
        textmotivo.setText("");
        nom_prod.setText("");
        id_motivo.setText("");
        id_prod.setText("");

    }

    public void bloquear_campos(boolean ok) {
        //  text_cod.setText(ok);
        textuser.setEnabled(ok);
        textmotivo.setEnabled(ok);
        nom_prod.setEnabled(ok);
        text_cant.setEnabled(ok);
        id_motivo.setEnabled(ok);
        id_prod.setEnabled(ok);
    }

    public void botones(boolean ok, boolean ok1) {
        btn_agregar.setEnabled(ok);
        btn_actualizar.setEnabled(ok1);
        btn_delete.setEnabled(ok1);
    }

    public boolean form_validado() {
        boolean ok = true;
        //validar requerido
        if (!Validaciones.esRequerido(textuser)) {
            ok = false;
        }
        if (!Validaciones.esFlotante(text_cant)) {
            ok = false;
        }
        /* if (!Validaciones.esRequerido(textmotivo)) {
         ok = false;
         }
         if (!Validaciones.esRequerido(nom_prod)) {
         ok = false;
         }*/

        if (!Validaciones.esRequerido(id_prod)) {
            ok = false;

        }
        if (!Validaciones.esRequerido(id_motivo)) {
            ok = false;
        }

        return ok;
    }

    public void cargarCamposProd(String dato) {
        listProd = rp.llenar_prod_vnta(dato);
        for (Producto pr : listProd) {
            id_prod.setText(pr.getCod_prod());
            nom_prod.setText(pr.getDescripcion());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_all = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnel_inf1 = new javax.swing.JPanel();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnel_inf = new javax.swing.JPanel();
        lblproveedor = new javax.swing.JLabel();
        text_cant = new javax.swing.JTextField();
        id_user = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        lblcod = new javax.swing.JLabel();
        lblDescrip = new javax.swing.JLabel();
        btn_mrca = new javax.swing.JButton();
        btn_cat = new javax.swing.JButton();
        id_prod = new javax.swing.JTextField();
        textuser = new javax.swing.JTextField();
        id_motivo = new javax.swing.JTextField();
        textmotivo = new javax.swing.JTextField();
        nom_prod = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        bnt_cancel = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_load = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_ajuste = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lbl_busc_todo = new javax.swing.JLabel();
        text_buscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ajuste de Inventario");
        setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_all.setBackground(new java.awt.Color(204, 204, 204));
        panel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Seleccionar dando doble clic");
        panel_all.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 160, 20));

        pnel_inf1.setBackground(new java.awt.Color(204, 204, 204));
        pnel_inf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_all.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 690, 40));

        getContentPane().add(panel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 660, 40));

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ajuste de Inventario");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(523, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 30));

        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblproveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblproveedor.setText("Producto:");
        pnel_inf.add(lblproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, 20));

        text_cant.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cantKeyReleased(evt);
            }
        });
        pnel_inf.add(text_cant, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 90, -1));

        id_user.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        id_user.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        id_user.setEnabled(false);
        pnel_inf.add(id_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 90, -1));

        lblTipo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTipo.setText("Motivo:");
        pnel_inf.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, 20));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Código Usuario:");
        lblcod.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblcodComponentHidden(evt);
            }
        });
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        lblDescrip.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblDescrip.setText("Cantidad:");
        pnel_inf.add(lblDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, 20));

        btn_mrca.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_mrca.setText("---");
        btn_mrca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mrcaActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_mrca, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 40, -1));

        btn_cat.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_cat.setText("---");
        btn_cat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_catActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_cat, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 40, -1));

        id_prod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        pnel_inf.add(id_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 90, -1));

        textuser.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        textuser.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pnel_inf.add(textuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 320, -1));

        id_motivo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        pnel_inf.add(id_motivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 90, -1));

        textmotivo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        textmotivo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pnel_inf.add(textmotivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 270, -1));

        nom_prod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nom_prod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pnel_inf.add(nom_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 270, -1));

        getContentPane().add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 640, 140));

        jToolBar1.setBackground(new java.awt.Color(204, 0, 0));
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

        bnt_cancel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        bnt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        bnt_cancel.setText("Limpiar");
        bnt_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_cancelActionPerformed(evt);
            }
        });
        jToolBar1.add(bnt_cancel);

        btn_delete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.setText("Eliminar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_delete);

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
        jToolBar1.add(jSeparator3);

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 660, 40));

        table_ajuste.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        table_ajuste.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table_ajuste.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_ajusteMouseClicked(evt);
            }
        });
        table_ajuste.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_ajusteKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_ajuste);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 640, 230));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_busc_todo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbl_busc_todo.setText("Buscar:");

        text_buscar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
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

        jButton1.setBackground(new java.awt.Color(0, 153, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lupa.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 51, 0));
        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Todo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_busc_todo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_busc_todo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 640, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_ajusteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_ajusteMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pinta_text();
            id_ajuste = table_ajuste.getValueAt(table_ajuste.getSelectedRow(), 0).toString();
            id_motivo.setText(table_ajuste.getValueAt(table_ajuste.getSelectedRow(), 1).toString());
            id_prod.setText(table_ajuste.getValueAt(table_ajuste.getSelectedRow(), 2).toString());
            id_user.setText(table_ajuste.getValueAt(table_ajuste.getSelectedRow(), 3).toString());
            text_cant.setText(table_ajuste.getValueAt(table_ajuste.getSelectedRow(), 4).toString());

            bloquear_campos(true);
            // botones(false, true);
            if (delet == true && update == true) {
                botones(false, true);
            } else {
                actBotones(update, delet);
            }
        }
    }//GEN-LAST:event_table_ajusteMouseClicked

    private void table_ajusteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_ajusteKeyPressed
        // TODO add your handling code here:
        /*text_cod.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 0).toString());
         combo_marca.setSelectedItem(table_prod.getValueAt(table_prod.getSelectedRow(), 1).toString());
         combo_tip.setSelectedItem(table_prod.getValueAt(table_prod.getSelectedRow(), 2).toString());
         prec_uni.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 3).toString());
         text_cant.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 4).toString());
         chec_iva.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 5).toString());
         prec_vnta.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 6).toString());
         text_desc.setText(table_prod.getValueAt(table_prod.getSelectedRow(), 7).toString());*/
    }//GEN-LAST:event_table_ajusteKeyPressed

    private void text_cantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cantKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_cant)) {
            Validaciones.pinta_text(text_cant);
        }
    }//GEN-LAST:event_text_cantKeyReleased

    private void lblcodComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblcodComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodComponentHidden

    private void btn_mrcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mrcaActionPerformed
        // TODO add your handling code here:
        vntana_ver_prod dlg = new vntana_ver_prod(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        // loadPermisos(ced);
        String cod = vntana_ver_prod.codigo;
        cargarCamposProd(rp.busc_cod(cod));


    }//GEN-LAST:event_btn_mrcaActionPerformed

    private void btn_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_catActionPerformed
        // TODO add your handling code here:
        vntana_ver_motivos dlg = new vntana_ver_motivos(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);

        id_motivo.setText(vntana_ver_motivos.codigo);
        textmotivo.setText(vntana_ver_motivos.motivo);

        tipo = vntana_ver_motivos.tipo;

        // loadPermisos(ced);
        //load produc

    }//GEN-LAST:event_btn_catActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:

        //  id_user.setText(rp.codProductos() + "");
        bloquear_campos(true);
        pinta_text();
        botones(true, false);

    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed

        if (form_validado()) {

            int seleccion = JOptionPane.showOptionDialog(null, "Desea Registrar Producto",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {

                rajus.agregarAjuste(Integer.parseInt(id_motivo.getText()), id_prod.getText(),
                        Integer.parseInt(id_user.getText()), Integer.parseInt(text_cant.getText()));

                // si el tipo es salida   caso contrario aumenta el estok
                rp.prod_stok(id_prod.getText(), Integer.parseInt(text_cant.getText()));

                if (tipo.equals("Salida")) {
                    rp.descont_stok();//descontar productos
                } else if (tipo.equals("Entrada")) {
                    rp.aumentar_stok();
                }

                bloquear_campos(false);
                pinta_text();
                fill_table();
                botones(false, false);
            }

        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar Ajuste",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {

                rajus.editarAjuste(Integer.parseInt(id_ajuste), Integer.parseInt(id_motivo.getText()), id_prod.getText(),
                        Integer.parseInt(id_user.getText()), Integer.parseInt(text_cant.getText()));
                rp.prod_stok(id_prod.getText(), Integer.parseInt(text_cant.getText()));
                if (tipo.equals("Salida")) {
                    rp.descont_stok();//descontar productos
                } else if (tipo.equals("Entrada")) {
                    rp.aumentar_stok();
                }

                pinta_text();
                fill_table();
            } else {
                System.out.println("datos no actualizados");
            }

        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void bnt_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_cancelActionPerformed
        //cancelar
        pinta_text();
        bloquear_campos(false);
        botones(false, false);

    }//GEN-LAST:event_bnt_cancelActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            rajus.EliminarAjuste(Integer.parseInt(id_ajuste));
            fill_table();
            botones(false, false);
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loadActionPerformed
        // TODO add your handling code here:
        fill_table();


    }//GEN-LAST:event_btn_loadActionPerformed

    private void text_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_buscarActionPerformed

    private void text_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscarKeyReleased
        // TODO add your handling code here:

        table_ajuste.setModel(rajus.setFilas_ajustes(text_buscar.getText()));
        modelo_tabla();
    }//GEN-LAST:event_text_buscarKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        fill_table();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_ajust_invent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_ajust_invent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_ajust_invent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_ajust_invent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_ajust_invent dialog = new vntana_ajust_invent(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_load;
    private javax.swing.JButton btn_mrca;
    private javax.swing.JButton btn_new;
    private javax.swing.JTextField id_motivo;
    private javax.swing.JTextField id_prod;
    private javax.swing.JTextField id_user;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblDescrip;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lbl_busc_todo;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblproveedor;
    private javax.swing.JTextField nom_prod;
    private javax.swing.JPanel panel_all;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable table_ajuste;
    private javax.swing.JTextField text_buscar;
    private javax.swing.JTextField text_cant;
    private javax.swing.JTextField textmotivo;
    private javax.swing.JTextField textuser;
    // End of variables declaration//GEN-END:variables
}
