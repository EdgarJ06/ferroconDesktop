/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;

/**
 *
 * @author Leonel
 */
public class caja {
   cn_BaseDeDatos bd = new cn_BaseDeDatos(); 
     
    Connection cn = bd.Conectar();
    
    private int id_caja;
    private int num_caja;
    private String descrip;

    public caja() {
    }

    public caja(int id_caja, int num_caja, String descrip) {
        this.id_caja = id_caja;
        this.num_caja = num_caja;
        this.descrip = descrip;
    }

    public int getId_caja() {
        return id_caja;
    }

    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    public int getNum_caja() {
        return num_caja;
    }

    public void setNum_caja(int num_caja) {
        this.num_caja = num_caja;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    
      
     public void comboListCaja(JComboBox<caja> cmbcaja) {
        try {
           // Connection cn = bd.Conectar();
            //   listciu = new ArrayList<ciudad>();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM caja");
            while (rs.next()) {
               
                cmbcaja.addItem(new caja(rs.getInt(1), rs.getInt(2),rs.getString(3)));
            }
             bd.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex + ":marca error");
        }

        // return listciu;
    }

    @Override
    public String toString() {
        return descrip;

    }
    
    
    
}
