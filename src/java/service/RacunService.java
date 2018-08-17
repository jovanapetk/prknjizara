/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Korisnik;
import domain.Porudzbina;
import domain.Stavka;
import domain.StavkaPK;
import domain.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;

/**
 *
 * @author Korisnik
 */
@Named
@RequestScoped
public class RacunService {
    @PersistenceContext(unitName = "prknjizaraPU")
    private EntityManager em;
    
    @Resource
    private javax.transaction.UserTransaction utx;    
    
    @Inject
    StavkaService stavkaService;
     public boolean sacuvajPorudzbinu(Porudzbina porudzbina) {
     try {
            utx.begin();
            int rbr=1;
            for (Stavka stavka : porudzbina.getStavkaList()) {
             stavka.setStavkaPK(new StavkaPK(porudzbina.getPorudzbinaID(), rbr));
             stavka.setPorudzbina(porudzbina);
             rbr++;
             stavkaService.sacuvajStavku(stavka);
         }
            em.persist(porudzbina);
            utx.commit();
            System.out.println("true");
            return true;
        } catch (Exception e) {
             System.out.println("false");
           return false;
        }
    }
     public int vratiRacunID(){
      try {
            utx.begin();
            int id=em.createNamedQuery("Porudzbina.findAll").getResultList().size()+1;
            utx.commit();
            return id;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Tag nije vracen", e);
            throw new RuntimeException(e);
        }
     }
     
}
