/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package per;

import domain.Autor;

/**
 *
 * @author Korisnik
 */
public class IzmeniPersist {
    private static IzmeniPersist instance;

    private IzmeniPersist () {

    }
    private Autor izmeni;


    public static IzmeniPersist  getInstance() {
        if (instance == null) {
            instance = new IzmeniPersist();
        }
        return instance;
    }

    public Autor getIzmeni() {
        return izmeni;
    }

    public void setIzmeni(Autor izmeni) {
        this.izmeni = izmeni;
    }
    public void reset(){
    izmeni=null;
    }
}
