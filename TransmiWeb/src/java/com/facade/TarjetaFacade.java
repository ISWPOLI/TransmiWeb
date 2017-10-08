/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade;

import com.entity.Tarjeta;
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
public class TarjetaFacade extends AbstractFacade<Tarjeta> {

    @PersistenceContext(unitName = "TransmiWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarjetaFacade() {
        super(Tarjeta.class);
    }
    public List<Tarjeta> TarjetasDeUsuario(Usuario idUsuario){
        List<Tarjeta> tarjetas = new ArrayList<>();
        try {
            Query p = em.createQuery("SELECT t FROM Tarjeta t WHERE t.iDUsuario  = :user and t.iDEstado.iDEstado = 1");
            p.setParameter("user", idUsuario);
            tarjetas = p.getResultList();
        } catch (Exception e) {
        }
        return tarjetas;
    }
    
    public List<Tarjeta> TarjetasDeUsuario(Usuario idUsuario,int[] range) {
        List<Tarjeta> tarjetas = new ArrayList<>();
        try {
            Query p = em.createQuery("SELECT t FROM Tarjeta t WHERE t.iDUsuario  = :user and t.iDEstado.iDEstado = 1");
            p.setParameter("user", idUsuario);
            p.setMaxResults(range[1] - range[0]);
            p.setFirstResult(range[0]  + 1);
            tarjetas = p.getResultList();
        } catch (Exception e) {
        }
        return tarjetas;
    }
}
