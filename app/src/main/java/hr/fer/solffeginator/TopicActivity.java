package hr.fer.solffeginator;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TopicActivity extends ActionBarActivity {

    private int mTextResourceId;
    static public String ARG_TEXT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Intent in = getIntent ();
        mTextResourceId = in.getIntExtra ("ARG_TEXT_ID", 0);
        if (mTextResourceId <= 0) mTextResourceId = R.string.no_help_available;
        TextView textView = (TextView) findViewById (R.id.topic_text);
        textView.setMovementMethod (LinkMovementMethod.getInstance());
        textView.setText (Html.fromHtml(getString(mTextResourceId), new ImageGetter(), null));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic, menu);
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

    private class ImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {
            int p = source.lastIndexOf(".");
            String image = source.substring(0, p);
            Context context = getApplicationContext();
            int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
            Drawable d = getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    };
}
