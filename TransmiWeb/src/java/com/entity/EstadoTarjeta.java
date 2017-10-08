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
@Table(name = "estado_tarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoTarjeta.findAll", query = "SELECT e FROM EstadoTarjeta e")
    , @NamedQuery(name = "EstadoTarjeta.findByIDEstado", query = "SELECT e FROM EstadoTarjeta e WHERE e.iDEstado = :iDEstado")
    , @NamedQuery(name = "EstadoTarjeta.findByDescripcion", query = "SELECT e FROM EstadoTarjeta e WHERE e.descripcion = :descripcion")})
public class EstadoTarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iDEstado")
    private Integer iDEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDEstado")
    private Collection<Tarjeta> tarjetaCollection;

    public EstadoTarjeta() {
    }

    public EstadoTarjeta(Integer iDEstado) {
        this.iDEstado = iDEstado;
    }

    public EstadoTarjeta(Integer iDEstado, String descripcion) {
        this.iDEstado = iDEstado;
        this.descripcion = descripcion;
    }

    public Integer getIDEstado() {
        return iDEstado;
    }

    public void setIDEstado(Integer iDEstado) {
        this.iDEstado = iDEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Tarjeta> getTarjetaCollection() {
        return tarjetaCollection;
    }

    public void setTarjetaCollection(Collection<Tarjeta> tarjetaCollection) {
        this.tarjetaCollection = tarjetaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDEstado != null ? iDEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoTarjeta)) {
            return false;
        }
        EstadoTarjeta other = (EstadoTarjeta) object;
        if ((this.iDEstado == null && other.iDEstado != null) || (this.iDEstado != null && !this.iDEstado.equals(other.iDEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.EstadoTarjeta[ iDEstado=" + iDEstado + " ]";
    }
    
}
