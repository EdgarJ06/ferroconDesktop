/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_pedidos;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_proveedor;
import Modelo.Pedidos;
import Modelo.Permisos;
import Modelo.Validaciones;
import Modelo.Ventas;
import java.awt.Color;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class vntana_ver_cmpras extends javax.swing.JDialog {

    /**
     * Creates new form vnatana_caja
     */
    ArrayList<Ventas> listVntas;
    DefaultTableModel campos;
    reg_proveedor rprov = new reg_proveedor();
    DefaultTableModel datos;
    reg_pedidos rped = new reg_pedidos();
    reg_personal regUs = new reg_personal();
    ArrayList<Pedidos> listped;
    ArrayList<Permisos> listPerm;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    String ced;
    DecimalFormat fd = new DecimalFormat("0.00");

    public vntana_ver_cmpras(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);//Centrar ventana
         setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
       ced = menu_principal.ced_usu;
        loadPermisos(ced);
        text_fec.setText(Validaciones.fechaActual());
        bloquearColumTable(rped.buscCod_pedido_date(text_fec.getText(), text_fec.getText()));
        countVntas();
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
                /* System.out.println("Ingresa al eliminar");
                 btn_delete.setEnabled(true);
                 delet = true;*/
            }
        }
    }

    public void bloquearColumTable(String sql) {//edicion de la tabla
        try {
            loadCompras(sql);
            modelo_tabla();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void modelo_tabla() {
        tblcmpras.getColumnModel().getColumn(0).setPreferredWidth(1);
        tblcmpras.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblcmpras.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblcmpras.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblcmpras.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblcmpras.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblcmpras.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblcmpras.getColumnModel().getColumn(7).setPreferredWidth(20);       
        tblcmpras.getTableHeader().setBackground(Color.decode("#666666"));
        tblcmpras.getTableHeader().setForeground(Color.WHITE);


        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblcmpras.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public void loadCompras(String sql) {
        datos = rped.label_table();
        Object[] data = new Object[8];
        listped = rped.llenar_ped(sql);
        for (Pedidos ped : listped) {
            try {
                data[0] = ped.getCod_pedido();
                data[1] = rprov.ObtNomProv(ped.getCod_prov());//  nombre del proveedor      
                data[2] = regUs.load_nom_user(ped.getCod_usu());//  nombre del cliente
                data[3] = ped.getFecha_pedid();
                data[4] = ped.getSubtotal();
                data[5] = fd.format(ped.getIva_pedid()).replace(",", ".");
                data[6] = fd.format(ped.getDesc_pedid()).replace(",", ".");
                data[7] = fd.format(ped.getTotal_pedid()).replace(",", ".");
                datos.addRow(data);
                tblcmpras.setModel(datos);
            } catch (Exception ex) {
                Logger.getLogger(vntana_productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void countVntas() {
        try {
            int cont = 0;
            double subt = 0, iva = 0, total = 0;
            for (int i = 0; i < tblcmpras.getRowCount(); i++) {
                cont++;
                subt = subt + Double.parseDouble(tblcmpras.getValueAt(i, 4).toString());
                iva = iva + Double.parseDouble(tblcmpras.getValueAt(i, 5).toString());
                total = total + Double.parseDouble(tblcmpras.getValueAt(i, 7).toString());
            }
            txtSubt.setText(fd.format(subt).replace(",", "."));
            txtIva.setText(fd.format(iva).replace(",", "."));
            txtTot.setText(fd.format(total).replace(",", "."));
            lblCount.setText(cont + "");
        } catch (Exception err) {
            // JOptionPane.showMessageDialog(this,"No existen datos");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnel_cab2 = new javax.swing.JPanel();
        txtSubt = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        txtTot = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        text_fec = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtFec1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtFec2 = new javax.swing.JTextField();
        btn_busc = new javax.swing.JButton();
        btn_all = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lblCount = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcmpras = new javax.swing.JTable();
        btn_impr = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pedidos");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Compras");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(839, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnel_cab2.setBackground(new java.awt.Color(204, 204, 204));
        pnel_cab2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_cab2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSubt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubt.setEnabled(false);
        pnel_cab2.add(txtSubt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 60, -1));

        txtIva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIva.setEnabled(false);
        pnel_cab2.add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 60, -1));

        txtTot.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTot.setEnabled(false);
        pnel_cab2.add(txtTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 60, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("SUBTOTAL VENTAS:");
        pnel_cab2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setText("TOTAL IVA:");
        pnel_cab2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, 20));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("TOTAL VENTAS:");
        pnel_cab2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, 20));

        getContentPane().add(pnel_cab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(364, 446, 530, 40));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Fecha:");

        text_fec.setText("jLabel1");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Fecha Inicio:");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Fecha Fin:");

        btn_busc.setBackground(new java.awt.Color(0, 153, 204));
        btn_busc.setForeground(new java.awt.Color(255, 255, 255));
        btn_busc.setText("Buscar");
        btn_busc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscActionPerformed(evt);
            }
        });

        btn_all.setBackground(new java.awt.Color(204, 51, 0));
        btn_all.setForeground(new java.awt.Color(255, 255, 255));
        btn_all.setText("Todo");
        btn_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_allActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Cantidad de Ventas:");

        lblCount.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addGap(6, 6, 6)
                .addComponent(text_fec, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel12)
                .addGap(4, 4, 4)
                .addComponent(txtFec1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel13)
                .addGap(4, 4, 4)
                .addComponent(txtFec2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(btn_busc)
                .addGap(18, 18, 18)
                .addComponent(btn_all)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(10, 10, 10)
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(text_fec, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtFec1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtFec2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btn_busc))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btn_all))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 880, 50));

        tblcmpras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblcmpras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblcmprasMouseClicked(evt);
            }
        });
        tblcmpras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblcmprasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblcmpras);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 884, 294));

        btn_impr.setBackground(new java.awt.Color(0, 153, 102));
        btn_impr.setForeground(new java.awt.Color(255, 255, 255));
        btn_impr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imprimir.png"))); // NOI18N
        btn_impr.setText("Imprimir");
        btn_impr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprActionPerformed(evt);
            }
        });
        getContentPane().add(btn_impr, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 446, 130, 40));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Doble clic para imprimir la factura");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(644, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 884, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscActionPerformed
        //  loadVntas(txtFec1.getText(), txtFec2.getText());
        try {
            loadCompras(rped.buscCod_pedido_date(txtFec1.getText(), txtFec2.getText()));
            modelo_tabla();
            countVntas();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, "Error no existen datos");
        }


    }//GEN-LAST:event_btn_buscActionPerformed

    private void tblcmprasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblcmprasKeyPressed
        // TODO add your handling code here:


    }//GEN-LAST:event_tblcmprasKeyPressed

    private void tblcmprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcmprasMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Imprimir Factura",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {

                String num = (tblcmpras.getValueAt(tblcmpras.getSelectedRow(), 0).toString());

                JOptionPane.showMessageDialog(this, "Realizando operación.......Espere un momento");
                try {

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("cod_ped", num);
                    System.out.println(num);
                    Reportes_g.generarReporte("rep_cmpras", params);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }

    }//GEN-LAST:event_tblcmprasMouseClicked

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed
        // TODO add your handling code here:
        try {
            // if (!lblCount.getText().equals("0")) {
            loadCompras(rped.buscCod_pedido_ord("", ""));
            modelo_tabla();
            countVntas();
            /* } else {
             JOptionPane.showMessageDialog(this, "No existen datos");
             }*/
        } catch (Exception err) {
            System.out.println(err);
        }
    }//GEN-LAST:event_btn_allActionPerformed

    private void btn_imprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprActionPerformed
        // TODO add your handling code here:
        if (!lblCount.getText().equals("0")) {
            try {
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
                Reportes_g.generarReporte("rep_cmpras_realz", params);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Existe un error en los datos");
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No existen datos");
        }
    }//GEN-LAST:event_btn_imprActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_ver_cmpras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_cmpras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_cmpras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_ver_cmpras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_ver_cmpras dialog = new vntana_ver_cmpras(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JPanel pnel_cab2;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable tblcmpras;
    private javax.swing.JLabel text_fec;
    private javax.swing.JTextField txtFec1;
    private javax.swing.JTextField txtFec2;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtSubt;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
}
