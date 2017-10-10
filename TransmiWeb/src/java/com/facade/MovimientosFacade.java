/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade;

import com.entity.Movimientos;
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
public class MovimientosFacade extends AbstractFacade<Movimientos> {

    @PersistenceContext(unitName = "TransmiWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimientosFacade() {
        super(Movimientos.class);
    }
    public List<Movimientos> movimientoDeTarjeta(int idTarjeta,int[] range) {
        List<Movimientos> movimientos = new ArrayList<>();
        try {
            Query p = em.createQuery("SELECT m FROM Movimientos m WHERE m.iDTarjeta.iDTarjeta  = :tarjeta ");
            p.setParameter("tarjeta", idTarjeta);
            p.setMaxResults(range[1] - range[0]);
            p.setFirstResult(range[0] );
            movimientos = p.getResultList();
        } catch (Exception e) {
        }
        return movimientos;
    }
}
