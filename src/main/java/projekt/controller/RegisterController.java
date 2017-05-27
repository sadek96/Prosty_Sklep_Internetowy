/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import projekt.ejb.AdresFacade;
import projekt.ejb.AuthFacade;
import projekt.ejb.PersonFacade;
import projekt.model.Adres;
import projekt.model.Auth;
import projekt.model.Person;

/**
 *
 * @author Daniel
 */
@Named(value = "registerController")
@RequestScoped
public class RegisterController {

    private static final Logger LOG = Logger.getLogger(RegisterController.class);

    @EJB
    private PersonFacade personEJB;

    @EJB
    private AuthFacade authEJB;

    @EJB
    private AdresFacade adresEJB;

    private Person person;
    private Auth auth;
    private Adres adres;

    private String imie,
            nazwisko,
            typ,
            login,
            pass,
            ulica,
            miasto,
            kod,
            email;

    /**
     * Creates a new instance of PersonController
     */
    @PostConstruct
    public void init() {
        person = new Person();
        auth = new Auth();
        adres = new Adres();
        typ = "klient";
    }

    public RegisterController() {
    }

    public String register() {

        LOG.info("Podjęta próba stworzenia nowego konta");
        
        if (imie == null || "".equals(imie) || nazwisko == null || "".equals(nazwisko) || login == null || "".equals(login) || pass == null || "".equals(pass)
                || email == null || "".equals(email)) {
            return null;
        }

        if (authEJB.existLogin(login)) {
            LOG.error("Nieudana próba rejestracji: login zajęty");
            FacesContext.getCurrentInstance().addMessage("registerForm:login", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taki login już istnieje", null));
            return null;
        }

        if (personEJB.existsEmail(email)) {
            LOG.error("Nieudana próba rejestracji: email zajęty");
            FacesContext.getCurrentInstance().addMessage("registerForm:login", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email zajęty", null));
            return null;
        }

        person.setImie(imie);
        person.setNazwisko(nazwisko);
        person.setTyp(typ);
        person.setZamowienieList(null);

        person.setEmail(email);

        auth.setLogin(login);
        auth.setPass(pass);

        adres.setKod(kod);
        adres.setMiasto(miasto);
        adres.setUlica(ulica);

        authEJB.create(auth);

        adresEJB.create(adres);

        person.setAuth(auth);
        person.setAdres(adres);

        try {
            personEJB.create(person);
        } catch (Exception e) {
            LOG.error("Bład podczas tworzenia nowego konta");
            FacesContext.getCurrentInstance().addMessage("registerForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie udało się stworzyć klienta", null));

        }

        LOG.info("Nowe konto nr:"+person.getPersonId()+" zostało stworzone");
        return "login.xhtml?faces-redirect=true";
    }

    public List<Person> findAll() {
        return personEJB.findAll();
    }

    public PersonFacade getPersonEJB() {
        return personEJB;
    }

    public void setPersonEJB(PersonFacade personEJB) {
        this.personEJB = personEJB;
    }

    public AuthFacade getAuthEJB() {
        return authEJB;
    }

    public void setAuthEJB(AuthFacade authEJB) {
        this.authEJB = authEJB;
    }

    public AdresFacade getAdresEJB() {
        return adresEJB;
    }

    public void setAdresEJB(AdresFacade adresEJB) {
        this.adresEJB = adresEJB;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
