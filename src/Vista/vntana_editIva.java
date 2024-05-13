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
public class vntana_editIva extends javax.swing.JDialog {

    reg_productos rp = new reg_productos();
    DefaultTableModel datos;
    ArrayList<iva> listIva;
    String ced;
    ArrayList<Permisos> listPerm;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();

    public vntana_editIva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
        try {
            btn_save.setEnabled(false);
            ced = menu_principal.ced_usu;
            loadPermisos(ced);

            load_table(rp.busc_cod_iva(""));
            modelo_tabla();

        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_regist()) {
                // btn_nuevo.setEnabled(true);
            }
            if (per.isPer_udate()) {
                //btn_actualizar.setEnabled(true);
                // update = true;
                btn_save.setEnabled(true);
            }
            if (per.isPer_delete()) {
                /* System.out.println("Ingresa al eliminar");
                 btn_delete.setEnabled(true);
                 delet = true;*/
            }
        }
    }

    public void modelo_tabla() {
        tbIva.getColumnModel().getColumn(0).setPreferredWidth(5);
        tbIva.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbIva.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbIva.getTableHeader().setBackground(Color.decode("#666666"));
        tbIva.getTableHeader().setForeground(Color.white);

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tbIva.getColumnModel().getColumn(0).setCellRenderer(tcr);
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
                tbIva.setModel(datos);
            } catch (Exception ex) {
                Logger.getLogger(vntana_productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbIva = new javax.swing.JTable();
        btn_save = new javax.swing.JButton();
        lblcod = new javax.swing.JLabel();
        txt_val_iva = new javax.swing.JTextField();
        pnel_cab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnel_all = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Iva");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbIva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbIva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbIvaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbIva);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 200, 110));

        btn_save.setBackground(new java.awt.Color(0, 153, 153));
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Realizar configuración");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });
        getContentPane().add(btn_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 180, -1));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("IVA:");
        lblcod.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lblcodComponentHidden(evt);
            }
        });
        getContentPane().add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 30, 20));

        txt_val_iva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_val_iva.setEnabled(false);
        getContentPane().add(txt_val_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 110, -1));

        pnel_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setDisplayedMnemonic('I');
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Configurar Valor del IVA");
        pnel_cab.add(jLabel1);

        getContentPane().add(pnel_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 30));
        getContentPane().add(pnel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 230));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblcodComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lblcodComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_lblcodComponentHidden
    int cod;
    private void tbIvaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbIvaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            //  pinta_text();
            cod = Integer.parseInt(tbIva.getValueAt(tbIva.getSelectedRow(), 0).toString());
            txt_val_iva.setText(tbIva.getValueAt(tbIva.getSelectedRow(), 1).toString());
            txt_val_iva.setEditable(true);

        }
    }//GEN-LAST:event_tbIvaMouseClicked

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        //Activar Nuevo Iva

        if (!txt_val_iva.getText().equals("")) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar Iva",
                    " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                rp.desactivar_all_iva();
                rp.edit_iva(cod, Double.parseDouble(txt_val_iva.getText()), 1);
                //cargar iva
                load_table(rp.busc_cod_iva(""));
                modelo_tabla();

            } else {
                System.out.println("producto no actualizado");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Los campos no deben estar vacios");
        }

    }//GEN-LAST:event_btn_saveActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_editIva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_editIva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_editIva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_editIva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_editIva dialog = new vntana_editIva(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblcod;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnel_cab;
    private javax.swing.JTable tbIva;
    private javax.swing.JTextField txt_val_iva;
    // End of variables declaration//GEN-END:variables
}
