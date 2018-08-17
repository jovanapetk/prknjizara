/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


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
public class TagService {
    @PersistenceContext(unitName = "prknjizaraPU")
    private EntityManager em;
    
    @Resource
    private javax.transaction.UserTransaction utx;
   
    public List<Tag> vratiSveTagove(){
       try {
            utx.begin();
            List<Tag> listaTagova = em.createNamedQuery("Tag.findAll").getResultList();
            utx.commit();
           
            return listaTagova;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Lista tagova nije vracena", e);
            throw new RuntimeException(e);
        }
    }
     public boolean obrisiTag(Tag tag){
         try {
            utx.begin();
            Tag obrisi= (Tag) em.createNamedQuery("Tag.findByTagID").setParameter("tagID", tag.getTagID()).getSingleResult();
            em.remove(obrisi);
            utx.commit();
            return true;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Knjiga nije obrisana", e);
            return false;
        }
    }
    public boolean sacuvajTag(Tag tag) {
     try {
            utx.begin();
            em.persist(tag);
            utx.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return false;
        }
    }
    public boolean izmeniTag(Tag tag) {
     try {
            utx.begin();
            em.merge(tag);
            utx.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return false;
        }
    }
    public Tag vratiTag(int tagID){
     try {
            utx.begin();
            Tag tag = (Tag) em.createNamedQuery("Tag.findByTagID").setParameter("tagID", tagID).getSingleResult();
            utx.commit();
            return tag;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Tag nije vracen", e);
            throw new RuntimeException(e);
        }
   
    }

}
