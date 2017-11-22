/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "solicitud_recarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudRecarga.findAll", query = "SELECT s FROM SolicitudRecarga s")
    , @NamedQuery(name = "SolicitudRecarga.findBySolicitudRecargaID", query = "SELECT s FROM SolicitudRecarga s WHERE s.solicitudRecargaID = :solicitudRecargaID")
    , @NamedQuery(name = "SolicitudRecarga.findByMonto", query = "SELECT s FROM SolicitudRecarga s WHERE s.monto = :monto")})
public class SolicitudRecarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "solicitudRecargaID")
    private Integer solicitudRecargaID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private int monto;
    @JoinColumn(name = "tarjetaID", referencedColumnName = "IDTarjeta")
    @ManyToOne(optional = false)
    private Tarjeta tarjetaID;
    @JoinColumn(name = "estadoRecargaID", referencedColumnName = "estadorecargaID")
    @ManyToOne(optional = false)
    private EstadoRecarga estadoRecargaID;
    @JoinColumn(name = "IDUsuario", referencedColumnName = "IDUsuario")
    @ManyToOne(optional = false)
    private Usuario iDUsuario;

    public SolicitudRecarga() {
    }

    public SolicitudRecarga(Integer solicitudRecargaID) {
        this.solicitudRecargaID = solicitudRecargaID;
    }

    public SolicitudRecarga(Integer solicitudRecargaID, int monto) {
        this.solicitudRecargaID = solicitudRecargaID;
        this.monto = monto;
    }

    public Integer getSolicitudRecargaID() {
        return solicitudRecargaID;
    }

    public void setSolicitudRecargaID(Integer solicitudRecargaID) {
        this.solicitudRecargaID = solicitudRecargaID;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Tarjeta getTarjetaID() {
        return tarjetaID;
    }

    public void setTarjetaID(Tarjeta tarjetaID) {
        this.tarjetaID = tarjetaID;
    }

    public EstadoRecarga getEstadoRecargaID() {
        return estadoRecargaID;
    }

    public void setEstadoRecargaID(EstadoRecarga estadoRecargaID) {
        this.estadoRecargaID = estadoRecargaID;
    }

    public Usuario getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuario iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudRecargaID != null ? solicitudRecargaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudRecarga)) {
            return false;
        }
        SolicitudRecarga other = (SolicitudRecarga) object;
        if ((this.solicitudRecargaID == null && other.solicitudRecargaID != null) || (this.solicitudRecargaID != null && !this.solicitudRecargaID.equals(other.solicitudRecargaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.SolicitudRecarga[ solicitudRecargaID=" + solicitudRecargaID + " ]";
    }
    
}
