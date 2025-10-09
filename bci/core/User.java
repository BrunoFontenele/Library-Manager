package bci.core;

import java.io.Serializable;

class User implements Serializable{
    private final int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private int _fine;
    private UserBehavior _behavior;

    User(String name, String email, int nextUserId){
        _id = nextUserId;
        _isActive = true;
        _name = name;
        _email = email;
        _fine = 0;
        _behavior = UserBehavior.NORMAL;
    }

    String isActive() {
        if(_isActive) return "ACTIVO";
        return "SUSPENSO";
    }

    int getUserId(){return _id;}

    String getName(){return _name;}


    public String toString(){
        if(_fine == 0)
            return String.format("%d - %s - %s - %s - %s",
            _id, _name, _email, _behavior, isActive());
        return String.format("%d - %s - %s - %s - %s - %s %d",
        _id, _name, _email, _behavior, isActive(), "EUR", _fine);
    }
}
