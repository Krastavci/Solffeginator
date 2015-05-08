package hr.fer.solffeginator.threads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;

import hr.fer.solffeginator.R;
import hr.fer.solffeginator.TappingActivity;
import hr.fer.solffeginator.musical.Nota;
import hr.fer.solffeginator.musical.Skladba;
import hr.fer.solffeginator.musical.Takt;
import hr.fer.solffeginator.musicplayer.Metronome;
import hr.fer.solffeginator.musicplayer.MusicPlayer;

/**
 * Created by Valerio on 4/26/2015.
 */

/**
 * Dretva koja svira skladbu.
 */
public class PlayThread extends AsyncTask<Void, Void, Void>{
    private Skladba skladba;
    private MusicPlayer musicPlayer;
    private TappingActivity caller;
    private Dialog d;


    public PlayThread(Skladba skladba, MusicPlayer musicPlayer, TappingActivity caller) {
        this.skladba = skladba;
        this.musicPlayer = musicPlayer;
        this.caller = caller;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Metronome m = new Metronome(skladba.getDivident(), skladba.getTrajanje() / skladba.getDivisor());
        m.toggle();
        try {
            Thread.sleep(skladba.getTrajanje() * 2, 0);
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
                //musicPlayer.play(Sound.ORGAN, tmpNota.getNota().getValue().floatValue());
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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        d = new Dialog(caller);
        d.setTitle("Result");
        d.setCanceledOnTouchOutside(false);
        d.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                caller.finish();
            }
        });
        d.setContentView(R.layout.result_display);

        TextView tv = (TextView) d.findViewById(R.id.result);
        tv.setText("Green: " + caller.getStats().getGreen() + ",\n" +
                "Yellow: " + caller.getStats().getYellow() + ",\n" +
                "Red: " + caller.getStats().getRed());
        tv.setTextSize(30);

        Button restart =(Button) d.findViewById(R.id.restart_button);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
        caller.setCanBeTapped(false);
    }
}
