package hr.fer.solffeginator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;


public class vrijednost_nota extends ActionBarActivity {

    TextView zadana_vrijednost;
    TextView trenutna_vrijednost;
    TextView score;
    TextView rekord_view;
    ImageButton cijela_nota;
    ImageButton polovinka;
    ImageButton cetvrtinka;
    ImageButton osminka;
    ImageButton sesnaestinka;
    ImageButton restart;
    double zbroj=0;
    double[] ciljne_vrijednosti={2.5,3.125,4,5.25,1.125,0.5,0.125,1.75};
    int brojac;
    int zgoditak;
    int rekord;


    // Implementing Fisher–Yates shuffle
    static void shuffleArray(double[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            double a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrijednost_nota);
        initControls();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vrijednost_nota, menu);
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

    public void initControls(){
        shuffleArray(ciljne_vrijednosti);
        zadana_vrijednost=(TextView)findViewById(R.id.zadana_vrijednost);
        trenutna_vrijednost=(TextView)findViewById(R.id.trenutna_vrijednost);
        score=(TextView)findViewById(R.id.score);
        rekord_view=(TextView)findViewById(R.id.rekord);
        cijela_nota=(ImageButton)findViewById(R.id.imageButton5);
        polovinka=(ImageButton)findViewById(R.id.imageButton2);
        cetvrtinka=(ImageButton)findViewById(R.id.imageButton);
        osminka=(ImageButton)findViewById(R.id.imageButton3);
        sesnaestinka=(ImageButton)findViewById(R.id.imageButton4);
        zadana_vrijednost.setText(Double.toString(ciljne_vrijednosti[0]));
        trenutna_vrijednost.setText("0");
        restart=(ImageButton)findViewById(R.id.imageButton6);
        brojac=0;
        zgoditak=0;
        rekord=0;
        rekord_view.setText(Double.toString(rekord));






        }
    public void onButtonClick(View v) {
        ImageButton slika_note=(ImageButton) v;
        String bText=slika_note.getContentDescription().toString();
        double value =Double.parseDouble(bText);
        zbroj+=value;
        trenutna_vrijednost.setText(Double.toString(zbroj));
        trenutna_vrijednost.setTextColor(Color.parseColor("#000000"));


        if (Double.parseDouble(zadana_vrijednost.getText().toString())==Double.parseDouble(trenutna_vrijednost.getText().toString())){
           trenutna_vrijednost.setTextColor(Color.parseColor("#00FF00"));
            zgoditak++;
            score.setText(Integer.toString(zgoditak));
            if (zgoditak>rekord) {
                rekord = zgoditak;
                rekord_view.setText(Integer.toString(rekord));
            }

            cijela_nota.setEnabled(false);
            polovinka.setEnabled(false);
            cetvrtinka.setEnabled(false);
            osminka.setEnabled(false);
            sesnaestinka.setEnabled(false);
        }
        if (Double.parseDouble(zadana_vrijednost.getText().toString())<Double.parseDouble(trenutna_vrijednost.getText().toString())){
            trenutna_vrijednost.setTextColor(Color.parseColor("#FF0000"));
            zgoditak=0;
            score.setText(Integer.toString(zgoditak));
            cijela_nota.setEnabled(false);
            polovinka.setEnabled(false);
            cetvrtinka.setEnabled(false);
            osminka.setEnabled(false);
            sesnaestinka.setEnabled(false);
        }
    }

    public void onRestartClick (View v){
        cijela_nota.setEnabled(true);
        polovinka.setEnabled(true);
        cetvrtinka.setEnabled(true);
        osminka.setEnabled(true);
        sesnaestinka.setEnabled(true);
        zbroj=0;
        if (brojac+1==ciljne_vrijednosti.length){ brojac=0;}
        else {brojac++;}

        zadana_vrijednost.setText(Double.toString(ciljne_vrijednosti[brojac]));
        trenutna_vrijednost.setTextColor(Color.parseColor("#000000"));
        trenutna_vrijednost.setText(Double.toString(zbroj));
    }
    }




