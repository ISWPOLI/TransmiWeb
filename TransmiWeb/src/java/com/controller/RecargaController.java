package com.controller;

import com.controller.util.JsfUtil;
import com.entity.EstadoRecarga;
import com.entity.EstadoSolicitud;
import com.entity.SolicitudRecarga;
import com.entity.Solicitudes;
import com.entity.Tarjeta;
import com.entity.Usuario;
import com.facade.Conexion;
import com.facade.SolicitudRecargaFacade;
import java.io.File;
import java.io.IOException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named("recargacontroller")
@SessionScoped
public class RecargaController implements Serializable {

    private SolicitudRecarga current;
    FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    Usuario log = (Usuario) session.getAttribute("usuario");
    
    @EJB
    private com.facade.SolicitudRecargaFacade ejbFacade;
    private int selectedItemIndex,valorRecarga = 0;

    public RecargaController() {
    }

    public SolicitudRecarga getSelected() {
        if (current == null) {
            current = new SolicitudRecarga();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public String recargar() {
        try {
            current = new SolicitudRecarga();
            current.setMonto(valorRecarga);
            Tarjeta trj = new Tarjeta(selectedItemIndex);
            current.setTarjetaID(trj);
            EstadoRecarga estRec = new EstadoRecarga(1);
            current.setEstadoRecargaID(estRec);
            current.setIDUsuario(log);
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Recargar realizada ");
            return "/recargas/List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("La recarga no se pudo realizar");
            return null;
        }
    }
    public void exportarPDF(ActionEvent actionEvent, int codigo) throws JRException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Conexion cnn = new Conexion();
            Map<String, Object> parametros = new HashMap<String, Object>();

            parametros.put("codigo", codigo);

            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/Comprobante.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, cnn.getConexion());

            cnn.cerrarConexion();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream stream = response.getOutputStream();
                    response.addHeader("Content-disposition", "attachment; filename=Omprobante  " + codigo + ".pdf");

                    JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

                    stream.flush();

                    stream.close();

                    FacesContext.getCurrentInstance().responseComplete();
    }

    public List<SolicitudRecarga> mostarPorUsuario() {
        try {
            return getFacade().RecargasDeUsuario(log);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    private SolicitudRecargaFacade getFacade() {
        return ejbFacade;
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstadoSolicitudCreated"));
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

   
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstadoSolicitudUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

  

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstadoSolicitudDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }


    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public int getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(int valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

   

}
