package hr.fer.solffeginator.info;

/**
 * Created by Valerio on 5/19/2015.
 */
public class Record {

    private String date;
    private int points;
    private String level;

    public Record(String date, int points, String level) {
        this.date = date;
        this.points = points;
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public int getPoints() {
        return points;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return String.valueOf(points) + "\t" + level + "\t" + date + "\n";
    }

}
