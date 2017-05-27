/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import projekt.model.Produkt;

/**
 *
 * @author Daniel
 */
@Stateless
public class ProduktFacade extends AbstractFacade<Produkt> {

    @PersistenceContext(unitName = "pawww_projekt_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProduktFacade() {
        super(Produkt.class);
    }
    
    public void deleteById(int id){
        Query query = em.createQuery("DELETE FROM Produkt p WHERE p.produktId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    
    public Produkt findById(int id){
        TypedQuery query = em.createNamedQuery("Produkt.findByProduktId", Produkt.class);
        query.setParameter("produktId", id);
        return (Produkt) query.getSingleResult();
    }
}
