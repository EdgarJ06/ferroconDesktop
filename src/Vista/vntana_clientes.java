package Vista;

import Controlador.reg_clientes;
import Controlador.reg_pais;
import Controlador.reg_permisos;
import Controlador.reg_personal;
import Modelo.Ciudad;
import Modelo.Cliente;
import Modelo.Permisos;
import Modelo.Validaciones;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
public class vntana_clientes extends javax.swing.JDialog {

    static reg_clientes rcl = new reg_clientes();
    static reg_pais rp = new reg_pais();
    Cliente cl = new Cliente();
    reg_permisos rperm = new reg_permisos();
    reg_personal rpersonal = new reg_personal();
    boolean delet = false;
    boolean update = false;
    DefaultTableModel datos;
    ArrayList<Cliente> listCli;
    public static String codigo;
    ArrayList<Permisos> listPerm;
    ArrayList<Ciudad> comCiu;
    String ced;

    public vntana_clientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);//Centrar ventana
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
       try {
            blockBotones(false);//desactivado todos los botones 
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            campos(false);
            cargarComboCiudad();
            bloquearColumTable("");
            text_cont.setText(rcl.count() + "");
        } catch (Exception err) {
            System.out.println("Aqui el error:" + err);
        }
    }

  

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

    public void blockBotones(boolean ok) {
        btn_nuevo.setEnabled(ok);
        btn_agregar.setEnabled(ok);
        btn_actualizar.setEnabled(ok);
        btn_delete.setEnabled(ok);
    }

    public void cargarComboCiudad() {
        comCiu = rp.CargarListCiu("");
        combo_ciudad.removeAllItems();
        combo_ciudad.addItem("");
        for (Ciudad c : comCiu) {
            //JOptionPane.showMessageDialog(this, c.getNom_ciu());
            combo_ciudad.addItem(c.getNom_ciu());
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

    public void modelo_tabla() {
        tabla_cli.getColumnModel().getColumn(0).setPreferredWidth(1);
        tabla_cli.getColumnModel().getColumn(1).setPreferredWidth(30);
        tabla_cli.getColumnModel().getColumn(2).setPreferredWidth(50);
        tabla_cli.getColumnModel().getColumn(3).setPreferredWidth(50);
        tabla_cli.getColumnModel().getColumn(4).setPreferredWidth(200);
        tabla_cli.getColumnModel().getColumn(5).setPreferredWidth(50);
        tabla_cli.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabla_cli.getColumnModel().getColumn(7).setPreferredWidth(50);
        
        tabla_cli.getTableHeader().setBackground(Color.decode("#666666"));
        tabla_cli.getTableHeader().setForeground(Color.white);
       
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tabla_cli.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public void load_table(String dato) {//cargar tabla clientes
        datos = rcl.load_clientes();//manda a llamar el header de la tabla 
        String[] data = new String[8];
        listCli = rcl.llenar_Clientes(rcl.load_cliente(dato));
        for (Cliente cl : listCli) {
            data[0] = cl.getCod_clien() + "";
            data[1] = cl.getCed();
            data[2] = cl.getNombre();
            data[3] = cl.getApellido();
            data[4] = cl.getDireccion();
            data[5] = cl.getTelefono();
            data[6] = cl.getCorreo_elect();
            data[7] = rp.ObtNomCiu(cl.getCod_ciu() + "");
            datos.addRow(data);
            tabla_cli.setModel(datos);
        }
    }

    public void limpiar_campos() {
        text_cod_cli.setText("");
        text_ced_cli.setText("");
        text_nom_cli.setText("");
        text_ape_cli.setText("");
        text_dir_cli.setText("");
        text_telf_cli.setText("");
        text_correo_cli.setText("");
        combo_ciudad.setSelectedIndex(0);
        pinta_text();
    }

    public void campos(boolean ok) {
        text_ced_cli.setEnabled(ok);
        text_nom_cli.setEnabled(ok);
        text_ape_cli.setEnabled(ok);
        text_dir_cli.setEnabled(ok);
        text_telf_cli.setEnabled(ok);
        text_correo_cli.setEnabled(ok);
        combo_ciudad.setEnabled(ok);
        pinta_text();
    }

    public void pinta_text() {
        Validaciones.pinta_text(text_ced_cli);
        Validaciones.pinta_text(text_nom_cli);
        Validaciones.pinta_text(text_ape_cli);
        Validaciones.pinta_text(text_dir_cli);
        Validaciones.pinta_text(text_telf_cli);
        Validaciones.pinta_text(text_correo_cli);
        Validaciones.pinta_Combo(combo_ciudad);
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

    public boolean form_validado() {
        boolean ok = true;
        String men = "Campos requeridos o con errores: ";
        if (!Validaciones.esCedula(text_ced_cli)) {
            ok = false;
            men += "Ruc-Ci ";
        }

        if (!Validaciones.esLetras(text_nom_cli)) {
            ok = false;
            men += "Nombre ";
        }
        if (!Validaciones.esLetras(text_ape_cli)) {
            ok = false;
            men += "Apellido ";
        }
        if (!Validaciones.es_Combo(combo_ciudad)) {
            ok = false;
        }

        if (!Validaciones.esRequerido(text_dir_cli)) {
            ok = false;
            men += "Dirección ";
        }
        if (!Validaciones.esTelefono(text_telf_cli)) {
            ok = false;
            men += "Telefono ";
        }
        return ok;
    }

    public void update_client() {
        try {
            cl.setCod_clien(Integer.parseInt(text_cod_cli.getText()));
            cl.setCed(text_ced_cli.getText());
            cl.setNombre(text_nom_cli.getText());
            cl.setApellido(text_ape_cli.getText());
            cl.setDireccion(text_dir_cli.getText());
            cl.setTelefono(text_telf_cli.getText());
            cl.setCorreo_elect(text_correo_cli.getText());
            cl.setCod_ciu(rp.ObtCodCiu(combo_ciudad.getSelectedItem().toString()));
            rcl.update(cl);//guardar los datos
            botones(false, false);
            limpiar_campos();
            bloquearColumTable("");
            campos(false);
        } catch (Throwable ex) {
            Logger.getLogger(vntana_clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete() {
        cl.setCod_clien(Integer.parseInt(text_cod_cli.getText()));
        rcl.Eliminar(cl);//guardar los datos        
        limpiar_campos();
        campos(false);
        bloquearColumTable("");
        botones(false, false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel4 = new javax.swing.JPanel();
        pnel_inf = new javax.swing.JPanel();
        lblced = new javax.swing.JLabel();
        text_correo_cli = new javax.swing.JTextField();
        text_cod_cli = new javax.swing.JTextField();
        lblemail = new javax.swing.JLabel();
        lblciu = new javax.swing.JLabel();
        lblnom = new javax.swing.JLabel();
        combo_ciudad = new javax.swing.JComboBox();
        text_ced_cli = new javax.swing.JTextField();
        text_ape_cli = new javax.swing.JTextField();
        text_nom_cli = new javax.swing.JTextField();
        lbldir = new javax.swing.JLabel();
        text_telf_cli = new javax.swing.JTextField();
        text_dir_cli = new javax.swing.JTextField();
        lblape = new javax.swing.JLabel();
        lbltelf = new javax.swing.JLabel();
        lblcod = new javax.swing.JLabel();
        pnel_inf1 = new javax.swing.JPanel();
        lblbuscli = new javax.swing.JLabel();
        text_busc_cli = new javax.swing.JTextField();
        tbar = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btnciu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        btn_imp_cli = new javax.swing.JButton();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnbusTodo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_cli = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        text_cont = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CLIENTES");

        panel4.setToolTipText("");
        panel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblced.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblced.setText("DPI:");
        pnel_inf.add(lblced, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, 20));

        text_correo_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_correo_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_correo_cliActionPerformed(evt);
            }
        });
        text_correo_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_correo_cliKeyReleased(evt);
            }
        });
        pnel_inf.add(text_correo_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 230, -1));

        text_cod_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_cod_cli.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_cli.setEnabled(false);
        pnel_inf.add(text_cod_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 30, -1));

        lblemail.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblemail.setText("NIT:");
        pnel_inf.add(lblemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 40, 20));

        lblciu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblciu.setText("Sector:");
        pnel_inf.add(lblciu, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 50, 20));

        lblnom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblnom.setText("Nombre:");
        pnel_inf.add(lblnom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        combo_ciudad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        combo_ciudad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---" }));
        combo_ciudad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                combo_ciudadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combo_ciudadKeyReleased(evt);
            }
        });
        pnel_inf.add(combo_ciudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 230, -1));

        text_ced_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_ced_cli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_ced_cliFocusLost(evt);
            }
        });
        text_ced_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ced_cliKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                text_ced_cliKeyTyped(evt);
            }
        });
        pnel_inf.add(text_ced_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 140, -1));

        text_ape_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_ape_cli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_ape_cliFocusLost(evt);
            }
        });
        text_ape_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_ape_cliKeyReleased(evt);
            }
        });
        pnel_inf.add(text_ape_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 160, -1));

        text_nom_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_nom_cli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_nom_cliFocusLost(evt);
            }
        });
        text_nom_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_nom_cliKeyReleased(evt);
            }
        });
        pnel_inf.add(text_nom_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 210, -1));

        lbldir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbldir.setText("Dirección:");
        pnel_inf.add(lbldir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        text_telf_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_telf_cli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_telf_cliFocusLost(evt);
            }
        });
        text_telf_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_telf_cliKeyReleased(evt);
            }
        });
        pnel_inf.add(text_telf_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 160, -1));

        text_dir_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_dir_cli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_dir_cliFocusLost(evt);
            }
        });
        text_dir_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_dir_cliKeyReleased(evt);
            }
        });
        pnel_inf.add(text_dir_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 740, -1));

        lblape.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblape.setText("Apellido:");
        pnel_inf.add(lblape, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 60, 20));

        lbltelf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbltelf.setText("Télefono:");
        pnel_inf.add(lbltelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 20));

        lblcod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblcod.setText("Código:");
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        panel4.add(pnel_inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 820, 110));

        pnel_inf1.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblbuscli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblbuscli.setText("Buscar Clientes por DPI/Nombres:");
        pnel_inf1.add(lblbuscli, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        text_busc_cli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        text_busc_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_busc_cliActionPerformed(evt);
            }
        });
        text_busc_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_busc_cliKeyReleased(evt);
            }
        });
        pnel_inf1.add(text_busc_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 500, -1));

        panel4.add(pnel_inf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 196, 724, 40));

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

        btn_agregar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/salvar.png"))); // NOI18N
        btn_agregar.setText("Guardar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        tbar.add(btn_agregar);

        btn_actualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar-archivo.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        tbar.add(btn_actualizar);

        btn_cancelar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        btn_cancelar.setText("Limpiar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        tbar.add(btn_cancelar);

        btn_delete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/borrar.png"))); // NOI18N
        btn_delete.setText("Eliminar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        tbar.add(btn_delete);

        btnciu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnciu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/mapa.png"))); // NOI18N
        btnciu.setText("Sector");
        btnciu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnciuActionPerformed(evt);
            }
        });
        tbar.add(btnciu);
        tbar.add(jSeparator1);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Imprimir Clientes:");
        tbar.add(jLabel2);

        btn_imp_cli.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_imp_cli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imprimir.png"))); // NOI18N
        btn_imp_cli.setFocusable(false);
        btn_imp_cli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_imp_cli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_imp_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imp_cliActionPerformed(evt);
            }
        });
        tbar.add(btn_imp_cli);

        panel4.add(tbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 840, 40));

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));
        pnl_cab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Clientes");
        pnl_cab.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        panel4.add(pnl_cab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 28));

        btnbusTodo.setBackground(new java.awt.Color(0, 153, 102));
        btnbusTodo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnbusTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnbusTodo.setText("Todo");
        btnbusTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusTodoActionPerformed(evt);
            }
        });
        panel4.add(btnbusTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 196, 90, 40));

        tabla_cli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla_cli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_cliMouseClicked(evt);
            }
        });
        tabla_cli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_cliKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabla_cli);

        panel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 242, 820, 260));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Doble clic para seleccionar Cliente");

        jLabel10.setText("Cantidad de clientes:");

        text_cont.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        text_cont.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_cont)
                            .addComponent(jLabel10))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12))))
        );

        panel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 840, -1));

        getContentPane().add(panel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar cliente",
                    " Mensaje ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                update_client();
            }
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btnbusTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusTodoActionPerformed
        // TODO add your handling code here:
        bloquearColumTable("");
    }//GEN-LAST:event_btnbusTodoActionPerformed

    private void text_nom_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_nom_cliKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esLetras(text_nom_cli)) {
            Validaciones.pinta_text(text_nom_cli);
        }
    }//GEN-LAST:event_text_nom_cliKeyReleased

    private void text_ape_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ape_cliKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esLetras(text_ape_cli)) {
            Validaciones.pinta_text(text_ape_cli);
        }
    }//GEN-LAST:event_text_ape_cliKeyReleased

    private void text_dir_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dir_cliKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_dir_cli)) {
            Validaciones.pinta_text(text_dir_cli);
        }
    }//GEN-LAST:event_text_dir_cliKeyReleased

    private void text_correo_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_correo_cliKeyReleased
        // TODO add your handling code here:
        /*if (Validaciones.esEmail(text_correo_cli)) {
            Validaciones.pinta_text(text_correo_cli);
        }*/
    }//GEN-LAST:event_text_correo_cliKeyReleased

    private void text_telf_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_telf_cliKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esTelefono(text_telf_cli)) {
            Validaciones.pinta_text(text_telf_cli);
        }
    }//GEN-LAST:event_text_telf_cliKeyReleased

    private void text_ced_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ced_cliKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esCedula(text_ced_cli)) {
            Validaciones.pinta_text(text_ced_cli);
        }
    }//GEN-LAST:event_text_ced_cliKeyReleased

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        campos(true);
        text_cod_cli.setText(rcl.codigoclientes() + "");
        botones(true, false);

    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed

        if (form_validado()) {
            cl.setCod_clien(Integer.parseInt(text_cod_cli.getText()));
            cl.setCed(text_ced_cli.getText());
            cl.setNombre(text_nom_cli.getText());
            cl.setApellido(text_ape_cli.getText());
            cl.setDireccion(text_dir_cli.getText());
            cl.setTelefono(text_telf_cli.getText());
            cl.setCorreo_elect(text_correo_cli.getText());
            cl.setCod_ciu(rp.ObtCodCiu(combo_ciudad.getSelectedItem().toString()));
            rcl.insert(cl);
            limpiar_campos();
            bloquearColumTable("");
            campos(false);
            botones(false, false);
            text_cont.setText(rcl.count() + "");
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        campos(false);
        botones(false, false);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:     
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Cliente",
                    " Eliminar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                delete();
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void text_nom_cliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_nom_cliFocusLost
        // TODO add your handling code here:
        Validaciones.esLetras(text_nom_cli);
    }//GEN-LAST:event_text_nom_cliFocusLost

    private void text_ape_cliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_ape_cliFocusLost
        // TODO add your handling code here:
        Validaciones.esLetras(text_ape_cli);
    }//GEN-LAST:event_text_ape_cliFocusLost

    private void text_telf_cliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_telf_cliFocusLost
        // TODO add your handling code here:
        Validaciones.esTelefono(text_telf_cli);
    }//GEN-LAST:event_text_telf_cliFocusLost

    private void text_dir_cliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_dir_cliFocusLost
        // TODO add your handling code here:
        Validaciones.esRequerido(text_dir_cli);
    }//GEN-LAST:event_text_dir_cliFocusLost

    private void text_ced_cliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_ced_cliFocusLost
        // TODO add your handling code here:
        Validaciones.esCedula(text_ced_cli);
    }//GEN-LAST:event_text_ced_cliFocusLost

    //BUSCA LOS CLIENTES POR MEDIO DEL NOMBRE O APELLIDO INGRESADO
    private void text_busc_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_busc_cliKeyReleased

        bloquearColumTable(text_busc_cli.getText());

    }//GEN-LAST:event_text_busc_cliKeyReleased

    private void btnciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnciuActionPerformed

        vntana_pais dlg = new vntana_pais(null, false);
        dlg.setModal(true); //no permite acceder a otros formularios hasta que se cierre
        dlg.setVisible(true);
        //cargar todo nuevamente
        cargarComboCiudad();


    }//GEN-LAST:event_btnciuActionPerformed

    private void combo_ciudadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_ciudadKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_combo_ciudadKeyPressed

    private void combo_ciudadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_ciudadKeyReleased
        // TODO add your handling code here:
        if (Validaciones.es_Combo(combo_ciudad)) {
            Validaciones.pinta_Combo(combo_ciudad);
        }
    }//GEN-LAST:event_combo_ciudadKeyReleased

    private void btn_imp_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imp_cliActionPerformed

        try {
            if (!text_cont.getText().equals("0")) {
                Reportes_g.generarReporte("rep_clientes");
            } else {
                JOptionPane.showMessageDialog(null, "No existen datos","Error", JOptionPane.ERROR_MESSAGE);
           
               
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_btn_imp_cliActionPerformed

    private void tabla_cliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_cliKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tabla_cliKeyReleased

    private void tabla_cliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_cliMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            cargarComboCiudad();
            text_cod_cli.setText(tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 0).toString());
            text_ced_cli.setText(tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 1).toString());
            text_nom_cli.setText(tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 2).toString());
            text_ape_cli.setText(tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 3).toString());
            text_dir_cli.setText(tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 4).toString());
            text_correo_cli.setText(tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 6).toString());
            text_telf_cli.setText(tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 5).toString());
            String ciu = tabla_cli.getValueAt(tabla_cli.getSelectedRow(), 7).toString();

            combo_ciudad.setSelectedItem(ciu);
            pinta_text();
            campos(true);
            if (delet == true && update == true) {
                botones(false, true);

            } else {
                actBotones(update, delet);
            }
        }
    }//GEN-LAST:event_tabla_cliMouseClicked

    private void text_ced_cliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ced_cliKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if (((car < '0') || (car > '9')) && (car != KeyEvent.VK_BACK_SPACE) ) {
            evt.consume();
        }
    }//GEN-LAST:event_text_ced_cliKeyTyped

    private void text_correo_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_correo_cliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_correo_cliActionPerformed

    private void text_busc_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_busc_cliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_busc_cliActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_clientes dialog = new vntana_clientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_imp_cli;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btnbusTodo;
    private javax.swing.JButton btnciu;
    private javax.swing.JComboBox combo_ciudad;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JLabel lblape;
    private javax.swing.JLabel lblbuscli;
    private javax.swing.JLabel lblced;
    private javax.swing.JLabel lblciu;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lbldir;
    private javax.swing.JLabel lblemail;
    private javax.swing.JLabel lblnom;
    private javax.swing.JLabel lbltelf;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable tabla_cli;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_ape_cli;
    private javax.swing.JTextField text_busc_cli;
    private javax.swing.JTextField text_ced_cli;
    private javax.swing.JTextField text_cod_cli;
    private javax.swing.JLabel text_cont;
    private javax.swing.JTextField text_correo_cli;
    private javax.swing.JTextField text_dir_cli;
    private javax.swing.JTextField text_nom_cli;
    private javax.swing.JTextField text_telf_cli;
    // End of variables declaration//GEN-END:variables
}
