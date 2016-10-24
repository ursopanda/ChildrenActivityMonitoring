package database;

/**
 * Created by Emil on 24/10/2016.
 */
public class Device {

    int _id;
    String _MAC_ADDR_PHONE;
    String _MAC_ADDR_DEVICE;

    public Device() {}

    public Device(int id, String MAC_ADDR_PHONE, String MAC_ADDR_DEVICE) {
        this._id = id;
        this._MAC_ADDR_DEVICE = MAC_ADDR_DEVICE;
        this._MAC_ADDR_PHONE = MAC_ADDR_PHONE;
    }

    public Device(String MAC_ADDR_PHONE, String MAC_ADDR_DEVICE) {
        this._MAC_ADDR_DEVICE = MAC_ADDR_DEVICE;
        this._MAC_ADDR_PHONE = MAC_ADDR_PHONE;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_MAC_ADDR_PHONE() {
        return _MAC_ADDR_PHONE;
    }

    public void set_MAC_ADDR_PHONE(String _MAC_ADDR_PHONE) {
        this._MAC_ADDR_PHONE = _MAC_ADDR_PHONE;
    }

    public String get_MAC_ADDR_DEVICE() {
        return _MAC_ADDR_DEVICE;
    }

    public void set_MAC_ADDR_DEVICE(String _MAC_ADDR_DEVICE) {
        this._MAC_ADDR_DEVICE = _MAC_ADDR_DEVICE;
    }
}
