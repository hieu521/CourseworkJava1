package com.example.nagivationbutton;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nagivationbutton.Hiking;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_HIKING = "test";
    private static final String HIKE_TABLE_NAME = "hikes";
    public static final String HIKE_ID_COLUMN = "hike_id";
    public static final String HIKE_NAME_COLUMN = "name";
    public static final String HIKE_LOCATION_COLUMN = "location";
    public static final String HIKE_DATE_COLUMN = "date";
    public static final String HIKE_PARKING_COLUMN = "parking";
    public static final String HIKE_LENGTH_COLUMN = "length";
    public static final String HIKE_DIFFICULTY_COLUMN = "difficulty";
    public static final String HIKE_DESCRIPTION_COLUMN = "description";

    // Define the table Observation
    private static final String DATABASE_OBSERVATION = "observation";
    private static final String OBSERVATION_ID_COLUM = "id";
    private static final String OBSERVATION_NAME_COLUMN = "name";
    private static final String OBSERVATION_Time_COLUMN = "time";
    private static final String OBSERVATION_COMMENT_COLUMN = "comment";
    private static final String HikingId_COLUMN = "hikingId";

    private static final String HIKE_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT );",
            HIKE_TABLE_NAME, HIKE_ID_COLUMN, HIKE_NAME_COLUMN, HIKE_LOCATION_COLUMN, HIKE_DATE_COLUMN, HIKE_PARKING_COLUMN,
            HIKE_LENGTH_COLUMN, HIKE_DIFFICULTY_COLUMN, HIKE_DESCRIPTION_COLUMN
    );

    private static final String createTableObservationSQL = " CREATE TABLE IF NOT EXISTS " + DATABASE_OBSERVATION + " (" +
            OBSERVATION_ID_COLUM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            OBSERVATION_NAME_COLUMN + " TEXT, " +
            OBSERVATION_Time_COLUMN + " TEXT, " +
            OBSERVATION_COMMENT_COLUMN + " TEXT, " +
            HikingId_COLUMN + " INTEGER);";
    ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_HIKING, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(HIKE_TABLE_CREATE);
        sqLiteDatabase.execSQL(createTableObservationSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HIKE_TABLE_NAME);
        onCreate(db);
    }

    public long insertHikeDetails(String name, String location, String date, String parking, String length, String difficulty, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rowValues = new ContentValues();

        rowValues.put(HIKE_NAME_COLUMN, name);
        rowValues.put(HIKE_LOCATION_COLUMN, location);
        rowValues.put(HIKE_DATE_COLUMN, date);
        rowValues.put(HIKE_PARKING_COLUMN, parking);
        rowValues.put(HIKE_LENGTH_COLUMN, length);
        rowValues.put(HIKE_DIFFICULTY_COLUMN, difficulty);
        rowValues.put(HIKE_DESCRIPTION_COLUMN, description);

        long newRowId = db.insert(HIKE_TABLE_NAME, null, rowValues);
        db.close();

        return newRowId;
    }

    public List<Hiking> getAllHikes() {
        List<Hiking> hikeList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(HIKE_TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Extract data from the cursor for each record
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(HIKE_ID_COLUMN));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(HIKE_NAME_COLUMN));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(HIKE_LOCATION_COLUMN));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(HIKE_DATE_COLUMN));
                @SuppressLint("Range") String parkingAvailable = cursor.getString(cursor.getColumnIndex(HIKE_PARKING_COLUMN));
                @SuppressLint("Range") String lengthOfHike = cursor.getString(cursor.getColumnIndex(HIKE_LENGTH_COLUMN));
                @SuppressLint("Range") String difficultLevel = cursor.getString(cursor.getColumnIndex(HIKE_DIFFICULTY_COLUMN));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(HIKE_DESCRIPTION_COLUMN));

                // Create a UserData object for this record
                Hiking hiking = new Hiking(id, name, location, date, parkingAvailable, lengthOfHike, difficultLevel, description);

                // Add the UserData object to the list
                hikeList.add(hiking);
            }
            cursor.close();
        }

        return hikeList;
    }

    public int updateHikingRecord(long id, String name, String location, String date, String parkingAvailable, String lengthOfHike, String difficultLevel, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rowvalues = new ContentValues();
        rowvalues.put(HIKE_NAME_COLUMN, name);
        rowvalues.put(HIKE_LOCATION_COLUMN, location);
        rowvalues.put(HIKE_DATE_COLUMN, date);
        rowvalues.put(HIKE_PARKING_COLUMN, parkingAvailable);
        rowvalues.put(HIKE_LENGTH_COLUMN, lengthOfHike);
        rowvalues.put(HIKE_DIFFICULTY_COLUMN, difficultLevel);
        rowvalues.put(HIKE_DESCRIPTION_COLUMN, description);
        int rowsUpdated = db.update(HIKE_TABLE_NAME, rowvalues, HIKE_ID_COLUMN + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated;
    }

    @SuppressLint("Range")
    public Hiking getHikingRecordById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = HIKE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(HIKE_NAME_COLUMN, null, selection, selectionArgs, null, null, null);

        Hiking hikingData = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int Id = cursor.getColumnIndex("id");
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String parkingAvailable = cursor.getString(cursor.getColumnIndex("parkingAvailable"));
                String lengthOfHike = cursor.getString(cursor.getColumnIndex("lengthOfHike"));
                String difficultLevel = cursor.getString(cursor.getColumnIndex("difficultLevel"));
                String description = cursor.getString(cursor.getColumnIndex("description"));

                // Create a HikingData object for this record
                hikingData = new Hiking(
                        cursor.getInt(Id),
                        name,
                        location,
                        date,
                        parkingAvailable,
                        lengthOfHike,
                        difficultLevel,
                        description
                );
            }

            cursor.close();
        }

        db.close();
        return hikingData;
    }

    // Delete Hiking a record from the table
    public int deleteHikingRecord(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(HIKE_TABLE_NAME, HIKE_ID_COLUMN + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    // Insert Observation a new record into the table
    public long insertObservationRecord(String name, String time, String comment, int hikingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBSERVATION_NAME_COLUMN,name);
        values.put(OBSERVATION_Time_COLUMN, time);
        values.put(OBSERVATION_COMMENT_COLUMN, comment);
        values.put(HikingId_COLUMN, hikingId);
        long newRowId = db.insert(DATABASE_OBSERVATION, null, values);
        db.close();
        return newRowId;
    }

    // Query Observation all records from the table
    public Cursor getAllObservationRecords(int hikingId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = HIKE_ID_COLUMN + "=?";
        String[] selectionArgs = {String.valueOf(hikingId)};
        return db.query(DATABASE_OBSERVATION, null, selection, selectionArgs, null, null, null);
    }

    // Update Observation a record in the table
    public int updateObservationRecord(int id, String name, String time, String comment, int hikingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBSERVATION_NAME_COLUMN, name);
        values.put(OBSERVATION_Time_COLUMN, time);
        values.put(OBSERVATION_COMMENT_COLUMN, comment);
        values.put(HikingId_COLUMN, hikingId);
        int rowsUpdated = db.update(DATABASE_OBSERVATION, values, OBSERVATION_ID_COLUM + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated;
    }



    // Delete Observation a record from the table
    public int deleteObservationRecord(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(DATABASE_OBSERVATION, OBSERVATION_ID_COLUM + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    // Get an ObservationData record by its ID
    @SuppressLint("Range")
    public Observation getObservationRecordById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                OBSERVATION_ID_COLUM,
                OBSERVATION_NAME_COLUMN,
                OBSERVATION_Time_COLUMN,
                OBSERVATION_COMMENT_COLUMN,
                HikingId_COLUMN
        };
        String selection = OBSERVATION_ID_COLUM + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(DATABASE_OBSERVATION, projection, selection, selectionArgs, null, null, null);

        Observation observationData = null;

        if (cursor != null && cursor.moveToFirst()) {
            int obsId = cursor.getInt(cursor.getColumnIndex(OBSERVATION_ID_COLUM));
            String name = cursor.getString(cursor.getColumnIndex(OBSERVATION_NAME_COLUMN));
            String time = cursor.getString(cursor.getColumnIndex(OBSERVATION_Time_COLUMN));
            String comment = cursor.getString(cursor.getColumnIndex(OBSERVATION_COMMENT_COLUMN));
            int hikingId = cursor.getInt(cursor.getColumnIndex(HikingId_COLUMN));

            observationData = new Observation(obsId, name, time, comment, hikingId);
            cursor.close();
        }

        db.close();
        return observationData;
    }
}
