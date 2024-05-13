/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_caja;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Modelo.Permisos;
import Modelo.Validaciones;
import Modelo.caja;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Edgar
 */
public class vntana_caja extends javax.swing.JDialog {

    /**
     * Creates new form vntana_caja
     */
    reg_caja rc = new reg_caja();
    caja c = new caja();
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    String ced;

    public vntana_caja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());

        ced = menu_principal.ced_usu;
        loadPermisos(ced);
        bloquearCampos(false);
        fill_table();
    }

    ArrayList<Permisos> listPerm;

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_regist()) {
                //btn_new.setEnabled(true);
            }
            if (per.isPer_udate()) {
                /* btn_actualizar.setEnabled(true);
                 update = true;*/
            }
            if (per.isPer_delete()) {
                System.out.println("Ingresa al eliminar");
                /*  btn_delete.setEnabled(true);
                 delet = true;*/
            }
        }
    }

    public void fill_table() {
        try {
            tbl.setModel(rc.setFilas_caja(""));
            modelo_tabla();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void modelo_tabla() {
        tbl.getColumnModel().getColumn(0).setPreferredWidth(1);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(40);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl.getTableHeader().setBackground(Color.decode("#666666"));
        tbl.getTableHeader().setForeground(Color.white);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tbl.getColumnModel().getColumn(0).setCellRenderer(tcr);

    }

    public void savecaja() {
        c.setNum_caja(Integer.parseInt(txtnumcaja.getText()));
        c.setDescrip(txtDescrip.getText());
        rc.insertCaja(c);

    }
    int id_caja = 0;

    public void cod_id(int id) {
        id_caja = id;
    }

    public void update_caja() {
        c.setId_caja(id_caja);
        c.setNum_caja(Integer.parseInt(txtnumcaja.getText()));
        c.setDescrip(txtDescrip.getText());
        rc.updateCaja(c);

    }

    public void pintar_campos() {
        Validaciones.pinta_text(txtnumcaja);
        Validaciones.pinta_text(txtDescrip);

    }

    public void bloquearCampos(boolean ok) {
        txtnumcaja.setEditable(ok);
        txtDescrip.setEditable(ok);

    }

    public boolean form_validado() {
        boolean ok = true;
        if (!Validaciones.esFlotante(txtnumcaja)) {
            ok = false;
        }
        if (!Validaciones.esRequerido(txtDescrip)) {
            ok = false;
        }

        return ok;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtnumcaja = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDescrip = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tbar = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Caja");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Caja");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(338, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 30));

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 350, 150));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Num:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        txtnumcaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnumcajaKeyReleased(evt);
            }
        });
        jPanel1.add(txtnumcaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 90, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Descrición:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        txtDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripKeyReleased(evt);
            }
        });
        jPanel1.add(txtDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 250, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 350, 70));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Dar doble clic para seleccionar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 370, -1));

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        btn_new.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new.setText("Nuevo");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        tbar.add(btn_new);

        btn_guardar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        tbar.add(btn_guardar);

        btn_actualizar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_actualizar.setText("Editar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        tbar.add(btn_actualizar);

        btn_delete.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.setText("Eliminar");
        btn_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_delete.setFocusable(false);
        btn_delete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        tbar.add(btn_delete);

        getContentPane().add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 370, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            savecaja();
            fill_table();
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            update_caja();
            fill_table();

        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        // cargar datos
        if (evt.getClickCount() == 2) {

            String id = (tbl.getValueAt(tbl.getSelectedRow(), 0).toString());
            txtnumcaja.setText(tbl.getValueAt(tbl.getSelectedRow(), 1).toString());
            txtDescrip.setText(tbl.getValueAt(tbl.getSelectedRow(), 2).toString());

            //load codigo
            cod_id(Integer.parseInt(id));
            bloquearCampos(true);

        }
    }//GEN-LAST:event_tblMouseClicked

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        pintar_campos();
        bloquearCampos(true);
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            c.setId_caja(id_caja);
            rc.Eliminar(c);
            fill_table();
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txtnumcajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnumcajaKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esFlotante(txtnumcaja)) {
            Validaciones.pinta_text(txtnumcaja);
        }
    }//GEN-LAST:event_txtnumcajaKeyReleased

    private void txtDescripKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripKeyReleased
        // TODO add your handling code here:

        if (Validaciones.esRequerido(txtDescrip)) {
            Validaciones.pinta_text(txtDescrip);
        }
    }//GEN-LAST:event_txtDescripKeyReleased

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
            java.util.logging.Logger.getLogger(vntana_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_caja dialog = new vntana_caja(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_new;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtDescrip;
    private javax.swing.JTextField txtnumcaja;
    // End of variables declaration//GEN-END:variables
}
