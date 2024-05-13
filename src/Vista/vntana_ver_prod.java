/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_marca_tip;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_productos;
import Modelo.Permisos;
import Modelo.Producto;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class vntana_ver_prod extends javax.swing.JDialog {

    /**
     * Creates new form vntana_ver_cli
     */
    reg_marca_tip rmt = new reg_marca_tip();
    reg_productos rp = new reg_productos();
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    DefaultTableModel datos;
    ArrayList<Permisos> listPerm;
    ArrayList<Producto> listprod;
    public static String codigo;

    String ced;

    public vntana_ver_prod(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
         setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
         try {
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            loadTable(rp.busc_cod(""));
            lblcountProd.setText(rp.countProd() + "");
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

    public void loadTable(String dato) {
        load_table(dato);
        modelo_tabla();
    }

    public void modelo_tabla() {
        try {
            table_prod.getColumnModel().getColumn(0).setPreferredWidth(3);
            table_prod.getColumnModel().getColumn(1).setPreferredWidth(10);
            table_prod.getColumnModel().getColumn(2).setPreferredWidth(30);
            table_prod.getColumnModel().getColumn(3).setPreferredWidth(30);
            table_prod.getColumnModel().getColumn(4).setPreferredWidth(40);
            table_prod.getColumnModel().getColumn(5).setPreferredWidth(60);
            table_prod.getColumnModel().getColumn(6).setPreferredWidth(15);
            table_prod.getColumnModel().getColumn(7).setPreferredWidth(30);
            table_prod.getColumnModel().getColumn(8).setPreferredWidth(60);
            table_prod.getColumnModel().getColumn(9).setPreferredWidth(15);
            table_prod.getTableHeader().setBackground(Color.decode("#666666"));
            table_prod.getTableHeader().setForeground(Color.white);
        } catch (Exception err) {
            System.out.println(err);
        }

    }

    public void load_table(String dato) {
        datos = rp.label_table();
        Object[] data = new Object[11];
        listprod = rp.llenar_prod_vnta(dato);
        for (Producto pr : listprod) {
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
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_prod = new javax.swing.JTable();
        panel_cabez = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tbar = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        text_buscar = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_busc_todo = new javax.swing.JButton();
        btn_prod = new javax.swing.JButton();
        btn_rfres = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lblcountProd = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        pneinf = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Productos");

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
        jScrollPane1.setViewportView(table_prod);

        panel_cabez.setBackground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Listado de Productos");

        javax.swing.GroupLayout panel_cabezLayout = new javax.swing.GroupLayout(panel_cabez);
        panel_cabez.setLayout(panel_cabezLayout);
        panel_cabezLayout.setHorizontalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cabezLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(853, Short.MAX_VALUE))
        );
        panel_cabezLayout.setVerticalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Código:");
        tbar.add(jLabel3);
        tbar.add(jSeparator4);

        text_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_buscarKeyReleased(evt);
            }
        });
        tbar.add(text_buscar);
        tbar.add(jSeparator1);

        btn_busc_todo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lupa.png"))); // NOI18N
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

        btn_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos.png"))); // NOI18N
        btn_prod.setText("Productos");
        btn_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prodActionPerformed(evt);
            }
        });
        tbar.add(btn_prod);

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

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Cantidad de Productos:");
        tbar.add(jLabel5);
        tbar.add(jSeparator2);

        lblcountProd.setText("0");
        tbar.add(lblcountProd);
        tbar.add(jSeparator3);

        pneinf.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Doble clic para seleccionar");

        javax.swing.GroupLayout pneinfLayout = new javax.swing.GroupLayout(pneinf);
        pneinf.setLayout(pneinfLayout);
        pneinfLayout.setHorizontalGroup(
            pneinfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pneinfLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        pneinfLayout.setVerticalGroup(
            pneinfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pneinfLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_cabez, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pneinf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_cabez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pneinf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_prodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_prodMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            codigo = (table_prod.getValueAt(table_prod.getSelectedRow(), 1).toString());
            dispose(); //cerrar el diálogo
        }
    }//GEN-LAST:event_table_prodMouseClicked

    private void text_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscarKeyReleased
        // TODO add your handling code here:
        loadTable(rp.busc_cod(text_buscar.getText()));
    }//GEN-LAST:event_text_buscarKeyReleased

    private void btn_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prodActionPerformed
        // TODO add your handling code here:
        vntana_productos dlg = new vntana_productos(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_btn_prodActionPerformed

    private void btn_rfresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rfresActionPerformed
        // TODO add your handling code here:
        loadTable(rp.busc_cod(""));
        lblcountProd.setText(rp.countProd() + "");
    }//GEN-LAST:event_btn_rfresActionPerformed

    private void btn_busc_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busc_todoActionPerformed
        // TODO add your handling code here:   
        loadTable(rp.busc_cod(""));
        lblcountProd.setText(rp.countProd() + "");
    }//GEN-LAST:event_btn_busc_todoActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_ver_prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_ver_prod dialog = new vntana_ver_prod(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JLabel lblcountProd;
    private javax.swing.JPanel panel_cabez;
    private javax.swing.JPanel pneinf;
    private javax.swing.JTable table_prod;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_buscar;
    // End of variables declaration//GEN-END:variables
}
