/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade;

import com.entity.Solicitudes;
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
public class SolicitudesFacade extends AbstractFacade<Solicitudes> {

    @PersistenceContext(unitName = "TransmiWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudesFacade() {
        super(Solicitudes.class);
    }
    public List<Solicitudes> SolicirudesDeUsuario(Usuario idUsuario){
        List<Solicitudes> sol = new ArrayList<>();
        try {
            Query p = em.createQuery("SELECT s FROM Solicitudes s WHERE s.iDUsuario  = :user");
            p.setParameter("user", idUsuario);
            sol = p.getResultList();
        } catch (Exception e) {
        }
        return sol;
    }
}
