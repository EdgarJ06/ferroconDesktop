/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.reg_caja;
import Controlador.reg_permisos;
import Modelo.Permisos;
import Modelo.Validaciones;
import Modelo.det_caja;
import java.awt.Color;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class vntana_gastos extends javax.swing.JDialog {

    det_caja dtc = new det_caja();

    reg_permisos rperm = new reg_permisos();
    boolean delet = false;
    boolean update = false;
    DefaultTableModel datos;

    public static String codigo;
    ArrayList<Permisos> listPerm;

  
    String ced;
    static reg_caja rc = new reg_caja();
    String id = "";

    public vntana_gastos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);//Centrar ventana
        setIconImage(new ImageIcon(getClass().getResource("/iconos/tools.png")).getImage());      
       
        try {

            text_date.setText(Validaciones.fechaActual());
            date1.setText(Validaciones.fechaActual());
            date2.setText(Validaciones.fechaActual());
            blockBotones(false);//desactivado todos los botones 
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            campos(false);
            bloquearColumTable(date1.getText(), date2.getText());
           
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

    public void bloquearColumTable(String date1, String date2) {//edicion de la tabla
        try {

            tabla_gastos.setModel(rc.load_detalle_caja(rc.operaciones_gastos(date1, date2)));
            modelo_tabla();
            countGastos();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void modelo_tabla() {
        tabla_gastos.getColumnModel().getColumn(0).setPreferredWidth(1);
        tabla_gastos.getColumnModel().getColumn(1).setPreferredWidth(350);
        tabla_gastos.getColumnModel().getColumn(2).setPreferredWidth(2);
        tabla_gastos.getColumnModel().getColumn(3).setPreferredWidth(2);
        tabla_gastos.getColumnModel().getColumn(4).setPreferredWidth(2);
        tabla_gastos.getTableHeader().setBackground(Color.decode("#666666"));
        tabla_gastos.getTableHeader().setForeground(Color.white);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tabla_gastos.getColumnModel().getColumn(0).setCellRenderer(tcr);
    }

    public void limpiar_campos() {
        // text_cod_caja.setText("");
        text_costo.setText("");

        text_descrip.setText("");
        //  text_date.setText("");

        pinta_text();
    }

    public void campos(boolean ok) {
        text_costo.setEnabled(ok);

        text_descrip.setEnabled(ok);
        text_date.setEnabled(ok);

        pinta_text();
    }

    public void pinta_text() {
        Validaciones.pinta_text(text_costo);

        Validaciones.pinta_text(text_descrip);
        Validaciones.pinta_text(text_date);

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
        if (!Validaciones.esFlotante(text_costo)) {
            ok = false;
            men += "Costo";
        }

        if (!Validaciones.esRequerido(text_descrip)) {
            ok = false;
            men += "descrip";
        }
        if (!Validaciones.esRequerido(text_date)) {
            ok = false;
            men += "Fecha ";
        }
        return ok;
    }

    public void guardarGastos() {

        dtc.setId_per_caja(rc.cod_caja_activa());//codigo de la caja activa 
        dtc.setFecha(Validaciones.fechaActual()); //fecha actual
        dtc.setDescripcion(text_descrip.getText()); //venta de algo      
        dtc.setCod_vntas(null);//codigo de la venta
        dtc.setIngreso(0.0);//total
        dtc.setCod_pedido(null);//si es pedido codiog
        dtc.setEgreso(Double.parseDouble(text_costo.getText()));//total del pago por pedido
        
        rc.SaveDetalleApertCaja(dtc);
        JOptionPane.showMessageDialog(null, "Datos Registrados Correctamente");

    }

    public void update_gastos() {
        try {
            //id_detalle
            dtc.setId_det(Integer.parseInt(id));
            dtc.setId_per_caja(rc.cod_caja_activa());//codigo de la caja activa 
            dtc.setFecha(Validaciones.fechaActual()); //fecha actual
            dtc.setDescripcion(text_descrip.getText()); //venta de algo      
            dtc.setCod_vntas(null);//codigo de la venta
            dtc.setIngreso(0.0);//total
            dtc.setCod_pedido(null);//si es pedido codiog
            dtc.setEgreso(Double.parseDouble(text_costo.getText()));//total del pago por pedido
            rc.updateDetalleApertCaja(dtc);
            JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");

            botones(false, false);
            limpiar_campos();
             bloquearColumTable(date1.getText(), date2.getText());
            campos(false);
        } catch (Throwable ex) {

        }
    }

    public void delete() {
        dtc.setId_det(Integer.parseInt(id));
        rc.deleteDetallCaja(dtc);//eliminar los datos        
        limpiar_campos();
        campos(false);
        bloquearColumTable(date1.getText(), date2.getText());
        botones(false, false);
    }

     public void countGastos() {
        try {
            int cont = 0;
            double subt = 0, iva = 0, total = 0;
            for (int i = 0; i < tabla_gastos.getRowCount(); i++) {
                cont++;
               /* subt = subt + Double.parseDouble(tabla_gastos.getValueAt(i, 4).toString());
                iva = iva + Double.parseDouble(tabla_gastos.getValueAt(i, 5).toString());
                total = total + Double.parseDouble(tabla_gastos.getValueAt(i, 7).toString());*/
           
            }
           /* txtSubt.setText(fd.format(subt).replace(",", "."));
            txtIva.setText(fd.format(iva).replace(",", "."));
            txtTot.setText(fd.format(total).replace(",", "."));*/
            text_cont.setText(cont + "");
        } catch (Exception err) {
            System.out.println(err);
            // JOptionPane.showMessageDialog(this,"No existen datos");
        }

    }
     public void refres(){
        try {

            text_date.setText(Validaciones.fechaActual());
            date1.setText(Validaciones.fechaActual());
            date2.setText(Validaciones.fechaActual());
            blockBotones(false);//desactivado todos los botones 
            ced = menu_principal.ced_usu;
            loadPermisos(ced);
            campos(false);
            bloquearColumTable(date1.getText(), date2.getText());
           
        } catch (Exception err) {
            System.out.println("Aqui el error:" + err);
        }
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel4 = new javax.swing.JPanel();
        text_cont = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pnl_cab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tbar = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        btn_imp_cli = new javax.swing.JButton();
        pnel_inf = new javax.swing.JPanel();
        lblced = new javax.swing.JLabel();
        text_cod_gasto = new javax.swing.JTextField();
        text_costo = new javax.swing.JTextField();
        lbldir = new javax.swing.JLabel();
        text_date = new javax.swing.JTextField();
        text_descrip = new javax.swing.JTextField();
        lbltelf = new javax.swing.JLabel();
        lblcod = new javax.swing.JLabel();
        pnel_inf1 = new javax.swing.JPanel();
        lblbuscli = new javax.swing.JLabel();
        date1 = new javax.swing.JTextField();
        date2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblbuscli1 = new javax.swing.JLabel();
        btnbusTodo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_gastos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gastos");

        panel4.setBackground(new java.awt.Color(204, 204, 204));
        panel4.setToolTipText("");

        text_cont.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        text_cont.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_cont.setText("0");
        text_cont.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel10.setText("Cantidad de Registros:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Doble clic para seleccionar");

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(463, 463, 463)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(text_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnl_cab.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Gastos");

        javax.swing.GroupLayout pnl_cabLayout = new javax.swing.GroupLayout(pnl_cab);
        pnl_cab.setLayout(pnl_cabLayout);
        pnl_cabLayout.setHorizontalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cabLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addContainerGap(789, Short.MAX_VALUE))
        );
        pnl_cabLayout.setVerticalGroup(
            pnl_cabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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

        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sincronizar.png"))); // NOI18N
        jButton2.setText("Recargar");
        jButton2.setFocusable(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        tbar.add(jButton2);
        tbar.add(jSeparator1);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Imprimir Gastos:");
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

        pnel_inf.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblced.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblced.setText("Monto Q:");
        pnel_inf.add(lblced, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, 20));

        text_cod_gasto.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_cod_gasto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_cod_gasto.setEnabled(false);
        pnel_inf.add(text_cod_gasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 50, -1));

        text_costo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_costo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_costoFocusLost(evt);
            }
        });
        text_costo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_costoKeyReleased(evt);
            }
        });
        pnel_inf.add(text_costo, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 50, -1));

        lbldir.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbldir.setText("Descripción:");
        pnel_inf.add(lbldir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        text_date.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_date.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        text_date.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_dateFocusLost(evt);
            }
        });
        text_date.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_dateKeyReleased(evt);
            }
        });
        pnel_inf.add(text_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 170, -1));

        text_descrip.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        text_descrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_descripFocusLost(evt);
            }
        });
        text_descrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_descripKeyReleased(evt);
            }
        });
        pnel_inf.add(text_descrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 720, -1));

        lbltelf.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lbltelf.setText("Fecha:");
        pnel_inf.add(lbltelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, 20));

        lblcod.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblcod.setText("Código:");
        pnel_inf.add(lblcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        pnel_inf1.setBackground(new java.awt.Color(234, 234, 234));
        pnel_inf1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnel_inf1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblbuscli.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblbuscli.setText("Fecha fin:");
        pnel_inf1.add(lblbuscli, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, 20));

        date1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        date1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                date1KeyReleased(evt);
            }
        });
        pnel_inf1.add(date1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 150, -1));

        date2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        pnel_inf1.add(date2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 170, -1));

        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lupa.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnel_inf1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 0, -1, 50));

        lblbuscli1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblbuscli1.setText("Fecha inicio:");
        pnel_inf1.add(lblbuscli1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, 20));

        btnbusTodo.setBackground(new java.awt.Color(0, 153, 102));
        btnbusTodo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnbusTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnbusTodo.setText("Todo");
        btnbusTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusTodoActionPerformed(evt);
            }
        });

        tabla_gastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla_gastos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_gastosMouseClicked(evt);
            }
        });
        tabla_gastos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_gastosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabla_gastos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_cab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnel_inf1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnbusTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_cab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnel_inf, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnel_inf1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(btnbusTodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_costoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_costoFocusLost
        // TODO add your handling code here:
        Validaciones.esFlotante(text_costo);
    }//GEN-LAST:event_text_costoFocusLost

    private void text_costoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_costoKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esFlotante(text_costo)) {
            Validaciones.pinta_text(text_costo);
        }
    }//GEN-LAST:event_text_costoKeyReleased

    private void text_dateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_dateFocusLost
        // TODO add your handling code here:
        Validaciones.esRequerido(text_date);
    }//GEN-LAST:event_text_dateFocusLost

    private void text_dateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_dateKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_date)) {
            Validaciones.pinta_text(text_date);
        }
    }//GEN-LAST:event_text_dateKeyReleased

    private void text_descripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_descripFocusLost
        // TODO add your handling code here:
        Validaciones.esRequerido(text_descrip);
    }//GEN-LAST:event_text_descripFocusLost

    private void text_descripKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_descripKeyReleased
        // TODO add your handling code here:
        if (Validaciones.esRequerido(text_descrip)) {
            Validaciones.pinta_text(text_descrip);
        }
    }//GEN-LAST:event_text_descripKeyReleased

    private void date1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date1KeyReleased

        //  bloquearColumTable(text_busc_cli.getText());
    }//GEN-LAST:event_date1KeyReleased

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        campos(true);

        text_cod_gasto.setText( rc.cod_caja_activa() + "");

        botones(true, false);
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed

        if (form_validado()) {
            guardarGastos();
            limpiar_campos();
             bloquearColumTable(date1.getText(), date2.getText());
            campos(false);
            botones(false, false);
            //  text_cont.setText(rcl.count() + "");
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Actualizar Registro",
                    " Mensaje ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                update_gastos();
            }
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
        limpiar_campos();
        campos(false);
        botones(false, false);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        if (form_validado()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Desea Eliminar Registro",
                    " Eliminar ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si    ", "   No",}, "Si");
            if (seleccion == JOptionPane.YES_OPTION) {
                  delete();
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_imp_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imp_cliActionPerformed

         try {
            if (!text_cont.getText().equals("0")) {
                Date fec1;// = Date.valueOf(txtFec1.getText());
                Date fec2;//= Date.valueOf(txtFec2.getText());
                if (date1.getText().equals("") || date2.getText().equals("")) {
                    fec1 = Date.valueOf("1000-01-01");
                    fec2 = Date.valueOf("4000-01-01");
                } else {
                    fec1 = Date.valueOf(date1.getText());
                    fec2 = Date.valueOf(date2.getText());
                }
                JOptionPane.showMessageDialog(this, "Realizando operación.......Espere un momento");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("date1", fec1);
                params.put("date2", fec2);
                Reportes_g.generarReporte("rp_ingresos_egresos", params);
            } else {
                JOptionPane.showMessageDialog(this, "No existen datos");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Existe un error");
            System.out.println(ex);
        }
    }//GEN-LAST:event_btn_imp_cliActionPerformed

    private void btnbusTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusTodoActionPerformed
        // TODO add your handling code here:
       date1.setText("");
       date2.setText("");
        try {

            tabla_gastos.setModel(rc.load_detalle_caja(rc.operaciones_gastosAll()));
            modelo_tabla();
            countGastos();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }//GEN-LAST:event_btnbusTodoActionPerformed

    private void tabla_gastosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_gastosMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            id = (tabla_gastos.getValueAt(tabla_gastos.getSelectedRow(), 0).toString());

            text_cod_gasto.setText(tabla_gastos.getValueAt(tabla_gastos.getSelectedRow(), 0).toString());

            text_descrip.setText(tabla_gastos.getValueAt(tabla_gastos.getSelectedRow(), 1).toString());

            text_date.setText(tabla_gastos.getValueAt(tabla_gastos.getSelectedRow(), 2).toString());
            text_costo.setText(tabla_gastos.getValueAt(tabla_gastos.getSelectedRow(), 4).toString());

            pinta_text();
            campos(true);
            if (delet == true && update == true) {
                botones(false, true);

            } else {
                actBotones(update, delet);
            }
        }
    }//GEN-LAST:event_tabla_gastosMouseClicked

    private void tabla_gastosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_gastosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla_gastosKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        bloquearColumTable(date1.getText(),date2.getText());
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        refres();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(vntana_gastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vntana_gastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vntana_gastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vntana_gastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vntana_gastos dialog = new vntana_gastos(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField date1;
    private javax.swing.JTextField date2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JLabel lblbuscli;
    private javax.swing.JLabel lblbuscli1;
    private javax.swing.JLabel lblced;
    private javax.swing.JLabel lblcod;
    private javax.swing.JLabel lbldir;
    private javax.swing.JLabel lbltelf;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel pnel_inf;
    private javax.swing.JPanel pnel_inf1;
    private javax.swing.JPanel pnl_cab;
    private javax.swing.JTable tabla_gastos;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextField text_cod_gasto;
    private javax.swing.JLabel text_cont;
    private javax.swing.JTextField text_costo;
    private javax.swing.JTextField text_date;
    private javax.swing.JTextField text_descrip;
    // End of variables declaration//GEN-END:variables
}
