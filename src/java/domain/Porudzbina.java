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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "porudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Porudzbina.findAll", query = "SELECT p FROM Porudzbina p")
    , @NamedQuery(name = "Porudzbina.findByPorudzbinaID", query = "SELECT p FROM Porudzbina p WHERE p.porudzbinaID = :porudzbinaID")
    , @NamedQuery(name = "Porudzbina.findByDatumPorudzbine", query = "SELECT p FROM Porudzbina p WHERE p.datumPorudzbine = :datumPorudzbine")
    , @NamedQuery(name = "Porudzbina.findByCenaBezPDV", query = "SELECT p FROM Porudzbina p WHERE p.cenaBezPDV = :cenaBezPDV")
    , @NamedQuery(name = "Porudzbina.findByUkupno", query = "SELECT p FROM Porudzbina p WHERE p.ukupno = :ukupno")})
public class Porudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "porudzbinaID")
    private Integer porudzbinaID;
    @Column(name = "datumPorudzbine")
    @Temporal(TemporalType.DATE)
    private Date datumPorudzbine;
    @Column(name = "cenaBezPDV")
    private double cenaBezPDV;
    @Column(name = "ukupno")
    private double ukupno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "porudzbina")
    private List<Stavka> stavkaList;
    @JoinColumn(name = "username", referencedColumnName = "username1")
    @ManyToOne(optional = false)
    private Korisnik username;

    public Porudzbina() {
    }

    public Porudzbina(Integer porudzbinaID) {
        this.porudzbinaID = porudzbinaID;
    }

    public Porudzbina(Integer porudzbinaID, Date datumPorudzbine, double cenaBezPDV, double ukupno) {
        this.porudzbinaID = porudzbinaID;
        this.datumPorudzbine = datumPorudzbine;
        this.cenaBezPDV = cenaBezPDV;
        this.ukupno = ukupno;
    }

    public Integer getPorudzbinaID() {
        return porudzbinaID;
    }

    public void setPorudzbinaID(Integer porudzbinaID) {
        this.porudzbinaID = porudzbinaID;
    }

    public Date getDatumPorudzbine() {
        return datumPorudzbine;
    }

    public void setDatumPorudzbine(Date datumPorudzbine) {
        this.datumPorudzbine = datumPorudzbine;
    }

    public double getCenaBezPDV() {
        return cenaBezPDV;
    }

    public void setCenaBezPDV(double cenaBezPDV) {
        this.cenaBezPDV = cenaBezPDV;
    }

    public double getUkupno() {
        return ukupno;
    }

    public void setUkupno(double ukupno) {
        this.ukupno = ukupno;
    }

    @XmlTransient
    public List<Stavka> getStavkaList() {
        return stavkaList;
    }

    public void setStavkaList(List<Stavka> stavkaList) {
        this.stavkaList = stavkaList;
    }

    public Korisnik getUsername() {
        return username;
    }

    public void setUsername(Korisnik username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (porudzbinaID != null ? porudzbinaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Porudzbina)) {
            return false;
        }
        Porudzbina other = (Porudzbina) object;
        if ((this.porudzbinaID == null && other.porudzbinaID != null) || (this.porudzbinaID != null && !this.porudzbinaID.equals(other.porudzbinaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Porudzbina[ porudzbinaID=" + porudzbinaID + " ]";
    }
    
}
