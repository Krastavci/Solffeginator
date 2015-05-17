package hr.fer.solffeginator.musicplayer;

/**
 * Created by Zlatan on 7.5.2015..
 */
public class LoopPlayer implements Runnable{
    private boolean playing = false;
    private MusicPlayer mp;
    private Sound playedSound;
    private Integer tone;

    public LoopPlayer(Sound s){
        mp = new MusicPlayer();
        playedSound = s;
    }

    public void start(int tone){
        this.tone = tone;
        start();
    }

    public void start(){
        playing = true;

        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public void stop(){
        if(!playing){
            throw new IllegalStateException("Tried to stop method that wasn't playing.");
        } else {
            playing = false;
            tone = null;
        }
    }

    @Override
    public void run() {
        if(tone == null){
            tone = 0;
        }
        mp.playStart(playedSound, tone);
        while(playing){
            mp.playMiddle(playedSound, tone);
        }
        mp.playEnd(playedSound, tone);
    }
}
