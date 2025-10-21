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
        return Collections.unmodifiableList(_userRequests);
    }

    void addUserRequest(Request request){
        _userRequests.add(request);
    }

    int removeUserRequest(int workId){
        Iterator<Request> iterator = _userRequests.iterator();
        while (iterator.hasNext()) {
            Request request = iterator.next();
            if (request.getWorkId() == workId) {
                iterator.remove(); 
                int fine = (getCurrentDay() - request.getEndOfRequest()) * 5;
                _fine += fine;
                return fine <= 0? 0:fine; 
            }
        }
        return -1;
    }

    void payFine(int quantity){
        if(quantity>0)
            _fine -= quantity;

        
    }

    int getUserFine(){
        return _fine;
    }

    public String toString(){
        if(_fine == 0)
            return String.format("%d - %s - %s - %s - %s",
            _id, _name, _email, _behavior, _isActive? "ACTIVO":"SUSPENSO");
        return String.format("%d - %s - %s - %s - %s - %s %d",
        _id, _name, _email, _behavior, _isActive? "ACTIVO":"SUSPENSO" , "EUR", _fine);
    }
}
