package hr.fer.solffeginator.info;

/**
 * Created by Valerio on 4/26/2015.
 */

/**
 * Klasa koja sluzi za pracenje statistike
 */
public class Statistics {

    private int green;
    private int yellow;
    private int red;

    public Statistics() {
        green = 0;
        yellow = 0;
        red = 0;
    }

    /**
     * Metoda koja uvecava zastavicu zelenog prolaza za 1
     */
    public void hitGreen() {
        green++;
    }
    /**
     * Metoda koja uvecava zastavicu zutog prolaza za 1
     */
    public void hitYellow() {
        yellow++;
    }
    /**
     * Metoda koja uvecava zastavicu crvenog prolaza za 1
     */
    public void hitRed() {
        red++;
    }

    /**
     * Metoda koja vraca vrijednost zelenih prolaza
     * @return int
     */
    public int getGreen() {
        return green;
    }

    /**
     * Metoda koja vraca vrijednost zutih prolaza
     * @return int
     */
    public int getYellow() {
        return yellow;
    }

    /**
     * Metoda koja vraca vrijednost crvenih prolaza
     * @return int
     */
    public int getRed() {
        return red;
    }
}
