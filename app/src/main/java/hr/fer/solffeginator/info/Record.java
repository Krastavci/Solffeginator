package hr.fer.solffeginator.info;

/**
 * Created by Valerio on 5/19/2015.
 */
public class Record {

    private String date;
    private int points;

    public Record(String date, int points) {
        this.date = date;
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public int getPoints() {
        return points;
    }

}
