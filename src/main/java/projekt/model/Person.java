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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "PERSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findByPersonId", query = "SELECT p FROM Person p WHERE p.personId = :personId"),
    @NamedQuery(name = "Person.findByImie", query = "SELECT p FROM Person p WHERE p.imie = :imie"),
    @NamedQuery(name = "Person.findByNazwisko", query = "SELECT p FROM Person p WHERE p.nazwisko = :nazwisko"),
    @NamedQuery(name = "Person.findByTyp", query = "SELECT p FROM Person p WHERE p.typ = :typ"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PERSON_ID")
    private Integer personId;
    @Size(max = 20)
    @Column(name = "IMIE")
    private String imie;
    @Size(max = 30)
    @Column(name = "NAZWISKO")
    private String nazwisko;
    @Size(max = 20)
    @Column(name = "TYP")
    private String typ;
    @JoinColumn(name = "ADRES", referencedColumnName = "ADRES_ID")
    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    private Adres adres;
    @JoinColumn(name = "AUTH", referencedColumnName = "AUTH_ID")
    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    private Auth auth;
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klient", orphanRemoval = true)
    private List<Zamowienie> zamowienieList;

    public Person() {
    }

    public Person(Integer personId) {
        this.personId = personId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @XmlTransient
    public List<Zamowienie> getZamowienieList() {
        return zamowienieList;
    }

    public void setZamowienieList(List<Zamowienie> zamowienieList) {
        this.zamowienieList = zamowienieList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personId != null ? personId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personId == null && other.personId != null) || (this.personId != null && !this.personId.equals(other.personId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pawww.projekt.model.Person[ personId=" + personId + " ]";
    }

}
