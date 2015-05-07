package hr.fer.solffeginator.threads;

import android.util.Log;

import java.util.Iterator;

import hr.fer.solffeginator.musical.Nota;
import hr.fer.solffeginator.musical.Skladba;
import hr.fer.solffeginator.musical.Takt;
import hr.fer.solffeginator.musicplayer.Metronome;
import hr.fer.solffeginator.musicplayer.MusicPlayer;
import hr.fer.solffeginator.musicplayer.Sound;

/**
 * Created by Valerio on 4/26/2015.
 */

/**
 * Dretva koja svira skladbu.
 */
public class PlayThread implements Runnable{
    private Skladba skladba;
    private MusicPlayer musicPlayer;

    public PlayThread(Skladba skladba, MusicPlayer musicPlayer) {
        this.skladba = skladba;
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void run() {
        Metronome m = new Metronome(skladba.getDivident(), skladba.getTrajanje()/skladba.getDivisor());
        m.toggle();
        try {
            Thread.sleep(skladba.getTrajanje()*2, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Iterator<Takt> taktovi = skladba.iterator();


        while (taktovi.hasNext()) {

            Takt tmpTakt = taktovi.next();
            Iterator<Nota> note = tmpTakt.iterator();

            while (note.hasNext()) {

                Nota tmpNota = note.next();
                Log.d("Prije:", Long.toString(System.currentTimeMillis()));
                musicPlayer.play(Sound.ORGAN, tmpNota.getNota().getValue().floatValue());
                Log.d("Poslije:", Long.toString(System.currentTimeMillis()));

                Log.d("Vrijeme pocetka svirke:", Long.toString(System.currentTimeMillis()));
                Log.d("Trajanje note:", Long.toString(tmpNota.getTrajanje()));
                try {
                    Thread.sleep(tmpNota.getTrajanje(), 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Vrijeme gotovo svirke:", Long.toString(System.currentTimeMillis()));

            }

        }
        m.toggle();

    }

}
