package hr.fer.solffeginator.parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import hr.fer.solffeginator.exceptions.ParserException;
import hr.fer.solffeginator.info.Info;
import hr.fer.solffeginator.musical.*;

/**
 * Created by Valerio on 4/25/2015.
 */

/**
 * Klasa koja parsira text vjezbe. Radi na nacin da prije procita prve dvije linije koje predstavljaju mjeru i trajanje, a zatim cita liniju po liniju
 * te tako slaze skladbu.
 */
public class Parser {

    private Skladba skladba;

    public Parser (Context context, String exercise) {
        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(exercise);
            Log.d("Citanje", "Citam");

        } catch(IOException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String trajanje = null;
        String mjera = null;

        try {
            trajanje = br.readLine();
            mjera = br.readLine();
            Log.d("Ucitavanje", "Uspjeh");
        } catch (IOException e) {
            e.printStackTrace();
        }

        skladba = new Skladba(trajanje, mjera);

        Double mjeraNumeric = 1.0 * skladba.getDivident()/skladba.getDivisor();
        int trajanjeTemp = 0;
        Double mjeraTemp = 0.;
        int trajanjeNumeric = skladba.getTrajanje();

        String line = "";
        Takt takt = new Takt();
        try {
            while((line=br.readLine())!=null) {
                Info infoNota = Info.getInfo(line);
                Log.d("Parsirana nota:", infoNota.getName());
                takt.dodajNotu(new Nota(infoNota, (long) (infoNota.getValue()/mjeraNumeric * trajanjeNumeric)));
                mjeraTemp += infoNota.getValue();
                Log.d("Trenutna mjera", Double.toString(mjeraTemp));
                if (mjeraTemp.compareTo(mjeraNumeric) == 0) {
                    Log.d("Dodajem takt", "Idemo");
                    skladba.dodajTakt(takt);
                    takt = new Takt();
                    mjeraTemp = 0.;
                }
                else if (mjeraTemp.compareTo(mjeraNumeric) > 0) {
                    throw new ParserException("Pogreska u parsiranju.");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException p) {
            p.printStackTrace();
        }

    }

    /**
     * Metoda koja vraca skladbu
     * @return Skladba
     */
    public Skladba getSkladba() {
        return skladba;
    }

}
