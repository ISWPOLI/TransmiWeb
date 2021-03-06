package com.controller;

import com.entity.Solicitudes;
import com.controller.util.JsfUtil;
import com.controller.util.PaginationHelper;
import com.entity.Correo;
import com.entity.EstadoSolicitud;
import com.entity.Usuario;
import com.facade.SolicitudesFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@Named("solicitudesController")
@SessionScoped
public class SolicitudesController implements Serializable {

    private Solicitudes current;
    private Correo mail = new Correo();
    private String algo;

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }
    private DataModel items = null;
    FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    Usuario log = (Usuario) session.getAttribute("usuario");
    
    @EJB
    private com.facade.SolicitudesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex,valorRecarga = 0;

    public SolicitudesController() {
    }

    public Solicitudes getSelected() {
        if (current == null) {
            current = new Solicitudes();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public void responder() {
        try {
            mail.setContrasena("gjchlaythbawuzok");
            mail.setUsuario("transmiweb@gmail.com");
            mail.setAsunto("Respuesta a solicitud N° ");
            mail.setMensaje("Hola");
            mail.setArchivo("");
            mail.setRutaArchivo("");
            Mailer m = new Mailer();
            if (m.enviarCorreo(mail)) {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudesCreated"));
            }else{
                
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudesCreated"));
            }
        } catch (Exception e) {
             JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public List<Solicitudes> solicitudesUsuario() {
        return    getFacade().SolicirudesDeUsuario(log);
    }
    
    public List<Solicitudes> todasSolicitudes() {
        return    getFacade().findAll();
    }
    
    private SolicitudesFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Solicitudes) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Solicitudes();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String recargar() {
        try {
            current = new Solicitudes();
            String descripcion = "recargar tarjeta con ";
            current.setIDUsuario(log);
            current.setDescripcion(descripcion + valorRecarga);
            java.util.Date fecha = new Date();
            current.setFecha(fecha);
            EstadoSolicitud solic = new EstadoSolicitud();
            solic.setIDEstadoSolicitudd(1);
            current.setIDestadosolicitud(solic);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudesCreated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Solicitudes) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Solicitudes) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Solicitudes getSolicitudes(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public int getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(int valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    @FacesConverter(forClass = Solicitudes.class)
    public static class SolicitudesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SolicitudesController controller = (SolicitudesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "solicitudesController");
            return controller.getSolicitudes(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Solicitudes) {
                Solicitudes o = (Solicitudes) object;
                return getStringKey(o.getIDSolicitud());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Solicitudes.class.getName());
            }
        }

    }

    public Correo getMail() {
        return mail;
    }

    public void setMail(Correo mail) {
        this.mail = mail;
    }

}
