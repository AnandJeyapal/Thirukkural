package com.impiger.thirukkural.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Favorite;
import com.impiger.thirukkural.model.KuralFavorite;
import com.impiger.thirukkural.model.Thirukkural;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by anand on 17/11/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TABLE_KURALS = "kurals";
    private static final String TABLE_ADHIGARAMS = "Adhigarams";
    private static final String TABLE_FAVORITES = "Favorites";
    private static final String TABLE_KURAL_FAVORITES = "Kural_Favorites";
    private static final String PART_NAME = "PartName";
    private static final String ADHIGARAM_NUMBER = "Number";

    private static final String TAG = DBHelper.class.getSimpleName();
    private static String DB_PATH = "/data/data/com.impiger.thirukkural/databases/";
    private static String DB_NAME = "kurals.db";
    private SQLiteDatabase myDataBase;

    private Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        myContext = context;
        try {
            createDataBase();
        } catch (IOException ioe) {
            Log.d(TAG, "Error while opening");
        }

        try {
            openDataBase();
        } catch (SQLException sqle) {
            Log.d(TAG, "Error while opening 2");
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        if (!checkDataBase()) {
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory()) {
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

            }

        } catch (SQLiteException e) {

            Log.e("XXX", "Error while reading DB" + e.getMessage());

        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Thirukkural> getAllKurals() {
        ArrayList<Thirukkural> thirukkurals = new ArrayList<Thirukkural>();

        String query = "SELECT  * FROM " + TABLE_KURALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Thirukkural thirukkural = null;
        if (cursor.moveToFirst()) {
            do {
                thirukkural = new Thirukkural();
                thirukkural.setId(Integer.parseInt(cursor.getString(0)));
                thirukkural.setKural(cursor.getString(1));
                thirukkural.setFirstExplanation(cursor.getString(2));
                thirukkural.setSecondExplanation(cursor.getString(3));
                thirukkural.setThirdExplanation(cursor.getString(4));

                thirukkurals.add(thirukkural);
            } while (cursor.moveToNext());
        }

        Log.d("getAllKurals()", thirukkurals.toString());

        return thirukkurals;
    }

    public ArrayList<Thirukkural> getKurals(int startNumber, int endNumber) {
        ArrayList<Thirukkural> thirukkurals = new ArrayList<Thirukkural>();

        String query = "SELECT  * FROM " + TABLE_KURALS + " WHERE id >= " + startNumber + " AND " +
                "id <= " + endNumber;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Thirukkural thirukkural = null;
        if (cursor.moveToFirst()) {
            do {
                thirukkural = new Thirukkural();
                thirukkural.setId(Integer.parseInt(cursor.getString(0)));
                thirukkural.setKural(cursor.getString(1));
                thirukkural.setFirstExplanation(cursor.getString(2));
                thirukkural.setSecondExplanation(cursor.getString(3));
                thirukkural.setThirdExplanation(cursor.getString(4));

                thirukkurals.add(thirukkural);
            } while (cursor.moveToNext());
        }

        Log.d("getAllKurals()", thirukkurals.toString());

        return thirukkurals;
    }

    public Adhigaram getAdhigaramsByNumber(int number) {

        String query = "SELECT  * FROM " + TABLE_ADHIGARAMS + " WHERE " + ADHIGARAM_NUMBER + " = " + number ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Adhigaram adhigaram = null;
        if (cursor.moveToFirst()) {
                adhigaram = new Adhigaram();
                adhigaram.setPartName(cursor.getString(0));
                adhigaram.setIyalName(cursor.getString(1));
                adhigaram.setAdhigaramName(cursor.getString(2));
                adhigaram.setAdhigaramNumber(cursor.getInt(3));
                adhigaram.setStartKural(cursor.getInt(4));
                adhigaram.setEndKural(cursor.getInt(5));
                return adhigaram;
        }
        return adhigaram;
    }

    public ArrayList<Adhigaram> getAdhigaramsByPart(String partName) {
        ArrayList<Adhigaram> adhigarams = new ArrayList<Adhigaram>();

        String query = "SELECT  * FROM " + TABLE_ADHIGARAMS + " WHERE " + PART_NAME + " = '" + partName+"'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Adhigaram adhigaram = null;
        if (cursor.moveToFirst()) {
            do {
                adhigaram = new Adhigaram();
                adhigaram.setPartName(cursor.getString(0));
                adhigaram.setIyalName(cursor.getString(1));
                adhigaram.setAdhigaramName(cursor.getString(2));
                adhigaram.setAdhigaramNumber(cursor.getInt(3));
                adhigaram.setStartKural(cursor.getInt(4));
                adhigaram.setEndKural(cursor.getInt(5));
                adhigarams.add(adhigaram);
            } while (cursor.moveToNext());
        }

        Log.d("getAllAdhigarams()", adhigarams.toString());

        return adhigarams;
    }

    public ArrayList<Adhigaram> getAllAdhigarams() {
        ArrayList<Adhigaram> adhigarams = new ArrayList<Adhigaram>();

        String query = "SELECT  * FROM " + TABLE_ADHIGARAMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Adhigaram adhigaram = null;
        if (cursor.moveToFirst()) {
            do {
                adhigaram = new Adhigaram();
                adhigaram.setPartName(cursor.getString(0));
                adhigaram.setIyalName(cursor.getString(1));
                adhigaram.setAdhigaramName(cursor.getString(2));
                adhigaram.setAdhigaramNumber(cursor.getInt(3));
                adhigaram.setStartKural(cursor.getInt(4));
                adhigaram.setEndKural(cursor.getInt(5));
                adhigarams.add(adhigaram);
            } while (cursor.moveToNext());
        }

        Log.d("getAllAdhigarams()", adhigarams.toString());

        return adhigarams;
    }

    public void insertThirukkuralRecord(ContentValues values) {
        myDataBase.insert(TABLE_KURALS, null, values);
    }

    public void insertAdhigaramRecord(ContentValues values) {
        myDataBase.insert(TABLE_ADHIGARAMS, null, values);
    }

    public void insertFavoriteRecord(ContentValues values) {
        myDataBase.insert(TABLE_FAVORITES, null, values);
    }

    public boolean isFavorite(String adhigaramIdx) {
        String query = "select 1 from Favorites where Number = " + adhigaramIdx;
        Cursor cursor = myDataBase.rawQuery(query, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean deleteFavorite(String adhigaramIdx) {
        return myDataBase.delete(TABLE_FAVORITES,  " Number =" + adhigaramIdx, null) > 0;
    }

    public ArrayList<Favorite> getAllFavorites() {
        ArrayList<Favorite> favorites = new ArrayList<Favorite>();

        String query = "SELECT  * FROM " + TABLE_FAVORITES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Favorite favorite = null;
        if (cursor.moveToFirst()) {
            do {
                favorite = new Favorite(cursor.getInt(1), cursor.getString(0));
                favorites.add(favorite);
            } while (cursor.moveToNext());
        }

        Log.d("getAllFavorites()", favorites.toString());

        return favorites;
    }

    public void insertKuralFavoriteRecord(ContentValues values) {
        myDataBase.insert(TABLE_KURAL_FAVORITES, null, values);
    }

    public boolean isKuralFavorite(String adhigaramIdx) {
        String query = "select 1 from Kural_Favorites where Number = " + adhigaramIdx;
        Cursor cursor = myDataBase.rawQuery(query, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean deleteKuralFavorite(String adhigaramIdx) {
        return myDataBase.delete(TABLE_KURAL_FAVORITES,  " Number =" + adhigaramIdx, null) > 0;
    }

    public ArrayList<Thirukkural> getAllKuralFavorites() {
        ArrayList<Thirukkural> thirukkurals = new ArrayList<Thirukkural>();
        String query = "SELECT  kurals.id, kurals.kural, kurals.first_exp, kurals.second_exp, kurals.third_exp FROM " + TABLE_KURALS + "," + TABLE_KURAL_FAVORITES + " " + " WHERE kurals.id = " + "Kural_Favorites.Number+1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Thirukkural thirukkural = null;
        if (cursor.moveToFirst()) {
            do {
                thirukkural = new Thirukkural();
                thirukkural.setId(Integer.parseInt(cursor.getString(0)));
                thirukkural.setKural(cursor.getString(1));
                thirukkural.setFirstExplanation(cursor.getString(2));
                thirukkural.setSecondExplanation(cursor.getString(3));
                thirukkural.setThirdExplanation(cursor.getString(4));

                thirukkurals.add(thirukkural);
            } while (cursor.moveToNext());
        }
        return thirukkurals;
    }
}
