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
@Table(name = "tipo_movimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMovimiento.findAll", query = "SELECT t FROM TipoMovimiento t")
    , @NamedQuery(name = "TipoMovimiento.findByIDTipoMovimiento", query = "SELECT t FROM TipoMovimiento t WHERE t.iDTipoMovimiento = :iDTipoMovimiento")
    , @NamedQuery(name = "TipoMovimiento.findByDescripcion", query = "SELECT t FROM TipoMovimiento t WHERE t.descripcion = :descripcion")})
public class TipoMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTipoMovimiento")
    private Integer iDTipoMovimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMovimiento")
    private Collection<Movimientos> movimientosCollection;

    public TipoMovimiento() {
    }

    public TipoMovimiento(Integer iDTipoMovimiento) {
        this.iDTipoMovimiento = iDTipoMovimiento;
    }

    public TipoMovimiento(Integer iDTipoMovimiento, String descripcion) {
        this.iDTipoMovimiento = iDTipoMovimiento;
        this.descripcion = descripcion;
    }

    public Integer getIDTipoMovimiento() {
        return iDTipoMovimiento;
    }

    public void setIDTipoMovimiento(Integer iDTipoMovimiento) {
        this.iDTipoMovimiento = iDTipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTipoMovimiento != null ? iDTipoMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMovimiento)) {
            return false;
        }
        TipoMovimiento other = (TipoMovimiento) object;
        if ((this.iDTipoMovimiento == null && other.iDTipoMovimiento != null) || (this.iDTipoMovimiento != null && !this.iDTipoMovimiento.equals(other.iDTipoMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.TipoMovimiento[ iDTipoMovimiento=" + iDTipoMovimiento + " ]";
    }
    
}
