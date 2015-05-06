package hr.fer.solffeginator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class vrijednost_nota extends ActionBarActivity {

    TextView zadana_vrijednost;
    TextView trenutna_vrijednost;
    ImageButton cijela_nota;
    ImageButton polovinka;
    ImageButton cetvrtinka;
    ImageButton osminka;
    ImageButton sesnaestinka;
    ImageButton restart;
    double zbroj=0;



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

        zadana_vrijednost=(TextView)findViewById(R.id.zadana_vrijednost);
        trenutna_vrijednost=(TextView)findViewById(R.id.trenutna_vrijednost);
        cijela_nota=(ImageButton)findViewById(R.id.imageButton5);
        polovinka=(ImageButton)findViewById(R.id.imageButton2);
        cetvrtinka=(ImageButton)findViewById(R.id.imageButton);
        osminka=(ImageButton)findViewById(R.id.imageButton3);
        sesnaestinka=(ImageButton)findViewById(R.id.imageButton5);
        zadana_vrijednost.setText(cijela_nota.getContentDescription());
        trenutna_vrijednost.setText("0");
        restart=(ImageButton)findViewById(R.id.imageButton6);




        }
    public void onButtonClick(View v){
        ImageButton slika_note=(ImageButton) v;
        String bText=slika_note.getContentDescription().toString();
        double value =Double.parseDouble(bText);
        zbroj+=value;
        trenutna_vrijednost.setText(Double.toString(zbroj));
        trenutna_vrijednost.setTextColor(Color.parseColor("#000000"));

        if (Double.parseDouble(zadana_vrijednost.getText().toString())==Double.parseDouble(trenutna_vrijednost.getText().toString())){
            trenutna_vrijednost.setTextColor(Color.parseColor("#00FF00"));

        }
    }

    public void onRestartClick (View v){
        zbroj=0;
        trenutna_vrijednost.setText(Double.toString(zbroj));
    }
    }




