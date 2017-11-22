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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "solicitudes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudes.findAll", query = "SELECT s FROM Solicitudes s")
    , @NamedQuery(name = "Solicitudes.findByIDSolicitud", query = "SELECT s FROM Solicitudes s WHERE s.iDSolicitud = :iDSolicitud")
    , @NamedQuery(name = "Solicitudes.findByDescripcion", query = "SELECT s FROM Solicitudes s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "Solicitudes.findByFecha", query = "SELECT s FROM Solicitudes s WHERE s.fecha = :fecha")})
public class Solicitudes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSolicitud")
    private Integer iDSolicitud;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "IDUsuario", referencedColumnName = "IDUsuario")
    @ManyToOne(optional = false)
    private Usuario iDUsuario;
    @JoinColumn(name = "IDestadosolicitud", referencedColumnName = "IDEstadoSolicitudd")
    @ManyToOne(optional = false)
    private EstadoSolicitud iDestadosolicitud;

    public Solicitudes() {
    }

    public Solicitudes(Integer iDSolicitud) {
        this.iDSolicitud = iDSolicitud;
    }

    public Solicitudes(Integer iDSolicitud, String descripcion, Date fecha) {
        this.iDSolicitud = iDSolicitud;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Integer getIDSolicitud() {
        return iDSolicitud;
    }

    public void setIDSolicitud(Integer iDSolicitud) {
        this.iDSolicitud = iDSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuario iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    public EstadoSolicitud getIDestadosolicitud() {
        return iDestadosolicitud;
    }

    public void setIDestadosolicitud(EstadoSolicitud iDestadosolicitud) {
        this.iDestadosolicitud = iDestadosolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDSolicitud != null ? iDSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudes)) {
            return false;
        }
        Solicitudes other = (Solicitudes) object;
        if ((this.iDSolicitud == null && other.iDSolicitud != null) || (this.iDSolicitud != null && !this.iDSolicitud.equals(other.iDSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Solicitudes[ iDSolicitud=" + iDSolicitud + " ]";
    }
    
}
