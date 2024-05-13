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
import Modelo.Pais;
import Modelo.Permisos;
import Modelo.Provincia;
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
public class vntana_pais extends javax.swing.JDialog {

    /**
     * Creates new form vntana_pais
     */
    private static reg_pais rp = new reg_pais();
    Pais pa = new Pais();
    Provincia prov = new Provincia();
    Ciudad ciu = new Ciudad();
    int ident = 0;
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    ArrayList<Permisos> listPerm;
    boolean delet = false;
    boolean update = false;
    String ced;
    ArrayList<Pais> listPais;
    ArrayList<Provincia> listProv;
    ArrayList<Ciudad> listCiu;

    public vntana_pais(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
         setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
       
        try {
            //bloquear botones
            blockBotones_pais(false);
            blockBotones_estate(false);
            blockBotones_city(false);
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            loadTPais("");
            loadEstado("");
            load_ciu("");
            blo_cam_pais(false);
            blo_cam_ciu(false);
            blo_cam_estado(false);
            //cargar combos
            carg_comb();

        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void loadPermisos(String ced) {//Busca por cedula
        listPerm = rperm.cargar_permisos(ced);
        for (Permisos per : listPerm) {

            if (per.isPer_regist()) {
                btn_new_pais.setEnabled(true);
                btn_new_state.setEnabled(true);
                btn_new_ciu.setEnabled(true);

            }
            if (per.isPer_udate()) {
                btn_act_city.setEnabled(true);
                btn_update_state.setEnabled(true);
                btnAct_pais.setEnabled(true);

                update = true;
            }
            if (per.isPer_delete()) {
                System.out.println("Ingresa al eliminar");
                // btn_delete.setEnabled(true);
                delet = true;
            }
        }
    }

    public void blockBotones_pais(boolean ok) {
        btn_new_pais.setEnabled(ok);
        btnSave_pais.setEnabled(ok);
        btnAct_pais.setEnabled(ok);
        btn_delete_pais.setEnabled(ok);
    }

    public void blockBotones_estate(boolean ok) {
        btn_new_state.setEnabled(ok);
        btn_save_state.setEnabled(ok);
        btn_update_state.setEnabled(ok);
        btn_delete_state.setEnabled(ok);
    }

    public void blockBotones_city(boolean ok) {
        btn_new_ciu.setEnabled(ok);
        btn_save_city.setEnabled(ok);
        btn_act_city.setEnabled(ok);
        btn_delete_city.setEnabled(ok);
    }

    public void carg_comb() {
        listPais = rp.CargarListPais("");
        comboPais.removeAllItems();
        comboPais.addItem("");
        combo_pais_prov.removeAllItems();
        combo_pais_prov.addItem("");
        for (Pais p : listPais) {
            combo_pais_prov.addItem(p.getNom_pais());
            comboPais.addItem(p.getNom_pais());
        }
    }

    public void carg_comb_prov(String data) {////codiog del pais
        listProv = rp.CargarListProvincia(data);
        cmb_prov.removeAllItems();
        cmb_prov.addItem("");
        for (Provincia p : listProv) {
            cmb_prov.addItem(p.getNom_prov());

        }

    }

    //VALIDAR   
    public void pinta_text() {
        Validaciones.pinta_text(text_prov);
        Validaciones.pinta_text(text_nom_pais);
        Validaciones.pinta_text(text_ciudad);
        Validaciones.pinta_Combo(combo_pais_prov);
        Validaciones.pinta_Combo(comboPais);
        Validaciones.pinta_Combo(cmb_prov);

    }

    public boolean form_validado(int n) {
        boolean ok = true;
        if (n == 1) {
            if (!Validaciones.esRequerido(text_nom_pais)) {
                ok = false;
            }

        }
        if (n == 2) {
            //JOptionPane.showMessageDialog(this, "si ingresa");
            if (!Validaciones.es_Combo(combo_pais_prov)) {
                ok = false;
            }
            if (!Validaciones.esRequerido(text_prov)) {
                ok = false;
            }

        }
        if (n == 3) {
            if (!Validaciones.esLetras(text_ciudad)) {
                ok = false;
            }
            if (!Validaciones.es_Combo(comboPais)) {
                ok = false;
            }
            if (!Validaciones.es_Combo(cmb_prov)) {
                ok = false;
            }

        }
        return ok;
    }

    //Modelo de tabla pais
    public void loadTPais(String dato) {
        loadPais(dato);
        modelo_tabla();
    }

    public void modelo_tabla() {
        try {
            tblPais.getColumnModel().getColumn(0).setPreferredWidth(2);
            tblPais.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblPais.getTableHeader().setBackground(Color.decode("#666666"));
            tblPais.getTableHeader().setForeground(Color.WHITE);
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            tblPais.getColumnModel().getColumn(0).setCellRenderer(tcr);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    //cargar pais
    DefaultTableModel dataP;

    public void loadPais(String dato) {
        dataP = rp.label_pais();
        listPais = rp.CargarListPais(dato);
        Object[] data = new Object[2];
        for (Pais p : listPais) {
            data[0] = p.getCod_pais();
            data[1] = p.getNom_pais();
            dataP.addRow(data);
            tblPais.setModel(dataP);
        }
    }

    //ESTADO***************************************************
    //Modelo de tabla pais
    public void loadEstado(String dato) {
        loadProv(dato);
        modelo_tabla_estado();

    }

    public void modelo_tabla_estado() {
        try {
            tblProv.getColumnModel().getColumn(0).setPreferredWidth(2);
            tblProv.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblProv.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblProv.getTableHeader().setBackground(Color.decode("#666666"));
            tblProv.getTableHeader().setForeground(Color.WHITE);

            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            tblProv.getColumnModel().getColumn(0).setCellRenderer(tcr);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    //cargar pais
    DefaultTableModel dataE;

    public void loadProv(String dato) {
        dataE = rp.label_Prov();
        listProv = rp.CargarListProvincia(dato);
        Object[] data = new Object[3];
        for (Provincia p : listProv) {
            data[0] = p.getCod_prov();
            data[1] = obtPais(p.getCod_pais() + "");
            data[2] = p.getNom_prov();
            dataE.addRow(data);
            tblProv.setModel(dataE);
        }
    }

    public String obtPais(String dato) {
        String pais = null;
        listPais = rp.CargarListPais(dato);
        for (Pais p : listPais) {
            pais = (p.getNom_pais());
        }
        return pais;
    }

    //CIUDAD **********************************************
    public void load_ciu(String dato) {
        loadCiu(dato);
        modelo_tabla_estado_c();

    }

    public void modelo_tabla_estado_c() {
        try {
            tblCiu.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblCiu.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblCiu.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblCiu.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblCiu.getTableHeader().setBackground(Color.decode("#666666"));
            tblCiu.getTableHeader().setForeground(Color.WHITE);
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            tblCiu.getColumnModel().getColumn(0).setCellRenderer(tcr);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    //cargar pais
    DefaultTableModel dataCiu;

    public void loadCiu(String dato) {
        dataCiu = rp.label_Ciudad();
        listCiu = rp.CargarListCiu(dato);
        Object[] data = new Object[4];
        for (Ciudad c : listCiu) {
            data[0] = c.getCod_ciu();
            //ciudad nombre de la provincia
            data[1] = obtPais(rp.ObtCodPais_of_Provincia(rp.ObtNomProvincia(c.getCod_prov() + "")) + "");
            data[2] = rp.ObtNomProvincia(c.getCod_prov() + "");
            data[3] = c.getNom_ciu();
            // data[2] = p.getNom_prov();
            dataCiu.addRow(data);
            tblCiu.setModel(dataCiu);
        }
    }

    public void clean_state() {

        text_cod_prov.setText("");
        combo_pais_prov.setSelectedIndex(0);
        text_prov.setText("");

    }

    public void clean_city() {
        text_cod_ciu.setText("");
        comboPais.setSelectedIndex(0);
        cmb_prov.setSelectedIndex(0);
        text_ciudad.setText("");

    }

    public void botones_pais(boolean ok, boolean ok1) {
        btnSave_pais.setEnabled(ok);
        btnAct_pais.setEnabled(ok1);//true
        btn_delete_pais.setEnabled(ok1);//true
    }

    public void botones_state(boolean ok, boolean ok1) {
        btn_save_state.setEnabled(ok);
        btn_update_state.setEnabled(ok1);//true
        btn_delete_state.setEnabled(ok1);//true
    }

    public void botones_city(boolean ok, boolean ok1) {
        btn_save_city.setEnabled(ok);
        btn_act_city.setEnabled(ok1);//true
        btn_delete_city.setEnabled(ok1);//true
    }

    public boolean actBotones_pais(boolean upda, boolean delet) {
        btnAct_pais.setEnabled(upda);//true
        btn_delete_pais.setEnabled(delet);//true
        return true;
    }

    public boolean actBotones_state(boolean upda, boolean delet) {
        btn_update_state.setEnabled(upda);//true
        btn_delete_state.setEnabled(delet);//true
        return true;
    }

    public boolean actBotones_city(boolean upda, boolean delet) {
        btn_act_city.setEnabled(upda);//true
        btn_delete_city.setEnabled(delet);//true
        return true;
    }

    public void blo_cam_pais(boolean ok) {
        text_nom_pais.setEnabled(ok);

    }

    public void blo_cam_ciu(boolean ok) {
        comboPais.setEnabled(ok);
        cmb_prov.setEnabled(ok);
        text_ciudad.setEnabled(ok);

    }

    public void blo_cam_estado(boolean ok) {
        combo_pais_prov.setEnabled(ok);
        text_prov.setEnabled(ok);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnel_all = new javax.swing.JPanel();
        pnel_inf = new javax.swing.JPanel();
        lblpais = new javax.swing.JLabel();
        text_cod_pais = new javax.swing.JTextField();
        text_nom_pais = new javax.swing.JTextField();
        lblcod = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPais = new javax.swing.JTable();
        btnbusTodoP = new javax.swing.JButton();
        tbar = new javax.swing.JToolBar();
        btn_new_pais = new javax.swing.JButton();
        btn_clean_pais = new javax.swing.JButton();
        btn_delete_pais = new javax.swing.JButton();
        btnSave_pais = new javax.swing.JButton();
        btnAct_pais = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pnel_all_prov = new javax.swing.JPanel();
        pnel_prov0 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        text_cod_prov = new javax.swing.JTextField();
        text_prov = new javax.swing.JTextField();
        combo_pais_prov = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProv = new javax.swing.JTable();
        btn_cus_state = new javax.swing.JButton();
        tbarprv = new javax.swing.JToolBar();
        btn_new_state = new javax.swing.JButton();
        btn_clean_state = new javax.swing.JButton();
        btn_delete_state = new javax.swing.JButton();
        btn_save_state = new javax.swing.JButton();
        btn_update_state = new javax.swing.JButton();
        txtBuscProv = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        panel_ciu = new javax.swing.JLayeredPane();
        tbar_ciu = new javax.swing.JToolBar();
        btn_new_ciu = new javax.swing.JButton();
        bnt_clean_ciu = new javax.swing.JButton();
        btn_delete_city = new javax.swing.JButton();
        btn_save_city = new javax.swing.JButton();
        btn_act_city = new javax.swing.JButton();
        pnel_all_ciu = new javax.swing.JPanel();
        btn_busc_city = new javax.swing.JButton();
        txtBusCiu = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCiu = new javax.swing.JTable();
        pnel_ciu0 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text_cod_ciu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        text_ciudad = new javax.swing.JTextField();
        comboPais = new javax.swing.JComboBox();
        cmb_prov = new javax.swing.JComboBox();
        lblcab = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pais - Departamento - Ciudad");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));

        pnel_all.setBackground(new java.awt.Color(234, 234, 234));

        pnel_inf.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblpais.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblpais.setText("Pais:");
        pnel_inf.add(lblpais, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, 20));

        text_cod_pais.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_cod_pais.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_pais.setEnabled(false);
        pnel_inf.add(text_cod_pais, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 70, -1));

        text_nom_pais.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_nom_pais.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_nom_paisFocusLost(evt);
            }
        });
        text_nom_pais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_nom_paisKeyReleased(evt);
            }
        });
        pnel_inf.add(text_nom_pais, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 330, -1));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Código:");
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 19, -1, -1));

        tblPais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPaisMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPais);

        btnbusTodoP.setBackground(new java.awt.Color(0, 153, 102));
        btnbusTodoP.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnbusTodoP.setForeground(new java.awt.Color(255, 255, 255));
        btnbusTodoP.setText("Todo");
        btnbusTodoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusTodoPActionPerformed(evt);
            }
        });

        tbar.setBackground(new java.awt.Color(204, 204, 204));
        tbar.setRollover(true);
        tbar.setEnabled(false);

        btn_new_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new_pais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btn_new_pais.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_new_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_paisActionPerformed(evt);
            }
        });
        tbar.add(btn_new_pais);

        btn_clean_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        btn_clean_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clean_paisActionPerformed(evt);
            }
        });
        tbar.add(btn_clean_pais);

        btn_delete_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_paisActionPerformed(evt);
            }
        });
        tbar.add(btn_delete_pais);

        btnSave_pais.setForeground(new java.awt.Color(255, 255, 255));
        btnSave_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btnSave_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_paisActionPerformed(evt);
            }
        });
        tbar.add(btnSave_pais);

        btnAct_pais.setForeground(new java.awt.Color(255, 255, 255));
        btnAct_pais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btnAct_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAct_paisActionPerformed(evt);
            }
        });
        tbar.add(btnAct_pais);

        txtBuscar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Buscar:");

        javax.swing.GroupLayout pnel_allLayout = new javax.swing.GroupLayout(pnel_all);
        pnel_all.setLayout(pnel_allLayout);
        pnel_allLayout.setHorizontalGroup(
            pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnel_allLayout.createSequentialGroup()
                .addGroup(pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnel_allLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnbusTodoP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnel_allLayout.createSequentialGroup()
                        .addContainerGap(15, Short.MAX_VALUE)
                        .addGroup(pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnel_inf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        pnel_allLayout.setVerticalGroup(
            pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnel_allLayout.createSequentialGroup()
                .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnbusTodoP, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Pais", pnel_all);

        pnel_all_prov.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnel_prov0.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_prov0.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel8.setText("Pais:");
        pnel_prov0.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel2.setText("Código:");
        pnel_prov0.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        text_cod_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cod_prov.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_prov.setEnabled(false);
        pnel_prov0.add(text_cod_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 70, -1));

        text_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_prov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_provFocusLost(evt);
            }
        });
        text_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_provKeyReleased(evt);
            }
        });
        pnel_prov0.add(text_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 180, -1));

        combo_pais_prov.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        combo_pais_prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_pais_provActionPerformed(evt);
            }
        });
        combo_pais_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combo_pais_provKeyReleased(evt);
            }
        });
        pnel_prov0.add(combo_pais_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 180, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel13.setText("Municipio :");
        pnel_prov0.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, -1, 20));

        pnel_all_prov.add(pnel_prov0, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 540, 80));

        tblProv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProvMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProv);

        pnel_all_prov.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 540, 180));

        btn_cus_state.setBackground(new java.awt.Color(0, 153, 102));
        btn_cus_state.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btn_cus_state.setForeground(new java.awt.Color(255, 255, 255));
        btn_cus_state.setText("Todo");
        btn_cus_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cus_stateActionPerformed(evt);
            }
        });
        pnel_all_prov.add(btn_cus_state, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 70, 20));

        tbarprv.setBackground(new java.awt.Color(204, 204, 204));
        tbarprv.setRollover(true);
        tbarprv.setEnabled(false);

        btn_new_state.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_stateActionPerformed(evt);
            }
        });
        tbarprv.add(btn_new_state);

        btn_clean_state.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        btn_clean_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clean_stateActionPerformed(evt);
            }
        });
        tbarprv.add(btn_clean_state);

        btn_delete_state.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_stateActionPerformed(evt);
            }
        });
        tbarprv.add(btn_delete_state);

        btn_save_state.setForeground(new java.awt.Color(255, 255, 255));
        btn_save_state.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_save_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_stateActionPerformed(evt);
            }
        });
        tbarprv.add(btn_save_state);

        btn_update_state.setForeground(new java.awt.Color(255, 255, 255));
        btn_update_state.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_update_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_stateActionPerformed(evt);
            }
        });
        tbarprv.add(btn_update_state);

        pnel_all_prov.add(tbarprv, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 40));

        txtBuscProv.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        txtBuscProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscProvActionPerformed(evt);
            }
        });
        txtBuscProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscProvKeyReleased(evt);
            }
        });
        pnel_all_prov.add(txtBuscProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 410, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel11.setText("Bucar:");
        pnel_all_prov.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 40, 20));

        jTabbedPane1.addTab("Municipio", pnel_all_prov);

        panel_ciu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbar_ciu.setBackground(new java.awt.Color(204, 204, 204));
        tbar_ciu.setRollover(true);
        tbar_ciu.setEnabled(false);

        btn_new_ciu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        btn_new_ciu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_ciuActionPerformed(evt);
            }
        });
        tbar_ciu.add(btn_new_ciu);

        bnt_clean_ciu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        bnt_clean_ciu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_clean_ciuActionPerformed(evt);
            }
        });
        tbar_ciu.add(bnt_clean_ciu);

        btn_delete_city.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete_city.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_cityActionPerformed(evt);
            }
        });
        tbar_ciu.add(btn_delete_city);

        btn_save_city.setForeground(new java.awt.Color(255, 255, 255));
        btn_save_city.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_save_city.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_cityActionPerformed(evt);
            }
        });
        tbar_ciu.add(btn_save_city);

        btn_act_city.setForeground(new java.awt.Color(255, 255, 255));
        btn_act_city.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_act_city.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_act_cityActionPerformed(evt);
            }
        });
        tbar_ciu.add(btn_act_city);

        panel_ciu.add(tbar_ciu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 40));

        pnel_all_ciu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_busc_city.setBackground(new java.awt.Color(0, 153, 102));
        btn_busc_city.setForeground(new java.awt.Color(255, 255, 255));
        btn_busc_city.setText("Todo");
        btn_busc_city.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busc_cityActionPerformed(evt);
            }
        });
        pnel_all_ciu.add(btn_busc_city, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, -1, 21));

        txtBusCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusCiuActionPerformed(evt);
            }
        });
        txtBusCiu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusCiuKeyReleased(evt);
            }
        });
        pnel_all_ciu.add(txtBusCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 410, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Buscar:");
        pnel_all_ciu.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 50, -1));

        tblCiu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblCiu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCiuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblCiu);

        pnel_all_ciu.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 540, 180));

        pnel_ciu0.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_ciu0.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("Pais:");
        pnel_ciu0.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 20));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Código:");
        pnel_ciu0.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        text_cod_ciu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_ciu.setEnabled(false);
        pnel_ciu0.add(text_cod_ciu, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 70, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Sector :");
        pnel_ciu0.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, 20));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Departamento:");
        pnel_ciu0.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        text_ciudad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_ciudadFocusLost(evt);
            }
        });
        text_ciudad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ciudadKeyReleased(evt);
            }
        });
        pnel_ciu0.add(text_ciudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 170, -1));

        comboPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPaisActionPerformed(evt);
            }
        });
        comboPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                comboPaisKeyReleased(evt);
            }
        });
        pnel_ciu0.add(comboPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 340, -1));

        cmb_prov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_provActionPerformed(evt);
            }
        });
        cmb_prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmb_provKeyReleased(evt);
            }
        });
        pnel_ciu0.add(cmb_prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 200, -1));

        pnel_all_ciu.add(pnel_ciu0, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 550, 70));

        panel_ciu.add(pnel_all_ciu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 360));

        jTabbedPane1.addTab("Sector", panel_ciu);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 570, 390));

        lblcab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pais-Departamento-Ciudad");

        javax.swing.GroupLayout lblcabLayout = new javax.swing.GroupLayout(lblcab);
        lblcab.setLayout(lblcabLayout);
        lblcabLayout.setHorizontalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblcabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        lblcabLayout.setVerticalGroup(
            lblcabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(lblcab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_nom_paisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_nom_paisFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_text_nom_paisFocusLost

    private void text_nom_paisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_nom_paisKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_nom_pais)) {
            Validaciones.pinta_text(text_nom_pais);
        }
    }//GEN-LAST:event_text_nom_paisKeyReleased

    private void text_provFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_provFocusLost
        // TODO add your handling code here:
        //Validaciones.esLetras(text_canton);
    }//GEN-LAST:event_text_provFocusLost

    private void text_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_prov)) {
            Validaciones.pinta_text(text_prov);
        }
    }//GEN-LAST:event_text_provKeyReleased

    private void text_ciudadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_ciudadFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ciudadFocusLost

    private void text_ciudadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ciudadKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_ciudad)) {
            Validaciones.pinta_text(text_ciudad);
        }
    }//GEN-LAST:event_text_ciudadKeyReleased

    private void btnSave_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_paisActionPerformed
        // Registrar pais
        int n = 1;
        if (form_validado(n)) {
            pa.setCod_pais(Integer.parseInt(text_cod_pais.getText()));
            pa.setNom_pais(text_nom_pais.getText());
            rp.insert_pais(pa);
            loadTPais("");
            JOptionPane.showMessageDialog(this, "Registro Guardado");
        }


    }//GEN-LAST:event_btnSave_paisActionPerformed

    private void btn_save_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_stateActionPerformed
        // TODO add your handling code here:
        int n = 2;//combo_pais_prov
        if (form_validado(n)) {
            prov.setCod_prov(Integer.parseInt(text_cod_prov.getText()));
            prov.setCod_pais(rp.ObtCodPais(combo_pais_prov.getSelectedItem().toString()));
            System.out.println(rp.ObtCodPais(combo_pais_prov.getSelectedItem().toString()));
            prov.setNom_prov(text_prov.getText());
            rp.insert_prov(prov);
            loadEstado("");
            JOptionPane.showMessageDialog(this, "Registro Guardado");
        }
    }//GEN-LAST:event_btn_save_stateActionPerformed

    private void btn_save_cityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_cityActionPerformed
        // TODO add your handling code here:
        //el pais solo para buscar 
        int n = 3;
        if (form_validado(n)) {
            ciu.setCod_ciu(Integer.parseInt(text_cod_ciu.getText()));
            ciu.setCod_prov(rp.ObtCodProv(cmb_prov.getSelectedItem().toString()));
            ciu.setNom_ciu(text_ciudad.getText());
            rp.insert_ciu(ciu);
            load_ciu("");
            JOptionPane.showMessageDialog(this, "Registro Guardado");
        }
    }//GEN-LAST:event_btn_save_cityActionPerformed

    private void btn_new_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_paisActionPerformed
        text_nom_pais.setEnabled(true);
        pinta_text();
        carg_comb();
        text_cod_pais.setText(rp.cod_pais() + "");
        //limpiar comobos
        text_nom_pais.setText("");
        botones_pais(true, false);


    }//GEN-LAST:event_btn_new_paisActionPerformed

    private void btn_new_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_stateActionPerformed
        // TODO add your handling code here:
        blo_cam_estado(true);
        pinta_text();
        carg_comb();
        text_cod_prov.setText(rp.cod_prov() + "");

        botones_state(true, false);


    }//GEN-LAST:event_btn_new_stateActionPerformed

    private void btn_act_cityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_act_cityActionPerformed
        // TODO add your handling code here:
        int n = 3;
        if (form_validado(n)) {
            ciu.setCod_ciu(Integer.parseInt(text_cod_ciu.getText()));
            ciu.setCod_prov(rp.ObtCodProv(cmb_prov.getSelectedItem().toString()));
            ciu.setNom_ciu(text_ciudad.getText());
            rp.update_ciu(ciu);
            load_ciu("");
            // JOptionPane.showMessageDialog(this, "Registro Actualizado");
        }
    }//GEN-LAST:event_btn_act_cityActionPerformed

    private void btn_new_ciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_ciuActionPerformed
        // TODO add your handling code here:
        blo_cam_ciu(true);
        pinta_text();
        carg_comb();//cod_tablasCiudad
        //  text_cod_ciu.setText("C"+rp.);
        text_cod_ciu.setText(rp.cod_ciu());
        text_ciudad.setText("");
        botones_city(true, false);
    }//GEN-LAST:event_btn_new_ciuActionPerformed

    private void comboPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPaisActionPerformed
        // TODO add your handling code here:
        // comboPais
        try {
            carg_comb_prov(rp.ObtCodPais(comboPais.getSelectedItem().toString()) + "");
            Validaciones.pinta_Combo(comboPais);
        } catch (Exception err) {
            System.out.println(err);
        }

    }//GEN-LAST:event_comboPaisActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        loadTPais(txtBuscar.getText());

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnbusTodoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusTodoPActionPerformed
        // TODO add your handling code here:
        loadTPais("");
    }//GEN-LAST:event_btnbusTodoPActionPerformed

    private void btnAct_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAct_paisActionPerformed
        // TODO add your handling code here:
        int n = 1;
        if (form_validado(n)) {
            pa.setCod_pais(Integer.parseInt(text_cod_pais.getText()));
            pa.setNom_pais(text_nom_pais.getText());
            rp.update_pais(pa);
            loadTPais("");
            JOptionPane.showMessageDialog(this, "Registro Actualizado");
        }
    }//GEN-LAST:event_btnAct_paisActionPerformed

    private void tblPaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPaisMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            text_cod_pais.setText(tblPais.getValueAt(tblPais.getSelectedRow(), 0).toString());
            text_nom_pais.setText(tblPais.getValueAt(tblPais.getSelectedRow(), 1).toString());

            pinta_text();
            blo_cam_pais(true);

            //  campos(true);
            if (delet == true && update == true) {
                botones_pais(false, true);

            } else {
                actBotones_pais(update, delet);
            }

        }


    }//GEN-LAST:event_tblPaisMouseClicked

    private void txtBuscProvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscProvKeyReleased
        // TODO add your handling code here:
        loadEstado(txtBuscProv.getText());
    }//GEN-LAST:event_txtBuscProvKeyReleased

    private void btn_cus_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cus_stateActionPerformed
        // TODO add your handling code here:
        loadEstado("");
    }//GEN-LAST:event_btn_cus_stateActionPerformed

    private void txtBusCiuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusCiuKeyReleased
        // TODO add your handling code here:
        load_ciu(txtBusCiu.getText());
    }//GEN-LAST:event_txtBusCiuKeyReleased

    private void btn_busc_cityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busc_cityActionPerformed
        // TODO add your handling code here:
        load_ciu("");
    }//GEN-LAST:event_btn_busc_cityActionPerformed

    private void tblProvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProvMouseClicked
        if (evt.getClickCount() == 2) {
            carg_comb();
            text_cod_prov.setText(tblProv.getValueAt(tblProv.getSelectedRow(), 0).toString());
            combo_pais_prov.setSelectedItem(tblProv.getValueAt(tblProv.getSelectedRow(), 1).toString());
            text_prov.setText(tblProv.getValueAt(tblProv.getSelectedRow(), 2).toString());

            pinta_text();
            blo_cam_estado(true);
            //  campos(true);
            if (delet == true && update == true) {
                botones_state(false, true);

            } else {
                actBotones_state(update, delet);
            }
        }
    }//GEN-LAST:event_tblProvMouseClicked

    private void tblCiuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCiuMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            carg_comb();
            text_cod_ciu.setText(tblCiu.getValueAt(tblCiu.getSelectedRow(), 0).toString());
            comboPais.setSelectedItem(tblCiu.getValueAt(tblCiu.getSelectedRow(), 1).toString());
            cmb_prov.setSelectedItem(tblCiu.getValueAt(tblCiu.getSelectedRow(), 2).toString());
            text_ciudad.setText(tblCiu.getValueAt(tblCiu.getSelectedRow(), 3).toString());

            pinta_text();
            blo_cam_ciu(true);
            if (delet == true && update == true) {
                botones_city(false, true);

            } else {
                actBotones_city(update, delet);
            }
        }
    }//GEN-LAST:event_tblCiuMouseClicked

    private void btn_update_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_stateActionPerformed
        // TODO add your handling code here:
        int n = 2;//combo_pais_prov
        if (form_validado(n)) {

            prov.setCod_prov(Integer.parseInt(text_cod_prov.getText()));
            prov.setCod_pais(rp.ObtCodPais(combo_pais_prov.getSelectedItem().toString()));
            prov.setNom_prov(text_prov.getText());
            rp.update_prov(prov);
            loadEstado("");
            // JOptionPane.showMessageDialog(this, "Registro Actualizado");
        }
    }//GEN-LAST:event_btn_update_stateActionPerformed

    private void btn_clean_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clean_paisActionPerformed
        // TODO add your handling code here:
        text_nom_pais.setText("");
        text_cod_pais.setText("");
        blo_cam_pais(false);
        botones_pais(false, false);

    }//GEN-LAST:event_btn_clean_paisActionPerformed

    private void btn_delete_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_paisActionPerformed
        // TODO add your handling code here:

        int n = 1;
        if (form_validado(n)) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Pais",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                pa.setCod_pais(Integer.parseInt(text_cod_pais.getText()));
                pa.setNom_pais(text_nom_pais.getText());
                rp.delete_pais(pa);
                text_nom_pais.setText("");
                text_cod_pais.setText("");
                blo_cam_pais(false);
                blockBotones_pais(false);

                loadTPais("");
            }

        }


    }//GEN-LAST:event_btn_delete_paisActionPerformed

    private void btn_clean_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clean_stateActionPerformed
        // TODO add your handling code here:
        pinta_text();
        clean_state();
        blo_cam_estado(false);
        botones_state(false, false);
    }//GEN-LAST:event_btn_clean_stateActionPerformed

    private void bnt_clean_ciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_clean_ciuActionPerformed
        // TODO add your handling code here:
        clean_city();
        pinta_text();
        blo_cam_ciu(false);
        botones_city(false, false);

    }//GEN-LAST:event_bnt_clean_ciuActionPerformed

    private void btn_delete_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_stateActionPerformed
        // TODO add your handling code here:
        //Eliminar provincia
        int n = 2;//combo_pais_prov
        if (form_validado(n)) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Provincia",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {

                prov.setCod_prov(Integer.parseInt(text_cod_prov.getText()));
                prov.setCod_pais(rp.ObtCodPais(combo_pais_prov.getSelectedItem().toString()));
                System.out.println(rp.ObtCodPais(combo_pais_prov.getSelectedItem().toString()));
                prov.setNom_prov(text_prov.getText());
                rp.delete_prov(prov);
                loadEstado("");
                clean_state();
                blo_cam_estado(false);
                blockBotones_estate(false);

            }
        }


    }//GEN-LAST:event_btn_delete_stateActionPerformed

    private void btn_delete_cityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_cityActionPerformed

        int n = 3;
        if (form_validado(n)) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Ciudad",
                    "  ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {

                ciu.setCod_ciu(Integer.parseInt(text_cod_ciu.getText()));
                ciu.setCod_prov(rp.ObtCodProv(cmb_prov.getSelectedItem().toString()));
                ciu.setNom_ciu(text_ciudad.getText());
                rp.delete_ciu(ciu);
                load_ciu("");
                clean_city();
                blo_cam_ciu(false);
                blockBotones_city(false);
            }
        }
    }//GEN-LAST:event_btn_delete_cityActionPerformed

    private void txtBusCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusCiuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusCiuActionPerformed

    private void txtBuscProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscProvActionPerformed

    private void comboPaisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboPaisKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_Combo(comboPais)) {
            Validaciones.pinta_Combo(comboPais);
        }
    }//GEN-LAST:event_comboPaisKeyReleased

    private void cmb_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmb_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_Combo(cmb_prov)) {
            Validaciones.pinta_Combo(cmb_prov);
        }
    }//GEN-LAST:event_cmb_provKeyReleased

    private void combo_pais_provKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_pais_provKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_Combo(combo_pais_prov)) {
            Validaciones.pinta_Combo(combo_pais_prov);
        }
    }//GEN-LAST:event_combo_pais_provKeyReleased

    private void cmb_provActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_provActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(cmb_prov);
    }//GEN-LAST:event_cmb_provActionPerformed

    private void combo_pais_provActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_pais_provActionPerformed
        // TODO add your handling code here:
        Validaciones.pinta_Combo(combo_pais_prov);
    }//GEN-LAST:event_combo_pais_provActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_pais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_pais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_pais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_pais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_pais dialog = new vntana_pais(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bnt_clean_ciu;
    private javax.swing.JButton btnAct_pais;
    private javax.swing.JButton btnSave_pais;
    private javax.swing.JButton btn_act_city;
    private javax.swing.JButton btn_busc_city;
    private javax.swing.JButton btn_clean_pais;
    private javax.swing.JButton btn_clean_state;
    private javax.swing.JButton btn_cus_state;
    private javax.swing.JButton btn_delete_city;
    private javax.swing.JButton btn_delete_pais;
    private javax.swing.JButton btn_delete_state;
    private javax.swing.JButton btn_new_ciu;
    private javax.swing.JButton btn_new_pais;
    private javax.swing.JButton btn_new_state;
    private javax.swing.JButton btn_save_city;
    private javax.swing.JButton btn_save_state;
    private javax.swing.JButton btn_update_state;
    private javax.swing.JButton btnbusTodoP;
    private javax.swing.JComboBox cmb_prov;
    private javax.swing.JComboBox comboPais;
    private javax.swing.JComboBox combo_pais_prov;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel lblcab;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lblpais;
    private javax.swing.JLayeredPane panel_ciu;
    private javax.swing.JPanel pnel_all;
    private javax.swing.JPanel pnel_all_ciu;
    private javax.swing.JPanel pnel_all_prov;
    private javax.swing.JPanel pnel_ciu0;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_prov0;
    private javax.swing.JToolBar tbar;
    private javax.swing.JToolBar tbar_ciu;
    private javax.swing.JToolBar tbarprv;
    private javax.swing.JTable tblCiu;
    private javax.swing.JTable tblPais;
    private javax.swing.JTable tblProv;
    private javax.swing.JTextField text_ciudad;
    private javax.swing.JTextField text_cod_ciu;
    private javax.swing.JTextField text_cod_pais;
    private javax.swing.JTextField text_cod_prov;
    private javax.swing.JTextField text_nom_pais;
    private javax.swing.JTextField text_prov;
    private javax.swing.JTextField txtBusCiu;
    private javax.swing.JTextField txtBuscProv;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
