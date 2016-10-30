package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Emil on 24/10/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "activityMonitoringDB";

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
    }

    // CRUD operations for Event Table
    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EVENT_ID, event.get_id());
        values.put(EVENT_TIMESTAMP, event.get_timestamp());
        values.put(ACTION, event.get_action());
        values.put(CHILD_ID, event.get_child_ID());
        values.put(BT_STATUS, event.get_BT_status());
        values.put(BATTERY_STATUS, event.get_battery_status());
        values.put(IS_PROCESSING_RUNNING, event.get_isProcessingRunning());
        values.put(IS_DATA_OK, event._isDataOK);
    }

    // CRUD operations for Device Table
    public void addDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DEVICE_ID, device.get_id());
        values.put(MAC_ADDR_DEVICE, device.get_MAC_ADDR_DEVICE());
        values.put(MAC_ADDR_PHONE, device.get_MAC_ADDR_PHONE());
    }
}
