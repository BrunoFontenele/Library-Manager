package bci.core;

import bci.core.UserBehavior;

public class User {
    private int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private int _fine;
    private UserBehavior _behavior;

    public User(String name, String email, int id){
        _id = id;
        _newUserId++;
        _isActive = true;
        _name = name;
        _email = email;
        _fine = 0;
        _behavior = "NORMAL";
    }

    boolean isActive() {
        return _isActive;
    }

    public String toString(){
        if(_fine == 0)
            return String.format("%d - %s - %s - %s - %b",
            _id, _name, _email, _behavior, _isActive);
        return String.format("%d - %s - %s - %s - %b - %s %d",
        _id, _name, _email, _behavior, _isActive, "EUR", _fine);
    }
}
