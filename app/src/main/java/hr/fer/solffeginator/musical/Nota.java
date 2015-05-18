package hr.fer.solffeginator.musical;

import hr.fer.solffeginator.info.Info;
import hr.fer.solffeginator.info.Ljestvica;

/**
 * Created by Valerio on 4/25/2015.
 */

/**
 * Klasa koja sluzi za opis note. Opisuje je Enumom note te njenim realnim trajanjem.
 */
public class Nota {

    private Info nota;
    private Ljestvica ljestvica;
    private int visina;
    private long trajanje;

    public Nota(Info nota, long trajanje) {
        this.nota = nota;
        this.trajanje = trajanje;
    }

    public Nota(Info nota, long trajanje, Ljestvica ljestvica, int visina) {
        this.nota = nota;
        this.trajanje = trajanje;
        this.ljestvica = ljestvica;
        this.visina = visina;
    }

    /**
     * Getter koji vraca Enum vrijednost note.
     * @return Info
     */
    public Info getNota() {
        return nota;
    }

    /**
     * Getter koji vraca trajanje note
     * @return long
     */
    public long getTrajanje() { return trajanje; }

    public Ljestvica getLjestvica() {
        return ljestvica;
    }

    public int getVisina() {
        return visina;
    }
}
