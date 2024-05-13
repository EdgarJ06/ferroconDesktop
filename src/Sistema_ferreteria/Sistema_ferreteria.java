/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sistema_ferreteria;

//import Vista.menu_principal;
import Controlador.reg_personal;
import Modelo.Validaciones;
import static Modelo.Validaciones.fechaActual;
import Vista.login;
import Vista.menu_principal;
import Vista.vntana_crear_user;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 *
 * @author Leonel
 */
public class Sistema_ferreteria {

    /**
     * @param args the command line arguments
     */
   static reg_personal rp = new reg_personal();

    public static void main(String[] args) throws ParseException {
       
       if (rp.cont_user() != 0) {
           login lg = new login();
           lg.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Crear Usuario","Mensaje",JOptionPane.INFORMATION_MESSAGE); 
             vntana_crear_user vcu = new vntana_crear_user();
            vcu.setVisible(true);

        }
        
      
      
   /*     SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
       // System.out.println(Validaciones.fechaActual());
     Date  fecha = null,aux=null;//form.parse(Validaciones.fechaActual());
    
     String fec = Validaciones.fechaActual();
      fecha = form.parse(fec);
        for(int i=0 ;i<5;i++){
           
           aux = fecha;
            
            fec = form.format(fecha);//convertir a string una fecha
            System.out.println("fecha actual:"+fec+" --- "+ fec);
            fecha  =   Validaciones.sumarDiasAFecha( aux  , 5);
            
        }*/
        
    }
    
    

}
