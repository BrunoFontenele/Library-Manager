package bci.core;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

class User implements Serializable, Notifiable{
    private final int _id;
    private boolean _isActive;
    private String _name;
    private String _email;
    private int _fine;
    private UserBehavior _behavior;
    private List<Request> _activeUserRequests;
    private List<Boolean> _lastRequestsOnTime;
    private final List<Notification> _notifications;

    User(String name, String email, int nextUserId){
        _id = nextUserId;
        _isActive = true;
        _name = name;
        _email = email;
        _fine = 0;
        _behavior = Normal.getNormalBehavior();
        _activeUserRequests = new ArrayList<>();
        _lastRequestsOnTime = new ArrayList<>();
        _notifications = new ArrayList<>();
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

    int removeUserRequest(int workId, int day){
        Iterator<Request> iterator = _activeUserRequests.iterator();
        while (iterator.hasNext()) {
            Request request = iterator.next();
            if (request.getWorkId() == workId) {
                iterator.remove();
                _lastRequestsOnTime.add(day > request.getEndOfRequest());
                checkBehavior();
                return _fine;
            }
        }
        return -1; //isso vai ser visto no app
    }

    void checkBehavior(){
        int size = _activeUserRequests.size();
        if(size<3) return;
        if(size>5)
            _lastRequestsOnTime.subList(0,size-5).clear();
            //only the 5 last elements
        List<Boolean> lastThree = _lastRequestsOnTime.subList(size-3, size);
        if(lastThree.stream().noneMatch(v -> v == true)) //if all are false
            setBehavior(Faltoso.getFaltosoBehavior());
        if(lastThree.stream().noneMatch(v -> v == false)){
            if(size == 5 && _lastRequestsOnTime.subList(0,size-3).stream().noneMatch(v -> v == false)) {
                setBehavior(Cumpridor.getCumpridorBehavior());
                return;
            }
            setBehavior(Normal.getNormalBehavior());
        }
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

    public void receiveNotification(Notification n) {
        _notifications.add(n);
    }

    List<Notification> viewNotifications() {
        List<Notification> copy = new ArrayList<>(_notifications);
        _notifications.clear();
        return copy;
    }
}
