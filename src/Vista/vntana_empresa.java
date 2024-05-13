/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_empresa;
import Controlador.reg_permisos;
import Modelo.Permisos;
import Modelo.Validaciones;
import Modelo.empresa;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Edgar
 */
public class vntana_empresa extends javax.swing.JDialog {

    /**
     * Creates new form vntana_empresa
     */
    reg_empresa rem = new reg_empresa();
    empresa em = new empresa();
    String cod_emp;
    boolean delet = false;
    boolean update = false;
    String ced;
   
  

    public vntana_empresa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);//Centrar ventana
        try {
            blockBotones(false);//desactivado todos los botones 
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            campos(false);
            bloquearColumTable("");
            text_cont.setText(rem.count() + "");
        } catch (Exception err) {
            System.out.println("Aqui el error:" + err);
        }
         
         
    }
    ArrayList<Permisos> listPerm;
    reg_permisos rperm = new reg_permisos();

    // reg_personal rpersonal = new reg_personal();

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {
            if (per.isPer_regist()) {
                btn_nuevo.setEnabled(true);
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

  

    public void bloquearColumTable(String dato) {//edicion de la tabla
        try {
            load_table(dato);
            modelo_tabla();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    DefaultTableModel datos;
    ArrayList<empresa> listem;

    public void load_table(String dato) {//cargar tabla clientes
        datos = rem.load_label_empresa();
        Object[] data = new Object[8];
        listem = rem.llenar_empresa(rem.sql_load_empresa(dato));
        for (empresa em : listem) {
            data[0] = em.getCod_empresa();
            data[1] = em.getNom_empresa();
            data[2] = em.getRuc();
            data[3] = em.getNom_representante();
            data[4] = em.getTelf();
            data[5] = em.getDireccion();
            data[6] = em.getEmail();
            data[7] = em.isEstado();
            datos.addRow(data);
            tabla_em.setModel(datos);
        }
    }

    public void modelo_tabla() {
        tabla_em.getColumnModel().getColumn(0).setPreferredWidth(1);
        tabla_em.getColumnModel().getColumn(1).setPreferredWidth(30);
        tabla_em.getColumnModel().getColumn(2).setPreferredWidth(50);
        tabla_em.getColumnModel().getColumn(3).setPreferredWidth(50);
        tabla_em.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla_em.getColumnModel().getColumn(5).setPreferredWidth(50);
        tabla_em.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabla_em.getColumnModel().getColumn(6).setPreferredWidth(50);
        tabla_em.getTableHeader().setBackground(Color.decode("#666666"));
         tabla_em.getTableHeader().setForeground(Color.WHITE);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tabla_em.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public void blockBotones(boolean ok) {
        btn_nuevo.setEnabled(ok);
        btn_agregar.setEnabled(ok);
        btn_actualizar.setEnabled(ok);
        btn_delete.setEnabled(ok);
    }

    public void pinta_text() {
        Validaciones.pinta_text(text_nom_empresa);
        Validaciones.pinta_text(text_ced_ruc);
        Validaciones.pinta_text(text_nom_re);
        Validaciones.pinta_text(text_dir_em);
        Validaciones.pinta_text(text_telf_em);
        Validaciones.pinta_text(text_correo_em);
    }

    public void limpiar_campos() {
        text_nom_empresa.setText("");
        text_ced_ruc.setText("");
        text_nom_re.setText("");
        text_dir_em.setText("");
        text_telf_em.setText("");
        text_correo_em.setText("");
        checEstado.setSelected(false); 
        pinta_text();
    }

    public void campos(boolean ok) {
       checEstado.setEnabled(ok);
        text_nom_empresa.setEnabled(ok);
        text_ced_ruc.setEnabled(ok);
        text_nom_re.setEnabled(ok);
        text_dir_em.setEnabled(ok);
        text_telf_em.setEnabled(ok);
        text_correo_em.setEnabled(ok);
        pinta_text();
    }

    public boolean form_validado() {
        boolean ok = true;
        String men = "Campos requeridos o con errores: ";
        if (!Validaciones.esCedula(text_ced_ruc)) {
            ok = false;
            men += "Ruc-Ci ";
        }

        if (!Validaciones.esLetras(text_nom_re)) {
            ok = false;
            men += "Nombre ";
        }
        if (!Validaciones.esRequerido(text_dir_em)) {
            ok = false;
            men += "Apellido ";
        }

        if (!Validaciones.esRequerido(text_nom_empresa)) {
            ok = false;

        }
        if (!Validaciones.esTelefono(text_telf_em)) {
            ok = false;
            men += "Telefono ";
        }
        if (!Validaciones.esEmail(text_correo_em)) {
            ok = false;
            men += "Telefono ";
        }
        return ok;
    }

    public void botones(boolean ok, boolean ok1) {
        btn_agregar.setEnabled(ok);
        btn_actualizar.setEnabled(ok1);//true
        btn_delete.setEnabled(ok1);//true
    }

    public boolean actBotones(boolean upda, boolean delet) {
        btn_actualizar.setEnabled(upda);//true
        btn_delete.setEnabled(delet);//true
        return true;
    }

    public void update_empr() {
        try {
            em.setCod_empresa(Integer.parseInt(cod_emp));
            em.setNom_empresa(text_nom_empresa.getText());
            em.setRuc(text_ced_ruc.getText());
            em.setNom_representante(text_nom_re.getText());
            em.setDireccion(text_dir_em.getText());
            em.setTelf(text_telf_em.getText());
            em.setEmail(text_correo_em.getText());
            em.setEstado(checEstado.isSelected());
            rem.update(em);//guardar los datos
            botones(false, false);
            limpiar_campos();
            bloquearColumTable("");
            campos(false);
        } catch (Throwable ex) {
            System.out.println(ex);
        }
    }

    public void delete() {
        em.setCod_empresa(Integer.parseInt(cod_emp));
        rem.Eliminar(em);//guardar los datos        
        limpiar_campos();
        campos(false);
        bloquearColumTable("");
        botones(false, false);
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
        btn_nuevo = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnel_inf = new javax.swing.JPanel();
        lblced = new javax.swing.JLabel();
        text_correo_em = new javax.swing.JTextField();
        text_nom_empresa = new javax.swing.JTextField();
        lblemail = new javax.swing.JLabel();
        lblnom = new javax.swing.JLabel();
        text_ced_ruc = new javax.swing.JTextField();
        text_nom_re = new javax.swing.JTextField();
        lbldir = new javax.swing.JLabel();
        text_telf_em = new javax.swing.JTextField();
        text_dir_em = new javax.swing.JTextField();
        lbltelf = new javax.swing.JLabel();
        lblcod = new javax.swing.JLabel();
        checEstado = new javax.swing.JCheckBox();
        lbltelf1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_em = new javax.swing.JTable();
        btnbusTodo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblbuscli = new javax.swing.JLabel();
        text_busc_em = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        text_cont = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empresa");

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        tbar.add(btn_nuevo);

        btn_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        tbar.add(btn_agregar);

        btn_actualizar.setForeground(new java.awt.Color(255, 255, 255));
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        tbar.add(btn_actualizar);

        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        tbar.add(btn_cancelar);

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        tbar.add(btn_delete);
        tbar.add(jSeparator1);

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Empresas");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(773, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        pnel_inf.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblced.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblced.setText("NIT:");
        pnel_inf.add(lblced, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, 20));

        text_correo_em.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_correo_emKeyReleased(evt);
            }
        });
        pnel_inf.add(text_correo_em, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 360, -1));

        text_nom_empresa.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_nom_empresa.setEnabled(false);
        pnel_inf.add(text_nom_empresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 320, -1));

        lblemail.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblemail.setText("Email:");
        pnel_inf.add(lblemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 40, 20));

        lblnom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblnom.setText("Representante:");
        pnel_inf.add(lblnom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        text_ced_ruc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_ced_rucFocusLost(evt);
            }
        });
        text_ced_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ced_rucKeyReleased(evt);
            }
        });
        pnel_inf.add(text_ced_ruc, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 150, -1));

        text_nom_re.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_nom_reFocusLost(evt);
            }
        });
        text_nom_re.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_nom_reKeyReleased(evt);
            }
        });
        pnel_inf.add(text_nom_re, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 280, -1));

        lbldir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbldir.setText("Dirección:");
        pnel_inf.add(lbldir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        text_telf_em.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_telf_emFocusLost(evt);
            }
        });
        text_telf_em.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_telf_emKeyReleased(evt);
            }
        });
        pnel_inf.add(text_telf_em, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 140, -1));

        text_dir_em.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_dir_emFocusLost(evt);
            }
        });
        text_dir_em.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_dir_emKeyReleased(evt);
            }
        });
        pnel_inf.add(text_dir_em, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 650, -1));

        lbltelf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbltelf.setText("Activar:");
        pnel_inf.add(lbltelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, -1, 20));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Nombre:");
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));
        pnel_inf.add(checEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 70, -1, -1));

        lbltelf1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbltelf1.setText("Télefono:");
        pnel_inf.add(lbltelf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, 20));

        tabla_em.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla_em.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_emMouseClicked(evt);
            }
        });
        tabla_em.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_emKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabla_em);

        btnbusTodo.setBackground(new java.awt.Color(0, 153, 102));
        btnbusTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnbusTodo.setText("Todo");
        btnbusTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusTodoActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblbuscli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblbuscli.setText("Buscar NIT/Nombre:");
        jPanel1.add(lblbuscli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        text_busc_em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_busc_emActionPerformed(evt);
            }
        });
        text_busc_em.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_busc_emKeyReleased(evt);
            }
        });
        jPanel1.add(text_busc_em, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 550, -1));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Doble clic para seleccionar empresa");

        jLabel10.setText("Cantidad de Registros:");

        text_cont.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        text_cont.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_cont.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(text_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnbusTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbusTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        campos(true);
        //  text_nom_empresa.setText(rcl.codigoclientes() + "");
        botones(true, false);
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        campos(false);
        botones(false, false);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            checEstado.setEnabled(false);
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Registro",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                delete();
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void text_correo_emKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_correo_emKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esEmail(text_correo_em)) {
            Validaciones.pinta_text(text_correo_em);
        }
    }//GEN-LAST:event_text_correo_emKeyReleased

    private void text_ced_rucFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_ced_rucFocusLost
        // TODO add your handling code here:
        Validaciones.esCedula(text_ced_ruc);
    }//GEN-LAST:event_text_ced_rucFocusLost

    private void text_ced_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ced_rucKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esCedula(text_ced_ruc)) {
            Validaciones.pinta_text(text_ced_ruc);
        }
    }//GEN-LAST:event_text_ced_rucKeyReleased

    private void text_nom_reFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_nom_reFocusLost
        // TODO add your handling code here:
        Validaciones.esLetras(text_nom_re);
    }//GEN-LAST:event_text_nom_reFocusLost

    private void text_nom_reKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_nom_reKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esLetras(text_nom_re)) {
            Validaciones.pinta_text(text_nom_re);
        }
    }//GEN-LAST:event_text_nom_reKeyReleased

    private void text_telf_emFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_telf_emFocusLost
        // TODO add your handling code here:
        Validaciones.esTelefono(text_telf_em);
    }//GEN-LAST:event_text_telf_emFocusLost

    private void text_telf_emKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_telf_emKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esTelefono(text_telf_em)) {
            Validaciones.pinta_text(text_telf_em);
        }
    }//GEN-LAST:event_text_telf_emKeyReleased

    private void text_dir_emFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_dir_emFocusLost
        // TODO add your handling code here:
        Validaciones.esRequerido(text_dir_em);
    }//GEN-LAST:event_text_dir_emFocusLost

    private void text_dir_emKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dir_emKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_dir_em)) {
            Validaciones.pinta_text(text_dir_em);
        }
    }//GEN-LAST:event_text_dir_emKeyReleased

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
       
        if(checEstado.isSelected()){
            //Editar todas las empresas el estado ponerlo a 0
            rem.desactivar_all_empresas();
        
        }
        
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar Empresa",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                
                
                update_empr();
                
                
            }
        }
        
        
        
        
        
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed

        if (form_validado()) {
            checEstado.setEnabled(true);
            em.setNom_empresa(text_nom_empresa.getText());
            em.setRuc(text_ced_ruc.getText());
            em.setNom_representante(text_nom_re.getText());
            em.setDireccion(text_dir_em.getText());
            em.setTelf(text_telf_em.getText());
            em.setEmail(text_correo_em.getText());
            em.setEstado(false);
            
            rem.insert(em);
            limpiar_campos();
            bloquearColumTable("");
            campos(false);
            botones(false, false);
            text_cont.setText(rem.count() + "");
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void tabla_emMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_emMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            checEstado.setEnabled(true);
            cod_emp = tabla_em.getValueAt(tabla_em.getSelectedRow(), 0).toString();
            text_nom_empresa.setText(tabla_em.getValueAt(tabla_em.getSelectedRow(), 1).toString());
            text_ced_ruc.setText(tabla_em.getValueAt(tabla_em.getSelectedRow(), 2).toString());
            text_nom_re.setText(tabla_em.getValueAt(tabla_em.getSelectedRow(), 3).toString());
            text_telf_em.setText(tabla_em.getValueAt(tabla_em.getSelectedRow(), 4).toString());
            text_dir_em.setText(tabla_em.getValueAt(tabla_em.getSelectedRow(), 5).toString());
            text_correo_em.setText(tabla_em.getValueAt(tabla_em.getSelectedRow(), 6).toString());
            checEstado.setSelected(Boolean.parseBoolean(tabla_em.getValueAt(tabla_em.getSelectedRow(), 7).toString()));
           
            pinta_text();
            campos(true);
            if (delet == true && update == true) {
                botones(false, true);

            } else {
                actBotones(update, delet);
            }
        }
    }//GEN-LAST:event_tabla_emMouseClicked

    private void tabla_emKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_emKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla_emKeyReleased

    private void text_busc_emKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_busc_emKeyReleased

        bloquearColumTable(text_busc_em.getText());
    }//GEN-LAST:event_text_busc_emKeyReleased

    private void btnbusTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusTodoActionPerformed
        // TODO add your handling code here:
        bloquearColumTable("");
    }//GEN-LAST:event_btnbusTodoActionPerformed

    private void text_busc_emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_busc_emActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_busc_emActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_empresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_empresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_empresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_empresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_empresa dialog = new vntana_empresa(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btnbusTodo;
    private javax.swing.JCheckBox checEstado;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JLabel lblbuscli;
    private javax.swing.JLabel lblced;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lbldir;
    private javax.swing.JLabel lblemail;
    private javax.swing.JLabel lblnom;
    private javax.swing.JLabel lbltelf;
    private javax.swing.JLabel lbltelf1;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable tabla_em;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_busc_em;
    private javax.swing.JTextField text_ced_ruc;
    private javax.swing.JLabel text_cont;
    private javax.swing.JTextField text_correo_em;
    private javax.swing.JTextField text_dir_em;
    private javax.swing.JTextField text_nom_empresa;
    private javax.swing.JTextField text_nom_re;
    private javax.swing.JTextField text_telf_em;
    // End of variables declaration//GEN-END:variables
}
