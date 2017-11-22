package com.controller;

import com.entity.Perfil;
import com.controller.util.JsfUtil;
import com.controller.util.PaginationHelper;
import com.facade.PerfilFacade;

import java.io.Serializable;
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

@Named("perfilController")
@SessionScoped
public class PerfilController implements Serializable {

    private Perfil current;
    private DataModel items = null;
    @EJB
    private com.facade.PerfilFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PerfilController() {
    }

    public Perfil getSelected() {
        if (current == null) {
            current = new Perfil();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PerfilFacade getFacade() {
        return ejbFacade;
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PerfilUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

  

    public List<Perfil> todosPerfiles() {
        return ejbFacade.findAll();
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public Perfil getPerfil(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Perfil.class)
    public static class PerfilControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PerfilController controller = (PerfilController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "perfilController");
            return controller.getPerfil(getKey(value));
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
            if (object instanceof Perfil) {
                Perfil o = (Perfil) object;
                return getStringKey(o.getIDperfil());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Perfil.class.getName());
            }
        }

    }

}
