/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_pais;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Controlador.reg_proveedor;
import Modelo.Ciudad;
import Modelo.Pais;
import Modelo.Permisos;
import Modelo.Proveedores;
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
public class vntana_proveedores extends javax.swing.JDialog {

    /**
     * Creates new form
     */
    reg_proveedor rprove = new reg_proveedor();
    Proveedores prov = new Proveedores();
    reg_pais rp = new reg_pais();
    ArrayList<Pais> listPais;
    ArrayList<Ciudad> listCiu;
    boolean delet = false;
    boolean update = false;
    ArrayList<Permisos> listPerm;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    DefaultTableModel datos;
    ArrayList<Proveedores> listProve;
   

    String ced;

    public vntana_proveedores(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
        blockBotones(false);//desactivado todos los botones
        ced = menu_principal.ced_usu;
        loadPermisos(ced);    
        carg_table("");
        modelo_tabla();
        bloquear_campos(false);
        carg_comb_ciu("");
        labelContProve.setText(rprove.contProve() + "");

    }


    public void loadPermisos(String ced) {
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

    public void blockBotones(boolean ok) {
        btn_nuevo.setEnabled(ok);
        btn_save.setEnabled(ok);
        btn_actualizar.setEnabled(ok);
        btn_delete.setEnabled(ok);
    }

    public boolean actBotones(boolean upda, boolean delet) {
        btn_actualizar.setEnabled(upda);//true
        btn_delete.setEnabled(delet);//true
        return true;
    }

    public void modelo_tabla() {
        try {
            table_prove.getColumnModel().getColumn(0).setPreferredWidth(1);
            table_prove.getColumnModel().getColumn(1).setPreferredWidth(30);
            table_prove.getColumnModel().getColumn(2).setPreferredWidth(50);
            table_prove.getColumnModel().getColumn(3).setPreferredWidth(50);
            table_prove.getColumnModel().getColumn(4).setPreferredWidth(100);
            table_prove.getColumnModel().getColumn(5).setPreferredWidth(100);
            table_prove.getColumnModel().getColumn(6).setPreferredWidth(40);
            table_prove.getTableHeader().setBackground(Color.decode("#666666"));
            table_prove.getTableHeader().setForeground(Color.WHITE);

            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            table_prove.getColumnModel().getColumn(0).setCellRenderer(tcr);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void carg_comb_ciu(String data) {
        listPais = rp.CargarListPais(data);
        //listCiu = rp.CargarListCiuCombos(data);
        cmb_cod_ciu.removeAllItems();
        cmb_cod_ciu.addItem("");
        for (Pais p : listPais) {
            cmb_cod_ciu.addItem(p.getNom_pais());
            //cmb_cod_ciu.addItem(p.getNom_ciu());
        }
    }

    public void carg_table(String dato) {
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
                table_prove.setModel(datos);
            }
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void botones(boolean ok, boolean ok1) {
        btn_save.setEnabled(ok);
        btn_actualizar.setEnabled(ok1);
        btn_delete.setEnabled(ok1);
    }

    public void bloquear_campos(boolean aux) {
        txt_ced_prov.setEnabled(aux);
        txt_nom_prov.setEnabled(aux);
        txt_telf_prov.setEnabled(aux);
        cmb_cod_ciu.setSelectedItem(aux);
        txt_dir_prov.setEnabled(aux);
        txt_email_prov.setEnabled(aux);
        cmb_cod_ciu.setSelectedItem(aux);
        cmb_cod_ciu.setEnabled(aux);
    }

    public void save_proveedor() {
        prov.setCod_prove(Integer.parseInt(text_cod_prov.getText()));
        prov.setCod_ciu(rp.ObtCodCiu(cmb_cod_ciu.getSelectedItem().toString()));
        prov.setCed_ruc(txt_ced_prov.getText());
        prov.setNom_prove(txt_nom_prov.getText());
        prov.setDir_prov(txt_dir_prov.getText());
        prov.setCorreo_prove(txt_email_prov.getText());
        prov.setTelefono(txt_telf_prov.getText());
        rprove.guardar(prov); //guardar proveedores  
    }

    public boolean form_validado() {
        boolean ok = true;
        if (!Validaciones.esCedula(txt_ced_prov)) {
            ok = false;
        }

        if (!Validaciones.esRequerido(txt_dir_prov)) {
            ok = false;
        }
        if (!Validaciones.esEmail(txt_email_prov)) {
            ok = false;
        }

        if (!Validaciones.esRequerido(txt_nom_prov)) {
            ok = false;
        }

        if (!Validaciones.esTelefono(txt_telf_prov)) {
            ok = false;
        }
        if (!Validaciones.es_Combo(cmb_cod_ciu)) {
            ok = false;
        }
        return ok;
    }

    public void limpiar_campos() {
        //  text_cod_prov.setText("");
        txt_telf_prov.setText("");
        txt_nom_prov.setText("");
        txt_email_prov.setText("");
        txt_ced_prov.setText("");
        txt_dir_prov.setText("");
        cmb_cod_ciu.setSelectedItem("");
        pinta_text();
    }

    public void pinta_text() {
        // Validaciones.pinta_text(text_cod_cli);
        Validaciones.pinta_text(txt_telf_prov);
        Validaciones.pinta_text(txt_nom_prov);
        Validaciones.pinta_text(txt_email_prov);
        Validaciones.pinta_text(txt_ced_prov);
        Validaciones.pinta_text(txt_dir_prov);
        Validaciones.pinta_Combo(cmb_cod_ciu);
        // label_men.setText("");
    }

    public void update_prove() {
        prov.setCod_prove(Integer.parseInt(text_cod_prov.getText()));
        prov.setCod_ciu(rp.ObtCodCiu(cmb_cod_ciu.getSelectedItem().toString()));
        System.out.println("este es codiog:" + rp.ObtCodCiu(cmb_cod_ciu.getSelectedItem().toString()));
        prov.setCed_ruc(txt_ced_prov.getText());
        prov.setNom_prove(txt_nom_prov.getText());
        prov.setDir_prov(txt_dir_prov.getText());
        prov.setCorreo_prove(txt_email_prov.getText());
        prov.setTelefono(txt_telf_prov.getText());
        rprove.update(prov);//actualizar
        botones(false, false);
        carg_table("");
        limpiar_campos();
        bloquear_campos(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        tbar = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btnCiu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblciu = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        panel_cabez = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnelTodo = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelContProve = new javax.swing.JLabel();
        panel_datos = new javax.swing.JPanel();
        lblced = new javax.swing.JLabel();
        txt_email_prov = new javax.swing.JTextField();
        lblcod = new javax.swing.JLabel();
        text_cod_prov = new javax.swing.JTextField();
        lblnom = new javax.swing.JLabel();
        lblemail = new javax.swing.JLabel();
        cmb_cod_ciu = new javax.swing.JComboBox();
        txt_ced_prov = new javax.swing.JTextField();
        txt_nom_prov = new javax.swing.JTextField();
        lbldir = new javax.swing.JLabel();
        txt_telf_prov = new javax.swing.JTextField();
        txt_dir_prov = new javax.swing.JTextField();
        lbltelf = new javax.swing.JLabel();
        lblciu1 = new javax.swing.JLabel();
        panel_inf = new javax.swing.JPanel();
        lblbuscli = new javax.swing.JLabel();
        text_buscar = new javax.swing.JTextField();
        btn_busc_todo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_prove = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proveedores");

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);

        btn_nuevo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_nuevo.setText("Nuevo");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        tbar.add(btn_nuevo);

        btnCancelar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setFocusable(false);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        tbar.add(btnCancelar);

        btn_save.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_save.setText("Guardar");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });
        tbar.add(btn_save);

        btn_actualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        tbar.add(btn_actualizar);

        btn_delete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.setText("Eliminar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        tbar.add(btn_delete);

        btnCiu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCiu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/mapa.png"))); // NOI18N
        btnCiu.setText("Ciudad");
        btnCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCiuActionPerformed(evt);
            }
        });
        tbar.add(btnCiu);
        tbar.add(jSeparator1);

        lblciu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblciu.setText("Imprimir Proveedores:");
        tbar.add(lblciu);

        btnImprimir.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imprimir.png"))); // NOI18N
        btnImprimir.setFocusable(false);
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        tbar.add(btnImprimir);

        panel_cabez.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Proveedores");

        javax.swing.GroupLayout panel_cabezLayout = new javax.swing.GroupLayout(panel_cabez);
        panel_cabez.setLayout(panel_cabezLayout);
        panel_cabezLayout.setHorizontalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cabezLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(784, Short.MAX_VALUE))
        );
        panel_cabezLayout.setVerticalGroup(
            panel_cabezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        pnelTodo.setBackground(new java.awt.Color(204, 204, 204));
        pnelTodo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Cantidad:");
        pnelTodo.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 60, 20));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("Doble clic para seleccionar Proveedor");
        pnelTodo.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 330, 20));

        labelContProve.setText("0");
        pnelTodo.add(labelContProve, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 40, 20));

        panel_datos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_datos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblced.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblced.setText("Cédula/NIT:");
        panel_datos.add(lblced, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, 20));

        txt_email_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_email_provKeyReleased(evt);
            }
        });
        panel_datos.add(txt_email_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 400, -1));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Código:");
        panel_datos.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        text_cod_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_prov.setEnabled(false);
        panel_datos.add(text_cod_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 40, -1));

        lblnom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblnom.setText("Nombre:");
        panel_datos.add(lblnom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        lblemail.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblemail.setText("Email:");
        panel_datos.add(lblemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 40, 20));

        cmb_cod_ciu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_cod_ciuActionPerformed(evt);
            }
        });
        cmb_cod_ciu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmb_cod_ciuKeyReleased(evt);
            }
        });
        panel_datos.add(cmb_cod_ciu, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 190, -1));

        txt_ced_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ced_provKeyReleased(evt);
            }
        });
        panel_datos.add(txt_ced_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 170, -1));

        txt_nom_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nom_provKeyReleased(evt);
            }
        });
        panel_datos.add(txt_nom_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 290, -1));

        lbldir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbldir.setText("Dirección:");
        panel_datos.add(lbldir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        txt_telf_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_telf_provKeyReleased(evt);
            }
        });
        panel_datos.add(txt_telf_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 150, -1));

        txt_dir_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dir_provKeyReleased(evt);
            }
        });
        panel_datos.add(txt_dir_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 770, -1));

        lbltelf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbltelf.setText("Télefono:");
        panel_datos.add(lbltelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 60, 20));

        lblciu1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblciu1.setText("Ciudad:");
        panel_datos.add(lblciu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, 20));

        panel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblbuscli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblbuscli.setText("Buscar por NIT/Ced/Nombres:");
        panel_inf.add(lblbuscli, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 17));

        text_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_buscarKeyReleased(evt);
            }
        });
        panel_inf.add(text_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 560, -1));

        btn_busc_todo.setBackground(new java.awt.Color(0, 153, 102));
        btn_busc_todo.setForeground(new java.awt.Color(255, 255, 255));
        btn_busc_todo.setText("Todo");
        btn_busc_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busc_todoActionPerformed(evt);
            }
        });

        table_prove.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table_prove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_proveMouseClicked(evt);
            }
        });
        table_prove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_proveKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_prove);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_busc_todo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnelTodo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_cabez, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(panel_datos, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_cabez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(panel_datos, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_busc_todo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnelTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea eliminar proveedor",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {

                prov.setCod_prove(Integer.parseInt(text_cod_prov.getText()));
                rprove.delete(prov);
                carg_table("");
                 modelo_tabla();
                limpiar_campos();
                bloquear_campos(false);
                botones(false, false);
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_busc_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busc_todoActionPerformed
        carg_table("");
         modelo_tabla();
    }//GEN-LAST:event_btn_busc_todoActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // TODO add your handling code here:
        text_cod_prov.setText(rprove.cod_prov() + "");
        carg_comb_ciu("");
        pinta_text();
        limpiar_campos();
        bloquear_campos(true);
        botones(true, false);

    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            save_proveedor();
            carg_table("");
            modelo_tabla();
            limpiar_campos();
            bloquear_campos(false);

            botones(false, false);
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar proveedor",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                update_prove();
                carg_table("");
                modelo_tabla();
            } else {
                System.out.println("");
            }
        }

    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void table_proveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_proveMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            carg_comb_ciu("");
            text_cod_prov.setText(table_prove.getValueAt(table_prove.getSelectedRow(), 0).toString());
            String ciu = table_prove.getValueAt(table_prove.getSelectedRow(), 1).toString();
            cmb_cod_ciu.setSelectedItem(ciu);
            txt_ced_prov.setText(table_prove.getValueAt(table_prove.getSelectedRow(), 2).toString());
            txt_nom_prov.setText(table_prove.getValueAt(table_prove.getSelectedRow(), 3).toString());
            txt_dir_prov.setText(table_prove.getValueAt(table_prove.getSelectedRow(), 4).toString());
            txt_email_prov.setText(table_prove.getValueAt(table_prove.getSelectedRow(), 5).toString());
            txt_telf_prov.setText(table_prove.getValueAt(table_prove.getSelectedRow(), 6).toString());
            pinta_text();
            bloquear_campos(true);
            if (delet == true && update == true) {
                botones(false, true);

            } else {
                actBotones(update, delet);
            }
        }

    }//GEN-LAST:event_table_proveMouseClicked

    private void table_proveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_proveKeyPressed

            //rellenar_text(table_prod.getValueAt(table_prod.getSelectedRow(), 0).toString());
        //combo_tip.setSelectedItem(table_prove.getValueAt(table_prove.getSelectedRow(), 2).toString());
        // pinta_text();
        //campos(true);         

    }//GEN-LAST:event_table_proveKeyPressed

    private void text_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscarKeyReleased
        // TODO add your handling code here:
        carg_table(text_buscar.getText());
         modelo_tabla();

    }//GEN-LAST:event_text_buscarKeyReleased

    private void txt_ced_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ced_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esCedula(txt_ced_prov)) {
            Validaciones.pinta_text(txt_ced_prov);
        }
    }//GEN-LAST:event_txt_ced_provKeyReleased

    private void txt_telf_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telf_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esTelefono(txt_telf_prov)) {
            Validaciones.pinta_text(txt_telf_prov);
        }
    }//GEN-LAST:event_txt_telf_provKeyReleased

    private void txt_email_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_email_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esEmail(txt_email_prov)) {
            Validaciones.pinta_text(txt_email_prov);
        }

    }//GEN-LAST:event_txt_email_provKeyReleased

    private void txt_nom_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nom_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(txt_nom_prov)) {
            Validaciones.pinta_text(txt_nom_prov);
        }
    }//GEN-LAST:event_txt_nom_provKeyReleased

    private void txt_dir_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dir_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(txt_dir_prov)) {
            Validaciones.pinta_text(txt_dir_prov);
        }
    }//GEN-LAST:event_txt_dir_provKeyReleased

    private void btnCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCiuActionPerformed
        // TODO add your handling code here:
        vntana_pais dlg = new vntana_pais(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
    }//GEN-LAST:event_btnCiuActionPerformed

    private void cmb_cod_ciuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_cod_ciuKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_Combo(cmb_cod_ciu)) {
            Validaciones.pinta_Combo(cmb_cod_ciu);
        }
    }//GEN-LAST:event_cmb_cod_ciuKeyReleased

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        bloquear_campos(false);
        botones(false, false);

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        try {
            if (!labelContProve.getText().equals("0")) {
                Reportes_g.generarReporte("rep_proveedor");
            } else {
                JOptionPane.showMessageDialog(this, "No existen datos");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }


    }//GEN-LAST:event_btnImprimirActionPerformed

    private void cmb_cod_ciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_cod_ciuActionPerformed
        // TODO add your handling code here:
         Validaciones.pinta_Combo(cmb_cod_ciu);
    }//GEN-LAST:event_cmb_cod_ciuActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_proveedores dialog = new vntana_proveedores(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCiu;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_busc_todo;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox cmb_cod_ciu;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel labelContProve;
    private javax.swing.JLabel lblbuscli;
    private javax.swing.JLabel lblced;
    private javax.swing.JLabel lblciu;
    private javax.swing.JLabel lblciu1;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lbldir;
    private javax.swing.JLabel lblemail;
    private javax.swing.JLabel lblnom;
    private javax.swing.JLabel lbltelf;
    private javax.swing.JPanel panel_cabez;
    private javax.swing.JPanel panel_datos;
    private javax.swing.JPanel panel_inf;
    private javax.swing.JPanel pnelTodo;
    private javax.swing.JTable table_prove;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_buscar;
    private javax.swing.JTextField text_cod_prov;
    private javax.swing.JTextField txt_ced_prov;
    private javax.swing.JTextField txt_dir_prov;
    private javax.swing.JTextField txt_email_prov;
    private javax.swing.JTextField txt_nom_prov;
    private javax.swing.JTextField txt_telf_prov;
    // End of variables declaration//GEN-END:variables
}
