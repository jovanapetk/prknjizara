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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k")
    , @NamedQuery(name = "Korisnik.findByUsername1", query = "SELECT k FROM Korisnik k WHERE k.username1 = :username1")
    , @NamedQuery(name = "Korisnik.findByPassword1", query = "SELECT k FROM Korisnik k WHERE k.password1 = :password1")
    , @NamedQuery(name = "Korisnik.findByImePrezime", query = "SELECT k FROM Korisnik k WHERE k.imePrezime = :imePrezime")
    , @NamedQuery(name = "Korisnik.findByTelefon", query = "SELECT k FROM Korisnik k WHERE k.telefon = :telefon")
    , @NamedQuery(name = "Korisnik.findByAdresa", query = "SELECT k FROM Korisnik k WHERE k.adresa = :adresa")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "username1")
    private String username1;
    @Column(name = "password1")
    private String password1;
    @Column(name = "imePrezime")
    private String imePrezime;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "adresa")
    private String adresa;
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Porudzbina> porudzbinaList;
*/
    public Korisnik() {
    }

    public Korisnik(String username1) {
        this.username1 = username1;
    }

    public Korisnik(String username1, String password1, String imePrezime, String telefon, String adresa) {
        this.username1 = username1;
        this.password1 = password1;
        this.imePrezime = imePrezime;
        this.telefon = telefon;
        this.adresa = adresa;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
/*
    @XmlTransient
    public List<Porudzbina> getPorudzbinaList() {
        return porudzbinaList;
    }

    public void setPorudzbinaList(List<Porudzbina> porudzbinaList) {
        this.porudzbinaList = porudzbinaList;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username1 != null ? username1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.username1 == null && other.username1 != null) || (this.username1 != null && !this.username1.equals(other.username1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username1;
    }
    
}
