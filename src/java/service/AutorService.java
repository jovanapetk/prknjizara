/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Autor;
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
public class AutorService {
    @PersistenceContext(unitName = "prknjizaraPU")
    private EntityManager em;
    
    @Resource
    private javax.transaction.UserTransaction utx;

   
    public List<Autor> vratiSveAutore(){
       try {
            utx.begin();
            List<Autor> listaAutora = em.createNamedQuery("Autor.findAll").getResultList();
            utx.commit();
            return listaAutora;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Lista autora nije vracena", e);
            throw new RuntimeException(e);
        }
    }
    public boolean obrisiAutora(Autor autor){
         try {
            utx.begin();
            Autor obrisi= (Autor) em.createNamedQuery("Autor.findByAutorID").setParameter("autorID",autor.getAutorID()).getSingleResult();
            em.remove(obrisi);
            utx.commit();
            return true;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Autor nije obrisan.", e);
            return false;
        }
    }
    public boolean sacuvajAutora(Autor autor) {
     try {
            utx.begin();
            em.persist(autor);
            utx.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return false;
        }
    }
    public boolean izmeniAutora(Autor autor) {
     try {
            utx.begin();
            em.merge(autor);
            utx.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return false;
        }
    }
    public Autor vratiAutora(int autorID){
     try {
            utx.begin();
            Autor autor = (Autor) em.createNamedQuery("Autor.findByAutorID").setParameter("autorID",autorID).getSingleResult();
            utx.commit();
            return autor;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Autor nije vracen", e);
            throw new RuntimeException(e);
        }
   
    }
}
