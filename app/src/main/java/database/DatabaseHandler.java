package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 08/08/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "monitoringSession";

    // Fields for table children
    private static final String TABLE_CHILD = "children";
    private static final String TABLE_CHILD_ID = "personID";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String SEX = "sex";
    private static final String AGE = "age";

    // Fields for table sessions
    private static final String TABLE_SESSIONS = "sessions";
    private static final String SESSION_ID = "id";
    private static final String TIMESTAMP = "timestamp";
    private static final String ACC1X = "acc1x";
    private static final String ACC1Y = "acc1y";
    private static final String ACC1Z = "acc1z";
    private static final String ACC2X = "acc2x";
    private static final String ACC2Y = "acc2y";
    private static final String ACC2Z = "acc2z";
    private static final String EVENTTYPE = "eventType";
    private static final String CHILDIDFK = "childID";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHILD_TABLE = "CREATE TABLE " + TABLE_CHILD + "("
                + TABLE_CHILD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT,"
                + SURNAME + " TEXT,"
                + SEX + " TEXT,"
                + AGE + " INTEGER" + ")";

        String CREATE_SESSIONS_TABLE = "CREATE TABLE " + TABLE_SESSIONS + "("
                + SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TIMESTAMP + " TIMESTAMP,"
                + ACC1X + " FLOAT,"
                + ACC1Y + " FLOAT,"
                + ACC1Z + " FLOAT,"
                + ACC2X + " FLOAT,"
                + ACC2Y + " FLOAT,"
                + ACC2Z + " FLOAT,"
                + EVENTTYPE + " TEXT" + ")";
//                + CHILDIDFK + " INTEGER,"
//                + " FOREIGN KEY ("+CHILDIDFK+") REFERENCES "+TABLE_CHILD+"("+TABLE_CHILD_ID+"));";

        db.execSQL(CREATE_CHILD_TABLE);
        db.execSQL(CREATE_SESSIONS_TABLE);
    }

    // upgrading DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older DB version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);

        // create table again
        onCreate(db);
    }

    // implementation of CRUD operations for Child table
    // adding new child
    public void addChild(Child child) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_CHILD_ID, child.get_id());
        values.put(NAME, child.get_name());
        values.put(SURNAME, child.get_surname());
        values.put(AGE, child.get_age());
        values.put(SEX, child.get_sex());

        db.insert(TABLE_CHILD, null, values);
        db.close();
    }

    // getting single Child
    public Child getChild(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHILD, new String[] {TABLE_CHILD_ID, NAME, SURNAME, SEX, AGE},
                TABLE_CHILD_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Child child = new Child();
        child.set_id(Integer.parseInt(cursor.getString(0)));
        child.set_name(cursor.getString(1));
        child.set_surname(cursor.getString(2));
        child.set_sex(cursor.getString(3));
        child.set_age(Integer.parseInt(cursor.getString(4)));

        return child;
    }

    public List<Child> getAllChildren() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Child> childList = new ArrayList<>();
        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_CHILD;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to created arrayList
        if (cursor.moveToFirst()) {
            do {
                Child child = new Child();

                child.set_id(Integer.parseInt(cursor.getString(0)));
                child.set_name(cursor.getString(1));
                child.set_surname(cursor.getString(2));
                child.set_sex(cursor.getString(3));
                child.set_age(Integer.parseInt(cursor.getString(4)));

                // adding child to the list
                childList.add(child);
            } while (cursor.moveToNext());
        }
        return childList;
    }

    // delete single child from DB
    public void deleteChild(Child child) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHILD, TABLE_CHILD_ID + " = ?",
                new String[] { String.valueOf(child.get_id()) });
        db.close();
    }

    // implementation of CRUD operations for Reading table
    // adding new reading
    public void addReading(Reading reading) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TIMESTAMP, reading.get_timestamp());
        values.put(ACC1X, reading.get_acc1x());
        values.put(ACC1Y, reading.get_acc1y());
        values.put(ACC1Z, reading.get_acc1z());
        values.put(ACC2X, reading.get_acc2x());
        values.put(ACC2Y, reading.get_acc2y());
        values.put(ACC2Z, reading.get_acc2z());
        values.put(EVENTTYPE, reading.get_eventType());
//        values.put(CHILDIDFK, reading.get_childID());

        db.insert(TABLE_SESSIONS, null, values);
        String selectQuery = "SELECT * FROM " + TABLE_SESSIONS;
        db.rawQuery(selectQuery, null);
        db.close();
    }

    // getting single reading
    public Reading getReading(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SESSIONS, new String[]
                {SESSION_ID, TIMESTAMP, ACC1X, ACC1Y, ACC1Z, ACC2X, ACC2Y, ACC2Z, EVENTTYPE},
//                        , CHILDIDFK},
                SESSION_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Reading reading = new Reading();
        reading.set_id(Integer.parseInt(cursor.getString(0)));
        reading.set_timestamp(Long.parseLong(cursor.getString(1)));

        reading.set_acc1x(Float.parseFloat(cursor.getString(2)));
        reading.set_acc1y(Float.parseFloat(cursor.getString(3)));
        reading.set_acc1z(Float.parseFloat(cursor.getString(4)));
        reading.set_acc2x(Float.parseFloat(cursor.getString(5)));
        reading.set_acc2y(Float.parseFloat(cursor.getString(6)));
        reading.set_acc2z(Float.parseFloat(cursor.getString(7)));

        reading.set_eventType(cursor.getString(8));
//        reading.set_childID(Integer.parseInt(cursor.getString(9)));

        return reading;
    }

//    // getting all readings
    public List<Reading> getAllReadings() {
        List<Reading> readingList = new ArrayList<Reading>();

        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_SESSIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to arraylist
        if (cursor.moveToFirst()) {
            do {
                Reading reading = new Reading();
                reading.set_id(Integer.parseInt(cursor.getString(0)));
                reading.set_timestamp(Long.parseLong(cursor.getString(1)));

                reading.set_acc1x(Float.parseFloat(cursor.getString(2)));
                reading.set_acc1y(Float.parseFloat(cursor.getString(3)));
                reading.set_acc1z(Float.parseFloat(cursor.getString(4)));
                reading.set_acc2x(Float.parseFloat(cursor.getString(5)));
                reading.set_acc2y(Float.parseFloat(cursor.getString(6)));
                reading.set_acc2z(Float.parseFloat(cursor.getString(7)));

                reading.set_eventType(cursor.getString(8));
//                reading.set_childID(Integer.parseInt(cursor.getString(9)));

                // adding reading to the list
                readingList.add(reading);

            } while (cursor.moveToNext());
        }
        return readingList;
    }

    // TO-DO: Implement this in order to get rows amount in TABLE_SESSIONS

//    public void getReadingsAmount(SQLiteDatabase db) {
//        db.execSQL(SELECT COALESCE(MAX(id)+1, 0) FROM TABLE_SESSIONS);
//    }

    // delete single reading
    public void deleteReading(Reading reading) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESSIONS, SESSION_ID + " = ?",
                new String[] { String.valueOf(reading.get_id()) });
        db.close();
    }

    public void deleteDataFromDB() {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CHILD, null, null);
        db.delete(TABLE_SESSIONS, null, null);
        db.close();
    }
}