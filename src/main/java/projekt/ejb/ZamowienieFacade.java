/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.ejb;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import projekt.model.Zamowienie;

/**
 *
 * @author Daniel
 */
@Stateless
public class ZamowienieFacade extends AbstractFacade<Zamowienie> {

    @PersistenceContext(unitName = "pawww_projekt_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZamowienieFacade() {
        super(Zamowienie.class);
    }
    
    public Zamowienie addZamowienie(Zamowienie zamowienie){
        this.create(zamowienie);
        return zamowienie;
    }
    
    public void deleteEmpty(){
        try{
        em.createQuery("DELETE FROM Zamowienie z WHERE z.pZamowionyList IS EMPTY ").executeUpdate();
        }catch(Exception e){
            throw e;
        }
    }
    
}
