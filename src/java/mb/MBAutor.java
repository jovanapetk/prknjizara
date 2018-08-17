/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Autor;
import domain.Knjiga;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import per.IzmeniPersist;
import service.AutorService;

/**
 *
 * @author Korisnik
 */
@ViewScoped
@Named(value = "mBAutor")
public class MBAutor implements Serializable{

@Inject
AutorService autorService;

private List<Autor> listaAutora;
private Autor autor;
private byte[] image;
@PostConstruct
public void init(){

listaAutora=autorService.vratiSveAutore();

if(IzmeniPersist.getInstance().getIzmeni()==null){
autor= new Autor();

}else{
autor=IzmeniPersist.getInstance().getIzmeni();
}

}

    public List<Autor> getListaAutora() {
        return listaAutora;
    }

    public void setListaAutora(List<Autor> listaAutora) {
        this.listaAutora = listaAutora;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public void uploadSlike(FileUploadEvent event) throws IOException {
    image = IOUtils.toByteArray(event.getFile().getInputstream());
    }
    //crud autor
    public void sacuvajIzmeniAutora(){
        if(IzmeniPersist.getInstance().getIzmeni()==null){
        for (Autor autor1 : listaAutora) {
            if(autor.getIme().equals(autor1.getIme()) && autor.getPrezime().equals(autor1.getPrezime())){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Ovaj autor već postoji."));
             return;
            }
        }
        if(image==null){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Morate uploadovati sliku"));
        return;
        }
        autor.setPortret(image);
    boolean sacuvajAutora=autorService.sacuvajAutora(autor);
    if(sacuvajAutora==true){
         listaAutora.add(autor);
         autor= new Autor();
         image=null;
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh","Autor je sačuvan."));
         
         }else{
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Autor nije sačuvan."));
         }
        }
        else{
        if(image!=null){
        autor.setPortret(image);
        }
        boolean izmeniAutora = autorService.izmeniAutora(autor);
        if(izmeniAutora==true){
         for (Autor autor1 : listaAutora) {
            if(autor.getAutorID()==autor1.getAutorID()){
            autor1=autor;
            }
        }
         
         IzmeniPersist.getInstance().reset();
         autor=new Autor();
         image=null;
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh","Autor je izmenjen."));
         }else{
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Autor nije izmenjen."));
         }
        }
    }
    
    public void obrisiAutora(Autor obrisi){
    
     if(obrisi.getKnjigaList().isEmpty()){
    boolean obrisiAutora = autorService.obrisiAutora(obrisi);
    if(obrisiAutora==true){
         listaAutora.remove(obrisi);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh","Autor je obrisan."));
         }else{
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Autor nije obrisan. Neophodno je prvo obrisati knjige koje je napisao autor: "+obrisi.getIme()+" "+obrisi.getPrezime()));
         }
     }else{
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Greska","Autora nije moguće obrisati zato što postoje knjige u bazi koje je napisao autor:"+obrisi.getIme()+" "+obrisi.getPrezime()));
     return;
    } 
    }

    
    public String postaviAutoraZaIzmenu(Autor autorZaIzmenu){
    IzmeniPersist.getInstance().setIzmeni(autorZaIzmenu);
    return "adminlogDodajIzmeniAutora.xhtml?faces-redirect=true";
    }
    public void ukloniKnjiguIzListeKnjigaAutora(Knjiga knjiga){
     for (Autor autor1 : listaAutora) {
         System.out.println("usao je u petlju");
         if(autor1.getKnjigaList().contains(knjiga)){
             autor1.getKnjigaList().remove(knjiga);
             autorService.izmeniAutora(autor1);
         }
     
    }
   }
    public void ubaciKnjiguUListuKnjigaAutora(Knjiga knjiga){
        for (Autor autor1 : listaAutora) {
            for (Autor autor2 : knjiga.getAutorList()) {
                if(autor1.getAutorID()==autor2.getAutorID()){
                autor1.getKnjigaList().add(knjiga);
                }
            }
            autorService.izmeniAutora(autor1);
        }
    }
    public Autor vratiAutora(int id){
    return autorService.vratiAutora(id);
    }
    
    //ostalo
    private Autor autorinfo;

    public Autor getAutorinfo() {
        return autorinfo;
    }

    public void setAutorinfo(Autor autorinfo) {
        this.autorinfo = autorinfo;
    }
    
       public String vratiPortetEncoded(Autor autor) throws UnsupportedEncodingException{
       byte[] encodeBase64 = Base64.getEncoder().encode(autor.getPortret());
       String base64Encoded = new String(encodeBase64, "UTF-8");
       return base64Encoded;
    }
   
    
}
