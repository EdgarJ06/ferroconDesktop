package Modelo;

//import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Edgar
 */
public class Validaciones {

    cn_BaseDeDatos bd = new cn_BaseDeDatos();
    

    //pinta color normal de fondo y borde     
    public static void pinta_text(JTextField txt) {
        txt.setBorder(BorderFactory.createLineBorder(Color.gray));
        txt.setBackground(Color.white);
    }

    public static void pinta_Combo(JComboBox txt) {
        txt.setBorder(BorderFactory.createLineBorder(Color.gray));
        txt.setBackground(Color.white);
    }

    public static void pinta_text(JTextArea txt) {
        txt.setBorder(BorderFactory.createLineBorder(Color.gray));
        txt.setBackground(Color.white);
    }

    public static boolean es_passward(JPasswordField txt) {
        boolean ok = true;
        char contrasena[] = txt.getPassword();
        String contra = new String(contrasena);
        if (contra.equals("")) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    public static boolean es_passward_True(JPasswordField txt, String dato) {
        boolean ok = true;
        char contrasena[] = txt.getPassword();
        String contra = new String(contrasena);

        if (!contra.equals(dato)) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    public static boolean es_Combo(JComboBox txt) {
        boolean ok = true;
        if (txt.getSelectedItem().equals("")) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    //verifica si es requerido
    public static boolean esRequerido(JTextField txt) {
        boolean ok = true;
        if (txt.getText().trim().equals("")) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    public static boolean esRequerido(JTextArea txt) {
        boolean ok = true;
        if (txt.getText().trim().equals("")) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    //verifica flotantes    
    public static boolean esFlotante(JTextField txt) {
        boolean ok = true;
        if (!txt.getText().trim().matches("[0-9]{1,10}[.]{0,1}[0-9]{0,}")) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    //verifica si es email
    public static boolean esEmail(JTextField txt) {
        boolean ok = true;
        //if (!txt.getText().trim().matches("[a-z]{1,}[@]{1}[a-z]{1,}[.]{1}[a-z]{1,}[.]{0,1}[a-z]{0,2}"))
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(txt.getText().trim());
        if (!mat.find()) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    public static boolean esTelefono(JTextField txt) {
        boolean ok = true;
        if (!txt.getText().trim().matches("[0-9]{7,13}")) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    public static boolean esCedula(JTextField txt) {
        boolean ok = true;
        if (!((txt.getText().trim().length() > 1))) {
       // if (!((txt.getText().trim().length() == 10) || (txt.getText().trim().length() == 13))) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    public static boolean esLetras(JTextField txt) {
        boolean ok = true;
        if (!txt.getText().trim().matches("[a-zA-ZáéíóúÁÉÍÓÚÑñ ]{2,}")) {
            txt.setBorder(BorderFactory.createLineBorder(Color.red));
            txt.setBackground(Color.pink);
            ok = false;
        }
        return ok;
    }

    //Validacion de la cedula 
    public static boolean validadorDeCedula(String cedula) {//Validar cedula
        boolean cedulaCorrecta = false;
        if (!cedula.equals("2222222222")) {
            try {

                if (cedula.length() == 10) // ConstantesApp.LongitudCedula
                {
                    int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                    JOptionPane.showMessageDialog(null, "el terce digito es" + tercerDigito);
                    if (tercerDigito < 6) {
                        int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                        int verificador = Integer.parseInt(cedula.substring(9, 10));
                        int suma = 0;
                        int digito = 0;
                        for (int i = 0; i < (cedula.length() - 1); i++) {
                            digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                            suma += ((digito % 10) + (digito / 10));
                        }

                        if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                            cedulaCorrecta = true;
                        } else if ((10 - (suma % 10)) == verificador) {
                            cedulaCorrecta = true;
                        } else {
                            cedulaCorrecta = false;
                        }
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } catch (NumberFormatException nfe) {
                cedulaCorrecta = false;
            } catch (Exception err) {
                System.out.println("Una excepcion ocurrio en el proceso de validadcion");
                cedulaCorrecta = false;
            }

        } else if (!cedulaCorrecta) {
            JOptionPane.showMessageDialog(null, "NO es correcto");

        }
        if (!cedulaCorrecta) {
            //txtLabel.setText("Cedula incorrecta");
            //JOptionPane.showMessageDialog(null,"No es correcto tampoos");

        }
        return cedulaCorrecta;
    }

 

    public static void convertiraMayusculasEnJtextfield(javax.swing.JTextField jTextfieldS) {
        String cadena = (jTextfieldS.getText()).toUpperCase();
        jTextfieldS.setText(cadena);
    }
     public static String fechaActualHoraSql() {
        Date fecha = new Date();
         SimpleDateFormat formatofecha = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return formatofecha.format(fecha);
    }
    
    public static String fechaActualSql(){
        Date fecha = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("YYYY-MM-dd");
        //return fecha
        return formatdate.format(fecha);
    }

    public static String fechaActual() {
        Date fecha = new Date();
         SimpleDateFormat formatofecha = new SimpleDateFormat("dd-MM-YYYY");
        // return fecha;
        return formatofecha.format(fecha);
    }
    
     public static String fechaActualHora() {
        Date fecha = new Date();
         SimpleDateFormat formatofecha = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        return formatofecha.format(fecha);
    }
     
    public static Date ParseFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-YYYY");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return fechaDate;
    }
    
    public static String ParseFechaSql(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-YYYY");
        
        //SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd");
        Date fechaDate = new Date();
        
        return formato.format(fechaDate);
    }

    public String cod_tablasCiudad(String sql) {//PONER CODIGO A LOS OBJETOS
        Connection cn = bd.Conectar();
        String n = "", r = "";
        int num = 0;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                n = rs.getString(1);
            }
            num = Integer.parseInt(n) + 1;
            bd.desconectar();
        } catch (Exception err) {
            System.out.println("Error:" + err);
        } catch (Throwable ex) {
            Logger.getLogger(Validaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return num + "";
    }

    public int cod_tablas(String sql) {//PONER CODIGO A LOS OBJETOS
        Connection cn = bd.Conectar();
        int n = 0;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                n = rs.getInt(1);
            }
             bd.desconectar();
        } catch (Exception err) {
            System.out.println(err);
        } catch (Throwable ex) {
            Logger.getLogger(Validaciones.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        return n + 1;
    }

    public int count(String sql) {
        int c = 0;
        try {
            Connection cn = bd.Conectar();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                c = rs.getInt(1);
            }
            bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Throwable ex) {
            Logger.getLogger(Validaciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return c;
    }
//Codigos para la factura

    public String countFact(String sql) {
        String c = "";
        try {
            Connection cn = bd.Conectar();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                c = rs.getString(1);
            }
            bd.desconectar();

        } catch (Throwable ex) {
            Logger.getLogger(Validaciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    //
    public String codFacturas(String sql) {
        String codFac = "F";
        String num = countFact(sql);
        String cadena = "";
        String aux = "";
        String numero = "";
        int ok = 0;
        if (num != null) {
            for (int i = 0; i < num.length(); i++) {
                char letra = num.charAt(i);
                if ((letra != '0' && letra != 'F') || (ok != 0)) {
                    aux = aux + letra;
                    ok = 1;
                } else {
                    cadena = cadena + letra;
                }
            }//fin del for 
            numero = cadena + (Integer.parseInt(aux) + 1) + "";
            if (numero.length() > 8) {
                for (int i = 2; i < numero.length(); i++) {
                    char d = numero.charAt(i);
                    codFac = codFac + d;
                }
                numero = codFac;
            }
            return numero;
        }
        return "F0000001";
    }
    
    
     public String codPedido(String sql) {
        String codFac = "P";
        String num = countFact(sql);
        String cadena = "";
        String aux = "";
        String numero = "";
        int ok = 0;
        if (num != null) {
            for (int i = 0; i < num.length(); i++) {
                char letra = num.charAt(i);
                if ((letra != '0' && letra != 'P') || (ok != 0)) {
                    aux = aux + letra;
                    ok = 1;
                } else {
                    cadena = cadena + letra;
                }
            }//fin del for 
            numero = cadena + (Integer.parseInt(aux) + 1) + "";
            if (numero.length() > 8) {
                for (int i = 2; i < numero.length(); i++) {
                    char d = numero.charAt(i);
                    codFac = codFac + d;
                }
                numero = codFac;
            }
            return numero;
        }
        return "P0000001";
    }
     
     
     
     //Tiempo de fechas
    
     public static Date sumarDiasAFecha(Date fecha, int dias){         
      if (dias==0) return fecha;
      
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(fecha); 
      calendar.add(Calendar.DAY_OF_YEAR, dias);  
     // SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
   
       return calendar.getTime();
     // return formato.format(calendar.getTime());
}
     
     
    
    
    
}
