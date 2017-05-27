/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "PRODUKT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produkt.findAll", query = "SELECT p FROM Produkt p"),
    @NamedQuery(name = "Produkt.findByProduktId", query = "SELECT p FROM Produkt p WHERE p.produktId = :produktId"),
    @NamedQuery(name = "Produkt.findByNazwa", query = "SELECT p FROM Produkt p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Produkt.findByCena", query = "SELECT p FROM Produkt p WHERE p.cena = :cena")})
public class Produkt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUKT_ID")
    private Integer produktId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAZWA")
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CENA")
    private double cena;

    public Produkt() {
    }

    public Produkt(Integer produktId) {
        this.produktId = produktId;
    }

    public Produkt(Integer produktId, String nazwa, double cena) {
        this.produktId = produktId;
        this.nazwa = nazwa;
        this.cena = cena;
    }

    public Integer getProduktId() {
        return produktId;
    }

    public void setProduktId(Integer produktId) {
        this.produktId = produktId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (produktId != null ? produktId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produkt)) {
            return false;
        }
        Produkt other = (Produkt) object;
        if ((this.produktId == null && other.produktId != null) || (this.produktId != null && !this.produktId.equals(other.produktId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pawww.projekt.model.Produkt[ produktId=" + produktId + " ]";
    }
    
}
