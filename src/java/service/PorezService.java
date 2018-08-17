/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Porez;
import domain.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Korisnik
 */
@Named
@RequestScoped
public class PorezService {
    @PersistenceContext(unitName = "prknjizaraPU")
    private EntityManager em;
    
    @Resource
    private javax.transaction.UserTransaction utx;
    
     public List<Porez> vratiSvePoreze(){
       try {
            utx.begin();
            List<Porez> listaPoreza = em.createNamedQuery("Porez.findAll").getResultList();
            utx.commit();
           
            return listaPoreza;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Lista tagova nije vracena", e);
            throw new RuntimeException(e);
        }
    }
}
