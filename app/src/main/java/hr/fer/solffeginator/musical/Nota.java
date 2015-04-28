package hr.fer.solffeginator.musical;

import hr.fer.solffeginator.info.Info;

/**
 * Created by Valerio on 4/25/2015.
 */

/**
 * Klasa koja sluzi za opis note. Opisuje je Enumom note te njenim realnim trajanjem.
 */
public class Nota {

    private Info nota;
    private long trajanje;

    public Nota(Info nota, long trajanje) {
        this.nota = nota;
        this.trajanje = trajanje;
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
}
