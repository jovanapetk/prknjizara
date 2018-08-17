/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Autor;
import domain.Knjiga;
import domain.Tag;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import service.TagService;

/**
 *
 * @author Korisnik
 */

@ViewScoped
@Named(value= "mBTag")
public class MBTag implements Serializable{
@Inject
TagService tagService;

private List<Tag> listaTagova;
private List<String> listaTagovaString; 
private Tag tag;
private String nazivTagaInput;

@PostConstruct
public void init(){   
listaTagova=tagService.vratiSveTagove();
listaTagovaString=new ArrayList<>();
listaOsnovnihTagova= new ArrayList<>();
    for (Tag tag1 : listaTagova) {
        listaTagovaString.add(tag1.getNazivTaga());
        if(tag1.getTagID()<10){
        listaOsnovnihTagova.add(tag1);
        }
    }
tag= new Tag();

}

public List<Tag> getListaTagova() {
    return listaTagova;
}

public void setListaTagova(List<Tag> listaTagova) {
    this.listaTagova = listaTagova;
}

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
    public String getNazivTagaInput() {
        return nazivTagaInput;
    }

    public void setNazivTagaInput(String nazivTagaInput) {
        this.nazivTagaInput = nazivTagaInput;
    }
   

    

    public List<String> getListaTagovaString() {
        return listaTagovaString;
    }

    public void setListaTagovaString(List<String> listaTagovaString) {
        this.listaTagovaString = listaTagovaString;
    }


//crud tag   
public void izmeniTag(Tag izmeni){

         boolean izmeniTag = tagService.izmeniTag(izmeni);
         if(izmeniTag==true){
             for (Tag tag1 : listaTagova) {
                 if(tag1.getTagID()==izmeni.getTagID()){
                 tag1.setNazivTaga(izmeni.getNazivTaga());
                 }
             }
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh","Tag je izmenjen."));
         }else{
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Tag nije izmenjen."));
         }
}    
 public void sacuvajTag(){
         if(nazivTagaInput.isEmpty()){
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Popunite polje za naziv taga.")); 
          return;
         }
         tag.setNazivTaga(nazivTagaInput);
         for (Tag tag1 : listaTagova) {
                 if(tag1.getNazivTaga().equals(tag.getNazivTaga())){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Tag sa ovim imenom već postoji.")); 
                   return;
                 }
         }
         boolean sacuvajTag = tagService.sacuvajTag(tag);
         if(sacuvajTag==true){
         listaTagova.add(tag);
         tag= new Tag();
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh","Tag je sačuvan."));
         //listaTagova.add(tag);
         }else{
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Tag nije sačuvan."));
         }
  

 }
 public void obrisiTag(Tag obrisi){
     //zasto je ovo ovako a kod autora radi?
     for (Tag tag1 : listaTagova) {
      if(tag1.getTagID()==obrisi.getTagID()) {
      if(!tag1.getKnjigaList().isEmpty()){
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Tag nije moguće obrisati zato što postoje knjige u bazi koje su tagoovane sa: "+obrisi.getNazivTaga()));
      return;
      }
      }
      }  
        
    boolean obrisiTag = tagService.obrisiTag(obrisi);
    if(obrisiTag==true){
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh","Tag je obrisan."));
         listaTagova.remove(obrisi);
         }else{
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Tag nije obrisan."));
         }
    
 }   
 public void ukloniKnjiguIzListeKnjigaTagova(Knjiga knjiga){
     for (Tag tag1 : listaTagova) {
         if(tag1.getKnjigaList().contains(knjiga)){
         tag1.getKnjigaList().remove(knjiga);
         tagService.izmeniTag(tag1);
         }
     }
 }
 public void ubaciKnjiguUListuKnjigaTagova(Knjiga knjiga){
        for (Tag tag1 : listaTagova) {
            for (Tag tag2 : knjiga.getTagList()) {
                if(tag1.getTagID()==tag2.getTagID()){
                tag1.getKnjigaList().add(knjiga);
                }
            }
            tagService.izmeniTag(tag1);
        }
    }

 public Tag vratiTag(int id){
    return tagService.vratiTag(id);
    } 
//tag u knjizi
  
public List<Knjiga> vratiKnjigeSaTagom(Tag tag){
return tag.getKnjigaList();
}   
private List<Tag> listaOsnovnihTagova;

    public List<Tag> getListaOsnovnihTagova() {
        return listaOsnovnihTagova;
    }

    public void setListaOsnovnihTagova(List<Tag> listaOsnovnihTagova) {
        this.listaOsnovnihTagova = listaOsnovnihTagova;
    }

   

   

    
   
}
