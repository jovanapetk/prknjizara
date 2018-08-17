/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Korisnik;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.persistence.PersistenceContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.RollbackException;
import java.util.List;
/**
 *
 * @author Korisnik
 */
@Named
@RequestScoped
public class KorisnikService {
   @PersistenceContext(unitName = "prknjizaraPU")
    private EntityManager em;
    
    @Resource
    private javax.transaction.UserTransaction utx;

   
    public List<Korisnik> vratiSveKorisnike(){
       try {
            utx.begin();
            List<Korisnik> listaKorisnika = em.createNamedQuery("Korisnik.findAll").getResultList();
            utx.commit();
            return listaKorisnika;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Lista autora nije vracena", e);
            throw new RuntimeException(e);
        }
    }
    
    public boolean sacuvajKorisnika(Korisnik korisnik) {
     try {
            utx.begin();
            em.persist(korisnik);
            utx.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return false;
        }
    }
    
    public Korisnik prijaviKorisnika(String username, String password){
        
        try {
            Korisnik userDB = null;
            
            utx.begin();
            
            userDB = (Korisnik) em.createNamedQuery("Korisnik.findByUsername1")
                    .setParameter("username1", username).getSingleResult();
            
            if (userDB.getPassword1().equals(password)){
                return userDB;
            }
        } catch (Exception ex) {
            return null;
        } 
        return null;
        
        
    }
   
}
