/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "repo_perdida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepoPerdida.findAll", query = "SELECT r FROM RepoPerdida r")
    , @NamedQuery(name = "RepoPerdida.findByIDrepoperdida", query = "SELECT r FROM RepoPerdida r WHERE r.iDrepoperdida = :iDrepoperdida")
    , @NamedQuery(name = "RepoPerdida.findByFecha", query = "SELECT r FROM RepoPerdida r WHERE r.fecha = :fecha")})
public class RepoPerdida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDrepoperdida")
    private Integer iDrepoperdida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "ID_Tarjeta", referencedColumnName = "IDTarjeta")
    @ManyToOne(optional = false)
    private Tarjeta iDTarjeta;

    public RepoPerdida() {
    }

    public RepoPerdida(Integer iDrepoperdida) {
        this.iDrepoperdida = iDrepoperdida;
    }

    public RepoPerdida(Integer iDrepoperdida, Date fecha) {
        this.iDrepoperdida = iDrepoperdida;
        this.fecha = fecha;
    }

    public Integer getIDrepoperdida() {
        return iDrepoperdida;
    }

    public void setIDrepoperdida(Integer iDrepoperdida) {
        this.iDrepoperdida = iDrepoperdida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tarjeta getIDTarjeta() {
        return iDTarjeta;
    }

    public void setIDTarjeta(Tarjeta iDTarjeta) {
        this.iDTarjeta = iDTarjeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDrepoperdida != null ? iDrepoperdida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepoPerdida)) {
            return false;
        }
        RepoPerdida other = (RepoPerdida) object;
        if ((this.iDrepoperdida == null && other.iDrepoperdida != null) || (this.iDrepoperdida != null && !this.iDrepoperdida.equals(other.iDrepoperdida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.RepoPerdida[ iDrepoperdida=" + iDrepoperdida + " ]";
    }
    
}
