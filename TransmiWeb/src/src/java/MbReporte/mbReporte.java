/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MbReporte;

import DAOConnection.ConexionMySQL;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Torres
 */
@Named(value = "mbReporte")
@SessionScoped
public class mbReporte implements Serializable {

    /**
     * Creates a new instance of mbReporte
     */
    public mbReporte() {
    }

    public void generarReporte() {
        ConexionMySQL cn = new ConexionMySQL();
        Connection c = cn.Conectar();
        try {
            String ubicacionCompilado = "C:\\reportes\\text.jasper";
            String ruta = "C:\\reportes\\paises.pdf";
            JasperPrint jasperprint = null;
            JasperReport report;
            Map<String, Object> parametros = new HashMap<String, Object>();
            report = (JasperReport) JRLoader.loadObjectFromFile(ubicacionCompilado);
            jasperprint = JasperFillManager.fillReport(report, null, c);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=paises.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperprint, servletOutputStream);
            try {
            JasperExportManager.exportReportToPdfFile(jasperprint, ruta);
            } catch (Exception e) {
                System.out.println(e);
            }
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
        }
    }
}
