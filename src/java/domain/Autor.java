/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "autor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autor.findAll", query = "SELECT a FROM Autor a")
    , @NamedQuery(name = "Autor.findByAutorID", query = "SELECT a FROM Autor a WHERE a.autorID = :autorID")
    , @NamedQuery(name = "Autor.findByIme", query = "SELECT a FROM Autor a WHERE a.ime = :ime")
    , @NamedQuery(name = "Autor.findByPrezime", query = "SELECT a FROM Autor a WHERE a.prezime = :prezime")
    , @NamedQuery(name = "Autor.findByBiografija", query = "SELECT a FROM Autor a WHERE a.biografija = :biografija")})
public class Autor implements Serializable {


    @Lob
    @Column(name = "portret")
    private byte[] portret;

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue 
    @Column(name = "autorID")
    private Integer autorID;
   
    @Column(name = "ime")
    private String ime;
  
    @Column(name = "prezime")
    private String prezime;
   
    @Column(name = "biografija")
    private String biografija;
    
    @ManyToMany(mappedBy = "autorList" )
    private List<Knjiga> knjigaList;

    public Autor() {
    }

    public Autor(Integer autorID) {
        this.autorID = autorID;
    }

    public Autor(Integer autorID, String ime, String prezime, String biografija, byte[] portret) {
        this.autorID = autorID;
        this.ime = ime;
        this.prezime = prezime;
        this.biografija = biografija;
        this.portret = portret;
    }

    public Integer getAutorID() {
        return autorID;
    }

    public void setAutorID(Integer autorID) {
        this.autorID = autorID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }

    @XmlTransient
    public List<Knjiga> getKnjigaList() {
        return knjigaList;
    }

    public void setKnjigaList(List<Knjiga> knjigaList) {
        this.knjigaList = knjigaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (autorID != null ? autorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.autorID == null && other.autorID != null) || (this.autorID != null && !this.autorID.equals(other.autorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ime+" "+prezime;
    }

    public byte[] getPortret() {
        return portret;
    }

    public void setPortret(byte[] portret) {
        this.portret = portret;
    }
    
}
