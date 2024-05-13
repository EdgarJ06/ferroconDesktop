package Vista;

import Controlador.reg_pais;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Modelo.Ciudad;
import Modelo.Permisos;
import Modelo.Personal;
import Modelo.Validaciones;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Edgar
 */
public class vntana_crear_user extends javax.swing.JFrame {

    Personal per = new Personal();
    Permisos perm = new Permisos();
    reg_personal rp = new reg_personal();
    reg_pais rc = new reg_pais();
    reg_permisos rperm = new reg_permisos();

    public vntana_crear_user() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());

        cargarComboCiudad();
        text_cod_us.setText(rp.cod_user() + "");//codigo del personal

    }

    public boolean compararPassward() {
        boolean ok = false;
        String pasw = new String(passward.getPassword());
        String pasw1 = new String(passward2.getPassword());
        System.out.println(pasw + "  " + pasw1);
        if (pasw.equals(pasw1)) {
            ok = true;
        }
        return ok;
    }

    public String contrasenia() {//convertir passward
        String pasw = new String(passward.getPassword());
        return pasw;
    }
    ArrayList<Ciudad> listCiu;

    public void cargarComboCiudad() {
        listCiu = rc.CargarListCiu("");
        combo_ciu_us.removeAllItems();
        combo_ciu_us.addItem("");
        for (Ciudad c : listCiu) {
            combo_ciu_us.addItem(c.getNom_ciu());
        }
    }

    public boolean form_validado() {
        boolean ok = true;
        String men = "Campos requeridos o con errores: ";
        //validar requerido
        if (!Validaciones.esCedula(text_ced_us)) {
            ok = false;
            men += "Ruc-Ci ";
        }

        if (!Validaciones.esLetras(text_nom_us)) {
            ok = false;
            men += "Nombre ";
        }

        if (!Validaciones.esLetras(text_ape_us)) {
            ok = false;
            men += "Apellido ";
        }

        if (!Validaciones.esEmail(text_email_us)) {
            ok = false;
            men += "Email ";
        }

        if (!Validaciones.esRequerido(text_dir_us)) {
            ok = false;
            men += "Dirección ";
        }

        if (!Validaciones.esTelefono(text_telf_us)) {
            ok = false;
            men += "Telefono ";
        }

        if (!Validaciones.es_Combo(combo_ciu_us)) {
            ok = false;
            men += "Ciudad";
        }
        if (!Validaciones.es_passward(passward)) {
            ok = false;
            men += "Contraseña";
        }

        if (!Validaciones.es_passward(passward2)) {
            ok = false;
            men += "Contraseña";
        }
        return ok;
    }
  /*    public void save() {
        if (form_validado()) {
            if (compararPassward()) {
                per.setCod_usua(Integer.parseInt(text_cod_us.getText()));//codigo
                per.setTip_usua(rp.loadCodTipUser(combo_tip_us.getSelectedItem().toString()));
                per.setPassword(contrasenia());//);
                per.setCed(text_ced_us.getText());
                per.setNombre(text_nom_us.getText());
                per.setApellido(text_ape_us.getText());
                per.setDireccion(text_dir_us.getText());
                per.setTelefono(text_telf_us.getText());
                per.setCorreo_elect(text_email_us.getText());
                per.setCod_ciu(rc.ObtCodCiu(combo_ciu_us.getSelectedItem().toString()));
                per.setEstado(false);
                rp.save_customer(per);
                savePermisos();
                JOptionPane.showMessageDialog(null, "Usuario registrado");
            } else {
                JOptionPane.showMessageDialog(null, "Contrasenias incorrectas");
            }
        }
    }*/

    public void save() {
        per.setCod_usua(Integer.parseInt(text_cod_us.getText()));
        per.setTip_usua(1);
        per.setPassword(contrasenia());//);
        per.setCed(text_ced_us.getText());
        per.setNombre(text_nom_us.getText());
        per.setApellido(text_ape_us.getText());
        per.setDireccion(text_dir_us.getText());
        per.setTelefono(text_telf_us.getText());
        per.setCorreo_elect(text_email_us.getText());
        per.setCod_ciu(rc.ObtCodCiu(combo_ciu_us.getSelectedItem().toString()));
        per.setEstado(false);
        rp.save_customer(per);
        savePermisos();
    }

    public void savePermisos() {
        perm.setId_permisos(Integer.parseInt(text_cod_us.getText()));
        perm.setCed_usua(text_ced_us.getText());
        perm.setPer_delete(true);
        perm.setPer_regist(true);
        perm.setPer_udate(true);
        perm.setCod_disc(4);
        perm.setId_ima(1);
        rperm.save_Permisos(perm);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        text_cod_us = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        combo_ciu_us = new javax.swing.JComboBox();
        text_ced_us = new javax.swing.JTextField();
        text_ape_us = new javax.swing.JTextField();
        text_nom_us = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        text_telf_us = new javax.swing.JTextField();
        text_dir_us = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        text_email_us = new javax.swing.JTextField();
        passward = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        passward2 = new javax.swing.JPasswordField();
        jLabel16 = new javax.swing.JLabel();
        btn_save = new javax.swing.JButton();
        txtAdmin = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crear Usuario");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Cédula/ID:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Código:");

        text_cod_us.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_us.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Apellido:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Télefono:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Tipo de usuario:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Ciudad:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("Nombre:");

        combo_ciu_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_ciu_usActionPerformed(evt);
            }
        });
        combo_ciu_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                combo_ciu_usKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combo_ciu_usKeyReleased(evt);
            }
        });

        text_ced_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ced_usKeyReleased(evt);
            }
        });

        text_ape_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ape_usKeyReleased(evt);
            }
        });

        text_nom_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_nom_usKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Dirección:");

        text_telf_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_telf_usKeyReleased(evt);
            }
        });

        text_dir_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_dir_usKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("Correo Electrónico:");

        text_email_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_email_usKeyReleased(evt);
            }
        });

        passward.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwardKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("Contraseña:");

        passward2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passward2KeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("Repetir Contraseña:");

        btn_save.setBackground(new java.awt.Color(0, 153, 153));
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_save.setText("Guardar");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        txtAdmin.setText("Administrador");
        txtAdmin.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAdmin.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(text_cod_us, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(text_ced_us, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(text_nom_us, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(text_ape_us, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(text_telf_us, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(combo_ciu_us, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(13, 13, 13)
                        .addComponent(text_dir_us, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(11, 11, 11)
                        .addComponent(text_email_us, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30)
                        .addComponent(txtAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addGap(51, 51, 51)
                        .addComponent(passward, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(7, 7, 7)
                        .addComponent(passward2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2))
                    .addComponent(text_cod_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(text_ced_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(text_nom_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(text_ape_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(text_telf_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_ciu_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_dir_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(text_email_us, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(passward, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(passward2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_save)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 390, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_ced_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ced_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esCedula(text_ced_us)) {
            Validaciones.pinta_text(text_ced_us);
        }
    }//GEN-LAST:event_text_ced_usKeyReleased

    private void text_ape_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ape_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esLetras(text_ape_us)) {
            Validaciones.pinta_text(text_ape_us);
        }
    }//GEN-LAST:event_text_ape_usKeyReleased

    private void text_nom_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_nom_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esLetras(text_nom_us)) {
            Validaciones.pinta_text(text_nom_us);
        }
    }//GEN-LAST:event_text_nom_usKeyReleased

    private void text_telf_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_telf_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esTelefono(text_telf_us)) {
            Validaciones.pinta_text(text_telf_us);
        }
    }//GEN-LAST:event_text_telf_usKeyReleased

    private void text_dir_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dir_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_dir_us)) {
            Validaciones.pinta_text(text_dir_us);
        }
    }//GEN-LAST:event_text_dir_usKeyReleased

    private void text_email_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_email_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esEmail(text_email_us)) {
            Validaciones.pinta_text(text_email_us);
        }
    }//GEN-LAST:event_text_email_usKeyReleased

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        //validaciones 
        try {
            if (compararPassward()) {
                if (form_validado()) {
                    JOptionPane.showMessageDialog(null, "Administrador Registrado");
                    save();
                    login vlg = new login();
                    vlg.setVisible(true);
                    this.setVisible(false);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Contraseñas no Coinciden");
            }
        } catch (Exception err) {
            System.out.println("aquie el error:" + err);
        }

    }//GEN-LAST:event_btn_saveActionPerformed

    private void passwardKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwardKeyReleased
        // TODO add your handling code here:

        if (Validaciones.es_passward(passward)) {
            Validaciones.pinta_text(passward);
        }
    }//GEN-LAST:event_passwardKeyReleased

    private void passward2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passward2KeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_passward(passward)) {
            Validaciones.pinta_text(passward2);
        }
    }//GEN-LAST:event_passward2KeyReleased

    private void combo_ciu_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_ciu_usKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_combo_ciu_usKeyReleased

    private void combo_ciu_usKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_ciu_usKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_combo_ciu_usKeyPressed

    private void combo_ciu_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_ciu_usActionPerformed
        // TODO add your handling code here:
        //  if (Validaciones.es_Combo(combo_ciu_us)) {
        Validaciones.pinta_Combo(combo_ciu_us);
        // }
    }//GEN-LAST:event_combo_ciu_usActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_crear_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_crear_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_crear_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_crear_user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vntana_crear_user().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox combo_ciu_us;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField passward;
    private javax.swing.JPasswordField passward2;
    private javax.swing.JTextField text_ape_us;
    private javax.swing.JTextField text_ced_us;
    private javax.swing.JTextField text_cod_us;
    private javax.swing.JTextField text_dir_us;
    private javax.swing.JTextField text_email_us;
    private javax.swing.JTextField text_nom_us;
    private javax.swing.JTextField text_telf_us;
    private javax.swing.JTextField txtAdmin;
    // End of variables declaration//GEN-END:variables
}
