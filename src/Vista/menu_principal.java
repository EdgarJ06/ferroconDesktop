package Vista;

import Controlador.reg_personal;
import Modelo.Personal;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Edgar
 */
public class menu_principal extends javax.swing.JFrame {

    /**
     * Creates new form menu_principal
     */
    reg_personal rp = new reg_personal();
    ArrayList<Personal> listuser;
    public static String ced_usu;

    public menu_principal() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());
        ced_usu = login.ced_usuario;//
        load_user(ced_usu);

    }

    public void load_user(String ced) {
        listuser = rp.user_active(rp.load_user_active(ced, 1 + ""));
        for (Personal us : listuser) {
            ced_usu = us.getCed();
            label_idUsu.setText(rp.loadDescTipUser(us.getTip_usua() + "") + " : " + us.getNombre() + "  " + us.getApellido() + " | " + "Usuario :" + us.getCed());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tbar = new javax.swing.JToolBar();
        btnCli = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btn_vntas = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btn_prod = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btn_prove = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        bnt_cmpras = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        pnelinf = new javax.swing.JPanel();
        label_idUsu = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        smenu_starSesion = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        menu_reg = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        menu_oper = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        menu_merc = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        menu_conf = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Principal");
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        tbar.setBackground(new java.awt.Color(255, 153, 0));
        tbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCli.setBackground(new java.awt.Color(255, 153, 0));
        btnCli.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCli.setForeground(new java.awt.Color(255, 255, 255));
        btnCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/clientes_1.png"))); // NOI18N
        btnCli.setText("Clientes");
        btnCli.setBorder(null);
        btnCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliActionPerformed(evt);
            }
        });
        tbar.add(btnCli);
        tbar.add(jSeparator5);

        btn_vntas.setBackground(new java.awt.Color(255, 153, 0));
        btn_vntas.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_vntas.setForeground(new java.awt.Color(255, 255, 255));
        btn_vntas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/punto-de-venta.png"))); // NOI18N
        btn_vntas.setText("Ventas");
        btn_vntas.setBorder(null);
        btn_vntas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vntasActionPerformed(evt);
            }
        });
        tbar.add(btn_vntas);
        tbar.add(jSeparator4);

        btn_prod.setBackground(new java.awt.Color(255, 153, 0));
        btn_prod.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_prod.setForeground(new java.awt.Color(255, 255, 255));
        btn_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/caja.png"))); // NOI18N
        btn_prod.setText("Producto");
        btn_prod.setBorder(null);
        btn_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prodActionPerformed(evt);
            }
        });
        tbar.add(btn_prod);
        tbar.add(jSeparator3);

        btn_prove.setBackground(new java.awt.Color(255, 153, 0));
        btn_prove.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_prove.setForeground(new java.awt.Color(255, 255, 255));
        btn_prove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedor.png"))); // NOI18N
        btn_prove.setText("Proveedores");
        btn_prove.setBorder(null);
        btn_prove.setFocusable(false);
        btn_prove.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btn_prove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveActionPerformed(evt);
            }
        });
        tbar.add(btn_prove);
        tbar.add(jSeparator2);

        bnt_cmpras.setBackground(new java.awt.Color(255, 153, 0));
        bnt_cmpras.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        bnt_cmpras.setForeground(new java.awt.Color(255, 255, 255));
        bnt_cmpras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/carros.png"))); // NOI18N
        bnt_cmpras.setText("Compras");
        bnt_cmpras.setBorder(null);
        bnt_cmpras.setFocusable(false);
        bnt_cmpras.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bnt_cmpras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bnt_cmpras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_cmprasActionPerformed(evt);
            }
        });
        tbar.add(bnt_cmpras);
        tbar.add(jSeparator1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ferrocon.jpg"))); // NOI18N

        pnelinf.setBackground(new java.awt.Color(51, 51, 51));

        label_idUsu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        label_idUsu.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnelinfLayout = new javax.swing.GroupLayout(pnelinf);
        pnelinf.setLayout(pnelinfLayout);
        pnelinfLayout.setHorizontalGroup(
            pnelinfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnelinfLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_idUsu, javax.swing.GroupLayout.DEFAULT_SIZE, 1344, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnelinfLayout.setVerticalGroup(
            pnelinfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnelinfLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_idUsu, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1356, Short.MAX_VALUE)
            .addComponent(pnelinf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1356, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 538, Short.MAX_VALUE)
                .addComponent(pnelinf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(77, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(80, Short.MAX_VALUE)))
        );

        jMenuBar1.setBackground(new java.awt.Color(60, 63, 65));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("Inicio");

        smenu_starSesion.setText("Iniciar  Sesion");
        smenu_starSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smenu_starSesionActionPerformed(evt);
            }
        });
        jMenu1.add(smenu_starSesion);

        jMenuItem14.setText("Salir");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem14);

        jMenuBar1.add(jMenu1);

        menu_reg.setForeground(new java.awt.Color(255, 255, 255));
        menu_reg.setText("Registros");

        jMenuItem4.setText("Clientes");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        menu_reg.add(jMenuItem4);

        jMenuItem12.setText("Proveedores");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        menu_reg.add(jMenuItem12);

        jMenuItem13.setText("Usuarios");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        menu_reg.add(jMenuItem13);

        jMenuItem5.setText("Ciudad");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        menu_reg.add(jMenuItem5);

        jMenuItem15.setText("Empresa");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        menu_reg.add(jMenuItem15);

        jMenuBar1.add(menu_reg);

        menu_oper.setForeground(new java.awt.Color(255, 255, 255));
        menu_oper.setText("Operaciones");

        jMenuItem6.setText("Ventas");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        menu_oper.add(jMenuItem6);

        jMenuItem7.setText("Pedidos");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        menu_oper.add(jMenuItem7);

        jMenuItem2.setText("Ver Compras");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menu_oper.add(jMenuItem2);

        jMenuItem26.setText("Ver ventas");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        menu_oper.add(jMenuItem26);

        jMenuItem20.setText("Gastos");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        menu_oper.add(jMenuItem20);

        jMenuBar1.add(menu_oper);

        menu_merc.setForeground(new java.awt.Color(255, 255, 255));
        menu_merc.setText("Mercaderia");

        jMenuItem3.setText("Productos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menu_merc.add(jMenuItem3);

        jMenuItem16.setText("Marcas");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        menu_merc.add(jMenuItem16);

        jMenuItem17.setText("Tipo de Material");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        menu_merc.add(jMenuItem17);

        jMenuItem1.setText("Inventario");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_merc.add(jMenuItem1);

        jMenuItem27.setText("Ajuste de Inventario");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        menu_merc.add(jMenuItem27);

        jMenuBar1.add(menu_merc);

        menu_conf.setForeground(new java.awt.Color(255, 255, 255));
        menu_conf.setText("Configuraciones");

        jMenuItem9.setText("Configurar Iva");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        menu_conf.add(jMenuItem9);

        jMenuBar1.add(menu_conf);

        jMenu2.setForeground(new java.awt.Color(255, 255, 255));
        jMenu2.setText("Caja");

        jMenuItem24.setText("Apertura de Caja");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem24);

        jMenuItem25.setText("Cierre de Caja");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem25);

        jMenuItem10.setText("Caja");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);

        jMenuItem23.setText("Reporte de Caja");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem23);

        jMenuBar1.add(jMenu2);

        jMenu5.setForeground(new java.awt.Color(255, 255, 255));
        jMenu5.setText("Ayuda");

        jMenuItem11.setText("Calculadora");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenuItem18.setText("Calendario");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem18);

        jMenuItem19.setText("Blog de Notas");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_vntasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vntasActionPerformed
        // TODO add your handling code here:
        new vntana_Venta(this, false).setVisible(true);

    }//GEN-LAST:event_btn_vntasActionPerformed

    private void btn_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prodActionPerformed
        // TODO add your handling code here:

        new vntana_productos(this, false).setVisible(true);

    }//GEN-LAST:event_btn_prodActionPerformed

    private void btn_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveActionPerformed
        // TODO add your handling code here:
        new vntana_proveedores(this, false).setVisible(true);
    }//GEN-LAST:event_btn_proveActionPerformed

    private void btnCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliActionPerformed

        new vntana_clientes(this, false).setVisible(true);

        /*  vntana_clientes dlg = new vntana_clientes(null, false);
         dlg.setVisible(true);*/

    }//GEN-LAST:event_btnCliActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        vntana_personal dlg = new vntana_personal(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);

        /// new vntana_personal(this, false).setVisible(true);
        load_user(ced_usu);


    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:        
        new vntana_pedidos(this, false).setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new vntana_pais(this, false).setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        new vntana_marca(this, false).setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        new vntana_tipos(this, false).setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        new vntana_productos(this, false).setVisible(true);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        new vntana_Venta(this, false).setVisible(true);


    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void bnt_cmprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_cmprasActionPerformed
        // TODO add your handling code here:
        new vntana_pedidos(this, false).setVisible(true);
    }//GEN-LAST:event_bnt_cmprasActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        new vntana_clientes(this, false).setVisible(true);


    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        new vntana_proveedores(this, false).setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        rp.edit_estado_us(ced_usu, 0);
        System.exit(0);


    }//GEN-LAST:event_jMenuItem14ActionPerformed


    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        vntana_inventario dlg = new vntana_inventario(null, false);
        // dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        try {
            Runtime obj = Runtime.getRuntime();

            //La instrucción es notepad para abrir el bloc de notas, espacio
            //y la ruta donde esta el archivo}
            obj.exec("notepad ");//ABRE UN BLOG DE NOTAS

            //obj.exec("notepad "+System.getProperty("user.dir")+"\\Archivo.txt");
            //Pueden usar System.getProperty("user.dir") Obtiene la ruta del proyecto
        } catch (IOException ex) {
            System.out.println("IOException " + ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        vntana_ver_cmpras dlg = new vntana_ver_cmpras(null, false);
       // dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    private void smenu_starSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smenu_starSesionActionPerformed
        login lg = new login();
        // block(false);
        // loggin();
        //Cerrar cesion actual
        rp.edit_estado_us(ced_usu, 0);//salir de la cuenta 
        lg.setVisible(true);
        this.setVisible(false);

        // this.setEnabled(false);
        //  login.setVisible(true);

    }//GEN-LAST:event_smenu_starSesionActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        Process runtimeProcess;
        try {

            System.out.println(System.getProperty("os.name"));
            String comandoCalculadora = System.getProperty("os.name").startsWith("Windows") ? "calc" : "gcalctool";
            runtimeProcess = Runtime.getRuntime().exec(comandoCalculadora);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        vntana_iva dlg = new vntana_iva(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        vntana_caja dlg = new vntana_caja(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        vntana_apertura_caja dlg = new vntana_apertura_caja(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        vntana_cierre_caja dlg = new vntana_cierre_caja(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed

        /*    vntana_empresa dlg = new vntana_empresa(null, false);
         dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
         dlg.setVisible(true);*/
        new vntana_empresa(this, false).setVisible(true);


    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        vntana_ver_ventas dlg = new vntana_ver_ventas(null, false);
        // dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:

        vntana_gastos dlg = new vntana_gastos(null, false);
        // dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);


    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:

        try {
            JOptionPane.showMessageDialog(null, "Imprimiendo Reporte ......");
            Reportes_g.generarReporte("rep_caja");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No existen datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:

        vntana_ajust_invent dlg = new vntana_ajust_invent(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem27ActionPerformed

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
            java.util.logging.Logger.getLogger(menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnt_cmpras;
    private javax.swing.JButton btnCli;
    private javax.swing.JButton btn_prod;
    private javax.swing.JButton btn_prove;
    private javax.swing.JButton btn_vntas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JLabel label_idUsu;
    private javax.swing.JMenu menu_conf;
    private javax.swing.JMenu menu_merc;
    private javax.swing.JMenu menu_oper;
    private javax.swing.JMenu menu_reg;
    private javax.swing.JPanel pnelinf;
    private javax.swing.JMenuItem smenu_starSesion;
    private javax.swing.JToolBar tbar;
    // End of variables declaration//GEN-END:variables
}
