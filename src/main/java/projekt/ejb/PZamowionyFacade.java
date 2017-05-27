/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import projekt.model.PZamowiony;

/**
 *
 * @author Daniel
 */
@Stateless
public class PZamowionyFacade extends AbstractFacade<PZamowiony> {

    @PersistenceContext(unitName = "pawww_projekt_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PZamowionyFacade() {
        super(PZamowiony.class);
    }

    public boolean existsId(int id) {
        try {
            find(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void deleteWithId(Integer id) {
        try {
            em.createNamedQuery("PZamowiony.deleteWithId").setParameter("idProduktu", id).executeUpdate();

        } catch (Exception e) {

            throw e;
        }
    }
}
