/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projekt.model.Person;

/**
 *
 * @author Daniel
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "pawww_projekt_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }

    public Person findByLogin(String login) {

        Query query = em.createQuery("SELECT p FROM Person p join p.auth a WHERE a.login = :login", Person.class);
        query.setParameter("login", login);

        return (Person) query.getSingleResult();
    }
    
    public boolean authenticateUser(String login,String password){
        Query query = em.createQuery("SELECT p FROM Person p join p.auth a WHERE a.login = :login", Person.class);
        query.setParameter("login", login);
        try{query.getSingleResult();}
        catch(NoResultException e){
            return false;
        }
        return true;
    }
    
    public boolean existsEmail(String email){
        Query query = em.createNamedQuery("Person.findByEmail");
        query.setParameter("email", email);
        try{query.getSingleResult();}
        catch(NoResultException e){
            return false;
        }
        return true;
    }

}
