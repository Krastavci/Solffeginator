package hr.fer.solffeginator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class HelpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
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

    public void onClickHelp(View v) {
        int id = v.getId ();
        int textId = -1;
        switch (id) {
            case R.id.help_button1 :
                textId = R.string.topic_section1;
                break;
            case R.id.help_button2 :
                textId = R.string.topic_section2;
                break;
            case R.id.help_button3 :
                textId = R.string.topic_section3;
                break;
            case R.id.help_button4 :
                textId = R.string.topic_section4;
                break;
            case R.id.help_button5 :
                textId = R.string.topic_section5;
                break;
            default:
                break;
        }
        if (textId >= 0) startInfoActivity (textId);
        else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Detailed Help for that topic is not available.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void startInfoActivity (int textId) {
        if (textId >= 0) {
            Intent intent = (new Intent(this, TopicActivity.class));
            intent.putExtra ("ARG_TEXT_ID", textId);
            startActivity (intent);
        }
    }
}
