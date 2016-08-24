package database;

/**
 * Created by Emil on 08/08/16.
 */
public class Reading {

    int _id;
    long _timestamp;

    float _acc1x;
    float _acc1y;
    float _acc1z;
    float _acc2x;
    float _acc2y;
    float _acc2z;

    String _eventType;

    // Variable for storing the FK to table Children
    int _childID;

    public Reading() {}

    public Reading(int id, long timestamp, float acc1x, float acc1y, float acc1z, float acc2x, float acc2y, float acc2z, String eventType, int childID) {
        this._id = id;
        this._timestamp = timestamp;
        this._acc1x = acc1x;
        this._acc1y = acc1y;
        this._acc1z = acc1z;
        this._acc2x = acc2x;
        this._acc2y = acc2y;
        this._acc2z = acc2z;
        this._eventType = eventType;
        this._childID = childID;
    }

    public Reading(long timestamp, float acc1x, float acc1y, float acc1z, float acc2x, float acc2y, float acc2z, String eventType, int childID) {
        this._timestamp = timestamp;
        this._acc1x = acc1x;
        this._acc1y = acc1y;
        this._acc1z = acc1z;
        this._acc2x = acc2x;
        this._acc2y = acc2y;
        this._acc2z = acc2z;
        this._eventType = eventType;
        this._childID = childID;
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

    public float get_acc1x() {
        return _acc1x;
    }

    public void set_acc1x(float _acc1x) {
        this._acc1x = _acc1x;
    }

    public float get_acc1y() {
        return _acc1y;
    }

    public void set_acc1y(float _acc1y) {
        this._acc1y = _acc1y;
    }

    public float get_acc1z() {
        return _acc1z;
    }

    public void set_acc1z(float _acc1z) {
        this._acc1z = _acc1z;
    }

    public float get_acc2x() {
        return _acc2x;
    }

    public void set_acc2x(float _acc2x) {
        this._acc2x = _acc2x;
    }

    public float get_acc2y() {
        return _acc2y;
    }

    public void set_acc2y(float _acc2y) {
        this._acc2y = _acc2y;
    }

    public float get_acc2z() {
        return _acc2z;
    }

    public void set_acc2z(float _acc2z) {
        this._acc2z = _acc2z;
    }

    public String get_eventType() {
        return _eventType;
    }

    public void set_eventType(String _eventType) {
        this._eventType = _eventType;
    }

    public int get_childID() {
        return _childID;
    }

    public void set_childID(int _childID) {
        this._childID = _childID;
    }
}