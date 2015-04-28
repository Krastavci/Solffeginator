package hr.fer.solffeginator.musical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Valerio on 4/25/2015.
 */

/**
 * Klasa koja opisuje takt. Takt se opisuje listom nota.
 */
public class Takt implements Iterable<Nota> {

    private ArrayList<Nota> note;

    public Takt() {
        note = new ArrayList<Nota>();
    }

    /**
     * Metoda koja dodaje notu u takt
     * @param nota void
     */
    public void dodajNotu(Nota nota) {
        note.add(nota);
    }

    /**
     * Metoda koja vraca listu nota
     * @return ArrayList<Nota>
     */
    public ArrayList<Nota> getNote() { return new ArrayList<>(note); }

    /**
     * Metoda koja vraca iterator
     * @return Iterator<Nota>
     */
    @Override
    public Iterator<Nota> iterator() {
        return note.iterator();
    }
}
