package database;

/**
 * Created by Emil on 24/10/2016.
 */
public class Event {

    int _id;
    long _timestamp;
    String _action;
    String _child_ID;
    String _BT_status;
    String _battery_status;
    boolean _isProcessingRunning;
    boolean _isDataOK;

    public Event() {}

    public Event(int id, long timestamp, String action, String child_ID,
                 String BT_status, String battery_status, boolean isProcessingRunning, boolean isDataOK) {
            this._id = id;
            this._timestamp = timestamp;
            this._action = action;
            this._child_ID = child_ID;
            this._BT_status = BT_status;
            this._battery_status = battery_status;
            this._isProcessingRunning = isProcessingRunning;
            this._isDataOK = isDataOK;
    }

    public Event(long timestamp, String action, String child_ID,
                 String BT_status, String battery_status, boolean isProcessingRunning, boolean isDataOK) {
        this._timestamp = timestamp;
        this._action = action;
        this._child_ID = child_ID;
        this._BT_status = BT_status;
        this._battery_status = battery_status;
        this._isProcessingRunning = isProcessingRunning;
        this._isDataOK = isDataOK;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public long get_timestamp() {
        return _timestamp;
    }

    public void set_timestamp(long _timestamp) {
        this._timestamp = _timestamp;
    }

    public String get_action() {
        return _action;
    }

    public void set_action(String _action) {
        this._action = _action;
    }

    public String get_child_ID() {
        return _child_ID;
    }

    public void set_child_ID(String _child_ID) {
        this._child_ID = _child_ID;
    }

    public String get_BT_status() {
        return _BT_status;
    }

    public void set_BT_status(String _BT_status) {
        this._BT_status = _BT_status;
    }

    public String get_battery_status() {
        return _battery_status;
    }

    public void set_battery_status(String _battery_status) {
        this._battery_status = _battery_status;
    }

    public boolean is_isProcessingRunning() {
        return _isProcessingRunning;
    }

    public void set_isProcessingRunning(boolean _isProcessingRunning) {
        this._isProcessingRunning = _isProcessingRunning;
    }

    public boolean is_isDataOK() {
        return _isDataOK;
    }

    public void set_isDataOK(boolean _isDataOK) {
        this._isDataOK = _isDataOK;
    }
}
