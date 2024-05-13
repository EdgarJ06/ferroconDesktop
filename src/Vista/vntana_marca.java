/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_marca_tip;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Modelo.Marca;
import Modelo.Permisos;
import Modelo.Validaciones;
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
public class vntana_marca extends javax.swing.JDialog {

    /**
     * Creates new form vntana_marca
     */
    reg_marca_tip marc = new reg_marca_tip();
    Marca m = new Marca();
    boolean delet = false;
    boolean update = false;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    ArrayList<Permisos> listPerm;
    String ced;

    public vntana_marca(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());
        blockBotones(false);//desactivado todos los botones
        ced = menu_principal.ced_usu;
        loadPermisos(ced);
        fill_table();
        lblCont.setText(marc.count_marca() + "");
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
        btn_guardar.setEnabled(ok);
        btn_actualizar.setEnabled(ok);
        btn_delete.setEnabled(ok);

    }

    public void botones(boolean ok, boolean ok1) {
        btn_guardar.setEnabled(ok);
        btn_actualizar.setEnabled(ok1);//true
        btn_delete.setEnabled(ok1);//true
    }
    DefaultTableModel etiqueta;

    public void fill_table() {
        try {
            tbl_marca.setModel(marc.setFilas_marca(""));
            modelo_tabla();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void modelo_tabla() {
        tbl_marca.getColumnModel().getColumn(0).setPreferredWidth(3);
        tbl_marca.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl_marca.getTableHeader().setBackground(Color.decode("#666666"));

        tbl_marca.getTableHeader().setForeground(Color.WHITE);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tbl_marca.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public boolean actBotones(boolean upda, boolean delet) {

        btn_actualizar.setEnabled(upda);//true
        btn_delete.setEnabled(delet);//true

        return true;
    }

    public boolean validar() {
        boolean ok = true;
        String mens = "Campo Requerido:";
        if (!Validaciones.esRequerido(text_nom_marca)) {
            ok = false;
            mens += "Nombre de la marca ";
        };
        /* label_men.setForeground(Color.RED);
         label_men.setText(mens);*/
        return ok;
    }

    public void limpiar_campos() {
        text_cod.setText("");
        text_nom_marca.setText("");
        pinta_text();
    }

    public void pinta_text() {
        Validaciones.pinta_text(text_cod);
        Validaciones.pinta_text(text_nom_marca);
    }

    public void campos(boolean ok) {
        text_nom_marca.setEnabled(ok);
        pinta_text();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tbar = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lblCont = new javax.swing.JLabel();
        pnel_inf = new javax.swing.JPanel();
        lblmarc = new javax.swing.JLabel();
        lblcod = new javax.swing.JLabel();
        text_cod = new javax.swing.JTextField();
        text_nom_marca = new javax.swing.JTextField();
        pnel_inf1 = new javax.swing.JPanel();
        lbel_bus = new javax.swing.JLabel();
        text_busc = new javax.swing.JTextField();
        pnel_all = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btnbusTodo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_marca = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Marcas");

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Marcas");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(338, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        tbar.add(btn_new);

        btn_guardar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_guardar.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        tbar.add(btn_guardar);

        btn_actualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_actualizar.setForeground(new java.awt.Color(255, 255, 255));
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        tbar.add(btn_actualizar);

        btn_delete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        tbar.add(btn_delete);
        tbar.add(jSeparator1);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Cantidad de Registros:");
        tbar.add(jLabel10);
        tbar.add(jSeparator2);

        lblCont.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCont.setText("0");
        tbar.add(lblCont);

        pnel_inf.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblmarc.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblmarc.setText("Marca:");
        pnel_inf.add(lblmarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        lblcod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblcod.setText("Código:");
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        text_cod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cod.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod.setEnabled(false);
        pnel_inf.add(text_cod, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 70, -1));

        text_nom_marca.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        pnel_inf.add(text_nom_marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 290, -1));

        pnel_inf1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbel_bus.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbel_bus.setText("Buscar:");
        pnel_inf1.add(lbel_bus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        text_busc.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_busc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_buscKeyReleased(evt);
            }
        });
        pnel_inf1.add(text_busc, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 10, 210, -1));

        pnel_all.setBackground(new java.awt.Color(204, 204, 204));
        pnel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("Doble clic para seleccionar Marca");
        pnel_all.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 20));

        btnbusTodo.setBackground(new java.awt.Color(0, 153, 102));
        btnbusTodo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnbusTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnbusTodo.setText("Todo");
        btnbusTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusTodoActionPerformed(evt);
            }
        });

        tbl_marca.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnel_all, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnel_inf1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnbusTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnel_inf1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbusTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnel_all, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Marca",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                m.setCod_marca(Integer.parseInt(text_cod.getText()));
                marc.Delete(m);
                fill_table();
                lblCont.setText(marc.count_marca() + "");
            }

            /* label_men.setForeground(Color.GREEN);
             label_men.setText("Datos Eliminados Correctamente");*/
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btnbusTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusTodoActionPerformed

        fill_table();


    }//GEN-LAST:event_btnbusTodoActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed

        //registrar marcas
        if (validar()) {
            m.setCod_marca(Integer.parseInt(text_cod.getText()));
            m.setNom_marca(text_nom_marca.getText());
            marc.insert(m);
            JOptionPane.showMessageDialog(this, "Marca Registrada");
            fill_table();
            lblCont.setText(marc.count_marca() + "");

        }


    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:

        limpiar_campos();

        campos(true);

        text_cod.setText(marc.cod_marca() + "");
        botones(true, false);


    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar la Marca",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                m.setCod_marca(Integer.parseInt(text_cod.getText()));
                m.setNom_marca(text_nom_marca.getText());
                marc.Update(m);
                fill_table();
            }

        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void tbl_marcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_marcaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            text_cod.setText(tbl_marca.getValueAt(tbl_marca.getSelectedRow(), 0).toString());
            text_nom_marca.setText(tbl_marca.getValueAt(tbl_marca.getSelectedRow(), 1).toString());
            pinta_text();
            campos(true);

            if (delet == true && update == true) {
                botones(false, true);

            } else {
                actBotones(update, delet);
            }
        }
    }//GEN-LAST:event_tbl_marcaMouseClicked

    private void text_buscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscKeyReleased
        // TODO add your handling code here:
        tbl_marca.setModel(marc.setFilas_marca(text_busc.getText()));
        modelo_tabla();
    }//GEN-LAST:event_text_buscKeyReleased

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
            java.util.logging.Logger.getLogger(vntana_marca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_marca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_marca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_marca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_marca dialog = new vntana_marca(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnbusTodo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JLabel lbel_bus;
    private javax.swing.JLabel lblCont;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblmarc;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTable tbl_marca;
    private javax.swing.JTextField text_busc;
    private javax.swing.JTextField text_cod;
    private javax.swing.JTextField text_nom_marca;
    // End of variables declaration//GEN-END:variables
}
