package hr.fer.solffeginator.musical;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Valerio on 4/25/2015.
 */

/**
 * Klasa koja sluzi za opis skladbe. Skladba se opisuje listom taktova, vrijednoscu trajanja takta u milisekundama te mjerom takta.
 */
public class Skladba implements Iterable<Takt> {

    private ArrayList<Takt> taktovi;
    private int trajanjeTakt;
    private String mjera;

    public Skladba(String trajanjeTakt, String mjera) {
        this.trajanjeTakt = Integer.parseInt(trajanjeTakt);
        this.mjera = mjera;
        taktovi = new ArrayList<Takt>();
    }

    /**
     * Metoda koja dodaje takt u skladbu
     * @param takt Takt
     */
    public void dodajTakt(Takt takt) {
        taktovi.add(takt);
    }

    /**
     * Metoda koja vraca gornju vrijednost mjere
     * @return int
     */
    public int getDivident() {
        return Integer.parseInt(mjera.split("/")[0]);
    }

    /**
     * Metoda koja vraca donju vrijednost mjere
     * @return int
     */
    public int getDivisor() {
        return Integer.parseInt(mjera.split("/")[1]);
    }

    /**
     * Metoda koja vraca vrijednost trajanja takta
     * @return int
     */
    public int getTrajanje() {
        return trajanjeTakt;
    }

    /**
     * Metoda koja vraca broj taktova skladbe
     * @return int
     */
    public int getBrojTaktova() { return taktovi.size(); }

    /**
     * Metoda koja vraca iterator
     * @return Iterator<Takt>
     */
    @Override
    public Iterator<Takt> iterator() {
        return taktovi.iterator();
    }
}
