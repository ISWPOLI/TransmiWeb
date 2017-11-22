package com.controller;

import com.controller.util.JsfUtil;
import com.entity.Movimientos;
import com.controller.util.PaginationHelper;
import com.facade.Conexion;
import com.facade.MovimientosFacade;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named("movimientosController")
@SessionScoped
public class MovimientosController implements Serializable {

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    private Movimientos current;
    private List<Movimientos> items = null;

    @EJB
    private com.facade.MovimientosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex, tipoReporte = 1;

    public MovimientosController() {
    }

    public List<Movimientos> movimientosTarjeta() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return getFacade().movimientosDeTarjeta(selectedItemIndex);
    }

    public void exportarPDF(ActionEvent actionEvent) throws JRException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (tipoReporte == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../faces/movimientos/List.xhtml");
        } else {
            Conexion cnn = new Conexion();
            Map<String, Object> parametros = new HashMap<String, Object>();

            parametros.put("selectedItemIndex", selectedItemIndex);

            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/MovimientosReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, cnn.getConexion());

            cnn.cerrarConexion();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream stream = response.getOutputStream();
                    response.addHeader("Content-disposition", "attachment; filename=MovimientosTarj" + selectedItemIndex + ".pdf");

                    JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

                    stream.flush();

                    stream.close();

                    FacesContext.getCurrentInstance().responseComplete();

        }
    }

    public Movimientos getSelected() {
        if (current == null) {
            current = new Movimientos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MovimientosFacade getFacade() {
        return ejbFacade;
    }

    public String prepareCreate() {
        current = new Movimientos();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosDeleted"));
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

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Movimientos getMovimientos(java.lang.Integer id) {
        return ejbFacade.find(id);

    }

    public int getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(int tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    @FacesConverter(forClass = Movimientos.class)
    public static class MovimientosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimientosController controller = (MovimientosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimientosController");
            return controller.getMovimientos(getKey(value));
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
            if (object instanceof Movimientos) {
                Movimientos o = (Movimientos) object;
                return getStringKey(o.getIDMovimiento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Movimientos.class.getName());
            }
        }

    }

}
