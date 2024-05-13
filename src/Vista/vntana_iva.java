/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_productos;
import Modelo.Permisos;
import Modelo.iva;
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
public class vntana_iva extends javax.swing.JDialog {

    /**
     * Creates new form vntana_iva
     */
    reg_productos rp = new reg_productos();
    String ced;

    public vntana_iva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
        try {
            bloqBotones(false);
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            load_table(rp.busc_cod_iva(""));

            load_table(rp.busc_cod_iva(""));
            modelo_tabla();

        } catch (Exception err) {
            System.out.println(err);
        }

    }

    public double iva(double iva) {
        double iv;
        iv = iva / 100;
        return iv;
    }
    ArrayList<Permisos> listPerm;  
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    DefaultTableModel datos;
    ArrayList<iva> listIva;
    boolean delet = false;
    boolean update = false;

   

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_regist()) {
                bntnuevo.setEnabled(true);
            }
            if (per.isPer_udate()) {
                btnAct.setEnabled(true);
                update = true;
            }
            if (per.isPer_delete()) {
                System.out.println("Ingresa al eliminar");
                //btn_delete.setEnabled(true);
                delet = true;
            }
        }
    }

    public void modelo_tabla() {
        tbliva.getColumnModel().getColumn(0).setPreferredWidth(5);
        tbliva.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbliva.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbliva.getTableHeader().setBackground(Color.decode("#666666"));
        tbliva.getTableHeader().setForeground(Color.white);

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tbliva.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public void load_table(String dato) {
        datos = rp.label_table_iva();
        Object[] data = new Object[3];
        listIva = rp.load_iva_tbl(dato);
        for (iva pr : listIva) {
            try {
                data[0] = pr.getCod_iva();
                data[1] = pr.getVal_iva();
                data[2] = pr.isEstado();

                datos.addRow(data);
                tbliva.setModel(datos);
            } catch (Exception ex) {
                Logger.getLogger(vntana_productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void bloqBotones(boolean ok) {
        bntnuevo.setEnabled(ok);
        btn_reg.setEnabled(ok);
        btnAct.setEnabled(ok);
    }

    public boolean actBotones(boolean upda, boolean delet) {
        btnAct.setEnabled(upda);//true
        //.setEnabled(delet);//true
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbliva = new javax.swing.JTable();
        pnel_cab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        bntnuevo = new javax.swing.JButton();
        btn_reg = new javax.swing.JButton();
        btnAct = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblcod = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblcod1 = new javax.swing.JLabel();
        textcod = new javax.swing.JTextField();
        lblcod2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuración Iva");

        tbliva.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tbliva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbliva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblivaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbliva);

        pnel_cab.setBackground(new java.awt.Color(51, 51, 51));
        pnel_cab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("IVA");
        pnel_cab.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        jToolBar1.setBackground(new java.awt.Color(204, 204, 204));
        jToolBar1.setRollover(true);

        bntnuevo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        bntnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        bntnuevo.setText("Nuevo");
        bntnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntnuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(bntnuevo);

        btn_reg.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_reg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_reg.setText("Registrar");
        btn_reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_reg);

        btnAct.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnAct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btnAct.setText("Actualizar");
        btnAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAct);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("IVA:");
        lblcod.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblcodComponentHidden(evt);
            }
        });

        txtIva.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIvaKeyTyped(evt);
            }
        });

        lblcod1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod1.setText("%");
        lblcod1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblcod1ComponentHidden(evt);
            }
        });

        textcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        textcod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        textcod.setEnabled(false);

        lblcod2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod2.setText("cod:");
        lblcod2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblcod2ComponentHidden(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(lblcod1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblcod2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textcod, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblcod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcod2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(116, 116, 116)
                .addComponent(lblcod1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(pnel_cab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnel_cab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblcodComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblcodComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodComponentHidden

    private void lblcod1ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblcod1ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcod1ComponentHidden

    private void btnActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActActionPerformed
        // TODO add your handling code here:
        try {
            if (!txtIva.getText().equals("")) {
                rp.edit_iva(Integer.parseInt(textcod.getText()), iva(Double.parseDouble(txtIva.getText())), 0);
                load_table(rp.busc_cod_iva(""));
            } else {
                JOptionPane.showMessageDialog(this, "Existe un error");
            }

        } catch (Exception err) {
            System.out.println("Aqui esta el error del iva:" + err);
        }

    }//GEN-LAST:event_btnActActionPerformed

    private void btn_regActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regActionPerformed
        // TODO add your handling code here:
        try {
            if (!txtIva.getText().equals("")) {
                rp.save_iva(rp.codIva(), iva(Double.parseDouble(txtIva.getText())));
                load_table(rp.busc_cod_iva(""));
                txtIva.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Existe un error");
            }
        } catch (Exception err) {
            System.out.println("Aqui esta el error del iva:" + err);
        }
    }//GEN-LAST:event_btn_regActionPerformed

    private void lblcod2ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblcod2ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcod2ComponentHidden

    private void bntnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntnuevoActionPerformed
        // TODO add your handling code here:
        textcod.setText(rp.codIva() + "");
        btn_reg.setEnabled(true);
        btnAct.setEnabled(false);
        txtIva.setText("");

    }//GEN-LAST:event_bntnuevoActionPerformed

    private void tblivaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblivaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            textcod.setText(tbliva.getValueAt(tbliva.getSelectedRow(), 0).toString());
            txtIva.setText(Double.parseDouble(tbliva.getValueAt(tbliva.getSelectedRow(), 1).toString()) * 100 + "");
            if (delet == true && update == true) {

                btn_reg.setEnabled(false);
                btnAct.setEnabled(true);

            } else {
                actBotones(update, delet);
            }

        }
    }//GEN-LAST:event_tblivaMouseClicked

    private void txtIvaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIvaKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) && (car != '.')) {
            evt.consume();
        }
        if (car == '.' && txtIva.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIvaKeyTyped

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
            java.util.logging.Logger.getLogger(vntana_iva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_iva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_iva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_iva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_iva dialog = new vntana_iva(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bntnuevo;
    private javax.swing.JButton btnAct;
    private javax.swing.JButton btn_reg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblcod1;
    private javax.swing.JLabel lblcod2;
    private javax.swing.JPanel pnel_cab;
    private javax.swing.JTable tbliva;
    private javax.swing.JTextField textcod;
    private javax.swing.JTextField txtIva;
    // End of variables declaration//GEN-END:variables
}
