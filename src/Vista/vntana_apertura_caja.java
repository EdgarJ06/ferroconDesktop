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
import Modelo.Personal;
import Modelo.Validaciones;
import Modelo.apert_caja;
import Modelo.caja;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Edgar
 */
public class vntana_apertura_caja extends javax.swing.JDialog {

    /**
     * Creates new form vntana_apertura_caja
     */
    String ced;
    ArrayList<Personal> listuser;
    reg_caja rc = new reg_caja();
    reg_personal ru = new reg_personal();
    caja cj = new caja();
    apert_caja apc = new apert_caja();
    int cod_usu;
    ArrayList<Permisos> listPerm;
    reg_permisos rperm = new reg_permisos();
    ArrayList<apert_caja> listEstadoCaja;

    public vntana_apertura_caja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
         setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
       
        ced = menu_principal.ced_usu;

        loadPermisos(ced);
        txtFecha.setText(Validaciones.fechaActualHora());
        load_user(ced);
        //  estado_caja              (            1             );
        cmbCaja.addItem(null);
        cj.comboListCaja(cmbCaja);
    }


    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
           if (per.isPer_regist()) {
                // btn_nuevo.setEnabled(true);
            }
            if (per.isPer_udate()) {
                //  btn_actualizar.setEnabled(true);
                // update = true;
            }
            if (per.isPer_delete()) {
                System.out.println("Ingresa al eliminar");
                // btn_delete.setEnabled(true);
                // delet = true;
            }
        }

    }

    //ESTADO DE LA CAJA
    public void estado_caja(int dato) {
        listEstadoCaja = rc.llenar_datos_caja(rc.load_caja(dato));
        txtEstado.setText("Desactivada");
        for (apert_caja us : listEstadoCaja) {

            if (us.getEstado()) {
                txtEstado.setText("Activo");
            } else {
                txtEstado.setText("Desactivada");
            }
        }
    }

    public boolean isActiva() {
        boolean ok = true;
        listEstadoCaja = rc.llenar_datos_caja(rc.load_all_caja());
        for (apert_caja us : listEstadoCaja) {
            if (us.getEstado()) {
                ok = false;
            }
        }
        return ok;
    }

    //CERRAR METODO ESTADO DE LA CAJA
    public void load_user(String dato) {
        listuser = ru.user_active(ru.bus_user_ced(dato));
        for (Personal us : listuser) {
            text_usuario.setText(us.getNombre() + "  " + us.getApellido());
            cod_usu = us.getCod_usua();
        }
    }

    public boolean form_validado() {
        boolean ok = true;
        String men = "Campos requeridos o con errores: ";
        if (!Validaciones.esFlotante(txtmontoini)) {
            ok = false;
            men += "campo requerido ";
        }
        return ok;
    }

    public void iscajaActiva() {
        txtEstado.setText(null);

        try {
            estado_caja(cmbCaja.getItemAt(cmbCaja.getSelectedIndex()).getId_caja());
            txtnumcaja.setText(cmbCaja.getItemAt(cmbCaja.getSelectedIndex()).getNum_caja() + "");
        } catch (Exception err) {
            System.out.println(err);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_cab = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pnel_inf = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btn_agregar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cmbCaja = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtmontoini = new javax.swing.JTextField();
        text_usuario = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtnumcaja = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Apertura de Caja");

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Apertura de Caja");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Q:");
        pnel_inf.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 136, -1, 20));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("Monto Inicial");
        pnel_inf.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 136, -1, 20));

        btn_agregar.setBackground(new java.awt.Color(0, 153, 204));
        btn_agregar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar.setText("Activar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 176, -1, -1));

        btn_cancelar.setBackground(new java.awt.Color(204, 51, 0));
        btn_cancelar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_cancelar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        pnel_inf.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(276, 176, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Seleccionar Caja:");
        pnel_inf.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        cmbCaja.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCajaActionPerformed(evt);
            }
        });
        pnel_inf.add(cmbCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 10, 327, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("Fecha Apertura:");
        pnel_inf.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 104, -1, 20));

        txtFecha.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        pnel_inf.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 105, 210, -1));

        txtmontoini.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtmontoini.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmontoiniKeyReleased(evt);
            }
        });
        pnel_inf.add(txtmontoini, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 137, 210, -1));

        text_usuario.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_usuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_usuario.setEnabled(false);
        text_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_usuarioActionPerformed(evt);
            }
        });
        pnel_inf.add(text_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 73, 370, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setText("Usuario:");
        pnel_inf.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 73, -1, 20));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("Número de Caja:");
        pnel_inf.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 41, -1, 20));

        txtnumcaja.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtnumcaja.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtnumcaja.setEnabled(false);
        pnel_inf.add(txtnumcaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 42, 109, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Estado:");
        pnel_inf.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 41, -1, 20));

        txtEstado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtEstado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEstado.setEnabled(false);
        pnel_inf.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 42, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnl_cab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_usuarioActionPerformed

    private void cmbCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCajaActionPerformed
        // TODO add your handling code here:

        iscajaActiva();

    }//GEN-LAST:event_cmbCajaActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        //  verificar si existe una caja activa si es asi no debe permitir activar hast aque no se cierre
        //  if (isActiva()) {    
            if (form_validado()) {
                if (isActiva()) {
                    apc.setId_caja(cmbCaja.getItemAt(cmbCaja.getSelectedIndex()).getId_caja());
                    apc.setId_user(cod_usu);
                    apc.setFecha_hora_aper(Validaciones.fechaActualHoraSql());
                    apc.setMonto_ini(Double.parseDouble(txtmontoini.getText()));
                    apc.setFecha_hora_cierr(null);
                    apc.setMonto_final(0.0);
                    apc.setEstado(true);
                    rc.insertApertCaja(apc);
                    iscajaActiva();
                } else {
                    JOptionPane.showMessageDialog(this, "Existe una caja Activa debe cerrarla para continuar "
                            + "con la operación");
                }
            }
        if (txtEstado.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Seleccione Caja");
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void txtmontoiniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoiniKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esFlotante(txtmontoini)) {
            Validaciones.pinta_text(txtmontoini);
        }
    }//GEN-LAST:event_txtmontoiniKeyReleased

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
            java.util.logging.Logger.getLogger(vntana_apertura_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_apertura_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_apertura_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_apertura_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_apertura_caja dialog = new vntana_apertura_caja(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JComboBox<caja> cmbCaja;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTextField text_usuario;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtmontoini;
    private javax.swing.JTextField txtnumcaja;
    // End of variables declaration//GEN-END:variables
}
