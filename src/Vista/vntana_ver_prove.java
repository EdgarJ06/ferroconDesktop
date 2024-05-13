package Vista;

import Controlador.reg_pais;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_proveedor;
import Modelo.Permisos;
import Modelo.Proveedores;
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
public class vntana_ver_prove extends javax.swing.JDialog {

    reg_proveedor rprove = new reg_proveedor();
    reg_pais rp = new reg_pais();
    public static int codigoProve;
    reg_permisos rperm = new reg_permisos();
    ArrayList<Permisos> listPerm;
    reg_personal ru = new reg_personal();
    String ced;

    public vntana_ver_prove(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());
        try {
            ced = menu_principal.ced_usu;

            loadPermisos(ced);
            load_table("");
            modelo_tabla();
            lblcount.setText(rprove.contProve() + "");
        } catch (Exception err) {
            System.out.println(err);
        }

    }

    DefaultTableModel datos;
    ArrayList<Proveedores> listProve;

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

    public void modelo_tabla() {
        try {
            table_proveedores.getColumnModel().getColumn(0).setPreferredWidth(3);
            table_proveedores.getColumnModel().getColumn(1).setPreferredWidth(10);
            table_proveedores.getColumnModel().getColumn(2).setPreferredWidth(30);
            table_proveedores.getColumnModel().getColumn(3).setPreferredWidth(100);
            table_proveedores.getColumnModel().getColumn(4).setPreferredWidth(100);
            table_proveedores.getColumnModel().getColumn(5).setPreferredWidth(60);
            table_proveedores.getColumnModel().getColumn(6).setPreferredWidth(15);
            table_proveedores.getTableHeader().setBackground(Color.decode("#666666"));
            table_proveedores.getTableHeader().setForeground(Color.white);
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            table_proveedores.getColumnModel().getColumn(0).setCellRenderer(tcr);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void load_table(String dato) {
        try {
            datos = rprove.label_Proveedores();
            String[] data = new String[7];
            listProve = rprove.llenar_Proveedores(rprove.sql_prove(dato));
            for (Proveedores pr : listProve) {
                data[0] = pr.getCod_prove() + "";
                data[1] = rp.ObtNomCiu(pr.getCod_ciu() + "");
                data[2] = pr.getCed_ruc();
                data[3] = pr.getNom_prove();
                data[4] = pr.getDir_prov();
                data[5] = pr.getCorreo_prove();
                data[6] = pr.getTelefono();
                datos.addRow(data);
                table_proveedores.setModel(datos);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_cab = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnel_all = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tbar = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        text_buscar = new javax.swing.JTextField();
        btn_all = new javax.swing.JButton();
        btn_prove = new javax.swing.JButton();
        btn_rfres = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lblcount = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_proveedores = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Proveedores");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Listado de Proveedores");

        javax.swing.GroupLayout panel_cabLayout = new javax.swing.GroupLayout(panel_cab);
        panel_cab.setLayout(panel_cabLayout);
        panel_cabLayout.setHorizontalGroup(
            panel_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(656, Short.MAX_VALUE))
        );
        panel_cabLayout.setVerticalGroup(
            panel_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(panel_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 30));

        pnel_all.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Doble clic para seleccionar");

        javax.swing.GroupLayout pnel_allLayout = new javax.swing.GroupLayout(pnel_all);
        pnel_all.setLayout(pnel_allLayout);
        pnel_allLayout.setHorizontalGroup(
            pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_allLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(646, Short.MAX_VALUE))
        );
        pnel_allLayout.setVerticalGroup(
            pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_allLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(pnel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 810, 30));

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Código/Ruc/ced:");
        tbar.add(jLabel3);

        text_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_buscarKeyReleased(evt);
            }
        });
        tbar.add(text_buscar);

        btn_all.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lupa.png"))); // NOI18N
        btn_all.setText("Ver todo");
        btn_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_allActionPerformed(evt);
            }
        });
        tbar.add(btn_all);

        btn_prove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedor.png"))); // NOI18N
        btn_prove.setText("Registrar Proveedor");
        btn_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveActionPerformed(evt);
            }
        });
        tbar.add(btn_prove);

        btn_rfres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sincronizar.png"))); // NOI18N
        btn_rfres.setText("Recargar");
        btn_rfres.setFocusable(false);
        btn_rfres.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btn_rfres.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_rfres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rfresActionPerformed(evt);
            }
        });
        tbar.add(btn_rfres);
        tbar.add(jSeparator1);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Cantidad de Registros:");
        tbar.add(jLabel5);
        tbar.add(jSeparator2);

        lblcount.setText("0");
        tbar.add(lblcount);
        tbar.add(jSeparator3);

        getContentPane().add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 810, -1));

        table_proveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table_proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_proveedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_proveedores);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 790, 390));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_proveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_proveedoresMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            codigoProve = Integer.parseInt(table_proveedores.getValueAt(table_proveedores.getSelectedRow(), 0).toString());
            // codigoProve = (table_proveedores.getValueAt(table_proveedores.getSelectedRow(), 3).toString());
            dispose(); //cerrar el diálogo
        }
    }//GEN-LAST:event_table_proveedoresMouseClicked

    private void text_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscarKeyReleased
        // TODO add your handling code here:
        load_table(text_buscar.getText());//buscar datos
        modelo_tabla();
    }//GEN-LAST:event_text_buscarKeyReleased

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed
        //ver todo
        load_table("");//buscar datos
        modelo_tabla();
        lblcount.setText(rprove.contProve() + "");
    }//GEN-LAST:event_btn_allActionPerformed

    private void btn_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveActionPerformed
        // TODO add your handling code here:
        //proveedor
        vntana_proveedores dlg = new vntana_proveedores(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);


    }//GEN-LAST:event_btn_proveActionPerformed

    private void btn_rfresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rfresActionPerformed
        // TODO add your handling code here:
        load_table("");//buscar datos
        modelo_tabla();
        lblcount.setText(rprove.contProve() + "");
    }//GEN-LAST:event_btn_rfresActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_ver_prove.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_prove.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_prove.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_prove.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_ver_prove dialog = new vntana_ver_prove(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_prove;
    private javax.swing.JButton btn_rfres;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JLabel lblcount;
    private javax.swing.JPanel panel_cab;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JTable table_proveedores;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_buscar;
    // End of variables declaration//GEN-END:variables
}
