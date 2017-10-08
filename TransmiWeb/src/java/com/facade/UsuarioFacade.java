/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade;

import com.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miguel
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "TransmiWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    public List<Usuario> ValidarIDContraseña(String correo, String clave){
        List<Usuario> logueado = new ArrayList<>();
        
        try {
            Query p = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.clave = :clave");
            p.setParameter("correo", correo);
            p.setParameter("clave", clave);
            logueado = p.getResultList();
        } catch (Exception e) {
        }
        return logueado;
    }
}
