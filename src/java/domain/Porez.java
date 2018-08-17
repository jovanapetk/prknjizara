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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "porez")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Porez.findAll", query = "SELECT p FROM Porez p")
    , @NamedQuery(name = "Porez.findByPorezID", query = "SELECT p FROM Porez p WHERE p.porezID = :porezID")
    , @NamedQuery(name = "Porez.findByStopaPoreza", query = "SELECT p FROM Porez p WHERE p.stopaPoreza = :stopaPoreza")})
public class Porez implements Serializable {

  
    @Column(name = "stopaPoreza")
    private double stopaPoreza;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "porezID")
    private Integer porezID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "porezID")
    private List<Knjiga> knjigaList;

    public Porez() {
    }

    public Porez(Integer porezID) {
        this.porezID = porezID;
    }

    public Porez(Integer porezID, double stopaPoreza) {
        this.porezID = porezID;
        this.stopaPoreza = stopaPoreza;
    }

    public Integer getPorezID() {
        return porezID;
    }

    public void setPorezID(Integer porezID) {
        this.porezID = porezID;
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
        hash += (porezID != null ? porezID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Porez)) {
            return false;
        }
        Porez other = (Porez) object;
        if ((this.porezID == null && other.porezID != null) || (this.porezID != null && !this.porezID.equals(other.porezID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Porez[ porezID=" + porezID + " ]";
    }

    public double getStopaPoreza() {
        return stopaPoreza;
    }

    public void setStopaPoreza(double stopaPoreza) {
        this.stopaPoreza = stopaPoreza;
    }
    
}
