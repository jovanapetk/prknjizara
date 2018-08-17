/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Korisnik
 */
@Embeddable
public class StavkaPK implements Serializable {

    @Column(name = "porudzbinaID")
    private int porudzbinaID;
    @Column(name = "rbr")
    private int rbr;

    public StavkaPK() {
    }

    public StavkaPK(int porudzbinaID, int rbr) {
        this.porudzbinaID = porudzbinaID;
        this.rbr = rbr;
    }

    public int getPorudzbinaID() {
        return porudzbinaID;
    }

    public void setPorudzbinaID(int porudzbinaID) {
        this.porudzbinaID = porudzbinaID;
    }

    public int getRbr() {
        return rbr;
    }

    public void setRbr(int rbr) {
        this.rbr = rbr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) porudzbinaID;
        hash += (int) rbr;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaPK)) {
            return false;
        }
        StavkaPK other = (StavkaPK) object;
        if (this.porudzbinaID != other.porudzbinaID) {
            return false;
        }
        if (this.rbr != other.rbr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.StavkaPK[ porudzbinaID=" + porudzbinaID + ", rbr=" + rbr + " ]";
    }
    
}
