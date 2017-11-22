/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade;

import com.entity.SolicitudRecarga;
import com.entity.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Miguel
 */
@Stateless
public class SolicitudRecargaFacade extends AbstractFacade<SolicitudRecarga> {

    @PersistenceContext(unitName = "TransmiWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudRecargaFacade() {
        super(SolicitudRecarga.class);
    }
    public List<SolicitudRecarga> RecargasDeUsuario(Usuario idUsuario){

        List<SolicitudRecarga> solRecarga = new ArrayList<>();
        try {
            Query p = em.createQuery("SELECT s FROM SolicitudRecarga s WHERE s.iDUsuario  = :user");
            p.setParameter("user", idUsuario);
            solRecarga = p.getResultList();
        } catch (Exception e) {
        }
        return solRecarga;
    }
}
