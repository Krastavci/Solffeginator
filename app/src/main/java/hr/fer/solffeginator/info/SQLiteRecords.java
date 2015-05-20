package hr.fer.solffeginator.info;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLInput;
import java.util.LinkedList;
import java.util.List;

import hr.fer.solffeginator.PogodiMelodiju;

/**
 * Created by Valerio on 5/19/2015.
 */
public class SQLiteRecords extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 7;
    // Database Name
    private static final String DATABASE_NAME = "RecordsDB";

    // Tapping records tablename
    public static final String TABLE_TAPPING = "tapping_table";
    public static final String TABLE_POGODIMELODIJU = "pogodimelodiju_table";
    public static final String TABLE_VRIJEDNOSTNOTA = "vrijednostnota_table";


    // Tapping table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_POINTS = "points";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_DATE = "date";

    private static final String[] COLUMNS = {KEY_ID, KEY_POINTS, KEY_DATE};



    public SQLiteRecords (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQL statement to create tapping table
        String CREATE_TAPPING_TABLE = "CREATE TABLE tapping_table ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "points INTEGER, " +
                "level text, " +
                "date DATETIME DEFAULT CURRENT_TIMESTAMP );";
        sqLiteDatabase.execSQL(CREATE_TAPPING_TABLE);
        // SQL statement to create pogodi melodiju table
        String CREATE_POGODIMELODIJU_TABLE = "CREATE TABLE pogodimelodiju_table ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "points INTEGER, " +
                "date DATETIME DEFAULT CURRENT_TIMESTAMP );";
        sqLiteDatabase.execSQL(CREATE_POGODIMELODIJU_TABLE);
        // SQL statement to create vrijednost nota table
        String CREATE_VRIJEDNOSTNOTA_TABLE = "CREATE TABLE vrijednostnota_table ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "points INTEGER, " +
                "date DATETIME DEFAULT CURRENT_TIMESTAMP );";
        sqLiteDatabase.execSQL(CREATE_VRIJEDNOSTNOTA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Drop tables if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tapping_table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS pogodimelodiju_table");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS vrijednostnota_table");

        // create fresh db
        this.onCreate(sqLiteDatabase);
    }

    public void addRecord (String table, Record record) {
        Log.d("add record", record.toString());
        Log.d("to table:", table);

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_POINTS, record.getPoints());
        if (table.equals("tapping_table")) values.put(KEY_LEVEL, record.getLevel());

        // 3. insert
        db.insert(table, null, values);

        // 4. close
        db.close();

    }


    public Record getRecord (String table, int id) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(table, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selection args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Log.d ("getRecord {" + id + ")", cursor.getString(1));
        if (table.equals("tapping_table")) return new Record(cursor.getString(3), Integer.parseInt(cursor.getString(1)), cursor.getString(2));
        return new Record(cursor.getString(3), Integer.parseInt(cursor.getString(1)), null);
    }

    public List<Record> getAllRecords(String table) {
        List<Record> records = new LinkedList<Record>();

        // 1. build query
        String query = "SELECT * FROM " + table;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(3);
                int value = Integer.parseInt(cursor.getString(1));
                String level = cursor.getString(2);
                records.add(new Record(date, value, level));
            } while (cursor.moveToNext());
        }

        Log.d ("getAllRecords()", records.toString());

        return records;
    }

    public int updateTable (String table, int id, Record record) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create contentValues to add key "column"/values
        ContentValues values = new ContentValues();
        values.put("points", record.getPoints());
        if (table.equals("tapping_table")) values.put("level", record.getLevel());

        // 3. updating row
        int i = db.update(table,
                values,
                KEY_ID+" = ?",
                new String[] { String.valueOf(id)});

        // 4. close
        db.close();

        return i;
    }

    public void deleteRecord (String table, int id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. delete
        db.delete(table,
                KEY_ID+" = ?",
                new String[] { String.valueOf(id)});

        // 3. close
        db.close();

        // log
        Log.d("deleteRecord", String.valueOf(id));
    }

    public boolean isBetterThanRecord (String table, Record record) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build
        String query = null;
        if (table.equals("tapping_table")) {
            query = "SELECT * FROM " + table + " WHERE level=\"" + record.getLevel() + "\" ORDER BY points ASC";
        }
        else {
            query = "SELECT * FROM " + table + " ORDER BY points ASC";
        }

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(1)) < record.getPoints();
        }
        else {
            return true;
        }


    }

    public Record getBestRecord(String table, String exercise) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        String query;
        if (table.equals("tapping_table")) {
            query = "SELECT * FROM " + table + " WHERE level=\"" + exercise + "\" ORDER BY points DESC";
        }
        else {
            query = "SELECT * FROM " + table + " ORDER BY points DESC";
        }


        Cursor cursor = db.rawQuery(query, null);
        Log.d("Vjezba", table);

        if (cursor.moveToFirst()) {
            int record = Integer.parseInt(cursor.getString(1));
            String date = null;
            if (table.equals("tapping_table")) date = cursor.getString(3);
            else date = cursor.getString(2);
            if (table.equals("tapping_table")) {
                String level = cursor.getString(2);
                return new Record(date, record, level);
            }
            else return new Record(date, record, null);
        }

        return null;

    }

}
