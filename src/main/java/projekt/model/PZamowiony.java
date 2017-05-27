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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "P_ZAMOWIONY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PZamowiony.findAll", query = "SELECT p FROM PZamowiony p"),
    @NamedQuery(name = "PZamowiony.findById", query = "SELECT p FROM PZamowiony p WHERE p.id = :id"),
    @NamedQuery(name = "PZamowiony.findByIdProduktu", query = "SELECT p FROM PZamowiony p WHERE p.idProduktu = :idProduktu"),
    @NamedQuery(name = "PZamowiony.findByNazwa", query = "SELECT p FROM PZamowiony p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "PZamowiony.findByIlosc", query = "SELECT p FROM PZamowiony p WHERE p.ilosc = :ilosc")})
public class PZamowiony implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CENA")
    private double cena;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PRODUKTU")
    private int idProduktu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAZWA")
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ILOSC")
    private int ilosc;
    @JoinColumn(name = "ID_ZAMOWIENIA", referencedColumnName = "NUMER")
    @ManyToOne(optional = false)
    private Zamowienie idZamowienia;

    public PZamowiony() {
    }

    public PZamowiony(Integer id) {
        this.id = id;
    }

    public PZamowiony(Integer id, int idProduktu, String nazwa, int ilosc) {
        this.id = id;
        this.idProduktu = idProduktu;
        this.nazwa = nazwa;
        this.ilosc = ilosc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdProduktu() {
        return idProduktu;
    }

    public void setIdProduktu(int idProduktu) {
        this.idProduktu = idProduktu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public Zamowienie getIdZamowienia() {
        return idZamowienia;
    }

    public void setIdZamowienia(Zamowienie idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PZamowiony)) {
            return false;
        }
        PZamowiony other = (PZamowiony) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pawww.projekt.model.PZamowiony[ id=" + id + " ]";
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
    
}
