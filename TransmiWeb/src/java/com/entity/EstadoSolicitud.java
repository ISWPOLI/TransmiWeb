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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "estado_solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoSolicitud.findAll", query = "SELECT e FROM EstadoSolicitud e")
    , @NamedQuery(name = "EstadoSolicitud.findByIDEstadoSolicitudd", query = "SELECT e FROM EstadoSolicitud e WHERE e.iDEstadoSolicitudd = :iDEstadoSolicitudd")
    , @NamedQuery(name = "EstadoSolicitud.findByDescripcion", query = "SELECT e FROM EstadoSolicitud e WHERE e.descripcion = :descripcion")})
public class EstadoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDEstadoSolicitudd")
    private Integer iDEstadoSolicitudd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDestadosolicitud")
    private Collection<Solicitudes> solicitudesCollection;

    public EstadoSolicitud() {
    }

    public EstadoSolicitud(Integer iDEstadoSolicitudd) {
        this.iDEstadoSolicitudd = iDEstadoSolicitudd;
    }

    public EstadoSolicitud(Integer iDEstadoSolicitudd, String descripcion) {
        this.iDEstadoSolicitudd = iDEstadoSolicitudd;
        this.descripcion = descripcion;
    }

    public Integer getIDEstadoSolicitudd() {
        return iDEstadoSolicitudd;
    }

    public void setIDEstadoSolicitudd(Integer iDEstadoSolicitudd) {
        this.iDEstadoSolicitudd = iDEstadoSolicitudd;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Solicitudes> getSolicitudesCollection() {
        return solicitudesCollection;
    }

    public void setSolicitudesCollection(Collection<Solicitudes> solicitudesCollection) {
        this.solicitudesCollection = solicitudesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDEstadoSolicitudd != null ? iDEstadoSolicitudd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoSolicitud)) {
            return false;
        }
        EstadoSolicitud other = (EstadoSolicitud) object;
        if ((this.iDEstadoSolicitudd == null && other.iDEstadoSolicitudd != null) || (this.iDEstadoSolicitudd != null && !this.iDEstadoSolicitudd.equals(other.iDEstadoSolicitudd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.EstadoSolicitud[ iDEstadoSolicitudd=" + iDEstadoSolicitudd + " ]";
    }
    
}
