package hr.fer.solffeginator;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import hr.fer.solffeginator.R;
import hr.fer.solffeginator.dialog.Dialogs;
import hr.fer.solffeginator.info.Record;
import hr.fer.solffeginator.info.SQLiteRecords;
import hr.fer.solffeginator.musical.Skladba;
import hr.fer.solffeginator.musicplayer.LoopPlayer;
import hr.fer.solffeginator.musicplayer.Sound;
import hr.fer.solffeginator.parser.Parser;
import hr.fer.solffeginator.threads.GuessThread;

public class PogodiMelodiju extends ActionBarActivity {

    private ImageButton odg1;
    private ImageButton odg2;
    private ImageButton odg3;
    private ImageButton odg4;
    private ImageButton gumb;
    private int TOCAN;
    private String correctPicName;

    private int trenutniRezultat;
    private int rekord;

    // svi IDovi slika (vjezba)
    private ArrayList<Integer> imageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pogodi_melodiju);

        imageIds = new ArrayList<Integer>();
        imageIds.add(R.drawable.vj1);
        imageIds.add(R.drawable.vj2);
        imageIds.add(R.drawable.vj3);
        imageIds.add(R.drawable.vj4);
        imageIds.add(R.drawable.vj5);
        imageIds.add(R.drawable.vj6);
        imageIds.add(R.drawable.vj7);
        imageIds.add(R.drawable.vj8);
        imageIds.add(R.drawable.vj9);
        imageIds.add(R.drawable.vj10);
        imageIds.add(R.drawable.vj11);
        imageIds.add(R.drawable.vj12);

        trenutniRezultat = 0;

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // prozor ce biti landscape

        addListenerOnButtons();
    }

    public void addListenerOnButtons() {

        // gumb za sviranje
        gumb = (ImageButton) findViewById(R.id.gumb);

        ImageButton odg;
        Random generator = new Random();

        // svi vec koristeni IDovi
        ArrayList<Integer> usedIds = new ArrayList<Integer>();

        for(int i=1;i<=4;i++) {
            int randomImageId;
            do {
                // izaberi random ID slike
                randomImageId = imageIds.get(generator.nextInt(imageIds.size()));
            } while (usedIds.contains(randomImageId));

            // sacuvaj ID da se ne bi pojavila ista slika
            usedIds.add(randomImageId);
        }

        // izaberemo tocan odgovor
        int indexTocan = generator.nextInt(usedIds.size()) + 1;

        //  *******************************************************************************************
        //  *************** TU DOHVATI .txt ZA SVIRANJE ODREĐENE VJEZBE!!!!!!!!! **********************
        //  *******************************************************************************************


        for (int i = 1; i <= 4; i++) {
            int randomImageId = usedIds.get(i-1);
            switch (i) {
                case (1):
                    if (indexTocan == 1){
                        TOCAN = R.id.odg1;
                    }
                    odg = (ImageButton) findViewById(R.id.odg1);
                    odg.setImageResource(randomImageId);
                    break;
                case (2):
                    if (indexTocan == 2){
                        TOCAN = R.id.odg2;
                    }
                    odg = (ImageButton) findViewById(R.id.odg2);
                    odg.setImageResource(randomImageId);
                    break;
                case (3):
                    if (indexTocan == 3){
                        TOCAN = R.id.odg3;
                    }
                    odg = (ImageButton) findViewById(R.id.odg3);
                    odg.setImageResource(randomImageId);
                    break;
                case (4):
                    if (indexTocan == 4){
                        TOCAN = R.id.odg4;
                    }
                    odg = (ImageButton) findViewById(R.id.odg4);
                    odg.setImageResource(randomImageId);
                    break;
                default:
                    break;
            }
        }

        correctPicName = getResources().getResourceEntryName(usedIds.get(indexTocan-1));
        Log.d("Ime tocne vjezbe", correctPicName);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pogodi_melodiju, menu);
        return true;
    }

    public void clickImage(View view){
        if(view.getId()==TOCAN){
            Toast.makeText(PogodiMelodiju.this, "TOČNO", Toast.LENGTH_SHORT).show();
            trenutniRezultat++;
            addListenerOnButtons();
        }
        else{
            Toast.makeText(PogodiMelodiju.this, "NETOČNO", Toast.LENGTH_SHORT).show();
            SQLiteRecords db = new SQLiteRecords(this);
            Record record = new Record(null, trenutniRezultat, null);
            if (db.isBetterThanRecord(SQLiteRecords.TABLE_POGODIMELODIJU, record)) {
                db.addRecord(SQLiteRecords.TABLE_POGODIMELODIJU, record);
                Log.d("Rekord", "Dodajem rekord");
            }
            trenutniRezultat = 0;
        }
    }

    public void clickPlay(View view){
        String fPath = "guessMelody/"+correctPicName+".txt";
        Log.d("Citam::", fPath);
        Parser parser = new Parser(getApplicationContext(), fPath);
        Skladba skladba = parser.getSkladba();
        LoopPlayer lp = new LoopPlayer(Sound.ORGAN);
        GuessThread gt = new GuessThread(skladba, lp);
        gt.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.showRecord) {
            Dialogs d = new Dialogs(this, null);
            if (d != null) d.getRecordDialog().show();
        }

        return super.onOptionsItemSelected(item);
    }
}
