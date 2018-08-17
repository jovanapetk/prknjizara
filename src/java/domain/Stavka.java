/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s")
    , @NamedQuery(name = "Stavka.findByPorudzbinaID", query = "SELECT s FROM Stavka s WHERE s.stavkaPK.porudzbinaID = :porudzbinaID")
    , @NamedQuery(name = "Stavka.findByRbr", query = "SELECT s FROM Stavka s WHERE s.stavkaPK.rbr = :rbr")
    , @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina")
    , @NamedQuery(name = "Stavka.findByCenaBezPDV", query = "SELECT s FROM Stavka s WHERE s.cenaBezPDV = :cenaBezPDV")
    , @NamedQuery(name = "Stavka.findByCenaPDV", query = "SELECT s FROM Stavka s WHERE s.cenaPDV = :cenaPDV")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StavkaPK stavkaPK;
    @Column(name = "kolicina")
    private int kolicina;
    @Column(name = "cenaBezPDV")
    private double cenaBezPDV;
    @Column(name = "cenaPDV")
    private double cenaPDV;
    @JoinColumn(name = "porudzbinaID", referencedColumnName = "porudzbinaID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Porudzbina porudzbina;
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private Knjiga isbn;

    public Stavka() {
    }

    public Stavka(StavkaPK stavkaPK) {
        this.stavkaPK = stavkaPK;
    }

    public Stavka(StavkaPK stavkaPK, int kolicina, double cenaBezPDV, double cenaPDV) {
        this.stavkaPK = stavkaPK;
        this.kolicina = kolicina;
        this.cenaBezPDV = cenaBezPDV;
        this.cenaPDV = cenaPDV;
    }

    public Stavka(int porudzbinaID, int rbr) {
        this.stavkaPK = new StavkaPK(porudzbinaID, rbr);
    }

    public StavkaPK getStavkaPK() {
        return stavkaPK;
    }

    public void setStavkaPK(StavkaPK stavkaPK) {
        this.stavkaPK = stavkaPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCenaBezPDV() {
        return cenaBezPDV;
    }

    public void setCenaBezPDV(double cenaBezPDV) {
        this.cenaBezPDV = cenaBezPDV;
    }

    public double getCenaPDV() {
        return cenaPDV;
    }

    public void setCenaPDV(double cenaPDV) {
        this.cenaPDV = cenaPDV;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    public Knjiga getIsbn() {
        return isbn;
    }

    public void setIsbn(Knjiga isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaPK != null ? stavkaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.stavkaPK == null && other.stavkaPK != null) || (this.stavkaPK != null && !this.stavkaPK.equals(other.stavkaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Stavka[ stavkaPK=" + stavkaPK + " ]";
    }
    
}
