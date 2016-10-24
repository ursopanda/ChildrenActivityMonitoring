package database;

/**
 * Created by Emil on 24/10/2016.
 */
public class Data {

    int _id;
    long _timestamp;

    float _acc1x;
    float _acc1y;
    float _acc1z;
    float _acc2x;
    float _acc2y;
    float _acc2z;

    float _magn1x;
    float _magn1y;
    float _magn1z;
    float _magn2x;
    float _magn2y;
    float _magn2z;

    String _smart_device_ID;

   // TO:DO!!! String DEVICE_ID;

    // Constructors

    public Data() {}

    public Data(int id, long timestamp,
                float acc1x, float acc1y, float acc1z, float acc2x, float acc2y, float acc2z,
                float magn1x, float magn1y, float magn1z, float magn2x, float magn2y, float magn2z, String smart_device_ID) {
        this._id = id;
        this._timestamp = timestamp;

        this._acc1x = acc1x;
        this._acc1y = acc1y;
        this._acc1z = acc1z;
        this._acc2x = acc2x;
        this._acc2y = acc2y;
        this._acc2z = acc2z;

        this._magn1x = magn1x;
        this._magn1y = magn1y;
        this._magn1z = magn1z;
        this._magn2x = magn2x;
        this._magn2z = magn2z;

        this._smart_device_ID = smart_device_ID;
    }

    public Data(long timestamp,
                float acc1x, float acc1y, float acc1z, float acc2x, float acc2y, float acc2z,
                float magn1x, float magn1y, float magn1z, float magn2x, float magn2y, float magn2z, String smart_device_ID) {
        this._timestamp = timestamp;

        this._acc1x = acc1x;
        this._acc1y = acc1y;
        this._acc1z = acc1z;
        this._acc2x = acc2x;
        this._acc2y = acc2y;
        this._acc2z = acc2z;

        this._magn1x = magn1x;
        this._magn1y = magn1y;
        this._magn1z = magn1z;
        this._magn2x = magn2x;
        this._magn2z = magn2z;

        this._smart_device_ID = smart_device_ID;
    }

    // Getters and Setters

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

    public float get_magn1x() {
        return _magn1x;
    }

    public void set_magn1x(float _magn1x) {
        this._magn1x = _magn1x;
    }

    public float get_magn1y() {
        return _magn1y;
    }

    public void set_magn1y(float _magn1y) {
        this._magn1y = _magn1y;
    }

    public float get_magn1z() {
        return _magn1z;
    }

    public void set_magn1z(float _magn1z) {
        this._magn1z = _magn1z;
    }

    public float get_magn2x() {
        return _magn2x;
    }

    public void set_magn2x(float _magn2x) {
        this._magn2x = _magn2x;
    }

    public float get_magn2y() {
        return _magn2y;
    }

    public void set_magn2y(float _magn2y) {
        this._magn2y = _magn2y;
    }

    public float get_magn2z() {
        return _magn2z;
    }

    public void set_magn2z(float _magn2z) {
        this._magn2z = _magn2z;
    }

    public String get_smart_device_ID() {
        return _smart_device_ID;
    }

    public void set_smart_device_ID(String _device_ID) {
        this._smart_device_ID = _smart_device_ID;
    }
}
