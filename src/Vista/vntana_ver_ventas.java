/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_clientes;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_vntas;
import Modelo.Permisos;
import Modelo.Validaciones;
import Modelo.Ventas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class vntana_ver_ventas extends javax.swing.JDialog {

    /**
     * Creates new form vnatana_caja
     */
    reg_vntas rv = new reg_vntas();
    reg_clientes rcl = new reg_clientes();
    ArrayList<Ventas> listVntas;
    DefaultTableModel campos;
    String ced;
    DecimalFormat fd = new DecimalFormat("0.00");

    public vntana_ver_ventas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);//Centrar ventana
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());

        text_fec.setText(Validaciones.fechaActual());
        text_fec2.setText(Validaciones.ParseFechaSql(text_fec.getText()));
        ced = menu_principal.ced_usu;
        loadPermisos(ced);
        //bloquearColumTable(rv.show_vnta_date(Validaciones.fechaActualSql(), Validaciones.fechaActualSql()));
        bloquearColumTable(rv.show_vnta_date(txtFec1.getText(), txtFec2.getText()));
        countVntas();
    }

    ArrayList<Permisos> listPerm;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {

            if (per.isPer_regist()) {
                // btn_nuevo.setEnabled(true);
            }
            if (per.isPer_udate()) {
                /* btn_actualizar.setEnabled(true);
                 update = true;*/
            }
            if (per.isPer_delete()) {
                /* System.out.println("Ingresa al eliminar");
                 btn_delete.setEnabled(true);
                 delet = true;*/
            }
        }
    }

    public void bloquearColumTable(String sql) {//edicion de la tabla
        try {
            loadVntas(sql);
            modelo_tabla();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void modelo_tabla() {
        tblvntasC.getColumnModel().getColumn(0).setPreferredWidth(1);
        tblvntasC.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblvntasC.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblvntasC.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblvntasC.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblvntasC.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblvntasC.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblvntasC.getColumnModel().getColumn(7).setPreferredWidth(20);
        tblvntasC.getTableHeader().setBackground(Color.decode("#666666"));
        tblvntasC.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblvntasC.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public void loadVntas(String sql) {
        campos = rv.load_label_caja();//se manda a llamar el header de la tabla
        Object[] data = new Object[8];//se crea los objetos que se almacenaran en la tabla
        listVntas = rv.load_vntas_caja(sql);
        //listVntas = rv.load_vntas_caja(rv.show_vnta_name(sql));
        for (Ventas v : listVntas) {
            data[0] = v.getCod_vnta();
            data[1] = rcl.load_nom_cli(v.getCod_cli());
            data[2] = rpersonal.load_nom_user(v.getCod_usu());
            data[3] = v.getFecha();
            data[4] = v.getSubtotal();
            data[5] = fd.format(v.getIva_vntas()).replace(",", ".");
            data[6] = fd.format(v.getDesc()).replace(",", ".");
            data[7] = fd.format(v.getTotal()).replace(",", ".");
            campos.addRow(data);
            tblvntasC.setModel(campos);

        }
    }

    public void loadVntasName(String sql) {
        campos = rv.load_label_caja();//se manda a llamar el header de la tabla
        Object[] data = new Object[8];//se crea los objetos que se almacenaran en la tabla
        //listVntas = rv.load_vntas_caja(sql);
        listVntas = rv.load_vntas_caja(rv.show_vnta_name(sql));
        for (Ventas v : listVntas) {
            data[0] = v.getCod_vnta();
            data[1] = rcl.load_nom_cli(v.getCod_cli());
            data[2] = rpersonal.load_nom_user(v.getCod_usu());
            data[3] = v.getFecha();
            data[4] = v.getSubtotal();
            data[5] = fd.format(v.getIva_vntas()).replace(",", ".");
            data[6] = fd.format(v.getDesc()).replace(",", ".");
            data[7] = fd.format(v.getTotal()).replace(",", ".");
            campos.addRow(data);
            tblvntasC.setModel(campos);

        }
    }
    
    public void countVntas() {
        try {
            int cont = 0;
            double subt = 0, iva = 0, total = 0;
            for (int i = 0; i < tblvntasC.getRowCount(); i++) {
                cont++;
                subt = subt + Double.parseDouble(tblvntasC.getValueAt(i, 4).toString());
                iva = iva + Double.parseDouble(tblvntasC.getValueAt(i, 5).toString());
                total = total + Double.parseDouble(tblvntasC.getValueAt(i, 7).toString());
            }
            txtSubt.setText(fd.format(subt).replace(",", "."));
            txtIva.setText(fd.format(iva).replace(",", "."));
            txtTot.setText(fd.format(total).replace(",", "."));
            lblCount.setText(cont + "");
        } catch (Exception err) {
            System.out.println("");
            // JOptionPane.showMessageDialog(this,"No existen datos");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnel_cab2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtSubt = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        txtTot = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtFec1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtFec2 = new javax.swing.JTextField();
        btn_busc = new javax.swing.JButton();
        btn_all = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lblCount = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        text_fec2 = new javax.swing.JLabel();
        text_busc_cli = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        text_fec = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblvntasC = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_impr = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventas");

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
                .addContainerGap(828, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        pnel_cab2.setBackground(new java.awt.Color(204, 204, 204));
        pnel_cab2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_cab2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setText("TOTAL IVA:");
        pnel_cab2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, 20));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("SUBTOTAL VENTAS:");
        pnel_cab2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        txtSubt.setBackground(new java.awt.Color(255, 255, 102));
        txtSubt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubt.setEnabled(false);
        pnel_cab2.add(txtSubt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 80, -1));

        txtIva.setBackground(new java.awt.Color(255, 255, 102));
        txtIva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIva.setEnabled(false);
        pnel_cab2.add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 80, -1));

        txtTot.setEditable(false);
        txtTot.setBackground(new java.awt.Color(255, 255, 102));
        txtTot.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTot.setEnabled(false);
        pnel_cab2.add(txtTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 80, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("TOTAL VENTAS:");
        pnel_cab2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 90, 20));

        jPanel1.setBackground(new java.awt.Color(234, 234, 234));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Fecha Inicio:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, 20));

        txtFec1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFec1KeyTyped(evt);
            }
        });
        jPanel1.add(txtFec1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 100, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Fecha Fin:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, 20));

        txtFec2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFec2KeyTyped(evt);
            }
        });
        jPanel1.add(txtFec2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 100, -1));

        btn_busc.setBackground(new java.awt.Color(0, 153, 204));
        btn_busc.setForeground(new java.awt.Color(255, 255, 255));
        btn_busc.setText("Buscar");
        btn_busc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscActionPerformed(evt);
            }
        });
        jPanel1.add(btn_busc, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, -1, -1));

        btn_all.setBackground(new java.awt.Color(204, 51, 0));
        btn_all.setForeground(new java.awt.Color(255, 255, 255));
        btn_all.setText("Todo");
        btn_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_allActionPerformed(evt);
            }
        });
        jPanel1.add(btn_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Cantidad de Ventas:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, 20));

        lblCount.setText("0");
        jPanel1.add(lblCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 20, 20));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Fecha Actual:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        text_fec2.setText("--");
        jPanel1.add(text_fec2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 90, 20));

        text_busc_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_busc_cliKeyReleased(evt);
            }
        });
        jPanel1.add(text_busc_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 520, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("BUSQUEDA DE VENTAS POR NOMBRE :");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 20));

        text_fec.setText("--");
        jPanel1.add(text_fec, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 90, 20));

        tblvntasC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblvntasC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblvntasCMouseClicked(evt);
            }
        });
        tblvntasC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblvntasCKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblvntasC);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Doble clic para seleccionar datos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_impr.setBackground(new java.awt.Color(0, 153, 102));
        btn_impr.setForeground(new java.awt.Color(255, 255, 255));
        btn_impr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imprimir.png"))); // NOI18N
        btn_impr.setText("Imprimir");
        btn_impr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_impr)
                .addGap(67, 67, 67)
                .addComponent(pnel_cab2, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnel_cab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_impr, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscActionPerformed

        try {
            loadVntas(rv.show_vnta_date(Validaciones.ParseFechaSql(txtFec1.getText()), (Validaciones.ParseFechaSql(txtFec2.getText()))));
            //loadVntas(rv.show_vnta_date(txtFec1.getText(), (txtFec2.getText())));
            modelo_tabla();
            countVntas();

        } catch (Exception err) {
            System.out.println(err);
            JOptionPane.showMessageDialog(this, "Error no existen datos");
        }


    }//GEN-LAST:event_btn_buscActionPerformed

    private void tblvntasCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblvntasCKeyPressed
        // TODO add your handling code here:


    }//GEN-LAST:event_tblvntasCKeyPressed

    private void tblvntasCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvntasCMouseClicked
        // TODO add your handling code here:
        //verificar si se quiere imprimir la factura
        if (evt.getClickCount() == 2) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Imprimir Factura",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                String num = (tblvntasC.getValueAt(tblvntasC.getSelectedRow(), 0).toString());

                JOptionPane.showMessageDialog(this, "Realizando operación.......Espere un momento");
                try {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("cod_vntas", num);
                    System.out.println(num);
                    Reportes_g.generarReporte("rep_vntas", params);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }//GEN-LAST:event_tblvntasCMouseClicked

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed
        // TODO add your handling code here:
        try {
            // if (!lblCount.getText().equals("0")) {
            bloquearColumTable(rv.show_all_vntas());
            countVntas();
            /* } else {
             JOptionPane.showMessageDialog(this, "No existen datos");
             }*/
        } catch (Exception err) {
            System.out.println(err);
        }

    }//GEN-LAST:event_btn_allActionPerformed

    private void btn_imprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprActionPerformed
        try {
            if (!lblCount.getText().equals("0")) {
                Date date1;// = Date.valueOf(txtFec1.getText());
                Date date2;//= Date.valueOf(txtFec2.getText());
                if (txtFec1.getText().equals("") || txtFec2.getText().equals("")) {
                    date1 = Date.valueOf("1000-01-01");
                    date2 = Date.valueOf("4000-01-01");
                } else {
                    date1 = Date.valueOf(txtFec1.getText());
                    date2 = Date.valueOf(txtFec2.getText());
                }
                JOptionPane.showMessageDialog(this, "Realizando operación.......Espere un momento");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("date1", date1);
                params.put("date2", date2);
                Reportes_g.generarReporte("rp_vntas_realz", params);
            } else {
                JOptionPane.showMessageDialog(this, "No existen datos");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Existe un error");
            System.out.println(ex);
        }

    }//GEN-LAST:event_btn_imprActionPerformed

    private void txtFec1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFec1KeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();

        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '-')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFec1KeyTyped

    private void txtFec2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFec2KeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '-')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFec2KeyTyped

    //BUSCA LAS VENTAS REALIZADAS A CADA CLIENTE
    private void text_busc_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_busc_cliKeyReleased
        // TODO add your handling code here:
        bloquearColumTable(text_busc_cli.getText());
    }//GEN-LAST:event_text_busc_cliKeyReleased

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
            java.util.logging.Logger.getLogger(vntana_ver_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_ver_ventas dialog = new vntana_ver_ventas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_all;
    private javax.swing.JButton btn_busc;
    private javax.swing.JButton btn_impr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JPanel pnel_cab2;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable tblvntasC;
    private javax.swing.JTextField text_busc_cli;
    private javax.swing.JLabel text_fec;
    private javax.swing.JLabel text_fec2;
    private javax.swing.JTextField txtFec1;
    private javax.swing.JTextField txtFec2;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtSubt;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
}
