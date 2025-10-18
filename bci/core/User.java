package bci.core;

import java.io.Serializable;

class User implements Serializable{
    private final int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private int _fine;
    private UserBehavior _behavior;
    private int _activeReqNumber;

    User(String name, String email, int nextUserId){
        _id = nextUserId;
        _isActive = true;
        _name = name;
        _email = email;
        _fine = 0;
        _behavior = Normal.getNormalBehavior();
        _activeReqNumber = 0;
    }

    void alterActiveReqNum(int num){
        if(num < 0 && _activeReqNumber != 0) _activeReqNumber -= 1; 
        else{
            _activeReqNumber += 1;
        }
    }

    void setBehavior(Behavior newBehavior){
        _behavior = newBehavior;
    }

    void setActive(){
        _isActive = !_isActive;
    }

    Boolean isActive() {
        return _isActive;
    }

    int getUserId(){return _id;}

    String getName(){return _name;}

    int getActiveNumReq(){return _activeReqNumber;}

    UserBehavior getBehavior() {return _behavior;}


    public String toString(){
        if(_fine == 0)
            return String.format("%d - %s - %s - %s - %s",
            _id, _name, _email, _behavior, _isActive? "ACTIVO":"SUSPENSO");
        return String.format("%d - %s - %s - %s - %s - %s %d",
        _id, _name, _email, _behavior, _isActive? "ACTIVO":"SUSPENSO" , "EUR", _fine);
    }
}
