/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;


public class StavkaNarudzbine {
    private StavkaMenija stavkaMenija;
    private boolean placeno;

    public StavkaMenija getStavkaMenija() {
        return stavkaMenija;
    }

    public void setStavkaMenija(StavkaMenija sm) {
        this.stavkaMenija = sm;
    }

    public boolean isPlaceno() {
        return placeno;
    }

    public void setPlaceno(boolean placeno) {
        this.placeno = placeno;
    }

    @Override
    public String toString() {
        return stavkaMenija.getNaziv();
    }
    
    
}
