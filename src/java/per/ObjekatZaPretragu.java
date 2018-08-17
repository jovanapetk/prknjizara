/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package per;

import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public class ObjekatZaPretragu implements Serializable{
    private int idVrsteObjekta;
    private String idObjektaString;
    private int idObjektaInteger;
    private String nazivObjekta;

    public int getIdObjektaInteger() {
        return idObjektaInteger;
    }

    public void setIdObjektaInteger(int idObjektaInteger) {
        this.idObjektaInteger = idObjektaInteger;
    }

    public int getIdVrsteObjekta() {
        return idVrsteObjekta;
    }

    public void setIdVrsteObjekta(int idVrsteObjekta) {
        this.idVrsteObjekta = idVrsteObjekta;
    }

    public String getIdObjektaString() {
        return idObjektaString;
    }

    public void setIdObjektaString(String idObjektaString) {
        this.idObjektaString = idObjektaString;
    }

    public String getNazivObjekta() {
        return nazivObjekta;
    }

    public void setNazivObjekta(String nazivObjekta) {
        this.nazivObjekta = nazivObjekta;
    }

    public ObjekatZaPretragu(int idVrsteObjekta, String idObjektaString, int idObjektaInteger, String nazivObjekta) {
        this.idVrsteObjekta = idVrsteObjekta;
        this.idObjektaString = idObjektaString;
        this.idObjektaInteger = idObjektaInteger;
        this.nazivObjekta = nazivObjekta;
    }

    @Override
    public String toString() {
        return  nazivObjekta ;
    }

    public ObjekatZaPretragu() {
    }

   

   
    
    
}
