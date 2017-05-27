/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.EJB;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import projekt.ejb.ProduktFacade;
import projekt.model.Produkt;

/**
 *
 * @author Daniel
 */
@RunWith(Arquillian.class)
public class ProduktControllerTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ProduktFacade.class).addAsManifestResource("persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Inject
    ProduktFacade produktFacade;

    @Test
    public void should_find_id() {
        Produkt produkt = new Produkt();
        produkt.setProduktId(1);
        produkt.setNazwa("Nazwa");
        produkt.setCena(7.77);
        produktFacade.create(produkt);
        Produkt result = produktFacade.find(1);
        assert (produkt.equals(result));
    }
}
