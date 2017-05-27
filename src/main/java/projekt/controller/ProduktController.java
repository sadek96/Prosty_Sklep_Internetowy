package projekt.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import projekt.ejb.PZamowionyFacade;
import projekt.ejb.ProduktFacade;
import projekt.ejb.ZamowienieFacade;
import projekt.model.PZamowiony;
import projekt.model.Produkt;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "produktController")
@ViewScoped
public class ProduktController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(ProduktController.class);
    
    @EJB
    private ProduktFacade produktEJB;
    
    @EJB
    private PZamowionyFacade pZamowionyEJB;
    
    @EJB
    private ZamowienieFacade zamowienieEJB;
    
    private Produkt produkt;
    
    private List<Produkt> list;
    
    private Integer produktId;
    private String nazwa;
    private double cena;

    /**
     * Creates a new instance of ProduktController
     */
    public ProduktController() {
        
    }
    
    @PostConstruct
    public void init() {
        produkt = new Produkt();
        list = new <Produkt> ArrayList();
    }
    
    public void dodajProdukt() {
        
        LOG.info("Próba dodania nowego produktu");
        if (produktId == null || nazwa == null || cena == 0.0) {
            LOG.error("Nie wszystkie pola wypełnione");
            FacesContext.getCurrentInstance().addMessage("produktForm:add", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie wszystkie pola wypełnione!", null));
        } else if (existsId()) {
            LOG.error("Produkt o takim id istnieje");
            FacesContext.getCurrentInstance().addMessage("produktForm:add", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produkt o takim id już istnieje!", null));
            
        } else {
            try {
                produkt.setCena(cena);
                produkt.setNazwa(nazwa);
                produkt.setProduktId(produktId);
                
                produktEJB.create(produkt);
                LOG.info("Pomyslne dodanie nowego produktu nr:" + produkt.getProduktId());
                FacesContext.getCurrentInstance().addMessage("produktForm:add", new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie dodano produkt do listy", null));
                
            } catch (Exception e) {
                LOG.error("Błąd podczas dodawania nowego produktu");
                FacesContext.getCurrentInstance().addMessage("produktForm:add", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie udało się dodać produktu!", null));
                
            }
        }
    }
    
    public void deleteProdukt() {
        
        if (!existsId()) {
            LOG.error("Próba usunięcia nieistniejącego produktu");
            FacesContext.getCurrentInstance().addMessage("produktForm:delete", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie istnieje produkt o takim id!", null));
            
        } else {
            try {
                produkt = produktEJB.find(produktId);
                
                if (pZamowionyEJB.existsId(produktId)) {
                    
                    try {
                        pZamowionyEJB.deleteWithId(produktId);
                        LOG.info("Usunięto również wszystkie powiązane produkty z zamówień");
                        FacesContext.getCurrentInstance().addMessage("produktForm:delete", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunięto również z zamówień", null));
                        try {
                            zamowienieEJB.deleteEmpty();
                            LOG.info("Usunięto puste zamówienia");
                            FacesContext.getCurrentInstance().addMessage("produktForm:delete", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunięto puste zamówienia", null));
                        } catch (Exception e) {
                        }
                    } catch (Exception e) {
                        
                    }
                }
                
                produktEJB.remove(produkt);
                FacesContext.getCurrentInstance().addMessage("produktForm:delete", new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie usunięto produkt", null));
                LOG.info("Pomyslne usunięcie produktu");
            } catch (Exception e) {
                LOG.error("Błąd podczas usuwania produktu");
                FacesContext.getCurrentInstance().addMessage("produktForm:delete", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie udało się usunąć produktu!", null));
            }
        }
    }
    
    public void editProdukt() {
        LOG.info("Edycja produktu nr:" + produktId);
        if (nazwa == null || produktId == null || cena == 0.0) {
            LOG.error("Pola nie wypełnione");
            FacesContext.getCurrentInstance().addMessage("produktForm:edit", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie wszystkie pola wypełnione!", null));
        } else if (existsId()) {
            try {
                produkt.setCena(cena);
                produkt.setProduktId(produktId);
                produkt.setNazwa(nazwa);
                
                produktEJB.edit(produkt);
                FacesContext.getCurrentInstance().addMessage("produktForm:edit", new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie zaktualizowano produkt", null));
                LOG.info("Pomyslna edycja produktu");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("produktForm:edit", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie udało się edytować produktu!", null));
                LOG.error("Błąd podczas edycji produktu");
            }
        } else {
            LOG.error("Nie istnieje produkt o takim id");
            FacesContext.getCurrentInstance().addMessage("produktForm:edit", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie istnieje produkt o takim id!", null));
        }
    }
    
    public boolean existsId() {
        try {
            produkt = produktEJB.findById(produktId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Produkt> findAll() {
        list = produktEJB.findAll();
        return list;
    }
    
    public ProduktFacade getProduktEJB() {
        return produktEJB;
    }
    
    public void setProduktEJB(ProduktFacade produktEJB) {
        this.produktEJB = produktEJB;
    }
    
    public Produkt getProdukt() {
        
        return produkt;
    }
    
    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
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
    
    public PZamowionyFacade getpZamowionyEJB() {
        return pZamowionyEJB;
    }
    
    public void setpZamowionyEJB(PZamowionyFacade pZamowionyEJB) {
        this.pZamowionyEJB = pZamowionyEJB;
    }
    
    public List<Produkt> getList() {
        return list;
    }
    
    public void setList(List<Produkt> list) {
        this.list = list;
    }
    
    public double getCena() {
        return cena;
    }
    
    public void setCena(double cena) {
        this.cena = cena;
    }
    
}
