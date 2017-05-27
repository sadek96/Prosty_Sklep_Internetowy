/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import projekt.ejb.ProduktFacade;
import projekt.model.PZamowiony;
import projekt.model.Produkt;

/**
 *
 * @author Daniel
 */
@Named(value = "cartSessionBean")
@SessionScoped
public class CartSessionBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(CartSessionBean.class);
    
    @EJB
    ProduktFacade produktEJB;

    private Integer itemId;

    private List<PZamowiony> cart;

    private double total;

    /**
     * Creates a new instance of CartSessionBean
     */
    public CartSessionBean() {
    }

    @PostConstruct
    public void init() {
        cart = new <PZamowiony> ArrayList();
    }

    public void addToCart(Produkt produkt) {
        for (PZamowiony pZamowiony : cart) {
            if (Objects.equals(pZamowiony.getIdProduktu(), produkt.getProduktId())) {

                pZamowiony.setIlosc(pZamowiony.getIlosc() + 1);
                return;
            }
        }

        PZamowiony pZamowiony = new PZamowiony();
        pZamowiony.setIdProduktu(produkt.getProduktId());
        pZamowiony.setNazwa(produkt.getNazwa());
        pZamowiony.setCena(produkt.getCena());
        pZamowiony.setIlosc(1);
        cart.add(pZamowiony);

        LOG.info("Użytkownik dodał produkt do koszyka");
    }

    public void remove(PZamowiony pZamowiony) {
        for (PZamowiony item : cart) {
            if (item.equals(pZamowiony)) {
                cart.remove(item);
                LOG.info("Użytkownik usunął produkt z koszyka");
                break;
            }
        }

    }

    public void clear() {
        cart.clear();
    }

    public List<PZamowiony> getCart() {
        return cart;
    }

    public void setCart(List<PZamowiony> cart) {
        this.cart = cart;
    }

    public Double getTotal() {
        total = 0;
        for (PZamowiony item : cart) {
            total = (double) (total + (item.getIlosc() * item.getCena()));
        }
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return the itemId
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;

    }

    public ProduktFacade getProduktEJB() {
        return produktEJB;
    }

    public void setProduktEJB(ProduktFacade produktEJB) {
        this.produktEJB = produktEJB;
    }

}
