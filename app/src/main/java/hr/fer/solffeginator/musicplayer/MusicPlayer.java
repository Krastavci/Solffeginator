package hr.fer.solffeginator.musicplayer;

/**
 * Created by Valerio on 4/26/2015.
 */
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.util.HashMap;
import java.util.Map;


public class MusicPlayer {

    private static Map<Sound, SoundData> readSounds;
    private static final int DEFAULT_VOLUME = 40;
    private static final float DEFAULT_DURATION = 1;

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
        SoundData sd = null;
        if(readSounds.containsKey(s)){
            sd = readSounds.get(s);
        } else {
            sd = new SoundData(Sound.soundName(s));
            readSounds.put(s, sd);
        }

        Thread t = new Thread(new PlaySound(sd, volume, duration));
        t.setPriority(Thread.MAX_PRIORITY);
        t.setDaemon(true);
        t.start();

    }

    public void togglePlay(Sound s){

    }

    private static class PlaySound implements Runnable{
        SoundData data;
        int volume = DEFAULT_VOLUME;
        float duration = DEFAULT_DURATION;

        public PlaySound(SoundData data) {
            super();
            this.data = data;
        }

        public PlaySound(SoundData data, int volume, float duration){
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

            AudioTrack audioTrack = new AudioTrack(
                    AudioManager.STREAM_MUSIC, data.getSamplingRate(),
                    data.getChannel(),
                    data.getEncoding(), buffsize,
                    AudioTrack.MODE_STREAM);

            audioTrack.play();

//			Thread t = new Thread(new LinearSuppressor(audioTrack, data.getDuration(), volume));
//			t.setDaemon(true);
//			t.start();

            float maxVolume = AudioTrack.getMaxVolume() * volume/(float)100;

            audioTrack.setVolume(maxVolume);

            for(int i = 0, step = data.getDataLength()/5; i < data.getDataLength(); i += step){
                boolean flag = false;
                try{
                    short[] original = data.getShortData();
                    short[] real = new short[(int) (data.getDataLength()*duration)];
                    for(int j=0; j < real.length; j++){
                        if(j*(1/duration) < original.length){
                            real[j] = original[(int) (j*(1/duration))];
                        }
                    }

                    audioTrack.write(real, (int)(i*duration), (int)(step*duration));
                } catch(Exception supposedToHappen){
                    //supposedToHappen.printStackTrace();
                    flag = true;
                }

                if(flag){
                    try {
                        audioTrack.write(data.getByteData(), i, i + step);
                    } catch(Exception supposedToHappen) {
                    }
                }

                audioTrack.setVolume(maxVolume - (maxVolume/data.getDataLength()*duration*3) * i);
            }

            audioTrack.stop();
            audioTrack.release();
        }

    }

    public void render(Sound...sounds) {
        for(Sound s: sounds){
            if(!readSounds.containsKey(s)){
                readSounds.put(s, new SoundData(Sound.soundName(s)));
            }
        }
    }

}
