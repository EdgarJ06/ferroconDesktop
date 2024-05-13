/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_ajustes;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Modelo.Permisos;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class vntana_ver_motivos extends javax.swing.JDialog {

    /**
     * Creates new form vntana_ver_motivos
     */
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    DefaultTableModel datos;
    ArrayList<Permisos> listPerm;

    public static String codigo;
    public static String motivo;
    public static String tipo;

    String ced;
    reg_ajustes rajus = new reg_ajustes();

    public vntana_ver_motivos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
         setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
         try {
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            fill_table();

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
            tbl_marca.setModel(rajus.setFilas_motivo(""));
            modelo_tabla();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void modelo_tabla() {
        tbl_marca.getColumnModel().getColumn(0).setPreferredWidth(3);
        tbl_marca.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbl_marca.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_marca.getTableHeader().setBackground(Color.decode("#666666"));
        tbl_marca.getTableHeader().setForeground(Color.white);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tbl_marca.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbar = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        text_buscar = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_busc_todo = new javax.swing.JButton();
        btn_prod = new javax.swing.JButton();
        btn_rfres = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        panel_cabez = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_marca = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Motivos");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Código/Tipo:");
        tbar.add(jLabel3);
        tbar.add(jSeparator4);

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
        tbar.add(text_buscar);
        tbar.add(jSeparator1);

        btn_busc_todo.setBackground(new java.awt.Color(204, 51, 0));
        btn_busc_todo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_busc_todo.setForeground(new java.awt.Color(255, 255, 255));
        btn_busc_todo.setText("Todo");
        btn_busc_todo.setFocusable(false);
        btn_busc_todo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_busc_todo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_busc_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busc_todoActionPerformed(evt);
            }
        });
        tbar.add(btn_busc_todo);

        btn_prod.setBackground(new java.awt.Color(0, 153, 204));
        btn_prod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_prod.setForeground(new java.awt.Color(255, 255, 255));
        btn_prod.setText("Motivos");
        btn_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prodActionPerformed(evt);
            }
        });
        tbar.add(btn_prod);

        btn_rfres.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_rfres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sincronizar.png"))); // NOI18N
        btn_rfres.setText("Refres");
        btn_rfres.setFocusable(false);
        btn_rfres.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_rfres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rfresActionPerformed(evt);
            }
        });
        tbar.add(btn_rfres);
        tbar.add(jSeparator2);

        getContentPane().add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 680, 40));

        panel_cabez.setBackground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Listado de Motivos");

        javax.swing.GroupLayout panel_cabezLayout = new javax.swing.GroupLayout(panel_cabez);
        panel_cabez.setLayout(panel_cabezLayout);
        panel_cabezLayout.setHorizontalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cabezLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(553, Short.MAX_VALUE))
        );
        panel_cabezLayout.setVerticalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(panel_cabez, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 30));

        tbl_marca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_marca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_marcaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_marca);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 74, 660, 380));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Doble clic para seleccionar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 526, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 680, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_rfresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rfresActionPerformed
        // TODO add your handling code here:
        fill_table();

    }//GEN-LAST:event_btn_rfresActionPerformed

    private void btn_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prodActionPerformed
        // TODO add your handling code here:
        vntana_motiv_ajuste dlg = new vntana_motiv_ajuste(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_btn_prodActionPerformed

    private void btn_busc_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busc_todoActionPerformed
        // TODO add your handling code here:
        fill_table();
    }//GEN-LAST:event_btn_busc_todoActionPerformed

    private void text_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscarKeyReleased
        // TODO add your handling code here:
        tbl_marca.setModel(rajus.setFilas_motivo(text_buscar.getText()));
        modelo_tabla();
    }//GEN-LAST:event_text_buscarKeyReleased

    private void tbl_marcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_marcaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            codigo = (tbl_marca.getValueAt(tbl_marca.getSelectedRow(), 0).toString());
            motivo = (tbl_marca.getValueAt(tbl_marca.getSelectedRow(), 1).toString());
            tipo = (tbl_marca.getValueAt(tbl_marca.getSelectedRow(), 2).toString());
            dispose(); //cerrar el diálogo
        }
    }//GEN-LAST:event_tbl_marcaMouseClicked

    private void text_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_buscarActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_ver_motivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_motivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_motivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_motivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_ver_motivos dialog = new vntana_ver_motivos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_busc_todo;
    private javax.swing.JButton btn_prod;
    private javax.swing.JButton btn_rfres;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPanel panel_cabez;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTable tbl_marca;
    private javax.swing.JTextField text_buscar;
    // End of variables declaration//GEN-END:variables
}
