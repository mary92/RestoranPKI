/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Narudzbina {
    private String sto;
    private Korisnik konobar;
    private Date vreme;
    private List<StavkaNarudzbine> stavke = new LinkedList<>();

    public String getSto() {
        return sto;
    }

    public void setSto(String sto) {
        this.sto = sto;
    }

    public Korisnik getKonobar() {
        return konobar;
    }

    public void setKonobar(Korisnik konobar) {
        this.konobar = konobar;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public List<StavkaNarudzbine> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaNarudzbine> stavke) {
        this.stavke = stavke;
    }

    @Override
    public String toString() {
        return sto;
    }


}
