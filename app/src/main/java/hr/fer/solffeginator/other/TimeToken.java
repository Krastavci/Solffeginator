package hr.fer.solffeginator.other;

/**
 * Created by Valerio on 4/26/2015.
 */

/**
 * Pomocna klasa koja sluzi za pohranu pogodnih vremena tapkanja. Odnosno vrijeme kad se prst stavio na ekran i vrijeme kad se prst dignuo od ekrana.
 */
public class TimeToken {

    private long timeBefore;
    private long timeAfter;

    public TimeToken(long a, long b) {
        timeBefore = a;
        timeAfter = b;
    }

    /**
     * Metoda koja vraca sumu apsolutnih vremena, odnosno, npr. vrijeme kad se trebao staviti prst i vrijeme kad je u stvarnosti stavljen prst.
     * @param a long
     * @param b long
     * @return long
     */
    public long getSumOfAbsoluteDifferences(long a, long b) {
        return Math.abs(timeBefore - a) + Math.abs(timeAfter - b);
    }

    /**
     * Metoda koja vraca pretpostavljeno vrijeme stavljanja prsta na ekran.
     * @return long
     */
    public long getTimeBefore() {
        return timeBefore;
    }

    /**
     * Metoda koja vraca pretpostavljeno vrijeme dignuca prsta s ekrana.
     * @return long
     */
    public long getTimeAfter() {
        return timeAfter;
    }

}
