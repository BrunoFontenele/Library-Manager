package bci.core;

import java.io.Serializable;
import java.net.Authenticator.RequestorType;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

class User implements Serializable{
    private final int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private int _fine;
    private UserBehavior _behavior;
    private List<Request> _activeUserRequests;
    private List<Request> _inactiveUserRequests;

    User(String name, String email, int nextUserId){
        _id = nextUserId;
        _isActive = true;
        _name = name;
        _email = email;
        _fine = 0;
        _behavior = Normal.getNormalBehavior();
        _activeUserRequests = new ArrayList<>();
        _inactiveUserRequests = new ArrayList<>();
    }

    void setActive(){
        _isActive = !_isActive;
    }

    Boolean isActive() {
        return _isActive;
    }

    void setBehavior(UserBehavior newBehavior){
        _behavior = newBehavior;
    }

    UserBehavior getBehavior() {return _behavior;}

    int getUserId(){return _id;}

    String getName(){return _name;}

    List<Request> getUserRequests(){
        return Collections.unmodifiableList(_activeUserRequests);
    }

    int getActiveNumReq(){return _activeUserRequests.size();}

    void addUserRequest(Request request){
        _activeUserRequests.add(request);
    }

    int removeUserRequest(int workId){
        Iterator<Request> iterator = _activeUserRequests.iterator();
        while (iterator.hasNext()) {
            Request request = iterator.next();
            if (request.getWorkId() == workId) {
                iterator.remove();
                _inactiveUserRequests.add(request);
                return _fine;
            }
        }
        return -1;
    }

    void payFine(int quantity, int day){
        if(quantity>0)
            _fine -= quantity;
        for(Request request : _activeUserRequests){
            if(request.getWorkId() < day)
                return;
        }
        _isActive = true;
    }

    int getUserFine(){
        return _fine;
    }

    void checkRequisitions(int day){
        if(_fine != 0) _fine = 0;
        for(Request request : _activeUserRequests)
            if(request.getWorkId() < day) {
                _isActive = false;
                _fine += (day - request.getEndOfRequest()) * 5;
            }
    }

    public String toString(){
        if(_fine == 0)
            return String.format("%d - %s - %s - %s - %s",
            _id, _name, _email, _behavior, _isActive? "ACTIVO":"SUSPENSO");
        return String.format("%d - %s - %s - %s - %s - %s %d",
        _id, _name, _email, _behavior, _isActive? "ACTIVO":"SUSPENSO" , "EUR", _fine);
    }
}
