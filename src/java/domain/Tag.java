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
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")
    , @NamedQuery(name = "Tag.findByTagID", query = "SELECT t FROM Tag t WHERE t.tagID = :tagID")
    , @NamedQuery(name = "Tag.findByNazivTaga", query = "SELECT t FROM Tag t WHERE t.nazivTaga = :nazivTaga")})
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "tagID")
    private Integer tagID;
    @Column(name = "nazivTaga")
    private String nazivTaga;
    
    @ManyToMany(mappedBy = "tagList")
    private List<Knjiga> knjigaList;

    public Tag() {
    }

    public Tag(Integer tagID) {
        this.tagID = tagID;
    }

    public Tag(Integer tagID, String nazivTaga) {
        this.tagID = tagID;
        this.nazivTaga = nazivTaga;
    }

    public Integer getTagID() {
        return tagID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }

    public String getNazivTaga() {
        return nazivTaga;
    }

    public void setNazivTaga(String nazivTaga) {
        this.nazivTaga = nazivTaga;
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
        hash += (tagID != null ? tagID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.tagID == null && other.tagID != null) || (this.tagID != null && !this.tagID.equals(other.tagID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazivTaga;
    }
    
}
