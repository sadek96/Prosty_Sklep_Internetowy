/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.log4j.Logger;
import projekt.ejb.PersonFacade;

import projekt.model.Person;

/**
 *
 * @author Daniel
 */
@Named("loginSessionBean")
@SessionScoped
public class LoginSessionBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(LoginSessionBean.class);

    private String username, pass;

    @EJB
    private PersonFacade personEJB;

    private Person currentUser;

    /**
     * Creates a new instance of loginSessionBean
     */
    public LoginSessionBean() {

    }

    @PostConstruct
    public void init() {
        currentUser = null;
    }

    public String logIn() {
        Person person;
        try {
            LOG.info("Próba logowania, login:" + username);
            person = personEJB.findByLogin(this.username);
        } catch (Exception e) {
            LOG.error("Nie znaleziono loginu:" + username);
            //throw e;
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginForm:login", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zły login", null));
            return null;

        }

        if (person != null) {

            if (person.getAuth().getPass().equals(this.pass)) {
                LOG.info("Pomyślnie zalogowany:" + username);
                currentUser = person;
                return "index.xhtml?faces-redirect=true";
            } else {
                LOG.info("Złe hasło dla loginu:" + username);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("loginForm:hasło", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Złe hasło", null));
                return null;
            }
        } else {
            return null;
        }
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getTyp().equals("admin");
    }

    public String logout() {
        LOG.info("Wylogowano " + username);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public PersonFacade getPersonEJB() {
        return personEJB;
    }

    public void setPersonEJB(PersonFacade personEJB) {
        this.personEJB = personEJB;
    }

}
