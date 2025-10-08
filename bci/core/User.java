package bci.core;

import java.io.Serializable;

public class User implements Serializable{
    private int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private int _fine;
    private UserBehavior _behavior;

    public User(String name, String email, int nextUserId){
        _id = nextUserId;
        _isActive = true;
        _name = name;
        _email = email;
        _fine = 0;
        _behavior = UserBehavior.NORMAL;
    }

    public String isActive() {
        if(_isActive){return "ACTIVO";}
        else{return "SUSPENSO";}
    }

    public int getUserId(){return _id;}

    public String getName(){return _name;}

    public String toString(){
        if(_fine == 0)
            return String.format("%d - %s - %s - %s - %s",
            _id, _name, _email, _behavior, isActive());
        return String.format("%d - %s - %s - %s - %s - %s %d",
        _id, _name, _email, _behavior, isActive(), "EUR", _fine);
    }
}
