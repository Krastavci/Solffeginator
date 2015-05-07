package hr.fer.solffeginator.musicplayer;

/**
 * Created by Valerio on 4/26/2015.
 */
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MusicPlayer {

    private static Map<Sound, SoundData> readSounds;
    private static final int DEFAULT_VOLUME = 40;
    private static final float DEFAULT_DURATION = 1;
    private AudioTrack at = null;
    private SoundData currentData = null;
    private static final double PRECISION = 0.005;

    public MusicPlayer(){
        if(readSounds == null){
            readSounds = new HashMap<Sound, SoundData>();
        }
    }

    public void play(Sound s){
        play(s, DEFAULT_DURATION);
    }

    public void play(Sound s, float duration){
        play(s, DEFAULT_VOLUME, duration);
    }

    public void play(Sound s, int volume){
        play(s, volume, DEFAULT_DURATION);
    }

    public void play(Sound s, int volume, float duration){
        SoundData sd = getSoundData(s);

        Thread t = new Thread(new PlaySound(sd, volume, duration));
        t.setPriority(Thread.MAX_PRIORITY);
        t.setDaemon(true);
        t.start();
    }

    void playStart(Sound s){
        SoundData sd = getSoundData(s);
        at = audioTrackInit(sd);
        at.play();
        if(sd.getResolution() == 16){
            try {
                short[] realData = sd.getPartialShortData(0, (int) (sd.getDataLength() * PRECISION));
                at.write(realData, 0, realData.length);
                //at.release();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        } else {
            try {
                byte[] realData = sd.getPartialByteData(0, (int) (sd.getDataLength() * PRECISION));
                at.write(realData, 0, realData.length);
                //at.release();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }

    }

    void playMiddle(Sound s){
        SoundData sd = getSoundData(s);
        AudioTrack at = audioTrackInit(sd);
        at.play();
        if(sd.getResolution() == 16){
            try {
                short[] realData = sd.getPartialShortData(sd.getDataLength()/2, (int) (sd.getDataLength() * PRECISION));
                at.write(realData, 0, realData.length);
               // at.release();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        } else {
            try {
                byte[] realData = sd.getPartialByteData(sd.getDataLength()/2, (int) (sd.getDataLength() * PRECISION));
                at.write(realData, 0, realData.length);
               // at.release();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    void playEnd(Sound s){
        SoundData sd = getSoundData(s);
        AudioTrack at = audioTrackInit(sd);
        at.play();
        if(sd.getResolution() == 16){
            try {
                short[] realData = sd.getPartialShortData(sd.getDataLength()-(int)(sd.getDataLength()*PRECISION*3-1)
                        , (int) (sd.getDataLength() * PRECISION*3)-1);
                at.write(realData, 0, realData.length);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        } else {
            try {
                byte[] realData = sd.getPartialByteData(sd.getDataLength()-(int)(sd.getDataLength()*PRECISION)
                        , (int) (sd.getDataLength() * PRECISION)-1);
                at.write(realData, 0, realData.length);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private SoundData getSoundData(Sound s){
        SoundData sd = null;
        if(readSounds.containsKey(s)){
            sd = readSounds.get(s);
        } else {
            sd = new SoundData(Sound.soundName(s));
            readSounds.put(s, sd);
        }
        return sd;
    }

    private static class PlaySound implements Runnable {
        SoundData data;
        int volume = DEFAULT_VOLUME;
        float duration = DEFAULT_DURATION;

        public PlaySound(SoundData data) {
            super();
            this.data = data;
        }

        public PlaySound(SoundData data, int volume, float duration) {
            this(data);
            this.volume = volume;
            this.duration = duration;
        }


        @SuppressLint("NewApi")
        @Override
        public void run() {

            int buffsize = AudioTrack.getMinBufferSize(data.getSamplingRate(),
                    data.getChannel(),
                    data.getEncoding());

            AudioTrack at = new AudioTrack(
                    AudioManager.STREAM_MUSIC, data.getSamplingRate(),
                    data.getChannel(),
                    data.getEncoding(), buffsize,
                    AudioTrack.MODE_STREAM);

            at.play();

//			Thread t = new Thread(new LinearSuppressor(audioTrack, data.getDuration(), volume));
//			t.setDaemon(true);
//			t.start();

            float maxVolume = AudioTrack.getMaxVolume() * volume / (float) 100;

            at.setVolume(maxVolume);

            for (int i = 0, step = data.getDataLength() / 5; i < data.getDataLength(); i += step) {
                boolean flag = false;
                try {
                    short[] original = data.getShortData();
                    short[] real = new short[(int) (data.getDataLength() * duration)];
                    for (int j = 0; j < real.length; j++) {
                        if (j * (1 / duration) < original.length) {
                            real[j] = original[(int) (j * (1 / duration))];
                        }
                    }

                    at.write(real, (int) (i * duration), (int) (step * duration));
                } catch (Exception supposedToHappen) {
                    //supposedToHappen.printStackTrace();
                    flag = true;
                }

                if (flag) {
                    try {
                        at.write(data.getByteData(), i, i + step);
                    } catch (Exception supposedToHappen) {
                    }
                }

                at.setVolume(maxVolume - (maxVolume / data.getDataLength() * duration * 3) * i);
            }

            at.stop();
            at.release();
        }

    }

    public void render(Sound...sounds) {
        for(Sound s: sounds){
            if(!readSounds.containsKey(s)){
                readSounds.put(s, new SoundData(Sound.soundName(s)));
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AudioTrack audioTrackInit(SoundData sd){
        if(at == null || !sd.equals(currentData)) {
            int buffsize = AudioTrack.getMinBufferSize(sd.getSamplingRate(),
                    sd.getChannel(),
                    sd.getEncoding());

            at = new AudioTrack(
                    AudioManager.STREAM_MUSIC, sd.getSamplingRate(),
                    sd.getChannel(),
                    sd.getEncoding(), buffsize,
                    AudioTrack.MODE_STREAM);
            currentData = sd;
            at.setVolume(0.4f);
        }
        return at;
    }
}
