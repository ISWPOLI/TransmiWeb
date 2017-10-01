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
@Table(name = "movimientos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimientos.findAll", query = "SELECT m FROM Movimientos m")
    , @NamedQuery(name = "Movimientos.findByIDMovimiento", query = "SELECT m FROM Movimientos m WHERE m.iDMovimiento = :iDMovimiento")
    , @NamedQuery(name = "Movimientos.findByMonto", query = "SELECT m FROM Movimientos m WHERE m.monto = :monto")})
public class Movimientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDMovimiento")
    private Integer iDMovimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Monto")
    private int monto;
    @JoinColumn(name = "Tipo_Movimiento", referencedColumnName = "IDTipoMovimiento")
    @ManyToOne(optional = false)
    private TipoMovimiento tipoMovimiento;
    @JoinColumn(name = "ID_Tarjeta", referencedColumnName = "IDTarjeta")
    @ManyToOne(optional = false)
    private Tarjeta iDTarjeta;

    public Movimientos() {
    }

    public Movimientos(Integer iDMovimiento) {
        this.iDMovimiento = iDMovimiento;
    }

    public Movimientos(Integer iDMovimiento, int monto) {
        this.iDMovimiento = iDMovimiento;
        this.monto = monto;
    }

    public Integer getIDMovimiento() {
        return iDMovimiento;
    }

    public void setIDMovimiento(Integer iDMovimiento) {
        this.iDMovimiento = iDMovimiento;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
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
        hash += (iDMovimiento != null ? iDMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.iDMovimiento == null && other.iDMovimiento != null) || (this.iDMovimiento != null && !this.iDMovimiento.equals(other.iDMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Movimientos[ iDMovimiento=" + iDMovimiento + " ]";
    }
    
}
