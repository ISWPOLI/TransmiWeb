package com.controller;

import com.entity.Usuario;
import com.controller.util.JsfUtil;
import com.entity.Perfil;
import com.entity.TipoDocumento;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario logueado = new Usuario(), selected = new Usuario();

    @EJB
    private com.facade.UsuarioFacade ejbFacade;
    private String correo, clave, confirmClave;
    private int idPerfil =1,idTipoDoc = 1;

    public UsuarioController() {
    }

    public void login() throws IOException {
        logueado = consultaUsuario(correo, clave);
        if (logueado != (null)) {
            HttpSession session = JsfUtil.getSession();
            session.setAttribute("usuario", logueado);
            FacesContext.getCurrentInstance().getExternalContext().redirect("../faces/tarjeta/List.xhtml");
        }
    }

    public void logout() throws IOException {
        logueado = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Object session = externalContext.getSession(false);
        HttpSession httpSession = (HttpSession) session;
        httpSession.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../faces/index.xhtml");
    }

    public void registro() {
        if (confirmClave.equalsIgnoreCase(clave)) {
            logueado.setClave(clave);
            ejbFacade.create(logueado);
        } else {
        }
    }

    public Usuario consultaUsuario(String correo, String clave) {
        Usuario usuObj;
        List<Usuario> usuList = ejbFacade.ValidarIDContraseña(correo, clave);
        if (!usuList.isEmpty()) {
            usuObj = usuList.get(0);
        } else {
            usuObj = null;
        }
        return usuObj;
    }

    public void preparaEdicion(String correo, String clave) throws IOException {
        selected = consultaUsuario(correo, clave);
        if (selected != (null)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../faces/usuario/Edit.xhtml");
        }
    }

    public String editar() throws IOException {
        try {
            Perfil perfil = new Perfil();
            perfil.setIDperfil(idPerfil);
            selected.setIDperfil(perfil);
            TipoDocumento tipoDocumento = new TipoDocumento();
            tipoDocumento.setIDTipoDocumento(idTipoDoc);
            selected.setIDTipoDocumento(tipoDocumento);
            ejbFacade.edit(selected);
            JsfUtil.addSuccessMessage("Se ha modificado el usuario " + selected.getNombre() + " " + selected.getApellido());
            return "List";
        } catch (Exception e) {
             JsfUtil.addSuccessMessage("Error al modificar el usuario " + selected.getNombre());
        }
        return "";
    }

    public void eliminar(int id) throws IOException {
        Usuario usuario = ejbFacade.find(id);
        try {
            ejbFacade.remove(usuario);
            JsfUtil.addSuccessMessage("Se ha eliminado el usuario " + usuario.getNombre());
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("Error al eliminar el usuario " + usuario.getNombre());
        }
    }

    public List<Usuario> todosUsuarios() {
        return ejbFacade.findAll();
    }

    // getters & setters
    public Usuario getUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public Usuario getlogueado() {
        return logueado;
    }

    public void setlogueado(Usuario logueado) {
        this.logueado = logueado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConfirmClave() {
        return confirmClave;
    }

    public void setConfirmClave(String confirmClave) {
        this.confirmClave = confirmClave;
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(int idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

}
