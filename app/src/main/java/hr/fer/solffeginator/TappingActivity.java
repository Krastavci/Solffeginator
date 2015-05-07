package hr.fer.solffeginator;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import hr.fer.solffeginator.info.Statistics;
import hr.fer.solffeginator.musical.Nota;
import hr.fer.solffeginator.musical.Takt;
import hr.fer.solffeginator.musicplayer.LoopPlayer;
import hr.fer.solffeginator.musicplayer.MusicPlayer;
import hr.fer.solffeginator.musicplayer.Sound;
import hr.fer.solffeginator.other.TimeToken;
import hr.fer.solffeginator.parser.Parser;
import hr.fer.solffeginator.threads.PlayThread;
import hr.fer.solffeginator.views.CustomView;


public class TappingActivity extends ActionBarActivity {

    private Parser parser;
    private long time;
    private Queue<TimeToken> timeList;
    private long lastTime;
    private Statistics stats;
    private boolean mBooleanIsPressed;
    private long clickStartTime;
    private static final long EXCELENT = 2000;
    private static final long GOOD = 4000;
    private LoopPlayer lp;

    RelativeLayout mainLayout; // linija
    private boolean canBeTapped;


    /**
     * Metoda koja se pokrece prilikom pokretanja dretve. Inicijalizira izgled ekrana, pokrene parser te postavlja zastavicu tapkanja na false.
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapping2);

        timeList = new LinkedList<TimeToken>();
        canBeTapped = false;
        parser = new Parser(getApplicationContext(), getIntent().getStringExtra("exerciseName"));
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // prozor ce biti landscape

        // visina + sirina ekrana
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        // prozirna pozadina
        Drawable loginActivityBackground = findViewById(R.id.bg_drums).getBackground();
        loginActivityBackground.setAlpha(150);

        mainLayout = (RelativeLayout) findViewById(R.id.bg_drums);
        CustomView customview = new CustomView(this, height, width, parser.getSkladba());
        mainLayout.addView(customview);

    }

    protected final static int getResourceID (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tapping, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }


    /**
     * Metoda koja odredjuje sto se zbiva prilikom tapkanja. Ukratko, uzme se prva vrijednost s reda te provjerava vrijednosti.
     * @param event MotionEvent
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!timeList.isEmpty() && canBeTapped) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mBooleanIsPressed = true;
                clickStartTime = System.currentTimeMillis();
                lp.start();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {

                if (mBooleanIsPressed) {
                    mBooleanIsPressed = false;
                    lp.stop();

                    TimeToken tempTimeToken = timeList.poll();

                    long currentTime = System.currentTimeMillis();
                    long mistakeDifference = tempTimeToken.getSumOfAbsoluteDifferences(clickStartTime, currentTime);

                    Log.d("Calc time start:", Long.toString(tempTimeToken.getTimeBefore()));
                    Log.d("Calc time finish:", Long.toString(tempTimeToken.getTimeAfter()));
                    Log.d("Real time start:", Long.toString(clickStartTime));
                    Log.d("Real time finish", Long.toString(currentTime));

                    if (mistakeDifference < EXCELENT) {
                        stats.hitGreen();
                    } else if (mistakeDifference < GOOD) {
                        stats.hitYellow();
                    } else {
                        stats.hitRed();
                    }
                }
            }
        }
        else {
            Dialog d = new Dialog(this);
            d.setTitle("Result");
            d.setContentView(R.layout.result_display);
            TextView tv = (TextView)d.findViewById(R.id.result);
            tv.setText("Green: " + stats.getGreen() + ", Yellow: " + stats.getYellow() + ", Red: " + stats.getRed());
            tv.setTextSize(30);
            d.show();
            canBeTapped = false;
        }


        return false;
    }

    /**
     * Metoda koja definira sto se dogadja prilikom klika na tipku start.
     * @param view
     */
    public void onClickStart(View view) {
        lp = new LoopPlayer(Sound.ORGAN);
        stats = new Statistics();
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.render(Sound.ORGAN, Sound.METRONOME_LIGHT, Sound.METRONOME_HEAVY);
        PlayThread playThread = new PlayThread(parser.getSkladba(), musicPlayer);
        new Thread(playThread).start();
        time = System.currentTimeMillis();
        timeList = createTimeList();
        canBeTapped = true;

        Toast t = Toast.makeText(getApplicationContext(), "READY", Toast.LENGTH_SHORT);
        t.show();
        try {
            Thread.sleep(parser.getSkladba().getTrajanje());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t = Toast.makeText(getApplicationContext(), "SET", Toast.LENGTH_SHORT);
        t.show();
        /*try {
            Thread.sleep(parser.getSkladba().getTrajanje());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t = Toast.makeText(getApplicationContext(), "GO", Toast.LENGTH_SHORT);
        t.show();*/
    }

    /**
     * Metoda koja napravi red vremena.
     * @return
     */
    public LinkedList<TimeToken> createTimeList() {
        LinkedList<TimeToken> times = new LinkedList<TimeToken>();
        long duration = time + 3000;
        Iterator<Takt> taktovi = parser.getSkladba().iterator();
        while(taktovi.hasNext()) {
            Takt tmpTakt = taktovi.next();
            Iterator<Nota> note = tmpTakt.iterator();
            while(note.hasNext()) {
                Nota tmpNota = note.next();
                times.add(new TimeToken(duration, duration+tmpNota.getTrajanje()));
                duration += tmpNota.getTrajanje();
            }
        }
        return times;
    }

    /**
     * Metoda kojom se dobije vrijeme zanjeg elementa
     * @param elements Queue<TimeToken>
     * @return long
     */
    public long getLastElement(Queue<TimeToken> elements) {
        long temp = 0;
        Iterator<TimeToken> it = elements.iterator();
        while(it.hasNext()) {
            temp = it.next().getTimeAfter();
        }
        return temp;
    }

}
