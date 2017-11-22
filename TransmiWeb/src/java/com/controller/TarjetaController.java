package com.controller;

import com.entity.Tarjeta;
import com.controller.util.JsfUtil;
import com.controller.util.PaginationHelper;
import com.entity.EstadoTarjeta;
import com.entity.Usuario;
import com.facade.TarjetaFacade;
import java.io.Serializable;
import java.util.ArrayList;



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
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




@Named("tarjetaController")
@SessionScoped
public class TarjetaController implements Serializable {

    private Tarjeta current;
    private DataModel items = null;
    FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    Usuario log = (Usuario) session.getAttribute("usuario");

    @EJB
    private com.facade.TarjetaFacade ejbFacade;
    @EJB
    private com.facade.MovimientosFacade movFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public List<Tarjeta> TarjetasDeUsuario() {
        return getFacade().TarjetasDeUsuario(log);
    }

    public TarjetaController() {
       
    }
   
    public String setTarjetaMovimiento() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        selectedItemIndex = Integer.parseInt(request.getParameter("movimientosForm:tarID"));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seleccionada", selectedItemIndex);       
        return "/movimientos/List";
    }

    public Tarjeta getSelected() {
        if (current == null) {
            current = new Tarjeta();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TarjetaFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {

        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Tarjeta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Tarjeta();
        selectedItemIndex = -1;
        return "/solicitudes/Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TarjetaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Tarjeta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            selectedItemIndex = Integer.parseInt(request.getParameter("reporteForm:tarID"));
            current = ejbFacade.find(selectedItemIndex);
            EstadoTarjeta tarEst = new EstadoTarjeta();
            tarEst.setIDEstado(2);
            current.setIDEstado(tarEst);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TarjetaUpdated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Tarjeta) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TarjetaDeleted"));
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

    public Tarjeta getTarjeta(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public Tarjeta getCurrent() {
        return current;
    }

    public void setCurrent(Tarjeta current) {
        this.current = current;
    }

    public com.facade.MovimientosFacade getMovFacade() {
        return movFacade;
    }

    public void setMovFacade(com.facade.MovimientosFacade movFacade) {
        this.movFacade = movFacade;
    }

    @FacesConverter(forClass = Tarjeta.class)
    public static class TarjetaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TarjetaController controller = (TarjetaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tarjetaController");
            return controller.getTarjeta(getKey(value));
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
            if (object instanceof Tarjeta) {
                Tarjeta o = (Tarjeta) object;
                return getStringKey(o.getIDTarjeta());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Tarjeta.class.getName());
            }
        }

    }

}
