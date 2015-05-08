package hr.fer.solffeginator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


public class LevelSelectActivity extends ActionBarActivity {

    public static String exerciseName;
    public static Resources res = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        Drawable background = findViewById(R.id.select_level).getBackground();
        background.setAlpha(150);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        res = getResources();
        AssetManager am = res.getAssets();
        String [] files = new String[0];
        try {
            files = am.list("tappingExercises");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ListView selectArea = (ListView) findViewById(R.id.excercise_areas);
        ArrayList<String> list = new ArrayList<>();
        final ArrayList<String> realNames = new ArrayList<>();
        int counter = 1;
        for(String f: files){
            if(f.endsWith(".txt")) {
                list.add("Vježba " + counter);
                realNames.add(f);
                counter++;
            }
        }
        selectArea.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list));
        selectArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LevelSelectActivity.this, TappingActivity.class);
                exerciseName ="tappingExercises/" + realNames.get(position);
                intent.putExtra("exerciseName", exerciseName);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_select, menu);
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
}
