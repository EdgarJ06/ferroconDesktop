
package Vista;
import Modelo.cn_BaseDeDatos;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * 
 * @author Wilson Belduma
 */
public class Reportes_g {
   
    //obtiene ruta de la carpeta de reportes
    public static String getPathReport(){
        return getPath()+"reportes\\";
    }
    //obtiene ruta de la carpeta de vistas
    public static String getPathVistas(){
      
        return getPath()+"Vista\\";
    }
    //obtiene ruta de la carpeta de vistas
    public static String getPathImagenes(){
        return getPath()+"iconos\\";
    }
    public static String getPathPortadas(){
        return getPath()+"Portadas\\";
    }
    
    //obtiene ruta del código fuente del Proyecto
    public static String getPath(){
        //extraer ruta del proyecto
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path=path.substring(0,path.length()-2);
        return path+="\\src\\";
    }  
    

    public static String getformPresupuesto() {
        return getPath() + "rep_presupuestos\\";
    }
    //genera reportes sin parámetros
    public static void generarReporte(String reporte) throws JRException, FileNotFoundException
    {
        Map<String, Object> params = new HashMap<String, Object>();
        generarReporte(reporte,params);
    }  
    
    //carga reporte simple
      cn_BaseDeDatos bd = new cn_BaseDeDatos();
   
    public static void generarReporte(String reporte,Map<String, Object> params) throws JRException, FileNotFoundException {     
        //conexión base de datos
        //Connection con = bd.Conectar(); 
        Connection con = new cn_BaseDeDatos().Conectar();      
        String path = Reportes_g.getPathReport()+reporte; 
        //String pathlogo = Reportes_g.getPathImagenes()+"logoB.png"; 
        //reporte fuente
        String reportSource = path+".jrxml";
        //archivo pdf destino
        String reportDest  = path+".pdf";     
        //Map<String, Object> params = new HashMap<String, Object>();
        params.put("Titulo",new String("Empresa ABC"));
        //params.put("logo",new String(pathlogo));     
        try
        {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con); //aqui           
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);            
            JasperViewer.viewReport(jasperPrint, false);
            new cn_BaseDeDatos().desconectar();
            
            // Connection con =  cn_BaseDeDatos().desconectar();
        } catch (Throwable ex) {
            Logger.getLogger(Reportes_g.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error:"+ex);
        }
        
    }
}
