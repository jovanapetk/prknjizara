/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domain.Autor;
import domain.Knjiga;
import domain.Porez;
import domain.Porudzbina;
import domain.Stavka;
import domain.Tag;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.SystemException;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import service.KnjigaService;

import org.primefaces.model.DefaultStreamedContent;
import per.ObjekatZaPretragu;
import service.KorisnikService;
import service.PorezService;
import service.RacunService;

/**
 *
 * @author Korisnik
 */
@Named(value="mBKnjiga")
@SessionScoped
public class MBKnjiga implements Serializable{

@Inject
KnjigaService knjigaService;
List<Knjiga> listaKnjiga;
private byte[] image;
@PostConstruct
public void init(){
listaKnjiga=knjigaService.vratiSveKnjige();

listaIzabranihAutora=new ArrayList<>();
izabraniAutori=" ";

listaIzabranihTagovaString= new ArrayList<>();
listaIzabranihTagova= new ArrayList<>();

//pretragaknjiga
listaKnjigaZaPrikaz= listaKnjiga.stream().collect(Collectors.toList());
listaObjekataZaPretragu= new ArrayList<>();
for(Knjiga knjiga: listaKnjiga){
        listaObjekataZaPretragu.add(new ObjekatZaPretragu(1, knjiga.getIsbn(),0,knjiga.getNazivKnjige()));
        }
        for(Tag tag: mbTag.getListaTagova()){
        listaObjekataZaPretragu.add(new ObjekatZaPretragu(2,"",tag.getTagID(),tag.getNazivTaga()));
        }
        for(Autor autor: mbautor.getListaAutora()){
        listaObjekataZaPretragu.add(new ObjekatZaPretragu(3,"" ,autor.getAutorID(),autor.getIme()+" "+autor.getPrezime()));
        }
//knjiga info
knjigainfo=listaKnjiga.get(1);
//korpa
listaKnjigaUKorpi= new ArrayList<>();
listaStavki= new ArrayList<>();
}

  
    public List<Knjiga> getListaKnjiga() {
        return listaKnjiga;
    }

    public void setListaKnjiga(List<Knjiga> listaKnjiga) {
        this.listaKnjiga = listaKnjiga;
    }
    public void obrisiKnjigu(Knjiga knjiga){
    String poruka = knjigaService.obrisiKnjigu(knjiga);
    listaKnjiga.remove(knjiga);
    mbTag.ukloniKnjiguIzListeKnjigaTagova(knjiga);
    mbautor.ukloniKnjiguIzListeKnjigaAutora(knjiga);
   
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "",poruka));
    }

    public void uploadSlike(FileUploadEvent event) throws IOException {
    image = IOUtils.toByteArray(event.getFile().getInputstream());
   
    }
    
    private String brojStrana;
    private Date datum;
    private String stopaPoreza;
    private String cena; 
    private String isbn;
    private String naziv;
    private String opis;
    
    public String getBrojStrana() {
        return brojStrana;
    }

    public void setBrojStrana(String brojStrana) {
        this.brojStrana = brojStrana;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getStopaPoreza() {
        return stopaPoreza;
    }

    public void setStopaPoreza(String stopaPoreza) {
        this.stopaPoreza = stopaPoreza;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    public  void sacuvajKnjiguTransaction(){
    
    if(image==null){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Morate uploadovati sliku"));
    return;
    } 
    if(listaIzabranihAutora.isEmpty()){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Morate izabrati autora"));
    return;
    } 
    if(listaIzabranihTagova.isEmpty()){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Morate izabrati tagove"));
    return;
    }
    
    int porezInt=Integer.parseInt(stopaPoreza);
    Porez porezObject= new Porez();
    porezObject.setPorezID(porezInt);
    switch(porezInt){
        case 1 : 
            porezObject.setStopaPoreza(10);
            break;
        case 2 : 
            porezObject.setStopaPoreza(15);
            break;
        case 3 : 
            porezObject.setStopaPoreza(20);
            break;    
        default: porezObject.setStopaPoreza(0);
    }
    java.sql.Date sqlDate = new java.sql.Date(datum.getTime());
    Knjiga knjiga= new Knjiga(isbn, naziv, Integer.parseInt(brojStrana),sqlDate, Double.parseDouble(cena), image, opis);
    knjiga.setAutorList(listaIzabranihAutora);
    knjiga.setTagList(listaIzabranihTagova);
    knjiga.setPorezID(porezObject);
  
    boolean sacuvana=knjigaService.sacuvajKnjigu(knjiga);
    
    if(sacuvana==false){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Paznja","Knjiga nije sacuvana"));
    }else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paznja","Knjiga je sacuvana"));
    //zasto ovo ne radi sa CASCADETYPE
    mbTag.ubaciKnjiguUListuKnjigaTagova(knjiga);
    mbautor.ubaciKnjiguUListuKnjigaAutora(knjiga);
    reset();
    }
    }
    
    //postavljanje autora
    @Inject
    MBAutor mbautor;
    private List<Autor> listaIzabranihAutora; 
    private String izabraniAutori;
   
    

    public List<Autor> getListaIzabranihAutora() {
        return listaIzabranihAutora;
    }

    public void setListaIzabranihAutora(List<Autor> listaIzabranihAutora) {
        this.listaIzabranihAutora = listaIzabranihAutora;
    }

    public String getIzabraniAutori() {
        return izabraniAutori;
    }

    public void setIzabraniAutori(String izabraniAutori) {
        this.izabraniAutori = izabraniAutori;
    }

    public void dodajAutoraUListu(Autor a){
    if(listaIzabranihAutora.contains(a)){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!","Autor je već dodat."));
    }else{
    listaIzabranihAutora.add(a);
    updateIzabrane();
    }
    }
    public void izbaciAutoraIzListe(Autor a){
    if(!listaIzabranihAutora.contains(a)){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska!","Autor je nije dodat."));
    }else{
    listaIzabranihAutora.remove(a);
    updateIzabrane();
    }
    }
    public void updateIzabrane(){
        izabraniAutori="";
    for (Autor a : listaIzabranihAutora) {
            izabraniAutori=izabraniAutori+" "+ a.getIme()+" "+a.getPrezime()+";";
      }
    }
  //postavljanje tagova
    @Inject
    MBTag mbTag;
    
 
    private List<Tag> listaIzabranihTagova;
    private List<String> listaIzabranihTagovaString;


    public List<Tag> getListaIzabranihTagova() {
        return listaIzabranihTagova;
    }

    public void setListaIzabranihTagova(List<Tag> listaIzabranihTagova) {
        this.listaIzabranihTagova = listaIzabranihTagova;
    }

  
    public List<String> getListaIzabranihTagovaString() {
        return listaIzabranihTagovaString;
    }

    public void setListaIzabranihTagovaString(List<String> listaIzabranihTagovaString) {
        this.listaIzabranihTagovaString = listaIzabranihTagovaString;
    }



  public void postaviListuIzabranihTagova(ValueChangeEvent event){
  listaIzabranihTagovaString =(List <String>) event.getNewValue();
  }

   public void konvertuj(){
   for(Tag t: mbTag.getListaTagova()){
   for(String t1:listaIzabranihTagovaString){
   if(t.getNazivTaga().equals(t1)){
   listaIzabranihTagova.add(t);
   }
   }
   }
   }
   public void reset(){
       setIsbn(null);
       setNaziv(null);
       setBrojStrana(null);
       setStopaPoreza(null);
       setDatum(null);
       setCena(null);
       setOpis(null);
       setImage(null);
       init();
   }
  private Knjiga knjigaZaIzmenu;
    public Knjiga getKnjigaZaIzmenu() {
        return knjigaZaIzmenu;
    }

    public void setKnjigaZaIzmenu(Knjiga knjigaZaIzmenu) {
        this.knjigaZaIzmenu = knjigaZaIzmenu;
    }
  public String postaviKnjiguZaIzmenu(Knjiga k){
      knjigaZaIzmenu=k;   
      isbn=knjigaZaIzmenu.getIsbn();
      naziv=knjigaZaIzmenu.getNazivKnjige();
      brojStrana=knjigaZaIzmenu.getBrojStrana()+"";
      datum=knjigaZaIzmenu.getDatumIzdavanja();
      stopaPoreza=knjigaZaIzmenu.getPorezID().getPorezID()+"";
      opis=knjigaZaIzmenu.getOpisRadnje();
      cena=knjigaZaIzmenu.getCenaBezPDV()+"";
      image=knjigaZaIzmenu.getNaslovna();
      return "adminlogIzmeniKnjigu.xhtml?faces-redirect=true";
  }
  public void izmeniKnjigu(){
   if(image==null){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska","Morate uploadovati sliku."));
    return;
    } 
    
    int porezInt=Integer.parseInt(stopaPoreza);
    Porez porezObject= new Porez();
    porezObject.setPorezID(porezInt);
    switch(porezInt){
        case 1 : 
            porezObject.setStopaPoreza(10);
            break;
        case 2 : 
            porezObject.setStopaPoreza(15);
            break;
        case 3 : 
            porezObject.setStopaPoreza(20);
            break;    
        default: porezObject.setStopaPoreza(0);
    }
    java.sql.Date sqlDate = new java.sql.Date(datum.getTime());
    knjigaZaIzmenu.setIsbn(isbn);
    knjigaZaIzmenu.setNazivKnjige(naziv);
    knjigaZaIzmenu.setBrojStrana(Integer.parseInt(brojStrana));
    knjigaZaIzmenu.setDatumIzdavanja(sqlDate);
    knjigaZaIzmenu.setPorezID(porezObject);
    knjigaZaIzmenu.setNaslovna(image);
    knjigaZaIzmenu.setOpisRadnje(opis);
    knjigaZaIzmenu.setCenaBezPDV(Double.parseDouble(cena));
    boolean izmenjena = knjigaService.izmeniKnjigu(knjigaZaIzmenu);
  
    if(izmenjena==false){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Paznja","Knjiga nije izmenjena"));
    }else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paznja","Knjiga je izmenjena"));    
    reset();
    }
  }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    ////// ostalo
    
    //pretragaKnjiga
    private List<ObjekatZaPretragu> listaObjekataZaPretragu;
    private List<Knjiga> listaKnjigaZaPrikaz;
    private String searchString;
    
    public List<ObjekatZaPretragu> getListaObjekataZaPretragu() {
        return listaObjekataZaPretragu;
    }

    public void setListaObjekataZaPretragu(List<ObjekatZaPretragu> listaObjekataZaPretragu) {
        this.listaObjekataZaPretragu = listaObjekataZaPretragu;
    }
   
     public List<Knjiga> getListaKnjigaZaPrikaz() {
        return listaKnjigaZaPrikaz;
    }

    public void setListaKnjigaZaPrikaz(List<Knjiga> listaKnjigaZaPrikaz) {
        this.listaKnjigaZaPrikaz = listaKnjigaZaPrikaz;
    }
  
    
    public void pretraziKnjigeZaPrikaz(){
        ObjekatZaPretragu objekatZaPretragu= new ObjekatZaPretragu();
        for (ObjekatZaPretragu obj : listaObjekataZaPretragu) {
            if(obj.getNazivObjekta().equals(searchString)){
            objekatZaPretragu=obj;
            }
        }
    listaKnjigaZaPrikaz=new ArrayList<>();
    if(searchString.equals("")){
    listaKnjigaZaPrikaz=listaKnjiga.stream().collect(Collectors.toList());
    }
    int vrsta =objekatZaPretragu.getIdVrsteObjekta();
    
    if(vrsta==1){
    Knjiga k = knjigaService.vratiKnjigu(objekatZaPretragu.getIdObjektaString());
    listaKnjigaZaPrikaz.add(k);
    }
    if(vrsta==2){
    int id=objekatZaPretragu.getIdObjektaInteger();
    Tag t = mbTag.vratiTag(id);
    listaKnjigaZaPrikaz= t.getKnjigaList().stream().collect(Collectors.toList());
    }
    if(vrsta==3){
    int id=objekatZaPretragu.getIdObjektaInteger();
    Autor a = mbautor.vratiAutora(id);
    listaKnjigaZaPrikaz= a.getKnjigaList().stream().collect(Collectors.toList());
    }
    searchString="";
    }
     public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    public void pretraziKnjigeSaTagom(Tag t){
        listaKnjigaZaPrikaz= new ArrayList<>();
        for (Knjiga k:t.getKnjigaList()) {
            listaKnjigaZaPrikaz.add(k);
            }
    }
    //prikaz  naslovne
    
   
    public String vratiEncodedNaslovnu(Knjiga k) throws UnsupportedEncodingException{
    if(k==null){
    k=knjigainfo;
    }
    byte[] encodeBase64 = Base64.getEncoder().encode(k.getNaslovna());
    String base64Encoded = new String(encodeBase64, "UTF-8");
    return base64Encoded;
    }
  
   
  //knjigainfo
    private Knjiga knjigainfo;
    
    public Knjiga getKnjigainfo() {
        return knjigainfo;
    }

    public String setKnjigainfo(Knjiga knjigainfo) {
        this.knjigainfo = knjigainfo;
        return "knjigainfo.xhtml?faces-redirect=true";
    }
    public String formatirajDatum(){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(knjigainfo.getDatumIzdavanja());
    
    }
    // korpa
    @Inject
    MBKorisnik mbKorisnik;
    
    @Inject
    PorezService porezService;
    
    @Inject
    RacunService racunService;
    
    private List<Knjiga>listaKnjigaUKorpi;

    public List<Knjiga> getListaKnjigaUKorpi() {
        return listaKnjigaUKorpi;
    }

    public void setListaKnjigaUKorpi(List<Knjiga> listaKnjigaUKorpi) {
        this.listaKnjigaUKorpi = listaKnjigaUKorpi;
    }
   
    private List <Stavka> listaStavki;
    
      public List <Stavka> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(List <Stavka> listaStavki) {
        this.listaStavki = listaStavki;
    }
    
   
    public void dodajUKorpu(Knjiga knjiga){ 
    if(knjiga==null){
    knjiga=knjigainfo;
    }
    if(listaKnjigaUKorpi.contains(knjiga)){
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Paznja","Već ste dodali knjigu u korpu."));
    return;
    }
   
    listaKnjigaUKorpi.add(knjiga);
    Stavka s= new Stavka();
    s.setIsbn(knjiga);
    s.setKolicina(1);
    listaStavki.add(s);
    RequestContext.getCurrentInstance().execute("$('.modalPseudoClass').modal()");
    }
    public void ukloniIzUKorpe(Stavka s){
    listaKnjigaUKorpi.remove(s.getIsbn());
    listaStavki.remove(s);
    }
    
    public String izracunajUkupnuCenu(){
    double ukupno=0;
    for(Stavka s: listaStavki ){
    ukupno=ukupno+s.getIsbn().getCenaBezPDV()*s.getKolicina();
    }
    return ukupno+ "";
    }
    
    public void sacuvajPorudzbinu(){
    if(mbKorisnik.getPrijavljeniKorisnik()==null){
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška","Niste prijavljeni."));
     return;
    }
        
        double ukupnoBezPDVa=0;
        double ukupnoSaPDVom=0;
        for (Stavka stavka : listaStavki) {
            stavka.setCenaBezPDV(stavka.getIsbn().getCenaBezPDV());
            ukupnoBezPDVa+=stavka.getCenaBezPDV()*stavka.getKolicina();
            for (Porez porez : porezService.vratiSvePoreze()) {
                if(stavka.getIsbn().getPorezID().getPorezID()==porez.getPorezID()){
                    stavka.setCenaPDV(stavka.getCenaBezPDV()+stavka.getCenaBezPDV()*porez.getStopaPoreza()/100); 
                    ukupnoSaPDVom+=stavka.getCenaPDV()*stavka.getKolicina();
                    
                }
            }
        }
        ukupnoSaPDVom=(double)Math.round(ukupnoSaPDVom * 100000d) / 100000d;
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        int porudzbinaID=racunService.vratiRacunID();
        Porudzbina porudzbina= new Porudzbina(porudzbinaID, timestamp, ukupnoBezPDVa, ukupnoSaPDVom);
        porudzbina.setUsername(mbKorisnik.getPrijavljeniKorisnik());
        porudzbina.setStavkaList(listaStavki);
       
        boolean sacuvajPorudzbinu = racunService.sacuvajPorudzbinu(porudzbina);
        if(sacuvajPorudzbinu==false){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška","Porudzbina nije sačuvana."));
        }else{
            listaKnjigaUKorpi= new ArrayList<>();
            listaStavki= new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paznja","Porudzbina je sačuvana."));
            
        }
    }
  //porudzbine info
    
   

    
  
}