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
import projekt.model.Auth;

/**
 *
 * @author Daniel
 */
@Stateless
public class AuthFacade extends AbstractFacade<Auth> {

    @PersistenceContext(unitName = "pawww_projekt_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthFacade() {
        super(Auth.class);
    }
    
    public Auth findByLogin(String login){
        Query query = em.createNamedQuery("Auth.findByLogin",Auth.class);
        query.setParameter("login",login);
        return (Auth) query.getSingleResult();
    }
    
    public boolean existLogin(String login){
        try{
        em.createNamedQuery("Auth.findByLogin",Auth.class).setParameter("login", login).getSingleResult();
        return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
