package hr.fer.solffeginator.musicplayer;

/**
 * Created by Valerio on 4/26/2015.
 */
public class WavHeader {
    private byte [] data;

    public WavHeader(byte[] data) {
        super();
        this.data = data;
    }

    private int parseInt(int start, int end){
        int returnValue = 0;
        for(int i = end; i >= start; i--){

            if(data[i] >= 0){
                returnValue += data[i];
            } else {
                returnValue += 256 + data[i];
            }

            if(i != start){
                returnValue *= Math.pow(2, 8);
            }
        }

        return returnValue;
    }

    public int getSamplingRate() {
        return parseInt(24, 27);
    }

    public int getResolution() {
        return parseInt(34, 35);
    }

    public int getChannel() {
        return parseInt(22, 23);
    }

    public int getDuration() {
        return parseInt(40, 43)/parseInt(28, 31);
    }
}