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
@Table(name = "estado_recarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoRecarga.findAll", query = "SELECT e FROM EstadoRecarga e")
    , @NamedQuery(name = "EstadoRecarga.findByEstadorecargaID", query = "SELECT e FROM EstadoRecarga e WHERE e.estadorecargaID = :estadorecargaID")
    , @NamedQuery(name = "EstadoRecarga.findByDescripcion", query = "SELECT e FROM EstadoRecarga e WHERE e.descripcion = :descripcion")})
public class EstadoRecarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estadorecargaID")
    private Integer estadorecargaID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoRecargaID")
    private Collection<SolicitudRecarga> solicitudRecargaCollection;

    public EstadoRecarga() {
    }

    public EstadoRecarga(Integer estadorecargaID) {
        this.estadorecargaID = estadorecargaID;
    }

    public EstadoRecarga(Integer estadorecargaID, String descripcion) {
        this.estadorecargaID = estadorecargaID;
        this.descripcion = descripcion;
    }

    public Integer getEstadorecargaID() {
        return estadorecargaID;
    }

    public void setEstadorecargaID(Integer estadorecargaID) {
        this.estadorecargaID = estadorecargaID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<SolicitudRecarga> getSolicitudRecargaCollection() {
        return solicitudRecargaCollection;
    }

    public void setSolicitudRecargaCollection(Collection<SolicitudRecarga> solicitudRecargaCollection) {
        this.solicitudRecargaCollection = solicitudRecargaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadorecargaID != null ? estadorecargaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoRecarga)) {
            return false;
        }
        EstadoRecarga other = (EstadoRecarga) object;
        if ((this.estadorecargaID == null && other.estadorecargaID != null) || (this.estadorecargaID != null && !this.estadorecargaID.equals(other.estadorecargaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.EstadoRecarga[ estadorecargaID=" + estadorecargaID + " ]";
    }
    
}
