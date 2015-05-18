package hr.fer.solffeginator.threads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;

import hr.fer.solffeginator.PogodiMelodiju;
import hr.fer.solffeginator.R;
import hr.fer.solffeginator.TappingActivity;
import hr.fer.solffeginator.musical.Nota;
import hr.fer.solffeginator.musical.Skladba;
import hr.fer.solffeginator.musical.Takt;
import hr.fer.solffeginator.musicplayer.LoopPlayer;
import hr.fer.solffeginator.musicplayer.Metronome;
import hr.fer.solffeginator.musicplayer.MusicPlayer;

/**
 * Created by Valerio on 4/26/2015.
 */

/**
 * Dretva koja svira skladbu.
 */
public class GuessThread extends AsyncTask<Void, Void, Void>{
    private Skladba skladba;
    private LoopPlayer loopPlayer;


    public GuessThread(Skladba skladba, LoopPlayer loopPlayer) {
        this.skladba = skladba;
        this.loopPlayer = loopPlayer;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Iterator<Takt> taktovi = skladba.iterator();


        while (taktovi.hasNext()) {

            Takt tmpTakt = taktovi.next();
            Iterator<Nota> note = tmpTakt.iterator();

            while (note.hasNext()) {

                Nota tmpNota = note.next();
                Log.d("Prije:", Long.toString(System.currentTimeMillis()));
                //musicPlayer.play(Sound.ORGAN, tmpNota.getNota().getValue().floatValue());
                Log.d("Poslije:", Long.toString(System.currentTimeMillis()));
                loopPlayer.start(tmpNota.getLjestvica().getValue() + (tmpNota.getVisina())*13);
                Log.d("Vrijeme pocetka svirke:", Long.toString(System.currentTimeMillis()));
                Log.d("Trajanje note:", Long.toString(tmpNota.getTrajanje()));
                try {
                    Thread.sleep(tmpNota.getTrajanje(), 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loopPlayer.stop();
                try {
                    Thread.sleep(10,0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        return;
    }
}
