package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import app.edi.palmprothesismotionmonitoring.PatientApplication;

/**
 * Created by Emil on 24/10/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "activityMonitoringDB";
    private PatientApplication application;

    // Fields for Table Data
    private static final String TABLE_DATA = "data";
    private static final String DATA_ID = "id";
    private static final String DATA_TIMESTAMP = "timestamp";

    private static final String ACC1X = "acc1x";
    private static final String ACC1Y = "acc1y";
    private static final String ACC1Z = "acc1z";
    private static final String ACC2X = "acc2x";
    private static final String ACC2Y = "acc2y";
    private static final String ACC2Z = "acc2z";

    private static final String MAGN1X = "magn1x";
    private static final String MAGN1Y = "magn1y";
    private static final String MAGN1Z = "magn1z";
    private static final String MAGN2X = "magn2x";
    private static final String MAGN2Y = "magn2y";
    private static final String MAGN2Z = "magn2z";

    private static final String SMART_DEVICE_ID = "smart_device_ID";

    // Fields for Table Event
    private static final String TABLE_EVENT = "event";
    private static final String EVENT_ID = "id";
    private static final String EVENT_TIMESTAMP = "timestamp";
    private static final String ACTION = "action";
    private static final String CHILD_ID = "child_ID";
    private static final String BT_STATUS = "bt_status";
    private static final String BATTERY_STATUS = "battery_status";
    private static final String IS_PROCESSING_RUNNING = "is_processing_running";
    private static final String IS_DATA_OK = "is_data_ok";

    // Field for Table Device
    private static final String TABLE_DEVICE = "device";
    private static final String DEVICE_ID = "device_ID";
    private static final String MAC_ADDR_DEVICE = "mac_addr_device";
    private static final String MAC_ADDR_PHONE  = "mac_addr_phone";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATA_TIMESTAMP + " TIMESTAMP,"
                + ACC1X + " FLOAT,"
                + ACC1Y + " FLOAT,"
                + ACC1Z + " FLOAT,"
                + ACC2X + " FLOAT,"
                + ACC2Y + " FLOAT,"
                + ACC2Z + " FLOAT,"
                + SMART_DEVICE_ID + " TEXT" + ")";

        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
                + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EVENT_TIMESTAMP + " TIMESTAMP,"
                + ACTION + " TEXT,"
                + CHILD_ID + " TEXT,"
                + BT_STATUS + " TEXT,"
                + BATTERY_STATUS + " TEXT,"
                + IS_PROCESSING_RUNNING + " BOOLEAN,"
                + IS_DATA_OK + " BOOLEAN" + ")";

        String CREATE_DEVICE_TABLE = "CREATE TABLE " + TABLE_DEVICE + "("
                + DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MAC_ADDR_DEVICE + " TEXT,"
                + MAC_ADDR_PHONE + " TEXT" + ")";

        db.execSQL(CREATE_DATA_TABLE);
        db.execSQL(CREATE_DEVICE_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
    }

    // upgrading DB
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older DB version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE);

        // create table again
        onCreate(db);
    }

    // CRUD operations for Data Table
    public void addData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATA_ID, data.get_id());
        values.put(DATA_TIMESTAMP, data.get_timestamp());

        values.put(ACC1X, data.get_acc1x());
        values.put(ACC1Y, data.get_acc1y());
        values.put(ACC1Z, data.get_acc1z());
        values.put(ACC2X, data.get_acc2x());
        values.put(ACC2Y, data.get_acc2y());
        values.put(ACC2Z, data.get_acc2z());

        values.put(MAGN1X, data.get_magn1x());
        values.put(MAGN1Y, data.get_magn1y());
        values.put(MAGN1Z, data.get_magn1z());
        values.put(MAGN2X, data.get_magn2x());
        values.put(MAGN2Y, data.get_magn2y());
        values.put(MAGN2Z, data.get_magn2z());

        values.put(DEVICE_ID, data.get_smart_device_ID());

        db.insert(TABLE_DATA, null, values);
        db.close();
    }

    public List<Data> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Data> dataList = new ArrayList<>();

        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_DATA;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to created arrayList
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();

                data.set_id(Integer.parseInt(cursor.getString(0)));
                data.set_timestamp(Long.parseLong(cursor.getString(1)));

                data.set_acc1x(Float.parseFloat(cursor.getString(2)));
                data.set_acc1y((Float.parseFloat(cursor.getString(3))));
                data.set_acc1z((Float.parseFloat(cursor.getString(4))));
                data.set_acc2x((Float.parseFloat(cursor.getString(5))));
                data.set_acc2y((Float.parseFloat(cursor.getString(6))));
                data.set_acc2z((Float.parseFloat(cursor.getString(7))));

                data.set_magn1x((Float.parseFloat(cursor.getString(8))));
                data.set_magn1y((Float.parseFloat(cursor.getString(9))));
                data.set_magn1z((Float.parseFloat(cursor.getString(10))));
                data.set_magn2x((Float.parseFloat(cursor.getString(11))));
                data.set_magn2z((Float.parseFloat(cursor.getString(12))));
                data.set_smart_device_ID(cursor.getString(13));

                dataList.add(data);
//                try {
//                    String tmp = data.get_timestamp() + " " + data.get_acc1x() + " " + data.get_acc1y() + " "
//                            + data.get_acc1z() + " " + data.get_acc2x() + " " + data.get_acc2y() + " " + data.get_acc2z();
//                    application.writeDataToFile(tmp, application.dataFile);
//                }
//                catch (IOException e) {}

            } while (cursor.moveToNext());
        }
        return dataList;
    }


    // CRUD operations for Event Table
    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(EVENT_ID, event.get_id());
        values.put(EVENT_TIMESTAMP, event.get_timestamp());
        values.put(ACTION, event.get_action());
        values.put(CHILD_ID, event.get_child_ID());
        values.put(BT_STATUS, event.get_BT_status());
        values.put(BATTERY_STATUS, event.get_battery_status());
        values.put(IS_PROCESSING_RUNNING, event.get_isProcessingRunning());
        values.put(IS_DATA_OK, event._isDataOK);

        db.insert(TABLE_EVENT, null, values);
        db.close();
    }

    public List<Event> getAllEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Event> eventList = new ArrayList<>();

        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_EVENT;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to created arrayList
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();

                event.set_id(Integer.parseInt(cursor.getString(0)));
                event.set_timestamp(Long.parseLong(cursor.getString(1)));
                event.set_action(cursor.getString(2));
                event.set_child_ID(cursor.getString(3));
                event.set_BT_status(cursor.getString(4));
                event.set_battery_status(cursor.getString(5));
                event.set_isProcessingRunning(Boolean.parseBoolean(cursor.getString(6)));
                event.set_isDataOK(Boolean.parseBoolean(cursor.getString(7)));

                eventList.add(event);

            } while (cursor.moveToNext());
        }
        return eventList;
    }

    // CRUD operations for Device Table
    public void addDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(DEVICE_ID, device.get_id());
        values.put(MAC_ADDR_DEVICE, device.get_MAC_ADDR_DEVICE());
        values.put(MAC_ADDR_PHONE, device.get_MAC_ADDR_PHONE());

        db.insert(TABLE_DEVICE, null, values);
        db.close();
    }

    public List<Device> getAllDevices() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Device> deviceList = new ArrayList<>();

        // select all query
        String selectQuery = "SELECT * FROM " + TABLE_DEVICE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to created arrayList
        if (cursor.moveToFirst()) {
            do {
                Device device = new Device();

                device.set_id(Integer.parseInt(cursor.getString(0)));
                device.set_MAC_ADDR_DEVICE(cursor.getString(1));
                device.set_MAC_ADDR_PHONE(cursor.getString(2));

                deviceList.add(device);

            } while (cursor.moveToNext());
        }
        return deviceList;
    }

    public int getDataRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public static void backupDatabase() throws IOException {
        //Open your local db as the input stream
        String inFileName = "/data/user/0/app.edi.palmprosthesismonitoring/databases/ActivityMonitoringDB";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory()+"/ActivityMonitoringDB";
        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        //Close the streams
        output.flush();
        output.close();
        fis.close();
    }

}
