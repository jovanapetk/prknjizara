/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "knjiga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Knjiga.findAll", query = "SELECT k FROM Knjiga k")
    , @NamedQuery(name = "Knjiga.findByIsbn", query = "SELECT k FROM Knjiga k WHERE k.isbn = :isbn")
    , @NamedQuery(name = "Knjiga.findByNazivKnjige", query = "SELECT k FROM Knjiga k WHERE k.nazivKnjige = :nazivKnjige")
    , @NamedQuery(name = "Knjiga.findByBrojStrana", query = "SELECT k FROM Knjiga k WHERE k.brojStrana = :brojStrana")
    , @NamedQuery(name = "Knjiga.findByDatumIzdavanja", query = "SELECT k FROM Knjiga k WHERE k.datumIzdavanja = :datumIzdavanja")
    , @NamedQuery(name = "Knjiga.findByCenaBezPDV", query = "SELECT k FROM Knjiga k WHERE k.cenaBezPDV = :cenaBezPDV")
    , @NamedQuery(name = "Knjiga.findByOpisRadnje", query = "SELECT k FROM Knjiga k WHERE k.opisRadnje = :opisRadnje")})
public class Knjiga implements Serializable {


    @Lob
    @Column(name = "naslovna")
    private byte[] naslovna;

    private static final long serialVersionUID = 1L;
    @Id
  
    @Column(name = "isbn")
    private String isbn;
  
    @Column(name = "nazivKnjige")
    private String nazivKnjige;
   
    @Column(name = "brojStrana")
    private int brojStrana;
   
    @Column(name = "datumIzdavanja")
    @Temporal(TemporalType.DATE)
    private Date datumIzdavanja;
  
    @Column(name = "cenaBezPDV")
    private double cenaBezPDV;
   
   
    @Column(name = "opisRadnje")
    private String opisRadnje;
    @ManyToMany(cascade={CascadeType.DETACH},fetch = FetchType.LAZY )
    @JoinTable(name = "tagknjige", joinColumns = {
        @JoinColumn(name = "isbn", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "tagID", referencedColumnName = "tagID")})
    private List<Tag> tagList;
    @ManyToMany(cascade={CascadeType.DETACH},fetch = FetchType.LAZY )
    @JoinTable(name = "autorknjige", joinColumns = {
        @JoinColumn(name = "isbn", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "autorID", referencedColumnName = "autorID")})
    private List<Autor> autorList;    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn")
    private List<Stavka> stavkaList;
    @JoinColumn(name = "porezID", referencedColumnName = "porezID")
    @ManyToOne(optional = false)
    private Porez porezID;

    public Knjiga() {
    }

    public Knjiga(String isbn) {
        this.isbn = isbn;
    }

    public Knjiga(String isbn, String nazivKnjige, int brojStrana, Date datumIzdavanja, double cenaBezPDV, byte[] naslovna, String opisRadnje) {
        this.isbn = isbn;
        this.nazivKnjige = nazivKnjige;
        this.brojStrana = brojStrana;
        this.datumIzdavanja = datumIzdavanja;
        this.cenaBezPDV = cenaBezPDV;
        this.naslovna = naslovna;
        this.opisRadnje = opisRadnje;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public int getBrojStrana() {
        return brojStrana;
    }

    public void setBrojStrana(int brojStrana) {
        this.brojStrana = brojStrana;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public double getCenaBezPDV() {
        return cenaBezPDV;
    }

    public void setCenaBezPDV(double cenaBezPDV) {
        this.cenaBezPDV = cenaBezPDV;
    }


    public String getOpisRadnje() {
        return opisRadnje;
    }

    public void setOpisRadnje(String opisRadnje) {
        this.opisRadnje = opisRadnje;
    }

    @XmlTransient
    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @XmlTransient
    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }

    @XmlTransient
    public List<Stavka> getStavkaList() {
        return stavkaList;
    }

    public void setStavkaList(List<Stavka> stavkaList) {
        this.stavkaList = stavkaList;
    }

    public Porez getPorezID() {
        return porezID;
    }

    public void setPorezID(Porez porezID) {
        this.porezID = porezID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Knjiga)) {
            return false;
        }
        Knjiga other = (Knjiga) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazivKnjige;
    }

    public byte[] getNaslovna() {
        return naslovna;
    }

    public void setNaslovna(byte[] naslovna) {
        this.naslovna = naslovna;
    }
    
    
}
