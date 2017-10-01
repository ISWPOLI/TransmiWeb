/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "tarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t")
    , @NamedQuery(name = "Tarjeta.findByIDTarjeta", query = "SELECT t FROM Tarjeta t WHERE t.iDTarjeta = :iDTarjeta")
    , @NamedQuery(name = "Tarjeta.findBySaldo", query = "SELECT t FROM Tarjeta t WHERE t.saldo = :saldo")})
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTarjeta")
    private Integer iDTarjeta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Saldo")
    private int saldo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDTarjeta")
    private Collection<Movimientos> movimientosCollection;
    @JoinColumn(name = "IDUsuario", referencedColumnName = "IDUsuario")
    @ManyToOne(optional = false)
    private Usuario iDUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDTarjeta")
    private Collection<RepoPerdida> repoPerdidaCollection;

    public Tarjeta() {
    }

    public Tarjeta(Integer iDTarjeta) {
        this.iDTarjeta = iDTarjeta;
    }

    public Tarjeta(Integer iDTarjeta, int saldo) {
        this.iDTarjeta = iDTarjeta;
        this.saldo = saldo;
    }

    public Integer getIDTarjeta() {
        return iDTarjeta;
    }

    public void setIDTarjeta(Integer iDTarjeta) {
        this.iDTarjeta = iDTarjeta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }

    public Usuario getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuario iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    @XmlTransient
    public Collection<RepoPerdida> getRepoPerdidaCollection() {
        return repoPerdidaCollection;
    }

    public void setRepoPerdidaCollection(Collection<RepoPerdida> repoPerdidaCollection) {
        this.repoPerdidaCollection = repoPerdidaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTarjeta != null ? iDTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.iDTarjeta == null && other.iDTarjeta != null) || (this.iDTarjeta != null && !this.iDTarjeta.equals(other.iDTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Tarjeta[ iDTarjeta=" + iDTarjeta + " ]";
    }
    
}
