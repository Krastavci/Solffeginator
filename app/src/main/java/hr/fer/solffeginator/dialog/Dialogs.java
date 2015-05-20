package hr.fer.solffeginator.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hr.fer.solffeginator.LevelSelectActivity;
import hr.fer.solffeginator.R;
import hr.fer.solffeginator.info.Record;
import hr.fer.solffeginator.info.SQLiteRecords;

/**
 * Created by Valerio on 5/19/2015.
 */
public class Dialogs {

    private Activity activity;
    String exercise;

    public Dialogs(Activity a, String e) {
        activity = a;
        exercise = e;
    }

    public Dialog getRecordDialog() {
        String aName = activity.getClass().getSimpleName();
        SQLiteRecords db = new SQLiteRecords(activity);

        Log.d("Aktivnost:", aName);

        Record record = null;
        if (aName.equals("TappingActivity")) {
            record = db.getBestRecord(SQLiteRecords.TABLE_TAPPING, exercise);
        }
        else if (aName.equals("PogodiMelodiju")) {
            record = db.getBestRecord(SQLiteRecords.TABLE_POGODIMELODIJU, null);
        }
        else if (aName.equals("VrijednostNota")) {
            record = db.getBestRecord(SQLiteRecords.TABLE_VRIJEDNOSTNOTA, null);
        }

        Dialog d = new Dialog(activity);
        d.setTitle("Rekord");

        d.setCanceledOnTouchOutside(true);
        d.setContentView(R.layout.record_display);


        TextView tv = (TextView) d.findViewById(R.id.result);
        if (record != null) {
            tv.setText("Vaš trenutni rekord:\n");
            tv.setText(tv.getText().toString() + "\t" + record.getPoints() + "\n");
            if (record.getLevel() != null && !record.getLevel().isEmpty()) {
                tv.setText(tv.getText().toString() + "Na vjezbi: " + record.getLevel() + "\n");
            }
            tv.setText(tv.getText().toString() + "Dana: " + record.getDate() + "\n");
            tv.setTextSize(15);
        }
        else {
            tv.setText("Nemate zabiljezen rekord za ovu vjezbu\n");
            tv.setTextSize(25);
        }

        return d;
    }

}
