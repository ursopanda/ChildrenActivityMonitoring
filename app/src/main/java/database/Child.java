package database;

/**
 * Created by Emil on 24/08/16.
 */
public class Child {

    int _personID;
    String _name;
    String _surname;
    String _sex;
    int _age;

    public Child() {}

    public Child(int personID, String name, String surname, String sex, int age) {
        this._personID = personID;
        this._name = name;
        this._surname = surname;
        this._sex = sex;
        this._age = age;
    }

    public Child(String name, String surname, String sex, int age) {
        this._name = name;
        this._surname = surname;
        this._sex = sex;
        this._age = age;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public String get_sex() {
        return _sex;
    }

    public void set_sex(String _sex) {
        this._sex = _sex;
    }

    public String get_surname() {
        return _surname;
    }

    public void set_surname(String _surname) {
        this._surname = _surname;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_id() {
        return _personID;
    }

    public void set_id(int _id) {
        this._personID = _id;
    }
}

