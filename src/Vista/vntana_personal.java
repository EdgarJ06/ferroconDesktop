/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_pais;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Modelo.Ciudad;
import Modelo.Permisos;
import Modelo.Personal;
import Modelo.Validaciones;
import Modelo.tipo_usuario;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Edgar
 */
public class vntana_personal extends javax.swing.JDialog {

    Personal per = new Personal();
    Permisos perm = new Permisos();
    reg_personal rp = new reg_personal();
    reg_pais rc = new reg_pais();
    reg_permisos rperm = new reg_permisos();
    int save_update = 0;
    ArrayList<Personal> listPers;
    ArrayList<Ciudad> listCiu;
    ArrayList<tipo_usuario> lisUser;
    boolean delet = false;
    boolean update = false;
    int cod_dis, id_ima;
    String passw;
    String ced;

    public vntana_personal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());
        bloquearCampos(false);
        ced = menu_principal.ced_usu;
        loadPermisos(ced);
        cargarComboTipUser();
        loadPermisos_us(ced);
        // pnlPermisos.setEnabled(false);
        load_personal(rp.bus_user_ced(ced));//usuario activo
    }

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_delete() && per.isPer_regist() && per.isPer_udate()) {
                blockBotones(true);
                //  pnlPermisos.setEnabled(true);
            } else {
                blockBotones(false);
                btnactualizar.setEnabled(true);
            }
        }
    }

    public void blockBotones(boolean ok) {
        btn_new.setEnabled(ok);
        btn_save.setEnabled(ok);
        btnactualizar.setEnabled(ok);
        btn_delete.setEnabled(ok);
        panel_adm.setVisible(ok);
        pnlPermisos.setEnabled(ok);
        checReg.setEnabled(ok);
        cheEdit.setEnabled(ok);
        cheDelete.setEnabled(ok);
    }

    public void cargarComboCiudad() {
        listCiu = rc.CargarListCiu("");
        combo_ciu_us.removeAllItems();
        combo_ciu_us.addItem("");
        for (Ciudad c : listCiu) {
            combo_ciu_us.addItem(c.getNom_ciu());
        }
    }

    public void cargarComboTipUser() {
        lisUser = rp.tipUser("");
        combo_tip_us.removeAllItems();
        combo_tip_us.addItem("");
        for (tipo_usuario tu : lisUser) {
            combo_tip_us.addItem(tu.getDescr());
        }
    }

    public void pinta_text() {
        Validaciones.pinta_text(text_cod_us);
        Validaciones.pinta_text(text_ced_us);
        Validaciones.pinta_text(text_nom_us);
        Validaciones.pinta_text(text_ape_us);
        Validaciones.pinta_text(text_dir_us);
        Validaciones.pinta_text(text_telf_us);
        Validaciones.pinta_text(text_email_us);
        Validaciones.pinta_Combo(combo_tip_us);
        Validaciones.pinta_Combo(combo_ciu_us);
        Validaciones.pinta_text(passAct);

    }

    public void bloquearCampos(boolean ok) {
        text_cod_us.setEditable(ok);
        text_ced_us.setEditable(ok);
        text_nom_us.setEditable(ok);
        text_ape_us.setEditable(ok);
        text_dir_us.setEditable(ok);
        text_telf_us.setEditable(ok);
        text_email_us.setEditable(ok);
        combo_tip_us.setEditable(ok);
        combo_ciu_us.setEditable(ok);

    }

    public boolean compararPassward() {
        boolean ok = false;
        String pasw = new String(passward.getPassword());
        String pasw1 = new String(passward2.getPassword());
        System.out.println(pasw + "  " + pasw1);
        if (pasw.equals(pasw1) && !pasw.equals("")) {
            ok = true;
        }
        return ok;
    }

    public String contrasenia() {//convertir passward
        String pasw = new String(passward.getPassword());
        return pasw;
    }

    public boolean form_validado() {
        boolean ok = true;

        if (!Validaciones.esCedula(text_ced_us)) {
            ok = false;
        }

        if (!Validaciones.esLetras(text_nom_us)) {
            ok = false;
        }

        if (!Validaciones.esLetras(text_ape_us)) {
            ok = false;
        }
        if (!Validaciones.esEmail(text_email_us)) {
            ok = false;
        }

        if (!Validaciones.esRequerido(text_dir_us)) {
            ok = false;
        }

        if (!Validaciones.esTelefono(text_telf_us)) {
            ok = false;
        }
        if (!Validaciones.es_Combo(combo_tip_us)) {
            ok = false;
        }
        if (!Validaciones.es_Combo(combo_ciu_us)) {
            ok = false;
        }
        return ok;
    }

    //sql
    public void load_personal(String sql) {//cargar tabla clientes
        cargarComboCiudad();
        cargarComboTipUser();
        listPers = rp.user_active(sql);
        for (Personal per : listPers) {
            text_cod_us.setText(per.getCod_usua() + "");
            text_ced_us.setText(per.getCed());
            text_nom_us.setText(per.getNombre());
            text_ape_us.setText(per.getApellido());
            text_dir_us.setText(per.getDireccion());
            text_telf_us.setText(per.getTelefono());
            text_email_us.setText(per.getCorreo_elect());
            combo_tip_us.setSelectedItem(rp.loadDescTipUser(per.getTip_usua() + ""));
            combo_ciu_us.setSelectedItem(rc.ObtNomCiu(per.getCod_ciu() + ""));
            passw = per.getPassword();
            passAct.setText(per.getPassword());
            //limpiar chechbox

            loadPermisos_us(per.getCed());//activar los checbox
            if (per.getTip_usua() == 1) {//si es administrador
                panel_adm.setVisible(true);
                btn_new.setEnabled(true);
            }
            loadPermisos(per.getCed());
        }
    }

    ArrayList<Permisos> listPerm;

    public boolean isPasswordCorrect() {
        String dato = new String(passAct.getPassword());
        boolean ok = false;
        if (dato.equals(passw)) {
            ok = true;
        }
        return ok;
    }

    public void loadPermisos_us(String dato) {
        checReg.setSelected(false);
        cheEdit.setSelected(false);
        cheDelete.setSelected(false);
        listPerm = rperm.cargar_permisos(dato);
        for (Permisos per : listPerm) {
            cod_dis = per.getCod_disc();
            id_ima = per.getId_ima();
            checReg.setSelected(per.isPer_regist());
            cheEdit.setSelected(per.isPer_udate());
            cheDelete.setSelected(per.isPer_delete());
        }
    }

    public void update() {
        if (form_validado()) {
            if (compararPassward()) {
                per.setCod_usua(Integer.parseInt(text_cod_us.getText()));
                per.setTip_usua(rp.loadCodTipUser(combo_tip_us.getSelectedItem().toString()));
                per.setPassword(contrasenia());
                per.setCed(text_ced_us.getText());
                per.setNombre(text_nom_us.getText());
                per.setApellido(text_ape_us.getText());
                per.setDireccion(text_dir_us.getText());
                per.setTelefono(text_telf_us.getText());
                per.setCorreo_elect(text_email_us.getText());
                per.setCod_ciu(rc.ObtCodCiu(combo_ciu_us.getSelectedItem().toString()));
                per.setEstado(true);
                rp.update_customer(per);
                UpedatePermisos();
                limpiarCombos();
                JOptionPane.showMessageDialog(null, "Usuario Actualizado");
            } else {
                JOptionPane.showMessageDialog(null, "Contrasenas incorrectas");
            }
        }
    }

    public void save() {
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
                limpiarCombos();
                JOptionPane.showMessageDialog(null, "Usuario registrado");
            } else {
                JOptionPane.showMessageDialog(null, "Contrasenas incorrectas");
            }
        }
    }

    //Permisos de usuario
    public void savePermisos() {
        perm.setId_permisos(Integer.parseInt(text_cod_us.getText()));
        perm.setCed_usua(text_ced_us.getText());
        perm.setPer_delete(cheDelete.isSelected());
        perm.setPer_regist(checReg.isSelected());
        perm.setPer_udate(cheEdit.isSelected());
        //por defecto
        perm.setCod_disc(4);
        perm.setId_ima(1);
        rperm.save_Permisos(perm);

    }

    //Editar permisos
    public void UpedatePermisos() {
        perm.setId_permisos(Integer.parseInt(text_cod_us.getText()));
        perm.setCed_usua(text_ced_us.getText());
        perm.setPer_delete(cheDelete.isSelected());
        perm.setPer_regist(checReg.isSelected());
        perm.setPer_udate(cheEdit.isSelected());
        // JOptionPane.showMessageDialog(this, id_ima);

        perm.setCod_disc(cod_dis);
        perm.setId_ima(id_ima);

        rperm.update_Permisos(perm);
    }

    public void limpiarCombos() {
        text_cod_us.setText("");
        text_ced_us.setText("");
        text_nom_us.setText("");
        text_ape_us.setText("");
        text_dir_us.setText("");
        text_telf_us.setText("");
        text_email_us.setText("");
        combo_tip_us.setSelectedItem("");
        combo_ciu_us.setSelectedItem(rc.ObtNomCiu(""));
        //limpiar chec
        checReg.setSelected(false);
        cheEdit.setSelected(false);
        cheDelete.setSelected(false);
        //limpiar contrasenias
        passAct.setText("");
        passward.setText("");
        passward2.setText("");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnel_all = new javax.swing.JPanel();
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
        combo_tip_us = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        btn_save = new javax.swing.JButton();
        btn_ciu = new javax.swing.JButton();
        pnlPermisos = new javax.swing.JPanel();
        cheDelete = new javax.swing.JCheckBox();
        cheEdit = new javax.swing.JCheckBox();
        checReg = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        passward = new javax.swing.JPasswordField();
        passward2 = new javax.swing.JPasswordField();
        jLabel17 = new javax.swing.JLabel();
        passAct = new javax.swing.JPasswordField();
        panel_adm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        text_buscar = new javax.swing.JTextField();
        btn_busAll = new javax.swing.JButton();
        tbar = new javax.swing.JToolBar();
        btn_new = new javax.swing.JButton();
        btnactualizar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Usuarios");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnel_all.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_all.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel8.setText("Nombre Usuario :");
        pnel_all.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, 20));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel2.setText("Código:");
        pnel_all.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 19, -1, 20));

        text_cod_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cod_us.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_us.setEnabled(false);
        pnel_all.add(text_cod_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 70, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel4.setText("Apellido:");
        pnel_all.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel10.setText("Télefono:");
        pnel_all.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 20));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel9.setText("Tipo de usuario:");
        pnel_all.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, -1, 20));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel5.setText("Ciudad:");
        pnel_all.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 20));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel11.setText("Nombre:");
        pnel_all.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 20));

        combo_ciu_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        combo_ciu_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_ciu_usActionPerformed(evt);
            }
        });
        combo_ciu_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combo_ciu_usKeyReleased(evt);
            }
        });
        pnel_all.add(combo_ciu_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 360, -1));

        text_ced_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_ced_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ced_usKeyReleased(evt);
            }
        });
        pnel_all.add(text_ced_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 210, -1));

        text_ape_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_ape_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ape_usKeyReleased(evt);
            }
        });
        pnel_all.add(text_ape_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 410, -1));

        text_nom_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_nom_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_nom_usKeyReleased(evt);
            }
        });
        pnel_all.add(text_nom_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 410, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel12.setText("Dirección:");
        pnel_all.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

        text_telf_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_telf_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_telf_usKeyReleased(evt);
            }
        });
        pnel_all.add(text_telf_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 410, -1));

        text_dir_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_dir_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_dir_usKeyReleased(evt);
            }
        });
        pnel_all.add(text_dir_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 400, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel14.setText("Email:");
        pnel_all.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, 20));

        text_email_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_email_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_email_usKeyReleased(evt);
            }
        });
        pnel_all.add(text_email_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 200, -1));

        combo_tip_us.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        combo_tip_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tip_usActionPerformed(evt);
            }
        });
        combo_tip_us.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combo_tip_usKeyReleased(evt);
            }
        });
        pnel_all.add(combo_tip_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 110, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel15.setText("Contraseña Actual:");
        pnel_all.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        btn_save.setBackground(new java.awt.Color(0, 204, 153));
        btn_save.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_save.setText("Guardar");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });
        pnel_all.add(btn_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 130, -1));

        btn_ciu.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_ciu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btn_ciu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ciuActionPerformed(evt);
            }
        });
        pnel_all.add(btn_ciu, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 40, -1));

        pnlPermisos.setBorder(javax.swing.BorderFactory.createTitledBorder("Asignar Permisos"));

        cheDelete.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        cheDelete.setText("Eliminar");

        cheEdit.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        cheEdit.setText("Editar");

        checReg.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        checReg.setText("Registrar");

        javax.swing.GroupLayout pnlPermisosLayout = new javax.swing.GroupLayout(pnlPermisos);
        pnlPermisos.setLayout(pnlPermisosLayout);
        pnlPermisosLayout.setHorizontalGroup(
            pnlPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPermisosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checReg)
                .addGap(18, 18, 18)
                .addComponent(cheEdit)
                .addGap(18, 18, 18)
                .addComponent(cheDelete)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        pnlPermisosLayout.setVerticalGroup(
            pnlPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPermisosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checReg)
                    .addComponent(cheEdit)
                    .addComponent(cheDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnel_all.add(pnlPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 470, 60));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel16.setText("Nueva Contraseña:");
        pnel_all.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, -1, -1));

        passward.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        pnel_all.add(passward, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 160, 20));

        passward2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        pnel_all.add(passward2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 160, 20));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel17.setText("Contraseña:");
        pnel_all.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, -1, -1));

        passAct.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        passAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passActKeyReleased(evt);
            }
        });
        pnel_all.add(passAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 160, -1));

        getContentPane().add(pnel_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 500, 460));

        panel_adm.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel1.setText("Usuario :");

        text_buscar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N

        btn_busAll.setBackground(new java.awt.Color(0, 153, 102));
        btn_busAll.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_busAll.setForeground(new java.awt.Color(255, 255, 255));
        btn_busAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lupa.png"))); // NOI18N
        btn_busAll.setText("Buscar");
        btn_busAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_admLayout = new javax.swing.GroupLayout(panel_adm);
        panel_adm.setLayout(panel_admLayout);
        panel_admLayout.setHorizontalGroup(
            panel_admLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_admLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addGap(47, 47, 47)
                .addComponent(btn_busAll, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panel_admLayout.setVerticalGroup(
            panel_admLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_admLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_admLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panel_admLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_admLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(text_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_busAll)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panel_adm, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 500, 50));

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        btn_new.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new.setText("Nuevo");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        tbar.add(btn_new);

        btnactualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnactualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btnactualizar.setText("Actualizar");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });
        tbar.add(btnactualizar);

        btn_delete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.setText("Eliminar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        tbar.add(btn_delete);

        getContentPane().add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 500, 40));

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Personal");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(438, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_busAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busAllActionPerformed
        // TODO add your handling code here:
        if (!text_buscar.getText().equals("")) {
            load_personal(rp.bus_user_ced(text_buscar.getText()));//Buscar
            Validaciones.pinta_text(passAct);
            //passAct.setText("");
            passward.setText("");
            passward2.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese ID de Usuario");
        }
    }//GEN-LAST:event_btn_busAllActionPerformed

    private void text_ced_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ced_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esCedula(text_ced_us)) {
            Validaciones.pinta_text(text_ced_us);
        }
    }//GEN-LAST:event_text_ced_usKeyReleased

    private void text_nom_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_nom_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esLetras(text_nom_us)) {
            Validaciones.pinta_text(text_nom_us);
        }
    }//GEN-LAST:event_text_nom_usKeyReleased

    private void text_ape_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ape_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esLetras(text_ape_us)) {
            Validaciones.pinta_text(text_ape_us);
        }
    }//GEN-LAST:event_text_ape_usKeyReleased

    private void text_dir_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dir_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_dir_us)) {
            Validaciones.pinta_text(text_dir_us);
        }
    }//GEN-LAST:event_text_dir_usKeyReleased

    private void text_telf_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_telf_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esTelefono(text_telf_us)) {
            Validaciones.pinta_text(text_telf_us);
        }
    }//GEN-LAST:event_text_telf_usKeyReleased

    private void text_email_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_email_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esEmail(text_email_us)) {
            Validaciones.pinta_text(text_email_us);
        }
    }//GEN-LAST:event_text_email_usKeyReleased

    private void btn_ciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ciuActionPerformed
        //guardar
        vntana_pais dlg = new vntana_pais(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_btn_ciuActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        //cargar el nuevo usuario
        load_personal(rp.bus_user_ced(ced));//usuario activo
        limpiarCombos();

        text_cod_us.setText(rp.cod_user() + "");//codigo del personal
        cargarComboCiudad();
        cargarComboTipUser();
        btn_save.setEnabled(true);
        save_update = 1;
        bloquearCampos(true);
        pinta_text();


    }//GEN-LAST:event_btn_newActionPerformed

    public void aux() {
        btn_save.setEnabled(false);
        bloquearCampos(false);
        // load_personal(1 + "");

    }

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed

        if (isPasswordCorrect()) {

            if (save_update == 1) {
                save();
                aux();
            }
            if (save_update == 2) {
                update();

            }
        } else {
            JOptionPane.showMessageDialog(this, "Contraseña Actual no es correcta");
        }

    }//GEN-LAST:event_btn_saveActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        // TODO add your handling code here:
        save_update = 2;
        btn_save.setEnabled(true);
        //desactivar los combos
        bloquearCampos(true);

    }//GEN-LAST:event_btnactualizarActionPerformed

    private void passActKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passActKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_passward_True(passAct, passw)) {
            Validaciones.pinta_text(passAct);
        }
    }//GEN-LAST:event_passActKeyReleased

    private void combo_ciu_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_ciu_usActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(combo_ciu_us);

    }//GEN-LAST:event_combo_ciu_usActionPerformed

    private void combo_tip_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tip_usActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(combo_tip_us);

    }//GEN-LAST:event_combo_tip_usActionPerformed

    private void combo_ciu_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_ciu_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_Combo(combo_ciu_us)) {
            Validaciones.pinta_Combo(combo_ciu_us);
        }
    }//GEN-LAST:event_combo_ciu_usKeyReleased

    private void combo_tip_usKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_tip_usKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_Combo(combo_tip_us)) {
            Validaciones.pinta_Combo(combo_tip_us);
        }
    }//GEN-LAST:event_combo_tip_usKeyReleased

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
            java.util.logging.Logger.getLogger(vntana_personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_personal dialog = new vntana_personal(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_busAll;
    private javax.swing.JButton btn_ciu;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btnactualizar;
    private javax.swing.JCheckBox cheDelete;
    private javax.swing.JCheckBox cheEdit;
    private javax.swing.JCheckBox checReg;
    private javax.swing.JComboBox combo_ciu_us;
    private javax.swing.JComboBox combo_tip_us;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panel_adm;
    private javax.swing.JPasswordField passAct;
    private javax.swing.JPasswordField passward;
    private javax.swing.JPasswordField passward2;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnlPermisos;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_ape_us;
    private javax.swing.JTextField text_buscar;
    private javax.swing.JTextField text_ced_us;
    private javax.swing.JTextField text_cod_us;
    private javax.swing.JTextField text_dir_us;
    private javax.swing.JTextField text_email_us;
    private javax.swing.JTextField text_nom_us;
    private javax.swing.JTextField text_telf_us;
    // End of variables declaration//GEN-END:variables
}
