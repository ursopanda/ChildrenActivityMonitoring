package database;

import android.content.Context;
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

}
