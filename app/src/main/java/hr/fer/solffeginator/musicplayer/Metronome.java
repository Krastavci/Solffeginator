package hr.fer.solffeginator.musicplayer;

/**
 * Created by Valerio on 4/26/2015.
 */
public class Metronome implements Runnable{
    private int measure;
    private long milis;
    private int volume = 20;

    private boolean isRunning = false;


    public Metronome(int measure, long milis) {
        this.measure = measure;
        this.milis = milis;
    }

    public Metronome(int measure, long milis, int volume) {
        this(measure, milis);
        this.volume = volume;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void Running(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void toggle(){
        if(isRunning){
            isRunning = false;
        } else {
            Thread t;
            t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }
    }


    @Override
    public void run() {
        isRunning = true;
        while(isRunning){
            MusicPlayer mp = new MusicPlayer();
            mp.play(Sound.METRONOME_HEAVY, volume);

            try {
                Thread.sleep(milis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i=0; i<measure-1; i++){
                mp.play(Sound.METRONOME_LIGHT, volume);

                try {
                    Thread.sleep(milis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

