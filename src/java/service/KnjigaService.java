/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import domain.Knjiga;
import domain.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.SystemException;
/**
 *
 * @author Korisnik
 */
@Named
@RequestScoped
public class KnjigaService {
    @PersistenceContext(unitName = "prknjizaraPU")
    private EntityManager em;
    
    @Resource
    private javax.transaction.UserTransaction utx;

   
    public List<Knjiga> vratiSveKnjige(){
       try {
            utx.begin();
            List<Knjiga> listaKnjiga = em.createNamedQuery("Knjiga.findAll").getResultList();
            utx.commit();
            return listaKnjiga;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Lista knjiga nije vracena", e);
            throw new RuntimeException(e);
        }
    }
    public String obrisiKnjigu(Knjiga knjiga){
         try {
            utx.begin();
            Knjiga obrisi= (Knjiga) em.createNamedQuery("Knjiga.findByIsbn").setParameter("isbn", knjiga.getIsbn()).getSingleResult();
            em.remove(obrisi);
            utx.commit();
            return "Knjiga sa isbn-om "+knjiga.getIsbn()+" je obrisana";
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Knjiga nije obrisana", e);
            return "Knjiga nije obrisana.";
        }
    }
    public boolean sacuvajKnjigu(Knjiga knjiga) {
     try {
            utx.begin();
            em.persist(knjiga);
            utx.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return false;
        }
    }
    public boolean izmeniKnjigu(Knjiga knjiga) {
     try {
            utx.begin();
            em.merge(knjiga);
            utx.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return false;
        }
    }
    public Knjiga vratiKnjigu(String isbn){
     try {
            utx.begin();
            Knjiga knjiga = (Knjiga) em.createNamedQuery("Knjiga.findByIsbn").setParameter("isbn", isbn).getSingleResult();
            utx.commit();
            return knjiga;
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Knjiga nije vracena", e);
            throw new RuntimeException(e);
        }
   
    }
}
