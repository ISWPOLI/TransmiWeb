/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recargas;

import com.entity.Tarjeta;
import com.facade.TarjetaFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Miguel
 */
@Named(value = "recargas")
@SessionScoped
public class recargas implements Serializable {
    @EJB
    private TarjetaFacade tarjetaFacade;
    /**
     * Creates a new instance of recargas
     */
    private ArrayList<Tarjeta> trj;
    private String nnn = "aaaaa";
    public recargas() {
        this.trj = new ArrayList<Tarjeta>();
        }
   
    public List<Tarjeta> mostrarTarjetas(){
        return tarjetaFacade.findAll();
    }

    /**
     * @return the trj
     */ 
    public ArrayList<Tarjeta> getTrj() {
        return trj;
    }

    /**
     * @param trj the trj to set
     */
    public void setTrj(ArrayList<Tarjeta> trj) {
        this.trj = trj;
    }

    /**
     * @return the nnn
     */
    public String getNnn() {
        return nnn;
    }

    /**
     * @param nnn the nnn to set
     */
    public void setNnn(String nnn) {
        this.nnn = nnn;
    }

    /**
     * @return the trj
     */
   
    
}
