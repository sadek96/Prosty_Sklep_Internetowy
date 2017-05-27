/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.model;

import java.io.Serializable;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "ZAMOWIENIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zamowienie.findAll", query = "SELECT z FROM Zamowienie z"),
    @NamedQuery(name = "Zamowienie.findByNumer", query = "SELECT z FROM Zamowienie z WHERE z.numer = :numer")})
public class Zamowienie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NUMER")
    private Integer numer;
    @JoinColumn(name = "KLIENT", referencedColumnName = "PERSON_ID")
    @ManyToOne(optional = false)
    private Person klient;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idZamowienia",orphanRemoval = true)
    private List<PZamowiony> pZamowionyList;

    public Zamowienie() {
    }

    public Zamowienie(Integer numer) {
        this.numer = numer;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public Person getKlient() {
        return klient;
    }

    public void setKlient(Person klient) {
        this.klient = klient;
    }

    @XmlTransient
    public List<PZamowiony> getPZamowionyList() {
        return pZamowionyList;
    }

    public void setPZamowionyList(List<PZamowiony> pZamowionyList) {
        this.pZamowionyList = pZamowionyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numer != null ? numer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zamowienie)) {
            return false;
        }
        Zamowienie other = (Zamowienie) object;
        if ((this.numer == null && other.numer != null) || (this.numer != null && !this.numer.equals(other.numer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pawww.projekt.model.Zamowienie[ numer=" + numer + " ]";
    }
    
}
