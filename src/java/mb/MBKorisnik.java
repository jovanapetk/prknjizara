/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Korisnik;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import service.KorisnikService;

/**
 *
 * @author Korisnik
 */
@Named (value= "mbPrijava")
@SessionScoped
public class MBKorisnik implements Serializable{
     
    
    @Inject 
    KorisnikService korisnikService;
    
    String username;
    String password;
   
    Korisnik prijavljeniKorisnik;
    Korisnik noviKorisnik;
    
    private List<Korisnik> listaKorisnika;
    
    @PostConstruct
    public void init(){
    noviKorisnik= new Korisnik();
    prijavljeniKorisnik=null;
    listaKorisnika= korisnikService.vratiSveKorisnike();
    }

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Korisnik getPrijavljeniKorisnik() {
        return prijavljeniKorisnik;
    }

    public void setPrijavljeniKorisnik(Korisnik prijavljeniKorisnik) {
        this.prijavljeniKorisnik = prijavljeniKorisnik;
    }
    
    public Korisnik getNoviKorisnik() {
        return noviKorisnik;
    }

    public void setNoviKorisnik(Korisnik noviKorisnik) {
        this.noviKorisnik = noviKorisnik;
    }
    public List<Korisnik> getListaKorisnika() {
        return listaKorisnika;
    }

    public void setListaKorisnika(List<Korisnik> listaKorisnika) {
        this.listaKorisnika = listaKorisnika;
    }
    public String prijaviKorisnika(){
        if(username.isEmpty() || password.isEmpty()){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška","Polja ne smeju da budu prazna."));
   
        }else{
        for (Korisnik korisnik : listaKorisnika) {
            if(username.equals(korisnik.getUsername1()) && password.equals(korisnik.getPassword1())){
            prijavljeniKorisnik= new Korisnik();
            prijavljeniKorisnik=korisnik;
            System.out.println("Prijavljeni korisnik je"+prijavljeniKorisnik.getUsername1());
            if(korisnik.getUsername1().equals("admin")){
            return "adminlog.xhtml?faces-redirect=true";
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh","Korisnik je autentifikovan"));
          
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška","Pogrešno korisničko ime ili lozinka."));
        }
        return "";
    }
        
       
        

    public String logout(){
    prijavljeniKorisnik=null;
    username=null;
    password=null;
    System.out.println("odlogovan"); 
    return "pocetna.xhtml?faces-redirect=true";
    }  

    
    
    public void sacuvajKorisnika(){
    boolean sacuvajKorisnika = korisnikService.sacuvajKorisnika(noviKorisnik);
    
    if(sacuvajKorisnika==false){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška","Korisnik nije sačuvan."));
    }else{
    listaKorisnika.add(noviKorisnik);
    noviKorisnik=new Korisnik();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paznja","Korisnik je sačuvan."));    
    }
   
    }
    
    
 
}
