/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import projekt.ejb.MailEJB;
import projekt.ejb.PZamowionyFacade;
import projekt.ejb.ZamowienieFacade;
import projekt.model.PZamowiony;
import projekt.model.Person;
import projekt.model.Zamowienie;

/**
 *
 * @author Daniel
 */
@Named(value = "zamowienieController")
@RequestScoped
public class zamowienieController {

    private static final Logger LOG = Logger.getLogger(zamowienieController.class);

    @EJB
    ZamowienieFacade zamowienieEJB;

    @EJB
    PZamowionyFacade pZamowionyEJB;

    @EJB
    MailEJB mailEJB;

    @Inject
    CartSessionBean cartSessionBean;

    @Inject
    LoginSessionBean loginSessionBean;

    private Person currentUser;

    private Zamowienie zamowienie;

    private List<Zamowienie> zamowienieList;

    private List<PZamowiony> pZamowioneList;

    /**
     * Creates a new instance of zamowienieController
     */
    public zamowienieController() {
    }

    @PostConstruct
    public void init() {
        currentUser = new Person();
        zamowienie = new Zamowienie();
        pZamowioneList = new <PZamowiony> ArrayList();
    }

    public void dodajZamówienie() {

        LOG.info("Użytkownik nr:" + currentUser.getPersonId() + ", próba zamówienia produktów z koszyka");
        currentUser = loginSessionBean.getCurrentUser();

        for (PZamowiony pZamowiony : cartSessionBean.getCart()) {

            pZamowioneList.add(pZamowiony);

        }

        if (pZamowioneList == null || pZamowioneList.isEmpty()) {
            LOG.error("Użytkownik nr:" + currentUser.getPersonId() + ", próba zamówienia pustego koszyka");

            FacesContext.getCurrentInstance().addMessage("cartForm:zamów", new FacesMessage(FacesMessage.SEVERITY_ERROR, "--Koszyk jest pusty", null));
            return;
        }

        for (PZamowiony pZamowiony : pZamowioneList) {
            pZamowiony.setIdZamowienia(zamowienie);
        }

        DecimalFormat df = new DecimalFormat("#.00");

        zamowienie.setKlient(currentUser);

        try {
            zamowienieEJB.create(zamowienie);

            String message = "Zamówienie nr: " + zamowienie.getNumer() + " zostało zarejestrowane.\nLista zamówionych produktów:\n";

            for (PZamowiony pZamowiony : pZamowioneList) {
                message = message.concat("-" + pZamowiony.getNazwa() + ", " + df.format(pZamowiony.getCena()) + "ZŁ, Ilość:" + pZamowiony.getIlosc() + "\n");
            }

            mailEJB.sendMail(loginSessionBean.getCurrentUser().getEmail(), "Zamówienie nr: " + zamowienie.getNumer(), message);
            LOG.info("Wysłano mail do użytkownika nr:" + currentUser.getPersonId() + " z informacją o zamówieniu nr:" + zamowienie.getNumer());
        } catch (Exception e) {
            LOG.error("Błąd podczas tworzenia nowego zamówienia");

            FacesContext.getCurrentInstance().addMessage("cartForm:zamów", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie udało się stworzyć zamówienia!", null));
            return;
        }

        for (PZamowiony pZamowiony : pZamowioneList) {
            pZamowionyEJB.create(pZamowiony);
        }

        FacesContext.getCurrentInstance().addMessage("cartForm:zamów", new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie dodano zamówienie", null));
        LOG.info("Użytkownik nr:" + currentUser.getPersonId() + "pomyślne dodanie zamówienia nr:"+zamowienie.getNumer());
        loginSessionBean.getPersonEJB().edit(currentUser);

        cartSessionBean.clear();
    }

    public ZamowienieFacade getZamowienieEJB() {
        return zamowienieEJB;
    }

    public void setZamowienieEJB(ZamowienieFacade zamowienieEJB) {
        this.zamowienieEJB = zamowienieEJB;
    }

    public CartSessionBean getCartSessionBean() {
        return cartSessionBean;
    }

    public void setCartSessionBean(CartSessionBean cartSessionBean) {
        this.cartSessionBean = cartSessionBean;
    }

    public LoginSessionBean getLoginSessionBean() {
        return loginSessionBean;
    }

    public void setLoginSessionBean(LoginSessionBean loginSessionBean) {
        this.loginSessionBean = loginSessionBean;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }

    public Zamowienie getZamowienie() {
        return zamowienie;
    }

    public void setZamowienie(Zamowienie zamowienie) {
        this.zamowienie = zamowienie;
    }

}
